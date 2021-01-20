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
public class Furniture extends Post{
    
    Set<String> uploadImage = new HashSet<String>();
    
    public void addFurnitureImage(String s){
        this.uploadImage.add(s);
    }

    public Set<String> getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(Set<String> uploadImage) {
        this.uploadImage = uploadImage;
    }
    
    
}
