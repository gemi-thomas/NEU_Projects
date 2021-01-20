<%-- 
    Document   : SuccessPage
    Created on : Nov 20, 2019, 9:02:02 PM
    Author     : Gemi
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success Page!</title>
    </head>
    <body>
        
        <c:choose>
            <c:when test= "${fn:contains(requestScope.data,'postadded')}">
                <h2> Post details added to User </h2>
            </c:when>
                
            <c:when test= "${fn:contains(requestScope.data,'useraccountcreated')}">
                <h2> User Account created! </h2>
            </c:when>
        </c:choose>
        [<a href="LoginPage.htm">Back to Login Page</a>]
        [<a href="HomePage.htm">Go Back </a>]
    </body>
</html>
