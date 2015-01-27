package tw.com.oscar.concurrency;

import tw.com.oscar.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by oscarwei on 2015/1/9.
 */
public class SimpleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept(); // blocking call - never null!
            System.out.println("Connection from " + socket);
            try (InputStream is = socket.getInputStream(); OutputStream os = socket.getOutputStream()) {
                int data;
                while ((data = is.read()) != -1) {
                    data = Utils.transmogrify(data);
                    os.write(data);
                }
            }
        }
    }
}
