package com.flight_common_service.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.flightApp.DTOs.FileDto;
import com.flightApp.DTOs.FlightDto;

/**
 * proxy to use api of other service using feign client
 *
 */
@FeignClient(value = "FLIGHT-WS")
public interface CommonProxy {

	/**
	 * search all Flights based on parameters
	 * 
	 * @param source
	 * @param destination
	 * @param departureDate
	 * @param seatType
	 * @return
	 */
	@GetMapping("/airline/search/")
	public List<FlightDto> searchFlights(@RequestParam String source, @RequestParam String destination,
			@RequestParam String departureDate, @RequestParam String seatType);

	/**
	 * get all flights
	 * 
	 * @return
	 */
	@GetMapping("/airline/flights")
	public List<FlightDto> getAllFlights();
	
	@PostMapping(name  = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadFile(MultipartFile file,@RequestParam String flightId);

	@GetMapping("/download")
	public List<FileDto> downloadFile(@RequestParam List<String> flightIds);

}
