package com.TruckBooking.routeData.Response;

import com.TruckBooking.routeData.Entities.Route;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class CreateRouteDataResponse {

    private String routeDataId;
    private String transporterId;
    private String stopageAddress;
    private String truckNo;
    private String truckId;
    private String duration;
    public Timestamp timestamp;
    private double latitude;
    private double longitude;
    private String deviceId;
    @Enumerated(EnumType.STRING)
    private StopageStatus stopageStatus;
    public enum StopageStatus {
        Loading_Point, Unloading_Point, Maintenance, Parking
    }
    private String imei;

}
