package com.TruckBooking.Booking.Entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "transporterId", "truckNo" }) })
public @Data class TruckData {

	@Id
	private String truckId;
	
	@NotBlank(message = "Transporter Id can not be null")
	private String transporterId;
	@NotBlank(message = "Truck Number can not be null")
	private String truckNo;
	
	private Boolean truckApproved;
	private String imei;
	private long passingWeight;
	private String driverId;
	private Integer tyres;
	private Long truckLength;

	@Enumerated(EnumType.STRING)
	public TruckType truckType;
	
	@CreationTimestamp
	public Timestamp timestamp;

	public enum TruckType {
		OPEN_HALF_BODY, OPEN_FULL_BODY, FLATBED, HALF_BODY_TRAILER, FULL_BODY_TRAILER, STANDARD_CONTAINER,
		HIGH_CUBE_CONTAINER;
	}

}
