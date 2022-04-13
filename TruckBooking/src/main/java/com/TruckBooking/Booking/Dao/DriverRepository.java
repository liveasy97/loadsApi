package com.TruckBooking.Booking.Dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.TruckBooking.Booking.Entities.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {

	@Query("select d from Driver d")
	List<Driver> findAllDrivers(Pageable pageable);


	List<Driver> findByTransporterId(String transporterId, Pageable pageable);

	List<Driver> findByPhoneNum(String phoneNum, Pageable pageable);

	List<Driver> findByTruckId(String truckId, Pageable pageable);

	List<Driver> findByPhoneNumAndTransporterId(String phoneNum, String transporterId, Pageable pageable);

	List<Driver> findByTruckIdAndTransporterId(String truckId, String transporterId, Pageable pageable);

	List<Driver> findByPhoneNumAndTruckId(String phoneNum, String truckId, Pageable pageable);

	List<Driver> findByPhoneNumAndTransporterIdAndTruckId(String phoneNum, String transporterId, String truckId,
			Pageable pageable);

}
