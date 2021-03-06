package tw.com.oscar.concurrency;

import tw.com.oscar.util.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by oscarwei on 2015/1/9.
 */
public class NonblockingMultiThreadedSelectorServer {

    static Map<SocketChannel, Queue<ByteBuffer>> pendingData = new ConcurrentHashMap<>();
    static Queue<SocketChannel> toWrite = new ConcurrentLinkedQueue<>();
    static ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            SocketChannel changeToWrite;
            while ((changeToWrite = toWrite.poll()) != null) {
                changeToWrite.register(selector, SelectionKey.OP_WRITE);
            }
            for (Iterator<SelectionKey> itKeys = selector.selectedKeys().iterator(); itKeys.hasNext(); ) {
                SelectionKey key = itKeys.next();
                itKeys.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        // Someone connected to our ServerSocketChannel
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }
            }
        }
    }

    private static void write(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        Queue<ByteBuffer> queue = pendingData.get(sc);
        ByteBuffer buffer;
        while ((buffer = queue.peek()) != null) {
            sc.write(buffer);
            if (!buffer.hasRemaining()) {
                queue.poll();
            } else {
                return;
            }
        }
        sc.register(key.selector(), SelectionKey.OP_READ);
    }

    private static void read(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // position = 0
        // limit = capacity = 1024
        int read = sc.read(byteBuffer);
        if (-1 == read) {
            pendingData.remove(sc);
            return;
        }
        pool.submit(() -> {
            byteBuffer.flip();
            for (int i = 0; i < byteBuffer.limit(); i++) {
                byteBuffer.put(i, (byte) Utils.transmogrify(byteBuffer.get(i)));
            }
            pendingData.get(sc).add(byteBuffer);
            Selector selector = key.selector();
            // sc.register(key.selector(), SelectionKey.OP_WRITE);
            toWrite.add(sc);
            selector.wakeup();
            return null;
        });

    }

    private static void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept(); // non-blocking, never null
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ);
        // add pending data entry.....DON'T FORGOT
        pendingData.put(sc, new ConcurrentLinkedDeque<>());
    }
}
