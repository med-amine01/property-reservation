package com.infor.propertyreservation.util;

import com.infor.propertyreservation.dto.request.PropertyRequest;
import com.infor.propertyreservation.entity.Property;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.infor.propertyreservation.util.PredicateUtil.addStringEqualsPredicate;
import static com.infor.propertyreservation.util.PredicateUtil.addStringLikePredicate;

public class PropertySpecification {

	public static Specification<Property> build(PropertyRequest request) {
		return Optional.ofNullable(request).map(req -> (Specification<Property>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// Create OR conditions for buildingName, city, and address
			List<Predicate> orPredicates = new ArrayList<>();
			addStringLikePredicate(criteriaBuilder, root.get("buildingName"), req.getBuildingName(), orPredicates);
			addStringLikePredicate(criteriaBuilder, root.get("city"), req.getCity(), orPredicates);
			addStringLikePredicate(criteriaBuilder, root.get("address"), req.getAddress(), orPredicates);

			if (!orPredicates.isEmpty()) {
				predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
			}

			addStringEqualsPredicate(criteriaBuilder, root.get("country"), req.getCountry(), predicates);

			Optional.ofNullable(req.getMinPrice())
				.ifPresent(minPrice -> predicates
					.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerDay"), minPrice)));

			Optional.ofNullable(req.getMaxPrice())
				.ifPresent(maxPrice -> predicates
					.add(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerDay"), maxPrice)));

			Optional.ofNullable(req.getAvailability())
				.ifPresent(
						availability -> predicates.add(criteriaBuilder.equal(root.get("availability"), availability)));

			return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		}).orElse(null);
	}

}
