package com.zeller.studrive.offerservice.repository;

import com.zeller.studrive.offerservice.model.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import java.text.CharacterIterator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CustomRideRepositoryImpl implements CustomRideRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public GeoResults<Ride> findAvailableRides(LocalDateTime startDate, Point point, Distance distance) {
		Query query = new Query();
		Criteria criteria = Criteria.where("startDate").gte(startDate);
		query.addCriteria(criteria);
		NearQuery near = NearQuery.near(point);
		near.query(query);
		near.spherical(true);
		near.inKilometers();
		near.maxDistance(distance);
		GeoResults<Ride> results = mongoTemplate.geoNear(near, Ride.class);
		return results;
	}
}
