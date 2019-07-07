package com.ee.parkinglot.ticketing;

import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.model.Ticket;

public class TicketManager {
	public Ticket issueTicket(ParkingSlot parkingSlot, Car car) {
		Ticket ticket = new Ticket(parkingSlot, car);
		return ticket;
	}
}
