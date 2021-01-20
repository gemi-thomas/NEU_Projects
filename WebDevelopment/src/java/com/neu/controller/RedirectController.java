/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.me.data.UserDAO;
import com.neu.pojo.Books;
import com.neu.pojo.Electronics;
import com.neu.pojo.Furniture;
import com.neu.pojo.HomeRentals;
import com.neu.pojo.Post;
import com.neu.pojo.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Gemi
 */
public class RedirectController extends AbstractController {
    
    public RedirectController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        String formName = request.getParameter("formName");
        
        if(!(formName.equals("login") ||formName.equals("createaccount")) )
        {   
            HttpSession session = (HttpSession) request.getSession(false);
            if(session == null)
            {
                return new ModelAndView("ErrorPage","data", "session null");
            }   
            else{
                if(session.getAttribute("username") == null)
                    return new ModelAndView("ErrorPage","data", "session null");                    
            }
        }
        
        

        String username = "";
        ModelAndView mav = null;
        
        
        List<Post> deleteList = new ArrayList<>();
        if(formName.contains("login"))
        {   
            username = request.getParameter("username");
            String password = request.getParameter("password");
            
            UserDAO ud = new UserDAO();
            User u = ud.userValidCheck(username, password);
                        
            if(u != null && u.getPassword().equals(password))
            {  
                HttpSession session = (HttpSession) request.getSession(true);
                session.setAttribute("username", u);
                List<User> userList = null;
                userList = ud.getAllUsers();
                
                if(u.getUsername().equals("gemi"))
                {
                    session.setAttribute("userListforAdmin", userList);
                }
                //request.setAttribute("userList", userList);
                mav = new ModelAndView("HomePage","data",userList);
            }
            else
            {
                mav = new ModelAndView("ErrorPage","data","UserValidationError");
            }
            
            return mav;
        }
        HttpSession session = (HttpSession) request.getSession(false);
        
        if(formName.contains("reload"))
        {
            UserDAO ud = new UserDAO();

            List<User> userList = null;
            userList = ud.getAllUsers();
            //request.setAttribute("userList", userList); 
            mav = new ModelAndView("HomePage","data",userList);
        }
        else if(formName.contains("createacc"))
        {
            mav = new ModelAndView("CreateAccount","data",null);
        }
        else if(formName.contains("makeapost"))
        {              
            mav = new ModelAndView("MakeAPost","data",username);
        }
        else if(formName.contains("viewpost"))
        {   
            UserDAO ud = new UserDAO();
            String searchKeyword = request.getParameter("searchBy");
            if(searchKeyword.contains("book"))
            {
                List<Books> bookList = ud.getBooks();
                mav = new ModelAndView("SearchBookView","data",bookList);
            }
            else if(searchKeyword.contains("elect"))
            {
                List<Electronics> electronicsList = ud.getElectronics();
                mav = new ModelAndView("SearchElectronicsView","data",electronicsList);
            }
            else if(searchKeyword.contains("furni"))
            {
                List<Furniture> furnList = ud.getFurniture();
                mav = new ModelAndView("SearchFurnitureView","data",furnList);
            }
            else if(searchKeyword.contains("home"))
            {
                List<HomeRentals> homeList = ud.getHomeRentals();
                mav = new ModelAndView("SearchHomeRentalView","data",homeList);
            }else{
                List<Books> bookList = ud.getBooks();
                mav = new ModelAndView("SearchBookView","data",bookList);
            }
                        
        }
        else if(formName.equals("viewmypost"))
        {   
            User u = (User) session.getAttribute("username");
//            int userID = u.getUserID();
//            UserDAO ud = new UserDAO();
            //List<Post> postList = ud.getMyPost(userID);
            
            mav = new ModelAndView("ViewMyPosts","data",u);
        }
        else if(formName.equals("deleteItem"))
        {
            String str = "";
            User u = (User) session.getAttribute("username");
            String[] checkedIdsB = request.getParameterValues("categoryTypeB");
            if(checkedIdsB != null)
            {
               for(int i = 0; i < checkedIdsB.length; i++)
                {	
                    u.deleteBook(checkedIdsB[i]);    
                } 
            }
            
            
            String[] checkedIdsE = request.getParameterValues("categoryTypeE");
            if(checkedIdsE != null)
            {
                for(int i = 0; i < checkedIdsE.length; i++)
                {	
                    u.deleteElectronics(checkedIdsE[i]);    
                }
            }
            
            String[] checkedIdsF = request.getParameterValues("categoryTypeF");
            if(checkedIdsF != null)
            {
                for(int i = 0; i < checkedIdsF.length; i++)
                {	
                    u.deleteFurniture(checkedIdsF[i]);    
                }
            }
            
            String[] checkedIdsH = request.getParameterValues("categoryTypeHR");
            
            if(checkedIdsH != null)
            {
                for(int i = 0; i < checkedIdsH.length; i++)
                {	
                    u.deleteHomeRental(checkedIdsH[i]);    
                }
            }
            UserDAO userDao = new UserDAO();
            userDao.updateUserAccount(u);
            session.setAttribute("username", u);
            mav = new ModelAndView("ViewMyPosts","data",u);
            
        }
        else if(formName.equals("deleteUserAdminRole"))
        {
            String str = "";
            User u = (User) session.getAttribute("username");
            String[] checkedIds = request.getParameterValues("categoryTypeUser");
            List<User> userList = (List<User>) session.getAttribute("userListforAdmin");
            UserDAO userDao = new UserDAO();
            if(checkedIds != null)
            {
               for(int i = 0; i < checkedIds.length; i++)
                {   
                    User usertemp = null;
                    for(User user: userList)
                    {   
                        if(user.toString().equals(checkedIds[i]))
                        {   
                            userDao.deleteUserAccount(user);
                            usertemp = user;
                            break;
                        }
                    }
                    userList.remove(usertemp);
                    
                } 
            }
            
            mav = new ModelAndView("AdminTasks","NullVar",null);
        }
        else if(formName.equals("logout"))
        {
            session.invalidate();
            mav = new ModelAndView("LoginPage","NullVar",null);
        }
        
        
//        UserDAO ud = new UserDAO();
//        
//        User u = new User();
//        u.setA(request.getParameter("actor"));
//        u.setActress(request.getParameter("actress"));
//        u.setGenre(request.getParameter("genre"));
//        u.setYear(Integer.parseInt(request.getParameter("year")));
//        u.setTitle(request.getParameter("movietitle"));
//             
//        mh.saveMovie(m); //Always the one Movie to save
//        ModelAndView mav = new ModelAndView("AddResults","data",null);
//        return mav;

        //To View all Posts on HomePage Initially
//        UserDAO ud = new UserDAO();
//        List<User> userList = ud.getAllUsers();
//        mav = new ModelAndView("HomePage","data",userList);
    
          return mav;
    }
    
}
