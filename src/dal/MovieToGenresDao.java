package dal;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by hu_minghao on 3/11/17.
 */
public class MovieToGenresDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static MovieToGenresDao instance = null;
    protected MovieToGenresDao() {
        connectionManager = new ConnectionManager();
    }
    public static MovieToGenresDao getInstance() {
        if(instance == null) {
            instance = new MovieToGenresDao();
        }
        return instance;
    }

    public MovieToGenres create(MovieToGenres movieToGenre) throws SQLException {
        String insertMovieToGenre = "INSERT MovieToGenres(MovieID,Genre) VALUE (?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertMovieToGenre,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, movieToGenre.getMovie().getMovieID());
            insertStmt.setString(2, movieToGenre.getGenre().getGenreName());
            insertStmt.executeUpdate();

            results = insertStmt.getGeneratedKeys();
            int MapID = -1;
            if(results.next()) {
                MapID = results.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            movieToGenre.setMapID(MapID);
            return movieToGenre;
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

    public MovieToGenres getMovieToGenreByMapID(int mapID) throws SQLException {
        String selectMovieToGenre = "SELECT MovieID,Genre FROM MovieToGenres WHERE MapID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovieToGenre);
            selectStmt.setInt(1, mapID);
            MoviesDao moviesDao = MoviesDao.getInstance();
            GenresDao genresDao = GenresDao.getInstance();
            results = selectStmt.executeQuery();
            if(results.next()) {
                int movieID = results.getInt("MovieID");
                String genreName = results.getString("Genre");

                Movies movie = moviesDao.getMovieByMovieID(movieID);
                Genres genre = genresDao.getGenreByGenreName(genreName);
                MovieToGenres movieToGenre = new MovieToGenres(mapID,genre,movie);
                return movieToGenre;
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

    public List<MovieToGenres> getMovieToGenresByMovieID(int movieID) throws SQLException {
        List<MovieToGenres> movieToGenres = new ArrayList<>();
        String selectMovieToGenres = "SELECT MapID,Genre FROM MovieToGenres WHERE MovieID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovieToGenres);
            selectStmt.setInt(1, movieID);
            MoviesDao moviesDao = MoviesDao.getInstance();
            GenresDao genresDao = GenresDao.getInstance();
            results = selectStmt.executeQuery();
            while(results.next()) {
                int mapID = results.getInt("MapID");
                String genreName = results.getString("Genre");

                Movies movie = moviesDao.getMovieByMovieID(movieID);
                Genres genre = genresDao.getGenreByGenreName(genreName);
                MovieToGenres movieToGenre = new MovieToGenres(mapID,genre,movie);
                movieToGenres.add(movieToGenre);
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
        return movieToGenres;
    }

    public List<MovieToGenres> getMovieToGenresByGenreName(String genreName) throws SQLException {
        List<MovieToGenres> movieToGenres = new ArrayList<>();
        String selectMovieToGenres = "SELECT MapID,MovieToGenres.MovieID AS MovieID,Movies.Year AS Year " +
                "FROM MovieToGenres INNER JOIN Movies " +
                "ON MovieToGenres.MovieID = Movies.MovieID " +
                "WHERE Genre=? ORDER BY Year DESC LIMIT 100;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovieToGenres);
            selectStmt.setString(1, genreName);
            MoviesDao moviesDao = MoviesDao.getInstance();
            GenresDao genresDao = GenresDao.getInstance();
            results = selectStmt.executeQuery();
            while(results.next()) {
                int mapID = results.getInt("MapID");
                int movieID = results.getInt("MovieID");

                Movies movie = moviesDao.getMovieByMovieID(movieID);
                Genres genre = genresDao.getGenreByGenreName(genreName);
                MovieToGenres movieToGenre = new MovieToGenres(mapID,genre,movie);
                movieToGenres.add(movieToGenre);
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
        return movieToGenres;
    }

    public MovieToGenres delete(MovieToGenres movieToGenre) throws SQLException{
        String deleteMovieToGenre = "DELETE FROM MovieToGenres WHERE MapID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteMovieToGenre);
            deleteStmt.setInt(1, movieToGenre.getMapID());
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
