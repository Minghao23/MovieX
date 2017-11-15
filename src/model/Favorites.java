package model;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class Favorites {
    protected int FavoriteID;
    protected Movies movie;
    protected Users user;

    public Favorites(int favoriteID) {
        FavoriteID = favoriteID;
    }

    //constructor

    public Favorites(Movies movie, Users user) {
        this.movie = movie;
        this.user = user;
    }

    public Favorites(int favoriteID, Movies movie, Users user) {
        FavoriteID = favoriteID;
        this.movie = movie;
        this.user = user;
    }

    //getter and setter

    public int getFavoriteID() {
        return FavoriteID;
    }

    public void setFavoriteID(int favoriteID) {
        FavoriteID = favoriteID;
    }

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
