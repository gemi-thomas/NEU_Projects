<%-- 
    Document   : MakeAPost
    Created on : Nov 22, 2019, 11:18:52 AM
    Author     : Gemi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- <link rel="stylesheet" type="text/css" href="/FinalProjResources/css/stylesheet_MP.css">   -->
        <link rel="stylesheet" type="text/css" href="/FinalProjResources/css/stylesheet_CA.css">
        <title>Publish A Listing</title>
    </head>
    <body class= "bg" >
        <a class="forlink" href="redirect.htm?formName=logout">[ LOGOUT ]</a>
        <a class="forlink" href="redirect.htm?formName=reload">[ HOMEPAGE ]</a>
        <br/>
        <br/>
        
        <div class="containerCreateAccount font-poppins">
            <form name="form1" action="makeapost.htm" method="post"> 
                <span style="text-align:center;font-size: 35px"> Choose Category: </span> <br/>
                     <input type="radio" name="category" value="books" onchange="getRating(this)" /><span style="font-size: 20px">Books</span>
                     <input type="radio" name="category" value="electronics" onchange="getRating(this)" /><span style="font-size: 20px">Electronics</span>
                     <input type="radio" name="category" value="furniture" onchange="getRating(this)" /><span style="font-size: 20px">Furniture</span>
                     <input type="radio" name="category" value="rental" onchange="getRating(this)" /><span style="font-size: 20px">Home Rental</span>
                     <br/><br/>
            </form> 
       <script >
            function getRating(myvariable) {
              
              var postType = myvariable.value;
              Remove();
              //console.log(postType);
              if(postType === "books")
              {
                var formElement = document.createElement('form');
                formElement.id = 'removeforms';
                formElement.setAttribute("style", "position: absolute; top: 21%; left: 35%; margin: 20px;padding-left: 20px; padding-bottom: 20px; min-width: 500px; background-color: white;");
               
                
                formElement.setAttribute('method','post');
                formElement.setAttribute('action','makeapost.htm?type=books');
                document.body.appendChild(formElement);
                
                  
                  //POST TITLE
                var componentLabel1 = document.createElement('label');
                componentLabel1.innerHTML = "Post Title:";
                formElement.appendChild(componentLabel1);

                var componentInput1 = document.createElement('input');
                
                componentInput1.name = 'posttitle';
                componentInput1.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                formElement.appendChild(componentInput1);

                var lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //CITY
                var componentLabel2 = document.createElement('label');
                componentLabel2.innerHTML = "City/Neighborhood:";
                formElement.appendChild(componentLabel2);

                var componentInput2 = document.createElement('input');
                componentInput2.name = 'city';
                componentInput2.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                formElement.appendChild(componentInput2);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //Postal Code
                var componentLabel3 = document.createElement('label');
                componentLabel3.innerHTML = "Postal Code:";
                formElement.appendChild(componentLabel3);

                var componentInput3 = document.createElement('input');
                componentInput3.name = 'postalcode';
                componentInput3.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                formElement.appendChild(componentInput3);

                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //Description
                var componentLabel4 = document.createElement('label');
                componentLabel4.innerHTML = "Description:";
                formElement.appendChild(componentLabel4);

                var componentInput4 = document.createElement('input');
                componentInput4.name = 'description';
                componentInput4.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                formElement.appendChild(componentInput4);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                                    
                  //Date Posted:
                var componentLabel5 = document.createElement('label');
                componentLabel5.innerHTML = "Date Posted:";
                formElement.appendChild(componentLabel5);

                var componentInput5 = document.createElement('input');
                componentInput5.name = 'dateposted';
                componentInput5.placeholder = 'dd/MM/YYYY';
                componentInput5.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                formElement.appendChild(componentInput5);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                  //Genre:
                var componentLabel6 = document.createElement('label');
                componentLabel6.innerHTML = "Genre:";
                formElement.appendChild(componentLabel6);
                  
                var componentInput6 = document.createElement('input');
                componentInput6.name = 'genre';
                componentInput6.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                formElement.appendChild(componentInput6);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
				  
                //Upload Path
                var componentLabel7 = document.createElement('label');
                componentLabel7.innerHTML = "Upload File:";
                formElement.appendChild(componentLabel7);

                 var componentInput7 = document.createElement('input');
                componentInput7.type = "file";
                componentInput7.name = 'uploadImage';
                componentInput7.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                formElement.appendChild(componentInput7);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                
                //SUBMIT BUTTON
                var s = document.createElement('input');
                s.type = 'submit';
                s.value = 'Submit';
                formElement.appendChild(s);
                  
              }
              else if(postType === "electronics")
              {
                  
                var formElement = document.createElement('form');
                formElement.id = 'removeforms';
                formElement.setAttribute("style", "position: absolute; top: 21%; left: 35%; margin: 20px;padding-left: 20px; padding-bottom: 20px; min-width: 500px; background-color: white;");
                
                formElement.setAttribute('method','post');
                formElement.setAttribute('action','makeapost.htm?type=electronics');
                document.body.appendChild(formElement);
                  
                  //POST TITLE
                var componentLabel1 = document.createElement('label');
                componentLabel1.innerHTML = "Post Title:";
                formElement.appendChild(componentLabel1);

                var componentInput1 = document.createElement('input');
                componentInput1.name = 'posttitle';
                formElement.appendChild(componentInput1);

                var lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //CITY
                var componentLabel2 = document.createElement('label');
                componentLabel2.innerHTML = "City/Neighborhood:";
                formElement.appendChild(componentLabel2);

                var componentInput2 = document.createElement('input');
                componentInput2.name = 'city';
                formElement.appendChild(componentInput2);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //Postal Code
                var componentLabel3 = document.createElement('label');
                componentLabel3.innerHTML = "Postal Code:";
                formElement.appendChild(componentLabel3);

                var componentInput3 = document.createElement('input');
                componentInput3.name = 'postalcode';
                formElement.appendChild(componentInput3);

                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //Description
                var componentLabel4 = document.createElement('label');
                componentLabel4.innerHTML = "Description:";
                formElement.appendChild(componentLabel4);

                var componentInput4 = document.createElement('input');
                componentInput4.name = 'description';
                formElement.appendChild(componentInput4);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                                    
                  //Date Posted:
                var componentLabel5 = document.createElement('label');
                componentLabel5.innerHTML = "Date Posted:";
                formElement.appendChild(componentLabel5);

                var componentInput5 = document.createElement('input');
                componentInput5.name = 'dateposted';
                componentInput5.placeholder = 'dd/MM/YYYY';
                formElement.appendChild(componentInput5);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                 //Genre:
                var componentLabel6 = document.createElement('label');
                componentLabel6.innerHTML = "Brand:";
                formElement.appendChild(componentLabel6);
                  
                var componentInput6 = document.createElement('input');
                componentInput6.name = 'brand';
                formElement.appendChild(componentInput6);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
				  
                //Upload Path
                var componentLabel7 = document.createElement('label');
                componentLabel7.innerHTML = "Upload File:";
                formElement.appendChild(componentLabel7);

                 var componentInput7 = document.createElement('input');
                componentInput7.type = "file";
                componentInput7.name = 'uploadImage';
                formElement.appendChild(componentInput7);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                
                componentInput1.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput2.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput3.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput4.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput5.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput6.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput7.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                
                //SUBMIT BUTTON
                var s = document.createElement('input');
                s.type = 'submit';
                s.value = 'Submit';
                formElement.appendChild(s);
  
              }
              else if(postType === "furniture")
              {
                  var formElement = document.createElement('form');
                  formElement.id = 'removeforms';
                  formElement.setAttribute("style", "position: absolute; top: 21%; left: 35%; margin: 20px;padding-left: 20px; padding-bottom: 20px; min-width: 500px; background-color: white;");
                  
                formElement.setAttribute('method','post');
                formElement.setAttribute('action','makeapost.htm?type=furniture');
                document.body.appendChild(formElement);
                  
                  //POST TITLE
                var componentLabel1 = document.createElement('label');
                componentLabel1.innerHTML = "Post Title:";
                formElement.appendChild(componentLabel1);

                var componentInput1 = document.createElement('input');
                componentInput1.name = 'posttitle';
                formElement.appendChild(componentInput1);

                var lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //CITY
                var componentLabel2 = document.createElement('label');
                componentLabel2.innerHTML = "City/Neighborhood:";
                formElement.appendChild(componentLabel2);

                var componentInput2 = document.createElement('input');
                componentInput2.name = 'city';
                formElement.appendChild(componentInput2);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //Postal Code
                var componentLabel3 = document.createElement('label');
                componentLabel3.innerHTML = "Postal Code:";
                formElement.appendChild(componentLabel3);

                var componentInput3 = document.createElement('input');
                componentInput3.name = 'postalcode';
                formElement.appendChild(componentInput3);

                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //Description
                var componentLabel4 = document.createElement('label');
                componentLabel4.innerHTML = "Description:";
                formElement.appendChild(componentLabel4);

                var componentInput4 = document.createElement('input');
                componentInput4.name = 'description';
                formElement.appendChild(componentInput4);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                                    
                  //Date Posted:
                var componentLabel5 = document.createElement('label');
                componentLabel5.innerHTML = "Date Posted:";
                formElement.appendChild(componentLabel5);

                var componentInput5 = document.createElement('input');
                componentInput5.name = 'dateposted';
                componentInput5.placeholder = 'dd/MM/YYYY';
                formElement.appendChild(componentInput5);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  				  
                //Upload Path
                var componentLabel7 = document.createElement('label');
                componentLabel7.innerHTML = "Upload File:";
                formElement.appendChild(componentLabel7);

                 var componentInput7 = document.createElement('input');
                componentInput7.type = "file";
                componentInput7.name = 'uploadImage';
                formElement.appendChild(componentInput7);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                
                componentInput1.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput2.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput3.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput4.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput5.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                
                componentInput7.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                
                //SUBMIT BUTTON
                var s = document.createElement('input');
                s.type = 'submit';
                s.value = 'Submit';
                formElement.appendChild(s);
              }
              else if(postType === "rental")
              {
                  var formElement = document.createElement('form');
                  formElement.id = 'removeforms';
                  formElement.setAttribute("style", "position: absolute; top: 21%; left: 35%; margin: 20px;padding-left: 20px; padding-bottom: 20px; min-width: 500px; background-color: white;");
                  
                formElement.setAttribute('method','post');
                formElement.setAttribute('action','makeapost.htm?type=rental');
                document.body.appendChild(formElement);
                  
                  //POST TITLE
                var componentLabel1 = document.createElement('label');
                componentLabel1.innerHTML = "Post Title:";
                formElement.appendChild(componentLabel1);

                var componentInput1 = document.createElement('input');
                componentInput1.name = 'posttitle';
                formElement.appendChild(componentInput1);

                var lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //CITY
                var componentLabel2 = document.createElement('label');
                componentLabel2.innerHTML = "City/Neighborhood:";
                formElement.appendChild(componentLabel2);

                var componentInput2 = document.createElement('input');
                componentInput2.name = 'city';
                formElement.appendChild(componentInput2);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //Postal Code
                var componentLabel3 = document.createElement('label');
                componentLabel3.innerHTML = "Postal Code:";
                formElement.appendChild(componentLabel3);

                var componentInput3 = document.createElement('input');
                componentInput3.name = 'postalcode';
                formElement.appendChild(componentInput3);

                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                //Description
                var componentLabel4 = document.createElement('label');
                componentLabel4.innerHTML = "Description:";
                formElement.appendChild(componentLabel4);

                var componentInput4 = document.createElement('input');
                componentInput4.name = 'description';
                formElement.appendChild(componentInput4);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                                    
                  //Date Posted:
                var componentLabel5 = document.createElement('label');
                componentLabel5.innerHTML = "Date Posted:";
                formElement.appendChild(componentLabel5);

                var componentInput5 = document.createElement('input');
                componentInput5.name = 'dateposted';
                componentInput5.placeholder = 'dd/MM/YYYY';
                formElement.appendChild(componentInput5);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                  
                 //price:
                var componentLabel6 = document.createElement('label');
                componentLabel6.innerHTML = "Price:";
                formElement.appendChild(componentLabel6);
                  
                var componentInput6 = document.createElement('input');
                componentInput6.name = 'price';
                formElement.appendChild(componentInput6);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
				  
                //Upload Path
                var componentLabel7 = document.createElement('label');
                componentLabel7.innerHTML = "Upload File:";
                formElement.appendChild(componentLabel7);

                 var componentInput7 = document.createElement('input');
                componentInput7.type = "file";
                componentInput7.name = 'uploadImage';
                //componentInput7.setAttribute("onfocusout","addmoreImages()");
                formElement.appendChild(componentInput7);
                lineBreak = document.createElement("br");
                formElement.appendChild(lineBreak);
                
                componentInput1.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput2.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput3.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput4.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput5.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput6.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                componentInput7.setAttribute("style","width: 95%; padding: 15px; margin: 5px 0 20px 0; border: none; background: #f1f1f1;");
                //SUBMIT BUTTON
                var s = document.createElement('input');
                s.type = 'submit';
                s.value = 'Submit';
                formElement.appendChild(s);
              }
            }
            
           function Remove() {
            var f = document.getElementById('removeforms');
            if (f != null && f!= "undefined") {	
                    f.parentNode.removeChild(f);
            }
         }
        </script>
        </div>
    </body>
</html>
