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
		
		createloadResponse = checkField(addLoad.getLoadingPoint(), CommonConstants.loadingError, CommonConstants.emptyloadingpoint, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
		
		createloadResponse = checkField(addLoad.getLoadingPointCity(), CommonConstants.loadingCityError, CommonConstants.emptyloadingcity, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
		
		createloadResponse = checkField(addLoad.getLoadingPointState(), CommonConstants.loadingStateError, CommonConstants.emptyloadingstate, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
		
		createloadResponse = checkField(addLoad.getUnloadingPoint(), CommonConstants.unloadingError, CommonConstants.emptyunloadingpoint, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}

		createloadResponse = checkField(addLoad.getUnloadingPointCity(), CommonConstants.unloadingCityError, CommonConstants.emptyunloadingcity, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
				
		createloadResponse = checkField(addLoad.getUnloadingPointState(), CommonConstants.unloadingStateError, CommonConstants.emptyunloadingstate, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
				
		createloadResponse = checkField(addLoad.getNoOfTrucks(), CommonConstants.noOfTrucksError, CommonConstants.emptytruckno, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
		
		createloadResponse = checkField(addLoad.getTruckType(), CommonConstants.truckTypeError, CommonConstants.emptytrucktype, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
		
		createloadResponse = checkField(addLoad.getProductType(), CommonConstants.productTypeError, CommonConstants.emptyproducttype, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}

		createloadResponse = checkField(addLoad.getWeight(), CommonConstants.weightError, CommonConstants.emptyweight, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
		
		createloadResponse = checkField(addLoad.getPostLoadId(), CommonConstants.idError, CommonConstants.emptypostloadid, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
		
		createloadResponse = checkField(addLoad.getLoadDate(), CommonConstants.dateError, CommonConstants.emptydate, createloadResponse);
		if(createloadResponse.getStatus()!=null) {
			return createloadResponse;
		}
		
		load.setLoadingPoint(addLoad.getLoadingPoint().trim());
		load.setLoadingPointCity(addLoad.getLoadingPointCity().trim());
		load.setLoadingPointState(addLoad.getLoadingPointState().trim());
		load.setUnloadingPoint(addLoad.getUnloadingPoint().trim());
		load.setUnloadingPointCity(addLoad.getUnloadingPointCity().trim());
		load.setUnloadingPointState(addLoad.getUnloadingPointState().trim());
		load.setNoOfTrucks(addLoad.getNoOfTrucks().trim());
		load.setTruckType(addLoad.getTruckType().trim());
		load.setProductType(addLoad.getProductType().trim());
		load.setWeight(addLoad.getWeight().trim());
		load.setPostLoadId(addLoad.getPostLoadId().trim());
		load.setLoadDate(addLoad.getLoadDate().trim());
		
		if(addLoad.getRate() != null)
		{
			load.setRate(addLoad.getRate());
		}

		if(addLoad.getUnitValue() != null)
		{
			String unitValue=String.valueOf(addLoad.getUnitValue());
			
			if("PER_TON".equals(unitValue))
			{
				load.setUnitValue(Load.UnitValue.PER_TON);
			}
			else if("PER_TRUCK".equals(unitValue))
			{
				load.setUnitValue(Load.UnitValue.PER_TRUCK);
			}
		}
		
	    load.setComment(addLoad.getComment());
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
		
		if(load.getUnitValue() != null)
		{
			String unitValue=String.valueOf(load.getUnitValue());
			if("PER_TON".equals(unitValue))
			{
				createloadResponse.setUnitValue(CreateLoadResponse.UnitValue.PER_TON);
			}
			else if("PER_TRUCK".equals(unitValue))
			{
				createloadResponse.setUnitValue(CreateLoadResponse.UnitValue.PER_TRUCK);
			}
		}
		
		return createloadResponse;
	}
	
	private CreateLoadResponse checkField(String value, String errorMsg, String emptyErrorMsg, CreateLoadResponse createloadResponse) {
		
		if(value==null) {
			createloadResponse.setStatus(errorMsg);
		}
		else {
			if(value.trim().length() < 1) {
				createloadResponse.setStatus(emptyErrorMsg);
			}
		}
		return createloadResponse;
	}
	
	@Override
	public List<Load> getLoads(Integer pageNo, String loadingPointCity, String unloadingPointCity,
			String postLoadId, String truckType, String loadDate, boolean suggestedLoads) {
		
		if (pageNo == null)
			pageNo = 0;
		
		Pageable currentPage = PageRequest.of(pageNo, CommonConstants.pagesize);
		
		if(suggestedLoads)
		{
			List<Load> load = loadDao.findByAll(currentPage);
			Collections.reverse(load);
			return load;
		}
		
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
			
		if(postLoadId != null) {
			List<Load> load = loadDao.findByPostLoadId(postLoadId, currentPage);            
			Collections.reverse(load);
			return load;
		}
			 
		if(truckType != null) {
			List<Load> load = loadDao.findByTruckType(truckType, currentPage);               
			Collections.reverse(load);
			return load;
		}
		
		if(loadDate != null) {
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
			
			updateloadResponse = checkField(updateLoad.getLoadingPoint(), CommonConstants.emptyloadingpoint, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}

			updateloadResponse = checkField(updateLoad.getLoadingPointCity(), CommonConstants.emptyloadingcity, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getLoadingPointState(), CommonConstants.emptyloadingstate, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getUnloadingPoint(), CommonConstants.emptyunloadingpoint, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getUnloadingPointCity(), CommonConstants.emptyunloadingcity, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getUnloadingPointState(), CommonConstants.emptyunloadingstate, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getNoOfTrucks(), CommonConstants.emptytruckno, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getTruckType(), CommonConstants.emptytrucktype, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getProductType(), CommonConstants.emptyproducttype, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getWeight(), CommonConstants.emptyweight, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getLoadDate(), CommonConstants.emptydate, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getPostLoadId(), CommonConstants.emptypostloadid, updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			updateloadResponse = checkField(updateLoad.getStatus(), "Empty status", updateloadResponse);
			if(updateloadResponse.getStatus()!=null) {
				return updateloadResponse;
			}
			
			load.setLoadingPoint(updateLoad.getLoadingPoint()!=null ? updateLoad.getLoadingPoint().trim() : null);
			load.setLoadingPointCity(updateLoad.getLoadingPointCity()!=null ? updateLoad.getLoadingPointCity().trim() : null);
			load.setLoadingPointState(updateLoad.getLoadingPointState()!=null ? updateLoad.getLoadingPointState().trim() : null);
			load.setUnloadingPoint(updateLoad.getUnloadingPoint()!=null ? updateLoad.getUnloadingPoint().trim() : null);
			load.setUnloadingPointCity(updateLoad.getUnloadingPointCity()!=null ? updateLoad.getUnloadingPointCity().trim() : null);
			load.setUnloadingPointState(updateLoad.getUnloadingPointState()!=null ? updateLoad.getUnloadingPointState().trim() : null);
			load.setNoOfTrucks(updateLoad.getNoOfTrucks()!=null ? updateLoad.getNoOfTrucks().trim() : null);
			load.setTruckType(updateLoad.getTruckType()!=null ? updateLoad.getTruckType().trim() : null);
			load.setProductType(updateLoad.getProductType()!=null ? updateLoad.getProductType().trim() : null);
			load.setWeight(updateLoad.getWeight()!=null ? updateLoad.getWeight().trim() : null);
			load.setLoadDate(updateLoad.getLoadDate()!=null ? updateLoad.getLoadDate().trim() : null);
			load.setPostLoadId(updateLoad.getPostLoadId()!=null ? updateLoad.getPostLoadId().trim() : null);
			load.setStatus(updateLoad.getStatus()!=null ? updateLoad.getStatus().trim() : null);
			
			if(updateLoad.getComment() != null)
			{
				load.setComment(updateLoad.getComment());
			}
			
			if(updateLoad.getRate() != null)
			{
				load.setRate(updateLoad.getRate());
			}
			
			if(updateLoad.getUnitValue() != null)
			{
				String unitValue=String.valueOf(updateLoad.getUnitValue());
				
				if("PER_TON".equals(unitValue))
				{
					load.setUnitValue(Load.UnitValue.PER_TON);
				}
				else if("PER_TRUCK".equals(unitValue))
				{
					load.setUnitValue(Load.UnitValue.PER_TRUCK);
				}
			}
			
			loadDao.save(load);
			
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
			updateloadResponse.setStatus(load.getStatus());
			updateloadResponse.setLoadDate(load.getLoadDate());
			updateloadResponse.setComment(load.getComment());
			updateloadResponse.setPostLoadId(load.getPostLoadId());
			updateloadResponse.setRate(load.getRate());
			
			if(load.getUnitValue() != null)
			{
				String unitValue=String.valueOf(load.getUnitValue());
				if("PER_TON".equals(unitValue))
				{
					updateloadResponse.setUnitValue(UpdateLoadResponse.UnitValue.PER_TON);
				}
				else if("PER_TRUCK".equals(unitValue))
				{
					updateloadResponse.setUnitValue(UpdateLoadResponse.UnitValue.PER_TRUCK);
				}
			}
			
			return updateloadResponse;
		}
		else {
			updateloadResponse.setStatus(CommonConstants.AccountNotFoundError);
			return updateloadResponse;
		}
	}

	private UpdateLoadResponse checkField(String value, String errorMsg, UpdateLoadResponse updateloadResponse) {
		if(value != null) {
			if(value.trim().length() < 1) {
				updateloadResponse.setStatus(errorMsg);
				return updateloadResponse;
			}
		}
		return updateloadResponse;
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
