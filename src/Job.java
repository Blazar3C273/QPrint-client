import org.json.simple.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Job {

    private String file_path;
    private JobState jobState;
    private String name;
    private String order_code;
    private int page_count;
    private double price;

    private String massage;
    private int job_id;
    private String file_name;


    public Job(JSONObject jsonObject) {
        this.file_path = (String) jsonObject.get("file_path");
        this.jobState = JobState.parceString((String) jsonObject.get("state"));
        this.name = (String) jsonObject.get("name");
        this.order_code = (String) jsonObject.get("order_code");
        this.page_count = Integer.parseInt(String.valueOf(jsonObject.get("pages_count")));
        this.price = (Double) jsonObject.get("price");
        this.massage = (String) jsonObject.get("massage");
        this.job_id = Integer.parseInt(String.valueOf(jsonObject.get("id")));
        this.file_name = this.order_code + ".pdf";
    }

    public String getFile_path() {
        return file_path;
    }

    public JobState getJobState() {
        return jobState;
    }

    public String getName() {
        return name;
    }

    public String getOrder_code() {
        return order_code;
    }

    public int getPage_count() {
        return page_count;
    }

    public double getPrice() {
        return price;
    }

    public String getMassage() {
        return massage;
    }

    public int getJob_id() {
        return job_id;
    }

    public String getFile_name() {
        return file_name;
    }


    public void setJobState(JobState jobState) {
        this.jobState = jobState;
    }


    public void startPrintingJob() {
        String PDF_URL = Server.SERVER_ADDRESS;
        PDF_URL += this.getFile_path();
        InputStream is = null;
        FileOutputStream os = null;
        try {
            URL fileurl = new URL(PDF_URL);
            URLConnection connection;
            if (Server.USE_PROXY)
                connection = fileurl.openConnection(new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(Server.PROXY_HOST_ADRESS, Server.PROXY_PORT)));
            else
                connection = fileurl.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();
            os = new FileOutputStream(this.file_name);
            int c;
            while ((c = is.read()) != -1) {
                os.write(c);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ////////////
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        try {
            //"pathToPDFPrinter.exe"+"localpathtoPDFfile"
            //help: http://www.coolutils.com/CommandLine/TotalPDFPrinter
            //PDFPrinter.exe <source> <destination> <options>
            String command_line = "";
            command_line += Server.PATH_TO_PDF_PRINTER_EXE + " command=printpdf input=\"";
            command_line += Server.LOCAL_PATH_TO_PDF_FILE;
            command_line += this.file_name+"\"";
            String optionsString = " -log error_printing_log.txt -verbosity detail -do";
            optionsString = "";
            command_line += optionsString;
            System.out.println(command_line);
            Runtime.getRuntime().exec(command_line);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

