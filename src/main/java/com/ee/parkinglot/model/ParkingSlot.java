package com.ee.parkinglot.model;

import java.util.Objects;

public class ParkingSlot {

	private int slotNumber;

	private State state;

	public ParkingSlot(int slotNumber, State state) {
		this.slotNumber = slotNumber;
		this.state = state;
	}

	public boolean isFree() {
		return this.state == State.FREE;
	}

	public State getState() {
		return state;
	}

	public int getSlotNumber() {
		return slotNumber;
	}


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

	public void allocated() {
		state = State.ALLOCATED;
	}

	public enum State {ALLOCATED, FREE}
}
