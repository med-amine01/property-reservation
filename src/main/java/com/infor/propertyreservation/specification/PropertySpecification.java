package com.infor.propertyreservation.specification;

import com.infor.propertyreservation.dto.request.PropertyRequest;
import com.infor.propertyreservation.entity.Property;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PropertySpecification {

	public static Specification<Property> build(PropertyRequest request) {
		if (request == null) {
			return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
		}

		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (request.getBuildingName() != null) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("buildingName")),
						"%" + request.getBuildingName().toLowerCase() + "%"));
			}

			if (request.getCity() != null) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("city")),
						"%" + request.getCity().toLowerCase() + "%"));
			}

			if (request.getAddress() != null) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")),
						"%" + request.getAddress().toLowerCase() + "%"));
			}

			if (request.getCountry() != null) {
				predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(root.get("country")),
						request.getCountry().toUpperCase()));
			}

			if (request.getMinPrice() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerDay"), request.getMinPrice()));
			}

			if (request.getMaxPrice() != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerDay"), request.getMaxPrice()));
			}

			if (request.getAvailability() != null) {
				if (request.getAvailability()) {
					predicates.add(criteriaBuilder.equal(root.get("availability"), true));
				}
				else {
					predicates.add(criteriaBuilder.equal(root.get("availability"), false));
				}
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
