/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part6.AverageNumberOfRidesByHour;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 *
 * @author gemi
 */
public class HourWritable implements  Writable{
    public static final Log log = LogFactory.getLog(HourWritable.class);
    private Text rideDate;
    private Text rideHour;
    
    public HourWritable() {
    }
    
    public HourWritable(Text rideDate, Text rideHour) {
        this.rideDate = rideDate;
        this.rideHour = rideHour;
    }

    public Text getRideDate() {
        return rideDate;
    }

    public void setRideDate(Text rideDate) {
        this.rideDate = rideDate;
    }

    public Text getRideHour() {
        return rideHour;
    }

    public void setRideHour(Text rideHour) {
        this.rideHour = rideHour;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        rideDate.write(out);
        rideHour.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        rideDate.readFields(in);
        rideHour.readFields(in);
    }

    public String theTimeOfDay(int hour){
        String[] hourNames = {"Morning (6-11am)", "Afternoon(11am-3pm)", "Evening(3pm-9pm)", "AfterHours(9pm-6am)"};
        if(hour == 6 || hour == 7 || hour == 8 || hour == 9 || hour == 10 ) {
            return hourNames[0];
        } else if(hour == 11 || hour == 12 || hour == 13 || hour == 14 || hour == 15 ) {
            return hourNames[1];
        } else if(hour == 16 || hour == 17 || hour == 18 || hour == 19 || hour == 20 || hour == 21 ) {
            log.info("theTimeOfDay: withing the HourWritable");
            return hourNames[2];
        } else {
            return hourNames[3];
        }
    }
    
    
    public Date getDateandHourObject(String date)
    {
        Date dateOut = null;
        try {
            
            dateOut =new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(date);

        } catch (Exception e) {
            
            try {
                Date date2=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);  

                
            } catch (Exception e1) {
                
                log.info("UNKNOWN date format:"+date);   
            }
        }
//        log.info(dateOut.toString());
        return dateOut;
    }
    
}
