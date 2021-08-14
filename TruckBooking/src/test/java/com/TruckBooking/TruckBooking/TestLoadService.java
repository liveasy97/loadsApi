
package com.TruckBooking.TruckBooking;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.TruckBooking.TruckBooking.Exception.BusinessException;
import com.TruckBooking.TruckBooking.Exception.EntityNotFoundException;
import com.TruckBooking.TruckBooking.Exception.LoadErrorResponse;
import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Dao.LoadDao;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Entities.Load.Status;
import com.TruckBooking.TruckBooking.Exception.LoadSubErrorResponse;
import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.TruckBooking.Model.LoadRequest.UnitValue;
import com.TruckBooking.TruckBooking.Response.CreateLoadResponse;
import com.TruckBooking.TruckBooking.Response.DeleteLoadResponse;
import com.TruckBooking.TruckBooking.Response.UpdateLoadResponse;
import com.TruckBooking.TruckBooking.Service.LoadService;
import com.TruckBooking.TruckBooking.Service.LoadServiceImpl;

import net.minidev.json.JSONObject;

@SpringBootTest
public class TestLoadService {
	
private static Validator validator;
	
    @BeforeAll
    public static void setUp() {
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
       validator = factory.getValidator();
    }

	@Autowired
	LoadServiceImpl loadservice;

	@MockBean
	LoadDao loaddao;

	@Test
	@Order(1)
	public void addLoad() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
				 
		Load load = createLoads().get(0);

		CreateLoadResponse createloadresponse = new CreateLoadResponse("load:862064c2-f58c-4758-986c-000000000000", "Nagpur","Nagpur" , "Maharashtra", "Raipur", "Raipur", "Chhattisgarh", "id:1", "Gold","OPEN_HALF_BODY" ,
				"6", "1000kg", "22/01/2021", Load.Status.PENDING, "add comment", (long) 100, CreateLoadResponse.UnitValue.PER_TON, Timestamp.valueOf("2021-07-28 23:28:50.134"));

		when(loaddao.save(load)).thenReturn(load);

		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		createloadresponse1.setLoadId("load:862064c2-f58c-4758-986c-000000000000");
		createloadresponse1.setTimestamp(Timestamp.valueOf("2021-07-28 23:28:50.134"));
		

		assertEquals(createloadresponse, createloadresponse1);
	}
	

	// null loding poi nt
	@Test
	@Order(2)
	public void addLoadfail1() {
		LoadRequest loadrequest = new LoadRequest(null, "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );
		
		
		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("Loading Point Cannot Be Empty"));
		
		
	}
	
	// null loding city
	@Test
	@Order(3)
	public void addLoadfail2() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", null, "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );
		

		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("Loading Point City Cannot Be Empty"));
	}
	

	// loadingstate null
	@Test
	@Order(4)
	public void addLoadfail3() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", null,null, "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );

		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(2, constraintViolations.size());
		
		assertTrue(set.contains("Loading Point State Cannot Be Empty"));
		assertTrue(set.contains("Loading Point City Cannot Be Empty"));
	}
	
	// post load id error
	@Test
	@Order(5)
	public void addLoadfail4() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur","Maharashtra", null, "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );

		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("PostLoad Id Cannot Be Empty"));
	}
	
	// unloadingpoint null
	@Test
	@Order(6)
	public void addLoadfail5() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur","Maharashtra","id:1" ,null , "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );

		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("Unloading Point Cannot Be Empty"));
	}

	// unloading city error
	@Test
	@Order(7)
	public void addLoadfail6() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur","Maharashtra","id:1" ,"Raipur" , null,
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );
		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("Unloading Point City Cannot Be Empty"));
	}

		// unloading state error
	@Test
	@Order(8)
	public void addLoadfail7() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur","Maharashtra","id:1" ,"Raipur" , "Raipur",
				null, "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );
		
		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());

		assertEquals(1, constraintViolations.size());
		assertTrue(set.contains("Unloading Point State Cannot Be Empty"));
		

		
	assertEquals("Unloading Point State Cannot Be Empty", constraintViolations.iterator().next().getMessage());
	}
	// product type null
	@Test
	@Order(9)
	public void addLoadfail8() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur","Maharashtra","id:1" ,"Raipur" , "Raipur",
				"Chhattisgarh", null, "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );

		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("Product Type Cannot Be Empty"));
	}
	// trucktype null
	@Test
	@Order(10)
	public void addLoadfail9() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur","Maharashtra","id:1" ,"Raipur" , "Raipur",
				"Chhattisgarh", "Gold", null, "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );

		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("Truck Type Cannot Be Empty"));
	}

	// no of truck
	@Test
	@Order(11)
	public void addLoadfail10() {
		
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur","Maharashtra","id:1" ,"Raipur" , "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", null, "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );
	
		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("No. of trucks Cannot Be Empty"));
	}

	// weight null
	@Test
	@Order(12)
	public void addLoadfail11() {
		
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur","Maharashtra","id:1" ,"Raipur" , "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", null,"add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );
	
		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("Weight Cannot Be Empty"));
	}
	// date null
	@Test
	@Order(13)
	public void addLoadfail12() {
		
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur","Maharashtra","id:1" ,"Raipur" , "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", null, (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		Set<ConstraintViolation<LoadRequest>> constraintViolations = validator.validate( loadrequest );

		Iterator<ConstraintViolation<LoadRequest>> itr= constraintViolations.iterator();
		Set<String> set =new HashSet<String>();
		
		while(itr.hasNext()) set.add(itr.next().getMessage());
		
		
		System.err.println(set.toString());
		
		
		assertEquals(1, constraintViolations.size());
		
		assertTrue(set.contains("Load Date Cannot Be Empty"));
	}

