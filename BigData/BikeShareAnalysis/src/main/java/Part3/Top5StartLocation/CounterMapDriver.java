/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part3.Top5StartLocation;

import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 *
 * @author gemi
 */
public class CounterMapDriver {
    public static final Log log = LogFactory.getLog(CounterMapDriver.class);
    public static class TopLocation implements Comparable<TopLocation>{
        private long mCount;
        private String locationName;

        @Override
        public int compareTo(TopLocation obj) {
          int result = (int) ((this.mCount - obj.getmCount()));
          
          if (result == 0)
          {
              return this.locationName.compareTo(obj.getLocationName());
          }
          
          return result;
        }

        public TopLocation() {
        }

        public TopLocation(long mCount, String locationName) {
            this.mCount = mCount;
            this.locationName = locationName;
        }

        public long getmCount() {
            return mCount;
        }

        public String getLocationName() {
            return locationName;
        }
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
      long start = System.currentTimeMillis();
      Configuration conf = new Configuration();        
      Job job = Job.getInstance(conf);
      job.setJarByClass(CounterMapDriver.class);        
      job.setMapperClass(Top5StartLocationUsingCounterMapper.class);  

      job.setNumReduceTasks(1);  

      FileInputFormat.addInputPath(job, new Path(args[0])); 
      TextOutputFormat.setOutputPath(job, new Path(args[1])); 
      job.setMapOutputKeyClass(Text.class);
      job.setMapOutputValueClass(IntWritable.class);  

      int code = job.waitForCompletion(true) ? 0 : 1;    
      PriorityQueue<TopLocation> pq = new PriorityQueue<TopLocation>(10);
      if(code==0)
      {   
          for(Counter counter: job.getCounters().getGroup(Top5StartLocationUsingCounterMapper.START_LOCATION)){
              TopLocation obj = new TopLocation(counter.getValue(), counter.getDisplayName());
              pq.add(obj); 
              if(pq.size() > 10) 
                  pq.poll();
          }
      }
     
       FileSystem hdfs = FileSystem.get(conf);
       
       Path hdfsFile = new Path(args[1]);
       
       if(!hdfs.exists(hdfsFile)) {
          
           hdfs.createNewFile(hdfsFile);
            
       } else
       {
          
           hdfs.delete(hdfsFile, true);
       }
      try {
            FSDataOutputStream out = hdfs.create(hdfsFile,true);
            
            
            Iterator value = pq.iterator(); 
            while (pq.size() > 0)
            {
                TopLocation obj = (TopLocation) pq.poll();
                String outputValue = obj.getLocationName()+", "+obj.getmCount()+"\n";

                out.writeBytes(outputValue);
            }

            out.close();
        } catch (Exception e) {
           
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("--------------------------");
        System.out.println("TimeElapsed: "+timeElapsed);
      System.exit(code);
  }
}
