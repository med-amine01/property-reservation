package com.infor.propertyreservation.dto.response;

import com.infor.propertyreservation.enums.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationResponse(String buildingName,
								  PropertyType propertyType,
								  String city,
								  String country,
								  String address,
								  BigDecimal moneySpent,
								  LocalDateTime startDate,
								  LocalDateTime endDate) {
}
