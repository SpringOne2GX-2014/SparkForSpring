SparkForSpring
==============

This is a somewhat cleaned up version of the code I presented at SpringOne2014 to show
how Spark can be used in Spring apps.  It's based on an app I'm writing to do real time
and processing of a data stream coming from a high powered model rocket.  The goal of this
sample is Spring and Spark, so most of the rocket code was eliminated.

What is left is a somewhat generic Spring Boot application.  The app will run with a webui for 
local experimentation, or as a standalone app for running on a cluster.  The behavior is 
controlled by the spring.profiles.active property.  For running in eclipse/STS I typically
use the webui, and the local data files in the project.  (The default is to use HDFS.)

In the run profile pass these parameters
--spring.profiles.active=webui --file.directory=src/main/resources/data/

For a simple run profile that just processes one of the data files pass
--file.directory=src/main/resources/data/ --flight.id=1 (or 0)

Deploy Spark Cluster
====================

I build spark from source (currently 1.1.0), and create a distribution.  From there
I configure it (set names for slaves and point to specific JDK 1.8), tgz it, and scp
it to my virtual servers (master, client, and 3 workers).