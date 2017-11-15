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
<html>
<head>
    <title>MovieX - the most popular movie information searching platform in the world</title>
    <link href="/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<%
    Users user = (Users)request.getAttribute("user");
%>
<div id="header"></div>
<div id="c  ontentArea">
    <iframe id="contentFrame" src="searchResults.jsp?UserName=<%=user.getUserName()%>"></iframe>
    <%--<img id="movieXPost" src="img/MovieXPost.jpg">--%>
</div>
<div id="userArea">
    <iframe id="userFrame" src="user.jsp?UserName=<%=user.getUserName()%>"></iframe>
</div>
<div id="footer"></div>
</body>
</html>
