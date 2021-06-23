package com.TruckBooking.TruckBooking.Service;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Dao.LoadDao;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Entities.Load.UnitValue;
import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.TruckBooking.Response.CreateLoadResponse;
import com.TruckBooking.TruckBooking.Response.DeleteLoadResponse;
import com.TruckBooking.TruckBooking.Response.UpdateLoadResponse;

@Service
public class LoadServiceImpl implements LoadService {
	@Autowired
	LoadDao loadDao;
	
	@Override
	public CreateLoadResponse addLoad(LoadRequest addLoad) {
		// TODO Auto-generated method stub
		CreateLoadResponse createloadResponse = new CreateLoadResponse();
		Load load = new Load();
		
		if(addLoad.getLoadingPoint()==null) {
			createloadResponse.setStatus(CommonConstants.loadingError);
			return createloadResponse;
		}
		if(addLoad.getLoadingPointCity()==null) {
			createloadResponse.setStatus(CommonConstants.loadingCityError);
			return createloadResponse;
		}
		if(addLoad.getLoadingPointState()==null) {
			createloadResponse.setStatus(CommonConstants.loadingStateError);
			return createloadResponse;
		}
		
		if(addLoad.getUnloadingPointCity()==null) {
			createloadResponse.setStatus(CommonConstants.unloadingCityError);
			return createloadResponse;
		}
		if(addLoad.getUnloadingPointState()==null) {
			createloadResponse.setStatus(CommonConstants.unloadingStateError);
			return createloadResponse;
		}
		if(addLoad.getUnloadingPoint()==null) {
			createloadResponse.setStatus(CommonConstants.unloadingError);
			return createloadResponse;
		}
		
		if(addLoad.getNoOfTrucks()==null) {
			createloadResponse.setStatus(CommonConstants.noOfTrucksError);
			return createloadResponse;
		}
		if(addLoad.getTruckType()==null) {
			createloadResponse.setStatus(CommonConstants.truckTypeError);
			return createloadResponse;
		}
		if(addLoad.getProductType()==null) {
			createloadResponse.setStatus(CommonConstants.productTypeError);
			return createloadResponse;
		}
		if(addLoad.getWeight()==null) {
			createloadResponse.setStatus(CommonConstants.weightError);
			return createloadResponse;
		}
		if(addLoad.getPostLoadId()==null) {
			createloadResponse.setStatus(CommonConstants.idError);
			return createloadResponse;
		}
		if(addLoad.getLoadDate()==null) {
			createloadResponse.setStatus(CommonConstants.dateError);
			return createloadResponse;
		}
		
		
		
		
		if(addLoad.getLoadingPoint() != null) {
			if(addLoad.getLoadingPoint().trim().length() < 1) {
				createloadResponse.setStatus("Empty loading point");
				return createloadResponse;
			}
			load.setLoadingPoint(addLoad.getLoadingPoint().trim());
		}
		if(addLoad.getLoadingPointCity() != null) {
			if(addLoad.getLoadingPointCity().trim().length() < 1) {
				createloadResponse.setStatus("Empty loading city");
				return createloadResponse;
			}
			load.setLoadingPointCity(addLoad.getLoadingPointCity().trim());
		}
		if(addLoad.getLoadingPointState() != null) {
			if(addLoad.getLoadingPointState().trim().length() < 1) {
				createloadResponse.setStatus("Empty loading state");
				return createloadResponse;
			}
			load.setLoadingPointState(addLoad.getLoadingPointState().trim());
		}
		
		if(addLoad.getUnloadingPoint()!= null) {
			if(addLoad.getUnloadingPoint().trim().length() < 1) {
				createloadResponse.setStatus("Empty unloading point");
				return createloadResponse;
			}
			load.setUnloadingPoint(addLoad.getUnloadingPoint().trim());
		}
		if(addLoad.getUnloadingPointCity() != null) {
			if(addLoad.getUnloadingPointCity().trim().length() < 1) {
				createloadResponse.setStatus("Empty unloading city");
				return createloadResponse;
			}
			load.setUnloadingPointCity(addLoad.getUnloadingPointCity().trim());
		}
		if(addLoad.getUnloadingPointState() != null) {
			if(addLoad.getUnloadingPointState().trim().length() < 1) {
				createloadResponse.setStatus("Empty unloading state");
				return createloadResponse;
			}
			load.setUnloadingPointState(addLoad.getUnloadingPointState().trim());
		}
		
		if(addLoad.getNoOfTrucks() != null) {
			if(addLoad.getNoOfTrucks().trim().length() < 1) {
				createloadResponse.setStatus("Empty truck number point");
				return createloadResponse;
			}
			load.setNoOfTrucks(addLoad.getNoOfTrucks().trim());
		}
		
		if(addLoad.getTruckType() != null) {
			if(addLoad.getTruckType().trim().length() < 1) {
				createloadResponse.setStatus("Empty truck type");
				return createloadResponse;
			}
			load.setTruckType(addLoad.getTruckType().trim());
		}
		
		if(addLoad.getProductType() != null) {
			if(addLoad.getProductType().trim().length() < 1) {
				createloadResponse.setStatus("Empty product type");
				return createloadResponse;
			}
			load.setProductType(addLoad.getProductType().trim());
		}
		
		if(addLoad.getWeight() != null) {
			if(addLoad.getWeight().trim().length() < 1) {
				createloadResponse.setStatus("Empty product type");
				return createloadResponse;
			}
			load.setWeight(addLoad.getWeight().trim());
		}
		
		if(addLoad.getLoadDate() != null) {
			if(addLoad.getLoadDate().trim().length() < 1) {
				createloadResponse.setStatus("Empty date");
				return createloadResponse;
			}
			load.setLoadDate(addLoad.getLoadDate().trim());
		}
		
		if(addLoad.getPostLoadId() != null) {
			if(addLoad.getPostLoadId().trim().length() < 1) {
				createloadResponse.setStatus("Empty Id");
				return createloadResponse;
			}
			load.setPostLoadId(addLoad.getPostLoadId().trim());
		}
		if(addLoad.getRate() != null)
		{
			load.setRate(addLoad.getRate());
		}
		if(addLoad.getUnitValue() != null)
		{
			if("PER_TON".equals(String.valueOf(addLoad.getUnitValue())))
			{
				//PER_TON, PER_TRUCK
				load.setUnitValue(UnitValue.PER_TON);
			}
			else if("PER_TRUCK".equals(String.valueOf(addLoad.getUnitValue())))
			{
				load.setUnitValue(UnitValue.PER_TRUCK);
			}
		}
		
		// adding comment
	    load.setComment(addLoad.getComment());
		//
		
		load.setLoadId("load:"+UUID.randomUUID());
		load.setStatus(CommonConstants.pending);
		loadDao.save(load);
		
		createloadResponse.setStatus(CommonConstants.pending);
		createloadResponse.setLoadId(load.getLoadId());
		createloadResponse.setLoadingPoint(load.getLoadingPoint());
		createloadResponse.setLoadingPointCity(load.getLoadingPointCity());
		createloadResponse.setLoadingPointState(load.getLoadingPointState());
		createloadResponse.setUnloadingPoint(load.getUnloadingPoint());
		createloadResponse.setUnloadingPointCity(load.getUnloadingPointCity());
		createloadResponse.setUnloadingPointState(load.getUnloadingPointState());
		createloadResponse.setNoOfTrucks(load.getNoOfTrucks());
		createloadResponse.setProductType(load.getProductType());
		createloadResponse.setTruckType(load.getTruckType());
		createloadResponse.setWeight(load.getWeight());
		createloadResponse.setLoadDate(load.getLoadDate());
		createloadResponse.setComment(load.getComment());
		createloadResponse.setPostLoadId(load.getPostLoadId());
		createloadResponse.setRate(load.getRate());
		
		if(load.getUnitValue()!=null)
		{
			if("PER_TON".equals(String.valueOf(load.getUnitValue())))
			{
				//PER_TON, PER_TRUCK
				createloadResponse.setUnitValue(com.TruckBooking.TruckBooking.Response.CreateLoadResponse.UnitValue.PER_TON);
			}
			else if("PER_TRUCK".equals(String.valueOf(load.getUnitValue())))
			{
				createloadResponse.setUnitValue(com.TruckBooking.TruckBooking.Response.CreateLoadResponse.UnitValue.PER_TRUCK);
			}
		}
		
		return createloadResponse;
	}
	
