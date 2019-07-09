package com.ee.parkinglot.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TicketTest {

	@Test
	public void shouldGetTicketAttributes() {
		ParkingSlot parkingSlot = new ParkingSlot(10, ParkingSlot.State.FREE);
		Car car = new Car("registration_number", Car.Color.Red);
		Ticket ticket = new Ticket(parkingSlot, car);

		assertEquals("registration_number", ticket.getCarRegistrationNumber());
		assertEquals(Car.Color.Red, ticket.getCarColor());
		assertEquals(parkingSlot.getSlotNumber(), ticket.getParkingSlotNumber());
	}
}