package com.ee.parkinglot.processor;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class ParkingLotProcessorTest {

	@Mock
	private ParkingLotAllocationStrategy ticketingStrategy;

	private ParkingLotProcessor parkingLotProcessor;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		parkingLotProcessor = new ParkingLotProcessor();
	}

	@Test
	public void shouldCallParkingLotAllocationStrategyToGetNextAvailableTicket() {
		parkingLotProcessor.park();

		ArgumentCaptor<List> parkingSlotsCaptor = ArgumentCaptor.forClass(List.class);
		verify(ticketingStrategy).getNextAvailableParkingSlot(parkingSlotsCaptor.capture());
	}
}
