<%--
  Created by IntelliJ IDEA.
  User: hu_minghao
  Date: 2/28/17
  Time: 4:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Movies" %>
<html>
<head>
    <link href="/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="panel">
    <div class="content">
        <h1>Log In</h1>
        <form action="login" method="post">
            <label for="username">Username:</label>
            <input id="username" type="text" name="inputUserName"><br>
            <label for="password">Password:</label>
            <input id="password" type="password" name="inputPassword"><br>
            <input type="submit" value="Login">
        </form>
        <p><a href="signup.jsp">No account? <br/>click here to sign up. It's free!</a></p>
        <%
            String msg = (String)request.getAttribute("msg");
            if(msg == null){
                msg =" ";
            }
        %>
        <span><%= msg %></span>
    </div>
</div>
</body>
</html>
