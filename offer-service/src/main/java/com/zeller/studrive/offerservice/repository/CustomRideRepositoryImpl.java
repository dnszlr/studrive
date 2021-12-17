package com.zeller.studrive.offerservice.repository;

import com.zeller.studrive.offerservice.model.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;

public class CustomRideRepositoryImpl implements CustomRideRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public GeoResults<Ride> findAvailableRides(String index, LocalDateTime startDate, Point point, Distance distance) {
		String indexName = mongoTemplate.indexOps("ride").ensureIndex(new GeospatialIndex(index));
		Query query = new Query();
		Criteria criteria = Criteria.where("startDate").lte(startDate);
		query.addCriteria(criteria);
		NearQuery near = NearQuery.near(point);
		near.query(query);
		near.spherical(true);
		near.inKilometers();
		near.maxDistance(distance);
		GeoResults<Ride> results = mongoTemplate.geoNear(near, Ride.class);
		mongoTemplate.indexOps("ride").dropIndex(indexName);
		return results;
	}
}
