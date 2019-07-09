package com.ee.parkinglot.allocation.strategy;

import com.ee.parkinglot.model.ParkingSlot;

import java.util.List;
import java.util.stream.Collectors;

public class SequenceParkingLotAllocationStrategy implements ParkingLotAllocationStrategy {
	public ParkingSlot getNextAvailableParkingSlot(List<ParkingSlot> parkingSlots) {
		List<ParkingSlot> parkingSlotList = parkingSlots.stream().filter(ParkingSlot::isFree).collect(Collectors.toList());
		return parkingSlotList.isEmpty() ? null : parkingSlotList.get(0);
	}
}
