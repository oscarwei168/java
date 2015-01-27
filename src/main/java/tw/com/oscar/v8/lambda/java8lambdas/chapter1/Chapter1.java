package tw.com.oscar.v8.lambda.java8lambdas.chapter1;

import tw.com.oscar.v8.lambda.java8lambdas.domain.Artist;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author oscarwei
 * @since 2015/1/16
 */
public class Chapter1 extends MusicChapter {

    public List<String> getNamesOfArtists_Lambda() {
        return artists.stream()
                .map(artist -> artist.getName())
                .collect(toList());
    }

    public List<String> getNamesOfArtists_MethodReference() {
        return artists.stream()
                .map(Artist::getName)
                .collect(toList());
    }

    public List<Artist> artistsLivingInLondon() {
        return artists.stream()
                .filter(artist -> "London".equals(artist.getNationality()))
                .collect(toList());
    }
}
