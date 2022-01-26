package com.TruckBooking.routeData.Dao;

import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.routeData.Entities.Route;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteDataDao extends JpaRepository<Route, String> {
    List<Route> findByImei(String imei);
    List<Route> findByTruckId(String truckId);
    List<Route> findByTruckNo(String truckNo);
    List<Route> findByTransporterId(String transporterId);
    List<Route> findByDeviceId(String deviceId);
}
