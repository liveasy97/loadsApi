package com.TruckBooking.TruckBooking.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Dao.LoadDao;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Exception.BusinessException;
import com.TruckBooking.TruckBooking.Exception.EntityNotFoundException;
import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.TruckBooking.Response.CreateLoadResponse;
import com.TruckBooking.TruckBooking.Response.UpdateLoadResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoadServiceImpl implements LoadService {

	@Autowired
	LoadDao loadDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public CreateLoadResponse addLoad(LoadRequest loadrequest) {
		log.info("addLoad service is started");

		String temp = "";
		Load load = new Load();
		CreateLoadResponse response = new CreateLoadResponse();

		temp = "load:" + UUID.randomUUID();
		load.setLoadId(temp);
		response.setLoadId(temp);

		temp = loadrequest.getLoadingPoint().trim();
		load.setLoadingPoint(temp);
		response.setLoadingPoint(temp);

		temp = loadrequest.getLoadingPointCity().trim();
		load.setLoadingPointCity(temp);
		response.setLoadingPointCity(temp);

		temp = loadrequest.getLoadingPointState().trim();
		load.setLoadingPointState(temp);
		response.setLoadingPointState(temp);

		temp = loadrequest.getUnloadingPoint().trim();
		load.setUnloadingPoint(temp);
		response.setUnloadingPoint(temp);

		temp = loadrequest.getUnloadingPointCity().trim();
		load.setUnloadingPointCity(temp);
		response.setUnloadingPointCity(temp);

		temp = loadrequest.getUnloadingPointState().trim();
		load.setUnloadingPointState(temp);
		response.setUnloadingPointState(temp);

		temp = loadrequest.getPostLoadId().trim();
		load.setPostLoadId(temp);
		response.setPostLoadId(temp);

		temp = loadrequest.getProductType().trim();
		load.setProductType(temp);
		response.setProductType(temp);

		temp = loadrequest.getTruckType().trim();
		load.setTruckType(temp);
		response.setTruckType(temp);

		temp = loadrequest.getNoOfTrucks().trim();
		load.setNoOfTrucks(temp);
		response.setNoOfTrucks(temp);

		temp = loadrequest.getWeight().trim();
		load.setWeight(temp);
		response.setWeight(temp);

		temp = loadrequest.getLoadDate().trim();
		load.setLoadDate(temp);
		response.setLoadDate(temp);

		load.setStatus(Load.Status.PENDING);
		response.setStatus(Load.Status.PENDING);

		temp = loadrequest.getComment();
		if (StringUtils.isNotBlank(temp)) {
			load.setComment(temp.trim());
			response.setComment(temp.trim());
		}

		if (loadrequest.getRate() != null) {
			load.setRate(loadrequest.getRate());
			response.setRate(loadrequest.getRate());
			if (loadrequest.getUnitValue() == null)
				throw new BusinessException("UnitValue can't be null when the rate is provided");

			temp = String.valueOf(loadrequest.getUnitValue());
			if ("PER_TON".equals(temp)) {
				load.setUnitValue(Load.UnitValue.PER_TON);
				response.setUnitValue(CreateLoadResponse.UnitValue.PER_TON);
			} else if ("PER_TRUCK".equals(temp)) {
				load.setUnitValue(Load.UnitValue.PER_TRUCK);
				response.setUnitValue(CreateLoadResponse.UnitValue.PER_TRUCK);
			}
		} else if (loadrequest.getUnitValue() != null) {
			throw new BusinessException("UnitValue can't be set when the rate is not provided");
		}

		loadDao.save(load);
		log.info("load is saved to the database");
		log.info("addLoad service response is returned");
		response.setTimestamp(load.getTimestamp());

		return response;
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public List<Load> getLoads(Integer pageNo, String loadingPointCity, String unloadingPointCity, String postLoadId,
			String truckType, String loadDate, boolean suggestedLoads) {
		log.info("getLoads service with params started");

		if (pageNo == null)
			pageNo = 0;

		Pageable currentPage = PageRequest.of(pageNo, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");

		if (suggestedLoads) {
			//List<Load> load = new ArrayList<>(loadDao.findAll(currentPage).getContent());
			// Collections.reverse(load);
			return loadDao.findByStatus(Load.Status.PENDING,currentPage);
		}

		if (loadingPointCity != null) {
			if (unloadingPointCity != null) {
				List<Load> load = loadDao.findByLoadingPointCityAndUnloadingPointCityAndStatus(loadingPointCity,
						unloadingPointCity, Load.Status.PENDING,currentPage);
			
				// Collections.reverse(load);
				return load;
			}
			List<Load> load = loadDao.findByLoadingPointCityAndStatus(loadingPointCity,Load.Status.PENDING, currentPage);
			
			if (load.isEmpty())
				throw new EntityNotFoundException(Load.class, "loadingPointCity", loadingPointCity.toString());
			// Collections.reverse(load);
			return load;
		}

		if (unloadingPointCity != null) {
			List<Load> load = loadDao.findByUnloadingPointCityAndStatus(unloadingPointCity,Load.Status.PENDING, currentPage);
			
			if (load.isEmpty())
				throw new EntityNotFoundException(Load.class, "unloadingPointCity", unloadingPointCity.toString());
			// Collections.reverse(load);
			return load;
			
		}

		if (postLoadId != null) {
			List<Load> load = loadDao.findByPostLoadIdAndStatus(postLoadId, Load.Status.PENDING,currentPage);
			
			if (load.isEmpty())
				throw new EntityNotFoundException(Load.class, "postLoadId", postLoadId.toString());
			// Collections.reverse(load);
			return load;
		}

		if (truckType != null) {
			List<Load> load = loadDao.findByTruckTypeAndStatus(truckType, Load.Status.PENDING,currentPage);
			
			if (load.isEmpty())
				throw new EntityNotFoundException(Load.class, "truckType", truckType.toString());
			// Collections.reverse(load);
			return load;
		}

		if (loadDate != null) {
			List<Load> load = loadDao.findByLoadDateAndStatus(loadDate,Load.Status.PENDING, currentPage);
			
			if (load.isEmpty())
				throw new EntityNotFoundException(Load.class, "loadDate", loadDate.toString());
			// Collections.reverse(load);
			return load;
		}


		log.info("getLoads service response is returned");

		return loadDao.findByStatus(Load.Status.PENDING,currentPage);
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public Load getLoad(String loadId) {
		log.info("getLoad service by Id is started");
		Optional<Load> load = loadDao.findByLoadId(loadId);
		if (load.isEmpty())
			throw new EntityNotFoundException(Load.class, "id", loadId.toString());
		log.info("getLoad service response is returned");
		return load.get();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public UpdateLoadResponse updateLoad(String loadId, LoadRequest updateLoad) {
		log.info("updateLoad service is started");

		Optional<Load> Id = loadDao.findByLoadId(loadId);
		if (Id.isEmpty())
			throw new EntityNotFoundException(Load.class, "id", loadId.toString());

		String temp = "";
		Load load = Id.get();

		temp = updateLoad.getLoadingPoint();
		if (StringUtils.isNotBlank(temp)) {
			load.setLoadingPoint(temp.trim());
		}

		temp = updateLoad.getLoadingPointCity();
		if (StringUtils.isNotBlank(temp)) {
			load.setLoadingPointCity(temp.trim());
		}

		temp = updateLoad.getLoadingPointState();
		if (StringUtils.isNotBlank(temp)) {
			load.setLoadingPointState(temp.trim());
		}

		temp = updateLoad.getUnloadingPoint();
		if (StringUtils.isNotBlank(temp)) {
			load.setUnloadingPoint(temp.trim());
		}

		temp = updateLoad.getUnloadingPointCity();
		if (StringUtils.isNotBlank(temp)) {
			load.setUnloadingPointCity(temp.trim());
		}

		temp = updateLoad.getUnloadingPointState();
		if (StringUtils.isNotBlank(temp)) {
			load.setUnloadingPointState(temp.trim());
		}

		temp = updateLoad.getNoOfTrucks();
		if (StringUtils.isNotBlank(temp)) {
			load.setNoOfTrucks(temp.trim());
		}

		temp = updateLoad.getTruckType();
		if (StringUtils.isNotBlank(temp)) {
			load.setTruckType(temp.trim());
		}

		temp = updateLoad.getProductType();
		if (StringUtils.isNotBlank(temp)) {
			load.setProductType(temp.trim());
		}

		temp = updateLoad.getWeight();
		if (StringUtils.isNotBlank(temp)) {
			load.setWeight(temp.trim());
		}

		temp = updateLoad.getLoadDate();
		if (StringUtils.isNotBlank(temp)) {
			load.setLoadDate(temp.trim());
		}

		temp = updateLoad.getPostLoadId();
		if (StringUtils.isNotBlank(temp)) {
			load.setPostLoadId(temp);
		}

		if (updateLoad.getComment() != null) {
			load.setComment(updateLoad.getComment());
		}

		if (updateLoad.getStatus() != null) {

			if (String.valueOf(updateLoad.getStatus()).equals("PENDING")) {
				load.setStatus(Load.Status.PENDING);
			} else if (String.valueOf(updateLoad.getStatus()).equals("ON_GOING")) {
				load.setStatus(Load.Status.ON_GOING);
			} else if (String.valueOf(updateLoad.getStatus()).equals("COMPLETED")) {
				load.setStatus(Load.Status.COMPLETED);
			} else if (String.valueOf(updateLoad.getStatus()).equals("EXPIRED")) {
				load.setStatus(Load.Status.EXPIRED);
			} else {
				throw new BusinessException("Unknown status");

			}
		}

		if (updateLoad.getRate() != null) {
			load.setRate(updateLoad.getRate());
			if (updateLoad.getUnitValue() == null)
				throw new BusinessException("UnitValue can't be null when the rate is provided");

			temp = String.valueOf(updateLoad.getUnitValue());

			if ("PER_TON".equals(temp)) {
				load.setUnitValue(Load.UnitValue.PER_TON);
			} else if ("PER_TRUCK".equals(temp)) {
				load.setUnitValue(Load.UnitValue.PER_TRUCK);
			}
		} else if (updateLoad.getUnitValue() != null) {
			throw new BusinessException("UnitValue can't be set when the rate is not provided");
		}

		UpdateLoadResponse response = new UpdateLoadResponse();
		response.setLoadId(loadId);
		response.setLoadingPoint(load.getLoadingPoint());
		response.setLoadingPointCity(load.getLoadingPointCity());
		response.setLoadingPointState(load.getLoadingPointState());
		response.setUnloadingPoint(load.getUnloadingPoint());
		response.setUnloadingPointCity(load.getUnloadingPointCity());
		response.setUnloadingPointState(load.getUnloadingPointState());
		response.setPostLoadId(load.getPostLoadId());
		response.setProductType(load.getProductType());
		response.setTruckType(load.getTruckType());
		response.setNoOfTrucks(load.getNoOfTrucks());
		response.setWeight(load.getWeight());
		response.setLoadDate(load.getLoadDate());
		response.setComment(load.getComment());
		response.setStatus(load.getStatus());
		response.setRate(load.getRate());
		response.setTimestamp(load.getTimestamp());

		temp = String.valueOf(load.getUnitValue());
		if ("PER_TON".equals(temp)) {
			response.setUnitValue(UpdateLoadResponse.UnitValue.PER_TON);
		} else if ("PER_TRUCK".equals(temp)) {
			response.setUnitValue(UpdateLoadResponse.UnitValue.PER_TRUCK);
		}

		loadDao.save(load);
		log.info("load is updated in the database");
		log.info("updateLoad service response is returned");
		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteLoad(String loadId) {
		log.info("deleteLoad service is started");
		Optional<Load> L = loadDao.findByLoadId(loadId);
		if (L.isEmpty())
			throw new EntityNotFoundException(Load.class, "id", loadId.toString());
		loadDao.delete(L.get());
		log.info("load is deleted successfully");
	}

}
