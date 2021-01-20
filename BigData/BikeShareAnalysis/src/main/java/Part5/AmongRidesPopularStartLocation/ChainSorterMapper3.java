/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part5.AmongRidesPopularStartLocation;

import Part1.AverageRidesPerQuarter.AverageRiderPerQMapper;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gemi
 */
public class ChainSorterMapper3 extends Mapper<LongWritable, Text, SortByValueKey, NullWritable> {

    public static final Log log = LogFactory.getLog(ChainSorterMapper3.class);
     public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        String input = value.toString();
         String[] eachLine = input.split(":");
         SortByValueKey obj = new SortByValueKey();
         obj.setStartLocation(eachLine[0]);
         String totatStr = eachLine[1];
         totatStr = totatStr.replaceAll("\\s", ""); 
         int total = Integer.parseInt(totatStr);
         obj.setCounter(total);
         log.info("key at ChainSorterMapper3" + eachLine[0]);
         log.info("value at ChainSorterMapper3" + eachLine[1]);
         
         context.write(obj, NullWritable.get());
     }
}
