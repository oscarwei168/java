package tw.com.oscar.concurrency;

import tw.com.oscar.util.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by oscarwei on 2015/1/9.
 */
public class NonblockingSingleThreadedPoolingServer {

    private static Collection<SocketChannel> sockets = Collections.newSetFromMap(new HashMap<SocketChannel, Boolean>());

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.configureBlocking(false);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            // nonblocking call - usually null
            if (null != socketChannel) {
                System.out.println("Connection from " + socketChannel);
                socketChannel.configureBlocking(false);
                sockets.add(socketChannel);
            }
            for (Iterator<SocketChannel> it = sockets.iterator(); it.hasNext(); ) {
                SocketChannel sc = it.next();
                try {
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
                    // position = 0
                    // limit = capacity = 1024
                    int read = sc.read(byteBuffer);
                    if (-1 == read) {
                        it.remove();
                    } else if (0 != read) {
                        byteBuffer.flip();
                        for (int i = 0; i < byteBuffer.limit(); i++) {
                            byteBuffer.put(i, (byte) Utils.transmogrify(byteBuffer.get(i)));
                        }
                        sc.write(byteBuffer);
                        // byteBuffer.clear();
                    }
                } catch (IOException e) {
                    System.err.println("Connection problem - " + e);
                }
            }
        }
    }
}
