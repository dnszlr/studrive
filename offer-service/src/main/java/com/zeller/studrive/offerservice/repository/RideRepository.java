package com.zeller.studrive.offerservice.repository;

import com.zeller.studrive.offerservice.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends MongoRepository<Ride, String>, CustomRideRepository {

	List<Ride> findRidesByDriverId(Long driverId);
}
