package com.pivotal.demo.spark.rocket.dom;

import lombok.Data;

@Data
public class NMEA {
	public String type;
	public Iterable<String> fields;
}
