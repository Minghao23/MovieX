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
import java.util.List;

/**
 * Created by Lenovo on 2016/2/24.
 */
@WebServlet("/addFavorite")
public class addFavorite extends HttpServlet {

    protected MoviesDao moviesDao;
    protected UsersDao usersDao;
    protected FavoritesDao favoritesDao;

    @Override
    public void init() throws ServletException {
        moviesDao = MoviesDao.getInstance();
        usersDao = UsersDao.getInstance();
        favoritesDao = FavoritesDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int movieID = Integer.parseInt(req.getParameter("inputmovieid"));
        String userName = req.getParameter("inputusername");
        String flag = req.getParameter("inputflag");
        try {
            Movies movie = moviesDao.getMovieByMovieID(movieID);
            Users user = usersDao.getUserByUserName(userName);
            if(flag.equals("add to")){
                favoritesDao.create(new Favorites(movie,user));
            }else{
                Favorites tempFavorite = favoritesDao.getFavoriteByMovieIDandUserName(movieID,userName);
                favoritesDao.delete(tempFavorite);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.getRequestDispatcher("details.jsp?MovieID=" + movieID + "&UserName=" + userName).forward(req, resp);
    }
}

