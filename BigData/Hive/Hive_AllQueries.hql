CREATE EXTERNAL TABLE main2017DataDB (Duration Int,StartDate Date, EndDate Date, StartStationNum Int, StartStation String,EndStationNum Int,EndStation String, BikeNum Int, MemberType String) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' lines terminated by '\n' tblproperties("skip.header.line.count"="1") ;

LOAD DATA INPATH 'hdfs://localhost:9000/FinalProj_2017Input' OVERWRITE INTO TABLE main2017DataDB;

INSERT OVERWRITE DIRECTORY '/hive/Part1_RoutesmostTaken' 
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE
Select StartStation, EndStation, count(*) as RoutePopularity
from main2017DataDB
group by StartStation, EndStation
order by RoutePopularity desc
Limit 20;

INSERT OVERWRITE DIRECTORY '/hive/Part2_SummarizationAnalysis' 
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE
Select StartStation, Round(avg(Duration), 2)/(60*1000) as AverageDuration, round(stddev(Duration), 2)/(60*1000) as StandardDevDuration
from main2017DataDB
group by StartStation
order by AverageDuration desc
Limit 100;

INSERT OVERWRITE DIRECTORY '/hive/Part3_2017Comparison' 
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE
Select StartStation, count(*) as Counter
from main2017DataDB
group by StartStation
order by Counter desc
limit 10;

