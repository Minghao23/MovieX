package model;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class MovieToGenres {
    protected int MapID;
    protected Genres genre;
    protected Movies movie;

    //constructor

    public MovieToGenres(int mapID) {
        MapID = mapID;
    }

    public MovieToGenres(Genres genre, Movies movie) {
        this.genre = genre;
        this.movie = movie;
    }

    public MovieToGenres(int mapID, Genres genre, Movies movie) {
        MapID = mapID;
        this.genre = genre;
        this.movie = movie;
    }

    //getter and setter

    public int getMapID() {
        return MapID;
    }

    public void setMapID(int mapID) {
        MapID = mapID;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }
}
