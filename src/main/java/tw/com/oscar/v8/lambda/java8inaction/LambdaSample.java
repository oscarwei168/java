package tw.com.oscar.v8.lambda.java8inaction;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * The Java 8 lambda expression samples
 *
 * @author Oscar Wei
 * @since 2015/1/20
 */
public class LambdaSample {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(1, r -> new Thread(r, "Oscar Thread"));
        Future future = pool.submit(() -> 1);
        pool.shutdown();
        System.out.println(future.isDone());

        Stream<Integer> emptyStream = Stream.empty(); // can be any generic type like String...
        System.out.println("Sum : " + emptyStream.mapToInt(d -> 0).sum());

        String str = "Oscar Wei Amy Hung Sunny Wei...";
        Stream<Character> charStream = IntStream.range(0, str.length()).mapToObj(str::charAt);
        charStream.parallel(); // from sequence to parallel style

        Stream<int[]> pythagoreanTriples = pythagoreanTriples();
        pythagoreanTriples.limit(5).forEach(t -> System.out.println(t[0] + "," + t[1] + "," +
                t[2]));

        long[] xx = LongStream.rangeClosed(1, 10).toArray();

        System.out.println("Unique words : " + uniqueWords("src/main/resources/data.txt"));

        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);

        Stream.iterate(new int[] {0, 1}, t -> new int[] {t[1], t[0] + t[1]}).limit(10).map(t ->
                t[0]).forEach(System.out::println);

        System.out.println("Multicore : " + Runtime.getRuntime().availableProcessors());

        Callable<Boolean> task = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        };

    }

    // The Pythagorean theorem --> a * a + b * b = c * c
    private static Stream<int[]> pythagoreanTriples() {
        return IntStream.rangeClosed(1, 100).boxed().flatMap(a -> IntStream.rangeClosed(a, 100)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).mapToObj(b -> new int[] {a, b, (int)
                        Math.sqrt(a * a + b * b)}).filter(t -> t[2] % 1 == 0));
    }

    /**
     * @param filename
     * @return
     */
    private static long uniqueWords(String filename) {
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get(filename), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return uniqueWords;
    }

}
