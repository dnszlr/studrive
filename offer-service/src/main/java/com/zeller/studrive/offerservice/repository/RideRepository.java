package com.zeller.studrive.offerservice.repository;

import com.zeller.studrive.offerservice.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends MongoRepository<String, Ride> {
}
