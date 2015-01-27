package tw.com.oscar.v8.lambda.java8lambdas;

import tw.com.oscar.v8.lambda.java8lambdas.domain.Album;
import tw.com.oscar.v8.lambda.java8lambdas.domain.Artist;
import tw.com.oscar.v8.lambda.java8lambdas.domain.Track;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * @author oscarwei
 * @since 2015/1/16
 */
public class SampleData {

    public static final Artist johnColtrane = new Artist("John Coltrane", "US");
    public static final Artist johnLennon = new Artist("John Lennon", "UK");
    public static final Artist paulMcCartney = new Artist("Paul McCartney", "UK");
    public static final Artist georgeHarrison = new Artist("George Harrison", "UK");
    public static final Artist ringoStarr = new Artist("Ringo Starr", "UK");
    public static final List<Artist> membersOfTheBeatles = asList(johnLennon, paulMcCartney, georgeHarrison, ringoStarr);
    public static final Artist theBeatles = new Artist("The Beatles", membersOfTheBeatles, "UK");
    public static final Album aLoveSupreme = new Album("A Love Supreme", asList(new Track("Acknowledgement", 467), new Track("Resolution", 442)), asList(johnColtrane));
    public static final Album sampleShortAlbum = new Album("sample Short Album", asList(new Track("short track", 30)), asList(johnColtrane));
    public static final Album manyTrackAlbum = new Album("sample Short Album", asList(new Track
                    ("short track", 10), new Track("short track 2", 20), new Track("short track 3", 30),
            new Track("short track 4", 40), new Track("short track 5", 50)), asList(johnColtrane));
    public static Stream<Album> albums = Stream.of(aLoveSupreme);

    public static Stream<Artist> threeArtists() {
        return Stream.of(johnColtrane, johnLennon, theBeatles);
    }

    public static List<Artist> getThreeArtists() {
        return asList(johnColtrane, johnLennon, theBeatles);
    }
}
