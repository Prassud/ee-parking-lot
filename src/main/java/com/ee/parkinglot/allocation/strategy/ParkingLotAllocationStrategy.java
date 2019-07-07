package com.ee.parkinglot.allocation.strategy;

import com.ee.parkinglot.model.ParkingLot;

import java.util.List;

public interface ParkingLotAllocationStrategy {
	ParkingLot getNextAvailableParkingSlot(List<ParkingLot> capture);
}
