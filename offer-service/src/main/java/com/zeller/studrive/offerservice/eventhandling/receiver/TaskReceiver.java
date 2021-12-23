package com.zeller.studrive.offerservice.eventhandling.receiver;

import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.model.RideStatus;
import com.zeller.studrive.offerservice.service.RideService;
import com.zeller.studrive.offerservicemq.eventmodel.FreeRide;
import com.zeller.studrive.offerservicemq.eventmodel.OccupyRide;
import com.zeller.studrive.orderservicemq.basic.RabbitMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TaskReceiver {

	final Logger logger = LoggerFactory.getLogger(TaskReceiver.class);

	@Autowired
	private RideService rideService;

	public TaskReceiver() {
	}

	/**
	 * Subscribes to the RabbitMQ query through which a message is transmitted from the order-service if a seat is accepted.
	 *
	 * @param occupyRide - RabbitMQ message object that contains all the required information.
	 */
	@RabbitListener(queues = RabbitMQConstant.OCCUPY_RIDE_QUEUE)
	public void occupyRide(OccupyRide occupyRide) {
		Optional<Ride> rideTemp = rideService.findById(occupyRide.getRideId());
		if(rideTemp.isPresent()) {
			Ride ride = rideTemp.get();
			if(ride.getCar().getSeats() == occupyRide.getCurrentSeats()) {
				ride.setRideStatus(RideStatus.OCCUPIED);
				rideService.save(ride);
			}
		} else {
			logger.info("No ride with the id: " + occupyRide.getRideId() + " was found.");
		}
	}

	/**
	 * Subscribes to the RabbitMQ query through which a message is transmitted from the order-service if a seat is canceled.
	 *
	 * @param freeRide- RabbitMQ message object that contains all the required information.
	 */
	@RabbitListener(queues = RabbitMQConstant.FREE_RIDE_QUEUE)
	public void freeRide(FreeRide freeRide) {
		Optional<Ride> rideTemp = rideService.findById(freeRide.getRideId());
		if(rideTemp.isPresent()) {
			Ride ride = rideTemp.get();
			if(ride.getRideStatus() == RideStatus.OCCUPIED) {
				ride.setRideStatus(RideStatus.AVAILABLE);
				rideService.save(ride);
			}
		} else {
			logger.info("No ride with the id: " + freeRide.getRideId() + " was found.");
		}
	}
}
