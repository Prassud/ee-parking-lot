package com.ee.parkinglot.model.ticketing.strategy;

import com.ee.parkinglot.model.ParkingLot;

import java.util.List;
import java.util.stream.Collectors;

public class SequenceTicketingStrategy {
	public ParkingLot getNextAvailableParkingSlot(List<ParkingLot> parkingLots) {
		List<ParkingLot> parkingLotList = parkingLots.stream().filter(ParkingLot::isFree).collect(Collectors.toList());
		return parkingLotList.isEmpty() ? null : parkingLotList.get(0);
	}
}
