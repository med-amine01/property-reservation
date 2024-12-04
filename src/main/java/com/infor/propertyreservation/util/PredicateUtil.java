package com.infor.propertyreservation.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.util.List;
import java.util.Optional;

public class PredicateUtil {

	private PredicateUtil() {
		// Private constructor to prevent instantiation
	}

	public static void addStringLikePredicate(CriteriaBuilder cb, Path<String> path, String value,
			List<Predicate> predicates) {
		Optional.ofNullable(value)
			.filter(val -> !val.isBlank())
			.map(String::trim)
			.map(String::toLowerCase)
			.ifPresent(val -> predicates.add(cb.like(cb.lower(path), "%" + val + "%")));
	}

	public static void addStringEqualsPredicate(CriteriaBuilder cb, Path<String> path, String value,
			List<Predicate> predicates) {
		Optional.ofNullable(value)
			.filter(val -> !val.isBlank())
			.map(String::trim)
			.map(String::toLowerCase)
			.ifPresent(val -> predicates.add(cb.equal(cb.lower(path), val)));
	}

}
