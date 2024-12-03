package com.infor.propertyreservation.service.impl;

import com.infor.propertyreservation.dto.request.ReservationRequest;
import com.infor.propertyreservation.dto.request.ReservationSearchFilter;
import com.infor.propertyreservation.dto.response.ReservationResponse;
import com.infor.propertyreservation.dto.response.ReservationSummaryResponse;
import com.infor.propertyreservation.entity.Property;
import com.infor.propertyreservation.entity.Reservation;
import com.infor.propertyreservation.entity.User;
import com.infor.propertyreservation.enums.PropertyType;
import com.infor.propertyreservation.exception.PropertyNotFoundException;
import com.infor.propertyreservation.exception.PropertyUnavailableException;
import com.infor.propertyreservation.exception.UserNotFoundException;
import com.infor.propertyreservation.mapper.ReservationMapper;
import com.infor.propertyreservation.repository.PropertyRepository;
import com.infor.propertyreservation.repository.ReservationRepository;
import com.infor.propertyreservation.repository.UserRepository;
import com.infor.propertyreservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;

	private final PropertyRepository propertyRepository;

	private final UserRepository userRepository;

	private final ReservationMapper reservationMapper;

	private static final double HOTEL_DISCOUNT = 0.15;

	private static final double FLAT_DISCOUNT = 0.10;

	@Override
	public void createReservation(ReservationRequest request) {
		// Retrieve user and property
		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new UserNotFoundException("User not found"));

		Property property = propertyRepository.findById(request.getPropertyId())
			.orElseThrow(() -> new PropertyNotFoundException("Property not found"));

		if (!property.isAvailability()) {
			throw new PropertyUnavailableException("Property is not available for reservation");
		}

		// Calculate duration of reservation
		LocalDateTime startDate = request.getStartDate().atStartOfDay();
		LocalDateTime endDate = request.getEndDate().atStartOfDay();

		long days = Duration.between(startDate, endDate).toDays();
		if (days <= 0) {
			throw new IllegalArgumentException("Reservation must be at least one day long");
		}

		// Calculate cost
		BigDecimal pricePerDay = property.getPricePerDay();
		BigDecimal baseCost = pricePerDay.multiply(BigDecimal.valueOf(days));

		// Apply discount based on property type
		BigDecimal discountRate = property.getPropertyType() == PropertyType.FLAT ? BigDecimal.valueOf(FLAT_DISCOUNT)
				: BigDecimal.valueOf(HOTEL_DISCOUNT);
		BigDecimal discount = baseCost.multiply(discountRate);

		// Calculate tax if duration exceeds 10 days
		BigDecimal tax = BigDecimal.ZERO;
		if (days > 10) {
			tax = baseCost.subtract(discount).multiply(BigDecimal.valueOf(0.05));
		}

		BigDecimal finalCost = baseCost.subtract(discount).add(tax);

		Reservation reservation = Reservation.builder()
			.user(user)
			.property(property)
			.startDate(startDate)
			.endDate(endDate)
			.moneySpent(finalCost)
			.discount(discount)
			.tax(tax)
			.build();

		reservationRepository.save(reservation);

		// Set property as unavailable
		property.setAvailability(false);
		propertyRepository.save(property);
	}

	@Override
	public ReservationSummaryResponse listReservations(ReservationSearchFilter request, PropertyType propertyType) {
		List<Reservation> reservations = reservationRepository.findFilteredReservations(request.getUserId(),
				propertyType, request.getBuildingName(), request.getCity(), request.getAddress(), request.getCountry(),
				request.getMinPrice(), request.getMaxPrice(), request.getStartDate(), request.getEndDate());

		BigDecimal totalMoneySpentOnFlats = computeTotalMoneySpent(reservations, PropertyType.FLAT);
		BigDecimal totalMoneySpentOnHotelRooms = computeTotalMoneySpent(reservations, PropertyType.HOTEL_ROOM);
		BigDecimal totalMoneySpent = totalMoneySpentOnFlats.add(totalMoneySpentOnHotelRooms);
		String mostVisitedCity = findMostVisitedCity(reservations);

		List<ReservationResponse> reservationResponses = reservations.stream()
			.map(reservationMapper::toResponse)
			.toList();

		return new ReservationSummaryResponse(reservationResponses, totalMoneySpentOnFlats, totalMoneySpentOnHotelRooms,
				totalMoneySpent, mostVisitedCity);
	}

	private BigDecimal computeTotalMoneySpent(List<Reservation> reservations, PropertyType propertyType) {
		// Map filtered stream to extract money
		// Reduce by applying SUM
		return reservations.stream()
			.filter(r -> r.getProperty().getPropertyType() == propertyType)
			.map(Reservation::getMoneySpent)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private String findMostVisitedCity(List<Reservation> reservations) {
		// CITY_NAME, COUNT()
		return reservations.stream()
			.collect(Collectors.groupingBy(r -> r.getProperty().getCity(), Collectors.counting()))
			.entrySet()
			.stream()
			.max(Map.Entry.comparingByValue())
			.map(Map.Entry::getKey)
			.orElse(null);
	}

}