// comment null
	@Test
	@Order(14)
	public void addLoadnullcomment() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg",null, "22/01/2021", (long) 100,LoadRequest.UnitValue.PER_TON, Load.Status.PENDING
				);
		Load load = createLoads().get(0);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse("load:862064c2-f58c-4758-986c-000000000000", "Nagpur","Nagpur" , "Maharashtra", "Raipur", "Raipur", "Chhattisgarh", "id:1", "Gold","OPEN_HALF_BODY" ,
				"6", "1000kg", "22/01/2021", Load.Status.PENDING, null, (long) 100, CreateLoadResponse.UnitValue.PER_TON, Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		
		
		when(loaddao.save(load)).thenReturn(load);
	
				CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
				createloadresponse1.setLoadId("load:862064c2-f58c-4758-986c-000000000000");
				createloadresponse1.setTimestamp(Timestamp.valueOf("2021-07-28 23:28:50.134"));
				
				

		assertEquals(createloadresponse, createloadresponse1);
	}

	// status null
	@Test
	@Order(15)
	public void addLoadnullstatus() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,LoadRequest.UnitValue.PER_TON, null
				);
		Load load = createLoads().get(0);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse("load:862064c2-f58c-4758-986c-000000000000", "Nagpur","Nagpur" , "Maharashtra", "Raipur", "Raipur", "Chhattisgarh", "id:1", "Gold","OPEN_HALF_BODY" ,
				"6", "1000kg", "22/01/2021", Load.Status.PENDING, "add comment", (long) 100, CreateLoadResponse.UnitValue.PER_TON, Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		
		
		when(loaddao.save(load)).thenReturn(load);
	
				CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
				createloadresponse1.setLoadId("load:862064c2-f58c-4758-986c-000000000000");
				createloadresponse1.setTimestamp(Timestamp.valueOf("2021-07-28 23:28:50.134"));
				
				

		assertEquals(createloadresponse, createloadresponse1);
	}

			// rate null
	@Test
	@Order(16)
	public void addLoadnullrate() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", null,  UnitValue.PER_TON, Load.Status.PENDING);
		
		Throwable exception = assertThrows(
				BusinessException.class, () -> {
					 loadservice.addLoad(loadrequest);		  
					
    }
				
);
	
		assertEquals("ErrorUnitValue can't be set when the rate is not provided", exception.getMessage());

	}

