/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part5.AmongRidesPopularStartLocation;

import Test.Part2.RiderKey;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 *
 * @author gemi
 */
public class KeySortComparator extends WritableComparator {
     public KeySortComparator() {
        super(SortByValueKey.class, true);
    }
     
    public int compare(WritableComparable a, WritableComparable b) {
        
        //a.compareTo(b);
        SortByValueKey k1 = (SortByValueKey) a;
        SortByValueKey k2 = (SortByValueKey) b;
        
        int result = -1*(k1.getCounter()- k2.getCounter());         
        if(result==0)
        {
            return k1.getStartLocation().compareTo(k2.getStartLocation());
        }        
        return result;  
    }
}
