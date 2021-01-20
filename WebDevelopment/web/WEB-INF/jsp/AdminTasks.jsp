<%-- 
    Document   : Propriety
    Created on : Oct 12, 2019, 12:49:09 AM
    Author     : 16173
--%>
<%@page import="com.neu.pojo.User"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Admin Page</title>
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
        <h1>List of Users:</h1>
        
        <form id='MyForm' action='redirect.htm' method='post'>
            <table>
                <th style="font-size: 25px"><b>Name of User</b></th>
                <th style="font-size: 25px"><b>Email-ID of User </b></th>
                <th style="font-size: 25px"><b>Location </b></th>
                <th style="font-size: 25px"><b>Select to Delete </b></th>
                
                
                
                        
                <c:forEach items="${sessionScope.userListforAdmin}" var="user" >
                            <c:choose>
                                <c:when test="${user.username == 'gemi'}">

                                </c:when>    
                                <c:otherwise>
                                    <tr>
                                        <td> ${user.username} </td>
                                        <td> ${user.email} </td>
                                        <td> ${user.location} </td>
                                        <td><input type='checkbox' name='categoryTypeUser' value = '${user}' /> </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                      </c:forEach> 
                   

            </table>
            
                    <input type='hidden' name='formName' value='deleteUserAdminRole' />
                    <input type='submit'  value='Delete User'/>		<br/>	 
            <br/>

        </form>
            <br/>

                <a href="pdf.htm">Download as PDF</a>
                
            <br/>
    </body>
</html>
