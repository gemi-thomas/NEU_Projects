/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.me.data.UserDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Gemi
 */
public class getuseremailidController extends AbstractController {
    
    public getuseremailidController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        
        String type = request.getParameter("type");
        String postid = request.getParameter("postid");
        
        UserDAO ud = new UserDAO();
        //String message = "Contact the seller for this item through this email address: ";
        String email = ud.getUserDetails(type, postid);;
        return new ModelAndView("ShowEmail","data",email);
       
    }
    
}
