/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Gemi
 */
public class Electronics extends Post{
    
    String brand;
    Set<String> uploadImage = new HashSet<String>();
    
    public void addElectronicsImage(String s){
        this.uploadImage.add(s);
    }

    public Set<String> getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(Set<String> uploadImage) {
        this.uploadImage = uploadImage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    
}
