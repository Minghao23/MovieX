package model;

import java.util.Date;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class Reviews {
    protected int ReviewID;
    protected String Content;
    protected Date Created;
    protected double Rating;
    protected Movies movie;
    protected Users user;

    //constructor

    public Reviews(int reviewID) {
        ReviewID = reviewID;
    }

    public Reviews(String content, Date created, double rating, Movies movie, Users user) {
        Content = content;
        Created = created;
        Rating = rating;
        this.movie = movie;
        this.user = user;
    }

    public Reviews(int reviewID, String content, Date created, double rating, Movies movie, Users user) {
        ReviewID = reviewID;
        Content = content;
        Created = created;
        Rating = rating;
        this.movie = movie;
        this.user = user;
    }

    //getter and setter

    public int getReviewID() {
        return ReviewID;
    }

    public void setReviewID(int reviewID) {
        ReviewID = reviewID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getCreated() {
        return Created;
    }

    public void setCreated(Date created) {
        Created = created;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
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
