package com.TruckBooking.Booking.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public @Data class Truck {
	private String truckNo;  //truckdata table
	private Boolean truckApproved; //truckdata table
	private String imei;  //truckdata table
	private String driverName;
	private String driverPhoneNumber;
}
