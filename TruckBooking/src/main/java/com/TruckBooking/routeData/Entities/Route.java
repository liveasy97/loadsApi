package com.TruckBooking.routeData.Entities;

import com.TruckBooking.TruckBooking.Entities.Load;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name= "routes")
@Data
public class Route {
    @Id
    private String routeDataId;
    //Optional
    private String transporterId;
    @NotBlank(message = "stopage Address can't be left blank.")
    private String stopageAddress;
    @NotBlank(message = "truckNo can't be left blank.")
    private String truckNo;
    @NotBlank(message = "truckID can't be left blank.")
    private String truckId;
    @NotBlank(message = "duration can't be left blank.")
    private String duration;
    @NotBlank(message = "imei can't be left blank.")
    private String imei;
    @CreationTimestamp
    public Timestamp timestamp;
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
