package com.pivotal.demo.spark.rocket.dom;

import lombok.Data;

@Data
public class Telemetry {
	// Raw data values
	public final int timestamp;
	public final float relativeAltitude;
	public final Acceleration a;
}
