package model;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class Votes {
    protected int VoteID;
    protected Reviews review;
    protected Users user;

    //constructor

    public Votes(int voteID) {
        VoteID = voteID;
    }

    public Votes(Reviews review, Users user) {
        this.review = review;
        this.user = user;
    }

    public Votes(int voteID, Reviews review, Users user) {
        VoteID = voteID;
        this.review = review;
        this.user = user;
    }

    //getter and setter

    public int getVoteID() {
        return VoteID;
    }

    public void setVoteID(int voteID) {
        VoteID = voteID;
    }

    public Reviews getReview() {
        return review;
    }

    public void setReview(Reviews review) {
        this.review = review;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
