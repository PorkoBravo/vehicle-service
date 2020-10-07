package es.evelb.xovetic.vehicle.service;

import java.util.List;

import es.evelb.xovetic.vehicle.service.dtos.Statistic;

public interface StatisticsService {
	public List<Statistic> get(String feature);
}
