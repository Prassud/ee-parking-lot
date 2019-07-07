package com.ee.parkinglot.model;

import java.util.Objects;

public class ParkingLot {

	public int getLotNumber() {
		return lotNumber;
	}

	private int lotNumber;

	public State getState() {
		return state;
	}

	private State state;

	public ParkingLot(int lotNumber, State state) {
		this.lotNumber = lotNumber;
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
		ParkingLot that = (ParkingLot) o;
		return getLotNumber() == that.getLotNumber();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getLotNumber());
	}

	public void allocated() {
		state = State.ALLOCATED;
	}

	public enum State {ALLOCATED, FREE}
}
