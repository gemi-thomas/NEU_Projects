/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.data;

import com.neu.pojo.Books;
import com.neu.pojo.Electronics;
import com.neu.pojo.Furniture;
import com.neu.pojo.HomeRentals;
import com.neu.pojo.Post;
import com.neu.pojo.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
/**
 *
 * @author Gemi
 */
public class UserDAO extends DAO{
    
    //Every subclass of DAO will have the operations that will be performed on the POJO(save, get, delete, update)
    //The main runtime interface between a Java application and Hibernate
    
    //SAVE USER ACCOUNT DETAILS
    public User saveUserAccount(User u) {
        begin();

        //Hibernate doesnot auto commit transactions. So we need to explicitly commit. 
        //Otherwise the object will not be saved (Will be rolled back)
        try{
        getSession().save(u);
        commit();
        close();
        }catch(HibernateException e)
        {
            close();
        }
        return u;
    } 
          

    //Return User for LOGIN
    public User userValidCheck(String username, String password) {
        begin();
        
        Query q = getSession().createQuery("from User where username = :username and password = :password");
        q.setString("username", username);
        q.setString("password", password);
        User u = (User) q.uniqueResult();
        //commit();
        close();
        return u;
    }
    
    public User updateUserAccount(User user) throws Exception {
        try{
        begin();
        getSession().update(user);
        commit();
        close();
        } catch (HibernateException e) {
            rollback();
            //throw new Exception("Could not list the categories " + e.toString(), e);
        }
        
        return user;
    }
    
    public List<Books> getBooks(){
            begin();
            Query q = getSession().createQuery("from Books");
            //Can add criterion(WHERE conditions)
            List<Books> list = q.list();
            //commit();
            close();
            return list;
            
//        } catch (HibernateException e) {
//            rollback();
//            throw new Exception("Could not list the categories", e);
//        }
    }
    
    public List<Books> getBooks(int pgId, int numtoDisplay){
            begin();
            Query q = getSession().createQuery("from Books");
            q.setFirstResult((pgId-1)*numtoDisplay);
            q.setMaxResults(numtoDisplay);
            //Can add criterion(WHERE conditions)
            List<Books> list = q.list();
           // commit();
            close();
            return list;
            
//        } catch (HibernateException e) {
//            rollback();
//            throw new Exception("Could not list the categories", e);
//        }
    }
    
    public List<Electronics> getElectronics(){
            begin();
            Query q = getSession().createQuery("from Electronics");
            List<Electronics> list = q.list();
            //commit();
            close();
            return list;
    }
    
    public List<Electronics> getElectronics(int pgId, int numtoDisplay){
            begin();
            Query q = getSession().createQuery("from Electronics");
            q.setFirstResult((pgId-1)*numtoDisplay);
            q.setMaxResults(numtoDisplay);
            //Can add criterion(WHERE conditions)
            List<Electronics> list = q.list();
            //commit();
            close();
            return list;
            
//        } catch (HibernateException e) {
//            rollback();
//            throw new Exception("Could not list the categories", e);
//        }
    }
    
    public List<Furniture> getFurniture(){
            begin();
            Query q = getSession().createQuery("from Furniture");
            List<Furniture> list = q.list();
            //commit();
            close();
            return list;
    }
    
    public List<Furniture> getFurniture(int pgId, int numtoDisplay){
            begin();
            Query q = getSession().createQuery("from Furniture");
            q.setFirstResult((pgId-1)*numtoDisplay);
            q.setMaxResults(numtoDisplay);
           
            List<Furniture> list = q.list();
            //commit();
            close();
            return list;
            
//        } catch (HibernateException e) {
//            rollback();
//            throw new Exception("Could not list the categories", e);
//        }
    }
    
    public List<HomeRentals> getHomeRentals(){
            begin();
            Query q = getSession().createQuery("from HomeRentals");
            List<HomeRentals> list = q.list();
            //commit();
            close();
            return list;
    }
    
    public List<HomeRentals> getHomeRentals(int pgId, int numtoDisplay){
            begin();
            Query q = getSession().createQuery("from HomeRentals");
            q.setFirstResult((pgId-1)*numtoDisplay);
            q.setMaxResults(numtoDisplay);
           
            List<HomeRentals> list = q.list();
            //commit();
            close();
            return list;
            
//        } catch (HibernateException e) {
//            rollback();
//            throw new Exception("Could not list the categories", e);
//        }
    }
    
    
    //Search By keyword - TBD
    
    //List of Users
    public List<User> getAllUsers(){
        
        begin();
        Query q = getSession().createQuery("from User");
        List<User> userList = q.list();
        //commit();
        close();
        return userList;
    }
    
    //TO be used for deleting a post
    List<Post> postList = new ArrayList<>();
    public List<Post> getMyPost(int userID){
            //begin();
            Query q1 = getSession().createQuery("from Books where userID = :userID");
            q1.setInteger("userID", userID);
            List<Books> blist = q1.list();
            Query q2 = getSession().createQuery("from Electronics where userID = :userID");
            q2.setInteger("userID", userID);
            List<Electronics> elist = q2.list();
            //ADD HOME RENTALS, FURNITURE
            
            for(Post p:blist)
            {
                postList.add(p);
            }
            for(Post p:elist)
            {
                postList.add(p);
            }
            //commit();
            //close();
            return postList;
    }
    
    //View each Post
    public Books getSingleBook(String postID, String title)
    {
        begin();
        Query q1 = getSession().createQuery("from Books where postID = :postID and title =: title");
        q1.setString("postID", postID);
        q1.setString("title", title);
        Books b = (Books) q1.getSingleResult();
        //commit();
        close();
        return b;
    }
    
    public Electronics getSingleElectronic(String postID, String title)
    {
        begin();
        Query q1 = getSession().createQuery("from Electronics where postID = :postID and title =: title");
        q1.setString("postID", postID);
        q1.setString("title", title);
        Electronics b = (Electronics) q1.getSingleResult();
        //commit();
        close();
        return b;
    }
    
    public Furniture getSingleFurniture(String postID, String title)
    {
        begin();
        Query q1 = getSession().createQuery("from Furniture where postID = :postID and title =: title");
        q1.setString("postID", postID);
        q1.setString("title", title);
        Furniture b = (Furniture) q1.getSingleResult();
        //commit();
        close();
        return b;
    }
    
    public HomeRentals getSingleHomeRentals(String postID, String title)
    {
        begin();
        Query q1 = getSession().createQuery("from HomeRentals where postID = :postID and title =: title");
        q1.setString("postID", postID);
        q1.setString("title", title);
        HomeRentals b = (HomeRentals) q1.getSingleResult();
        //commit();
        close();
        return b;
    }
    
    public User deleteUserAccount(User m) {
        
        begin();
        getSession().delete(m);
        commit();
        close();
        return m;
    }

    public String getUserDetails(String type, String postid) {        
        
        begin();
        //String query = "SELECT u FROM  User u, " + type +" b where  b.postID = " + postid;
        String query = "SELECT u FROM  User u join u."+ type  +" b where  b.postID = " + postid;
        Query q1 = getSession().createQuery(query);
        List<User> elist = q1.list();
        //User u = (User) q1.getSingleResult();
        //commit();
        close();
        return elist.get(0).getEmail();        
    }
}
