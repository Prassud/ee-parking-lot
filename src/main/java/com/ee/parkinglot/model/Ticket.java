package com.ee.parkinglot.model;

import java.util.Objects;

public class Ticket {
	private int parkingSlotNumber;

	private String carRegistrationNumber;

	private Car.Color carColor;

	public Ticket(ParkingSlot parkingSlot, Car car) {
		this.parkingSlotNumber = parkingSlot.getSlotNumber();
		this.carColor = car.getColor();
	}

	public int getParkingSlotNumber() {
		return parkingSlotNumber;
	}

	public String getCarRegistrationNumber() {
		return carRegistrationNumber;
	}

	public Car.Color getCarColor() {
		return carColor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Ticket)) {
			return false;
		}
		Ticket ticket = (Ticket) o;
		return parkingSlotNumber == ticket.parkingSlotNumber &&
				Objects.equals(getCarRegistrationNumber(), ticket.getCarRegistrationNumber()) &&
				getCarColor() == ticket.getCarColor();
	}

	@Override
	public int hashCode() {
		return Objects.hash(parkingSlotNumber, getCarRegistrationNumber(), getCarColor());
	}
}
