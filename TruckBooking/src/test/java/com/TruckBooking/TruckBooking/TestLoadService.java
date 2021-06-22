package com.TruckBooking.TruckBooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.Collections;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Dao.LoadDao;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.TruckBooking.Response.CreateLoadResponse;
import com.TruckBooking.TruckBooking.Response.DeleteLoadResponse;
import com.TruckBooking.TruckBooking.Response.UpdateLoadResponse;
import com.TruckBooking.TruckBooking.Service.LoadServiceImpl;


@SpringBootTest
public class TestLoadService {
	
	@Autowired
	LoadServiceImpl loadservice;
	
	@MockBean
	LoadDao loaddao;
	//////////////////////////*addload*/////////////////////////
	@Test
	@Order(1)
	public void addLoad()
	{
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = createLoads().get(0);
		
		CreateLoadResponse createloadresponse = new CreateLoadResponse("loadid:1", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, CreateLoadResponse.UnitValue.PER_TON);
		
		when(loaddao.save(load)).thenReturn(load);
		
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoad");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		/// load id will not be same
		assertEquals(createloadresponse.getStatus(), createloadresponse1.getStatus());
	}
	
	//null loding point
	@Test
	@Order(2)
	public void addLoadfail1()
	{
		LoadRequest loadrequest = new LoadRequest(null, "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", null, "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		
		when(loaddao.save(load)).thenReturn(load);
		
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.loadingError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	
	//null loding city
	@Test
	@Order(3)
	public void addLoadfail2()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur",null, "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur",null, "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
        	    
		when(loaddao.save(load)).thenReturn(load);
		
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.loadingCityError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}	
	
	//loadingstate null
	@Test
	@Order(4)
	public void addLoadfail3()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", null, "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1","Nagpur","Nagpur", null, "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.loadingStateError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	//post load id error
	@Test
	@Order(5)
	public void addLoadfail4()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", null, "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", null, "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.idError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	//unloadingpoint null
	@Test
	@Order(6)
	public void addLoadfail5()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:1", null, "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", null, "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.unloadingError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	//unloading city error
	@Test
	@Order(7)
	public void addLoadfail6()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:!", "Raipur", null, "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:!", "Raipur", null, "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.unloadingCityError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	//unloading state error
	@Test
	@Order(8)
	public void addLoadfail7()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", null, "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", null, "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.unloadingStateError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail7");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	//product type null
	@Test
	@Order(9)
	public void addLoadfail8()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:!", "Raipur", "Raipur", "Chhattisgarh", null,
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:!", "Raipur", "Raipur", "Chhattisgarh", null,
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.productTypeError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	//trucktype null
	@Test
	@Order(10)
	public void addLoadfail9()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    null, "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    null, "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.truckTypeError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail9");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	// no of truck
	@Test
	@Order(11)
	public void addLoadfail10()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:!", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", null, "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:!", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", null, "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.noOfTrucksError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	//weight null
	@Test
	@Order(12)
	public void addLoadfail11()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", null, "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", null, "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.weightError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	//date null
	@Test
	@Order(13)
	public void addLoadfail12()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", null, "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", null, "added comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( null, null, null, null, null, null, null,
				 null, null, null, null, null, null, null,CommonConstants.dateError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals(createloadresponse, createloadresponse1);
	}
	
	// comment null
	@Test
	@Order(14)
	public void addLoadfail13()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", null, "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", null, "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( "loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", null, "pending",(long) 100, CreateLoadResponse.UnitValue.PER_TON);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals("pending", createloadresponse1.getStatus());
	}
	
	
	//status null
	@Test
	@Order(15)
	public void addLoadfail14()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", null,(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending",(long) 100, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( "loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending",(long) 100, CreateLoadResponse.UnitValue.PER_TON);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals("pending", createloadresponse1.getStatus());
	}
	
	//rate null
	@Test
	@Order(16)
	public void addLoadfail15()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending",null, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending",null, Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( "loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending",null, CreateLoadResponse.UnitValue.PER_TON);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals("pending", createloadresponse1.getStatus());
	}
	
	
	//unit value null
	@Test
	@Order(17)
	public void addLoadfail16()
	{
		LoadRequest loadrequest = new LoadRequest( "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending",(long) 100, null);
		Load load = new Load("loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending",(long) 100, null);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse( "loadid:1", "Nagpur","Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending",(long) 100, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("addLoadfail1");
		System.err.println("req: " + createloadresponse);
		System.err.println("res: " + createloadresponse1);
		System.err.println("++++++++++++++++++++++++++++++++++++" );
		
		assertEquals("pending", createloadresponse1.getStatus());
	}
	
	///////////////////*get load*//////////////////////
	
	@Test
	@Order(18)
	public void getLoadsByLoadId()
	{
		Load load = createLoads().get(0);
		when(loaddao.findByLoadId("loadid:1")).thenReturn(Optional.of(load));
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("getLoadsByLoadId");
		System.err.println("req: " + load);
		System.err.println("res: " + loadservice.getLoad("loadid:1"));
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		assertEquals(load, loadservice.getLoad("loadid:1"));
	}
	
	
	//suggested loads true
	@Test
	@Order(19)
	public void getLoadsBytruckType1()
	{
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		List<Load> loads = createLoads().subList(3, 5);
		when(loaddao.findByAll(currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		
		List<Load> result = loadservice.getLoads(0, null, null, null, "OPEN_HALF_BODY", null, true);
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("getLoadsBytruckType1 ");
		System.err.println("req: " + loads);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		assertEquals(loads, result);
	}
	
	//suggested loads false
	@Test
	@Order(20)
	public void getLoadsBytruckType2()
	{
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		List<Load> loads = createLoads().subList(4, 5);
		when(loaddao.findByTruckType("OPEN_HALF_BODY", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, null, null, null, "OPEN_HALF_BODY", null, false);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("getLoadsBytruckType2 ");
		System.err.println("req: " + loads);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		assertEquals(loads, result);
	}
	
	@Test
	@Order(21)
	public void getLoadsBydate()
	{
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		List<Load> loads = createLoads().subList(0, 4);
		when(loaddao.findByLoadDate("01/01/21", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		
		List<Load> result = loadservice.getLoads(0, null, null, null, null, "01/01/21", false);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("getLoadsBydate");
		System.err.println("req: " + loads);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		
		assertEquals(loads, result);
	}
	
	@Test
	@Order(22)
	public void getLoadsByshipperId()
	{
		Pageable currentPage;
		currentPage = PageRequest.of(1, CommonConstants.pagesize);
		
		List<Load> loads = createLoads().subList(0, 1);
		when(loaddao.findByPostLoadId("id:1", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		
		List<Load> result = loadservice.getLoads(1, null, null, "id:1", null, null, false);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("getLoadsByshipperId");
		System.err.println("req: " + loads);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		
		assertEquals(loads, result);
	}
	
	@Test
	@Order(23)
	public void getLoadsByloadingPointCity()
	{
		Pageable currentPage;
		currentPage = PageRequest.of(1, CommonConstants.pagesize);
		
		List<Load> loads = createLoads();
		when(loaddao.findByLoadingPointCity("Nagpur", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		
		List<Load> result = loadservice.getLoads(1, "Nagpur", null, null, null, null, false);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("getLoadsByloadingPointCity");
		System.err.println("req: " + loads);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		
		assertEquals(loads, result);
	}
	
	// unable to search using only unloading point, loading point is also necessary for this
	@Test
	@Order(24)
	public void getLoadsByloadandunloadingPointCity()
	{
		Pageable currentPage;
		currentPage = PageRequest.of(1, CommonConstants.pagesize);
		
		List<Load> loads = createLoads();
		when(loaddao.findByLoadingPointCityAndUnloadingPointCity("Nagpur","Raipur", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		
		List<Load> result = loadservice.getLoads(1, "Nagpur", "Raipur", null, null, null, false);
		
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		System.err.println("getLoadsByloadandunloadingPointCity");
		System.err.println("req: " + loads);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++1");
		
		assertEquals(loads, result);
	}
	
	@Test
	@Order(25)
	public void getLoad()
	{
		List<Load> loads = createLoads();
		
		Pageable currentPage;
		currentPage = PageRequest.of(1, CommonConstants.pagesize);
		
		when(loaddao.findByAll(currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		
		List<Load> result = loadservice.getLoads(1, null, null, null, null, null, false);
		
		System.err.println("++++++++++++++++++++++++++++++++++++");
		System.err.println("getLoad");
		System.err.println("req: " + loads);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		assertEquals(loads, result);
	}
	
	///////////////////////*update load*//////////////////////////
	
	@Test
	@Order(26)
	public void updateLoad()
	{
		List<Load> loads = createLoads();
		when(loaddao.findByLoadId("loadid:1")).thenReturn(Optional.of(loads.get(0)));
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		UpdateLoadResponse updateloadresponse = new UpdateLoadResponse("loadid:1", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, UpdateLoadResponse.UnitValue.PER_TON);
		updateloadresponse.setStatus(CommonConstants.updateSuccess);
		
		UpdateLoadResponse result = loadservice.updateLoad("loadid:1", loadrequest);
		System.err.println("++++++++++++++++++++++++++++++++++++");
		System.err.println("updateLoad" );
		System.err.println("req: " + updateloadresponse);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		assertEquals(updateloadresponse, result);
	}
	
	@Test
	@Order(27)
	public void updateLoadfailed()
	{
		when(loaddao.findByLoadId("loadid:1")).thenReturn((Optional.empty()));
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		UpdateLoadResponse updateloadresponse = new UpdateLoadResponse(null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, CommonConstants.AccountNotFoundError, null, null );
		
		UpdateLoadResponse result = loadservice.updateLoad("loadid:1", loadrequest);
		System.err.println("++++++++++++++++++++++++++++++++++++");
		System.err.println("updateLoad" );
		System.err.println("req: " + updateloadresponse);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		assertEquals(updateloadresponse, result);
	}
	
	
	
	///////////////////*delete load*/////////////////////////
	
	@Test
	@Order(28)
	public void deleteLoadFail()
	{
		when(loaddao.findByLoadId("loadid:1")).thenReturn(Optional.empty());
		
		DeleteLoadResponse response = new DeleteLoadResponse(CommonConstants.AccountNotFoundError);
	
		DeleteLoadResponse result = loadservice.deleteLoad("loadid:6");
		System.err.println("++++++++++++++++++++++++++++++++++++");
		System.err.println("deleteLoadFail");
		System.err.println("req: " + response);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		assertEquals(response, result);
	}
	@Test
	@Order(29)
	public void deleteLoad()
	{
		DeleteLoadResponse response = new DeleteLoadResponse(CommonConstants.deleteSuccess);
		when(loaddao.findByLoadId("loadid:1")).thenReturn(Optional.of(createLoads().get(0)));
	
		DeleteLoadResponse result = loadservice.deleteLoad("loadid:1");
		System.err.println("++++++++++++++++++++++++++++++++++++");
		System.err.println("deleteLoad");
		System.err.println("req: " + response);
		System.err.println("res: " + result);
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		assertEquals(response, result);
	}
	
	public LoadRequest createLoadRequest()
	{
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		return loadrequest;
	}
	
	public List<Load> createLoads()
	{
        List<Load> loads = Arrays.asList
        (	
        new Load("loadid:1", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON),
        new Load("loadid:1", "Nagpur", "Nagpur", "Maharashtra", "id:2", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON),
        new Load("loadid:1", "Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_FULL_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON),
        new Load("loadid:1", "Nagpur", "Nagpur", "Maharashtra", "id:4", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "02/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON),
        new Load("loadid:1", "Nagpur", "Nagpur", "Maharashtra", "id:5", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "03/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON)
        );
		return loads;
	}
}
