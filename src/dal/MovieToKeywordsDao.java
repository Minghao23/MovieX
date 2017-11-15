package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Keywords;
import model.MovieToKeywords;
import model.Movies;

/**
 * Created by lisun on 3/23/2017.
 */
public class MovieToKeywordsDao {
  private ConnectionManager connectionManager;

  private static MovieToKeywordsDao instance = null;
  public MovieToKeywordsDao() {
    connectionManager = new ConnectionManager();
  }
  public static MovieToKeywordsDao getInstance() {
    if(instance == null) {
      instance = new MovieToKeywordsDao();
    }
    return instance;
  }

  public MovieToKeywords create(MovieToKeywords MovieToKeyword) throws SQLException {
    String insertMovieToKeywords = "INSERT INTO MovieToKeywords(MovieID,Keyword) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet results = null;
    try{
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertMovieToKeywords,
              Statement.RETURN_GENERATED_KEYS);
      insertStmt.setInt(1, MovieToKeyword.getMovie().getMovieID());
      insertStmt.setString(2, MovieToKeyword.getKeyword().getKeywordName());
      insertStmt.executeUpdate();

      results = insertStmt.getGeneratedKeys();
      int MapID = -1;
      if(results.next()) {
        MapID = results.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      MovieToKeyword.setMapID(MapID);
      return MovieToKeyword;
    }catch (SQLException e){
      e.printStackTrace();
      throw e;
    }finally {
      if(connection!=null){
        connection.close();
      }
      if(insertStmt!=null){
        insertStmt.close();
      }
    }
  }

  public MovieToKeywords getMovieToKeywordsById(int id) throws SQLException{
    String selectMovieToKeyword =
        "SELECT MovieID,Keyword FROM MovieToKeywords WHERE MapID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMovieToKeyword);
      selectStmt.setInt(1, id);
      results = selectStmt.executeQuery();
      if(results.next()) {
        int movieId = results.getInt("MovieID");
        Movies movie = MoviesDao.getInstance().getMovieByMovieID(movieId);
        String keyword = results.getString("Keyword");
        Keywords keywords = KeywordsDao.getInstance().getKeywordByKeyword(keyword);
        return new MovieToKeywords(id,movie,keywords);
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

  public List<MovieToKeywords> getMovieToKeywordsByMovieID(int id) throws SQLException{
    List<MovieToKeywords> movieKeywords = new ArrayList<>();
    String selectMovieToKeywords = "SELECT MapID,Keyword FROM MovieToKeywords WHERE MovieID=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMovieToKeywords);
      selectStmt.setInt(1, id);
      results = selectStmt.executeQuery();
      while(results.next()) {
        Movies movie = MoviesDao.getInstance().getMovieByMovieID(id);
        String keyword = results.getString("Keyword");
        Keywords keywords = KeywordsDao.getInstance().getKeywordByKeyword(keyword);
        int MapId = results.getInt("MapID");
        movieKeywords.add(new MovieToKeywords(MapId,movie,keywords));
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
    return movieKeywords;
  }

  public List<MovieToKeywords> getMovieToKeywordsByKeyword(String keyword) throws SQLException{
    List<MovieToKeywords> movieKeywords = new ArrayList<>();
    String selectMovieToKeywords = "SELECT MapID,MovieID FROM MovieToKeywords WHERE Keyword=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMovieToKeywords);
      selectStmt.setString(1, keyword);
      results = selectStmt.executeQuery();
      while(results.next()) {
        int movieId = results.getInt("MovieID");
        Movies movie = MoviesDao.getInstance().getMovieByMovieID(movieId);
        Keywords keywords = KeywordsDao.getInstance().getKeywordByKeyword(keyword);
        int MapId = results.getInt("MapID");
        movieKeywords.add(new MovieToKeywords(MapId,movie,keywords));
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
    return movieKeywords;
  }

  public MovieToKeywords delete(MovieToKeywords movieToKeywords) throws SQLException {
    String deleteMovieToKeywords = "DELETE FROM MovieToKeywords WHERE MapID=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteMovieToKeywords);
      deleteStmt.setInt(1, movieToKeywords.getMapID());
      deleteStmt.executeUpdate();
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }
}
