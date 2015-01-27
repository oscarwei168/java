package tw.com.oscar.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by oscarwei on 2015/1/9.
 */
public class Utils {

    public static int transmogrify(int data) {
        if (Character.isLetter(data)) {
            return data ^ ' ';
        }
        return data;
    }

    public static void process(Socket socket) {
        System.out.println("Connection from " + socket);
        try (InputStream is = socket.getInputStream(); OutputStream os = socket.getOutputStream()) {
            int data;
            while ((data = is.read()) != -1) {
                data = transmogrify(data);
                os.write(data);
            }
        } catch (IOException e) {
            System.err.println("Connection problem - " + e);
        }
    }

    public static void process(SocketChannel socketChannel) {
        System.out.println("Connection from " + socketChannel);
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // position = 0
            // limit = capacity = 1024
            while (socketChannel.read(byteBuffer) != -1) {
                // byte array with some additional conditions
                // position
                // limit
                // capacity
                byteBuffer.flip();
                for (int i = 0; i < byteBuffer.limit(); i++) {
                    byteBuffer.put(i, (byte) transmogrify(byteBuffer.get(i)));
                }
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            System.err.println("Connection problem - " + e);
        }
    }
}
