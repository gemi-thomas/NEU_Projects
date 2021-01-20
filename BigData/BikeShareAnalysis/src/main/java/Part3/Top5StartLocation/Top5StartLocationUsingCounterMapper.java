/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part3.Top5StartLocation;

import java.io.IOException;
import java.util.Date;
import java.util.TreeMap;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gemi
 */
public class Top5StartLocationUsingCounterMapper  extends Mapper<LongWritable, Text, Text, IntWritable> {
    
    public static final String START_LOCATION = "StartLocation";    
        
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
        String[] tokens = value.toString().split(",");    
         String startLocation = "";
         IntWritable one = new IntWritable(1);
         if(tokens.length == 7) {
            startLocation = tokens[2];  
        } else {          
            startLocation = tokens[4];
        }
        
        if(!startLocation.isEmpty())
        {   
            context.getCounter(START_LOCATION, startLocation).increment(1);
        }
        
    }
}
