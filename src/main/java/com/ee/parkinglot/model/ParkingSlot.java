package com.ee.parkinglot.model;

import java.util.Objects;

public class ParkingSlot {

	private final int slotNumber;

	private State state;

	private Car parkedCar;

	public ParkingSlot(int slotNumber, State state) {
		this.slotNumber = slotNumber;
		this.state = state;
	}

	public boolean isFree() {
		return this.state == State.FREE;
	}

	public boolean isAllocated() {
		return this.state == State.ALLOCATED;
	}

	public State getState() {
		return state;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public void allocatedTo(Car car) {
		this.setParkedCar(car);
		state = State.ALLOCATED;
	}

	private void setParkedCar(Car car) {
		this.parkedCar = car;
	}

	public String getParkedCarRegistrationNumber() {
		return this.parkedCar.getRegistrationNumber();
	}

	public enum State {ALLOCATED, FREE}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ParkingSlot that = (ParkingSlot) o;
		return getSlotNumber() == that.getSlotNumber();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSlotNumber());
	}
}
