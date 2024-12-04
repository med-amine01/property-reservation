package com.infor.propertyreservation.service;

import com.infor.propertyreservation.dto.request.PropertyRequest;
import com.infor.propertyreservation.dto.response.PropertyResponse;
import com.infor.propertyreservation.entity.Property;
import com.infor.propertyreservation.enums.AvailabilityStatus;
import com.infor.propertyreservation.enums.PropertyType;
import com.infor.propertyreservation.mapper.PropertyMapper;
import com.infor.propertyreservation.repository.PropertyRepository;
import com.infor.propertyreservation.service.impl.PropertyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PropertyServiceImplTest {

	@Mock
	private PropertyRepository propertyRepository;

	@Mock
	private PropertyMapper propertyMapper;

	@InjectMocks
	private PropertyServiceImpl propertyService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getProperties_WithValidRequest_ReturnsPropertyPage() {
		// Arrange
		int page = 0;
		int size = 10;
		PropertyRequest propertyRequest = new PropertyRequest();
		propertyRequest.setBuildingName("Test Property");
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

		Property property = new Property();
		property.setId(1L);
		property.setBuildingName("Test Property");
		property.setCity("Test City");
		property.setCountry("US");
		property.setAddress("123 Test St");
		property.setPricePerDay(BigDecimal.valueOf(100.0));
		property.setPropertyType(PropertyType.HOTEL_ROOM);
		property.setAvailability(true);

		PropertyResponse propertyResponse = new PropertyResponse(1L, "Test Property", PropertyType.HOTEL_ROOM,
				"Test City", "US", "123 Test St", BigDecimal.valueOf(100.0), AvailabilityStatus.AVAILABLE);

		Page<Property> propertyPage = new PageImpl<>(List.of(property));

		when(propertyRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(propertyPage);
		when(propertyMapper.toResponse(property)).thenReturn(propertyResponse);

		// Act
		Page<PropertyResponse> result = propertyService.getProperties(page, size, propertyRequest);

		// Assert
		assertEquals(1, result.getTotalElements());
		assertEquals(propertyResponse, result.getContent().get(0));
		verify(propertyRepository, times(1)).findAll(any(Specification.class), eq(pageRequest));
		verify(propertyMapper, times(1)).toResponse(property);
	}

	@Test
	void getProperties_WithNoMatchingProperties_ReturnsEmptyPage() {
		// Arrange
		int page = 0;
		int size = 10;
		PropertyRequest propertyRequest = new PropertyRequest();
		propertyRequest.setBuildingName("Nonexistent Property");
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

		Page<Property> emptyPropertyPage = new PageImpl<>(Collections.emptyList());

		when(propertyRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(emptyPropertyPage);

		// Act
		Page<PropertyResponse> result = propertyService.getProperties(page, size, propertyRequest);

		// Assert
		assertEquals(0, result.getTotalElements());
		verify(propertyRepository, times(1)).findAll(any(Specification.class), eq(pageRequest));
		verify(propertyMapper, never()).toResponse(any(Property.class));
	}

}
