package com.infor.propertyreservation.repository;

import com.infor.propertyreservation.entity.Reservation;
import com.infor.propertyreservation.enums.PropertyType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

	@Query("SELECT r FROM Reservation r " +
			"WHERE (:userId IS NULL OR r.user.id = :userId) " +
			"AND (:propertyType IS NULL OR r.property.propertyType = :propertyType) " +
			"AND (:buildingName IS NULL OR LOWER(r.property.buildingName) LIKE LOWER(CONCAT('%', :buildingName, '%'))) " +
			"AND (:city IS NULL OR LOWER(r.property.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
			"AND (:address IS NULL OR LOWER(r.property.address) LIKE LOWER(CONCAT('%', :address, '%'))) " +
			"AND (:country IS NULL OR LOWER(r.property.country) = LOWER(:country)) " +
			"AND (:minPrice IS NULL OR r.moneySpent >= :minPrice) " +
			"AND (:maxPrice IS NULL OR r.moneySpent <= :maxPrice) " +
			"AND (:startDate IS NULL OR r.startDate >= :startDate) " +
			"AND (:endDate IS NULL OR r.endDate <= :endDate)")
	List<Reservation> findFilteredReservations(
			Long userId,
			PropertyType propertyType,
			String buildingName,
			String city,
			String address,
			String country,
			BigDecimal minPrice,
			BigDecimal maxPrice,
			LocalDateTime startDate,
			LocalDateTime endDate
	);
}
