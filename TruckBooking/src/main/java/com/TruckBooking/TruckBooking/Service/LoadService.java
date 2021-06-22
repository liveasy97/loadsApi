package com.TruckBooking.TruckBooking.Service;

import java.util.List;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.TruckBooking.Response.CreateLoadResponse;
import com.TruckBooking.TruckBooking.Response.DeleteLoadResponse;
import com.TruckBooking.TruckBooking.Response.UpdateLoadResponse;

public interface LoadService {
	public CreateLoadResponse addLoad(LoadRequest loadrequest);
	public List<Load> getLoads(Integer pageNo, String loadingPointCity, String unloadingPointCity, String postLoadId, 
			String truckType, String date, boolean suggestedLoads);
	public UpdateLoadResponse updateLoad(String loadId, LoadRequest loadrequest); 
	public Load getLoad(String loadId);
	public DeleteLoadResponse deleteLoad(String loadId);
}
