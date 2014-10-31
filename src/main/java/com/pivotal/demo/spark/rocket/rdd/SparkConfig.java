package com.pivotal.demo.spark.rocket.rdd;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("spark")
@Configuration
public class SparkConfig
{
    private static final Logger LOGGER = Logger.getLogger(SparkConfig.class);

    @Value("${spark.master:local}")
    private String master;

	@Bean
    public JavaSparkContext javaSparkContext()
    {
		LOGGER.info("Creating SparkContext.  Master="+master);
		SparkConf conf = new SparkConf().setAppName("SparkForSpring")
		                                .setMaster(master)
		                                .set("spark.executor.memory", "128m")
		                                .set("spark.cores.max", "1")
		                                .set("spark.default.parallelism", "3");

		return new JavaSparkContext(conf);
    }
}
