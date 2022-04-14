package com.TruckBooking.installerTask.Service;

import com.TruckBooking.TruckBooking.Exception.BusinessException;
import com.TruckBooking.TruckBooking.Exception.EntityNotFoundException;
import com.TruckBooking.installerTask.Dao.InstallerTaskDao;
import com.TruckBooking.installerTask.Entities.InstallerTask;
import com.TruckBooking.installerTask.Model.InstallerTaskRequest;
import com.TruckBooking.installerTask.Response.CreateInstallerTaskResponse;
import com.TruckBooking.installerTask.Response.UpdateInstallerTaskResponse;
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
public class InstallerTaskServiceImpl implements InstallerTaskService {

    @Autowired
    InstallerTaskDao installerTaskDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CreateInstallerTaskResponse addInstallerTask(InstallerTaskRequest installerTaskRequest) {

        List<InstallerTask> installerTaskList = installerTaskDao.findByVehicleNo(installerTaskRequest.getVehicleNo());
        if (!installerTaskList.isEmpty()) {
            throw new BusinessException(": This vehicleNo already exists.");
        }
        else {
            String temp = "";
            InstallerTask installerTask = new InstallerTask();
            CreateInstallerTaskResponse response = new CreateInstallerTaskResponse();

            temp = "installerTaskId:" + UUID.randomUUID();
            installerTask.setInstallerTaskId(temp);
            response.setInstallerTaskId(temp);

            temp = installerTaskRequest.getVehicleNo();
            installerTask.setVehicleNo(temp.trim());
            response.setVehicleNo(temp.trim());

            temp = installerTaskRequest.getVehicleOwnerName();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setVehicleOwnerName(temp.trim());
                response.setVehicleOwnerName(temp.trim());
            }

            temp = installerTaskRequest.getVehicleOwnerPhoneNo();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setVehicleOwnerPhoneNo(temp.trim());
                response.setVehicleOwnerPhoneNo(temp.trim());
            }

