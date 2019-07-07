package com.ee.parkinglot.model;

import java.util.Objects;

public class ParkingSlot {

	public int getSlotNumber() {
		return slotNumber;
	}

	private int slotNumber;

	public State getState() {
		return state;
	}

	private State state;

	public ParkingSlot(int slotNumber, State state) {
		this.slotNumber = slotNumber;
		this.state = state;
	}

	public boolean isFree() {
		return this.state == State.FREE;
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
