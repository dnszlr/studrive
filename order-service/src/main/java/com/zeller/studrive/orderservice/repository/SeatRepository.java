package com.zeller.studrive.orderservice.repository;

import com.zeller.studrive.orderservice.model.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends MongoRepository<Seat, String> {

	Optional<Seat> findSeatById(String id);

	List<Seat> findSeatsByPassengerId(Long passengerId);

	List<Seat> findSeatsByRideId(String rideId);

}
