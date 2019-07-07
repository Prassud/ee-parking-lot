package com.ee.parkinglot.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TicketTest {

	@Test
	public void shouldGetTicketAttributes() {
		ParkingLot parkingLot = new ParkingLot(10, ParkingLot.State.FREE);
		Car car = new Car("registration_number", Car.Color.RED);
		Ticket ticket = new Ticket(parkingLot, car);

		assertEquals("registration_number", ticket.getCar().getRegistrationNumber());
		assertEquals(Car.Color.RED, ticket.getCar().getColor());
		assertEquals(parkingLot, ticket.getParkingLot());
	}
}