package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class FavoritesDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static FavoritesDao instance = null;
	protected FavoritesDao() {
		connectionManager = new ConnectionManager();
	}
	public static FavoritesDao getInstance() {
		if(instance == null) {
			instance = new FavoritesDao();
		}
		return instance;
	}
	
	public Favorites create(Favorites favorite) throws SQLException {
		String insertFavorite = "INSERT INTO Favorites(MovieID,UserName) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFavorite,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, favorite.getMovie().getMovieID());
			insertStmt.setString(2, favorite.getUser().getUserName());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int favoriteID = -1;
			if(resultKey.next()) {
				favoriteID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			favorite.setFavoriteID(favoriteID);
			return favorite;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public Favorites getFavoriteByID(int favoriteID) throws SQLException {
		String selectFavorite =
			"SELECT FavoriteID,MovieID,UserName " +
			"FROM Favorites " +
			"WHERE FavoriteID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavorite);
			selectStmt.setInt(1, favoriteID);
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultFavoriteID = results.getInt("FavoriteID");
				int movieID = results.getInt("MovieID");
				String userName = results.getString("UserName");
				
				Movies movie = moviesDao.getMovieByMovieID(movieID);
				Users user = usersDao.getUserByUserName(userName);
				Favorites favorite = new Favorites(resultFavoriteID, movie, user);
				return favorite;
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
	
	public Favorites delete(Favorites favorite) throws SQLException {
		String deleteFavorite = "DELETE FROM Favorites WHERE FavoriteID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFavorite);
			deleteStmt.setInt(1, favorite.getFavoriteID());
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
	
	public List<Favorites> getFavoritesByMovie(int movieID) throws SQLException {
		List<Favorites> favorites = new ArrayList<Favorites>();
		String selectFavorites =
			"SELECT FavoriteID,MovieID,UserName " +
			"FROM Favorites " +
			"WHERE MovieID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavorites);
			selectStmt.setInt(1, movieID);
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int favoriteID = results.getInt("FavoriteID");
				int resultMovieID = results.getInt("MovieID");
				String userName = results.getString("UserName");
				
				Movies movie = moviesDao.getMovieByMovieID(resultMovieID);
				Users user = usersDao.getUserByUserName(userName);
				Favorites favorite = new Favorites(favoriteID, movie, user);
				
				favorites.add(favorite);
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
		return favorites;
	}
	
	public List<Favorites> getFavoritesByUser(String userName) throws SQLException {
		List<Favorites> favorites = new ArrayList<Favorites>();
		String selectFavorites =
			"SELECT FavoriteID,MovieID,UserName " +
			"FROM Favorites " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavorites);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int favoriteID = results.getInt("FavoriteID");
				int movieID = results.getInt("MovieID");
				String resultUserName = results.getString("UserName");
				
				Movies movie = moviesDao.getMovieByMovieID(movieID);
				Users resultUser = usersDao.getUserByUserName(resultUserName);
				Favorites favorite = new Favorites(favoriteID, movie, resultUser);
				
				favorites.add(favorite);
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
		return favorites;
	}

	public Favorites getFavoriteByMovieIDandUserName(int movieID, String userName) throws SQLException {
		String selectFavorite =
				"SELECT FavoriteID " +
						"FROM Favorites " +
						"WHERE MovieID=? AND UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavorite);
			selectStmt.setInt(1, movieID);
			selectStmt.setString(2, userName);
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int favoriteID = results.getInt("FavoriteID");

				Movies movie = moviesDao.getMovieByMovieID(movieID);
				Users user = usersDao.getUserByUserName(userName);
				Favorites favorite = new Favorites(favoriteID, movie, user);
				return favorite;
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
}
