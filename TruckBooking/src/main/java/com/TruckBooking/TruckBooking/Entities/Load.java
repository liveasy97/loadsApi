package com.TruckBooking.TruckBooking.Entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="load")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Load {
	@Id
	private String loadId;
	
	private String loadingPoint;
	private String loadingPointCity;
	private String loadingPointState;
	private String postLoadId;
	private String unloadingPoint;
	private String unloadingPointCity;
	private String unloadingPointState;
	private String productType;
	private String truckType;
	private String noOfTrucks;
	private String weight; 
	private String loadDate;
	private String comment; //this should be an optional
	private String status;
	private Long rate;  //optional
	
	@Enumerated(EnumType.STRING)
	private UnitValue unitValue;    //optional

	public enum UnitValue{
		PER_TON, PER_TRUCK
	}
	
}


