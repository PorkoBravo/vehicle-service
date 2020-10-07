package es.evelb.xovetic.vehicle.services;

import static org.junit.Assert.assertEquals;
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
import es.evelb.xovetic.vehicle.service.repository.VehicleRepository;

@SpringBootTest(classes = StatisticsServiceImplementation.class)
public class StatisticsServiceShould {

	@Autowired
	StatisticsService statisctsService;

	@MockBean
	VehicleRepository vehicleRepository;

	private static final String FEATURE_COLOR = "color";
	private static final String COLOR_RED = "red";

	@Test
	public void return_crashed_car_statistics_by_color() {
		when(vehicleRepository.findByCrashedDateNotIsNull()).thenReturn(generateVehicleList());
		List<Statistic> statatistics = statisctsService.get(FEATURE_COLOR);
		Statistic redStatistic = statatistics.stream().filter(statistic -> statistic.getValue().equals(COLOR_RED))
				.findFirst().get();
		final Double RED_COLOR_CRASHED_CARS_PERCENTAGE = 0.4;
		assertEquals(RED_COLOR_CRASHED_CARS_PERCENTAGE, redStatistic.getPercentage());
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
