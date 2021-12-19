package com.zeller.studrive.offerservice.repository;

import com.zeller.studrive.offerservice.basic.Constant;
import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.model.RideStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class CustomRideRepositoryImpl implements CustomRideRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * Performs a geoquery on the database to find all available rides within a radius of 30 km
	 *
	 * @param index     - The index that must be applied to the respective address field
	 * @param startDate - All rides take place after this date
	 * @param point     - The point that determines the location of the address on a map
	 * @return The list of GeoResults returned from the database
	 */
	@Override
	public GeoResults<Ride> findAvailableRides(String index, LocalDateTime startDate, Point point) {
		// As far as I know it is not possible to define the 'Key' parameter in MongoRepository or MongoTemplate, the index has to be
		// created dynamically at this point and removed again at the end. This is due to the fact that mongodb doesn't support two
		// geospatialindexes in one entity, because otherwise it doesn't know which one to use when executing the query.
		// https://stackoverflow.com/questions/70397173/how-to-use-multiple-geospatialindexes-with-mongotemplate-or-mongorepository
		String indexName = mongoTemplate.indexOps(Constant.RIDECOLLECTION).ensureIndex(new GeospatialIndex(index));
		Query query = new Query();
		Criteria time = Criteria.where(Constant.STARTDATE).lte(startDate);
		Criteria status = Criteria.where(Constant.RIDESTATUS).is(RideStatus.AVAILABLE);
		query.addCriteria(time);
		query.addCriteria(status);
		NearQuery near = NearQuery.near(point);
		near.query(query);
		near.spherical(true);
		near.inKilometers();
		// If the radius should be set dynamically, this parameter must be passed through and not set here.
		near.maxDistance(new Distance(30));
		GeoResults<Ride> results = mongoTemplate.geoNear(near, Ride.class);
		mongoTemplate.indexOps(Constant.RIDECOLLECTION).dropIndex(indexName);
		return results;
	}
}
