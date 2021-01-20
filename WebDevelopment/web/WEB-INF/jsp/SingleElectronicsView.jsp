<%-- 
    Document   : SingleElectronicsView
    Created on : Dec 10, 2019, 11:41:24 AM
    Author     : Gemi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post View</title>
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
        <h1> Electronics Details! </h1>
               <br>
                <img src="${data.uploadImage.toArray()[0]}" width="400" height="300"/>
               <br>
                  Details: ${requestScope.data.title}.  <br>
                  ${requestScope.data.highlight}  
                  ${requestScope.data.description}
                  ${requestScope.data.brand} <br>
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
                var url="getuseremailid.htm?type=electronics&postid="+v;

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
</html>
