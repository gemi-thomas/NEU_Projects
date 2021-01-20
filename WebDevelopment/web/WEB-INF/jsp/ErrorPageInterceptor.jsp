<%-- 
    Document   : ErrorPageInterceptor
    Created on : Dec 12, 2019, 2:41:08 PM
    Author     : Gemi
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Interceptor Error Page</title>
    </head>
   <style>
        .forlink {
            float: right;
        }
    </style>
    <body>
        <a class="forlink" href="LoginPage.htm">[ LOGIN PAGE ]</a>
        <br/>
                <br/>
        <h2> Illegal data inserted into text field. </h2>
        <c:out value="${requestScope.interceptorError} Entered value is not a valid!"/>
    </body>
</html>
