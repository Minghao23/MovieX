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
        <h1>Sign Up</h1>
        <form action="signup" method="post">
            <label for="username">Username:</label>
            <input id="username" type="text" name="inputUserName"><br>
            <label for="password">Password:</label>
            <input id="password" type="password" name="inputPassword"><br>
            <label for="firstname">First Name:</label>
            <input id="firstname" type="text" name="inputFirstName"><br>
            <label for="lastname">Last Name:</label>
            <input id="lastname" type="text" name="inputLastName"><br>
            <label for="email">Email:</label>
            <input id="email" type="text" name="inputEmail"><br>
            <input type="submit" value="Create Account">
        </form>
        <p><a href="login.jsp">return login</a></p>
    </div>
</div>
</body>
</html>
