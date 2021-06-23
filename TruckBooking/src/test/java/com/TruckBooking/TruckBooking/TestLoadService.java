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

	@Test
	@Order(1)
	public void addLoad() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = createLoads().get(0);

		CreateLoadResponse createloadresponse = new CreateLoadResponse("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra",
				"id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21",
				"added comment", "pending", (long) 100, CreateLoadResponse.UnitValue.PER_TON);

		when(loaddao.save(load)).thenReturn(load);

		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		/// load id will not be same
		assertEquals(createloadresponse.getStatus(), createloadresponse1.getStatus());
	}

	// null loding point
	@Test
	@Order(2)
	public void addLoadfail1() {
		LoadRequest loadrequest = new LoadRequest(null, "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", null, "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);

		when(loaddao.save(load)).thenReturn(load);

		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.loadingError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// null loding city
	@Test
	@Order(3)
	public void addLoadfail2() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", null, "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", null, "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);

		when(loaddao.save(load)).thenReturn(load);

		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.loadingCityError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// loadingstate null
	@Test
	@Order(4)
	public void addLoadfail3() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", null, "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", null, "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);

		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.loadingStateError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		assertEquals(createloadresponse, createloadresponse1);
	}

	// post load id error
	@Test
	@Order(5)
	public void addLoadfail4() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", null, "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", null, "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.idError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);
		
		assertEquals(createloadresponse, createloadresponse1);
	}

	// unloadingpoint null
	@Test
	@Order(6)
	public void addLoadfail5() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", null, "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", null, "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.unloadingError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// unloading city error
	@Test
	@Order(7)
	public void addLoadfail6() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:!", "Raipur", null,
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:!", "Raipur", null, "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.unloadingCityError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// unloading state error
	@Test
	@Order(8)
	public void addLoadfail7() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", null,
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", null, "Gold",
				"OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.unloadingStateError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// product type null
	@Test
	@Order(9)
	public void addLoadfail8() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:!", "Raipur", "Raipur",
				"Chhattisgarh", null, "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:!", "Raipur", "Raipur", "Chhattisgarh",
				null, "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.productTypeError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// trucktype null
	@Test
	@Order(10)
	public void addLoadfail9() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", null, "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", null, "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.truckTypeError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// no of truck
	@Test
	@Order(11)
	public void addLoadfail10() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:!", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", null, "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:!", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", null, "10000kg", "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.noOfTrucksError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// weight null
	@Test
	@Order(12)
	public void addLoadfail11() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", null, "01/05/21", "added comment", "pending", (long) 100,
				LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", null, "01/05/21", "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.weightError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// date null
	@Test
	@Order(13)
	public void addLoadfail12() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", null, "added comment", "pending", (long) 100,
				LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", null, "added comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.dateError, null, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals(createloadresponse, createloadresponse1);
	}

	// comment null
	@Test
	@Order(14)
	public void addLoadnullcomment() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", null, "pending", (long) 100,
				LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", null, "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra",
				"id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", null,
				"pending", (long) 100, CreateLoadResponse.UnitValue.PER_TON);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals("pending", createloadresponse1.getStatus());
	}

	// status null
	@Test
	@Order(15)
	public void addLoadnullstatus() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", null, (long) 100,
				LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending", (long) 100,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra",
				"id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21",
				"comment", "pending", (long) 100, CreateLoadResponse.UnitValue.PER_TON);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals("pending", createloadresponse1.getStatus());
	}

	// rate null
	@Test
	@Order(16)
	public void addLoadnullrate() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending", null,
				LoadRequest.UnitValue.PER_TON);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending", null,
				Load.UnitValue.PER_TON);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra",
				"id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21",
				"comment", "pending", null, CreateLoadResponse.UnitValue.PER_TON);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals("pending", createloadresponse1.getStatus());
	}

	// unit value null
	@Test
	@Order(17)
	public void addLoadnullunitvalue() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending", (long) 100,
				null);
		Load load = new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
				"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "comment", "pending", (long) 100, null);
		when(loaddao.save(load)).thenReturn(load);
		CreateLoadResponse createloadresponse = new CreateLoadResponse("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra",
				"id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21",
				"comment", "pending", (long) 100, null);
		CreateLoadResponse createloadresponse1 = loadservice.addLoad(loadrequest);

		assertEquals("pending", createloadresponse1.getStatus());
	}

	@Test
	@Order(18)
	public void getLoadsByLoadId() {
		Load load = createLoads().get(0);
		when(loaddao.findByLoadId("load:862064c2-f58c-4758-986c-000000000000")).thenReturn(Optional.of(load));
		assertEquals(load, loadservice.getLoad("load:862064c2-f58c-4758-986c-000000000000"));
	}

	// suggested loads true
	@Test
	@Order(19)
	public void getLoadsBytruckType1() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		List<Load> loads = createLoads().subList(3, 5);
		when(loaddao.findByAll(currentPage)).thenReturn(loads);
		Collections.reverse(loads);

		List<Load> result = loadservice.getLoads(0, null, null, null, "OPEN_HALF_BODY", null, true);
		assertEquals(loads, result);
	}

	// suggested loads false
	@Test
	@Order(20)
	public void getLoadsBytruckType2() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		List<Load> loads = createLoads().subList(4, 5);
		when(loaddao.findByTruckType("OPEN_HALF_BODY", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, null, null, null, "OPEN_HALF_BODY", null, false);
		assertEquals(loads, result);
	}

	@Test
	@Order(21)
	public void getLoadsBydate() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		List<Load> loads = createLoads().subList(0, 4);
		when(loaddao.findByLoadDate("01/01/21", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, null, null, null, null, "01/01/21", false);
		assertEquals(loads, result);
	}

	@Test
	@Order(22)
	public void getLoadsByshipperId() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		List<Load> loads = createLoads().subList(0, 1);
		when(loaddao.findByPostLoadId("id:1", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, null, null, "id:1", null, null, false);
		assertEquals(loads, result);
	}

	@Test
	@Order(23)
	public void getLoadsByloadingPointCity() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		List<Load> loads = createLoads();
		when(loaddao.findByLoadingPointCity("Nagpur", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, "Nagpur", null, null, null, null, false);
		assertEquals(loads, result);
	}

	// unable to search using only unloading point, loading point is also necessary
	// for this
	@Test
	@Order(24)
	public void getLoadsByloadandunloadingPointCity() {
		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);

		List<Load> loads = createLoads();
		when(loaddao.findByLoadingPointCityAndUnloadingPointCity("Nagpur", "Raipur", currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, "Nagpur", "Raipur", null, null, null, false);
		assertEquals(loads, result);
	}

	@Test
	@Order(25)
	public void getLoad() {
		List<Load> loads = createLoads();

		Pageable currentPage;
		currentPage = PageRequest.of(0, CommonConstants.pagesize);
		when(loaddao.findByAll(currentPage)).thenReturn(loads);
		Collections.reverse(loads);
		List<Load> result = loadservice.getLoads(0, null, null, null, null, null, false);
		assertEquals(loads, result);
	}

	@Test
	@Order(26)
	public void updateLoad() {
		List<Load> loads = createLoads();
		when(loaddao.findByLoadId("load:862064c2-f58c-4758-986c-000000000000")).thenReturn(Optional.of(loads.get(0)));
		LoadRequest loadrequest = new LoadRequest("Surat", "Surat", "Gujarat", "id:5", "Mumbai", "Mumbai",
				"Maharashtra", "Silver", "OPEN_FULL_BODY", "7", "200kg", "05/05/21", "no comment",
				CommonConstants.updateSuccess, (long) 100, LoadRequest.UnitValue.PER_TRUCK);
		UpdateLoadResponse updateloadresponse = new UpdateLoadResponse("load:862064c2-f58c-4758-986c-000000000000", "Surat", "Surat", "Gujarat", "id:5",
				"Mumbai", "Mumbai", "Maharashtra", "Silver", "OPEN_FULL_BODY", "7", "200kg", "05/05/21", "no comment",
				CommonConstants.updateSuccess, (long) 100, UpdateLoadResponse.UnitValue.PER_TRUCK);
		updateloadresponse.setStatus(CommonConstants.updateSuccess);
		UpdateLoadResponse result = loadservice.updateLoad("load:862064c2-f58c-4758-986c-000000000000", loadrequest);
		assertEquals(updateloadresponse, result);
	}

	@Test
	@Order(27)
	public void updateLoadfailed() {
		when(loaddao.findByLoadId("load:862064c2-f58c-4758-986c-000000000000")).thenReturn(Optional.of(createLoads().get(0)));
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		UpdateLoadResponse updateloadresponse = new UpdateLoadResponse(null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, CommonConstants.AccountNotFoundError, null, null);

		UpdateLoadResponse result = loadservice.updateLoad("loadd:862064c2-f58c-4758-986c-095cd6c2091", loadrequest);

		assertEquals(updateloadresponse, result);
	}

	@Test
	@Order(28)
	public void deleteLoadFail() {
		when(loaddao.findByLoadId("load:862064c2-f58c-4758-986c-000000000000")).thenReturn(Optional.of(createLoads().get(0)));

		DeleteLoadResponse response = new DeleteLoadResponse(CommonConstants.AccountNotFoundError);

		DeleteLoadResponse result = loadservice.deleteLoad("loadd:862064c2-f58c-4758-986c-095cd6c2091");

		assertEquals(response, result);
	}

	@Test
	@Order(29)
	public void deleteLoad() {
		DeleteLoadResponse response = new DeleteLoadResponse(CommonConstants.deleteSuccess);
		when(loaddao.findByLoadId("load:862064c2-f58c-4758-986c-000000000000")).thenReturn(Optional.of(createLoads().get(0)));

		DeleteLoadResponse result = loadservice.deleteLoad("load:862064c2-f58c-4758-986c-000000000000");
		
		assertEquals(response, result);
	}

	public LoadRequest createLoadRequest() {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",
				(long) 100, LoadRequest.UnitValue.PER_TON);
		return loadrequest;
	}

	public List<Load> createLoads() {
		List<Load> loads = Arrays.asList(
				new Load("load:862064c2-f58c-4758-986c-000000000000", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
						"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
						Load.UnitValue.PER_TON),
				new Load("load:862064c2-f58c-4758-986c-000000000001", "Nagpur", "Nagpur", "Maharashtra", "id:2", "Raipur", "Raipur", "Chhattisgarh",
						"Gold", "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
						Load.UnitValue.PER_TON),
				new Load("load:862064c2-f58c-4758-986c-000000000002", "Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh",
						"Gold", "OPEN_FULL_BODY", "6", "10000kg", "01/05/21", "added comment", "pending", (long) 100,
						Load.UnitValue.PER_TON),
				new Load("load:862064c2-f58c-4758-986c-000000000003", "Nagpur", "Nagpur", "Maharashtra", "id:4", "Raipur", "Raipur", "Chhattisgarh",
						"Gold", "OPEN_HALF_BODY", "6", "10000kg", "02/05/21", "added comment", "pending", (long) 100,
						Load.UnitValue.PER_TON),
				new Load("load:862064c2-f58c-4758-986c-000000000004", "Nagpur", "Nagpur", "Maharashtra", "id:5", "Raipur", "Raipur", "Chhattisgarh",
						"Gold", "OPEN_HALF_BODY", "6", "10000kg", "03/05/21", "added comment", "pending", (long) 100,
						Load.UnitValue.PER_TON));
		return loads;
	}
}
