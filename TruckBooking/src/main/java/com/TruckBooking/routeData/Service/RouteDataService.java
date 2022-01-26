package com.TruckBooking.routeData.Service;

import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.routeData.Entities.Route;
import com.TruckBooking.routeData.Model.RouteDataRequest;
import com.TruckBooking.routeData.Response.CreateRouteDataResponse;

import java.util.List;

public interface RouteDataService {

    public CreateRouteDataResponse addRouteData(RouteDataRequest routeDataRequest);

    public Route getRouteData(String routeDataId);

    public List<Route> getRelevantRoutesData(String imei, String truckId, String truckNo, String transporterId, String deviceId);

}
