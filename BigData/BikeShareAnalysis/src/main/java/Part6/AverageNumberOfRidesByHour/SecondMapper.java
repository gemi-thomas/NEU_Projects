/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part6.AverageNumberOfRidesByHour;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gemi
 */
public class SecondMapper extends Mapper<LongWritable, Text,Text,IntWritable>{
    
    protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException, IOException {
        
        String input = value.toString();   
        String[] eachLine = input.split(",");
        
        String secondCol = eachLine[1];
        String[] secondColArr = secondCol.split("\t");
        
        String hourOfDay = secondColArr[0];
        int x = Integer.parseInt(secondColArr[1]);
        
        context.write(new Text(hourOfDay), new IntWritable(x));
    }
}
