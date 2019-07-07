package com.ee.parkinglot.model;

public class Ticket {
	private ParkingLot parkingLot;
	private Car car;

	public Ticket(ParkingLot parkingLot, Car car) {
		this.parkingLot = parkingLot;
		this.car = car;
	}

	public Car getCar() {
		return car;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}
}
