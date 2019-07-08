package com.ee.parkinglot.service;

import com.ee.parkinglot.manager.ParkingSlotManager;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.Ticket;

public class ParkingSlotService {
	private ParkingSlotManager parkingSlotManager;

	public ParkingSlotService(ParkingSlotManager parkingSlotManager) {
		this.parkingSlotManager = parkingSlotManager;
	}

	public Ticket park(Car car) {
		return parkingSlotManager.park(car);
	}
}
