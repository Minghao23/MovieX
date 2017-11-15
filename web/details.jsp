<%--
  Created by IntelliJ IDEA.
  User: hu_minghao
  Date: 2/28/17
  Time: 4:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page import="dal.*" %>
<html>
<head>
    <title>MovieX - the most popular movie information searching platform in the world</title>
    <link href="/details.css" rel="stylesheet" type="text/css">
    <link href="/main.css" rel="stylesheet" type="text/css">
    <script src="main.js"></script>
</head>
<body>
<%
    String userName = request.getParameter("UserName");
%>
<script>
    window.onload = function(){
        var searchType=document.getElementById("searchType");
        searchType.onchange=doit;
        hidden('searchByGenre');
        show('searchByOther');
        window.parent.document.getElementById('userFrame').src='user.jsp?UserName=<%=userName%>'
    }
</script>
<div id="panel">
    <div id="searchBar">
        <form id="searchForm" action="search" method="post">
            Search movies:
            <select id="searchType" form="searchForm" name="inputsearchtype">
                <option value="1" selected="selected">by title</option>
                <option value="2">by actor</option>
                <option value="3">by director</option>
                <option value="4">by genre</option>
            </select>

            <input id="searchByOther" type="text" name="inputname">
            <select id="searchByGenre" form="searchForm" name="inputgenre">
                <%
                    List<Genres> genres = GenresDao.getInstance().getAllGenres();
                    for(int i=0; i<genres.size(); i++){
                        String genreName = genres.get(i).getGenreName();
                %>
                <option value="<%=genreName%>"><%=genreName%></option>
                <%
                    }
                %>
            </select>
            <input type="hidden" name="inputusername" value="<%=userName%>">
            <input type="submit" value="Search">
            <input type="button" value="IMDb Top 100" onclick="top100Form()">
            <input id="analysisBtn" type="button" value="Analysis" onclick="analysisForm()">
            <%--<input type="submit" value="Search" onclick="parent.window.document.getElementById('movieXPost').style.display='none'">--%>
        </form>
    </div>
    <div id="detailArea">
        <%
            int movieID = Integer.parseInt(request.getParameter("MovieID"));
            Movies movie = MoviesDao.getInstance().getMovieByMovieID(movieID);
            Users user = UsersDao.getInstance().getUserByUserName(userName);
            List<MovieActors> movieActors = MovieActorsDao.getInstance().getMovieActorsByMovieID(movieID);
            List<MovieToGenres> movieToGenres = MovieToGenresDao.getInstance().getMovieToGenresByMovieID(movieID);
            List<Favorites> favorites = FavoritesDao.getInstance().getFavoritesByUser(userName);
        %>
        <h1><%=movie.getMovieTitle()%></h1>
        <%
            String flag = "add to";
            for(int i=0;i<favorites.size();i++){
                int tempMovieID = favorites.get(i).getMovie().getMovieID();
                if(tempMovieID == movieID){
                    flag = "delete from";
                    break;
                }
            }

            String imgSrc = "img/whiteHeart.png";
            if(flag.equals("delete from")){
                imgSrc = "img/redHeart.png";
            }
        %>

        <div id="image">
            <img src="<%=movie.getImgURL()%>" width="100%" height="100%">
        </div>
        <div id="information">
            <form id="favoriteForm" action="addFavorite" method="post">
                <input type="hidden" name="inputmovieid" value="<%=movieID%>">
                <input type="hidden" name="inputusername" value="<%=userName%>">
                <input type="hidden" name="inputflag" value="<%=flag%>">
                <input id="favoriteImg" type="image" width="30px" height="27px" src="<%=imgSrc%>">
                <label id="favoriteLabel" for="favoriteImg"><%=flag%> favorites</label>
            </form>
            <p><b>Director: </b><%=movie.getDirector().getName()%></p>
            <p><b>Actors: </b>
                <%
                    for(int i=0;i<movieActors.size();i++){
                        MovieActors movieActor = movieActors.get(i);
                        String actorsStr;
                        if(i==0){
                            actorsStr = movieActor.getActor().getName();
                        }else{
                            actorsStr = "/ " + movieActor.getActor().getName();
                        }
                %>
                <span><%=actorsStr%></span>
                <%
                    }
                %>
            </p>
            <p><b>Genres: </b>
                <%
                    for(int i=0;i<movieToGenres.size();i++){
                        MovieToGenres movieToGenre = movieToGenres.get(i);
                        String genresStr;
                        if(i==0){
                            genresStr = movieToGenre.getGenre().getGenreName();
                        }else{
                            genresStr = "/ " + movieToGenre.getGenre().getGenreName();
                        }
                %>
                <span><%=genresStr%></span>
                <%
                    }
                %>
            </p>
            <p><b>Runtime: </b><%=movie.getDuration()%> min</p>
            <p><b>Content Rating: </b><%=movie.getContentRating()%></p>
            <p><b>Country: </b><%=movie.getCountry()%></p>
            <p><b>Language: </b><%=movie.getLanguage()%></p>
            <p><b>Release Year: </b><%=movie.getYear()%></p>
            <p><b>Budget: </b>$<%=movie.getBudget()%></p>
            <p><b>Gross: </b>$<%=movie.getGross()%></p>
            <p><b>IMDb Link: </b><a target="_blank" href="<%=movie.getIMDbLink()%>"><%=movie.getIMDbLink()%></a></p>
            <p><b>IMDb Score: </b><%=movie.getIMDbScore()%></p>
        </div>
    </div>
    <div id="reviewArea">
        <%
            ReviewsDao reviewsDao = ReviewsDao.getInstance();
            List<Reviews> reviews = reviewsDao.getReviewsByMovie(movieID);
        %>
        <table id="previousReviews">
            <%
                for(int i=0; i<reviews.size(); i++){
                    Reviews curReview = reviews.get(i);
            %>
            <tr><td>
            <div class="previousReview">
                <table class="reviewTable">
                    <tr height="20%"><td>
                        #<%=i+1%>
                        <br>
                        <%=curReview.getUser().getUserName()%>&nbsp;&nbsp;&nbsp;&nbsp;rating: <%=curReview.getRating()%>
                        <span class="floatRightSpan"><%=curReview.getCreated()%></span>
                    </td></tr>
                    <tr height="80%"><td class="reviewContent">
                        <%=curReview.getContent()%>
                    </td></tr>
                </table>
            </div>
            </td></tr>
            <%
                }
            %>
        </table>
        <div id="addReview">
            <form id="submitReviewForm" action="submitReview" method="post">
            <table class="reviewTable">
                    <tr height="20%"><td>
                        <b>Your comment</b><br>
                        <label for="reviewRating">rating:</label>
                        <select id="reviewRating" form="submitReviewForm" name="inputrating">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3" selected="selected">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                        <span class="floatRightSpan">
                            <input type="hidden" name="inputmovieid" value="<%=movieID%>">
                            <input type="hidden" name="inputusername" value="<%=userName%>">
                            <input type="submit" value="Submit">
                        </span>
                    </td></tr>
                    <tr height="80%"><td class="reviewContent">
                        <textarea form="submitReviewForm" name="inputcontent"></textarea>
                    </td></tr>
            </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>
