import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class Server {

    public static  String PATH_TO_PDF_PRINTER_EXE = System.getProperty("user.dir") + System.getProperty("file.separator");
    public static final String LOCAL_PATH_TO_PDF_FILE = System.getProperty("user.dir") + System.getProperty("file.separator");
    public static  String SERVER_ADDRESS = "http://10.10.131.81/";

    private static String USER_AGENT = "QPrintClient/0.0";

    public static boolean USE_PROXY = false;

    public static  String PROXY_HOST_ADRESS = "10.10.131.121";
    public static  int PROXY_PORT = 3128;
    public static  HttpHost PROXY_HOST = new HttpHost(PROXY_HOST_ADRESS, PROXY_PORT, "http");

    private static Header global_cookieHeader;
    public static String  DEFAULT_USER;
    public static String  DEFAULT_PASS;
    private static String settingsFileName = System.getProperty("user.dir") + System.getProperty("file.separator") + "settings.json";

    public static Header login(String login, String password) throws LoginException {

        String url = SERVER_ADDRESS +"api/login/";
        Header cookieHeader = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost post = new HttpPost(url);
        if (USE_PROXY) {
            RequestConfig requestConfigr = RequestConfig.custom().setProxy(PROXY_HOST).build();
            post.setConfig(requestConfigr);
        }

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParametersLogin = new ArrayList<NameValuePair>();
        urlParametersLogin.add(new BasicNameValuePair("username", login));
        urlParametersLogin.add(new BasicNameValuePair("password", password));

        try {
            post.setEntity(new UrlEncodedFormEntity(urlParametersLogin));

            HttpResponse response = null;

            response = client.execute(post);

            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + post.getEntity());
            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = null;

            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            Header[] haders = response.getAllHeaders();

            for (int i = 0; i < haders.length; i++) {
                if (haders[i].getName().compareTo("Set-Cookie") == 0) {
                    cookieHeader = haders[i];
                    break;
                }
            }

            StringBuffer result = new StringBuffer();
            String line = "";

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonJobQueue = (JSONObject) jsonParser.parse(result.toString());
            String message = (String) jsonJobQueue.get("message");
            String state = (String) jsonJobQueue.get("state");
            System.out.println(result.toString() + "\n" + message + "\n" + state);
            if (state.compareTo("error") == 0) {
                throw new LoginException(message);
            }

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        global_cookieHeader = cookieHeader;
        return cookieHeader;
    }

    public static java.util.Queue<Job> getJobQueue(Header cookieHeader) throws IOException, ParseException {
        Queue<Job> jobQueue;
        jobQueue = new ArrayDeque<Job>();

        String url = SERVER_ADDRESS +"/api/sync/";

        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        if (USE_PROXY) {
            RequestConfig requestConfig = RequestConfig.custom().setProxy(PROXY_HOST).build();
            request.setConfig(requestConfig);
        }

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Cookie", cookieHeader.getValue());

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonJobQueue = (JSONArray) jsonParser.parse(result.toString());
        for (int i = 0; i < jsonJobQueue.size(); ++i) {
            JSONObject jsonObject = ((JSONObject) jsonJobQueue.get(i));
            jobQueue.add(new Job(jsonObject));
        }

        return jobQueue;
    }

    public static void updateJobState(Job currentJob) {

        String url = SERVER_ADDRESS +"api/job/";
        url += currentJob.getJob_id() + "/updateState/";


        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        if (USE_PROXY) {
            RequestConfig requestConfigr = RequestConfig.custom().setProxy(PROXY_HOST).build();
            post.setConfig(requestConfigr);
        }
        // add header
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Cookie", global_cookieHeader.getValue());

        List<NameValuePair> urlParametersLogin = new ArrayList<NameValuePair>();


        urlParametersLogin.add(new BasicNameValuePair("state", currentJob.getJobState().toString().toLowerCase()));
        urlParametersLogin.add(new BasicNameValuePair("message", "oeua"));

        try {
            post.setEntity(new UrlEncodedFormEntity(urlParametersLogin));

            HttpResponse response = null;

            System.out.println("\nSending job conformation 'P000OST' request to URL : " + url);
            System.out.println(post.getEntity().getContent());
            for (Header h : post.getAllHeaders()) {

                System.out.println("Post parameters : " + h.getValue());
            }
            response = httpclient.execute(post);


            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            CloseableHttpResponse response2 = httpclient.execute(post);
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            long foo = entity2.getContentLength();
            EntityUtils.consume(entity2);

            BufferedReader rd = null;

            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));


            StringBuffer result = new StringBuffer();
            String line = "";

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            System.out.println(foo + "->" + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void loadSettingsFromFile() {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();
        String[] keys = new String[]{
                "server_address",
                "proxy_host",
                "proxy_port",
                "use_proxy",
                "default_login",
                "default_password",
                "pdf_printer_exe_path"

        };

        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader(settingsFileName));
            SERVER_ADDRESS = (String) jsonObject.get(keys[0]);
            PROXY_HOST_ADRESS = (String) jsonObject.get(keys[1]);
            PROXY_PORT = Integer.parseInt((String) jsonObject.get(keys[2]));
            USE_PROXY = Boolean.parseBoolean((String) jsonObject.get(keys[3]));
            DEFAULT_USER = (String) jsonObject.get(keys[4]);
            DEFAULT_PASS = (String) jsonObject.get(keys[5]);
            PATH_TO_PDF_PRINTER_EXE = (String)jsonObject.get(keys[6]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

