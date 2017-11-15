<%--
  Created by IntelliJ IDEA.
  User: hu_minghao
  Date: 2/28/17
  Time: 4:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="panel">
    <%
        String username = request.getParameter("UserName");
    %>
    <div class="content">
        <h1>Change Password</h1>
        <form action="modifyPassword" method="post">
            <label for="newPassword">New password:</label>
            <input id="newPassword" type="password" name="inputNewPassword"><br>
            <input value="<%=username%>" type="hidden" name="inputUsername"><br>
            <input type="submit" value="OK">
        </form>
    </div>
</div>
</body>
</html>
