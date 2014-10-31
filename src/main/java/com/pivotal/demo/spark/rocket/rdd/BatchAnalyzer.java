package com.pivotal.demo.spark.rocket.rdd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import scala.Tuple2;

import com.pivotal.demo.spark.rocket.AnalyzeService;
import com.pivotal.demo.spark.rocket.FlightService;
import com.pivotal.demo.spark.rocket.dom.Flight;
import com.pivotal.demo.spark.rocket.dom.NMEA;
import com.pivotal.demo.spark.rocket.dom.Telemetry;
import com.pivotal.demo.spark.rocket.util.DOMUtil;

@Service
public class BatchAnalyzer implements AnalyzeService {
	private static final Logger log = Logger.getLogger(BatchAnalyzer.class);

	@Value("${file.directory}")
	private String directory;

	private final JavaSparkContext sc;
	private final FlightService fs;

	@Autowired
	public BatchAnalyzer(JavaSparkContext sc, FlightService fs) {
		this.sc = sc;
		this.fs = fs;
	}

	@Override
	public Map<String, Integer> analyzeFlight(int id) {
		log.info("Analyzing flight: " + id);

		Map<String, Integer> retVal = null;
		Flight f = fs.getFlight(id);

		// Load the lines and cache them to do multiple processing runs
		JavaRDD<String> lines = sc.textFile(directory + f.getFileName())
				.cache();

		retVal = gpsCount(lines);
		retVal.putAll(telemetryCount(lines));

		return retVal;
	}

	private Map<String, Integer> gpsCount(JavaRDD<String> lines) {
		HashMap<String, Integer> results = new HashMap<String, Integer>();

		// Find the NMEA objects
		JavaRDD<NMEA> gpsLines = lines.filter(line -> line.startsWith("$"))
				.map(line -> DOMUtil.createNMEAObject(line));

		// Simple count function
		JavaPairRDD<String, Integer> counts = gpsLines.mapToPair(
				nmea -> new Tuple2<String, Integer>(nmea.getType(), 1))
				.reduceByKey((x, y) -> x + y);

		List<Tuple2<String, Integer>> output = counts.collect();
		for (Tuple2<String, Integer> tuple : output) {
			results.put(tuple._1(), tuple._2());
		}

		return results;
	}

	private Map<String, Integer> telemetryCount(JavaRDD<String> lines) {
		HashMap<String, Integer> results = new HashMap<String, Integer>();

		// Find the telemetry lines
		JavaRDD<Telemetry> telemetryLines = lines.filter(
				line -> !line.startsWith("$")).map(
				line -> DOMUtil.createTelemetryObject(line));

		long count = telemetryLines.count();

		results.put("Telemetry", new Integer((int) count));

		return results;
	}
}
