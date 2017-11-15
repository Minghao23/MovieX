package dal;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by hu_minghao on 3/11/17.
 */
public class MoviesDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static MoviesDao instance = null;
    protected MoviesDao() {
        connectionManager = new ConnectionManager();
    }
    public static MoviesDao getInstance() {
        if(instance == null) {
            instance = new MoviesDao();
        }
        return instance;
    }

    public Movies create(Movies movie) throws SQLException {
        String insertMovie = "INSERT Movies(MovieTitle,Year,Country,Language,Duration,Gross,Budget," +
                "IMDbLink,ContentRating,IMDbScore,Director,ImgURL) VALUE (?,?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertMovie,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, movie.getMovieTitle());
            insertStmt.setInt(2, movie.getYear());
            insertStmt.setString(3, movie.getCountry());
            insertStmt.setString(4, movie.getLanguage());
            insertStmt.setInt(5, movie.getDuration());
            insertStmt.setLong(6, movie.getGross());
            insertStmt.setLong(7, movie.getBudget());
            insertStmt.setString(8, movie.getIMDbLink());
            insertStmt.setString(9, movie.getContentRating());
            insertStmt.setDouble(10, movie.getIMDbScore());
            insertStmt.setString(11, movie.getDirector().getName());
            insertStmt.setString(12, movie.getImgURL());
            insertStmt.executeUpdate();

            results = insertStmt.getGeneratedKeys();
            int movieID = -1;
            if(results.next()) {
                movieID = results.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            movie.setMovieID(movieID);
            return movie;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
    }

    public Movies getMovieByMovieID(int movieID) throws SQLException {
        String selectMovie = "SELECT MovieTitle,Year,Country,Language,Duration,Gross,Budget," +
                "IMDbLink,ContentRating,IMDbScore,Director,ImgURL FROM Movies WHERE MovieID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovie);
            selectStmt.setInt(1, movieID);
            results = selectStmt.executeQuery();
            DirectorsDao directorsDao = DirectorsDao.getInstance();
            if(results.next()) {
                String movieTitle = results.getString("MovieTitle");
                int year = results.getInt("Year");
                String country = results.getString("Country");
                String language = results.getString("Language");
                int duration = results.getInt("Duration");
                long gross = results.getLong("Gross");
                long budget = results.getLong("Budget");
                String IMDbLink = results.getString("IMDbLink");
                String contentRating = results.getString("ContentRating");
                double IMDbScore = results.getDouble("IMDbScore");
                String directorName = results.getString("Director");
                String imgURL = results.getString("ImgURL");

                Directors director = directorsDao.getDirectorByName(directorName);
                Movies movie = new Movies(movieID,movieTitle,year,country,language,duration,gross,budget,IMDbLink,contentRating,IMDbScore,director,imgURL);
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return null;
    }

    public List<Movies> getMoviesByDirector(String directorName) throws SQLException {
        List<Movies> movies= new ArrayList();
        String selectMovies = "SELECT MovieID,MovieTitle,Year,Country,Language,Duration,Gross,Budget," +
                "IMDbLink,ContentRating,IMDbScore,Director,ImgURL " +
                "FROM Movies WHERE Director=? ORDER BY Year DESC LIMIT 100;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovies);
            selectStmt.setString(1, directorName);
            results = selectStmt.executeQuery();
            DirectorsDao directorsDao = DirectorsDao.getInstance();
            while(results.next()) {
                int movieID = results.getInt("MovieID");
                String movieTitle = results.getString("MovieTitle");
                int year = results.getInt("Year");
                String country = results.getString("Country");
                String language = results.getString("Language");
                int duration = results.getInt("Duration");
                long gross = results.getLong("Gross");
                long budget = results.getLong("Budget");
                String IMDbLink = results.getString("IMDbLink");
                String contentRating = results.getString("ContentRating");
                double IMDbScore = results.getDouble("IMDbScore");
                String imgURL = results.getString("ImgURL");

                Directors director = directorsDao.getDirectorByName(directorName);
                Movies movie = new Movies(movieID,movieTitle,year,country,language,duration,gross,budget,IMDbLink,contentRating,IMDbScore,director,imgURL);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return movies;
    }

    public List<Movies> searchMovies(String str) throws SQLException {
        List<Movies> movies= new ArrayList();
        String selectMovies = "SELECT MovieID,MovieTitle,Year,Country,Language,Duration,Gross,Budget, " +
                                "IMDbLink,ContentRating,IMDbScore,Director,ImgURL " +
                                "FROM Movies ";
        String likeSearch = "WHERE MovieTitle like ?"
            + " ORDER BY CASE WHEN MovieTitle like ? THEN 0"
            + " WHEN MovieTitle like ? THEN 1"
            + " WHEN MovieTitle like ? THEN 2"
            + " ELSE 3"
            + " END, MovieTitle"
            + " LIMIT 100;";
        String exactSearch = "WHERE MovieTitle=?";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();

            if(isLikeSearch(str)){
                selectStmt = connection.prepareStatement(
                    (selectMovies+likeSearch));
                selectStmt.setString(1,"%" + str + "%");
                selectStmt.setString(2,str);
                selectStmt.setString(3,str+"%");
                selectStmt.setString(4,"%" + str);
                //selectStmt.setString(1, "%" + str + "%");// add single quotes automatically
            }else{
                selectStmt = connection.prepareStatement(selectMovies+exactSearch);
                selectStmt.setString(1, str.substring(1,str.length()-1));
            }
            results = selectStmt.executeQuery();
            DirectorsDao directorsDao = DirectorsDao.getInstance();
            while(results.next()) {
                int movieID = results.getInt("MovieID");
                String movieTitle = results.getString("MovieTitle");
                int year = results.getInt("Year");
                String country = results.getString("Country");
                String language = results.getString("Language");
                int duration = results.getInt("Duration");
                long gross = results.getLong("Gross");
                long budget = results.getLong("Budget");
                String IMDbLink = results.getString("IMDbLink");
                String contentRating = results.getString("ContentRating");
                double IMDbScore = results.getDouble("IMDbScore");
                String directorName = results.getString("Director");
                String imgURL = results.getString("ImgURL");

                Directors director = directorsDao.getDirectorByName(directorName);
                Movies movie = new Movies(movieID,movieTitle,year,country,language,duration,gross,budget,IMDbLink,contentRating,IMDbScore,director,imgURL);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return movies;
    }

    private boolean isLikeSearch(String query){
        Pattern pattern = Pattern.compile("\"[^\"]*\"");
        Matcher matcher = pattern.matcher(query);
        return !matcher.find();
    }

    public List<Movies> searchTop100() throws SQLException {
        List<Movies> movies= new ArrayList();
        String selectMovies = "SELECT MovieID,MovieTitle,Year,Country,Language,Duration,Gross,Budget, " +
                "IMDbLink,ContentRating,IMDbScore,Director,ImgURL " +
                "FROM Movies WHERE NOT Gross=0 AND NOT Year=0 ORDER BY IMDbScore DESC LIMIT 100;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovies);
            results = selectStmt.executeQuery();
            DirectorsDao directorsDao = DirectorsDao.getInstance();
            while(results.next()) {
                int movieID = results.getInt("MovieID");
                String movieTitle = results.getString("MovieTitle");
                int year = results.getInt("Year");
                String country = results.getString("Country");
                String language = results.getString("Language");
                int duration = results.getInt("Duration");
                long gross = results.getLong("Gross");
                long budget = results.getLong("Budget");
                String IMDbLink = results.getString("IMDbLink");
                String contentRating = results.getString("ContentRating");
                double IMDbScore = results.getDouble("IMDbScore");
                String directorName = results.getString("Director");
                String imgURL = results.getString("ImgURL");

                Directors director = directorsDao.getDirectorByName(directorName);
                Movies movie = new Movies(movieID,movieTitle,year,country,language,duration,gross,budget,IMDbLink,contentRating,IMDbScore,director,imgURL);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return movies;
    }

    public Movies delete(Movies movie) throws SQLException{
        String deleteMovie = "DELETE FROM Movies WHERE MovieID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteMovie);
            deleteStmt.setInt(1, movie.getMovieID());
            deleteStmt.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
}
