package com.ee.parkinglot.service;

import com.ee.parkinglot.bean.Status;
import com.ee.parkinglot.manager.ParkingSlotManager;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.model.Ticket;

import java.util.List;

public class ParkingLotService {
	private ParkingSlotManager parkingSlotManager;

	public ParkingLotService(ParkingSlotManager parkingSlotManager) {
		this.parkingSlotManager = parkingSlotManager;
	}

	public Ticket park(Car car) {
		return parkingSlotManager.park(car);
	}

	public Car unPark(int slotNumber) {
		return parkingSlotManager.unPark(slotNumber);
	}

	public List<ParkingSlot> search(String searchCommandName, Object searchParam) {
		return parkingSlotManager.searchParkingSlot(searchCommandName, searchParam);
	}

	public void createParkingLot(int size) {
		parkingSlotManager.createParkingLot(size);
	}

	public List<Status> status() {
		return parkingSlotManager.status();
	}
}
