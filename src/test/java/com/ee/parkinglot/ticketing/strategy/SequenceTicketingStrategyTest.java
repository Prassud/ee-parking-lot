package com.ee.parkinglot.ticketing.strategy;

import com.ee.parkinglot.model.ParkingLot;
import com.ee.parkinglot.model.ticketing.strategy.SequenceTicketingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SequenceTicketingStrategyTest {

	private SequenceTicketingStrategy sequenceTicketingStrategy;

	@Before
	public void setUp() throws Exception {
		sequenceTicketingStrategy = new SequenceTicketingStrategy();
	}

	@Test
	public void shouldReturnTheAvailableSlotSequestially() {
		List<ParkingLot> parkingLots = new ArrayList<>();
		ParkingLot expectedParkingSlot = new ParkingLot(10, ParkingLot.State.FREE);
		parkingLots.add(expectedParkingSlot);
		parkingLots.add(new ParkingLot(10, ParkingLot.State.FREE));
		parkingLots.add(new ParkingLot(9, ParkingLot.State.ALLOCATED));
		parkingLots.add(new ParkingLot(8, ParkingLot.State.ALLOCATED));
		parkingLots.add(new ParkingLot(7, ParkingLot.State.ALLOCATED));
		ParkingLot availableParkingSlot = sequenceTicketingStrategy.getNextAvailableParkingSlot(parkingLots);

		assertEquals(expectedParkingSlot, availableParkingSlot);
	}


	@Test
	public void shouldReturnTheAvailableSlotAsNullWhenThereIsNoSlot() {
		List<ParkingLot> parkingLots = new ArrayList<>();
		parkingLots.add(new ParkingLot(10, ParkingLot.State.ALLOCATED));
		parkingLots.add(new ParkingLot(10, ParkingLot.State.ALLOCATED));
		parkingLots.add(new ParkingLot(9, ParkingLot.State.ALLOCATED));
		parkingLots.add(new ParkingLot(8, ParkingLot.State.ALLOCATED));
		parkingLots.add(new ParkingLot(7, ParkingLot.State.ALLOCATED));
		ParkingLot availableParkingSlot = sequenceTicketingStrategy.getNextAvailableParkingSlot(parkingLots);

		assertNull(availableParkingSlot);
	}
}
