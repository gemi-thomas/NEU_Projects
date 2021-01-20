/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part1.AverageRidesPerQuarter;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Used Writable
 * @author gemi
 */
public class Driver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();     
        FileSystem fs =FileSystem.get(conf);
        if(fs.exists(new Path(args[1]))) {
            fs.delete(new Path(args[1]), true);
        }   
        Job job = Job.getInstance(conf); 
        //job.setPartitionerClass(AverageRiderPerQPartitioner.class);        
        job.setMapperClass(AverageRiderPerQMapper.class);
        job.setReducerClass(AverageRiderPerQReducer.class);  
        job.setJarByClass(Driver.class);   
        job.setNumReduceTasks(1);    
        
        TextInputFormat.addInputPath(job, new Path(args[0]));
        TextOutputFormat.setOutputPath(job, new Path(args[1]));   
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);     
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);  
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
