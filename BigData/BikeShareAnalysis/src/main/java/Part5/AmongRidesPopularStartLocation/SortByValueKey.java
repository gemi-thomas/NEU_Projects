/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part5.AmongRidesPopularStartLocation;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.WritableComparable;

/**
 *
 * @author gemi
 */
public class SortByValueKey implements WritableComparable<SortByValueKey> {
    public static final Log log = LogFactory.getLog(SortByValueKey.class);
    private String startLocation;
    private int counter;

    public SortByValueKey(String startLocation, int counter) {
        this.startLocation = startLocation;
        this.counter = counter;
    }
    
    public SortByValueKey() {
    }
    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(startLocation);
        out.writeInt(counter);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        startLocation = in.readUTF();
        counter = in.readInt();
    }

    @Override
    public int compareTo(SortByValueKey obj) {
        log.info("Compared is being called");
        int result = -1*(this.counter - obj.getCounter());
        if(result == 0) {
            return this.startLocation.compareTo(obj.getStartLocation());
        }
        return result;
    }
    
       @Override
    public String toString() {
        return startLocation+", "+counter;
    }
}
