package com.infor.propertyreservation.mapper;

import com.infor.propertyreservation.dto.response.ReservationResponse;
import com.infor.propertyreservation.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

	public ReservationResponse toResponse(Reservation reservation) {
		return new ReservationResponse(reservation.getProperty().getBuildingName(),
				reservation.getProperty().getPropertyType(), reservation.getProperty().getCity(),
				reservation.getProperty().getCountry(), reservation.getProperty().getAddress(),
				reservation.getMoneySpent(), reservation.getStartDate(), reservation.getEndDate());
	}

}
