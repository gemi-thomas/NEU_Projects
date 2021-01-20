<%-- 
    Document   : SearchElectronicsView
    Created on : Dec 10, 2019, 1:53:39 PM
    Author     : 16173
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Keyword View!</title>
    </head>
    
    <style>
        .forlink {
            float: right;
      }
      .btnbox {
            display: inline;
            width: 30px;
            height: 25px;
            background: #4E9CAF;
            padding: 10px;
            text-align: center;
            border-radius: 5px;
            color: white;
            font-weight: bold;
      }
    </style>
    <body>
        <a class="forlink" href="redirect.htm?formName=logout">[ LOGOUT ]</a>
        <a class="forlink" href="redirect.htm?formName=reload">[ HOMEPAGE ]</a>
        <br/>
        <br/>
        <h1> Search Results for Electronics</h1>
        
        <c:forEach items="${requestScope.data}" var="e" >
               <br>
                    <img src="${e.uploadImage.toArray()[0]}" width="400" height="300"/> <br/>
                    <a class="title"  href="singlepost.htm?type=electronics&postid=${e.postID}&title=${e.title}"> View This Post</a>
               <br>
               
                  Item highlight: ${e.highlight} <br>
                  Description: ${e.description} <br>
                  Brand: ${e.brand} <br>
                      
       </c:forEach> 
                  
       <br/><br/>
        <b>Further results in pages: </b> <br/>
        <br/>
       <c:forEach var="i" begin="1" end="${requestScope.numpg}">
           
           <a class="btnbox" href="postview.htm?category=electronics&pgid=${i}" />${i}</a>
           
        </c:forEach>  
    </body>
</html>
