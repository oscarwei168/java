package tw.com.oscar.v8.lambda.java8lambdas;

import tw.com.oscar.v8.lambda.java8lambdas.domain.Artist;

import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

/**
 * @author oscarwei
 * @since 2015/1/16
 */
public interface Performance {

    public String getName();

    public Stream<Artist> getMusicians();

    // TODO: test
    public default Stream<Artist> getAllMusicians() {
        return getMusicians().flatMap(artist -> {
            return concat(Stream.of(artist), artist.getMembers());
        });
    }
}
