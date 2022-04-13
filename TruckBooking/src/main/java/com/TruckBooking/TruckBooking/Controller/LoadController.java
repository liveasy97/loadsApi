package com.TruckBooking.TruckBooking.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Model.LoadRequest;
import com.TruckBooking.TruckBooking.Service.LoadServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoadController {
/// this is the new change of controller class
	@Autowired
	public LoadServiceImpl loadService;

	@GetMapping("/home")
	public String getmessage() {
		return "Welcome to loadApi git actions second check...!!!";
	}

	@PostMapping("/load")
	public ResponseEntity<Object> load(@Valid @RequestBody LoadRequest loadrequest) {
		log.info("Post Controller Started");
		return new ResponseEntity<>(loadService.addLoad(loadrequest), HttpStatus.CREATED);
	}

	@GetMapping("/load")
	public ResponseEntity<List<Load>> findLoads(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(name = "loadingPointCity", required = false) String loadingPointCity,
			@RequestParam(name = "unloadingPointCity", required = false) String unloadingPointCity,
			@RequestParam(name = "postLoadId", required = false) String postLoadId,
			@RequestParam(name = "truckType", required = false) String truckType,
			@RequestParam(name = "loadDate", required = false) String loadDate,
			@RequestParam(name = "suggestedLoads", required = false) boolean suggestedLoads) {

		log.info("Get with Params Controller Started");

		return new ResponseEntity<>(loadService.getLoads(pageNo, loadingPointCity, unloadingPointCity, postLoadId,
				truckType, loadDate, suggestedLoads), HttpStatus.OK);
	}

	@GetMapping("/load/{loadId}")
	public ResponseEntity<Object> findLoad(@PathVariable String loadId) {
		log.info("Get by loadId Controller Started");
		return new ResponseEntity<>(loadService.getLoad(loadId), HttpStatus.OK);
	}

	@PutMapping("/load/{loadId}")
	public ResponseEntity<Object> updateLoad(@PathVariable String loadId, @RequestBody LoadRequest loadrequest) {

		log.info("Put Controller Started");
		return new ResponseEntity<>(loadService.updateLoad(loadId, loadrequest), HttpStatus.OK);
	}

	@DeleteMapping("/load/{loadId}")
	public ResponseEntity<Object> deleteLoad(@PathVariable String loadId) {
		log.info("Delete Controller Started");
		loadService.deleteLoad(loadId);
		return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
	}

}
