package com.infor.propertyreservation.controller;

import com.infor.propertyreservation.dto.request.ReservationRequest;
import com.infor.propertyreservation.dto.request.ReservationSearchFilter;
import com.infor.propertyreservation.dto.response.ReservationSummaryResponse;
import com.infor.propertyreservation.enums.PropertyType;
import com.infor.propertyreservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping("/search")
	public ResponseEntity<ReservationSummaryResponse> listReservations(
			@Valid @RequestBody ReservationSearchFilter reservationSearchFilter,
			@RequestParam(required = false) PropertyType propertyType) {
		return ResponseEntity.ok(reservationService.listReservations(reservationSearchFilter, propertyType));
	}

	@PostMapping
	public ResponseEntity<Void> addReservation(@Valid @RequestBody ReservationRequest reservation) {
		reservationService.createReservation(reservation);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
