package model;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class Genres {
    protected String GenreName;

    //constructor

    public Genres(String genreName) {
        GenreName = genreName;
    }

    public String getGenreName() {
        return GenreName;
    }

    //getter and setter

    public void setGenreName(String genreName) {
        GenreName = genreName;
    }
}
