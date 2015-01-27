package tw.com.oscar.concurrency;

import tw.com.oscar.util.Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by oscarwei on 2015/1/9.
 */
public class SimpleExecutorServiceServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        // ExecutorService pool = Executors.newCachedThreadPool();
        ExecutorService pool = new ThreadPoolExecutor(0, 1000, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>
                (), new ThreadPoolExecutor.DiscardOldestPolicy());
//        ExecutorService pool1 = Executors.newFixedThreadPool(1000);
        while (true) {
            Socket socket = serverSocket.accept(); // blocking call - never null!
            pool.submit(() -> Utils.process(socket));
        }
    }
}
