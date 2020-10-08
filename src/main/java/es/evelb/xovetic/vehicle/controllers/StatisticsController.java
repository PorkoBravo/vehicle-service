package es.evelb.xovetic.vehicle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.evelb.xovetic.vehicle.service.StatisticsService;
import es.evelb.xovetic.vehicle.service.dtos.Statistic;
import es.evelb.xovetic.vehicle.service.exceptions.FeatureNotFoundException;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService vehicleStatisticsService;

	@GetMapping("/{feature}")
	public List<Statistic> getVehicleStatisticsByFeature(@PathVariable String feature) {
		try {
			return vehicleStatisticsService.get(feature);
		} catch (FeatureNotFoundException notFeatureException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature not found");
		}
	}

}
