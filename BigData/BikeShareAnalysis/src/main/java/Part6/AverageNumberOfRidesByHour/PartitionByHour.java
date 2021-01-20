/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part6.AverageNumberOfRidesByHour;

import static Part6.AverageNumberOfRidesByHour.HourWritable.log;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 *
 * @author gemi
 */
public class PartitionByHour extends Partitioner<Text, IntWritable> {

    public int getPartition(Text key, IntWritable val, int numPartitions) {
        
      String input = key.toString();   
      String[] eachLine = input.split(",");
      String hourOfDayStr = eachLine[1].trim();
      
      if(hourOfDayStr.equals("Morning (6-11am)")) {
           return 1;
        }
        else if(hourOfDayStr.equals("Afternoon(11am-3pm)")) {
            log.info("theTimeOfDay: withing the PartitionByHour");
           return 2 % numPartitions;
        }
        else if(hourOfDayStr.equals("Evening(3pm-9pm)")) {
           log.info("theTimeOfDay: withing the PartitionByHour");
           return 3 % numPartitions;
        } 
        else {
            return 4 % numPartitions;
        }
    }
    
}
