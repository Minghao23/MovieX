package model;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class MovieActors {
    protected int MapID;
    protected Movies movie;
    protected Actors actor;

    //constructor

    public MovieActors(int mapID) {
        MapID = mapID;
    }

    public MovieActors(Movies movie, Actors actor) {
        this.movie = movie;
        this.actor = actor;
    }

    public MovieActors(int mapID, Movies movie, Actors actor) {
        MapID = mapID;
        this.movie = movie;
        this.actor = actor;
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

    public Actors getActor() {
        return actor;
    }

    public void setActor(Actors actor) {
        this.actor = actor;
    }
}
