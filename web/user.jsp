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
    <link href="/user.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="panel">
    <div id="welcome" class="innerPanel">
        <%
            UsersDao usersDao = UsersDao.getInstance();
            String userName = request.getParameter("UserName");
            Users user = usersDao.getUserByUserName(userName);
        %>
        <h1>Welcome, </h1>
        <h1><%=user.getFirstName()%> <%=user.getLastName()%>!</h1>
        <p>Email: <%=user.getEmail()%></p>
    </div>
    <br>
    <br>
    <br>
    <div id="favorite" class="innerPanel">
        <%
            FavoritesDao favoritesDao = FavoritesDao.getInstance();
            List<Favorites> favorites = favoritesDao.getFavoritesByUser(userName);
        %>
        <h3>Favorite List</h3>
        <ul id="favoriteList" style="list-style-type:circle">
            <%
                for(int i=0;i<favorites.size();i++){
                    Movies movie = favorites.get(i).getMovie();
                    int movieID = movie.getMovieID();
                    String movieTitle = movie.getMovieTitle();
            %>
            <li name="favoriteMovie" ><a href="#" onclick="window.parent.document.getElementById('contentFrame').src=
                'details.jsp?MovieID=<%=movieID%>&UserName=<%=userName%>'"><%=movieTitle%></a></li>
            <%
                }
            %>
        </ul>
    </div>
    <div id="bottomBar">
        <a href="modifyPassword.jsp?UserName=<%=userName%>">Change password</a><br/>
        <a target="_parent" href="login.jsp">Sign out</a>
    </div>
</div>
</body>
</html>
