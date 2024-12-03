package com.infor.propertyreservation.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationSearchFilter {

	private Long userId;

	private String buildingName;

	private String city;

	private String address;

	@Size(min = 2, max = 3, message = "Country must be a valid IATA code.")
	private String country;

	@DecimalMin(value = "0.0", inclusive = false, message = "Minimum price must be greater than 0.")
	private BigDecimal minPrice;

	@DecimalMin(value = "0.0", inclusive = false, message = "Maximum price must be greater than 0.")
	private BigDecimal maxPrice;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

}
