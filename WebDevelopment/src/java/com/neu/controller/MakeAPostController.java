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
import java.util.ArrayList;
import com.neu.pojo.User;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Gemi
 */
public class MakeAPostController extends AbstractController {
    
    public MakeAPostController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response)  {
        
        HttpSession session = (HttpSession) request.getSession(false);
        if(session == null)
        {
            return new ModelAndView("ErrorPage","data", "session null");
        }
        else{
                if(session.getAttribute("username") == null)
                    return new ModelAndView("ErrorPage","data", "session null");                    
            }
        
        ModelAndView mav = null;
        String title = request.getParameter("posttitle");
        String city = request.getParameter("city");
        String postalcode = request.getParameter("postalcode");
        String highlightinfo = request.getParameter("highlight");
        String description = request.getParameter("description");
        String date = request.getParameter("dateposted");
        
        //String username = request.getParameter("username");

        UserDAO userDao = new UserDAO();
        User u = (User) session.getAttribute("username");
        
        String category = request.getParameter("type");
        
        if(category.equals("books"))
        {   
            
            String genre = request.getParameter("genre");
            String image = request.getParameter("uploadImage");
            Books b = new Books();
            
            b.setCity(city);
            b.setDate(date);
            b.setDescription(description);
            b.setHighlight(highlightinfo);
            b.setPostalcode(postalcode);
            b.setTitle(title);
            //ADDED NOW
            b.setGenre(genre);
            b.addBookImage(image);
            
            
            //Add Book to User
            u.addBook(b);
        }
        else if(category.equals("electronics"))
        {
            Electronics e = new Electronics();
            String brand = request.getParameter("brand");
            String image = request.getParameter("uploadImage");
            
            e.setCity(city);
            e.setDate(date);
            e.setDescription(description);
            e.setHighlight(highlightinfo);
            e.setPostalcode(postalcode);
            e.setTitle(title);
            //TO BE DONE
            e.setBrand(brand);
            e.addElectronicsImage(image);
            
            //Add Book to User
            u.addElectronics(e);
        }
        else if(category.equals("furniture"))
        {
            Furniture obj = new Furniture();
            String image = request.getParameter("uploadImage");
            obj.setDate(date);
            obj.setCity(city);
            obj.setDescription(description);
            obj.setHighlight(highlightinfo);
            obj.setPostalcode(postalcode);
            obj.setTitle(title);
            
            obj.addFurnitureImage(image);
            
            u.addFurniture(obj);
        }
        else if(category.equals("rental"))
        {
            HomeRentals obj = new HomeRentals();
            String price = request.getParameter("price");
            String image = request.getParameter("uploadImage");
            obj.setDate(date);
            obj.setCity(city);
            obj.setDescription(description);
            obj.setHighlight(highlightinfo);
            obj.setPostalcode(postalcode);
            obj.setTitle(title);
            
            obj.setPrice(price);
            obj.addHomeRentalsImage(image);
            u.addHomeRental(obj);
        }
                
        try
        {
            //userDao.saveUserAccount(u);
            userDao.updateUserAccount(u);
            session.setAttribute("username", u);
            //mav = new ModelAndView("SuccessPage","data","postadded");
            mav = new ModelAndView("ViewMyPosts","data",u);
        }
        catch(Exception e)
        {   
            mav = new ModelAndView("ErrorPage","data","postnotadded "+e.getMessage()+" "+e.getStackTrace()+" "+e.getMessage());
        }

        return mav;
    }
    
}
