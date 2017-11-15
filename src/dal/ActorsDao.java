package dal;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hu_minghao on 3/16/17.
 */
public class ActorsDao {
    protected ConnectionManager connectionManager;

    private static ActorsDao instance = null;
    protected ActorsDao() {
        connectionManager = new ConnectionManager();
    }
    public static ActorsDao getInstance() {
        if(instance == null) {
            instance = new ActorsDao();
        }
        return instance;
    }

    public Actors create(Actors actor) throws SQLException{
        String insertActor = "INSERT INTO Actors(Name,FaceBookLikes) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try{
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertActor);
            insertStmt.setString(1, actor.getName());
            insertStmt.setInt(2, actor.getFaceBookLikes());
            insertStmt.executeUpdate();
            return actor;
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

    public Actors getActorByName(String name) throws SQLException{
        String selectActor =
                "SELECT Name,FaceBookLikes FROM Actors WHERE Name=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectActor);
            selectStmt.setString(1, name);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int faceBookLikes = results.getInt("FaceBookLikes");

                Actors actor = new Actors(name,faceBookLikes);
                return actor;
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

    public Actors delete(Actors actor) throws SQLException {
        String deleteActor = "DELETE FROM Actors WHERE Name=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteActor);
            deleteStmt.setString(1, actor.getName());
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
