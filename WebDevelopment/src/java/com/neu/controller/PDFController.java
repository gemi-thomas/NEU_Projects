/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.controller;

import com.neu.pojo.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Gemi
 */
public class PDFController extends AbstractController {
    
    public PDFController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
       
        HttpSession session = request.getSession(false);
        Map<String, String> pdfInfo = new HashMap<>();
        //String url = request.getParameter("makeurl");
        
        //String[] arrSplitByamp = url.split("&");
//        for(int i=0;i< arrSplitByamp.length;i++)
//        {
//            String[] arrSplitByeq = arrSplitByamp[i].split("=");
//            pdfInfo.put(arrSplitByeq[0], arrSplitByeq[1]);
//        }
        
        ArrayList<User> list = (ArrayList<User>) session.getAttribute("userListforAdmin");
        int count = 1;
        for(User u: list)
        {   
            pdfInfo.put("User "+count, u.getUsername()+" "+u.getEmail());
            count++;
        }
        
        return new ModelAndView("Brochure", "brochure", pdfInfo);
    }
    
}
