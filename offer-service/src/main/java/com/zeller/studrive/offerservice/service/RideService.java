package com.zeller.studrive.offerservice.service;

import com.zeller.studrive.offerservice.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RideService {

    @Autowired
    private RideRepository rideRepository;
}
