package com.infor.propertyreservation.entity;

import com.infor.propertyreservation.enums.PropertyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "properties",
		indexes = { @Index(name = "idx_name_lower", columnList = "name"),
				@Index(name = "idx_city_lower", columnList = "city"),
				@Index(name = "idx_address_lower", columnList = "address"),
				@Index(name = "idx_country_lower", columnList = "country"),
				@Index(name = "idx_price_per_day", columnList = "price_per_day") })
public class Property extends AbstractEntity {

	@Column(name = "name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "property_type", nullable = false)
	private PropertyType propertyType;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "country", length = 3, nullable = false)
	private String country;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "price_per_day", precision = 10, scale = 2, nullable = false)
	private BigDecimal pricePerDay;

	@Column(name = "availability")
	private boolean availability = true;

}