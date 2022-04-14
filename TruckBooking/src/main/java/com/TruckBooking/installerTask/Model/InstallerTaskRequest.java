package com.TruckBooking.installerTask.Model;

import com.TruckBooking.installerTask.Entities.InstallerTask;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Data
public class InstallerTaskRequest {

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

    public InstallerTask.InstallerTaskStatus installerTaskStatus;

}
