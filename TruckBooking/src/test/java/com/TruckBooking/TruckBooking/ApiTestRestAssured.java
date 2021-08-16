
package com.TruckBooking.TruckBooking;

import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.TruckBooking.Model.LoadRequest.UnitValue;
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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class ApiTestRestAssured {

	private static String loadid1;
	private static String loadid2;
	private static String loadid3;
    private static String loadid4;

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
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		loadid4 = response.jsonPath().getString("loadId");

		while (true) {
			response11 = RestAssured.given().param("pageNo", loadingpointcitypagecount).param("loadingPointCity", "Nagpur")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();


			loadingpointcitycount += response11.jsonPath().getList("$").size();
			if (response11.jsonPath().getList("$").size() != CommonConstants.pagesize)
				break;

			loadingpointcitypagecount++;
		}

		Response response22;
		while (true) {
			response22 = RestAssured.given().param("pageNo", loadingunloadingpointcitypagecount).param("loadingPointCity", "Nagpur")
					.param("unloadingPointCity", "Raipur")
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
			response44 = RestAssured.given().param("pageNo", loaddatepagecount).param("loadDate", "22/01/2021")
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

		LoadRequest loadrequest1 = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson1 = mapToJson(loadrequest1);
		Response response1 = (Response) RestAssured.given().header("", "").body(inputJson1).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		LoadRequest loadrequest2 = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson2 = mapToJson(loadrequest2);
		Response response2 = (Response) RestAssured.given().header("", "").body(inputJson2).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		LoadRequest loadrequest3 = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson3 = mapToJson(loadrequest3);
		Response response3 = (Response) RestAssured.given().header("", "").body(inputJson3).header("accept", "application/json").header("Content-Type", "application/json").post().then().extract().response();

		loadid1 = response1.jsonPath().getString("loadId");
		loadid2 = response2.jsonPath().getString("loadId");
		loadid3 = response3.jsonPath().getString("loadId");

		assertEquals(201, response1.statusCode());
		assertEquals(Load.Status.PENDING.toString(), response1.jsonPath().getString("status"));

		assertEquals(201, response2.statusCode());
		assertEquals(Load.Status.PENDING.toString(), response2.jsonPath().getString("status"));

		assertEquals(201, response3.statusCode());
		assertEquals(Load.Status.PENDING.toString(), response3.jsonPath().getString("status"));

	}

	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		return objectMapper.writeValueAsString(object);
	}
	
	
	// when User request for post load with all parameter in proper data types.
	// line:191= changing load-request to J-son format & storing in inputJson. 
	//line:194= firing post api with inserting inputJson & and storing response in response variable.
	//line 200= checking expected status code with response status code.
	//line:202= checking staus of load either it is expected or not with response status.
	//line:205= deleting response by firing delete
	@Test
	@Order(1)
	public void addloadsuccess() throws Exception {
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();



		assertEquals(201, response.statusCode());
		assertEquals(Load.Status.PENDING.toString(), response.jsonPath().getString("status"));

		Response response1 = RestAssured.given().header("", "").delete("/" + response.jsonPath().getString("loadId")).then().extract().response();
		

	}
	
	
	// loading point city null
	// validation error occur 
	//checking response status code, error and response message with parameters.
	@Test
	@Order(4)
	public void addloadingcitynull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", null, "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		JsonPath jsonPathEvaluator = response.jsonPath();


		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=loadingPointCity, message=Loading Point City Cannot Be Empty, rejectedValue=null, object=loadRequest}]",
				jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}


		// loading point null
		// validation error occur 
		//checking response status code, error and response message with parameters.
	@Test
	@Order(5)
	public void addloadingpointpointnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest(null, "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=loadingPoint, message=Loading Point Cannot Be Empty, rejectedValue=null, object=loadRequest}]",
				jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}

			// loading point state null
			// validation error occur 
			//checking response status code, error and response message with parameters.
	@Test
	@Order(6)
	public void addloadingpointstatenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", null, "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=loadingPointState, message=Loading Point State Cannot Be Empty, rejectedValue=null, object=loadRequest}]",
				jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}
			// id null
			// validation error occur 
			//checking response status code, error and response message with parameters.
	@Test
	@Order(7)
	public void addidnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", null, "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=postLoadId, message=PostLoad Id Cannot Be Empty, rejectedValue=null, object=loadRequest}]",
				jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}
			// unloading point null
			// validation error occur 
			//checking response status code, error and response message with parameters.
	@Test
	@Order(8)
	public void addloadunloadingpointnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", null, "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=unloadingPoint, message=Unloading Point Cannot Be Empty, rejectedValue=null, object=loadRequest}]", jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}
			// unloading point city null
			// validation error occur 
			//checking response status code, error and response message with parameters.
	@Test
	@Order(9)
	public void addloadunloadingpointcitynull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", null,
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=unloadingPointCity, message=Unloading Point City Cannot Be Empty, rejectedValue=null, object=loadRequest}]",jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());
	}
	
	
	
			// unloading point state null
			// validation error occur 
			//checking response status code, error and response message with parameters.
	@Test
	@Order(10)
	public void addloadunloadingpointstatenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				null, "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=unloadingPointState, message=Unloading Point State Cannot Be Empty, rejectedValue=null, object=loadRequest}]",jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());
	}
	
	
			// producttype null
			// validation error occur 
			//checking response status code, error and response message with parameters.
	@Order(11)
	public void addloadproducttypenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", null, "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();
		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=productType, message=Product Type Cannot Be Empty, rejectedValue=null, object=loadRequest}]",jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}
			// trucktype null
			// validation error occur 
			//checking response status code, error and response message with parameters.
	@Test
	@Order(12)
	public void addloadtrucktypenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", null, "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();


		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=truckType, message=Truck Type Cannot Be Empty, rejectedValue=null, object=loadRequest}]",jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}
		// no of truck null
		// validation error occur 
		//checking response status code, error and response message with parameters.
	@Test
	@Order(13)
	public void addloadnooftrucknull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagrpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", null, "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=noOfTrucks, message=No. of trucks Cannot Be Empty, rejectedValue=null, object=loadRequest}]",jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}
	
	
			// weight null
			// validation error occur 
			//checking response status code, error and response message with parameters.
	@Test
	@Order(14)
	public void addloadweightnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", null,"add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=weight, message=Weight Cannot Be Empty, rejectedValue=null, object=loadRequest}]",jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}
			// load comment null (optioal)
			// no error occurs
			//checking expected status code with response status code.
			//checking staus of load either it is expected or not with response status.
			//deleting response by firing delete api
	@Test
	@Order(15)
	public void addloadcommentnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg",null, "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(201, response.statusCode());
		assertEquals(Load.Status.PENDING.toString(), response.jsonPath().getString("status"));

		Response response1 = RestAssured.given().header("", "").delete("/" + response.jsonPath().getString("loadId")).then().extract().response();
		
	}

			// loading date null
			// validation error occur 
			//checking response status code, error and response message with parameters.
	@Test
	@Order(16)
	public void addloaddatenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", null, (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(400, response.statusCode());
		assertEquals("Validation error", jsonPathEvaluator.get("loaderrorresponse.message").toString());
		assertEquals("[{field=loadDate, message=Load Date Cannot Be Empty, rejectedValue=null, object=loadRequest}]",jsonPathEvaluator.get("loaderrorresponse.subErrors").toString());

	}
				// load status null (optioal)
				// no error occurs
				//checking expected status code with response status code.
				//checking staus of load either it is expected or not with response status.
				//deleting response by firing delete api
	@Test
	@Order(17)
	public void addloadstatusnull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, null);

		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(201, response.statusCode());
		assertEquals(Load.Status.PENDING.toString(), response.jsonPath().getString("status"));

		Response response1 = RestAssured.given().header("", "").delete("/" + response.jsonPath().getString("loadId")).then().extract().response();
		
	}
	
			// load rate null
			// error occur 
			//checking response status code, error and response message with parameters.

	@Test
	@Order(16)
	public void addloadratenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/21", null,  UnitValue.PER_TON, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(422, response.statusCode());
		assertEquals("ErrorUnitValue can't be set when the rate is not provided", jsonPathEvaluator.get("loaderrorresponse.message").toString());


	}
	
	
			// unit value null
			//  error occur 
			//checking response status code, error and response message with parameters.

	@Test
	@Order(16)
	public void addloadunitvaluenull() throws Exception {

		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/21", (long)100,  null, Load.Status.PENDING);


		String inputJson = mapToJson(loadrequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		JsonPath jsonPathEvaluator = response.jsonPath();

		assertEquals(422, response.statusCode());
		assertEquals("ErrorUnitValue can't be null when the rate is provided", jsonPathEvaluator.get("loaderrorresponse.message").toString());


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

	//getload by loadingPointCity
	//user get in descending order according to timestamp each page having 15 loads
	// we checking size of load present in last page of our data base + 3 load (which we adding in before all )
	// by this we comparing size of load present in response.
	@Test
	@Order(19)
	public void getloadloadingpointcity() throws Exception {

		long loadingpointcity=3;
		long lastPageCount = (loadingpointcitycount + loadingpointcity)%CommonConstants.pagesize;
		long page = (loadingpointcitycount + loadingpointcity)/CommonConstants.pagesize;


		Response response = RestAssured.given().param("loadingPointCity", "Nagpur")
				.param("pageNo", page)
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();

    
		assertEquals(lastPageCount, response.jsonPath().getList("$").size());
	}

	// getload unloadingPointCity
	//user get in descending order according to timestamp each page having 15 loads
		// we checking size of load present in last page of our data base + 3 load (which we adding in before all )
		// by this we comparing size of load present in response.
	@Test
	@Order(20)
	public void getloadunloadingPointCity() throws Exception {

		long loadingunloadingpointcity=3;
		long lastPageCount = (loadingunloadingpointcitycount + loadingunloadingpointcity)%CommonConstants.pagesize;
		long page = (loadingunloadingpointcitycount + loadingunloadingpointcity)/CommonConstants.pagesize;

		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("loadingPointCity", "Nagpur")
				.param("unloadingPointCity", "Raipur")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();

		assertEquals(lastPageCount, response.jsonPath().getList("$").size());
	}
	//getloadshipperId
	//user get in descending order according to timestamp each page having 15 loads
		// we checking size of load present in last page of our data base + 3 load (which we adding in before all )
		// by this we comparing size of load present in response.
	@Test
	@Order(21)
	public void getloadloadshipperId() throws Exception {
		long loadshipperId = 3;
		long lastPageCount = (postloadidcount+loadshipperId)% CommonConstants.pagesize;
		long page = (postloadidcount+loadshipperId)/CommonConstants.pagesize;

		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("postLoadId", "id:1")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();

		assertEquals(lastPageCount, response.jsonPath().getList("$").size());
	}

	//getloadtruckType
	//user get in descending order according to timestamp each page having 15 loads
		// we checking size of load present in last page of our data base + 3 load (which we adding in before all )
		// by this we comparing size of load present in response.
	@Test
	@Order(22)
	public void getloadtruckType() throws Exception {

		long trucktype=3;
		long lastPageCount = (trucktypecount+trucktype)%CommonConstants.pagesize;
		long page = (trucktypecount+trucktype)/CommonConstants.pagesize;

		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("truckType", "OPEN_HALF_BODY")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();

		assertEquals(lastPageCount, response.jsonPath().getList("$").size());
	}
	//getload date
	//user get in descending order according to timestamp each page having 15 loads
		// we checking size of load present in last page of our data base + 3 load (which we adding in before all )
		// by this we comparing size of load present in response.
	@Test
	@Order(23)
	public void getloaddate() throws Exception {

		long loaddate=3;
		long lastPageCount = (loaddatecount+loaddate)%CommonConstants.pagesize;
		long page = (loaddatecount+loaddate)/CommonConstants.pagesize;

		Response response = RestAssured.given()
				.param("pageNo", page)
				.param("loadDate", "22/01/2021")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();

		assertEquals(lastPageCount, response.jsonPath().getList("$").size());

	}

	//getload loadid
	// in this we camparing each parameter with response parameter.
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
		assertEquals("1000kg", response.jsonPath().getString("weight"));
		assertEquals("22/01/2021", response.jsonPath().getString("loadDate"));

	}
	
	
	//when user update load with all parameters.
	//it is successfull case so we comparing all requesting parameters with response parametes.

	@Test
	@Order(25)
	public void updateDataallparamaters() throws Exception {

		String loadid = loadid1;

		//update request
		LoadRequest loadrequestupdate = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));
		assertEquals("Nagpur", responseupdate.jsonPath().getString("loadingPoint"));
		assertEquals("Nagpur", responseupdate.jsonPath().getString("loadingPointCity"));
		assertEquals("Maharashtra", responseupdate.jsonPath().getString("loadingPointState"));
		assertEquals("id:1", responseupdate.jsonPath().getString("postLoadId"));
		assertEquals("Raipur", responseupdate.jsonPath().getString("unloadingPoint"));
		assertEquals("Raipur", responseupdate.jsonPath().getString("unloadingPointCity"));
		assertEquals("Chhattisgarh", responseupdate.jsonPath().getString("unloadingPointState"));
		assertEquals("Gold", responseupdate.jsonPath().getString("productType"));
		assertEquals("OPEN_HALF_BODY", responseupdate.jsonPath().getString("truckType"));
		assertEquals("6", responseupdate.jsonPath().getString("noOfTrucks"));
		assertEquals("1000kg", responseupdate.jsonPath().getString("weight"));
		assertEquals("22/01/2021", responseupdate.jsonPath().getString("loadDate"));
		assertEquals("100", responseupdate.jsonPath().getString("rate"));
		assertEquals("PER_TON", responseupdate.jsonPath().getString("unitValue"));
	}

	// when user update load with all null values
	//then in response user get same previous load.
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
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));
		assertEquals("Nagpur", responseupdate.jsonPath().getString("loadingPoint"));
		assertEquals("Nagpur", responseupdate.jsonPath().getString("loadingPointCity"));
		assertEquals("Maharashtra", responseupdate.jsonPath().getString("loadingPointState"));
		assertEquals("id:1", responseupdate.jsonPath().getString("postLoadId"));
		assertEquals("Raipur", responseupdate.jsonPath().getString("unloadingPoint"));
		assertEquals("Raipur", responseupdate.jsonPath().getString("unloadingPointCity"));
		assertEquals("Chhattisgarh", responseupdate.jsonPath().getString("unloadingPointState"));
		assertEquals("Gold", responseupdate.jsonPath().getString("productType"));
		assertEquals("OPEN_HALF_BODY", responseupdate.jsonPath().getString("truckType"));
		assertEquals("6", responseupdate.jsonPath().getString("noOfTrucks"));
		assertEquals("1000kg", responseupdate.jsonPath().getString("weight"));
		assertEquals("22/01/2021", responseupdate.jsonPath().getString("loadDate"));
		assertEquals("100", responseupdate.jsonPath().getString("rate"));
		assertEquals("PER_TON", responseupdate.jsonPath().getString("unitValue"));

	}

	// when user enter loading point empty
	//they get same previous one string in that loading point null parameter after updated.
	// then we check statusCode and status of response

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
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));

	}
		// when user enter loading point state empty
		//they get same previous one string in that loading point null parameter after updated.
		// then we check statusCode and status of response
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
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));

	}
		// when user enter loading point city empty
		//they get same previous one string in that loading point null parameter after updated.
		// then we check statusCode and status of response
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
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));

	}
		// when user enter unloading point empty
		//they get same previous one string in that loading point null parameter after updated.
		// then we check statusCode and status of response
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
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));

	}

		// when user enter unloading state empty
		//they get same previous one string in that loading point null parameter after updated.
		// then we check statusCode and status of response
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
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));

	}
		// when user enter unloading city empty
		//they get same previous one string in that loading point null parameter after updated.
		// then we check statusCode and status of response	
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
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));

	}
		// when user enter id empty
		//they get same previous one string in that id null parameter after updated.
		// then we check statusCode and status of response
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
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));

	}

	// when user updated some parameters only and other are null
	// then user get load with updated parameters and null parameters are remain same as previous.
	// we checked all updated parametrs and previous paramametrs with updated response loads

	@Test
	@Order(34)
	public void updateData9() throws Exception {

		String loadid = loadid1;

		//update request
		LoadRequest loadrequestupdate = new LoadRequest("market", null, null, null, "mall", null,
				null, null, null, null, "10000kg",null, "01/05/20", null,  null, null);



		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		assertEquals(200, responseupdate.statusCode());
		assertEquals(Load.Status.PENDING.toString(), responseupdate.jsonPath().getString("status"));
		assertEquals("market", responseupdate.jsonPath().getString("loadingPoint"));
		assertEquals("Nagpur", responseupdate.jsonPath().getString("loadingPointCity"));
		assertEquals("Maharashtra", responseupdate.jsonPath().getString("loadingPointState"));
		assertEquals("id:1", responseupdate.jsonPath().getString("postLoadId"));
		assertEquals("mall", responseupdate.jsonPath().getString("unloadingPoint"));
		assertEquals("Raipur", responseupdate.jsonPath().getString("unloadingPointCity"));
		assertEquals("Chhattisgarh", responseupdate.jsonPath().getString("unloadingPointState"));
		assertEquals("Gold", responseupdate.jsonPath().getString("productType"));
		assertEquals("OPEN_HALF_BODY", responseupdate.jsonPath().getString("truckType"));
		assertEquals("6", responseupdate.jsonPath().getString("noOfTrucks"));
		assertEquals("10000kg", responseupdate.jsonPath().getString("weight"));
		assertEquals("01/05/20", responseupdate.jsonPath().getString("loadDate"));
		assertEquals("100", responseupdate.jsonPath().getString("rate"));
		assertEquals("PER_TON", responseupdate.jsonPath().getString("unitValue"));
	}

	
	//in this case user updating rate without updating unit value 
	//loaderror response occurs,
	// then we checked status of response and loaderrorresponse message 
	
	@Test
	@Order(34)
	public void updateDataFail1() throws Exception {

		String loadid = loadid1;

		//update request
		LoadRequest loadrequestupdate = new LoadRequest("market", null, null, null, "mall", null,
				null, null, null, null, "10000kg",null, "01/05/20", (long) 100,  null, null);



		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();
		JsonPath jsonPathEvaluator = responseupdate.jsonPath();

		assertEquals(422, responseupdate.statusCode());
		assertEquals("ErrorUnitValue can't be null when the rate is provided", jsonPathEvaluator.get("loaderrorresponse.message").toString());

	}

		//in this case user updating unit value without rate 
		//loaderror response occurs,
		// then we checked status of response and loaderrorresponse message 
	@Test
	@Order(34)
	public void updateDatafail2() throws Exception {

		String loadid = loadid1;

		//update request
		LoadRequest loadrequestupdate = new LoadRequest("market", null, null, null, "mall", null,
				null, null, null, null, "10000kg",null, "01/05/20", null,  UnitValue.PER_TON, null);



		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();
		JsonPath jsonPathEvaluator = responseupdate.jsonPath();

		assertEquals(422, responseupdate.statusCode());
		assertEquals("ErrorUnitValue can't be set when the rate is not provided", jsonPathEvaluator.get("loaderrorresponse.message").toString());
	}
	
	
	// when user updating load with wrong load id which not present in data base
	//loaderrorresponse occur 
	//check response status code with expected
	//comparing response message with expected message


	@Test
	@Order(35)
	public void updateDatafail() throws Exception {

		String loadid = "load1:0a5f1700-041a-43d4-b3eb-00000000123";

		//update request
		LoadRequest loadrequestupdate = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur",
				"Chhattisgarh", "Gold", "OPEN_HALF_BODY", "6", "1000kg","add comment", "22/01/2021", (long) 100,  UnitValue.PER_TON, Load.Status.PENDING);

		String inputJsonupdate = mapToJson(loadrequestupdate);

		Response responseupdate = RestAssured.given().header("", "").body(inputJsonupdate).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + loadid).then().extract().response();

		JsonPath jsonPathEvaluator = responseupdate.jsonPath();

		assertEquals(404, responseupdate.statusCode());
		assertEquals("Load was not found for parameters {id=load1:0a5f1700-041a-43d4-b3eb-00000000123}", jsonPathEvaluator.get("loaderrorresponse.message").toString());

	}
		
		// when user deleting load with wrong load id which not present in data base
		//loaderrorresponse occur 
		//check response status code with expected
		//comparing response message with expected message
	
	@Test
	@Order(36)
	public  void deleteDatafail() throws Exception{

		String loadid = "load1:0a5f1700-041a-43d4-b3eb-00000000123";
		Response response1 = RestAssured.given().header("", "").delete("/" + loadid).then().extract().response();

		JsonPath jsonPathEvaluator = response1.jsonPath();

		assertEquals(404, response1.statusCode());
		assertEquals("Load was not found for parameters {id=load1:0a5f1700-041a-43d4-b3eb-00000000123}", jsonPathEvaluator.get("loaderrorresponse.message").toString());
	}


	@AfterAll

	public static  void deleteData() throws Exception {


		Response response1 = RestAssured.given().header("", "").delete("/" + loadid1).then().extract().response();
		JsonPath jsonPathEvaluator = response1.jsonPath();
		assertEquals(200, response1.statusCode());
		assertEquals("Successfully Deleted", response1.asString());

		Response response2 = RestAssured.given().header("", "").delete("/" + loadid2).then().extract().response();
		assertEquals(200, response2.statusCode());
		assertEquals("Successfully Deleted", response1.asString());

		Response response3 = RestAssured.given().header("", "").delete("/" + loadid3).then().extract().response();
		assertEquals(200, response3.statusCode());
		assertEquals("Successfully Deleted", response1.asString());

		Response response4 = RestAssured.given().header("", "").delete("/" + loadid4).then().extract().response();
		assertEquals(200, response3.statusCode());
		assertEquals("Successfully Deleted", response1.asString());
	}
}

