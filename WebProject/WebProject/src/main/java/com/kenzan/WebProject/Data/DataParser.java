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
 * This is the parser class which makes calls to the Data storage on behalf of
 * main Rest API's
 * This function can be modified to change storage type on run time
 */
public class DataParser {
    
    private AbstractDataStorage dataStorageType;
    public DataParser() {
        DataStorageFactory factoryObject = new DataStorageFactory();
        String dataStorageTypeStr = "XML"; //We can read this from a property object at runtime 
        dataStorageType = factoryObject.getDataStorageType(dataStorageTypeStr);
    }
    
    public List<Employee> getAllEmployees() throws Exception {
        return dataStorageType.getAllEmployees();
    }
    public Employee getEmployee(String id) throws Exception {
        return dataStorageType.getEmployee(id);
    }
    public String updateEmployee(String id, String firstName,String middleInitial, 
            String lastName, String dateOfBirth,String dateOfEmployment) throws Exception {
        return dataStorageType.updateEmployee(id, firstName, middleInitial, lastName, dateOfBirth, dateOfEmployment);
    }
    public String deleteEmployee(String id) throws Exception {
        return dataStorageType.deleteEmployee(id);
    }
    public  String createEmployee(String firstName,String middleInitial, 
            String lastName, String dateOfBirth, String dateOfEmployment) throws Exception {
        return dataStorageType.createEmployee(firstName, middleInitial, lastName, dateOfBirth, dateOfEmployment);
    }
}
