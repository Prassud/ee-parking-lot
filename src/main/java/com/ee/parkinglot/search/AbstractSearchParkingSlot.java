package com.ee.parkinglot.search;

import com.ee.parkinglot.model.ParkingSlot;

import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractSearchParkingSlot {
	public abstract List<ParkingSlot> search(Object searchParam, List<ParkingSlot> parkingSlots);

	protected Stream<ParkingSlot> getAllocatedParkingSlots(List<ParkingSlot> parkingSlots) {
		return parkingSlots.stream().filter(ParkingSlot::isAllocated);
	}
}
