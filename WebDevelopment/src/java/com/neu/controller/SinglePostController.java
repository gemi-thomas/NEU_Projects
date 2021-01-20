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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author 16173
 */
public class SinglePostController extends AbstractController {
    
    public SinglePostController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        HttpSession session = (HttpSession) request.getSession(false);
        if(session == null)
        {
            return new ModelAndView("ErrorPage","data", "session null");
        }
        else{
                if(session.getAttribute("username") == null)
                    return new ModelAndView("ErrorPage","data", "session null");                    
            }
        
        String type = request.getParameter("type");
        String postID = request.getParameter("postid");
        String title = request.getParameter("title");
        String username = "";
        UserDAO ud = new UserDAO();
        ModelAndView mav = null;
        
        if(type.contains("book"))
        {
            Books b = ud.getSingleBook(postID, title);
            mav = new ModelAndView("SingleBookView", "data", b);
        }
        else if(type.contains("electronics"))
        {
            Electronics e = ud.getSingleElectronic(postID, title);
             mav = new ModelAndView("SingleElectronicsView", "data", e);
        }
        else if(type.contains("furniture"))
        {
            Furniture e = ud.getSingleFurniture(postID, title);
             mav = new ModelAndView("SingleFurnitureView", "data", e);
        }
        else 
        {
            HomeRentals e = ud.getSingleHomeRentals(postID, title);
            mav = new ModelAndView("SingleHomeRentalsView", "data", e);
        }
        
        return mav;
    }
    
}
