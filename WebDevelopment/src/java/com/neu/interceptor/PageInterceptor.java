/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.interceptor;
import java.util.Enumeration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author Gemi
 */
public class PageInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest) hsr;
        HttpServletResponse response = (HttpServletResponse) hsr1;
        String tempStr = "";
        boolean isbool = true;
        Enumeration<String> params = request.getParameterNames();
            while(params.hasMoreElements()){
                String name = params.nextElement();
                String value = request.getParameter(name);
                tempStr = value;
                if(((value.contains("1=1")) || (value.contains("<script>"))
                || (value.contains("DROP")) || (value.contains("drop"))))
                {
                    isbool = false;
                    break;
                }
                
        }
        if(isbool)
        {
            return true;
        }
        else
        {   
            request.setAttribute("interceptorError", tempStr);
            response.sendRedirect("ErrorPageInterceptor.htm");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav) throws Exception {
        
    }

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) throws Exception {
        
    }
    
}
