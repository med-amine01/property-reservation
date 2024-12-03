package com.infor.propertyreservation.controller;

import com.infor.propertyreservation.dto.request.PropertyRequest;
import com.infor.propertyreservation.dto.response.PropertyResponse;
import com.infor.propertyreservation.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/properties")
public class PropertyController {

	private final PropertyService propertyService;

	@PostMapping
	public ResponseEntity<Page<PropertyResponse>> listProperties(
			@RequestBody(required = false) @Valid PropertyRequest propertyRequest,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		return ResponseEntity.ok(propertyService.getProperties(page, size, propertyRequest));
	}

}
