package com.ee.parkinglot.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingLotTest {

	@Test
	public void shouldGetParkingLotNumber() {
		int lotNumber = 10;
		ParkingLot parkingLot = new ParkingLot(lotNumber);

		assertEquals(lotNumber, parkingLot.getLotNumber());
	}
}