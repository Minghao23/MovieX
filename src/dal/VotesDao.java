package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class VotesDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static VotesDao instance = null;
	protected VotesDao() {
		connectionManager = new ConnectionManager();
	}
	public static VotesDao getInstance() {
		if(instance == null) {
			instance = new VotesDao();
		}
		return instance;
	}
	
	public Votes create(Votes vote) throws SQLException {
		String insertVote = "INSERT INTO Votes(ReviewID,UserName) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertVote);
			
			insertStmt.setInt(1, vote.getReview().getReviewID());
			insertStmt.setString(2, vote.getUser().getUserName());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int voteID = -1;
			if(resultKey.next()) {
				voteID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			vote.setVoteID(voteID);
			return vote;
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
	
	public Votes getVoteByID(int voteID) throws SQLException {
		String selectVote =
			"SELECT VoteID,ReviewID,UserName " +
			"FROM Votes " +
			"WHERE VoteID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectVote);
			selectStmt.setInt(1, voteID);
			results = selectStmt.executeQuery();
			ReviewsDao reviewsDao = ReviewsDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultVoteID = results.getInt("VoteID");
				int reviewID = results.getInt("ReviewID");
				String userName = results.getString("UserName");
				
				Reviews review = reviewsDao.getReviewByID(reviewID);
				Users user = usersDao.getUserByUserName(userName);
				Votes vote = new Votes(resultVoteID, review, user);
				return vote;
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
	
	public List<Votes> getVotesByUserName(String userName) throws SQLException {
		List<Votes> votes = new ArrayList<Votes>();
		String selectVotes =
			"SELECT VoteID,ReviewID,UserName " +
			"FROM Votes " + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectVotes);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ReviewsDao reviewsDao = ReviewsDao.getInstance();
			while(results.next()) {
				int voteID = results.getInt("VoteID");
				int reviewID = results.getInt("ReviewID");
				String resultUserName = results.getString("UserName");
				
				Reviews review = reviewsDao.getReviewByID(reviewID);
				Users resultUser = usersDao.getUserByUserName(resultUserName);
				Votes vote = new Votes(voteID, review, resultUser);
				votes.add(vote);
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
		return votes;
	}
	
	public List<Votes> getVotesByReview(int reviewID) throws SQLException {
		List<Votes> votes = new ArrayList<Votes>();
		String selectVotes =
			"SELECT VoteID,ReviewID,UserName " +
			"FROM Votes " + 
			"WHERE ReviewID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectVotes);
			selectStmt.setInt(1, reviewID);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ReviewsDao reviewsDao = ReviewsDao.getInstance();
			while(results.next()) {
				int voteID = results.getInt("VoteID");
				int resultReviewID = results.getInt("ReviewID");
				String userName = results.getString("UserName");
				
				Reviews resultReview = reviewsDao.getReviewByID(resultReviewID);
				Users user = usersDao.getUserByUserName(userName);
				Votes vote = new Votes(voteID, resultReview, user);
				votes.add(vote);
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
		return votes;
	}
	
	public Votes delete(Votes vote) throws SQLException {
		String deleteVote = "DELETE FROM Votes WHERE VoteID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteVote);
			deleteStmt.setInt(1, vote.getVoteID());
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
