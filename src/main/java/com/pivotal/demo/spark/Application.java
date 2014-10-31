package com.pivotal.demo.spark;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.pivotal.demo.spark.rocket.AnalyzeService;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner {
	private static final Logger log = Logger.getLogger(Application.class);

	@Value("${spring.profiles.active:spark}")
	private String profiles;

	@Value("${flight.id:0}")
	private int id;

	@Autowired
	private AnalyzeService aService;

	// Use the run method when the app is launched as a job on the cluster
	public void run(String... args) {
		if (profiles.indexOf("web") < 0) {
			log.warn("Web profile not declaired, so running as a command line application.\nParameters:");
			log.warn("\tflight.id (default 0)");
			log.warn("\tfile.directory (default 0)");
			log.warn("Analysis:\n" + aService.analyzeFlight(id).toString());
		}

		System.exit(0);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
