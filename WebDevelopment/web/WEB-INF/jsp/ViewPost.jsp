<%-- 
    Document   : ViewPost
    Created on : Nov 23, 2019, 8:37:30 PM
    Author     : Gemi
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Page</title>
    </head>
    <body>
        <c:forEach items="${requestScope.data}" var="item" >  
            ${item.postID}
            ${item.title} 
            ${item.city} 
            ${item.postalcode} 
            ${item.highlight}
            ${item.date}
            <br/>
        </c:forEach> 
    </body>
</html>
