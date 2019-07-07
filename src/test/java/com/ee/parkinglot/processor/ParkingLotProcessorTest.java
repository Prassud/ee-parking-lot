package com.ee.parkinglot.processor;

import com.ee.parkinglot.ticketing.strategy.TicketingStrategy;
import org.junit.Before;

public class ParkingLotProcessorTest {

	private TicketingStrategy ticketingStrategy;

	private ParkingLotProcessor parkingLotProcessor;

	@Before
	public void setUp() throws Exception {
		parkingLotProcessor = new ParkingLotProcessor();
	}
}
