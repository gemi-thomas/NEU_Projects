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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author 16173
 */
public class PostViewController extends AbstractController {
    
    public PostViewController() {
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
        
        String categoryType = request.getParameter("category");
        String pgid =  request.getParameter("pgid");
        //max images in a Page
        int numtoDisplay = 3;
        
        ModelAndView mav = null;
        UserDAO ud = new UserDAO();

            
        if(categoryType.equals("book"))
        {
            List<Books> bookList = ud.getBooks();
            //Number of Pages is calibrated. deals with the scenario when listsize could be 0
            int numofpgids = (bookList.size()+ numtoDisplay - 1)/numtoDisplay;
            request.setAttribute("numpg", numofpgids);
            int pgIDasInt = Integer.parseInt(pgid);
            List<Books> toDisplay = ud.getBooks(pgIDasInt,numtoDisplay);
            mav = new ModelAndView("SearchBookView","data",toDisplay);
        }
        else if(categoryType.equals("electronics"))
        {
            List<Electronics> electronicsList = ud.getElectronics();
            
            int numofpgids = (electronicsList.size()+ numtoDisplay - 1)/numtoDisplay;
            request.setAttribute("numpg", numofpgids);
            int pgIDasInt = Integer.parseInt(pgid);
            List<Electronics> toDisplay = ud.getElectronics(pgIDasInt,numtoDisplay);
            mav = new ModelAndView("SearchElectronicsView","data",toDisplay);
        }
        else if(categoryType.equals("furniture"))
        {
            List<Furniture> furnList = ud.getFurniture();
            
            int numofpgids = (furnList.size()+ numtoDisplay - 1)/numtoDisplay;
            request.setAttribute("numpg", numofpgids);
            int pgIDasInt = Integer.parseInt(pgid);
            List<Furniture> toDisplay = ud.getFurniture(pgIDasInt,numtoDisplay);
            mav = new ModelAndView("SearchFurnitureView","data",toDisplay);
        }
        else if(categoryType.equals("homerentals"))
        {
            List<HomeRentals> homeList = ud.getHomeRentals();
            
            int numofpgids = (homeList.size()+ numtoDisplay - 1)/numtoDisplay;
            request.setAttribute("numpg", numofpgids);
            int pgIDasInt = Integer.parseInt(pgid);
            List<HomeRentals> toDisplay = ud.getHomeRentals(pgIDasInt,numtoDisplay);
            mav = new ModelAndView("SearchHomeRentalView","data",toDisplay);
        }
 
        return mav;
    }
    
}
