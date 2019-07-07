package com.ee.parkinglot.ticketing;

import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.model.Ticket;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicketManagerTest {
	private TicketManager ticketManager;

	@Before
	public void setUp() {
		ticketManager = new TicketManager();
	}

	@Test
	public void shouldIssueTicket() {
		ParkingSlot parkingSlot = new ParkingSlot(1, ParkingSlot.State.FREE);
		Car car = new Car("abc", Car.Color.RED);
		Ticket expectedTicket = new Ticket(parkingSlot, car);
		Ticket ticket = ticketManager.issueTicket(parkingSlot, car);

		assertEquals(expectedTicket, ticket);
	}

	@Test
	public void shouldGetTheCarsMatchingWithColorBasedOnTicket() {
		ParkingSlot parkingSlot = new ParkingSlot(1, ParkingSlot.State.FREE);
		Car car = new Car("abc", Car.Color.RED);
		Ticket expectedTicket = new Ticket(parkingSlot, car);
		Ticket ticket = ticketManager.issueTicket(parkingSlot, car);

		assertEquals(expectedTicket, ticket);
	}
}
