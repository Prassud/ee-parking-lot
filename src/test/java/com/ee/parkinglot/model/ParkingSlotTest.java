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
		assertTrue(parkingSlot.isFree());
	}

	@Test
	public void shouldSetParkingLotStateAsAllocated() {
		int lotNumber = 10;
		Car allocatedCar = new Car("abc", Car.Color.White);
		ParkingSlot parkingSlot = new ParkingSlot(lotNumber, ParkingSlot.State.FREE);

		parkingSlot.allocatedTo(allocatedCar);
		assertEquals(lotNumber, parkingSlot.getSlotNumber());
		assertEquals(ParkingSlot.State.ALLOCATED, parkingSlot.getState());
		assertEquals(allocatedCar.getRegistrationNumber(), parkingSlot.getParkedCarRegistrationNumber());
		assertEquals(allocatedCar.getColor(), parkingSlot.getParkedCarColor());
		assertTrue(parkingSlot.isAllocated());
	}
}