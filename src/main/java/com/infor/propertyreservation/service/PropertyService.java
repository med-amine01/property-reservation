package com.infor.propertyreservation.service;

import com.infor.propertyreservation.dto.request.PropertyRequest;
import com.infor.propertyreservation.dto.response.PropertyResponse;
import org.springframework.data.domain.Page;

public interface PropertyService {

	Page<PropertyResponse> getProperties(int page, int size, PropertyRequest propertyRequest);

}
