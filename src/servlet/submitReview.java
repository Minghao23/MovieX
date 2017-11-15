package servlet;
/**
 * Created by hu_minghao on 2/28/17.
 */

import dal.*;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet("/submitReview")
public class submitReview extends HttpServlet {
    protected ReviewsDao reviewsDao;
    protected MoviesDao moviesDao;
    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        reviewsDao = ReviewsDao.getInstance();
        moviesDao = MoviesDao.getInstance();
        usersDao = UsersDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userName = req.getParameter("inputusername");
        String movieID = req.getParameter("inputmovieid");
        String rating = req.getParameter("inputrating");
        String content = req.getParameter("inputcontent");

        try {
            Movies movie = moviesDao.getMovieByMovieID(Integer.parseInt(movieID));
            Users user = usersDao.getUserByUserName(userName);
            Reviews review = new Reviews(content, new Date(), Double.parseDouble(rating), movie, user);
            reviewsDao.create(review);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.getRequestDispatcher("details.jsp?MovieID=" + movieID + "&UserName=" + userName).forward(req, resp);
    }
}

