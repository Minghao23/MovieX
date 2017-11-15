package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Keywords;

/**
 * Created by lisun on 3/23/2017.
 */
public class KeywordsDao {
  private ConnectionManager connectionManager;

  private static KeywordsDao instance = null;
  public KeywordsDao() {
    connectionManager = new ConnectionManager();
  }
  public static KeywordsDao getInstance() {
    if(instance == null) {
      instance = new KeywordsDao();
    }
    return instance;
  }

  public Keywords create(Keywords keyword) throws SQLException {
    String insertKeyword = "INSERT INTO Keywords(Keyword) VALUES(?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try{
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertKeyword);
      insertStmt.setString(1, keyword.getKeywordName());
      insertStmt.executeUpdate();
      return keyword;
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

  public Keywords getKeywordByKeyword(String keyword) throws SQLException{
    String selectKeyword =
        "SELECT Keyword FROM Keywords WHERE Keyword=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectKeyword);
      selectStmt.setString(1, keyword);
      results = selectStmt.executeQuery();
      if(results.next()) {
        //String keyStr = results.getString("Keyword");
        return new Keywords(keyword);
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

  public Keywords delete(Keywords keyword) throws SQLException {
    String deleteKeyword = "DELETE FROM Keywords WHERE keyword=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteKeyword);
      deleteStmt.setString(1, keyword.getKeywordName());
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
