package tw.com.oscar.concurrency;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by oscarwei on 2015/1/9.
 */
public class NastyChump {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3000; i++) {
            try {
                new Socket("localhost", 8080);
                System.out.println(i);
            } catch (IOException e) {
                System.err.println("Could not connect - " + e);
            }
        }
        Thread.sleep(1000000000L);
    }
}
