package com.infor.propertyreservation.service;

import com.infor.propertyreservation.dto.request.ReservationRequest;
import com.infor.propertyreservation.dto.request.ReservationSearchFilter;
import com.infor.propertyreservation.dto.response.ReservationResponse;
import com.infor.propertyreservation.dto.response.ReservationSummaryResponse;
import com.infor.propertyreservation.entity.Property;
import com.infor.propertyreservation.entity.Reservation;
import com.infor.propertyreservation.entity.User;
import com.infor.propertyreservation.enums.PropertyType;
import com.infor.propertyreservation.exception.PropertyUnavailableException;
import com.infor.propertyreservation.exception.UserNotFoundException;
import com.infor.propertyreservation.mapper.ReservationMapper;
import com.infor.propertyreservation.repository.PropertyRepository;
import com.infor.propertyreservation.repository.ReservationFilterRepository;
import com.infor.propertyreservation.repository.ReservationRepository;
import com.infor.propertyreservation.repository.UserRepository;
import com.infor.propertyreservation.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private ReservationFilterRepository reservationFilterRepository;

	@Mock
	private PropertyRepository propertyRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ReservationMapper reservationMapper;

	@InjectMocks
	private ReservationServiceImpl reservationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createReservation_WithValidRequest_SavesReservation() {
		// Arrange
		ReservationRequest request = new ReservationRequest(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(5));
		User user = new User();
		Property property = new Property();
		property.setPricePerDay(BigDecimal.valueOf(100.0));
		property.setPropertyType(PropertyType.FLAT);
		property.setAvailability(true);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

		// Act
		reservationService.createReservation(request);

		// Assert
		verify(reservationRepository, times(1)).save(any(Reservation.class));
		verify(propertyRepository, times(1)).save(any(Property.class));
		assertFalse(property.isAvailability());
	}

	@Test
	void createReservation_PropertyNotAvailable_ThrowsException() {
		// Arrange
		ReservationRequest request = new ReservationRequest(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(5));
		User user = new User();
		Property property = new Property();
		property.setAvailability(false);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

		// Act & Assert
		assertThrows(PropertyUnavailableException.class, () -> reservationService.createReservation(request));
	}

	@Test
	void listReservations_WithValidFilter_ReturnsSummary() {
		// Arrange
		ReservationSearchFilter filter = new ReservationSearchFilter(1L, "Test Building", "Test City", "123 Test St",
				"US", BigDecimal.valueOf(50.0), BigDecimal.valueOf(200.0),
				LocalDateTime.now().minusDays(5).toLocalDate(), LocalDateTime.now().toLocalDate());

		Property property = new Property();
		property.setBuildingName("Test Building");
		property.setPropertyType(PropertyType.FLAT);
		property.setCity("Test City");
		property.setCountry("US");
		property.setAddress("123 Test St");

		Reservation reservation = new Reservation();
		reservation.setProperty(property);
		reservation.setMoneySpent(BigDecimal.valueOf(500.0));
		reservation.setStartDate(LocalDateTime.now().minusDays(5));
		reservation.setEndDate(LocalDateTime.now());

		ReservationResponse reservationResponse = new ReservationResponse("Test Building", PropertyType.FLAT,
				"Test City", "US", "123 Test St", BigDecimal.valueOf(500.0), LocalDateTime.now().minusDays(5),
				LocalDateTime.now());

		when(reservationFilterRepository.findFilteredReservations(filter, PropertyType.FLAT))
			.thenReturn(Collections.singletonList(reservation));
		when(reservationMapper.toResponse(reservation)).thenReturn(reservationResponse);

		// Act
		ReservationSummaryResponse response = reservationService.listReservations(filter, PropertyType.FLAT);

		// Assert
		assertNotNull(response);
		assertEquals(1, response.reservations().size());
		assertEquals(reservationResponse, response.reservations().get(0));
		assertEquals(BigDecimal.valueOf(500.0), response.totalMoneySpentOnFlats());
		assertEquals("Test City", response.mostVisitedCity());
	}

	@Test
	void createReservation_UserNotFound_ThrowsException() {
		// Arrange
		ReservationRequest request = new ReservationRequest(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(5));

		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> reservationService.createReservation(request));
	}

}
