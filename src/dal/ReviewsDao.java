package dal;

import model.*;

import java.sql.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ReviewsDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static ReviewsDao instance = null;
	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewsDao getInstance() {
		if(instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}
	
	public Reviews create(Reviews review) throws SQLException {
		String insertReview = "INSERT INTO Reviews(MovieID,UserName,Rating,Content,Created) VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, review.getMovie().getMovieID());
			insertStmt.setString(2, review.getUser().getUserName());
			insertStmt.setDouble(3, review.getRating());
			insertStmt.setString(4, review.getContent());
			insertStmt.setTimestamp(5, new Timestamp(review.getCreated().getTime()));
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int reviewID = -1;
			if(resultKey.next()) {
				reviewID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewID(reviewID);
			return review;
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
	
	public Reviews updateContent(Reviews review, String newContent) throws SQLException {
		String updateReview = "UPDATE Reviews SET Content=?,Created=? WHERE ReviewID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReview);
			updateStmt.setString(1, newContent);
			Date newCreatedTimestamp = new Date();
			updateStmt.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
			updateStmt.setInt(3, review.getReviewID());
			updateStmt.executeUpdate();

			review.setContent(newContent);
			review.setCreated(newCreatedTimestamp);
			return review;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	
	public Reviews delete(Reviews review) throws SQLException {
		String deleteReview = "DELETE FROM Reviews WHERE ReviewID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewID());
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
	
	
	public Reviews getReviewByID(int reviewID) throws SQLException {
		String selectReview =
			"SELECT ReviewID,MovieID,UserName,Rating,Content,Created " +
			"FROM Reviews " +
			"WHERE ReviewID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewID);
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultReviewID = results.getInt("ReviewID");
				int movieID = results.getInt("MovieID");
				String userName = results.getString("UserName");
				double rating = results.getDouble("Rating");
				String content = results.getString("Content");
				Date created =  new Date(results.getTimestamp("Created").getTime());
			
				Movies movie = moviesDao.getMovieByMovieID(movieID);
				Users user = usersDao.getUserByUserName(userName);
				Reviews review = new Reviews(resultReviewID, content, created, rating, 
					 movie, user);
				return review;
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
	
	
	public List<Reviews> getReviewsByUser(String userName) throws SQLException {
		List<Reviews> reviews = new ArrayList<Reviews>();
		String selectReviews =
			"SELECT ReviewID,MovieID,UserName,Rating,Content,Created " +
			"FROM Reviews " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int reviewID = results.getInt("ReviewID");
				int movieID = results.getInt("MovieID");
				String resultUserName = results.getString("UserName");
				double rating = results.getDouble("Rating");
				String content = results.getString("Content");
				Date created =  new Date(results.getTimestamp("Created").getTime());

				Movies movie = moviesDao.getMovieByMovieID(movieID);
				Users resultUser = usersDao.getUserByUserName(resultUserName);
				Reviews review = new Reviews(reviewID, content, created, rating, movie, resultUser);
				
				reviews.add(review);
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
		return reviews;
	}
	
	public List<Reviews> getReviewsByMovie(int movieID) throws SQLException {
		List<Reviews> reviews = new ArrayList<Reviews>();
		String selectReviews =
			"SELECT ReviewID,MovieID,UserName,Rating,Content,Created " +
			"FROM Reviews " +
			"WHERE MovieID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setInt(1, movieID);
			results = selectStmt.executeQuery();
			MoviesDao moviesDao = MoviesDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int reviewID = results.getInt("ReviewID");
				int resultMovieID = results.getInt("MovieID");
				String userName = results.getString("UserName");
				double rating = results.getDouble("Rating");
				String content = results.getString("Content");
				Date created =  new Date(results.getTimestamp("Created").getTime());

				Movies resultMovie = moviesDao.getMovieByMovieID(resultMovieID);
				Users user = usersDao.getUserByUserName(userName);
				Reviews review = new Reviews(reviewID, content, created, rating, resultMovie, user);
				
				reviews.add(review);
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
		return reviews;
	}
}
