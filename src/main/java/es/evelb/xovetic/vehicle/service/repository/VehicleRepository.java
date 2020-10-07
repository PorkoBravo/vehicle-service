package es.evelb.xovetic.vehicle.service.repository;

import java.util.List;

import es.evelb.xovetic.vehicle.entities.Vehicle;

public interface VehicleRepository {
	public List<Vehicle> findByCrashedDateNotIsNull();
}
