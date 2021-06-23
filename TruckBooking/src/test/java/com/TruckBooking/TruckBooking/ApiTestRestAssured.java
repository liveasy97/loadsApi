package com.TruckBooking.TruckBooking;

import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.TruckBooking.Response.UpdateLoadResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class ApiTestRestAssured {
	
	private static String loadid1;
	private static String loadid2;
	private static String loadid3;
	
	private static long loadingpointcitycount = 0;
	private static long loadingpointcitypagecount = 0;
	private static long loadingunloadingpointcitycount = 0;
	private static long loadingunloadingpointcitypagecount = 0;
	private static long postloadidcount = 0;
	private static long postloadidpagecount = 0;
	private static long loaddatecount = 0;
	private static long loaddatepagecount = 0;
	private static long trucktypecount = 0;
	private static long trucktypepagecount = 0;
	
	private static long totalentriescount = 0;
	private static long totalentriespagecount = 0;
	
	@BeforeAll
	public static void setup() throws Exception {
		
		RestAssured.baseURI = CommonConstants.BASEURI;
		
		Response response11;
		
		while (true) {
			response11 = RestAssured.given().param("pageNo", loadingpointcitypagecount).param("loadingPoint", "Nagpur")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			loadingpointcitycount += response11.jsonPath().getList("$").size();
			if (response11.jsonPath().getList("$").size() != CommonConstants.pagesize)
				break;

			loadingpointcitypagecount++;
		}
		
		Response response22;
		while (true) {
			response22 = RestAssured.given().param("pageNo", loadingunloadingpointcitypagecount).param("loadingPoint", "Nagpur")
					.param("unloadingPoint", "Raipur")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			loadingunloadingpointcitycount += response22.jsonPath().getList("$").size();
			if (response22.jsonPath().getList("$").size() != CommonConstants.pagesize)
				break;
			loadingunloadingpointcitypagecount++;
		}
		
		Response response33;
		while (true) {
			response33 = RestAssured.given().param("pageNo", postloadidpagecount).param("postLoadId", "id:1")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			postloadidcount += response33.jsonPath().getList("$").size();
			if (response33.jsonPath().getList("$").size() != CommonConstants.pagesize)
				break;
			postloadidpagecount++;
		}
		
		Response response44;
		while (true) {
			response44 = RestAssured.given().param("pageNo", loaddatepagecount).param("loadDate", "03/05/21")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			loaddatecount += response44.jsonPath().getList("$").size();
			if (response44.jsonPath().getList("$").size() != CommonConstants.pagesize)
				break;
			loaddatepagecount++;
		}
		
		Response response55;
		while (true) {
			response55 = RestAssured.given().param("pageNo", trucktypepagecount).param("truckType", "OPEN_HALF_BODY")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			trucktypecount += response55.jsonPath().getList("$").size();
			if (response55.jsonPath().getList("$").size() != CommonConstants.pagesize)
				break;
			trucktypepagecount++;
		}
		
		Response response66;
		while (true) {
			response66 = RestAssured.given().param("pageNo", totalentriespagecount)
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			totalentriescount += response66.jsonPath().getList("$").size();
			if (response66.jsonPath().getList("$").size() != CommonConstants.pagesize)
				break;
			totalentriespagecount++;
		}
		
		LoadRequest loadrequest1 = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment1", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		
        String inputJson1 = mapToJson(loadrequest1);
        Response response1 = (Response) RestAssured.given().header("", "").body(inputJson1).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
        LoadRequest loadrequest2 = new LoadRequest("Mumbai", "Mumbai", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_FULL_BODY", "6", "10000kg", "01/05/21", "added comment2", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		
		String inputJson2 = mapToJson(loadrequest2);
		Response response2 = (Response) RestAssured.given().header("", "").body(inputJson2).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		
		LoadRequest loadrequest3 = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:2", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "03/05/21", "added comment3", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		
		String inputJson3 = mapToJson(loadrequest3);
		Response response3 = (Response) RestAssured.given().header("", "").body(inputJson3).header("accept", "application/json").header("Content-Type", "application/json").post().then().extract().response();
		
		loadid1 = response1.jsonPath().getString("loadId");
		loadid2 = response2.jsonPath().getString("loadId");
		loadid3 = response3.jsonPath().getString("loadId");
		
		assertEquals(200, response1.statusCode());
		assertEquals(CommonConstants.pending, response1.jsonPath().getString("status"));
		
		assertEquals(200, response2.statusCode());
		assertEquals(CommonConstants.pending, response2.jsonPath().getString("status"));
		
		assertEquals(200, response3.statusCode());
		assertEquals(CommonConstants.pending, response3.jsonPath().getString("status"));

	}
	
	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		return objectMapper.writeValueAsString(object);
	}

	@Test
	@Order(3)
	public void addloadsuccess() throws Exception {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment4", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.pending, response.jsonPath().getString("status"));
		
		Response response1 = RestAssured.given().header("", "").delete("/" + response.jsonPath().getString("loadId")).then().extract().response();
		
	}
	//loading point null
	@Test
	@Order(4)
	public void addloadingpointnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest(null, "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.loadingError, response.jsonPath().getString("status"));

	}
	
	
	//loading point state null
	@Test
	@Order(5)
	public void addloadingpointstatenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", null, "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.loadingStateError, response.jsonPath().getString("status"));

	}
	
	//loading point city null
	@Test
	@Order(6)
	public void addloadingpointcitynull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", null, "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.loadingCityError, response.jsonPath().getString("status"));

	}
	//id null
	@Test
	@Order(7)
	public void addidnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", null, "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.idError, response.jsonPath().getString("status"));

	}
	//unloadingpoint null
	@Test
	@Order(8)
	public void addloadunloadingpointnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", null, "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.unloadingError, response.jsonPath().getString("status"));

	}
	//unloadingpointstate null
	@Test
	@Order(9)
	public void addloadunloadingpointstatenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", null, "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.unloadingStateError, response.jsonPath().getString("status"));
	}
	//unloadingpointcity null
	@Test
	@Order(10)
	public void addloadunloadingpointcitynull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", null, "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.unloadingCityError, response.jsonPath().getString("status"));

	}
	//producttype
	@Test
	@Order(11)
	public void addloadproducttypenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", null,
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.productTypeError, response.jsonPath().getString("status"));

	}
	//trucktype
	@Test
	@Order(12)
	public void addloadtrucktypenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    null, "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.truckTypeError, response.jsonPath().getString("status"));

	}
	//nooftruck
	@Test
	@Order(13)
	public void addloadnooftrucknull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", null, "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.noOfTrucksError, response.jsonPath().getString("status"));

	}
	//weight
	@Test
	@Order(14)
	public void addloadweightnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", null, "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.weightError, response.jsonPath().getString("status"));

	}
	//comment
	@Test
	@Order(15)
	public void addloadcommentnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", null, "pending",(long) 100, LoadRequest.UnitValue.PER_TON);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.pending, response.jsonPath().getString("status"));

		Response response1 = RestAssured.given().header("", "").delete("/" + response.jsonPath().getString("loadId")).then().extract().response();
		assertEquals(200, response1.statusCode());
		assertEquals(CommonConstants.deleteSuccess, response1.jsonPath().getString("status"));

	}
	
	//date
	@Test
	@Order(16)
	public void addloaddatenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", null, "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.dateError, response.jsonPath().getString("status"));

	}
	//status
	@Test
	@Order(17)
	public void addloadstatusnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment00", null,(long) 100, LoadRequest.UnitValue.PER_TON);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(CommonConstants.pending, response.jsonPath().getString("status"));
		
		Response response1 = RestAssured.given().header("", "").delete("/" + response.jsonPath().getString("loadId")).then().extract().response();
		assertEquals(200, response1.statusCode());
		assertEquals(CommonConstants.deleteSuccess, response1.jsonPath().getString("status"));
	}
	
	//get all load
	@Test
	@Order(18)
	public void getload() throws Exception {
		
		long lastPageCount = totalentriescount % CommonConstants.pagesize;
		long page = totalentriespagecount;

		if (lastPageCount >= CommonConstants.pagesize - 2)
			page++;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= CommonConstants.pagesize-3)
		{
			System.err.println("1");
			assertEquals(lastPageCount+3, response.jsonPath().getList("$").size());
		}
		else if(lastPageCount == CommonConstants.pagesize-2)
		{
			System.err.println("2 " + response.jsonPath().getList("$").size());
			assertEquals(1, response.jsonPath().getList("$").size());
		}
		else if(lastPageCount == CommonConstants.pagesize-1)
		{
			System.err.println("3");
			assertEquals(2, response.jsonPath().getList("$").size());
		}
		else if(lastPageCount == CommonConstants.pagesize)
		{
			System.err.println("4");
			assertEquals(3, response.jsonPath().getList("$").size());
		}
	}
	
	//getloadloadingPointCity
	@Test
	@Order(19)
	public void getloadloadingpointcity() throws Exception {
		long lastPageCount = loadingpointcitycount % CommonConstants.pagesize;
		long page = loadingpointcitypagecount;

		if (lastPageCount >= CommonConstants.pagesize - 1)
			page++;
		
		Response response = RestAssured.given().param("loadingPointCity", "Nagpur")
				.param("pageNo", page)
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= CommonConstants.pagesize-2)
		{
			assertEquals(lastPageCount+2, response.jsonPath().getList("status").size());
		}
		else if(lastPageCount == CommonConstants.pagesize-1)
		{
			assertEquals(1, response.jsonPath().getList("status").size());
		}
		else if(lastPageCount == CommonConstants.pagesize)
		{
			assertEquals(2, response.jsonPath().getList("status").size());
		}
	}
	
	// getload unloadingPointCity
	@Test
	@Order(20)
	public void getloadunloadingPointCity() throws Exception {
		
		long lastPageCount = loadingunloadingpointcitycount% CommonConstants.pagesize;
		long page = loadingunloadingpointcitypagecount;
		
		if (lastPageCount >= CommonConstants.pagesize - 1)
			page++;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("loadingPointCity", "Nagpur")
				.param("unloadingPointCity", "Raipur")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= CommonConstants.pagesize-2)
		{
			assertEquals(lastPageCount+2, response.jsonPath().getList("status").size());
		}
		else if(lastPageCount == CommonConstants.pagesize-1)
		{
			assertEquals(1, response.jsonPath().getList("status").size());
		}
		else if(lastPageCount == CommonConstants.pagesize)
		{
			assertEquals(2, response.jsonPath().getList("status").size());
		}
	}
	//getloadshipperId
	@Test
	@Order(21)
	public void getloadloadshipperId() throws Exception {
		
		long lastPageCount = postloadidcount% CommonConstants.pagesize;
		long page = postloadidpagecount;
		
		if (lastPageCount >= CommonConstants.pagesize - 1)
			page++;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("postLoadId", "id:1")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= CommonConstants.pagesize-2)
		{
			assertEquals(lastPageCount+2, response.jsonPath().getList("status").size());
		}
		else if(lastPageCount == CommonConstants.pagesize-1)
		{
			assertEquals(1, response.jsonPath().getList("status").size());
		}
		else if(lastPageCount == CommonConstants.pagesize)
		{
			assertEquals(2, response.jsonPath().getList("status").size());
		}
	}
	
	//getloadtruckType
	@Test
	@Order(22)
	public void getloadtruckType() throws Exception {
		
		long lastPageCount = trucktypecount%CommonConstants.pagesize;
		long page = trucktypepagecount;
		
		if (lastPageCount >= CommonConstants.pagesize - 1)
			page++;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("truckType", "OPEN_HALF_BODY")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= CommonConstants.pagesize-2)
		{
			System.err.println("aaa " + lastPageCount+2);
			assertEquals(lastPageCount+2, response.jsonPath().getList("status").size());
		}
		else if(lastPageCount == CommonConstants.pagesize-1)
		{
			assertEquals(1, response.jsonPath().getList("status").size());
		}
		else if(lastPageCount == CommonConstants.pagesize)
		{
			assertEquals(2, response.jsonPath().getList("status").size());
		}
	}
	//getload date
	@Test
	@Order(23)
	public void getloaddate() throws Exception {
		
		long lastPageCount = loaddatecount%CommonConstants.pagesize;
		long page = loaddatepagecount;
		
		if (lastPageCount >= CommonConstants.pagesize)
			page++;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("loadDate", "03/05/21")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		if(lastPageCount <= CommonConstants.pagesize-1)
		{
			assertEquals(lastPageCount + 1, response.jsonPath().getList("status").size());
		}
		else if(lastPageCount == CommonConstants.pagesize)
		{
			assertEquals(1, response.jsonPath().getList("status").size());
		}
	}
	
	//getload loadid
	@Test
	@Order(24)
	public void getloadloadid() throws Exception {
		
		Response response = RestAssured.given().param("","").header("accept", "application/json").header("Content-Type", "application/json").get("/"+loadid1).then().extract()
				.response();

		assertEquals("Nagpur", response.jsonPath().getString("loadingPointCity"));
		assertEquals("Maharashtra", response.jsonPath().getString("loadingPointState"));
		assertEquals("id:1", response.jsonPath().getString("postLoadId"));
		assertEquals("Raipur", response.jsonPath().getString("unloadingPointCity"));
		assertEquals("Chhattisgarh", response.jsonPath().getString("unloadingPointState"));
		assertEquals("Gold", response.jsonPath().getString("productType"));
		assertEquals("OPEN_HALF_BODY", response.jsonPath().getString("truckType"));
		assertEquals("6", response.jsonPath().getString("noOfTrucks"));
		assertEquals("10000kg", response.jsonPath().getString("weight"));
		assertEquals("01/05/21", response.jsonPath().getString("loadDate"));
		
	}

	@Test
	@Order(25)
	public void updateDataallparamaters() throws Exception {

		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest("Surat", "Surat", "Gujarat", "id:4", "Patna", "Patna",
				"Bihar", "Silver", "OPEN_HALF_BODY", "60", "1000kg", "01/05/20", 
				"added comment", "Done", (long)1000, LoadRequest.UnitValue.PER_TON);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.updateSuccess, responseupdate.jsonPath().getString("status"));
		assertEquals("Surat", responseupdate.jsonPath().getString("loadingPoint"));
		assertEquals("Surat", responseupdate.jsonPath().getString("loadingPointCity"));
		assertEquals("Gujarat", responseupdate.jsonPath().getString("loadingPointState"));
		assertEquals("id:4", responseupdate.jsonPath().getString("postLoadId"));
		assertEquals("Patna", responseupdate.jsonPath().getString("unloadingPoint"));
		assertEquals("Patna", responseupdate.jsonPath().getString("unloadingPointCity"));
		assertEquals("Bihar", responseupdate.jsonPath().getString("unloadingPointState"));
		assertEquals("Silver", responseupdate.jsonPath().getString("productType"));
		assertEquals("OPEN_HALF_BODY", responseupdate.jsonPath().getString("truckType"));
		assertEquals("60", responseupdate.jsonPath().getString("noOfTrucks"));
		assertEquals("1000kg", responseupdate.jsonPath().getString("weight"));
		assertEquals("01/05/20", responseupdate.jsonPath().getString("loadDate"));
		assertEquals("1000", responseupdate.jsonPath().getString("rate"));
		assertEquals("PER_TON", responseupdate.jsonPath().getString("unitValue"));
	}
	
	// all null
	@Test
	@Order(26)
	public void updateData1() throws Exception {
        
		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.updateSuccess, responseupdate.jsonPath().getString("status"));
		assertEquals("Surat", responseupdate.jsonPath().getString("loadingPoint"));
		assertEquals("Surat", responseupdate.jsonPath().getString("loadingPointCity"));
		assertEquals("Gujarat", responseupdate.jsonPath().getString("loadingPointState"));
		assertEquals("id:4", responseupdate.jsonPath().getString("postLoadId"));
		assertEquals("Patna", responseupdate.jsonPath().getString("unloadingPoint"));
		assertEquals("Patna", responseupdate.jsonPath().getString("unloadingPointCity"));
		assertEquals("Bihar", responseupdate.jsonPath().getString("unloadingPointState"));
		assertEquals("Silver", responseupdate.jsonPath().getString("productType"));
		assertEquals("OPEN_HALF_BODY", responseupdate.jsonPath().getString("truckType"));
		assertEquals("60", responseupdate.jsonPath().getString("noOfTrucks"));
		assertEquals("1000kg", responseupdate.jsonPath().getString("weight"));
		assertEquals("01/05/20", responseupdate.jsonPath().getString("loadDate"));
		assertEquals("1000", responseupdate.jsonPath().getString("rate"));
		assertEquals("PER_TON", responseupdate.jsonPath().getString("unitValue"));
		
	}
	
	// loading point empty
	@Test
	@Order(27)
	public void updateData2() throws Exception {

		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest("",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyloadingpoint, responseupdate.jsonPath().getString("status"));
		
	}
	// loading state empty
	@Test
	@Order(28)
	public void updateData3() throws Exception {

		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,"",null,null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyloadingstate, responseupdate.jsonPath().getString("status"));
		
	}
	//loading point city empty
	@Test
	@Order(29)
	public void updateData4() throws Exception {
		
		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest(null,"",null,null,null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyloadingcity, responseupdate.jsonPath().getString("status"));

	}
	// unloading point empty
	@Test
	@Order(30)
	public void updateData5() throws Exception {
		
		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,null,null,"",null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyunloadingpoint, responseupdate.jsonPath().getString("status"));

	}
	
	// unloading state empty
	@Test
	@Order(31)
	public void updateData6() throws Exception {

		
		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,null,null,null,null,"",null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyunloadingstate, responseupdate.jsonPath().getString("status"));

	}
	//unloading city empty
	@Test
	@Order(32)
	public void updateData7() throws Exception {
		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,null,null,null,"",null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyunloadingcity, responseupdate.jsonPath().getString("status"));

	}
	//empty id
	@Test
	@Order(33)
	public void updateData8() throws Exception {

		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,null,"",null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyshiperid, responseupdate.jsonPath().getString("status"));

	}
	

	@Test
	@Order(34)
	public void updateData9() throws Exception {

		String loadid = loadid1;
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest("market", null, null, null, "mall", null,
				null, null, null, null, "10000kg", "01/05/20", 
				null, null, null, LoadRequest.UnitValue.PER_TRUCK);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.updateSuccess, responseupdate.jsonPath().getString("status"));
		assertEquals("market", responseupdate.jsonPath().getString("loadingPoint"));
		assertEquals("Surat", responseupdate.jsonPath().getString("loadingPointCity"));
		assertEquals("Gujarat", responseupdate.jsonPath().getString("loadingPointState"));
		assertEquals("id:4", responseupdate.jsonPath().getString("postLoadId"));
		assertEquals("mall", responseupdate.jsonPath().getString("unloadingPoint"));
		assertEquals("Patna", responseupdate.jsonPath().getString("unloadingPointCity"));
		assertEquals("Bihar", responseupdate.jsonPath().getString("unloadingPointState"));
		assertEquals("Silver", responseupdate.jsonPath().getString("productType"));
		assertEquals("OPEN_HALF_BODY", responseupdate.jsonPath().getString("truckType"));
		assertEquals("60", responseupdate.jsonPath().getString("noOfTrucks"));
		assertEquals("10000kg", responseupdate.jsonPath().getString("weight"));
		assertEquals("01/05/20", responseupdate.jsonPath().getString("loadDate"));
		assertEquals("1000", responseupdate.jsonPath().getString("rate"));
		assertEquals("PER_TRUCK", responseupdate.jsonPath().getString("unitValue"));
	}
	
	@Test
	@Order(35)
	public void updateDatafail() throws Exception {

		String loadid = "load1:0a5f1700-041a-43d4-b3eb-00000000123";
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest("Surat", "Surat", "Gujarat", "id:4", "Patna", "Patna",
				"Bihar", "Silver", "OPEN_HALF_BODY", "60", "1000kg", "01/05/20", 
				"added comment", "Done", (long)1000, LoadRequest.UnitValue.PER_TON);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.AccountNotFoundError, responseupdate.jsonPath().getString("status"));
	}
	
	@Test
	@Order(36)
	public static void deleteDatafail() throws Exception{
		
		String loadid = "load1:0a5f1700-041a-43d4-b3eb-00000000123";
		Response response1 = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		assertEquals(200, response1.statusCode());
		assertEquals(CommonConstants.AccountNotFoundError, response1.jsonPath().getString("status"));
	}
	
	@Test
	@AfterAll
	public static void deleteData() throws Exception {

		Response response1 = RestAssured.given().header("", "").delete("/" + loadid1).then().extract().response();
		assertEquals(200, response1.statusCode());
		assertEquals(CommonConstants.deleteSuccess, response1.jsonPath().getString("status"));
		
		Response response2 = RestAssured.given().header("", "").delete("/" + loadid2).then().extract().response();
		assertEquals(200, response2.statusCode());
		assertEquals(CommonConstants.deleteSuccess, response2.jsonPath().getString("status"));
		
		Response response3 = RestAssured.given().header("", "").delete("/" + loadid3).then().extract().response();
		assertEquals(200, response3.statusCode());
		assertEquals(CommonConstants.deleteSuccess, response3.jsonPath().getString("status"));
	}
}
