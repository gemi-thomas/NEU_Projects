/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kenzan.WebProject.Data;

/**
 *
 * @author Gemi
 */
public class DataStorageFactory {
    
    public AbstractDataStorage getDataStorageType(String type) {
        
        if(type.equals("XML")) {
            return new XMLDataStore();
        }
        /*
        else if (type.equals("MySQL"))
            return new MySQLDataStore();
        */
        return null;
    }
}
