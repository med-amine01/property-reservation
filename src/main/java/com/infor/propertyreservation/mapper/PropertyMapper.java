package com.infor.propertyreservation.mapper;

import com.infor.propertyreservation.dto.response.PropertyResponse;
import com.infor.propertyreservation.entity.Property;
import com.infor.propertyreservation.enums.AvailabilityStatus;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapper {

	public PropertyResponse toResponse(Property property) {
		return new PropertyResponse(
				property.getBuildingName(),
				property.getPropertyType(),
				property.getCity(),
				property.getCountry(),
				property.getAddress(),
				property.getPricePerDay(),
				property.isAvailability() ? AvailabilityStatus.AVAILABLE : AvailabilityStatus.BOOKED
		);
	}
}
