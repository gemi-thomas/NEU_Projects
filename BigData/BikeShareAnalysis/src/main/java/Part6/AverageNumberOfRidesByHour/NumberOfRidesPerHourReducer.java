/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part6.AverageNumberOfRidesByHour;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author gemi
 */
public class NumberOfRidesPerHourReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
    
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for(IntWritable val : values) {
            count += val.get();
        }
        context.write(key, new IntWritable(count));
    }
}
