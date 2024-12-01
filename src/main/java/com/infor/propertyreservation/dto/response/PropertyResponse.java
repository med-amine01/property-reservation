package com.infor.propertyreservation.dto.response;

import com.infor.propertyreservation.enums.AvailabilityStatus;
import com.infor.propertyreservation.enums.PropertyType;

import java.math.BigDecimal;

public record PropertyResponse(String name, PropertyType propertyType, String city, String country, String address,
		BigDecimal price, AvailabilityStatus availability) {
}