// unit value null
	@Test
	@Order(17)
	public void addLoadnullunitvalue() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long)100, null, Load.Status.PENDING);
		
		Throwable exception = assertThrows(
				BusinessException.class, () -> {
					 loadservice.addLoad(loadrequest);
					
    }
);
		assertEquals("ErrorUnitValue can't be null when the rate is provided", exception.getMessage());

	}

 @Test
	@Order(18)
	public void getLoadsByLoadId() {
		Load load = createLoads().get(0);
		when(loaddao.findByLoadId("load:0a5f1700-041a-43d4-b3eb-000000000001")).thenReturn(Optional.of(load));
		assertEquals(load, loadservice.getLoad("load:0a5f1700-041a-43d4-b3eb-000000000001"));
	}
 
 @Test
	@Order(12)
	public void getLoad_id_not_present()
	{
		when(loaddao.findById("load:0de885e0-5f43-4c68-8dde-0000000000000")).thenReturn(Optional.empty());
		
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					Load load = loadservice.getLoad("load:0de885e0-5f43-4c68-8dde-0000000000000");
	            }
	    );
	    assertEquals("Load was not found for parameters {id=load:0de885e0-5f43-4c68-8dde-0000000000000}", exception.getMessage());
	}


	// suggested loads false
	@Test
	@Order(20)
	public void getLoadsBytruckType() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads().subList(0,3);
		when(loaddao.findByTruckTypeAndStatus("OPEN_HALF_BODY", Load.Status.PENDING, currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, null, null, null, "OPEN_HALF_BODY", null, false);
		assertEquals(loads, result);
	}
	
	@Test
	@Order(20)
	public void getLoadsBytruckTypefail() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads().subList(0,3);
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					loadservice.getLoads(0, null, null, null, "OPEN_HALF_BODY", null, false);
			
	            });
		assertEquals("Load was not found for parameters {truckType=OPEN_HALF_BODY}", exception.getMessage());
	}

	@Test
	@Order(21)
	public void getLoadsBydate() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads().subList(0, 3);
		
		when(loaddao.findByLoadDateAndStatus("22/01/21",Load.Status.PENDING, currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, null, null, null, null, "22/01/21", false);
		
		
		assertEquals(loads, result);
	}
	
	@Test
	@Order(21)
	public void getLoadsBydatefail() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads().subList(0, 3);
		
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					loadservice.getLoads(0, null, null, null, null, "22/01/21", false);
			
	            });
		assertEquals("Load was not found for parameters {loadDate=22/01/21}", exception.getMessage());
	}

	@Test
	@Order(22)
	public void getLoadsBypostLoadIdfail() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads().subList(0,1);
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					loadservice.getLoads(0, null, null, "id:2", null, null, false);
			
	            });
		assertEquals("Load was not found for parameters {postLoadId=id:2}", exception.getMessage());
	}
	
	@Test
	@Order(22)
	public void getLoadsBypostLoadId() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads().subList(0,1);
		when(loaddao.findByPostLoadIdAndStatus("id:1",Load.Status.PENDING, currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, null, null, "id:1", null, null, false);
		
		
		assertEquals(loads, result);
	}


	@Test
	@Order(23)
	public void getLoadsByloadingPointCity() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads();
		when(loaddao.findByLoadingPointCityAndStatus("Nagpur",Load.Status.PENDING, currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, "Nagpur", null, null, null, null, false);
		assertEquals(loads, result);
	}
	
	@Test
	@Order(23)
	public void getLoadsByloadingPointCityFail() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads();

		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					loadservice.getLoads(0, "Nagpur", null, null, null, null, false);
			
	            });
		assertEquals("Load was not found for parameters {loadingPointCity=Nagpur}", exception.getMessage());
				
	}
	
	
	
	@Test
	@Order(24)
	public void getLoadsByunloadingPointCity() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads();
		when(loaddao.findByUnloadingPointCityAndStatus("Raipur",Load.Status.PENDING, currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, null, "Raipur", null, null, null, false);
		
		System.err.println(loads);
		System.err.println(result);
		
		assertEquals(loads, result);
	} 
	
	@Test
	@Order(24)
	public void getLoadsByunloadingPointCityfail() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");
		List<Load> loads = createLoads();
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					loadservice.getLoads(0, null, "Raipur", null, null, null, false);
			
	            });
		assertEquals("Load was not found for parameters {unloadingPointCity=Raipur}", exception.getMessage());
	}
	

	// unable to search using only unloading point, loading point is also necessary
	// for this
	@Test
	@Order(25)
	public void getLoadsByloadingpointcityandunloadingPointCity() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize, Sort.Direction.DESC, "timestamp");

		List<Load> loads = createLoads();
		when(loaddao.findByLoadingPointCityAndUnloadingPointCityAndStatus("Nagpur", "Raipur",Load.Status.PENDING, currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, "Nagpur", "Raipur", null, null, null, false);
		assertEquals(loads, result);
	}

	@Test
	@Order(26)
	public void updateLoad() {
		List<Load> loads = createLoads();
		when(loaddao.findByLoadId("load:0a5f1700-041a-43d4-b3eb-000000000001")).thenReturn(Optional.of(loads.get(0)));
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","no comment", "22/01/21", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		UpdateLoadResponse updateloadresponse = new UpdateLoadResponse("load:0a5f1700-041a-43d4-b3eb-000000000001", "Nagpur", "Nagpur", "Maharashtra",
				"Raipur", "Raipur","Chhattisgarh","id:1", "Gold", "OPEN_HALF_BODY", "6", "1000kg", "22/01/21",Load.Status.PENDING,"no comment",
				 (long) 100, UpdateLoadResponse.UnitValue.PER_TON, Timestamp.valueOf("2021-07-28 23:28:50.134"));
		
		
		UpdateLoadResponse result = loadservice.updateLoad("load:0a5f1700-041a-43d4-b3eb-000000000001", loadrequest);
		
		
		assertEquals(updateloadresponse, result);
		
	}
		

	@Test
	@Order(27)
	public void updateLoadfailed() {
		List<Load> loads = createLoads();
	when(loaddao.findByLoadId("id=load:0de885e0-5f43-4c68-8dde-0000000000000")).thenReturn(Optional.empty());
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					loadservice.updateLoad("load:0de885e0-5f43-4c68-8dde-0000000000000",loadrequest);
			
	            }
	    );
		System.err.println(exception.getMessage());
	    assertEquals("Load was not found for parameters {id=load:0de885e0-5f43-4c68-8dde-0000000000000}", exception.getMessage());
	}

	@Test
	@Order(28)
	public void deleteLoadFail() {
	
//		when(loaddao.findByLoadId("id=load:0de885e0-5f43-4c68-8dde-0000000000000")).thenReturn(Optional.empty());
		
		Throwable exception = assertThrows(
				EntityNotFoundException.class, () -> {
					 loadservice.deleteLoad("load:0de885e0-5f43-4c68-8dde-0000000000000");
	            }
	    );
	    assertEquals("Load was not found for parameters {id=load:0de885e0-5f43-4c68-8dde-0000000000000}", exception.getMessage());
	}

	@Test
	@Order(29)
	public void deleteLoad() {
		{
			when(loaddao.findByLoadId("load:0a5f1700-041a-43d4-b3eb-000000000001")).thenReturn(Optional.of(createLoads().get(0)));
			assertDoesNotThrow(()->loadservice.deleteLoad("load:0a5f1700-041a-43d4-b3eb-000000000001"));
		}
		}


	public LoadRequest createLoadRequest() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);
		return loadrequest;
	}
	
	public List<Load> createLoads()
	{
		List<Load> loads = Arrays.asList(
		new Load("load:0a5f1700-041a-43d4-b3eb-000000000001","Nagpur","Mumbai","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
			"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,Load.UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
			), 
		new Load("load:0a5f1700-041a-43d4-b3eb-000000000002","Nagpur","Nagpur","Maharashtra","id:2","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,Load.UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")),
		new Load("load:0a5f1700-041a-43d4-b3eb-000000000003","Nagpur","Nagpur","Maharashtra","id:3","Raipur","Raipur","Chhattisgarh",
							"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,Load.UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134"))
		); 
		
		return loads;
	}
}

