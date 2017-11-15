package model;


/**
 * Created by hu_minghao on 3/2/17.
 */
public class Movies {
    protected int MovieID;
    protected String MovieTitle;
    protected int Year;
    protected String Country;
    protected String Language;
    protected int Duration;
    protected long Gross;
    protected long Budget;
    protected String IMDbLink;
    protected String ContentRating;
    protected double IMDbScore;
    protected Directors Director;
    protected String ImgURL;

    //constructor

    public Movies(int movieID) {
        MovieID = movieID;
    }

    public Movies(String movieTitle, int year, String country, String language, int duration, long gross, long budget, String IMDbLink, String contentRating, double IMDbScore, Directors director, String imgURL) {
        MovieTitle = movieTitle;
        Year = year;
        Country = country;
        Language = language;
        Duration = duration;
        Gross = gross;
        Budget = budget;
        this.IMDbLink = IMDbLink;
        ContentRating = contentRating;
        this.IMDbScore = IMDbScore;
        Director = director;
        ImgURL = imgURL;
    }

    public Movies(int movieID, String movieTitle, int year, String country, String language, int duration, long gross, long budget, String IMDbLink, String contentRating, double IMDbScore, Directors director, String imgURL) {
        MovieID = movieID;
        MovieTitle = movieTitle;
        Year = year;
        Country = country;
        Language = language;
        Duration = duration;
        Gross = gross;
        Budget = budget;
        this.IMDbLink = IMDbLink;
        ContentRating = contentRating;
        this.IMDbScore = IMDbScore;
        Director = director;
        ImgURL = imgURL;
    }

    //getter and setter

    public int getMovieID() {
        return MovieID;
    }

    public void setMovieID(int movieID) {
        MovieID = movieID;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        MovieTitle = movieTitle;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public long getGross() {
        return Gross;
    }

    public void setGross(long gross) {
        Gross = gross;
    }

    public long getBudget() {
        return Budget;
    }

    public void setBudget(long budget) {
        Budget = budget;
    }

    public String getIMDbLink() {
        return IMDbLink;
    }

    public void setIMDbLink(String IMDbLink) {
        this.IMDbLink = IMDbLink;
    }

    public String getContentRating() {
        return ContentRating;
    }

    public void setContentRating(String contentRating) {
        ContentRating = contentRating;
    }

    public double getIMDbScore() {
        return IMDbScore;
    }

    public void setIMDbScore(double IMDbScore) {
        this.IMDbScore = IMDbScore;
    }

    public Directors getDirector() {
        return Director;
    }

    public void setDirector(Directors director) {
        Director = director;
    }

    public String getImgURL() {
        return ImgURL;
    }

    public void setImgURL(String imgURL) {
        ImgURL = imgURL;
    }
}