	@Override 
	public List<Load> getLoads(Integer pageNo, String loadingPointCity, String unloadingPointCity,
			String postLoadId, String truckType, String loadDate, boolean suggestedLoads) {
		
		// TODO Auto-generated method stub
		if (pageNo == null)
			pageNo = 0;
		
		Pageable currentPage = PageRequest.of(pageNo, CommonConstants.pagesize);
		
		if(suggestedLoads==true)
		{
			List<Load> load = loadDao.findByAll(currentPage);
			Collections.reverse(load);
			return load;
		}
		//Pageable p = PageRequest.of(pageNo,2);
		if(loadingPointCity != null) {
			if(unloadingPointCity != null) {
				List<Load> load = loadDao.findByLoadingPointCityAndUnloadingPointCity(loadingPointCity, unloadingPointCity, currentPage);
				Collections.reverse(load);
				return load;
			}
			List<Load> load = loadDao.findByLoadingPointCity(loadingPointCity, currentPage);   
			Collections.reverse(load);
			return load;
		}
		
		if(unloadingPointCity != null)
		{
			List<Load> load = loadDao.findByUnloadingPointCity(unloadingPointCity, currentPage);
			Collections.reverse(load);
			return load;
		}
			
		if(postLoadId!=null) {
			List<Load> load = loadDao.findByPostLoadId(postLoadId, currentPage);            
			Collections.reverse(load);
			return load;
		}
			 
		if(truckType!=null) {
			List<Load> load = loadDao.findByTruckType(truckType, currentPage);               
			Collections.reverse(load);
			return load;
		}
		
		if(loadDate!=null) {
			List<Load> load = loadDao.findByLoadDate(loadDate, currentPage);                  
			Collections.reverse(load);
			return load;
		}
		
		List<Load> load = loadDao.findByAll(currentPage);
		Collections.reverse(load);
		return load;
	}
	

