<%-- 
    Document   : SearchBookView
    Created on : Dec 10, 2019, 1:52:56 PM
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
        <h1> Search Results for Books</h1>
        <c:forEach items="${requestScope.data}" var="books" >
               <br>
                    <img src="${books.uploadImage.toArray()[0]}" width="400" height="300"/><br>
                    <a class="title"  href="singlepost.htm?type=book&postid=${books.postID}&title=${books.title}"> View This Post</a>
               <br>
               
                  Item highlight: ${books.highlight} <br>
                  Description: ${books.description} 
                  Genre: ${books.genre} <br/>
                                
       </c:forEach> 
                  
        <br/><br/>
        <b>Further results in pages: </b> <br/>
        <br/>
       <c:forEach var="i" begin="1" end="${requestScope.numpg}">
           
           <a class="btnbox" href="postview.htm?category=book&pgid=${i}" />${i}</a>
           
        </c:forEach>       
         
    </body>
</html>
