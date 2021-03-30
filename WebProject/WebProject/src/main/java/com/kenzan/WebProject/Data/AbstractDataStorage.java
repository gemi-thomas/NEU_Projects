/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kenzan.WebProject.Data;

import com.kenzan.WebProject.Pojo.Employee;
import java.util.List;

/**
 *
 * @author Gemi
 * This abstract class specifies all the methods that different data storage types 
 * should implement. For now this is extended only for XML, but can be easily 
 * extended to other storage or ORM
 */
public abstract class AbstractDataStorage {
    
    abstract List<Employee> getAllEmployees() throws Exception;
    abstract Employee getEmployee(String id) throws Exception;
    abstract String updateEmployee(String id, String firstName,String middleInitial, 
            String lastName, String dateOfBirth,String dateOfEmployment) throws Exception;
    abstract String deleteEmployee(String id) throws Exception;
    abstract String createEmployee(String firstName,String middleInitial, 
            String lastName, String dateOfBirth, String dateOfEmployment) throws Exception;
}
