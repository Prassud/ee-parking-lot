package com.ee.parkinglot.allocation.strategy;

import com.ee.parkinglot.model.ParkingSlot;

import java.util.List;

public interface ParkingLotAllocationStrategy {
	ParkingSlot getNextAvailableParkingSlot(List<ParkingSlot> capture);
}
