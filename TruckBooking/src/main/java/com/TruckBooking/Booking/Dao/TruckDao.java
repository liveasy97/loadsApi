package com.TruckBooking.Booking.Dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.TruckBooking.Booking.Entities.TruckData;

//import com.TruckApi.TruckApi.entities.TruckData;

@Repository
public interface TruckDao extends JpaRepository<TruckData, String> {

	public TruckData findByTruckId(String truckId);

	public List<TruckData> findByTransporterIdAndTruckApproved(String transporterId, Boolean truckApproved,
			Pageable pageable);

	public List<TruckData> findByTransporterIdAndTruckNo(String transporterId, String truckNo);

	@Query("select t from TruckData t where t.transporterId = :transporterId")
	List<TruckData> findByTransporterId(String transporterId, Pageable pageable);

	@Query("select t from TruckData t where t.truckApproved = :truckApproved")
	List<TruckData> findByTruckApproved(Boolean truckApproved, Pageable pageable);

	@Query("select t from TruckData t where t.truckId = :truckId")
	List<TruckData> findByTruckId(String truckId, Pageable pageable);


	@Query("select t from TruckData t")
	List<TruckData> getAll(Pageable pageable);
	
	
}
