package com.ee.parkinglot.allocation.strategy;

import com.ee.parkinglot.model.ParkingSlot;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SequenceParkingSlotAllocationStrategyTest {

	private SequenceParkingLotAllocationStrategy sequenceTicketingStrategy;

	@Before
	public void setUp() throws Exception {
		sequenceTicketingStrategy = new SequenceParkingLotAllocationStrategy();
	}

	@Test
	public void shouldReturnTheAvailableSlotSequestially() {
		List<ParkingSlot> parkingSlots = new ArrayList<>();
		ParkingSlot expectedParkingSlot = new ParkingSlot(10, ParkingSlot.State.FREE);
		parkingSlots.add(expectedParkingSlot);
		parkingSlots.add(new ParkingSlot(10, ParkingSlot.State.FREE));
		parkingSlots.add(new ParkingSlot(9, ParkingSlot.State.ALLOCATED));
		parkingSlots.add(new ParkingSlot(8, ParkingSlot.State.ALLOCATED));
		parkingSlots.add(new ParkingSlot(7, ParkingSlot.State.ALLOCATED));
		ParkingSlot availableParkingSlot = sequenceTicketingStrategy.getNextAvailableParkingSlot(parkingSlots);

		assertEquals(expectedParkingSlot, availableParkingSlot);
	}


	@Test
	public void shouldReturnTheAvailableSlotAsNullWhenThereIsNoSlot() {
		List<ParkingSlot> parkingSlots = new ArrayList<>();
		parkingSlots.add(new ParkingSlot(10, ParkingSlot.State.ALLOCATED));
		parkingSlots.add(new ParkingSlot(10, ParkingSlot.State.ALLOCATED));
		parkingSlots.add(new ParkingSlot(9, ParkingSlot.State.ALLOCATED));
		parkingSlots.add(new ParkingSlot(8, ParkingSlot.State.ALLOCATED));
		parkingSlots.add(new ParkingSlot(7, ParkingSlot.State.ALLOCATED));
		ParkingSlot availableParkingSlot = sequenceTicketingStrategy.getNextAvailableParkingSlot(parkingSlots);

		assertNull(availableParkingSlot);
	}
}
