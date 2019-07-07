package com.ee.parkinglot.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingSlotTest {

	@Test
	public void shouldGetParkingLotNumber() {
		int lotNumber = 10;
		ParkingSlot parkingSlot = new ParkingSlot(lotNumber, ParkingSlot.State.FREE);

		assertEquals(lotNumber, parkingSlot.getSlotNumber());
		assertEquals(ParkingSlot.State.FREE, parkingSlot.getState());
	}
}