package com.flightApp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * flight dto
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FlightDto {
	private String flightId;

	private String airLine;

	private String source;

	private String destination;

	private String startDate;

	private String endDate;

	private String startTime;

	private String endTime;

	private String instrumentUsed;

	private Integer businessClassSeats;

	private Integer nonBusinessClassSeats;

	private Double ticketCost;

	private Integer totalRows;

	private String meal;

	private String status;

}
