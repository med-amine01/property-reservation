package com.infor.propertyreservation.service;

import com.infor.propertyreservation.dto.request.ReservationRequest;
import com.infor.propertyreservation.dto.request.ReservationSearchFilter;
import com.infor.propertyreservation.dto.response.ReservationSummaryResponse;
import com.infor.propertyreservation.enums.PropertyType;

public interface ReservationService {

	void createReservation(ReservationRequest request);

	ReservationSummaryResponse listReservations(ReservationSearchFilter reservationSearchFilter,
			PropertyType propertyType);

}
