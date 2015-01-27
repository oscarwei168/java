package tw.com.oscar.v8.lambda.java8lambdas.chapter1;

import tw.com.oscar.v8.lambda.java8lambdas.domain.Album;
import tw.com.oscar.v8.lambda.java8lambdas.domain.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oscarwei
 * @since 2015/1/16
 */
public abstract class MusicChapter {

    protected final List<Artist> artists;
    protected final List<Album> albums;

    public MusicChapter() {
        artists = new ArrayList<>();
        albums = new ArrayList<>();
        loadData("");
    }

    private void loadData(String file) {
    }
}
