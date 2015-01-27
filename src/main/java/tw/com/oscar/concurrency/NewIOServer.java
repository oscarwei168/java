package tw.com.oscar.concurrency;

import tw.com.oscar.util.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by oscarwei on 2015/1/9.
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        ExecutorService pool = Executors.newFixedThreadPool(1000);
        while (true) {
            SocketChannel sc = serverSocketChannel.accept();
            pool.submit(() -> Utils.process(sc));
        }
    }
}
