package com.TruckBooking.routeData.Controller;


import com.TruckBooking.routeData.Entities.Route;
import com.TruckBooking.routeData.Model.RouteDataRequest;
import com.TruckBooking.routeData.Service.RouteDataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class RouteController {

    @Autowired
    public RouteDataServiceImpl routeDataService;

    @GetMapping("/routeHome")
    public String home(){
        return "Its working";
    }

    @PostMapping("/routedata")
    public ResponseEntity<Object> route(@Valid @RequestBody RouteDataRequest routeDataRequest){
        log.info("Post Controller Started");
        return new ResponseEntity<>(routeDataService.addRouteData(routeDataRequest), HttpStatus.CREATED);
    }

    @GetMapping("/routedata/{routedataId}")
    public ResponseEntity<Object> findRouteData(@PathVariable String routedataId){
        log.info("Get by routeDataId Controller Started");
        return new ResponseEntity<>(routeDataService.getRouteData(routedataId), HttpStatus.OK);
    }

    @GetMapping("/routedata")
    public ResponseEntity<List<Route>> findRelevantRoutesData(
            @RequestParam(name = "imei", required = false) String imei,
            @RequestParam(name = "truckId", required = false) String truckId,
            @RequestParam(name = "truckNo", required = false) String trucNo,
            @RequestParam(name = "transporterId", required = false) String transporterId,
            @RequestParam(name= "deviceId", required = false) String deviceId)
    {
        log.info("Get with Params Controller Started");
        return new ResponseEntity<>(
                        routeDataService
                        .getRelevantRoutesData(imei, truckId, trucNo, transporterId, deviceId), HttpStatus.OK);
    }


}
