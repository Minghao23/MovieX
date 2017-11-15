package dal;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hu_minghao on 3/16/17.
 */
public class UsersDao {
    protected ConnectionManager connectionManager;

    private static UsersDao instance = null;
    protected UsersDao() {
        connectionManager = new ConnectionManager();
    }
    public static UsersDao getInstance() {
        if(instance == null) {
            instance = new UsersDao();
        }
        return instance;
    }

    public Users create(Users user) throws SQLException{
        String insertUser = "INSERT INTO Users(UserName,FirstName,LastName,Email,Password) VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try{
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertUser);
            insertStmt.setString(1, user.getUserName());
            insertStmt.setString(2, user.getFirstName());
            insertStmt.setString(3, user.getLastName());
            insertStmt.setString(4, user.getEmail());
            insertStmt.setString(5, user.getPassword());
            insertStmt.executeUpdate();
            return user;
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

    public Users update(Users user) throws SQLException{
        String updateUser = "UPDATE Users SET FirstName=?,LastName=?,Email=?,Password=? WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try{
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateUser);
            updateStmt.setString(1, user.getFirstName());
            updateStmt.setString(2, user.getLastName());
            updateStmt.setString(3, user.getEmail());
            updateStmt.setString(4, user.getPassword());
            updateStmt.setString(5, user.getUserName());
            updateStmt.executeUpdate();
            return user;
        }catch (SQLException e){
            e.printStackTrace();
            throw e;
        }finally {
            if(connection!=null){
                connection.close();
            }
            if(updateStmt!=null){
                updateStmt.close();
            }
        }
    }

    public Users getUserByUserName(String userName) throws SQLException{
        String selectUser =
                "SELECT FirstName,LastName,Email,Password FROM Users WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUser);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
            if(results.next()) {
                String firstName= results.getString("FirstName");
                String lastName= results.getString("LastName");
                String email= results.getString("Email");
                String password= results.getString("Password");

                Users user = new Users(userName,firstName,lastName,email,password);
                return user;
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

    public Users delete(Users user) throws SQLException {
        String deleteUser = "DELETE FROM Users WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteUser);
            deleteStmt.setString(1, user.getUserName());
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
