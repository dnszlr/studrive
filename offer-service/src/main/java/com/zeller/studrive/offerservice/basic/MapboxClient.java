package com.zeller.studrive.offerservice.basic;

import com.jayway.jsonpath.JsonPath;
import com.zeller.studrive.offerservice.model.Address;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
public class MapboxClient {

	private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

	private final WebClient mapboxClient;
	final Logger logger = LoggerFactory.getLogger(MapboxClient.class);

	public MapboxClient(WebClient mapboxClient) {
		this.mapboxClient = mapboxClient;
	}

	/**
	 * Calls the mapbox api and returns the latitude and longitude of the passed address.
	 *
	 * @param address - The address from which the latitude and longitude values are to be determined
	 * @return The passed Address with the updated latitude and longitude values.
	 */
	public boolean getGeodata(Address address) {
		boolean result = false;
		String response =
				mapboxClient.get().uri(Constant.SLASH + address.getQueryString() + Constant.TOKENEXT + Constant.MAPBOXTOKEN + Constant.LIMIT)
						.accept(MediaType.APPLICATION_JSON)
						.retrieve()
						.bodyToMono(String.class)
						.block(REQUEST_TIMEOUT);
		try {
			final JSONArray coordinates = JsonPath.read(response, "$.features[0].geometry.coordinates");
			double longitude = (double) coordinates.get(0);
			double latitude = (double) coordinates.get(1);
			address.setCoordinates(new double[]{longitude, latitude});
			result = true;
		} catch(Exception ex) {
			logger.error("Error while reading latitude and longitude from mapbox json", ex);
		}
		return result;
	}
}

