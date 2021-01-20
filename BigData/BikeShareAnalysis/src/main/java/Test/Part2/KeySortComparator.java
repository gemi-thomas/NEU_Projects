/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.Part2;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import Test.Part2.RiderKey;

/**
 *
 * @author gemi
 */
public class KeySortComparator extends WritableComparator{ 
     
    public KeySortComparator() {
        super(RiderKey.class, true);
    }
    
    public int compare(WritableComparable a, WritableComparable b) {
        
        //a.compareTo(b);
        RiderKey rk1 = (RiderKey) a;
        RiderKey rk2 = (RiderKey) b;
        
        int result = rk1.getMvalue()- rk2.getMvalue();         
//        if(result==0)
//        {
//            return -1*ck1.getBikeid().compareTo(ck2.getBikeid());
//        }        
        return result;  
    }
}
