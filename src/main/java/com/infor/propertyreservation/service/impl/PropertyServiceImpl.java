package com.infor.propertyreservation.service.impl;

import com.infor.propertyreservation.dto.request.PropertyRequest;
import com.infor.propertyreservation.dto.response.PropertyResponse;
import com.infor.propertyreservation.mapper.PropertyMapper;
import com.infor.propertyreservation.repository.PropertyRepository;
import com.infor.propertyreservation.service.PropertyService;
import com.infor.propertyreservation.specification.PropertySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyServiceImpl implements PropertyService {

	private final PropertyRepository propertyRepository;

	private final PropertyMapper propertyMapper;

	@Override
	public Page<PropertyResponse> getProperties(int page, int size, PropertyRequest propertyRequest) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		return propertyRepository.findAll(PropertySpecification.build(propertyRequest), pageRequest)
			.map(propertyMapper::toResponse);
	}

}
