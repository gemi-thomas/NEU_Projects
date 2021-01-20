<%-- 
    Document   : ViewSinglePost
    Created on : Dec 10, 2019, 11:04:16 AM
    Author     : Gemi
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Book Post</title>
    </head> 
    <style>
        .forlink {
            float: right;
      }
      .emaillink {
          color: #3399ff;
      }
    </style>
    <body>

        <a class="forlink" href="redirect.htm?formName=logout">[ LOGOUT ]</a>
        <a class="forlink" href="redirect.htm?formName=reload">[ HOMEPAGE ]</a>
        <a class="forlink"  href="https://venmo.com/account/sign-in">[PAYMENT] </a> 
        <br/>
        <br/>
        <h1> Book Details! </h1>
               <br>
                <img src="${data.uploadImage.toArray()[0]}" width="400" height="300"/>
               <br>
               
                  ${requestScope.data.highlight} <br>
                  ${requestScope.data.description} <br>
                  ${requestScope.data.genre} <br>
                  City: ${requestScope.data.city} <br>
                  ${requestScope.data.postalcode} <br>
                  Date Posted: ${requestScope.data.date} <br>

                  <form name="contactInfoForm">
                      <input type="hidden" name="postID" value="${requestScope.data.postID}" />
                      <input type="button"  value="Show Contact Information" onClick="sendInfo()">
                       <br/>
                      <br/>
                  </form>
                      <span class="emaillink" id="gemi"></span>
    </body> 
    
    <script>
            var request;
            function sendInfo()
                {
                var v=document.contactInfoForm.postID.value;
                console.log(v);
                var url="getuseremailid.htm?type=books&postid="+v;

                if(window.XMLHttpRequest){
                request=new XMLHttpRequest();
                }
                else if(window.ActiveXObject){
                request=new ActiveXObject("Microsoft.XMLHTTP");
                }

                try
                    {
                    request.onreadystatechange=getInfo;
                    request.open("GET",url,true);
                    request.send();
                    }
                    catch(e)
                    {
                    alert("Unable to connect to server");
                    }
                }

                function getInfo(){
                if(request.readyState==4){
                var val=request.responseText;
                document.getElementById('gemi').innerHTML=val;
                }
            }
        
        </script>
        
        <form name="catchCheckForm" action="captchverify.htm" method="post">
            <img src="captcha.htm">
            <br>
            <br>
            <input type="text" name="captcha" required="required" style="margin-top: 5px;">
            <br>
            <button type="submit" >Check Captcha</button>
                      
        </form>
        
         <button type="submit" >Report Spam</button>
   
</html>
