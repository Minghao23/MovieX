package servlet; /**
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
import java.util.*;

/**
 * Created by Lenovo on 2016/2/24.
 */
@WebServlet("/top100")
public class Top100 extends HttpServlet {

    protected MoviesDao moviesDao;

    @Override
    public void init() throws ServletException {
        moviesDao = MoviesDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userName = req.getParameter("inputusername");
        List<Movies> movieList;
        try {
            movieList = moviesDao.searchTop100();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("movieList", movieList);
        req.getRequestDispatcher("searchResults.jsp?UserName=" + userName).forward(req, resp);
    }
}
