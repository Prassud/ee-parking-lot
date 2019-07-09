package com.ee.parkinglot.model;

import java.util.Objects;
import java.util.stream.IntStream;

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

	public Car.Color getParkedCarColor() {
		return this.parkedCar.getColor();
	}

	public Car free() {
		this.state = State.FREE;
		Car parkedCar = this.parkedCar;
		this.setParkedCar(null);
		return parkedCar;
	}

	public String getStatus() {
		String slotNumber = appendSpaces(String.valueOf(this.slotNumber), 10);
		String registrationNumber = appendSpaces(this.getParkedCarRegistrationNumber(), 15);
		String color = appendSpaces(getParkedCarColor().name(), 7);

		return new StringBuffer().append(slotNumber).append(registrationNumber).append(color).toString();
	}

	private String appendSpaces(String string, int stringLength) {
		int currLen = string.length();
		int noOfSpacesToAppend = stringLength - currLen;
		StringBuffer buffer = new StringBuffer(10);
		buffer.append(string);
		IntStream.range(0, noOfSpacesToAppend).forEach((eachIndex) -> buffer.append(" "));
		return buffer.toString();
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
