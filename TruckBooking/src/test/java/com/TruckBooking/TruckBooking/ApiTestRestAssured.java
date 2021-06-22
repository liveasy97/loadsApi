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
			response44 = RestAssured.given().param("pageNo", loaddatepagecount).param("loadDate", "id:1")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			loaddatecount += response44.jsonPath().getList("$").size();
			if (response44.jsonPath().getList("$").size() != CommonConstants.pagesize)
				break;
			loaddatepagecount++;
		}
		
		Response response55;
		while (true) {
			response55 = RestAssured.given().param("pageNo", trucktypepagecount).param("loadDate", "id:1")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			trucktypecount += response55.jsonPath().getList("$").size();
			if (response55.jsonPath().getList("$").size() != CommonConstants.pagesize)
				break;
			trucktypepagecount++;
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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + loadid1);
		System.err.println("o " + loadid2);
		System.err.println("o " + loadid3);
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		assertEquals(200, response1.statusCode());
		assertEquals(CommonConstants.pending, response1.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response1.statusCode());
		System.err.println("o " + response1.jsonPath().getString("id"));
		System.err.println("o " + response1.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

	}
	
	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		return objectMapper.writeValueAsString(object);
	}
	
	//////////////////////////////////**addLoad()**////////////////////////
	
	
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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("loadId"));
		System.err.println("o " + response.jsonPath().getString("loadingPoint"));
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response response1 = RestAssured.given().header("", "").delete("/" + response.jsonPath().getString("loadId")).then().extract().response();
		System.err.println("++++++++++++++++++++++++++++++++++++17");
		System.err.println("o " + response1.statusCode());
		System.err.println("o " + response1.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response response1 = RestAssured.given().header("", "").delete("/" + response.jsonPath().getString("loadId")).then().extract().response();
		System.err.println("++++++++++++++++++++++++++++++++++++17");
		System.err.println("o " + response1.statusCode());
		System.err.println("o " + response1.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++4");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");

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
		
		System.err.println("++++++++++++++++++++++++++++++++++++17");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response response1 = RestAssured.given().header("", "").delete("/" + response.jsonPath().getString("loadId")).then().extract().response();
		System.err.println("++++++++++++++++++++++++++++++++++++17");
		System.err.println("o " + response1.statusCode());
		System.err.println("o " + response1.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
	}
	
    //////////////////////////////////**getLoad()**////////////////////////
	
	
	//get all load
	@Test
	@Order(18)
	public void getload() throws Exception {
		
		Response response = RestAssured.given().header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		System.err.println("++++++++++++++++++++++++++++++++++++18");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getList("status").size());
		System.err.println("++++++++++++++++++++++++++++++++++++");
		assertEquals(3, response.jsonPath().getList("status").size());
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
		
		System.err.println("++++++++++++++++++++++++++++++++++++19");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getList("status").size());
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
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
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("loadingPointCity", "Nagpur")
				.param("unloadingPointCity", "Raipur")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		System.err.println("++++++++++++++++++++++++++++++++++++20");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getList("status").size());
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
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
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("postLoadId", "id:1")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		System.err.println("++++++++++++++++++++++++++++++++++++21");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getList("status").size());
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
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
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("truckType", "OPEN_HALF_BODY")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		System.err.println("++++++++++++++++++++++++++++++++++++22");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getList("status").size());
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
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
	//getload date
	@Test
	@Order(23)
	public void getloaddate() throws Exception {
		
		long lastPageCount = loaddatecount%CommonConstants.pagesize;
		long page = loaddatepagecount;
		
		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("loadDate", "03/05/21")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();
		
		System.err.println("++++++++++++++++++++++++++++++++++++23");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getList("status").size());
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
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
		
		String loadid = response.jsonPath().getString("loadId");
		System.err.println("++++++++++++++++++++++++++++++++++++24");
		System.err.println("o " + response.statusCode());
		System.err.println("o " + response.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
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
	
//////////////////////////////////**updateLoad()**////////////////////////
	
	@Test
	@Order(25)
	public void updateData() throws Exception {

		//adding load
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment6", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);

        String inputJson = mapToJson(loadrequest);
        Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
		String loadid = response.jsonPath().getString("loadId");
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest("Surat", "Surat", "Gujarat", "id:4", "Patna", "Patna",
				"Bihar", "Silver", "OPEN_HALF_BODY", "60", "1000kg", "01/05/20", 
				"added comment", "Done", (long)1000, LoadRequest.UnitValue.PER_TON);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.updateSuccess, responseupdate.jsonPath().getString("status"));
		
		System.err.println("++++++++++++++++++++++++++++++++++++25");
		System.err.println("o " + responseupdate.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response response1 = RestAssured.given().header("accept", "application/json").header("Content-Type", "application/json").get("/"+loadid).then().extract()
				.response();
		
		System.err.println("++++++++++++++++++++++++++++++++++++24");
		System.err.println("o " + response1.statusCode());
		System.err.println("o " + response1.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		assertEquals("Surat", response1.jsonPath().getString("loadingPointCity"));
		assertEquals("Gujarat", response1.jsonPath().getString("loadingPointState"));
		assertEquals("id:4", response1.jsonPath().getString("postLoadId"));
		assertEquals("Patna", response1.jsonPath().getString("unloadingPointCity"));
		
		assertEquals("Bihar", response1.jsonPath().getString("unloadingPointState"));
		assertEquals("Silver", response1.jsonPath().getString("productType"));
		
		assertEquals("OPEN_HALF_BODY", response1.jsonPath().getString("truckType"));
		assertEquals("60", response1.jsonPath().getString("noOfTrucks"));
		assertEquals("1000kg", response1.jsonPath().getString("weight"));
		assertEquals("01/05/20", response1.jsonPath().getString("loadDate"));
		assertEquals("1000", response1.jsonPath().getString("rate"));
		assertEquals("PER_TON", response1.jsonPath().getString("unitValue"));
		
		Response responsedel = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		assertEquals(200, responsedel.statusCode());
		assertEquals(CommonConstants.deleteSuccess, responsedel.jsonPath().getString("status"));
	}
	
	// all null
	@Test
	public void updateData1() throws Exception {

		//adding load
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "1000kg", "01/05/21", "added comment7",
        	    "pending",(long) 100, LoadRequest.UnitValue.PER_TON);

        String inputJson = mapToJson(loadrequest);
        Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
		String loadid = response.jsonPath().getString("loadId");
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();
		
		System.err.println("++++++++++++++++++++++++++++++++++++24 updateData1");
		System.err.println("o " + responseupdate.jsonPath().getString("loadingPointCity"));
		System.err.println("o " + responseupdate.jsonPath().getString("loadingPointState"));
		System.err.println("o " + responseupdate.jsonPath().getString("postLoadId"));
		System.err.println("o " + responseupdate.jsonPath().getString("unloadingPointCity"));
		System.err.println("o " + responseupdate.jsonPath().getString("unloadingPointState"));
		System.err.println("o " + responseupdate.jsonPath().getString("productType"));
		System.err.println("o " + responseupdate.jsonPath().getString("truckType"));
		System.err.println("o " + responseupdate.jsonPath().getString("noOfTrucks"));
		System.err.println("o " + responseupdate.jsonPath().getString("weight"));
		System.err.println("o " + responseupdate.jsonPath().getString("rate"));
		System.err.println("o " + responseupdate.jsonPath().getString("loadDate"));
		System.err.println("o " + responseupdate.statusCode());
		System.err.println("o " + responseupdate.jsonPath().getString("status"));
		System.err.println("o " + responseupdate.jsonPath().getString("loadId"));
		System.err.println("o " + loadid);
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.updateSuccess, responseupdate.jsonPath().getString("status"));
		assertEquals("Nagpur", responseupdate.jsonPath().getString("loadingPointCity"));
		assertEquals("Maharashtra", responseupdate.jsonPath().getString("loadingPointState"));
		assertEquals("id:3", responseupdate.jsonPath().getString("postLoadId"));
		assertEquals("Raipur", responseupdate.jsonPath().getString("unloadingPointCity"));
		assertEquals("Chhattisgarh", responseupdate.jsonPath().getString("unloadingPointState"));
		assertEquals("Gold", responseupdate.jsonPath().getString("productType"));
		assertEquals("OPEN_HALF_BODY", responseupdate.jsonPath().getString("truckType"));
		assertEquals("6", responseupdate.jsonPath().getString("noOfTrucks"));
		assertEquals("1000kg", responseupdate.jsonPath().getString("weight"));
		assertEquals("01/05/21", responseupdate.jsonPath().getString("loadDate"));
		assertEquals("100", responseupdate.jsonPath().getString("rate"));
		assertEquals("PER_TON", responseupdate.jsonPath().getString("unitValue"));
		
		Response responsedel = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		
		assertEquals(200, responsedel.statusCode());
		assertEquals(CommonConstants.deleteSuccess, responsedel.jsonPath().getString("status"));
	}
	
	
	
	// loading point empty
	@Test
	@Order(25)
	public void updateData2() throws Exception {

		//adding load
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment8",
        	    "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
        String inputJson = mapToJson(loadrequest);
        Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
		String loadid = response.jsonPath().getString("loadId");
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest("",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyloadingpoint, responseupdate.jsonPath().getString("status"));
		
		System.err.println("++++++++++++++++++++++++++++++++++++25");
		System.err.println("o " + responseupdate.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response responsedel = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		assertEquals(200, responsedel.statusCode());
		assertEquals(CommonConstants.deleteSuccess, responsedel.jsonPath().getString("status"));
		
	}
	// loading state empty
	@Test
	@Order(25)
	public void updateData3() throws Exception {

		//adding load
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment9",
        	    "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
        String inputJson = mapToJson(loadrequest);
        Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
		String loadid = response.jsonPath().getString("loadId");
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,"",null,null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyloadingstate, responseupdate.jsonPath().getString("status"));
		
		System.err.println("++++++++++++++++++++++++++++++++++++25");
		System.err.println("o " + responseupdate.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response responsedel = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		assertEquals(200, responsedel.statusCode());
		assertEquals(CommonConstants.deleteSuccess, responsedel.jsonPath().getString("status"));
		
	}
	//loading point city empty
	@Test
	@Order(25)
	public void updateData4() throws Exception {

		//adding load
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment11",
        	    "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
        String inputJson = mapToJson(loadrequest);
        Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
		String loadid = response.jsonPath().getString("loadId");
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest(null,"",null,null,null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyloadingcity, responseupdate.jsonPath().getString("status"));
		
		System.err.println("++++++++++++++++++++++++++++++++++++25");
		System.err.println("o " + responseupdate.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response responsedel = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		assertEquals(200, responsedel.statusCode());
		assertEquals(CommonConstants.deleteSuccess, responsedel.jsonPath().getString("status"));
		
	}
	// unloading point empty
	@Test
	@Order(25)
	public void updateData5() throws Exception {

		//adding load
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment22",
        	    "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
        String inputJson = mapToJson(loadrequest);
        Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
		String loadid = response.jsonPath().getString("loadId");
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,null,null,"",null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyunloadingpoint, responseupdate.jsonPath().getString("status"));
		
		System.err.println("++++++++++++++++++++++++++++++++++++25");
		System.err.println("o " + responseupdate.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response responsedel = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		assertEquals(200, responsedel.statusCode());
		assertEquals(CommonConstants.deleteSuccess, responsedel.jsonPath().getString("status"));
		
	}
	
	// unloading state empty
	@Test
	@Order(25)
	public void updateData6() throws Exception {

		//adding load
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment33",
        	    "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
        String inputJson = mapToJson(loadrequest);
        Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
		String loadid = response.jsonPath().getString("loadId");
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,null,null,null,null,"",null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyunloadingstate, responseupdate.jsonPath().getString("status"));
		
		System.err.println("++++++++++++++++++++++++++++++++++++25");
		System.err.println("o " + responseupdate.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response responsedel = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		assertEquals(200, responsedel.statusCode());
		assertEquals(CommonConstants.deleteSuccess, responsedel.jsonPath().getString("status"));
		
	}
	//unloading city empty
	@Test
	@Order(25)
	public void updateData7() throws Exception {

		//adding load
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment44",
        	    "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
        String inputJson = mapToJson(loadrequest);
        Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
		String loadid = response.jsonPath().getString("loadId");
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,null,null,null,"",null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyunloadingcity, responseupdate.jsonPath().getString("status"));
		
		System.err.println("++++++++++++++++++++++++++++++++++++25");
		System.err.println("o " + responseupdate.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response responsedel = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		assertEquals(200, responsedel.statusCode());
		assertEquals(CommonConstants.deleteSuccess, responsedel.jsonPath().getString("status"));
	}
	//empty id
	@Test
	@Order(25)
	public void updateData8() throws Exception {

		//adding load
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment55",
        	    "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
        String inputJson = mapToJson(loadrequest);
        Response response = (Response) RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
        
		String loadid = response.jsonPath().getString("loadId");
		
		//update request
		LoadRequest loadrequestupdate = new LoadRequest( null,null,null,"",null,null,null,null,null,null,null,null,null,null,null,null);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(CommonConstants.emptyshiperid, responseupdate.jsonPath().getString("status"));
		
		System.err.println("++++++++++++++++++++++++++++++++++++25");
		System.err.println("o " + responseupdate.jsonPath().getString("status"));
		System.err.println("++++++++++++++++++++++++++++++++++++");
		
		Response responsedel = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();
		assertEquals(200, responsedel.statusCode());
		assertEquals(CommonConstants.deleteSuccess, responsedel.jsonPath().getString("status"));
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
