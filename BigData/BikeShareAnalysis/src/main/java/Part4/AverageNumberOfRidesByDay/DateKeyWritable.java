/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part4.AverageNumberOfRidesByDay;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

/**
 *
 * @author gemi
 */
public class DateKeyWritable implements WritableComparable<DateKeyWritable> {
    
    private Date dateObj;
    private Text rideDate;
    private Text day;

    public Date getDateObj() {
        return dateObj;
    }

    public void setDateObj(Date dateObj) {
        this.dateObj = dateObj;
    }
    
    public Text getRideDate() {
        return rideDate;
    }

    public void setRideDate(Text rideDate) {
        this.rideDate = rideDate;
    }

    public Text getDay() {
        return day;
    }

    public void setDay(Text day) {
        this.day = day;
    }

    public DateKeyWritable() {
    }

    public DateKeyWritable(Text rideDate, Text day) {
        this.rideDate = rideDate;
        this.day = day;
    }
    
    @Override
    public void write(DataOutput dataoutput) throws IOException {
       rideDate.write(dataoutput);
       day.write(dataoutput);
    }

    @Override
    public void readFields(DataInput datainput) throws IOException {
        rideDate.readFields(datainput);
        day.readFields(datainput);
    }
     public static final Log log = LogFactory.getLog(DateKeyWritable.class);
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
    
    public String theDay(int day){
        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return dayNames[day-1];
    }

    @Override
    public int compareTo(DateKeyWritable o) {
      return 0;
    }
    
}
