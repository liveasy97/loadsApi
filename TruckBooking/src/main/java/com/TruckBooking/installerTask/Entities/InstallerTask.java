package com.TruckBooking.installerTask.Entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


@Entity
@Table(name= "installer_task")
@Data
public class InstallerTask {

    @Id
    private String installerTaskId;

    @CreationTimestamp
    public Timestamp timestamp;

    @NotBlank(message = "Vehicle Number can not be left alone.")
    private String vehicleNo;

    private String vehicleOwnerName;

    private String vehicleOwnerPhoneNo;

    private String driverName;

    private String driverPhoneNo;

    private String installationDate;

    private String installationCompleteDate;

    private String installationLocation;

    private String gpsInstallerId;

    @Enumerated(EnumType.STRING)
    public InstallerTaskStatus installerTaskStatus;

    public enum InstallerTaskStatus
    {
        Assigned ,Pending, Completed
    }


}
