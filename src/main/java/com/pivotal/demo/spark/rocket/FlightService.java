package com.pivotal.demo.spark.rocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pivotal.demo.spark.rocket.dom.Flight;

@Service
public class FlightService
{
	final private ArrayList<Flight> theFlights;
	
	//Hard code two flights from our sample data
	public FlightService()
	{
		theFlights = new ArrayList<Flight>();
		Flight f = new Flight();
		f.setId(0); f.setFileName("20130316-DATA-00.TXT");
		theFlights.add(f);
		
		f = new Flight();
		f.setId(1); f.setFileName("20130413-DATA-00.TXT");
		theFlights.add(f);
	}

	public List<Flight> getFlights()
	{
		return theFlights;
	}

	public Flight getFlight(int id)
	{
		return theFlights.get(id);
	}
}
