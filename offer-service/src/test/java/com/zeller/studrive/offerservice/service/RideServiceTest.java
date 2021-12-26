package com.zeller.studrive.offerservice.service;

import com.zeller.studrive.offerservice.basic.Constant;
import com.zeller.studrive.offerservice.basic.MapboxClient;
import com.zeller.studrive.offerservice.eventhandling.sender.TaskSender;
import com.zeller.studrive.offerservice.model.Address;
import com.zeller.studrive.offerservice.model.Car;
import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.model.RideStatus;
import com.zeller.studrive.offerservice.repository.RideRepository;
import jdk.vm.ci.meta.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class RideServiceTest {

	private RideService testService;
	@Mock
	private RideRepository rideRepository;
	@Mock
	private TaskSender taskSender;
	@Mock
	private MapboxClient mapboxClient;
	private Ride ride;

	@BeforeEach
	void setUp() {
		testService = new RideService(rideRepository, taskSender, mapboxClient);
		Long driverId = 100L;
		Address start = new Address("Reutlingen", "72762", "Alteburgstraße", 150);
		Address destination = new Address("Berlin", "10117", "Unter den Linden", 77);
		LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 12, 0);
		LocalDateTime endDate = LocalDateTime.of(2021, 1, 1, 22, 0);
		Car car = new Car("BMW", "TÜ-TE-1337", 3);
		double pricePerSeat = 30;
		ride = new Ride(driverId, start, destination, startDate, endDate, car, pricePerSeat);
	}

	@Test
	void canSave() {
		testService.save(ride);
		verify(rideRepository).save(ride);
	}

	@Test
	void canOfferRide() {
		// Set up mocks
		Mockito.when(mapboxClient.getGeodata(ride.getStart())).thenReturn(true);
		ride.getStart().setCoordinates(new double[]{9.187890, 48.482890});
		Mockito.when(mapboxClient.getGeodata(ride.getDestination())).thenReturn(true);
		ride.getDestination().setCoordinates(new double[]{13.380010, 52.516110});
		// Mock save
		ride.setId("100");
		Mockito.when(rideRepository.save(ride)).thenReturn(ride);
		// Test
		Optional<Ride> result = testService.offerRide(ride);
		// Validation
		verify(mapboxClient).getGeodata(ride.getStart());
		verify(mapboxClient).getGeodata(ride.getDestination());
		verify(rideRepository).save(ride);
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void cantOfferRide() {
		Mockito.when(mapboxClient.getGeodata(ride.getStart())).thenReturn(true);
		ride.getStart().setCoordinates(new double[]{9.187890, 48.482890});
		Mockito.when(mapboxClient.getGeodata(ride.getDestination())).thenReturn(false);
		Optional<Ride> result = testService.offerRide(ride);
		verify(mapboxClient).getGeodata(ride.getStart());
		verify(mapboxClient).getGeodata(ride.getDestination());
		assertThat(result.isPresent()).isFalse();
	}

	@Test
	void canFindById() {
		String id = "100";
		ride.setId(id);
		Mockito.when(rideRepository.findRidesById(id)).thenReturn(Optional.of(ride));
		Optional<Ride> result = testService.findById(id);
		verify(rideRepository).findRidesById(id);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(ride);
	}

	@Test
	void canCancelRide() {
		String id = "100";
		ride.setId(id);
		ride.setRideStatus(RideStatus.AVAILABLE);
		Mockito.when(rideRepository.findRidesById(id)).thenReturn(Optional.of(ride));
		Optional<Ride> result = testService.cancelRide(id);
		verify(rideRepository).findRidesById(id);
		verify(taskSender).cancelRide(id);
		verify(rideRepository).save(ride);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(ride);
		assertThat(result.get().getRideStatus()).isEqualTo(RideStatus.CANCELED);
	}

	@Test
	void cantCancelRide() {
		String id = "100";
		ride.setId(id);
		ride.setRideStatus(RideStatus.CLOSED);
		Mockito.when(rideRepository.findRidesById(id)).thenReturn(Optional.of(ride));
		Optional<Ride> result = testService.cancelRide(id);
		verify(rideRepository).findRidesById(id);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getRideStatus()).isEqualTo(RideStatus.CLOSED);
		verifyNoInteractions(taskSender);
	}

	@Test
	void canCloseRide() {
		String id = "100";
		ride.setId(id);
		ride.setRideStatus(RideStatus.AVAILABLE);
		Mockito.when(rideRepository.findRidesById(id)).thenReturn(Optional.of(ride));
		Optional<Ride> result = testService.closeRide(id);
		verify(rideRepository).findRidesById(id);
		verify(taskSender).closeRide(id);
		verify(rideRepository).save(ride);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(ride);
		assertThat(result.get().getRideStatus()).isEqualTo(RideStatus.CLOSED);
	}

	@Test
	void canGetRidesByDriver() {
		Mockito.when(rideRepository.findRidesByDriverId(100L)).thenReturn(Collections.singletonList(ride));
		List<Ride> rides = testService.getRidesByDriver(100L);
		verify(rideRepository).findRidesByDriverId(100L);
		assertThat(rides.get(0)).isEqualTo(ride);
	}

	@Test
	void canVerifyRideSeats() {
		String id = "100";
		ride.setId(id);
		ride.setRideStatus(RideStatus.AVAILABLE);
		Mockito.when(rideRepository.findRidesById(id)).thenReturn(Optional.of(ride));
		boolean result = testService.verifyRideSeats(id);
		verify(rideRepository).findRidesById(id);
		assertTrue(result);
	}

	@Test
	void cantVerifyRideSeats() {
		String id = "100";
		ride.setId(id);
		ride.setRideStatus(RideStatus.CLOSED);
		Mockito.when(rideRepository.findRidesById(id)).thenReturn(Optional.of(ride));
		boolean result = testService.verifyRideSeats(id);
		verify(rideRepository).findRidesById(id);
		assertFalse(result);
	}

	@Test
	void canGetAvailableRide() {
		Address start = new Address("Reutlingen", "72762", "Alteburgstraße", 150);
		start.setCoordinates(new double[]{9.187890, 48.482890});
		Address destination = new Address("Berlin", "10117", "Unter den Linden", 77);
		destination.setCoordinates(new double[]{13.380010, 52.516110});
		Mockito.when(mapboxClient.getGeodata(start)).thenReturn(true);
		Mockito.when(mapboxClient.getGeodata(destination)).thenReturn(true);
		LocalDate ld = LocalDate.of(2020, 12, 30);
		LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.of(0, 0, 0));
		List<GeoResult<Ride>> result = Arrays.asList(new GeoResult(ride, new Distance(12)));
		GeoResults<Ride> results = new GeoResults<>(result);
		Mockito.when(rideRepository.findAvailableRides(Constant.STARTINDEX, ldt, new Point(9.187890, 48.482890))).thenReturn(results);
		Mockito.when(rideRepository.findAvailableRides(Constant.DESTINATIONINDEX, ldt, new Point(13.380010, 52.516110))).thenReturn(results);
		List<Ride> available = testService.getAvailableRide(ld, start, destination);
		assertThat(available.size()).isEqualTo(1);
	}
}