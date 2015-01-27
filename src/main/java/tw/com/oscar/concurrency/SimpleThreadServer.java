package tw.com.oscar.concurrency;

import tw.com.oscar.util.Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by oscarwei on 2015/1/9.
 */
public class SimpleThreadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept(); // blocking call - never null!
            new Thread(() -> Utils.process(socket)).start();
        }
    }
}
