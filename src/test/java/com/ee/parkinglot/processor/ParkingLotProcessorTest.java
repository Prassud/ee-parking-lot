package com.ee.parkinglot.processor;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import com.ee.parkinglot.model.ParkingLot;
import com.ee.parkinglot.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class ParkingLotProcessorTest {

	@Mock
	private ParkingLotAllocationStrategy parkingLotAllocationStrategy;

	private ParkingLotProcessor parkingLotProcessor;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		parkingLotProcessor = new ParkingLotProcessor(parkingLotAllocationStrategy, 10);
	}

	@Test
	public void shouldCallParkingLotAllocationStrategyToGetNextAvailableTicket() {
		parkingLotProcessor.park();

		ArgumentCaptor<List> parkingSlotsCaptor = ArgumentCaptor.forClass(List.class);
		verify(parkingLotAllocationStrategy).getNextAvailableParkingSlot(parkingSlotsCaptor.capture());
		List<ParkingLot> parkingSlots = parkingSlotsCaptor.getValue();
		List<ParkingLot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		assertEquals(expectedParkingSlots, parkingSlots);
	}
}
