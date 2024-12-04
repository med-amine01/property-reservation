package com.infor.propertyreservation.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ReservationRequest {

	@NotNull(message = "user is required.")
	private Long userId;

	@NotNull(message = "property is required.")
	private Long propertyId;

	@NotNull(message = "Start date is required.")
	@FutureOrPresent(message = "Start date must be in the present or future.")
	private LocalDate startDate;

	@NotNull(message = "End date is required.")
	@FutureOrPresent(message = "End date must be in the present or future.")
	private LocalDate endDate;

}
