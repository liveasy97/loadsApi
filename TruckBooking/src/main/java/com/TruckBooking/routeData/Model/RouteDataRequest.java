package com.TruckBooking.routeData.Model;

import com.TruckBooking.routeData.Entities.Route;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class RouteDataRequest {
    //optional
    private String transporterId;
    @NotBlank(message = "stopageAddress can't be left blank.")
    private String stopageAddress;
    @NotBlank(message = "truckNo can't be left blank.")
    private String truckNo;
    @NotBlank(message = "truckId can't be left blank.")
    private String truckId;
    @NotBlank(message = "duration can't be left blank.")
    private String duration;
    @NotBlank(message = "imei can't be left blank.")
    private String imei;
    private double latitude;
    private double longitude;
    @NotBlank(message = "deviceId can't be left blank.")
    private String deviceId;
    @Enumerated(EnumType.STRING)
    private StopageStatus stopageStatus;
    public enum StopageStatus {
        Loading_Point, Unloading_Point, Maintenance, Parking
    }

}
