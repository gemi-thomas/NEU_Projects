/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part5.AmongRidesPopularStartLocation;

import Part4.AverageNumberOfRidesByDay.Driver;
import Part4.AverageNumberOfRidesByDay.SecondMapper;
import Part4.AverageNumberOfRidesByDay.SecondReducer;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.map.InverseMapper;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import Part5.AmongRidesPopularStartLocation.ChainFilterMapper1;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;
/**
 *
 * @author gemi
 */
public class ChainFilterDriver {
    
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        Configuration conf = new Configuration();    
        Job job = Job.getInstance(conf);
        job.setJarByClass(ChainFilterDriver.class); 
        
        FileSystem fs =FileSystem.get(conf);  
        if(fs.exists(new Path(args[1]))) {
            fs.delete(new Path(args[1]), true);
        }  
        
        Configuration mapConf1 = new Configuration(false);
        ChainMapper.addMapper(job, 
                ChainFilterMapper1.class , 
                LongWritable.class, 
                Text.class, 
                LongWritable.class, 
                Text.class, mapConf1);
        
        Configuration mapConf2 = new Configuration(false);
        ChainMapper.addMapper(job, 
                ChainFilterMapper2.class , 
                LongWritable.class, 
                Text.class, 
                Text.class, 
                IntWritable.class, mapConf2);
        
        Configuration reduceConf = new Configuration(false); 
        ChainReducer.setReducer(job, 
                ChainFilterReducer.class, 
                Text.class, IntWritable.class,
                 Text.class, IntWritable.class,
                reduceConf);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        TextInputFormat.addInputPath(job, new Path(args[0]));
        TextOutputFormat.setOutputPath(job, new Path(args[1]));
        boolean jobResult = job.waitForCompletion(true);
        
         if(jobResult) {
            Job job1 = Job.getInstance(conf);
            job1.setJarByClass(ChainFilterDriver.class);
            job1.setMapperClass(ChainSorterMapper3.class);   
            job1.setSortComparatorClass(KeySortComparator.class);
            job1.setOutputKeyClass(SortByValueKey.class);
            job1.setOutputValueClass(NullWritable.class);            
            TextInputFormat.addInputPath(job1, new Path(args[1]));
            TextOutputFormat.setOutputPath(job1, new Path(args[2])); 
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("--------------------------");
            System.out.println("TimeElapsed: "+timeElapsed);
            job1.waitForCompletion(true);
        }
    }
}
