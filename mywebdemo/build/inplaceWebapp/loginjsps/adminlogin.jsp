<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Admin Login</title>
    </head>
    <body>
        <form method="post" action="AdminLoginController">
          <label for="uname"><b>Username</b></label><p></p>
          <input type="text" id="username" name="username"/><p></p>
          <label for="passd"><b>Password</b></label><p></p>
          <input type="password" id="password" name="password"/><p></p>
          <input type="submit" id="login" value="login" />
    </body>
</html>
