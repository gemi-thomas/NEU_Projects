<%-- 
    Document   : CreateAccount
    Created on : Nov 20, 2019, 1:02:12 PM
    Author     : 16173
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="/FinalProjResources/css/stylesheet_CA.css">
        <title>Create Account</title>
    </head>
    
    <body class= "bg" >
        <a class="forlink" href="LoginPage.htm">[ LOGIN PAGE ]</a>
        <br/>

	<div class="containerCreateAccount font-poppins">
		<h1 style="text-align:center">Create Account</h1>
	
        <form name='myForm' method="post" action="createaccount.htm">
           
            <label> E-mail ID: </label> <br/>
            <input type="email" id="box-style" pattern=".+@husky.neu.edu" placeholder="eg. email@husky.neu.edu" name = "email" required /><br/>
            
            <label> User-Name: </label> <br/>
            <input type="text" id="box-style" name = "username" required/><br/>
            
            <label> Password: </label> <br/>
            <input type="password" id="box-style" name = "password" required/><br/>
            
            <label> Location: </label> <br/>
            <input type="text" id="box-style" name = "loc" /><br/>
            
            <label> Phone Number: </label> <br/>
            <input type="tel" id="box-style" name = "phonenumber" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" placeholder="eg. xxx-xxx-xxxx" required/><br/>
            
            <div style="text-align:center;padding:10px;">  
                    <input type="submit" value="SUBMIT"/> 
            </div>  
            
        </form> 
	</div>
    </body>
</html>
