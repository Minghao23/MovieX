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
<%@ page import="dal.UsersDao" %>
<%@ page import="dal.GenresDao" %>
<html>
<head>
    <title>MovieX - the most popular movie information searching platform in the world</title>
    <link href="/searchResults.css" rel="stylesheet" type="text/css">
    <link href="/main.css" rel="stylesheet" type="text/css">
    <script src="main.js"></script>
</head>
<body>
<div id="panel">
    <div id="searchBar">
        <%
            String userName = request.getParameter("UserName");
        %>
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
    <div id="pictureArea">
        <img src="img/Picture1.png">
        <img src="img/Picture2.png">
        <img src="img/Picture3.png">
        <img src="img/Picture4.png">
        <img src="img/Picture5.png">
        <img src="img/Picture6.png">
        <img src="img/Picture7.png">
        <img src="img/Picture8.png">
    </div>
</div>
</body>
</html>
