package com.pivotal.demo.spark.rocket.util;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.pivotal.demo.spark.rocket.dom.Acceleration;
import com.pivotal.demo.spark.rocket.dom.NMEA;
import com.pivotal.demo.spark.rocket.dom.Telemetry;

public class DOMUtil {
	private static final Logger log = Logger.getLogger(DOMUtil.class);

	// Parse a line of text into a Telemetery object
	public static Telemetry createTelemetryObject(String line) {
		Telemetry retVal = null;

		if ("".equals(line.trim())) {
			log.warn("Received request to create a telemetry object from an empty/blank string.  Returning null.");
		} else {
			try {
				String[] parts = line.split("\t");

				if (parts.length == 5) {
					Acceleration a = new Acceleration(
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3]),
							Float.parseFloat(parts[4]));
					retVal = new Telemetry(Integer.parseInt(parts[0]),
							Float.parseFloat(parts[1]), a);
				} else {
					log.warn("String parsed to wrong number of items for telemetry object.  "+line);
				}
			} catch (Exception e) {
				log.warn("Exception parsing the Telemetry String: \"" + line + "\"");
				log.warn(e.toString());
				retVal = null;
			}
		}

		return retVal;
	}

	//Parse a line of text into a NMEA object.
	public static NMEA createNMEAObject(String line) {
		List<String> gpsElements = Arrays.asList(line.split(","));
		String key = gpsElements.get(0);
		NMEA nmea = new NMEA();
		nmea.setType(key);
		return nmea;
	}
}
