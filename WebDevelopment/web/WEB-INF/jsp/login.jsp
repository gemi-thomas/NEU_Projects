<%-- 
    Document   : login
    Created on : Oct 11, 2019, 10:33:04 PM
    Author     : 16173
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head><title>Restricted Login page</title>
  </head>
  <body>
        <h3>Login Using j_security_check:</h3>
        <form name="loginForm" method="POST" action="j_security_check">
            <p>User name: <input type="text" name="j_username" size="20"/></p>
            <p>Password: <input type="password" size="20" name="j_password"/></p>
            <p>  <input type="submit" value="Submit"/></p>
        </form>       
   </body>
</html> 
