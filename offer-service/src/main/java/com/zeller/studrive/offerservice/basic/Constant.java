package com.zeller.studrive.offerservice.basic;

public class Constant {

	// Mongo Config
	public static final String DATABASE = "offer-service";

	// Mapbox
	public static final String MAPBOXAPI = "https://api.mapbox.com/geocoding/v5/mapbox.places";
	public static final String MAPBOXTOKEN = "sk.eyJ1IjoibWthbGFzaCIsImEiOiJja3AyYWVsNm0xMjltMndsZ3FqZXhnZG11In0.G0zqmJ50IGR31LpPx82LNg";

	// Query parameters
	public static final String SLASH = "/";
	public static final String TOKENEXT = "?access_token=";
	public static final String LIMIT = "&limit=1";

	// MongoTemplate
	public static final String RIDECOLLECTION = "ride";
	public static final String RIDESTATUS = "rideStatus";
	public static final String STARTDATE = "startDate";
	public static final String STARTINDEX = "start.coordinates";
	public static final String DESTINATIONINDEX = "destination.coordinates";
}