	@Override
	public Load getLoad(String loadId) {
		// TODO Auto-generated method stub
		Optional<Load> L = loadDao.findByLoadId(loadId);
		if(L.isEmpty()) {
			return null;
		}
		return L.get();
	}

	@Override
	public UpdateLoadResponse updateLoad(String loadId, LoadRequest updateLoad) {
		UpdateLoadResponse updateloadResponse = new UpdateLoadResponse();
		Load load = new Load();
		
		Optional<Load> L = loadDao.findByLoadId(loadId);
		if(L.isPresent()) {
			load = L.get();
			
			if(updateLoad.getLoadingPoint() != null) {
				if(updateLoad.getLoadingPoint().trim().length() < 1) {
					updateloadResponse.setStatus("Empty loading point");
					return updateloadResponse;
				}
				load.setLoadingPoint(updateLoad.getLoadingPoint().trim());
			}
			if(updateLoad.getLoadingPointCity() != null) {
				if(updateLoad.getLoadingPointCity().trim().length() < 1) {
					updateloadResponse.setStatus("Empty loading city");
					return updateloadResponse;
				}
				load.setLoadingPointCity(updateLoad.getLoadingPointCity().trim());
			}
			if(updateLoad.getLoadingPointState() != null) {
				if(updateLoad.getLoadingPointState().trim().length() < 1) {
					updateloadResponse.setStatus("Empty loading state");
					return updateloadResponse;
				}
				load.setLoadingPointState(updateLoad.getLoadingPointState().trim());
			}
			
			if(updateLoad.getUnloadingPoint()!= null) {
				if(updateLoad.getUnloadingPoint().trim().length() < 1) {
					updateloadResponse.setStatus("Empty unloading point");
					return updateloadResponse;
				}
				load.setUnloadingPoint(updateLoad.getUnloadingPoint().trim());
			}
			if(updateLoad.getUnloadingPointCity() != null) {
				if(updateLoad.getUnloadingPointCity().trim().length() < 1) {
					updateloadResponse.setStatus("Empty unloading city");
					return updateloadResponse;
				}
				load.setUnloadingPointCity(updateLoad.getUnloadingPointCity().trim());
			}
			if(updateLoad.getUnloadingPointState() != null) {
				if(updateLoad.getUnloadingPointState().trim().length() < 1) {
					updateloadResponse.setStatus("Empty unloading state");
					return updateloadResponse;
				}
				load.setUnloadingPointState(updateLoad.getUnloadingPointState().trim());
			}
			
			if(updateLoad.getNoOfTrucks() != null) {
				if(updateLoad.getNoOfTrucks().trim().length() < 1) {
					updateloadResponse.setStatus("Empty truck number point");
					return updateloadResponse;
				}
				load.setNoOfTrucks(updateLoad.getNoOfTrucks().trim());
			}
			
			if(updateLoad.getTruckType() != null) {
				if(updateLoad.getTruckType().trim().length() < 1) {
					updateloadResponse.setStatus("Empty truck type");
					return updateloadResponse;
				}
				load.setTruckType(updateLoad.getTruckType().trim());
			}
			
			if(updateLoad.getProductType() != null) {
				if(updateLoad.getProductType().trim().length() < 1) {
					updateloadResponse.setStatus("Empty product type");
					return updateloadResponse;
				}
				load.setProductType(updateLoad.getProductType().trim());
			}
			
			if(updateLoad.getWeight() != null) {
				if(updateLoad.getWeight().trim().length() < 1) {
					updateloadResponse.setStatus("Empty product type");
					return updateloadResponse;
				}
				load.setWeight(updateLoad.getWeight().trim());
			}
			
			if(updateLoad.getLoadDate() != null) {
				if(updateLoad.getLoadDate().trim().length() < 1) {
					updateloadResponse.setStatus("Empty date");
					return updateloadResponse;
				}
				load.setLoadDate(updateLoad.getLoadDate().trim());
			}
			
			if(updateLoad.getPostLoadId() != null) {
				if(updateLoad.getPostLoadId().trim().length() < 1) {
					updateloadResponse.setStatus("Empty shipper Id");
					return updateloadResponse;
				}
				load.setPostLoadId(updateLoad.getPostLoadId().trim());
			}
			//
			
			if(updateLoad.getStatus() != null) {
				if(updateLoad.getStatus().trim().length() < 1) {
					updateloadResponse.setStatus("Empty comment");
					return updateloadResponse;
				}
				load.setStatus(updateLoad.getStatus().trim());
			}
			// updating comment
			if(updateLoad.getComment()!=null)
			{
				load.setComment(updateLoad.getComment());
			}
			if(updateLoad.getRate()!=null)
			{
				load.setRate(updateLoad.getRate());
			}
			if(updateLoad.getUnitValue() != null)
			{
				if("PER_TON".equals(String.valueOf(updateLoad.getUnitValue())))
				{
					//PER_TON, PER_TRUCK
					load.setUnitValue(UnitValue.PER_TON);
				}
				else if("PER_TRUCK".equals(String.valueOf(updateLoad.getUnitValue())))
				{
					load.setUnitValue(UnitValue.PER_TRUCK);
				}
			}
			
			loadDao.save(load);
			updateloadResponse.setStatus(CommonConstants.updateSuccess);
			updateloadResponse.setLoadId(load.getLoadId());
			updateloadResponse.setLoadingPoint(load.getLoadingPoint());
			updateloadResponse.setLoadingPointCity(load.getLoadingPointCity());
			updateloadResponse.setLoadingPointState(load.getLoadingPointState());
			updateloadResponse.setUnloadingPoint(load.getUnloadingPoint());
			updateloadResponse.setUnloadingPointCity(load.getUnloadingPointCity());
			updateloadResponse.setUnloadingPointState(load.getUnloadingPointState());
			updateloadResponse.setNoOfTrucks(load.getNoOfTrucks());
			updateloadResponse.setProductType(load.getProductType());
			updateloadResponse.setTruckType(load.getTruckType());
			updateloadResponse.setWeight(load.getWeight());
			updateloadResponse.setLoadDate(load.getLoadDate());
			updateloadResponse.setComment(load.getComment());
			updateloadResponse.setPostLoadId(load.getPostLoadId());
			updateloadResponse.setRate(load.getRate());
			if(load.getUnitValue()!=null)
			{
				if("PER_TON".equals(String.valueOf(load.getUnitValue())))
				{
					//PER_TON, PER_TRUCK
					updateloadResponse.setUnitValue(com.TruckBooking.TruckBooking.Response.UpdateLoadResponse.UnitValue.PER_TON);
				}
				else if("PER_TRUCK".equals(String.valueOf(load.getUnitValue())))
				{
					updateloadResponse.setUnitValue(com.TruckBooking.TruckBooking.Response.UpdateLoadResponse.UnitValue.PER_TRUCK);
				}
			}
			
			return updateloadResponse;
		}
		else {
			updateloadResponse.setStatus(CommonConstants.AccountNotFoundError);
			return updateloadResponse;
		}
	}

	@Override
	public DeleteLoadResponse deleteLoad(String loadId) {
		DeleteLoadResponse deleteloadResponse = new DeleteLoadResponse();
		Optional<Load> L = loadDao.findByLoadId(loadId);
		if(L.isEmpty()) {
			deleteloadResponse.setStatus(CommonConstants.AccountNotFoundError);
			return deleteloadResponse;
		}
		loadDao.delete(L.get());
		deleteloadResponse.setStatus(CommonConstants.deleteSuccess);
		return deleteloadResponse;
	}

}
