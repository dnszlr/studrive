package com.zeller.studrive.orderservice.service;

import com.zeller.studrive.orderservice.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;
}
