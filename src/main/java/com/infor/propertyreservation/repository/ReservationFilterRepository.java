package com.infor.propertyreservation.repository;

import com.infor.propertyreservation.dto.request.ReservationSearchFilter;
import com.infor.propertyreservation.entity.Reservation;
import com.infor.propertyreservation.enums.PropertyType;

import java.util.List;

public interface ReservationFilterRepository {

	List<Reservation> findFilteredReservations(ReservationSearchFilter filter, PropertyType propertyType);

}