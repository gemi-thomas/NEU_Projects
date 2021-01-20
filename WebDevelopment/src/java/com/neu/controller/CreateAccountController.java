/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.me.data.UserDAO;
import java.util.ArrayList;
import com.neu.pojo.User;
import java.sql.SQLException;
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
public class CreateAccountController extends AbstractController {
    
    public CreateAccountController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response)  {
        

        ModelAndView mav = null;
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String location = request.getParameter("loc");
        String phonenumber = request.getParameter("phonenumber");
        
        UserDAO userDao = new UserDAO();
        User user = new User();
        
        user.setEmail(email);
        user.setLocation(location);
        user.setPassword(password);
        user.setPhoneNum(phonenumber);
        user.setUsername(username);
        try
        {
            userDao.saveUserAccount(user);
            mav = new ModelAndView("SuccessPage","data","useraccountcreated");
        }
        catch(Exception e)
        {
            mav = new ModelAndView("ErrorPage","data","UserAccountCreationError");
        }

        return mav;
    }
    
}
