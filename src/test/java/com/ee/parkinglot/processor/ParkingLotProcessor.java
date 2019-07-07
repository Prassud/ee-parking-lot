package com.ee.parkinglot.processor;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import com.ee.parkinglot.model.ParkingLot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotProcessor {

	private final List<ParkingLot> parkingLots;

	private ParkingLotAllocationStrategy parkingLotAllocationStrategy;

	public ParkingLotProcessor(ParkingLotAllocationStrategy parkingLotAllocationStrategy, int range) {
		this.parkingLotAllocationStrategy = parkingLotAllocationStrategy;
		this.parkingLots = IntStream.range(1, range).mapToObj(
				eachIndex -> new ParkingLot(eachIndex, ParkingLot.State.FREE)).collect(Collectors.toList());
	}

	public void park() {
		parkingLotAllocationStrategy.getNextAvailableParkingSlot(parkingLots);
	}
}
