package com.ee.parkinglot.model;

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

	public enum State {ALLOCATED, FREE}
}