            temp = installerTaskRequest.getDriverName();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setDriverName(temp.trim());
                response.setDriverName(temp.trim());
            }

            temp = installerTaskRequest.getDriverPhoneNo();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setDriverPhoneNo(temp.trim());
                response.setDriverPhoneNo(temp.trim());
            }

            temp = installerTaskRequest.getInstallationDate();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setInstallationDate(temp.trim());
                response.setInstallationDate(temp.trim());
            }


            temp = installerTaskRequest.getInstallationCompleteDate();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setInstallationCompleteDate(temp.trim());
                response.setInstallationCompleteDate(temp.trim());
            }

            temp = installerTaskRequest.getInstallationLocation();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setInstallationLocation(temp.trim());
                response.setInstallationLocation(temp.trim());
            }

            temp = installerTaskRequest.getGpsInstallerId();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setGpsInstallerId(temp.trim());
                response.setGpsInstallerId(temp.trim());
            }

            installerTask.setInstallerTaskStatus(InstallerTask.InstallerTaskStatus.Pending);
            response.setInstallerTaskStatus(InstallerTask.InstallerTaskStatus.Pending);


            installerTaskDao.save(installerTask);
            log.info("installer Task Data is saved to the database");
            log.info("addInstallerTask service response is returned");
            return response;
        }
    }



    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<InstallerTask> getSpecifiedInstallerTasks(String vehicleNo, String gpsInstallerId) {
        log.info("getSpecifiedInstallerTasks service with params started");

        if(vehicleNo != null){
            List<InstallerTask> installerTaskList = installerTaskDao.findByVehicleNo(vehicleNo);

            if(installerTaskList.isEmpty())
                throw new EntityNotFoundException(InstallerTask.class, "vehicleNo", vehicleNo);
            else
                return installerTaskList;
        }

        if(gpsInstallerId != null){
            List<InstallerTask> installerTaskList = installerTaskDao.findByGpsInstallerIdAndInstallerTaskStatus(gpsInstallerId, InstallerTask.InstallerTaskStatus.Pending);

            if(installerTaskList.isEmpty())
                throw new EntityNotFoundException(InstallerTask.class, "GpsInstallerId", gpsInstallerId);
            else
                return installerTaskList;
        }

        log.info("getSpecifiedInstallerTasks service response is returned");
        return installerTaskDao.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public InstallerTask getInstallerTask(String installerTaskId) {
        log.info("getInstallerTask service by Id is started");
        Optional<InstallerTask> installerTask = installerTaskDao.findById(installerTaskId);
        if (installerTask.isEmpty())
            throw new EntityNotFoundException(InstallerTask.class, "ID",  installerTaskId.toString());
        log.info("getInstallerTask service response is returned");
        return installerTask.get();
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateInstallerTaskResponse updateInstallerTask(String vehicleNo, InstallerTaskRequest installerTaskRequest) {
        log.info("updateInstallerTask is started");

        List<InstallerTask> installerTaskList
                = installerTaskDao.findByVehicleNo(vehicleNo);

        if(installerTaskList.isEmpty()){
            throw new EntityNotFoundException(InstallerTask.class, "VehicleNo", vehicleNo);
        }
        else {

            String temp = "";
            InstallerTask installerTask = installerTaskList.get(0);
            UpdateInstallerTaskResponse response = new UpdateInstallerTaskResponse();

            response.setInstallerTaskId(installerTask.getInstallerTaskId());
            response.setVehicleNo(installerTask.getVehicleNo());
            response.setInstallerTaskStatus(installerTask.getInstallerTaskStatus());

            temp = installerTaskRequest.getVehicleOwnerName();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setVehicleOwnerName(temp.trim());
                response.setVehicleOwnerName(temp.trim());
            }

            temp = installerTaskRequest.getVehicleOwnerPhoneNo();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setVehicleOwnerPhoneNo(temp.trim());
                response.setVehicleOwnerPhoneNo(temp.trim());
            }

            temp = installerTaskRequest.getDriverName();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setDriverName(temp.trim());
                response.setDriverName(temp.trim());
            }

            temp = installerTaskRequest.getDriverPhoneNo();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setDriverPhoneNo(temp.trim());
                response.setDriverPhoneNo(temp.trim());
            }

            temp = installerTaskRequest.getInstallationDate();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setInstallationDate(temp.trim());
                response.setInstallationDate(temp.trim());
            }


            temp = installerTaskRequest.getInstallationCompleteDate();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setInstallationCompleteDate(temp.trim());
                response.setInstallationCompleteDate(temp.trim());
            }

            temp = installerTaskRequest.getInstallationLocation();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setInstallationLocation(temp.trim());
                response.setInstallationLocation(temp.trim());
            }

            temp = installerTaskRequest.getGpsInstallerId();
            if (StringUtils.isNotBlank(temp)) {
                installerTask.setGpsInstallerId(temp.trim());
                response.setGpsInstallerId(temp.trim());
            }
            if(installerTaskRequest.getInstallerTaskStatus()!=null)
            {
                if(String.valueOf(installerTaskRequest.getInstallerTaskStatus()).equals("Pending"))
                {
                    installerTask.setInstallerTaskStatus(InstallerTask.InstallerTaskStatus.Pending);

                }
                else if (String.valueOf(installerTaskRequest.getInstallerTaskStatus()).equals("Assigned"))
                { installerTask.setInstallerTaskStatus(InstallerTask.InstallerTaskStatus.Assigned); }

                else if(String.valueOf(installerTaskRequest.getInstallerTaskStatus()).equals("Completed"))
                {
                    installerTask.setInstallerTaskStatus(InstallerTask.InstallerTaskStatus.Completed);
                }
                else
                {
                    throw  new BusinessException("Unknown Status");
                }
            }

            installerTaskDao.save(installerTask);
            log.info("installerTask is updated in the database");
            log.info("updateInstallerTask service response is returned");
            return response;
        }
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteInstallerTask(String installerTaskId) {
        log.info("InstallerTask service is started");
        Optional<InstallerTask> installerTask = installerTaskDao.findById(installerTaskId);
        if (installerTask.isEmpty())
            throw new EntityNotFoundException(InstallerTask.class, "InstallerTaskId", installerTaskId.toString());
        installerTaskDao.delete(installerTask.get());
        log.info("InstallerTask is deleted successfully");
    }

}
