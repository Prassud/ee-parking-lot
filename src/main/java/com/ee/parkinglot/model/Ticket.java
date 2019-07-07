package com.ee.parkinglot.model;

import java.util.Objects;

public class Ticket {
	private int parkingSlotNumbe;

	private String carRegistrationNumber;

	private Car.Color carColor;

	public Ticket(ParkingSlot parkingSlot, Car car) {
		this.parkingSlotNumbe = parkingSlot.getLotNumber();
		this.carColor = car.getColor();
	}

	public int getParkingSlotNumber() {
		return parkingSlotNumbe;
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
		return parkingSlotNumbe == ticket.parkingSlotNumbe &&
				Objects.equals(getCarRegistrationNumber(), ticket.getCarRegistrationNumber()) &&
				getCarColor() == ticket.getCarColor();
	}

	@Override
	public int hashCode() {
		return Objects.hash(parkingSlotNumbe, getCarRegistrationNumber(), getCarColor());
	}
}
