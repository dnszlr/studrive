package com.zeller.studrive.orderservice.webclient;

import com.zeller.studrive.orderservice.service.SeatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/seats")
public class SeatController {

	private SeatService seatService;

	public SeatController(SeatService seatService) {
		this.seatService = seatService;
	}


}
