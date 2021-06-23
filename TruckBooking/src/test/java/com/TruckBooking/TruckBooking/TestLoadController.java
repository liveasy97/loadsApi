package com.TruckBooking.TruckBooking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Controller.LoadController;
import com.TruckBooking.TruckBooking.Dao.LoadDao;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.TruckBooking.Response.CreateLoadResponse;
import com.TruckBooking.TruckBooking.Response.DeleteLoadResponse;
import com.TruckBooking.TruckBooking.Response.UpdateLoadResponse;
import com.TruckBooking.TruckBooking.Service.LoadServiceImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(value = LoadController.class)
public class TestLoadController {
	String URI = "/load";
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoadDao loaddao;

	@MockBean
	private LoadServiceImpl loadservice;

	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		return objectMapper.writeValueAsString(object);
	}
	
	@Test
	@Order(1)
	public void addLoad() throws Exception {
		LoadRequest loadrequest = createLoadRequest();
		Load load = createLoads().get(0);
		CreateLoadResponse createloadresponse = createLoadResponse();
		
		when(loadservice.addLoad(Mockito.any(LoadRequest.class))).thenReturn(createloadresponse);
		
		String inputJson = mapToJson(loadrequest);
		String expectedJson = mapToJson(createloadresponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(CommonConstants.URI).accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	// sending loadid using url
	@Test
	@Order(2)
	public void getLoadbyloadid() throws Exception {
		LoadRequest loadrequest = createLoadRequest();
		List<Load> loads = createLoads();
		
		CreateLoadResponse createloadresponse = createLoadResponse();

		when(loadservice.getLoad("loadid:1")).thenReturn(loads.get(0));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(CommonConstants.LOADID_URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(loads.get(0));
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	// using loadingpoint city, suggestedloads = true
	@Test
	@Order(3)
	public void getLoadbyloadingpointcity1() throws Exception {
		LoadRequest loadrequest = createLoadRequest();
		List<Load> loads = createLoads();
		Collections.reverse(loads);
		
		CreateLoadResponse createloadresponse = createLoadResponse();

		when(loadservice.getLoads(0, "Nagpur", null, null, null, null, true)).thenReturn(loads);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/load")
				.queryParam("loadingPointCity", "Nagpur")
				.queryParam("pageNo", "0")
				.queryParam("suggestedLoads", "true")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(loads);
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	// using loadingpoint city, suggestedloads = false
	@Test
	@Order(4)
	public void getLoadbyloadingpointcity2() throws Exception {
		LoadRequest loadrequest = createLoadRequest();
		List<Load> loads = createLoads();
		Collections.reverse(loads);
		
		CreateLoadResponse createloadresponse = createLoadResponse();

		when(loadservice.getLoads(0, "Nagpur", null, null, null, null, false)).thenReturn(loads.subList(1, 5));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/load")
				.queryParam("loadingPointCity", "Nagpur")
				.queryParam("pageNo", "0")
				.queryParam("suggestedLoads", "false")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(loads.subList(1, 5));
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	
	@Test
	@Order(5)
	public void getLoadbyunloadingpointcity() throws Exception {
		List<Load> loads = createLoads();
		Collections.reverse(loads);

		when(loadservice.getLoads(0, null, "Raipur", null, null, null, false)).thenReturn(loads);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/load")
				.queryParam("unloadingPointCity", "Raipur")
				.queryParam("pageNo", "0")
				.queryParam("suggestedLoads", "false")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(loads);
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	@Test
	@Order(6)
	public void getLoadbypostloadid() throws Exception {
		List<Load> loads = createLoads();
		Collections.reverse(loads);

		when(loadservice.getLoads(0, null, null, "id:2", null, null, false)).thenReturn(loads.subList(3, 4));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/load")
				.queryParam("postLoadId", "id:2")
				.queryParam("pageNo", "0")
				.queryParam("suggestedLoads", "false")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(loads.subList(3, 4));
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	
	@Test
	@Order(7)
	public void getLoadbytrucktype() throws Exception {
		List<Load> loads = createLoads();
		Collections.reverse(loads);

		when(loadservice.getLoads(0, null, null, null,"OPEN_FULL_BODY", null, false)).thenReturn(loads.subList(2, 3));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/load")
				.queryParam("truckType", "OPEN_FULL_BODY")
				.queryParam("pageNo", "0")
				.queryParam("suggestedLoads", "false")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(loads.subList(2, 3));
		String outputInJson = result.getResponse().getContentAsString();
		
		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	@Test
	@Order(8)
	public void getLoadbyloaddate() throws Exception {
		List<Load> loads = createLoads();
		Collections.reverse(loads);

		when(loadservice.getLoads(0, null, null, null, null, "01/05/21", false)).thenReturn(loads.subList(2, 5));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/load")
				.queryParam("loadDate", "01/05/21")
				.queryParam("pageNo", "0")
				.queryParam("suggestedLoads", "false")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(loads.subList(2, 5));
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	@Test
	@Order(9)
	public void getLoad() throws Exception {
		
		LoadRequest loadrequest = createLoadRequest();
		List<Load> loadsret = createLoads().subList(0, 1);

		when(loadservice.getLoads(0, null, null, "id:1", null, null, false)).thenReturn(loadsret);

		String URI = "/load";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).queryParam("postLoadId", "id:1")
				.queryParam("pageNo", "0")
				.queryParam("suggestedLoads", "false")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(loadsret);
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	//get with paramaters
	@Test
	@Order(10)
	public void updateLoad() throws Exception {
		LoadRequest loadrequest = createLoadRequest();
		List<Load> loads = createLoads();
		
		UpdateLoadResponse response =  updateLoadResponse();
		String inputJson = mapToJson(loadrequest);

		when(loadservice.updateLoad(CommonConstants.LOADID, loadrequest)).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(CommonConstants.LOADID_URI).accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(response);
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	@Test
	@Order(11)
	public void deleteLoad() throws Exception {
		DeleteLoadResponse response = new DeleteLoadResponse();
		response.setStatus(CommonConstants.deleteSuccess);
		
		List<Load> loads = createLoads();
		when(loadservice.deleteLoad(CommonConstants.LOADID)).thenReturn(response);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(CommonConstants.LOADID_URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = mapToJson(response);
		String outputInJson = result.getResponse().getContentAsString();
		
		assertEquals(expectedJson, outputInJson);
		
		MockHttpServletResponse response1 = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response1.getStatus());
	}
	
	
	public LoadRequest createLoadRequest()
	{
		LoadRequest loadrequest = new LoadRequest("Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, LoadRequest.UnitValue.PER_TON);
		return loadrequest;
	}
	
	public CreateLoadResponse createLoadResponse()
	{
		CreateLoadResponse createloadresponse = new CreateLoadResponse("loadid:1", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, CreateLoadResponse.UnitValue.PER_TON);
		return createloadresponse;
	}
	
	public UpdateLoadResponse updateLoadResponse()
	{
		UpdateLoadResponse response = new UpdateLoadResponse();
		response.setStatus(CommonConstants.updateSuccess);
		return response;
	}
	
	public List<Load> createLoads()
	{
        List<Load> loads = Arrays.asList
        (	
        new Load("loadid:1", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON),
        new Load("loadid:2", "Nagpur", "Nagpur", "Maharashtra", "id:2", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON),
        new Load("loadid:3", "Nagpur", "Nagpur", "Maharashtra", "id:3", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_FULL_BODY", "6", "10000kg", "01/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON),
        new Load("loadid:4", "Nagpur", "Nagpur", "Maharashtra", "id:4", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "02/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON),
        new Load("loadid:5", "Mumbai", "Mumbai", "Maharashtra", "id:5", "Raipur", "Raipur", "Chhattisgarh", "Gold",
        	    "OPEN_HALF_BODY", "6", "10000kg", "03/05/21", "added comment", "pending",(long) 100, Load.UnitValue.PER_TON)
        );
		return loads;
	}	

}
