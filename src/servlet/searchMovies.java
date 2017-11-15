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
@WebServlet("/search")
public class searchMovies extends HttpServlet {

    protected MoviesDao moviesDao;
    protected MovieActorsDao movieActorsDao;
    protected DirectorsDao directorsDao;
    protected MovieToGenresDao movieToGenresDao;

    @Override
    public void init() throws ServletException {
        moviesDao = MoviesDao.getInstance();
        movieActorsDao = MovieActorsDao.getInstance();
        directorsDao = DirectorsDao.getInstance();
        movieToGenresDao = MovieToGenresDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("inputname");
        int searchType = Integer.parseInt(req.getParameter("inputsearchtype"));
        String genre = req.getParameter("inputgenre");
        String userName = req.getParameter("inputusername");
        List<Movies> movieList;
        List<MovieActors> movieActorList;
        List<MovieToGenres> movieToGenreList;
        try {
            if(searchType == 1){
                movieList = moviesDao.searchMovies(name);
            }
            else if(searchType == 2){
                movieActorList = movieActorsDao.getMovieActorsByActorName(name);
                movieList = new ArrayList<Movies>();
                for(MovieActors movieActor : movieActorList){
                    movieList.add(movieActor.getMovie());
                }
                Collections.sort(movieList, new YearComparator());
            }
            else if(searchType == 3){
                movieList = moviesDao.getMoviesByDirector(name);
                Collections.sort(movieList, new YearComparator());
            }
            else{
                movieToGenreList = movieToGenresDao.getMovieToGenresByGenreName(genre);
                movieList = new ArrayList<Movies>();
                for(MovieToGenres movieToGenre : movieToGenreList){
                    movieList.add(movieToGenre.getMovie());
                }
                Collections.sort(movieList, new YearComparator());
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("movieList", movieList);
        req.getRequestDispatcher("searchResults.jsp?UserName=" + userName).forward(req, resp);
    }
}
class YearComparator implements Comparator<Movies> {
    @Override
    public int compare(Movies a, Movies b) {
        if(a.getYear() > b.getYear())
            return -1;
        else if(a.getYear() < b.getYear())
            return 1;
        else{
            return 0;
        }
    }
}

