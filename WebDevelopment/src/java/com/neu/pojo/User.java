/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Gemi
 */
public class User {
    
    Integer userID;
    String email;
    String username;
    String location;
    String password;
    String phoneNum;
    
    private Set<Books> books = new HashSet<>(); 
    private Set<Furniture> furniture = new HashSet<>(); 
    private Set<Electronics> electronics = new HashSet<>(); 
    private Set<HomeRentals> homerentals = new HashSet<>(); 
    
    public void addBook(Books b){
        this.books.add(b);
    }
    
    public void addElectronics(Electronics e){
        this.electronics.add(e);
    }
    public void addFurniture(Furniture f){
        this.furniture.add(f);
    }
    public void addHomeRental(HomeRentals h){
        this.homerentals.add(h);
    }

    public Set<Books> getBooks() {
        return books;
    }

    public void setBooks(Set<Books> books) {
        this.books = books;
    }

    public Set<Furniture> getFurniture() {
        return furniture;
    }

    public void setFurniture(Set<Furniture> furniture) {
        this.furniture = furniture;
    }

    public Set<Electronics> getElectronics() {
        return electronics;
    }

    public void setElectronics(Set<Electronics> electronics) {
        this.electronics = electronics;
    }

    public Set<HomeRentals> getHomerentals() {
        return homerentals;
    }

    public void setHomerentals(Set<HomeRentals> homerentals) {
        this.homerentals = homerentals;
    }
    
    
    public Integer getUserID() {
        return userID;
    }

    // EACH USER will have associated several POSTS
    public void setUserID(Integer userID) {    
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public void deleteBook(String address) {
        for (Iterator<Books> iterator = books.iterator(); iterator.hasNext();) {
            Books b =  iterator.next();
            if (b.toString().equals(address)) {
                iterator.remove();
            }       
        }
    }
    
    public void deleteElectronics(String address) {
        for (Iterator<Electronics> iterator = electronics.iterator(); iterator.hasNext();) {
            Electronics b =  iterator.next();
            if (b.toString().equals(address)) {
                iterator.remove();
            }       
        }
    }
    
    public void deleteFurniture(String address) {
        for (Iterator<Furniture> iterator = furniture.iterator(); iterator.hasNext();) {
            Furniture b =  iterator.next();
            if (b.toString().equals(address)) {
                iterator.remove();
            }       
        }
    }
    
    public void deleteHomeRental(String address) {
        for (Iterator<HomeRentals> iterator = homerentals.iterator(); iterator.hasNext();) {
            HomeRentals b =  iterator.next();
            if (b.toString().equals(address)) {
                iterator.remove();
            }       
        }
    }      
    
}
