package com.infor.propertyreservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends AbstractEntity {

	@Column(name = "name", nullable = false)
	private String name;

}
