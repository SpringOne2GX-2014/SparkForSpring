package com.pivotal.demo.spark.rocket.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pivotal.demo.spark.rocket.AnalyzeService;
import com.pivotal.demo.spark.rocket.FlightService;

@Profile("webui")
@Controller
@RequestMapping({ "/", "/telemetry", "/rocket" })
public class AnalyzerController {
	// Autowired in constructor
	private final AnalyzeService aService;
	private final FlightService fs;

	@Autowired
	public AnalyzerController(AnalyzeService aService, FlightService fs) {
		this.aService = aService;
		this.fs = fs;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("message", "Select a flight:");
		model.addAttribute("flightList", fs.getFlights());
		return "home";
	}

	@RequestMapping("/analyze/{id}")
	public String analyze(@PathVariable int id, Model model) {
		model.addAttribute("message", "Analysis of Flight: " + id);
		model.addAttribute("results", aService.analyzeFlight(id));
		return "analysis";
	}
}
