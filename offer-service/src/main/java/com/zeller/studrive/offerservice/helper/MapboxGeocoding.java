package com.zeller.studrive.offerservice.helper;

import com.zeller.studrive.offerservice.model.Address;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class MapboxGeocoding {
	private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

	@Autowired
	private final WebClient apiClient;
	private final String mapboxToken = "sk.eyJ1IjoibWthbGFzaCIsImEiOiJja3AyYWVsNm0xMjltMndsZ3FqZXhnZG11In0.G0zqmJ50IGR31LpPx82LNg";
	Logger logger = LoggerFactory.getLogger(MapboxGeocoding.class);

	public MapboxGeocoding(WebClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Calls the mapbox api and returns the latitude and longitude of the passed address.
	 *
	 * @param address - The address from which the latitude and longitude values are to be determined
	 * @return The passed Address with the updated latitude and longitude values.
	 */
	public boolean getCoordinates(Address address) {
		boolean result = false;
		String response =
				apiClient.get().uri(Constants.SLASH + address.getQueryString() + Constants.TOKENEXT + Constants.MAPBOXTOKEN + Constants.LIMIT)
						.accept(MediaType.APPLICATION_JSON)
						.retrieve()
						.bodyToMono(String.class)
						.block(REQUEST_TIMEOUT);
		try {
			final JSONObject json = new JSONObject(response);
			// features, geometry, coordinates
			final JSONObject features = (JSONObject) json.getJSONArray("features").get(0);
			final JSONObject geometry = features.getJSONObject("geometry");
			final JSONArray coordinates = (JSONArray) geometry.get("coordinates");
			address.setLongitude(coordinates.getDouble(0));
			address.setLatitude(coordinates.getDouble(1));
			result = true;
		} catch(Exception ex) {
			logger.error("Error while reading latitude and longitude from mapbox json", ex);
		}
		return result;
	}
}

