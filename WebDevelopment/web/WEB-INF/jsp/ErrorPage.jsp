<%-- 
    Document   : ErrorPage
    Created on : Nov 20, 2019, 9:03:59 PM
    Author     : Gemi
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page!</title>
    </head>
    <body>
        <c:choose>
            <c:when test= "${fn:contains(requestScope.data,'UserValidationError')}">
                <h2> User Name or Password was incorrect. Please re-submit details on LOGIN Page </h2>
            </c:when>
            
            <c:when test= "${fn:contains(requestScope.data,'UserAccountCreationError')}">
                <h2> Error in adding User details to Db. User NOT added. </h2>
            </c:when>
                
            <c:when test= "${fn:contains(requestScope.data,'postnotadded')}">
                <h2> POST could not be added. </h2>
                <c:out value = "${requestScope.data}" />
            </c:when>
                
            <c:when test= "${fn:contains(requestScope.data, 'session null')}">
                <h2> You are Not logged in! </h2>
                
                <br/>
            </c:when>
                                
        </c:choose>
        <a class="forlink" href="LoginPage.htm">[ LOGIN PAGE ]</a>
        
    </body>
</html>
