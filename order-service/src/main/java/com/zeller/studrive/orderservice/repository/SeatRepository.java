package com.zeller.studrive.orderservice.repository;

import com.zeller.studrive.orderservice.model.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends MongoRepository<Seat, String> {

	List<Seat> findSeatsByPassengerId(Long passengerId);

	List<Seat> findSeatsByRideId(String rideId);

}
