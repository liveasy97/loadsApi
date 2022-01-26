package com.TruckBooking.routeData.Service;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Exception.BusinessException;
import com.TruckBooking.TruckBooking.Exception.EntityNotFoundException;
import com.TruckBooking.routeData.Dao.RouteDataDao;
import com.TruckBooking.routeData.Entities.Route;
import com.TruckBooking.routeData.Model.RouteDataRequest;
import com.TruckBooking.routeData.Response.CreateRouteDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class RouteDataServiceImpl implements RouteDataService {


    @Autowired
    RouteDataDao routeDataDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CreateRouteDataResponse addRouteData(RouteDataRequest routeDataRequest) {
        log.info("addRouteData service is Started");

        String temp = "";
        Route route = new Route();
        CreateRouteDataResponse response = new CreateRouteDataResponse();

        temp = "routeDataID:" + UUID.randomUUID();
        route.setRouteDataId(temp);
        response.setRouteDataId(temp);

        temp = routeDataRequest.getTransporterId();
        if (StringUtils.isNotBlank(temp)) {
            route.setTransporterId(temp.trim());
            response.setTransporterId(temp.trim());
        }

        temp = routeDataRequest.getStopageAddress().trim();
        route.setStopageAddress(temp);
        response.setStopageAddress(temp);

        temp = routeDataRequest.getTruckNo().trim();
        route.setTruckNo(temp);
        response.setTruckNo(temp);

        temp = routeDataRequest.getTruckId().trim();
        route.setTruckId(temp);
        response.setTruckId(temp);

        temp = routeDataRequest.getDuration().trim();
        route.setDuration(temp);
        response.setDuration(temp);

        temp = routeDataRequest.getDeviceId().trim();
        route.setDeviceId(temp);
        response.setDeviceId(temp);

        if (routeDataRequest.getLatitude() > 0){
            route.setLatitude(routeDataRequest.getLatitude());
            response.setLatitude(routeDataRequest.getLatitude());
        }
        else {
            throw new BusinessException(" Latitude Cannot be left empty.");
        }

        if (routeDataRequest.getLongitude() > 0){
            route.setLongitude(routeDataRequest.getLongitude());
            response.setLongitude(routeDataRequest.getLongitude());
        }
        else {
            throw new BusinessException(" Longitude Cannot be left empty.");
        }

        temp = routeDataRequest.getImei().trim();
        route.setImei(temp);
        response.setImei(temp);

        temp = String.valueOf(routeDataRequest.getStopageStatus());
        if (routeDataRequest.getStopageStatus() != null)
        {
            if ("Loading_Point".equals(temp)){
                route.setStopageStatus(Route.StopageStatus.Loading_Point);
                response.setStopageStatus(CreateRouteDataResponse.StopageStatus.Loading_Point);
            }
            else if ("Unloading_Point".equals(temp)){
                route.setStopageStatus(Route.StopageStatus.Unloading_Point);
                response.setStopageStatus(CreateRouteDataResponse.StopageStatus.Unloading_Point);
            }
            else if ("Maintenance".equals(temp)){
                route.setStopageStatus(Route.StopageStatus.Maintenance);
                response.setStopageStatus(CreateRouteDataResponse.StopageStatus.Maintenance);
            }
            else if ("Parking".equals(temp)){
                route.setStopageStatus(Route.StopageStatus.Parking);
                response.setStopageStatus(CreateRouteDataResponse.StopageStatus.Parking);
            }
        }
        else {
            throw new BusinessException(" Stopage Status Cannot be left empty.");
        }

        routeDataDao.save(route);
        log.info("routeData is saved to the database");
        log.info("addRouteData service response is returned");
        response.setTimestamp(route.getTimestamp());
        return response;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Route getRouteData(String routeDataId) {
        log.info("getRouteData service by routeId is started");
        Optional<Route> route = routeDataDao.findById(routeDataId);
        if (route.isEmpty())
            throw new EntityNotFoundException(Route.class, "id", routeDataId.toString());
            log.info("getRouteData service response is returned");
        return route.get();

    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Route> getRelevantRoutesData(String imei, String truckId, String truckNo, String transporterId, String deviceId) {
        log.info("getRouteData service with params started");
        if (imei != null) {
            List<Route> routes = routeDataDao.findByImei(imei);
            return routes;
        }

        if (truckId != null) {
            List<Route> routes = routeDataDao.findByTruckId(truckId);
            return routes;
        }

        if (truckNo != null) {
            List<Route> routes = routeDataDao.findByTruckNo(truckNo);
            return routes;
        }

        if (transporterId != null) {
            List<Route> routes = routeDataDao.findByTransporterId(transporterId);
            return routes;
        }

        if (deviceId != null) {
            List<Route> routes = routeDataDao.findByDeviceId(deviceId);
            return routes;
        }

        log.info("getRouteData service response is returned");
        return routeDataDao.findAll();
    }
}
