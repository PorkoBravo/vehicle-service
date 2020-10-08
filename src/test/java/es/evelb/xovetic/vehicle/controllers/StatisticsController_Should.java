package es.evelb.xovetic.vehicle.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import es.evelb.xovetic.vehicle.service.StatisticsService;
import es.evelb.xovetic.vehicle.service.dtos.Statistic;
import es.evelb.xovetic.vehicle.service.exceptions.FeatureNotFoundException;

@WebMvcTest(controllers = StatisticsController.class)
public class StatisticsController_Should {

	private final static String URI_MAPPING = "/statistics/{feature}";

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private StatisticsService service;

	private List<Statistic> mockedStatistics;

	@BeforeEach
	public void setup() {
		mockedStatistics = new ArrayList<>();
		Statistic statistic_1 = Statistic.builder().build();
		Statistic statistic_2 = Statistic.builder().build();
		mockedStatistics.add(statistic_1);
		mockedStatistics.add(statistic_2);
	}

	@Test
	public void return_ok_on_get() throws Exception {
		final String just_a_feature = "feature";

		mockMvc.perform(MockMvcRequestBuilders.get(URI_MAPPING, just_a_feature)).andExpect(status().isOk());
	}

	@Test
	public void invoke_service_1_time_with_requested_feature() throws Exception {
		final String requested_feature = "feature";
		when(service.get(requested_feature)).thenReturn(new ArrayList<Statistic>());

		mockMvc.perform(MockMvcRequestBuilders.get(URI_MAPPING, requested_feature)).andExpect(status().isOk());

		verify(service, times(1)).get(requested_feature);
	}

	@Test
	public void return_empty_list() throws Exception {
		final String requested_feature = "feature";
		when(service.get(requested_feature)).thenReturn(new ArrayList<Statistic>());

		mockMvc.perform(MockMvcRequestBuilders.get(URI_MAPPING, requested_feature)).andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	public void return_statistic_list() throws Exception {
		final String just_a_feature = "feature";
		when(service.get(just_a_feature)).thenReturn(mockedStatistics);

		mockMvc.perform(MockMvcRequestBuilders.get(URI_MAPPING, just_a_feature)).andExpect(status().isOk())
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(mockedStatistics.size()));
	}

	@Test
	public void return_not_found_when_feature_not_exist() throws Exception {
		final String feature_that_not_exist = "not_existing_feature";
		when(service.get(feature_that_not_exist)).thenThrow(FeatureNotFoundException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(URI_MAPPING, feature_that_not_exist))
				.andExpect(status().isNotFound());
	}

}
