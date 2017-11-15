package servlet;

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
 * Created by Yafang Chen on 03/25/17
 */
@WebServlet("/findmoviesbygenre")
public class FindMoviesByGenre extends HttpServlet {
	protected MovieToGenresDao movieToGenresDao;

    @Override
    public void init() throws ServletException {
        movieToGenresDao = MovieToGenresDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String genre = req.getParameter("genre");
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<MovieToGenres> movieToGenresList;
        try {
            movieToGenresList = movieToGenresDao.getMovieToGenresByGenreName(genre);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        messages.put("success", "Displaying movies of genre " + genre);
    	messages.put("previousGenre", genre);
        req.setAttribute("movieToGenresList", movieToGenresList);
        req.getRequestDispatcher("FindMoviesByGenre.jsp").forward(req, resp);
    }
}
