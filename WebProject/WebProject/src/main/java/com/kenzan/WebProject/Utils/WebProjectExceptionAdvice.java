/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kenzan.WebProject.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Gemi
 * Using Exception Handling to return custom message
 */
@ControllerAdvice
public class WebProjectExceptionAdvice {
  @ResponseBody
  @ExceptionHandler(WebProjectException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)

   String employeeExceptionHandler(WebProjectException ex) {   
        return JSON.toJSONString(ex.getMessage());
  }
}
