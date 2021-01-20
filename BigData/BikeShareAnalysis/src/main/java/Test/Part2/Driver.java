/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.Part2;

import Part2.AverageTImeofUsagePerMonth.*;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Used Writable, Combiner 
 * @author gemi
 */
public class Driver {
    
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        Configuration conf = new Configuration();     
        FileSystem fs =FileSystem.get(conf);
        if(fs.exists(new Path(args[1]))) {
            fs.delete(new Path(args[1]), true);
        }   
        Job job = Job.getInstance(conf); 
        
        job.setMapperClass(AvgTimeofUsagePerMonthMapper.class);
        job.setCombinerClass(AvgTimeofUsagePerMonthReducer.class);
        job.setReducerClass(AvgTimeofUsagePerMonthReducer.class);  
        job.setJarByClass(Driver.class);   
//        job.setGroupingComparatorClass(KeySortGroupComparator.class);
        job.setSortComparatorClass(KeySortComparator.class);
        job.setNumReduceTasks(1);    
        
        TextInputFormat.addInputPath(job, new Path(args[0]));
        TextOutputFormat.setOutputPath(job, new Path(args[1]));   
        job.setMapOutputKeyClass(RiderKey.class);
        job.setMapOutputValueClass(RiderWritable.class);     
        job.setOutputKeyClass(RiderKey.class);
        job.setOutputValueClass(RiderWritable.class);  
        
        
        boolean result = job.waitForCompletion(true);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("--------------------------");
        System.out.println("TimeElapsed: "+timeElapsed);
        System.exit(result ? 0:1);
    }
}
