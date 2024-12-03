package com.infor.propertyreservation.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PropertyRequest {

	private String buildingName;

	private String city;

	private String address;

	@Size(min = 2, max = 3)
	private String country;

	@DecimalMin(value = "0.0")
	private BigDecimal minPrice;

	@DecimalMin(value = "0.0")
	private BigDecimal maxPrice;

	private Boolean availability;

}
