package dal;

import model.Genres;
import model.Keywords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisun on 3/23/2017.
 */
public class GenresDao {
  private ConnectionManager connectionManager;

  private static GenresDao instance = null;
  public GenresDao() {
    connectionManager = new ConnectionManager();
  }
  public static GenresDao getInstance() {
    if(instance == null) {
      instance = new GenresDao();
    }
    return instance;
  }

  public Genres create(Genres genre) throws SQLException {
    String insertGenre = "INSERT INTO Genres(Genre) VALUES(?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try{
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertGenre);
      insertStmt.setString(1, genre.getGenreName());
      insertStmt.executeUpdate();
      return genre;
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

  public Genres getGenreByGenreName(String genreName) throws SQLException{
    String selectGenre =
        "SELECT Genre FROM Genres WHERE Genre=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectGenre);
      selectStmt.setString(1, genreName);
      results = selectStmt.executeQuery();
      if(results.next()) {
        return new Genres(genreName);
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

  public List<Genres> getAllGenres() throws SQLException{
    List<Genres> genres = new ArrayList();
    String selectGenre =
            "SELECT Genre FROM Genres;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectGenre);
      results = selectStmt.executeQuery();
      while(results.next()) {
        String genreName = results.getString("Genre");
        genres.add(new Genres(genreName));
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
    return genres;
  }

  public Genres delete(Genres genre) throws SQLException {
    String deleteGenre = "DELETE FROM Genres WHERE Genre=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteGenre);
      deleteStmt.setString(1, genre.getGenreName());
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
