//package com.infor.propertyreservation.service;
//
//import com.infor.propertyreservation.dto.request.PropertyRequest;
//import com.infor.propertyreservation.dto.response.PropertyResponse;
//import com.infor.propertyreservation.entity.Property;
//import com.infor.propertyreservation.enums.PropertyType;
//import com.infor.propertyreservation.exception.PropertyNotFoundException;
//import com.infor.propertyreservation.mapper.PropertyMapper;
//import com.infor.propertyreservation.repository.PropertyRepository;
//import com.infor.propertyreservation.service.impl.PropertyServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@DisplayName("Unit Tests for PropertyService")
//class PropertyServiceTest {
//
//	@Mock
//	private PropertyRepository propertyRepository;
//
//	@Mock
//	private PropertyMapper propertyMapper;
//
//	private PropertyService propertyService;
//
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//		propertyService = new PropertyServiceImpl(propertyRepository, propertyMapper);
//	}
//
//	@Test
//	@DisplayName("getProperties with valid pagination should return paginated properties")
//	void getProperties_ValidPagination_ReturnsPaginatedProperties() {
//		Pageable pageable = PageRequest.of(0, 5);
//		Property property = new Property();
//		property.setId(1L);
//		property.setBuildingName("Ocean View");
//
//		PropertyResponse response = new PropertyResponse(1L, "Ocean View", PropertyType.FLAT, "San Francisco", "USA",
//				"123 Beach St", new BigDecimal("150.00"));
//
//		when(propertyRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(property)));
//		when(propertyMapper.toResponse(property)).thenReturn(response);
//
//		Page<PropertyResponse> result = propertyService.getProperties(pageable);
//
//		assertNotNull(result);
//		assertEquals(1, result.getTotalElements());
//		assertEquals("Ocean View", result.getContent().get(0).getBuildingName());
//		verify(propertyRepository, times(1)).findAll(pageable);
//		verify(propertyMapper, times(1)).toResponse(property);
//	}
//
//	@Test
//	@DisplayName("getPropertyById with existing ID should return the property")
//	void getPropertyById_ExistingId_ReturnsProperty() {
//		Property property = new Property();
//		property.setId(1L);
//		property.setBuildingName("Ocean View");
//
//		PropertyResponse response = new PropertyResponse(1L, "Ocean View", PropertyType.FLAT, "San Francisco", "USA",
//				"123 Beach St", new BigDecimal("150.00"));
//
//		when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
//		when(propertyMapper.toResponse(property)).thenReturn(response);
//
//		PropertyResponse result = propertyService.getPropertyById(1L);
//
//		assertNotNull(result);
//		assertEquals("Ocean View", result.getBuildingName());
//		verify(propertyRepository, times(1)).findById(1L);
//		verify(propertyMapper, times(1)).toResponse(property);
//	}
//
//	@Test
//	@DisplayName("getPropertyById with non-existent ID should throw PropertyNotFoundException")
//	void getPropertyById_NonExistentId_ThrowsException() {
//		when(propertyRepository.findById(1L)).thenReturn(Optional.empty());
//
//		assertThrows(PropertyNotFoundException.class, () -> propertyService.getPropertyById(1L));
//		verify(propertyRepository, times(1)).findById(1L);
//	}
//
//	@Test
//	@DisplayName("createProperty with valid request should save and return the property")
//	void createProperty_ValidRequest_SavesAndReturnsProperty() {
//		PropertyRequest request = PropertyRequest.builder()
//			.buildingName("Ocean View")
//			.propertyType(PropertyType.FLAT)
//			.city("San Francisco")
//			.country("USA")
//			.address("123 Beach St")
//			.pricePerDay(new BigDecimal("150.00"))
//			.build();
//
//		Property property = new Property();
//		property.setId(1L);
//
//		PropertyResponse response = new PropertyResponse(1L, "Ocean View", PropertyType.FLAT, "San Francisco", "USA",
//				"123 Beach St", new BigDecimal("150.00"));
//
//		when(propertyMapper.toEntity(request)).thenReturn(property);
//		when(propertyRepository.save(property)).thenReturn(property);
//		when(propertyMapper.toResponse(property)).thenReturn(response);
//
//		PropertyResponse result = propertyService.createProperty(request);
//
//		assertNotNull(result);
//		assertEquals("Ocean View", result.getBuildingName());
//		verify(propertyMapper, times(1)).toEntity(request);
//		verify(propertyRepository, times(1)).save(property);
//		verify(propertyMapper, times(1)).toResponse(property);
//	}
//
//	@Test
//	@DisplayName("deleteProperty with non-existent ID should throw PropertyNotFoundException")
//	void deleteProperty_NonExistentId_ThrowsException() {
//		when(propertyRepository.existsById(1L)).thenReturn(false);
//
//		assertThrows(PropertyNotFoundException.class, () -> propertyService.deleteProperty(1L));
//		verify(propertyRepository, times(1)).existsById(1L);
//	}
//
//}
