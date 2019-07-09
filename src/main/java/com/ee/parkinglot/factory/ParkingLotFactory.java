package com.ee.parkinglot.factory;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import com.ee.parkinglot.allocation.strategy.SequenceParkingLotAllocationStrategy;
import com.ee.parkinglot.manager.ParkingSlotManager;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.ticketing.TicketManager;

import java.util.ArrayList;

public class ParkingLotFactory {

	public static ParkingLotService createParkingSlotService() {
		ParkingLotAllocationStrategy parkingLotAllocationStrategy = new SequenceParkingLotAllocationStrategy();
		TicketManager ticketManager = new TicketManager();
		ParkingSlotManager parkingSlotManager = new ParkingSlotManager(parkingLotAllocationStrategy, ticketManager, new ArrayList<>());
		return new ParkingLotService(parkingSlotManager);
	}
}
