/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part1.AverageRidesPerQuarter;

import static Part1.AverageRidesPerQuarter.AverageRiderPerQMapper.log;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
/**
 *
 * @author gemi
 */
public class RiderWritable implements Writable {
   
    private int totalduration;
    private Date startDate;
    private int starthour;
    private String startLocation;
    private Date endDate;
    private int endhour;
    private String destinationLocation;
    private String bikeNumber;
    private String userType;
    private String mSeason;
    
    public static final Log log = LogFactory.getLog(RiderWritable.class);
    
    public int getStarthour() {
        return starthour;
    }

    public void setStarthour(int starthour) {
        this.starthour = starthour;
    }

    public int getEndhour() {
        return endhour;
    }

    public void setEndhour(int endhour) {
        this.endhour = endhour;
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

    public RiderWritable() {
        
    }
    
    public void createRiderObject(String input) {
        
        String[] eachLine = input.split(",");
        Date startDateTemp = getDateObject(eachLine[1]);
        
        if(startDateTemp == null)
            return;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDateTemp);
//        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
//        log.info("month number:  "+month);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        log.info("hour number:  "+hour);
        
        setStartDate(startDateTemp);
        setStarthour(hour);
        ConvertMonthToSeason(month);
        
        if(eachLine.length == 7)
        {
            String userType = eachLine[6];    
            if(userType !=null && userType.equalsIgnoreCase("Casual"))
                setUserType("Casual");
            else
                setUserType("Registered");



            String destinationLoc = eachLine[4];
            setDestinationLocation(destinationLoc);

            Date endDateTemp = getDateObject(eachLine[3]);

            if(endDateTemp == null)
                return;

            String startLoc = eachLine[2];
            setStartLocation(startLoc);

            setEndDate(endDateTemp);
            setEndhour(endDateTemp.getHours());


            String bkNum = eachLine[5];
            setBikeNumber(bkNum);
            
        }else
        {
             String userType = eachLine[8];
        
            if(userType !=null && userType.equalsIgnoreCase("Casual"))
                setUserType("Casual");
            else
                setUserType("Registered");



            String destinationLoc = eachLine[6];
            setDestinationLocation(destinationLoc);

            Date endDateTemp = getDateObject(eachLine[2]);

            if(endDateTemp == null)
                return;

            String startLoc = eachLine[4];
            setStartLocation(startLoc);

            setEndDate(endDateTemp);
            setEndhour(endDateTemp.getHours());


            String bkNum = eachLine[7];
            setBikeNumber(bkNum);
        }
        
       
    }
    
    public String ConvertMonthToSeason(int month) {
        
        switch (month) {
            case 11:
            case 0:
            case 1:
                mSeason = "Winter"            ;
                break;
            case 2:
            case 3:
            case 4:
                mSeason = "Spring";
                break;
            case 5:           
            case 6:
            case 7:
                mSeason = "Summer";
                break;
            default:
                mSeason = "Fall";
                break;                
        }
        return mSeason;
    }
    
     public String getSeason() {
        return mSeason;
    }

    public void setSeason(String season) {
        this.mSeason = season;
    }
    
    public int getTotalduration() {
        return totalduration;
    }

    public void setTotalduration(int totalduration) {
        this.totalduration = totalduration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getBikeNumber() {
        return bikeNumber;
    }

    public void setBikeNumber(String bikeNumber) {
        this.bikeNumber = bikeNumber;
    }

    public String getUserType() {
            return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    @Override
    public void write(DataOutput arg0) throws IOException {
      
    }

    @Override
    public void readFields(DataInput arg0) throws IOException {
       
    }    
}
