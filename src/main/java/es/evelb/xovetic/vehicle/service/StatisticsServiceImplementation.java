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
		List<Statistic> result = new ArrayList<>();
		Map<String, Integer> appearancesByFeature = new HashMap<>();
		List<Vehicle> crashedVehicles = vehicleRepository.findByCrashedDateNotIsNull();
		for (Vehicle crashedVehicle : crashedVehicles) {
			Optional<String> featureValue = crashedVehicle.getValueByFeature(feature);
			Integer counter = appearancesByFeature.get(featureValue.get());
			if (counter == null) {
				counter = 0;
			}
			appearancesByFeature.put(featureValue.get(), ++counter);
		}
		for (String key : appearancesByFeature.keySet()) {
			result.add(Statistic.builder().value(key)
					.percentage(((double) appearancesByFeature.get(key)) / crashedVehicles.size()).build()
			);
		}
		return result;
	}
}
