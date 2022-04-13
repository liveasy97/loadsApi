package com.TruckBooking.Booking.Dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.TruckBooking.Booking.Entities.BookingData;

@Repository
public interface BookingDao extends JpaRepository<BookingData, String> {

	List<BookingData> findByLoadIdAndTransporterId(String loadId, String transporterId);

	BookingData findByBookingId(String id);

	List<BookingData> findByTransporterIdAndCancelAndCompleted(String transporterId, Boolean cancel, Boolean completed,
			Pageable p);

	List<BookingData> findByPostLoadIdAndCancelAndCompleted(String postLoadId, Boolean cancel, Boolean completed,
			Pageable p);

	List<BookingData> findByCancelAndCompleted(Boolean cancel, Boolean completed, Pageable p);
	
	@Query("select b from BookingData b")
	List<BookingData> getAll(Pageable p);
	
	@Query(value="SELECT b FROM BookingData b LEFT JOIN FETCH b.truckId WHERE b.transporterId = :transporterId and b.cancel = :cancel and b.completed = :completed" ,
			countProjection = "b.bookingId")
	List<BookingData> findByTransporterIdAndCancelAndCompletedWithJoinFetchTruckIds(String transporterId, Boolean cancel, Boolean completed,
			Pageable p);

}
