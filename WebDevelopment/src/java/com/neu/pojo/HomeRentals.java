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
public class HomeRentals extends Post{
    
    String price;
    Set<String> uploadImage = new HashSet<String>();
    
    public void addHomeRentalsImage(String s){
        this.uploadImage.add(s);
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Set<String> getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(Set<String> uploadImage) {
        this.uploadImage = uploadImage;
    }
    
}
