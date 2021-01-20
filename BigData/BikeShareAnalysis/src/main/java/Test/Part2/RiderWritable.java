/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.Part2;


import Part2.AverageTImeofUsagePerMonth.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 *
 * @author gemi
 */
public class RiderWritable implements Writable {
    
    private IntWritable mCount;
    private FloatWritable mTimeDuration;
    private Text mMonth;

    public Text getmMonth() {
        return mMonth;
    }

    public void setmMonth(Text mMonth) {
        this.mMonth = mMonth;
    }

    public IntWritable getmCount() {
        return mCount;
    }

    public void setmCount(IntWritable mCount) {
        this.mCount = mCount;
    }

    public FloatWritable getmTimeDuration() {
        return mTimeDuration;
    }

    public void setmTimeDuration(FloatWritable mTimeDuration) {
        this.mTimeDuration = mTimeDuration;
    }
   
    public static final Log log = LogFactory.getLog(RiderWritable.class);
    
    public RiderWritable() {
        mCount = new IntWritable();
        mTimeDuration = new FloatWritable();
        mMonth = new Text();
    }
    
    @Override
    public void write(DataOutput dataoutput) throws IOException {
        mCount.write(dataoutput);
        mTimeDuration.write(dataoutput);
        mMonth.write(dataoutput);
    }

    @Override
    public void readFields(DataInput datainput) throws IOException {
        mCount.readFields(datainput);
        mTimeDuration.readFields(datainput);
        mMonth.readFields(datainput);
    }
    
    public Date getDateObject(String date)
    {
        Date dateOut = null;
        try {
            
            dateOut =new SimpleDateFormat("MM/dd/yyyy").parse(date);

        } catch (Exception e) {
            
            try {
                Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(date);  

                
            } catch (Exception e1) {
                
                log.info("UNKNOWN date format:"+date);   
            }
        }
//        log.info(dateOut.toString());
        return dateOut;
    }
       
    public String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
    
    @Override
    public String toString() {
        return ", Average_Time_Duration: " + mTimeDuration.get();
    }  
}
