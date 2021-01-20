/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part5.AmongRidesPopularStartLocation;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gemi
 */
public class ChainFilterMapper1 extends Mapper<LongWritable,Text,LongWritable,Text> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        String input = value.toString();
        String[] eachLine = input.split(",");
        float timeduration;
        try {
            timeduration = Float.parseFloat(eachLine[0])/(1000*60);
        } catch (Exception e) {
            return;
        }
        
        if(timeduration > 180) {
            context.write(key, value);
        }  
    }
}
