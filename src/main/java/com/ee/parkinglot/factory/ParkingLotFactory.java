package com.ee.parkinglot.factory;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import com.ee.parkinglot.allocation.strategy.SequenceParkingLotAllocationStrategy;
import com.ee.parkinglot.manager.ParkingSlotManager;
import com.ee.parkinglot.search.SearchSlotByColor;
import com.ee.parkinglot.search.SearchSlotByRegistrationNumber;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.ticketing.TicketManager;
import com.ee.parkinglot.utils.MessageConstant;

import java.util.ArrayList;

public class ParkingLotFactory {

	public static ParkingLotService createParkingSlotService() {
		ParkingLotAllocationStrategy parkingLotAllocationStrategy = new SequenceParkingLotAllocationStrategy();
		TicketManager ticketManager = new TicketManager();
		ParkingSlotManager parkingSlotManager = new ParkingSlotManager(parkingLotAllocationStrategy, ticketManager, new ArrayList<>());
		parkingSlotManager.register(MessageConstant.GET_SLOT_BY_COLOR,new SearchSlotByColor());
		parkingSlotManager.register(MessageConstant.GET_SLOT_BY_RN,new SearchSlotByRegistrationNumber());
		return new ParkingLotService(parkingSlotManager);
	}
}
