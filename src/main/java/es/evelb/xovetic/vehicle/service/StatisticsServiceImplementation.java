package es.evelb.xovetic.vehicle.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.evelb.xovetic.vehicle.entities.Vehicle;
import es.evelb.xovetic.vehicle.service.dtos.Statistic;
import es.evelb.xovetic.vehicle.service.repository.VehicleRepository;

@Service
public class StatisticsServiceImplementation implements StatisticsService {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Override
	public List<Statistic> get(String feature) {
		List<Statistic> statistics = new ArrayList<>();
		List<Vehicle> crashedVehicles = vehicleRepository.findByCrashedDateNotIsNull();
		Map<String, Integer> appearancesByFeature = countAppereancesByFeature(feature, crashedVehicles);
		for (String key : appearancesByFeature.keySet()) {
			Statistic statistic = Statistic.builder().value(key)
					.percentage(calculatePercentage(appearancesByFeature.get(key), crashedVehicles.size())).build();
			statistics.add(statistic);
		}
		return statistics;
	}

	private Double calculatePercentage(Integer appereances, Integer samplingSize) {
		return ((double) appereances) / samplingSize;
	}

	private Map<String, Integer> countAppereancesByFeature(String feature, List<Vehicle> crashedVehicles) {
		Map<String, Integer> appearancesByFeature = new HashMap<>();
		for (Vehicle crashedVehicle : crashedVehicles) {
			Optional<String> featureValue = crashedVehicle.getValueByFeature(feature);
			Integer counter = appearancesByFeature.get(featureValue.get());
			if (counter == null) {
				counter = 0;
			}
			appearancesByFeature.put(featureValue.get(), ++counter);
		}
		return appearancesByFeature;
	}

}
