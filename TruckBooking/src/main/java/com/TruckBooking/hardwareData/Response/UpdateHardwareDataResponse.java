package com.TruckBooking.hardwareData.Response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateHardwareDataResponse {
    private String HardwareDataId;
    private String imei;
    private String simNumber;
    private String phoneNo;
}
