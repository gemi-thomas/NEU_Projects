<%-- 
    Document   : SingleHomeRentalsView
    Created on : Dec 10, 2019, 11:45:22 AM
    Author     : 16173
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post View </title>
    </head>
    <style>
        .forlink {
            float: right;
      }
      .emaillink {
          color: #3399ff;
      }
      #searchButton{
	  display:inline-block;
	  height:30px;
	  line-height:25px;
	  text-align:center;
	  text-decoration:none;
	  width:calc(95%/6);
	  
	  margin-top:calc(5%/3);
	  margin-bottom:calc(5%/3);
	  outline:0;
	  border:0;
	  border:solid 0.0px rgba(0,0,0,0.1);
	  border-radius: 30px 30px 30px 30px;
	  font-size:15px;
	  background-color:#ffe6e6;
	  color: #004080;
	  cursor:pointer;
	}
    </style>
    <body>
        
        <a class="forlink" href="redirect.htm?formName=logout">[ LOGOUT ]</a>
        <a class="forlink" href="redirect.htm?formName=reload">[ HOMEPAGE ]</a>
        <a class="forlink"  href="https://venmo.com/account/sign-in">[PAYMENT] </a> 
        <br/>
        <br/>
        <h1> Apartment/Home Rental Details! </h1>
               <br>
                <img src="${data.uploadImage.toArray()[0]}" width="400" height="300"/>
               <br>
               
                  ${requestScope.data.highlight} <br>
                  ${requestScope.data.description} <br>
                  ${requestScope.data.price} <br>
                  City: ${requestScope.data.city} <br>
                  ${requestScope.data.postalcode} <br>
                  Date Posted: ${requestScope.data.date} <br>
                  
                  <form name="contactInfoForm">
                      <input type="hidden" name="postID" value="${requestScope.data.postID}" />
                      <div> <input  type="button"  value="Show Contact Information" onClick="sendInfo()"> </div>
                      
                  </form>
                      <span class="emaillink" id="gemi"></span>
    </body>
    
    <script>
            var request;
            function sendInfo()
                {
                var v=document.contactInfoForm.postID.value;
                console.log(v);
                var url="getuseremailid.htm?type=homerentals&postid="+v;

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
