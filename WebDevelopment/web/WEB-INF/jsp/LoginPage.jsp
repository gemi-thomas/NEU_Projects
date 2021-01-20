<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="https://fonts.googleapis.com/css?family=Niramit" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="/FinalProjResources/css/stylesheet_CA.css">
        <title>Welcome to University Marketplace!</title>
        
    </head>

    <body>
	
	<div>
	<img src="C:\Users\16173\Desktop\files\background.jpg" class="bg-img" >
	<div class="containerLogin font-poppins">
	  <form form name='myForm' method="post" action="redirect.htm?formName=login" >
		<h1 style="text-align:center">Login</h1>

		<label for="email"><b>Email</b></label>
		<input type="text" placeholder="Enter College Email ID" name="username" required>

		<label for="psw"><b>Password</b></label>
		<input type="password" placeholder="Enter Password" name="password" required>

		<button type="submit" class="btn">Login</button>
	 </form>

		<span><h5>Do not have an account yet?</h5>[<a href="redirect.htm?formName=createaccount">Create Account</a>] </span>
	</div>
	</img>
	  
</div>
	
    </body>
</html>
