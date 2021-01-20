/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.Part2;

import org.apache.hadoop.io.WritableComparator;

/**
 *
 * @author gemi
 */
public class KeySortGroupComparator extends WritableComparator{
    
    public KeySortGroupComparator() {
//        super(RiderKey.class, true);
    }
    
    @Override
    public int compare(Object a, Object b)
    {
        RiderKey rk1 = (RiderKey)a;
        RiderKey rk2 = (RiderKey)b;       
        
        if (rk1.getmMonth() == null || rk2.getmMonth() == null)
            return 0;
        
        return rk1.getMvalue()- rk2.getMvalue();
    }
}
