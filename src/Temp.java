/**
 * Created by Tolik on 29.10.2014.
 */
public class Temp {
    public static void main(String[] args) {
        for (String s : System.getProperties().stringPropertyNames()) {
            System.out.println(s);
            System.out.println(System.getProperty(s));
        }
    }
}
