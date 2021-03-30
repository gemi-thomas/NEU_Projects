/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kenzan.WebProject.Utils;

import com.kenzan.WebProject.Pojo.Employee;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Gemi
 * This class helps in converting Employee into JSON object
 */
public class JSON {
    
    public static String toJSONArray(List<Employee> employeeList){
        String data = 
                employeeList
                .stream()
                .map(employee -> String.format("%n{%n\"id\":%d,%n"
                        + "\"firstName\":\"%s\",%n"
                        + "\"middleInitial\":\"%s\",%n"
                        + "\"lastName\":\"%s\",%n"
                         + "\"dateOfBirth\":\"%s\",%n"
                         + "\"dateOfEmployment\":\"%s\",%n"
                        + "\"status\":\"%s\"%n}",
                        employee.getId(),employee.getFirstName(),employee.getMiddleInitial(),
                        employee.getLastName(),employee.getDateOfBirth(),employee.getDateOfEmployment(),employee.getStatus()))
                .collect(Collectors.joining(","));
        return "[" + data +"]";
    }
    
     public static String toJSONObject(Employee employee) {
        if(employee == null)
             return null;
        return String.format("{%n\"id\":%d,%n"
                        + "\"firstName\":\"%s\",%n"
                        + "\"middleInitial\":\"%s\",%n"
                        + "\"lastName\":\"%s\",%n"
                         + "\"dateOfBirth\":\"%s\",%n"
                         + "\"dateOfEmployment\":\"%s\",%n"
                        + "\"status\":\"%s\"%n}",
                        employee.getId(),employee.getFirstName(),employee.getMiddleInitial(),
                        employee.getLastName(),employee.getDateOfBirth(),employee.getDateOfEmployment(),employee.getStatus());
    }
     
    public static String toJSONString(String message) {
        
        return String.format("{%n\"message\":\"%s\"%n}", message);
    }
}
