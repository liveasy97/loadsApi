package com.TruckBooking.Booking.Entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "transporterId", "phoneNum" }) })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {

	@Id
	private String driverId;

	@NotBlank(message = "Transporter Id can not be null")
	private String transporterId;
	@NotBlank(message = "Phone Number can not be null")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Must be a valid 10 digit number")
	private String phoneNum;
	@NotBlank(message = "Driver Name can not be null")
	private String driverName;

	private String truckId;

	@CreationTimestamp
	public Timestamp timestamp;

}
