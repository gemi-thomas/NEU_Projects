package com.kenzan.WebProject;

import com.kenzan.WebProject.Data.AbstractDataStorage;
import com.kenzan.WebProject.Data.DataParser;
import com.kenzan.WebProject.Data.DataStorageFactory;
import com.kenzan.WebProject.Utils.JSON;
import com.kenzan.WebProject.Data.XMLDataStore;
import com.kenzan.WebProject.Pojo.Employee;
import com.kenzan.WebProject.Utils.WebProjectException;
import com.kenzan.WebProject.Utils.WebProjectExceptionAdvice;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the entry point for web application. 
 * Implements HTTP Requests - GET, POST, PUT, DELETE
 */

@SpringBootApplication
@RestController
public class WebProjectApplication {
    
    public static void main(String[] args) {
            SpringApplication.run(WebProjectApplication.class, args);    
    }

    @GetMapping("/employee")
    public String getEmployees(@RequestParam(value="id", defaultValue = "")String id) {
        try
        {
            if(!id.isEmpty()) {
                Employee employee = new DataParser().getEmployee(id);
                return JSON.toJSONObject(employee);
            }
            List<Employee> employeeList = new DataParser().getAllEmployees();
            return JSON.toJSONArray(employeeList);
        }catch (Exception ex) {
            throw new WebProjectException(ex.getMessage());
        }
    }
    
    @PutMapping("/employee")
    public String putEmployee(@RequestParam(value = "id", defaultValue = "")String id,
        @RequestParam(value = "firstName", defaultValue = "") String firstName,
        @RequestParam(value = "middleInitial", defaultValue = "")String middleInitial,
        @RequestParam(value = "lastName", defaultValue = "") String lastName,
        @RequestParam(value = "dateOfBirth", defaultValue = "")String dateOfBirth,
        @RequestParam(value = "dateOfEmployment", defaultValue = "") String dateOfEmployment) {
        
        String message = " ";
        try {
            message = new DataParser().updateEmployee(id, firstName, middleInitial, lastName, dateOfBirth, dateOfEmployment);
        }catch (Exception ex) {
            throw new WebProjectException(ex.getMessage());
        }
        return message;
    }
        
    @DeleteMapping("/employee")
    public String deleteEmployee(@RequestParam(value = "id", defaultValue = "false")String id) {
        String message = " ";
        try {
            message = new DataParser().deleteEmployee(id);
        } catch (Exception ex) {
            throw new WebProjectException(ex.getMessage());
        }
        return message;
    }
        
    @PostMapping("/employee")
     public String createEmployee(@RequestParam(value = "firstName", defaultValue = "") String firstName,
        @RequestParam(value = "middleInitial", defaultValue = "")String middleInitial,
        @RequestParam(value = "lastName", defaultValue = "") String lastName,
        @RequestParam(value = "dateOfBirth", defaultValue = "")String dateOfBirth,
        @RequestParam(value = "dateOfEmployment", defaultValue = "") String dateOfEmployment) {
        
        String message = " ";
        try {
            message = new DataParser().createEmployee(firstName, middleInitial, lastName, dateOfBirth, dateOfEmployment);
        } catch (Exception ex) {
            throw new WebProjectException(ex.getMessage());
        }
        return message;
    }
}
