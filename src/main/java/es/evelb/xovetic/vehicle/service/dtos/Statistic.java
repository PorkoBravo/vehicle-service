package es.evelb.xovetic.vehicle.service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Statistic {
	private String value;
	private Double percentage;
}
