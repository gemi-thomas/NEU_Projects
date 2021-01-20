/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.Part2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 *
 * @author gemi
 */
public class RiderKey implements WritableComparable<RiderKey> {

    private String mMonth;
    private int mvalue;

    public int getMvalue() {
        return mvalue;
    }

    public void setMvalue(int mvalue) {
        this.mvalue = mvalue;
    }

    public RiderKey(String mMonth, int mvalue) {
        super();
        this.mMonth = mMonth;
        this.mvalue = mvalue;
    }

    public String getmMonth() {
        return mMonth;
    }

    public void setmMonth(String mMonth) {
        this.mMonth = mMonth;
    }


    public RiderKey() {
        super();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(mMonth);
        out.writeInt(mvalue);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        mMonth = in.readUTF();
        mvalue = in.readInt();
    }

    @Override
    public int compareTo(RiderKey obj) {
        
        return this.mvalue - obj.getMvalue();
    }

    @Override
    public String toString() {
        return mMonth;
    }

}
