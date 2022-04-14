package com.TruckBooking.installerTask.Response;


import com.TruckBooking.installerTask.Entities.InstallerTask;
import lombok.Data;

@Data
public class UpdateInstallerTaskResponse {

    private String installerTaskId;

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
