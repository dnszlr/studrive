package com.zeller.studrive.offerservice.repository;

import com.zeller.studrive.offerservice.model.Ride;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;

public interface CustomRideRepository {

	GeoResults<Ride> findAvailableRides(String index, LocalDateTime startDate, Point point);
}
