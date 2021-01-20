<%-- 
    Document   : HomePage
    Created on : Nov 21, 2019, 11:12:45 AM
    Author     : Gemi
--%>
<%@page import="com.neu.pojo.HomeRentals"%>
<%@page import="com.neu.pojo.Furniture"%>
<%@page import="com.neu.pojo.Electronics"%>
<%@page import="com.neu.pojo.Books"%>
<%@page import="java.awt.print.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.neu.pojo.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
  <head>
    <meta name="theme-color" content="#ffffff">
    <meta charset="utf-8"/>
    <link href="https://fonts.googleapis.com/css?family=Niramit" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/FinalProjResources/css/style.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>University Marketplace</title>
    
  </head>
  <style>
	  html{
	  font-family: 'Niramit', sans-serif;
	}
	body{
	  margin:0; 
	  background-image:url(../images/pattern.png);
	}
	#mainPanel{
	  width:100%;
	  margin:auto;
	}
        h3{
            margin-left: 16px;
        }
	#header{
	  width:100%;
	  background-color:black;
	  height:450px;
	  background-image:url("/FinalProjResources/images/Snow-December-Background-Christmas_unsplash-700x525.jpg");
	  background-size:cover;
	  background-position:center center;
	  background-repeat:no-repeat;
	  position:relative;
	}
        .displayLink{
            color: #3399ff;
        }
	#bannerTitle{
	  color:white;
	  text-align:center;  
	  width:100%;  
	  font-weight:bold;
	}
	#mainTitle{
	  padding-top: 10%;
	  font-size:50px;
	  font-family: cursive;
	}

	#navBar{
	  position:absolute;
	  width:100%;
	  background-color:rgba(0,0,0,0.4);
	  font-size:0;
	  bottom:0;
	}
	
	.topnav input[type=text] {
	  float: left;
	  padding: 10px;
	  border: none;
	  margin-top: 8px;
	  
	  font-size: 17px;
	  height: 40px;
	  
	}
	.topnav input[type=text] {
    border: 1px solid #ccc;
	width: 60%;
  }
  
  .topnav button[type=submit] {
    
	  margin-top: 8px;
	  width: 5%;
	  padding: 10px;
	  background: #2196F3;
	  color: white;
	  border: 1px solid grey;
	  border-left: none;
	  cursor: pointer;
	  height: 40px;
  }

	.navButton{
	  display:inline-block;
	  height:40px;
	  line-height:30px;
	  text-align:center;
	  text-decoration:none;
	  width:calc(95%/3);
	  margin-left:calc(5%/3);
	  margin-top:calc(5%/3);
	  margin-bottom:calc(5%/3);
	  outline:0;
	  border:0;
	  /*border:solid 0.0px rgba(0,0,0,0.1);
	  border-radius: 30px 30px 30px 30px;*/
	  font-size:20px;
	  background-color:white;
	  color: #004080;
	  cursor:pointer;
	}
        
        #searchBar{
	 
          
	  width:100%;
	  background-color:rgba(0,0,0,0.1);
	  font-size:0;
	  bottom:0;
	}
        .searchButton{
	  display:inline-block;
	  height:40px;
	  line-height:30px;
	  text-align:center;
	  text-decoration:none;
	  width:calc(95%/4.5);
	  margin-left:calc(5%/3);
	  margin-top:calc(5%/3);
	  margin-bottom:calc(5%/3);
	  outline:0;
	  border:0;
	  border:solid 0.0px rgba(0,0,0,0.1);
	  border-radius: 30px 30px 30px 30px;
	  font-size:25px;
	  background-color:white;
	  color: #004080;
	  cursor:pointer;
	}
        
        
	#Maincontent{
	  width:100%;
	  background-color:white;
	}
	.welcome{
	  width:100%;
	  -webkit-box-sizing: border-box; /* Safari/Chrome, other WebKit */
	  -moz-box-sizing: border-box;    /* Firefox, other Gecko */
	  box-sizing: border-box;        /* Opera/IE 8+ */
	  padding:15px 10px 15px 10px;
	}
	.image{
	  width:100%;
	  border:solid 2px rgba(0,0,0,0.2);
	  height: 450px;
	}
	.head{
	  font-size:50px;
	  font-weight:bold;
	  text-align: center;
	  color: #8585ad;
	}
	.subhead{
	  font-size:30px;
	  text-align: center;
	  color:#b3b3cc;
	}
	.para{
	  font-size:17px;
	  color: #555;
	}
	.para .about{
	  vertical-align: middle;
	}
	.middle-image {
	  background-image: url("");
	  background-color: #cccccc;
	  height: 500px;
	  background-position: center;
	  background-repeat: no-repeat;
	  background-size: cover;
	  position: relative;
	}

	.middle-text {
	  text-align: center;
	  position: absolute;
	  top: 50%;
	  left: 40%;
	  transform: translate(-50%, -50%);
	  color: white;
		font-size: 30px;
	  font-family: 'Niramit', sans-serif;
	}
	#iconBox{
	  text-align:center;
	}

	.icon{
	  position:relative;
	  height:160px;
	  width:160px;
	  border:solid 2px rgba(0,0,0,0.2);
	  display:inline-block;
	  margin:-1px 2px -1px 2px;
	  border-radius:20px;
	  cursor:pointer;
	}
	.iconText{
	  position:absolute;
	  top:20px;
	  bottom:10px;
	  left:10px;
	  right:10px;
	  color:#dac8a6;
	  text-shadow: 1px 1px 0 #000,
		-1px 1px 0 #000,
		1px -1px 0 #000,
		-1px -1px 0 #000,
		0px 1px 0 #000,
		0px -1px 0 #000,
		-1px 0px 0 #000,
		1px 0px 0 #000,
		2px 2px 0 #000,
		-2px 2px 0 #000,
		2px -2px 0 #000,
		-2px -2px 0 #000,
		0px 2px 0 #000,
		0px -2px 0 #000,
		-2px 0px 0 #000,
		2px 0px 0 #000,
		1px 2px 0 #000,
		-1px 2px 0 #000,
		1px -2px 0 #000,
		-1px -2px 0 #000,
		2px 1px 0 #000,
		-2px 1px 0 #000,
		2px -1px 0 #000,
		-2px -1px 0 #000;
	  transition:0.5s;
	  opacity:0;
	  font-size:20px;
	}
	.icon:hover .iconText{
	  opacity:1;
	}
	.iconBackground{
	  position:absolute;
	  top:0;
	  bottom:0;
	  left:0;
	  right:0;
	  background-position:center;
	  background-size:cover;
	  background-repeat:no-repeat;
	  border-radius:18px;
	  pointer-events:inherit;
	  transition:0.5s;
	}
	.icon:hover .iconBackground{
	  filter:blur(3px);
	  filter: brightness(0.50);
	}
	#footer{
	  width:100%;
	  padding:10px;
	  background-color:#cccc00; /*#dac8a6; */
	  -webkit-box-sizing: border-box; /* Safari/Chrome, other WebKit */
	  -moz-box-sizing: border-box;    /* Firefox, other Gecko */
	  box-sizing: border-box;         /* Opera/IE 8+ */
	}
	@media only screen and (min-width:300px){
	  .navButton{
		font-size:30px;
	  }
	}
	@media only screen and (min-width:600px){
	  .navButton{
		width:calc(95%/4);
		margin-left:calc(5%/5);
	  }
          
          .forlink {
            float: right;
      }
	}
  </style>
  <body>
    <div id="mainPanel">
      <div id="header">
        <div id="bannerTitle">
            <div id="mainTitle">NorthEastern University Marketplace</div>
        </div>
        <div id="navBar">
			<a class="navButton" href="redirect.htm?formName=makeapost">Make a Post</a>
                        <a class="navButton"  href="redirect.htm?formName=viewmypost">View My Post</a>
			
                        <a class="navButton"  href="https://venmo.com/account/sign-in">Payment</a> 
                        <a class="navButton"  href="redirect.htm?formName=viewmypost">View My Profile</a>
                        
        
                      <!--  <a class="navButton" href='AdminTasks.htm'>Admin Settings</a> -->	
       </div>
      </div>
      <div id="Maincontent">
        <div class="welcome">
            
            <span> <a class="forlink" href="redirect.htm?formName=logout">[ LOGOUT ]</a> </span>
            <a class="forlink" href='AdminTasks.htm'>[ ADMIN SETTING ] &nbsp;</a>
            
            <br/>
            <br/>
            <div class="head">The University Marketplace</div>
            
            <div class="para">
                <p> The University marketplace is a site exclusively for NEU students. 
				To make the transition of settling into college easier, here is a reliable community to share, sell and buy goods in good condition.
				While you are busy looking for places to stay, books to refer, laptops to buy etc. you can look for resources more readily available.</p>
                
            </div>
        </div>
		
		

		
				
        <!-- <img src="../images/header.png" width="100%" height="100"> -->
		
		
		
            <div class="welcome">
                <div class="head">Gallery</div>
                <br/>
             <div class="subhead">Search Popular Categories</div>
           <!--  <h3 style="text-align:center; color: #b3b3cc; "> Search By Category Name: </h3>    -->
           <br/>
           <br/>
           <div id="searchBar">
			<a class="searchButton" href="postview.htm?category=book&pgid=1"> Books </a>
                        <a class="searchButton"  href="postview.htm?category=electronics&pgid=1"> Electronics </a>
			<a class="searchButton"  href="postview.htm?category=furniture&pgid=1"> Furniture </a>
                        <a class="searchButton"  href="postview.htm?category=homerentals&pgid=1"> Apartment Rentals </a>                       
          </div>
           
           
           <div class="topnav" >
		<form name="myViewForm" action="redirect.htm" method="post">
		   <input type="text"  name="searchBy">
		   <button type="submit"><i class="fa fa-search"></i></button> 
		   <input type="hidden" name="formName" value="viewpost" />
                </form>
	 </div>
        
           <!-- STATIC IMAGE -->
        <div class="photos" style="text-align:center"> 
                <img src="/FinalProjResources/images/g1.jpg" width="400" height="300"/> 
				<a type="sumbit" href="redirect.htm?formName=viewmypost">${item.title}</a>       
            </div>
        </div>
        
      </div>
            
           
     <!-- DYNAMIC IMAGE -->
     <c:forEach items="${requestScope.data}" var="user" >
        <div style="text-align:center">
        <c:forEach items="${user.books}" var="books" >
           <br>
            <img src="${books.uploadImage.toArray()[0]}" width="400" height="300"/>
            <br>
            <!-- <input type="hidden" name="user" value=/> -->
            <a class="displayLink" type="submit" href="singlepost.htm?type=book&postid=${books.postID}&title=${books.title}">View more information about this Post - ${books.title}</a>
        </c:forEach>
        
            
        <c:forEach items="${user.electronics}" var="electronics" >
            <br>
            <img src="${electronics.uploadImage.toArray()[0]}" width="400" height="300"/>
            <br>
            <a class="displayLink" type="submit" href="singlepost.htm?type=electronics&postid=${electronics.postID}&title=${electronics.title}">View more information about this Post - ${electronics.title}</a>
        </c:forEach>
            
        <c:forEach items="${user.furniture}" var="furniture" >
           <br>
            <img src="${furniture.uploadImage.toArray()[0]}" width="400" height="300"/>
            <br>
            <a class="displayLink" type="submit" href="singlepost.htm?type=furniture&postid=${furniture.postID}&title=${furniture.title}">View more information about this Post - ${furniture.title}</a>
        </c:forEach>
            
        <c:forEach items="${user.homerentals}" var="homerentals" >
            <br>
            <img src="${homerentals.uploadImage.toArray()[0]}" width="400" height="300"/>
            <br>
            <a class="displayLink" type="submit" href="singlepost.htm?type=homerentals&postid=${homerentals.postID}&title=${homerentals.title}">View more information about this Post - ${homerentals.title}</a>
        </c:forEach>
        </div>    
    </c:forEach>
        

      <div id="footer">Copyright &copy; 2019 by NorthEastern University
            <a href="mailto:thomas.ge@husky.neu.edu">thomas.ge@husky.neu.edu</a>
            <a href="termsandconditions.htm" class="terms">*Terms & Conditions</a></div>
    </div>
    
    </body>
<!--    <script type="text/javascript"> 
        document.getElementById("circle").onclick = function() { 
  
            document.getElementById("circle").style.display = "none"; 
       } 
     </script>-->
</html>
