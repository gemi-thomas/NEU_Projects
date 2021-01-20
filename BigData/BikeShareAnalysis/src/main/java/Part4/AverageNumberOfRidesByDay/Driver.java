/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part4.AverageNumberOfRidesByDay;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
  

/**
 *
 * @author gemi
 */
public class Driver {
    
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();    
        Job job = Job.getInstance(conf);
        
        FileSystem fs =FileSystem.get(conf);
        if(fs.exists(new Path(args[1]))) {
            fs.delete(new Path(args[1]), true);
        }   
        if(fs.exists(new Path(args[2]))) {
            fs.delete(new Path(args[2]), true);
        }  
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);  
 
        job.setMapperClass(NumberOfRidesPerDayMapper.class);
        job.setReducerClass(NumberOfRidesPerDayReducer.class);  
        job.setJarByClass(Driver.class);   
        job.setNumReduceTasks(1);    
        
        TextInputFormat.addInputPath(job, new Path(args[0]));
        TextOutputFormat.setOutputPath(job, new Path(args[1]));   
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);     

        boolean result = job.waitForCompletion(true);
        
        if(result) {
            Job job1 = Job.getInstance(conf);
            job1.setJarByClass(Driver.class);
            job1.setMapperClass(SecondMapper.class);
            job1.setReducerClass(SecondReducer.class);          
            job1.setOutputKeyClass(Text.class);
            job1.setOutputValueClass(IntWritable.class);            
            TextInputFormat.addInputPath(job1, new Path(args[1]));
            TextOutputFormat.setOutputPath(job1, new Path(args[2]));            
            job1.waitForCompletion(true);
        }
        
    }
}
