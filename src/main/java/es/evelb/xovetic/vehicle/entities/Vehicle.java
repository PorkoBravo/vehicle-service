package es.evelb.xovetic.vehicle.entities;

import java.lang.reflect.Field;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Vehicle {
	private String color;

	public Optional<String> getValueByFeature(String feature) {
		Optional<Field> field = getField(Vehicle.class, feature);
		String value = null;
		if (field.isPresent()) {
			field.get().setAccessible(true);
			try {
				value = (String) field.get().get(this);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				value = null;
			}
			field.get().setAccessible(false);
		}
		return Optional.ofNullable(value);
	}

	private Optional<Field> getField(Class<?> clazz, String fieldKey) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(fieldKey)) {
				return Optional.of(field);
			}
		}
		return Optional.empty();
	}
}
