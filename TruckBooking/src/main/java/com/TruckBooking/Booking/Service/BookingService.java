package com.TruckBooking.Booking.Service;

import java.net.ConnectException;
import java.util.List;

import com.TruckBooking.Booking.Model.ResponseTesting;
import com.TruckBooking.Booking.Entities.BookingData;
import com.TruckBooking.Booking.Model.BookingDeleteResponse;
import com.TruckBooking.Booking.Model.BookingPostRequest;
import com.TruckBooking.Booking.Model.BookingPostResponse;
import com.TruckBooking.Booking.Model.BookingPutRequest;
import com.TruckBooking.Booking.Model.BookingPutResponse;

public interface BookingService {

	BookingPostResponse addBooking(BookingPostRequest request);

	BookingPutResponse updateBooking(String bookingId, BookingPutRequest request);

	BookingData getDataById(String Id);

	List<BookingData> getDataById(Integer pageNo, Boolean cancel, Boolean completed, String transporterId,
			String postLoadId);

	BookingDeleteResponse deleteBooking(String bookingId);
	
	void updating_load_status_by_loadid(String loadid, String inputJson) throws ConnectException, Exception;
	
	List<ResponseTesting> getDataTesting(Integer pageNo, Boolean cancel, Boolean completed, String transporterId,String postLoadId);

}
