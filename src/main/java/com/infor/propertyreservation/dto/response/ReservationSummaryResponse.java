package com.infor.propertyreservation.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ReservationSummaryResponse(List<ReservationResponse> reservations,
										 BigDecimal totalMoneySpentOnFlats,
										 BigDecimal totalMoneySpentOnHotelRooms,
										 BigDecimal totalMoneySpent,
										 String mostVisitedCity) {
}
