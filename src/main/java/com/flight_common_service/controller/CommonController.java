package com.flight_common_service.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.flightApp.DTOs.FileDto;
import com.flightApp.DTOs.FlightDto;
import com.flight_common_service.proxy.CommonProxy;
import com.flight_common_service.ui.FlightResponseModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

/**
 * @author common api's
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1.0/flight/")
@Slf4j
public class CommonController {

	private final CommonProxy commonProxy;

	private final ModelMapper modelMapper;

	public CommonController(CommonProxy commonProxy, ModelMapper modelMapper) {
		this.commonProxy = commonProxy;
		this.modelMapper = modelMapper;
	}

	/**
	 * search flights based on given parameters
	 * 
	 * @param source
	 * @param destination
	 * @param date
	 * @param seatType
	 * @return
	 */
	@GetMapping("/search/")
	@CircuitBreaker(name = "flightWSCircuitBreaker", fallbackMethod = "flightWSFallBack")
	public ResponseEntity<List<FlightDto>> searchFlights(@RequestParam String source, @RequestParam String destination,
			@RequestParam String date, @RequestParam String seatType) {
		
		log.info("searchFlights called");
		
		final List<FlightDto> searchedFlights = commonProxy.searchFlights(source, destination, date, seatType);
		
		return ResponseEntity.status(HttpStatus.OK).body(searchedFlights);

	}

	/**
	 * find all the flights
	 *
	 */
	@GetMapping("/flights")
	@CircuitBreaker(name = "flightWSCircuitBreaker", fallbackMethod = "flightWSFallBack")
	public ResponseEntity<List<FlightResponseModel>> getAllFlights() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		log.info("getAllFlights called");
		final List<FlightResponseModel> flights = new ArrayList<>();
		final List<FlightDto> res = commonProxy.getAllFlights();
		for (final FlightDto flightDto : res) {
			flights.add(modelMapper.map(flightDto, FlightResponseModel.class));
		}
		return ResponseEntity.status(HttpStatus.OK).body(flights);

	}

	/**
	 * upload flight logo
	 *
	 */
	@PostMapping("/upload")
	@CircuitBreaker(name = "flightWSCircuitBreaker", fallbackMethod = "flightWSFallBack")
	public ResponseEntity<String> uploadFile(MultipartFile file, @RequestParam String flightId) {
		log.info("started uploadFile **");
		final String message = commonProxy.uploadFile(file, flightId);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	/**
	 * download flight logo
	 *
	 */
	@GetMapping("/download")
	@CircuitBreaker(name = "flightWSCircuitBreaker", fallbackMethod = "flightWSFallBack")
	public ResponseEntity<List<FileDto>> downloadFile(
			@RequestParam(value = "flightIds", required = false) List<String> flightIds) {
		log.info("started downloadFile **");
		final List<FileDto> fileDto = commonProxy.downloadFile(flightIds);
		return ResponseEntity.status(HttpStatus.OK).body(fileDto);
	}

	/**
	 * show response message if another service is down
	 *
	 */
	public ResponseEntity<?> flightWSFallBack(final Exception e) {
		return ResponseEntity.ok("within myTestFallBack method. FLIGHT-WS is down" + e.toString());
	}

}