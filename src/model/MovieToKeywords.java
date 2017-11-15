package model;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class MovieToKeywords {
    protected int MapID;
    protected Movies movie;
    protected Keywords keyword;

    //constructor

    public MovieToKeywords(int mapID) {
        MapID = mapID;
    }

    public MovieToKeywords(Movies movie, Keywords keyword) {
        this.movie = movie;
        this.keyword = keyword;
    }

    public MovieToKeywords(int mapID, Movies movie, Keywords keyword) {
        MapID = mapID;
        this.movie = movie;
        this.keyword = keyword;
    }

    //getter and setter

    public int getMapID() {
        return MapID;
    }

    public void setMapID(int mapID) {
        MapID = mapID;
    }

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    public Keywords getKeyword() {
        return keyword;
    }

    public void setKeyword(Keywords keyword) {
        this.keyword = keyword;
    }
}
