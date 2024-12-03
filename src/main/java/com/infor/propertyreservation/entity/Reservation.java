package com.infor.propertyreservation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "property_id", nullable = false)
	private Property property;

	@Column(name = "start_date", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDateTime endDate;

	@Column(name = "money_spent", nullable = false)
	private BigDecimal moneySpent;

	@Column(name = "discount", nullable = false)
	private BigDecimal discount;

	@Column(name = "tax", nullable = false)
	private BigDecimal tax;

}