<%--
  Created by IntelliJ IDEA.
  User: Namng
  Date: 2/15/2022
  Time: 5:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<h1>Login page</h1>
<form action="DispatchController" method="POST">
    UserName<input type="text" name="txtUserName" value="98765"/> <br/>
    Password<input type="text" name="txtPassword"/> <br/>
    <input type="submit" value="Login" name="btnAction"/>
</form>
</body>
</html>
