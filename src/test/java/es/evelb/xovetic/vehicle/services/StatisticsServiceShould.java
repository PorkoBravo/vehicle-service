package es.evelb.xovetic.vehicle.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.evelb.xovetic.vehicle.entities.Vehicle;
import es.evelb.xovetic.vehicle.service.StatisticsService;
import es.evelb.xovetic.vehicle.service.StatisticsServiceImplementation;
import es.evelb.xovetic.vehicle.service.dtos.Statistic;
import es.evelb.xovetic.vehicle.service.exceptions.NotFoundFeatureException;
import es.evelb.xovetic.vehicle.service.repository.VehicleRepository;

@SpringBootTest(classes = StatisticsServiceImplementation.class)
public class StatisticsServiceShould {

	@Autowired
	StatisticsService statisctsService;

	@MockBean
	VehicleRepository vehicleRepository;

	private static final String FEATURE_COLOR = "color";
	private static final String FEATURE_NOT_EXISTS = "featureThatWillNeverExists...NEVER!";
	private static final String COLOR_RED = "red";

	@Test
	public void return_crashed_car_statistics_by_color() throws NotFoundFeatureException {
		when(vehicleRepository.findByCrashedDateNotIsNull()).thenReturn(generateVehicleList());
		List<Statistic> statatistics = statisctsService.get(FEATURE_COLOR);
		Statistic redStatistic = statatistics.stream().filter(statistic -> statistic.getValue().equals(COLOR_RED))
				.findFirst().get();
		final Double RED_COLOR_CRASHED_CARS_PERCENTAGE = 0.4;
		assertEquals(RED_COLOR_CRASHED_CARS_PERCENTAGE, redStatistic.getPercentage());
	}
	
	@Test
	public void throw_invalid_feature_exception_when_the_feature_is_null() {
		assertThrows(NotFoundFeatureException.class, () -> {
			statisctsService.get(null);
		});
	}
	
	@Test
	public void throw_exception_when_the_feature_does_not_exists() {
		assertThrows(NotFoundFeatureException.class, () -> {
			statisctsService.get(FEATURE_NOT_EXISTS);
		});
	}
	
	@Test
	public void return_empty_list_when_there_is_not_vehicles() throws NotFoundFeatureException {
		when(vehicleRepository.findByCrashedDateNotIsNull()).thenReturn(new ArrayList<>());
		List<Statistic> statatistics = statisctsService.get(FEATURE_COLOR);
		assertEquals(0, statatistics.size());
	}

	private List<Vehicle> generateVehicleList() {
		List<Vehicle> result = new ArrayList<>();
		result.add(Vehicle.builder().color("red").build());
		result.add(Vehicle.builder().color("red").build());
		result.add(Vehicle.builder().color("blue").build());
		result.add(Vehicle.builder().color("yellow").build());
		result.add(Vehicle.builder().color("black").build());
		return result;
	}

}
