package dal;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by hu_minghao on 3/11/17.
 */
public class MovieActorsDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static MovieActorsDao instance = null;
    protected MovieActorsDao() {
        connectionManager = new ConnectionManager();
    }
    public static MovieActorsDao getInstance() {
        if(instance == null) {
            instance = new MovieActorsDao();
        }
        return instance;
    }

    public MovieActors create(MovieActors movieActor) throws SQLException {
        String insertMovieActor = "INSERT MovieActors(MovieID,Actor) VALUE (?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertMovieActor,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, movieActor.getMovie().getMovieID());
            insertStmt.setString(2, movieActor.getActor().getName());
            insertStmt.executeUpdate();

            results = insertStmt.getGeneratedKeys();
            int MapID = -1;
            if(results.next()) {
                MapID = results.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            movieActor.setMapID(MapID);
            return movieActor;
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

    public MovieActors getMovieActorByMapID(int mapID) throws SQLException {
        String selectMovieActor = "SELECT MovieID,Actor FROM MovieActors WHERE MapID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovieActor);
            selectStmt.setInt(1, mapID);
            MoviesDao moviesDao = MoviesDao.getInstance();
            ActorsDao actorsDao = ActorsDao.getInstance();
            results = selectStmt.executeQuery();
            if(results.next()) {
                int movieID = results.getInt("MovieID");
                String actorName = results.getString("Actor");

                Movies movie = moviesDao.getMovieByMovieID(movieID);
                Actors actor = actorsDao.getActorByName(actorName);
                MovieActors movieActor = new MovieActors(mapID,movie,actor);
                return movieActor;
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

    public List<MovieActors> getMovieActorsByMovieID(int movieID) throws SQLException {
        List<MovieActors> movieActors = new ArrayList<>();
        String selectMovieActors = "SELECT MapID,Actor FROM MovieActors WHERE MovieID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovieActors);
            selectStmt.setInt(1, movieID);
            MoviesDao moviesDao = MoviesDao.getInstance();
            ActorsDao actorsDao = ActorsDao.getInstance();
            results = selectStmt.executeQuery();
            while(results.next()) {
                int mapID = results.getInt("MapID");
                String actorName = results.getString("Actor");

                Movies movie = moviesDao.getMovieByMovieID(movieID);
                Actors actor = actorsDao.getActorByName(actorName);
                MovieActors movieActor = new MovieActors(mapID,movie,actor);
                movieActors.add(movieActor);
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
        return movieActors;
    }

    public List<MovieActors> getMovieActorsByActorName(String actorName) throws SQLException {
        List<MovieActors> movieActors = new ArrayList<>();
        String selectMovieActors = "SELECT MapID,MovieID FROM MovieActors WHERE Actor=? " +
                "LIMIT 100;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovieActors);
            selectStmt.setString(1, actorName);
            MoviesDao moviesDao = MoviesDao.getInstance();
            ActorsDao actorsDao = ActorsDao.getInstance();
            results = selectStmt.executeQuery();
            while(results.next()) {
                int mapID = results.getInt("MapID");
                int movieID = results.getInt("MovieID");

                Movies movie = moviesDao.getMovieByMovieID(movieID);
                Actors actor = actorsDao.getActorByName(actorName);
                MovieActors movieActor = new MovieActors(mapID,movie,actor);
                movieActors.add(movieActor);
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
        return movieActors;
    }

    public MovieActors delete(MovieActors movieActor) throws SQLException{
        String deleteMovieActor = "DELETE FROM MovieActors WHERE MapID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteMovieActor);
            deleteStmt.setInt(1, movieActor.getMapID());
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
