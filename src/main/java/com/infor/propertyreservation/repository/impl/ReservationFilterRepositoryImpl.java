package com.infor.propertyreservation.repository.impl;

import com.infor.propertyreservation.dto.request.ReservationSearchFilter;
import com.infor.propertyreservation.entity.Property;
import com.infor.propertyreservation.entity.Reservation;
import com.infor.propertyreservation.enums.PropertyType;
import com.infor.propertyreservation.repository.ReservationFilterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.infor.propertyreservation.util.PredicateUtil.addStringEqualsPredicate;
import static com.infor.propertyreservation.util.PredicateUtil.addStringLikePredicate;

@Repository
public class ReservationFilterRepositoryImpl implements ReservationFilterRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Reservation> findFilteredReservations(ReservationSearchFilter filter, PropertyType propertyType) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
		Root<Reservation> reservation = query.from(Reservation.class);
		Join<Reservation, Property> property = reservation.join("property");

		List<Predicate> predicates = buildPredicates(cb, reservation, property, filter, propertyType);

		// Ordering
		query.where(cb.and(predicates.toArray(new Predicate[0]))).orderBy(cb.desc(reservation.get("id")));

		return entityManager.createQuery(query).getResultList();
	}

	private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Reservation> reservation,
			Join<Reservation, Property> property, ReservationSearchFilter filter, PropertyType propertyType) {
		List<Predicate> predicates = new ArrayList<>();

		// Using Optional to cleanly handle null checks
		Optional.ofNullable(filter.getUserId())
			.ifPresent(userId -> predicates.add(cb.equal(reservation.get("user").get("id"), userId)));

		Optional.ofNullable(propertyType)
			.ifPresent(type -> predicates.add(cb.equal(property.get("propertyType"), type)));

		addStringLikePredicate(cb, property.get("buildingName"), filter.getBuildingName(), predicates);
		addStringLikePredicate(cb, property.get("city"), filter.getCity(), predicates);
		addStringLikePredicate(cb, property.get("address"), filter.getAddress(), predicates);
		addStringEqualsPredicate(cb, property.get("country"), filter.getCountry(), predicates);

		Optional.ofNullable(filter.getMinPrice())
			.ifPresent(minPrice -> predicates.add(cb.greaterThanOrEqualTo(reservation.get("moneySpent"), minPrice)));

		Optional.ofNullable(filter.getMaxPrice())
			.ifPresent(maxPrice -> predicates.add(cb.lessThanOrEqualTo(reservation.get("moneySpent"), maxPrice)));

		// Add date overlap predicates
		addDateOverlapPredicate(cb, reservation, filter, predicates);

		return predicates;
	}

	private void addDateOverlapPredicate(CriteriaBuilder cb, Root<Reservation> reservation,
										 ReservationSearchFilter filter, List<Predicate> predicates) {
		Optional.ofNullable(filter.getStartDate()).ifPresent(startDate -> {
			Optional.ofNullable(filter.getEndDate())
				.ifPresentOrElse(
						endDate -> predicates.add(cb.and(cb.lessThanOrEqualTo(reservation.get("startDate"), endDate),
								cb.greaterThanOrEqualTo(reservation.get("endDate"), startDate))),
						() -> predicates.add(cb.greaterThanOrEqualTo(reservation.get("endDate"), startDate)));
		});

		Optional.ofNullable(filter.getEndDate())
			.filter(endDate -> filter.getStartDate() == null)
			.ifPresent(endDate -> predicates.add(cb.lessThanOrEqualTo(reservation.get("startDate"), endDate)));
	}

}
