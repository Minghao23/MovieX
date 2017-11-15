package dal;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hu_minghao on 3/16/17.
 */
public class DirectorsDao {
    protected ConnectionManager connectionManager;

    private static DirectorsDao instance = null;
    protected DirectorsDao() {
        connectionManager = new ConnectionManager();
    }
    public static DirectorsDao getInstance() {
        if(instance == null) {
            instance = new DirectorsDao();
        }
        return instance;
    }

    public Directors create(Directors director) throws SQLException{
        String insertDirector = "INSERT INTO Directors(Name,FaceBookLikes) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try{
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertDirector);
            insertStmt.setString(1, director.getName());
            insertStmt.setInt(2, director.getFaceBookLikes());
            insertStmt.executeUpdate();
            return director;
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

    public Directors getDirectorByName(String name) throws SQLException{
        String selectDirector =
                "SELECT Name,FaceBookLikes FROM Directors WHERE Name=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectDirector);
            selectStmt.setString(1, name);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int faceBookLikes = results.getInt("FaceBookLikes");

                Directors director = new Directors(name,faceBookLikes);
                return director;
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

    public Directors delete(Directors director) throws SQLException {
        String deleteDirector = "DELETE FROM Directors WHERE Name=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteDirector);
            deleteStmt.setString(1, director.getName());
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
