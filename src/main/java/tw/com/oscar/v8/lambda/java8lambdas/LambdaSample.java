package tw.com.oscar.v8.lambda.java8lambdas;

import tw.com.oscar.v8.lambda.java8lambdas.domain.Album;
import tw.com.oscar.v8.lambda.java8lambdas.domain.Artist;
import tw.com.oscar.v8.lambda.java8lambdas.domain.Track;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * @author oscarwei
 * @since 2015/1/19
 */
public class LambdaSample {

    ThreadLocal<DateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat
            ("yyyy/MM/dd HH:mm:ss"));

    public static void main(String[] args) {

        Runnable thread = () -> System.out.println("A Supplier functional-style programming");

        BinaryOperator<Integer> add = (a, b) -> a + b;
        System.out.println(add.apply(1, 2));

        String sampleStr = "Oscar Wei love Amy Hung very much"; // space not low case chars
        System.out.println("Low case : " + lowCaseCount(sampleStr)); // answer is 23
        Stream<String> arr = Arrays.stream(new String[] {"Oscar", "Wei", "Amy ", "Hung", "LOVE"});
        System.out.println("Max low case : " + arr.max(comparing(LambdaSample::lowCaseCount))
                .orElse("Nothing"));

        long count = SampleData.threeArtists().filter(a -> a.isFrom("UK")).count();
        System.out.println("Count : " + count);

        List<String> strList = Stream.of("a", "b", "c").map(String::toUpperCase).collect(toList());
        System.out.println(strList.toString());

        Stream<List<Integer>> intStream = Stream.of(asList(1, 2, 3), asList(4, 5, 6));

        List<Integer> intList = intStream.flatMap(numbers -> numbers.stream()).collect(toList());
        // Collect 'multiple' streams to 'one' stream
        Set<String> trackNames = SampleData.albums.flatMap(album -> album.getTracks()).filter(track
                -> track.getLength() > 60).map(track -> track.getName()).collect(toSet());
        System.out.println(trackNames.toString());
        System.out.println(intList.toString());
        System.out.println("Sum : " + intList.stream().reduce(0, (a, b) -> a + b));
        System.out.println("Min : " + intList.stream().reduce(Integer::min).get());
        System.out.println("Max : " + intList.stream().reduce(Integer::max).get());
        Track shortestTrack = SampleData.manyTrackAlbum.getTracks().max(comparing(track
                -> track.getLength())).get();

        // Just for demo
        BinaryOperator<Integer> accumylator = (acc, element) -> acc + element;
        System.out.println(accumylator.apply(accumylator.apply(accumylator.apply(0, 1), 2), 3));

        System.out.println(shortestTrack.getName());

        IntSummaryStatistics summary = SampleData.aLoveSupreme.getTracks().mapToInt(track ->
                track.getLength()).summaryStatistics();
        System.out.printf("Max : %d, Min : %d, Ave : %.2f, Sum : %d", summary.getMax(), summary
                .getMin(), summary.getAverage(), summary.getSum());

        String result = SampleData.getThreeArtists().stream().map(Artist::getName).collect
                (Collectors.joining(", ", "[", "]"));
        System.out.println(result);

        // page 65

    }

    public static int lowCaseCount(String stringStream) {
        Predicate<Integer> lowCaseCalculate = (c -> Character.isLowerCase(c));
        return (int) stringStream.chars().filter(c -> lowCaseCalculate.test(c))
                .count();
    }

    public static Optional<Artist> biggestGroup(Stream<Artist> artists) {
        Function<Artist, Long> getCount = artist -> artist.getMembers().count();
        return artists.collect(maxBy(comparing(getCount)));
    }

    public static double averageNumberOfTrackers(List<Album> albums) {
        return albums.stream().collect(averagingInt(album -> album.getTrackList().size()));
    }

    public Map<Boolean, List<Artist>> bandsAndSolo(Stream<Artist> artistStream) {
        return artistStream.collect(partitioningBy(Artist::isSolo)); // artist -> artist.isSolo()
    }

    public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
        return albums.collect(groupingBy(album -> album.getMainMusician()));
    }

    public Map<Artist, Long> numberOfAlbums(Stream<Album> albums) {
        return albums.collect(groupingBy(album -> album.getMainMusician(), counting()));
    }

    public Map<Artist, List<String>> nameOfAlbums(Stream<Album> albums) {
        return albums.collect(groupingBy(Album::getMainMusician, mapping(Album::getName, toList())));
    }

    public <T> void forEach(Consumer<? super T> action) {
//        for (T t : this) {
//            action.accept(t);
//        }
    }
}
