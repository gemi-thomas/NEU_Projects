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
        <title>View ALL MY Post</title>
    </head>
    <style>
        table {
            border-collapse: collapse;
            width: 60%;
            border: 2px solid black;
          }
         
        th, td {
            padding: 15px;
            text-align: left;
            border: 1px solid black;
          }
        tr:nth-child(even)
        {background-color: #f2f2f2;
        text-align: left; }
        
        .forlink {
	float: right;
  }
    </style>
    <body>
        <a class="forlink" href="redirect.htm?formName=logout">[ LOGOUT ]</a>
        <a class="forlink" href="redirect.htm?formName=reload">[ HOMEPAGE ]</a>
        <br/>
        <br/>
        <h1>List of My Posts: </h1>
        
        <form id='MyForm' action='redirect.htm' method='post'>
    
            <table>
                <th style="font-size: 25px"><b>Name </b></th>
                <th style="font-size: 25px"><b>Category </b></th>
                <th style="font-size: 25px"><b>Select to Delete </b></th>

               <c:forEach items="${requestScope.data.books}" var='book' >
               <tr>
                   <td> ${book.title} </td>
                   <td> Book </td>
                   <td><input type='checkbox' name='categoryTypeB' value = '${book}' /> </td>
               </tr>
               </c:forEach> 
               <c:forEach items="${requestScope.data.electronics}" var='electronics' >
               <tr>
                   <td> ${electronics.title} </td>
                   <td> Electronics </td>
                   <td><input type='checkbox' name='categoryTypeE' value = '${electronics}' /> </td>
               </tr>
               </c:forEach> 
               <c:forEach items="${requestScope.data.furniture}" var='furniture' >
               <tr>
                   <td> ${furniture.title} </td>
                   <td> Furniture </td>
                   <td><input type='checkbox' name='categoryTypeF' value = '${furniture}' /> </td>
               </tr>
               </c:forEach> 
               <c:forEach items="${requestScope.data.homerentals}" var='homerentals' >
               <tr>
                   <td> ${homerentals.title} </td>
                   <td> Home Rentals </td>
                   <td><input type='checkbox' name='categoryTypeHR' value = '${homerentals}' /> </td>
               </tr>
               </c:forEach>
            </table>
  
                <br/>
                    <input type='hidden' name='formName' value='deleteItem' />
                    <input type='submit'  value='Delete Post'/>		<br/>	 
                <br/>
        </form>
    </body>
</html>
