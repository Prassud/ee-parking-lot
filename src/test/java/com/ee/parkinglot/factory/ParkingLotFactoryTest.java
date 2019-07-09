package com.ee.parkinglot.factory;

import com.ee.parkinglot.allocation.strategy.SequenceParkingLotAllocationStrategy;
import com.ee.parkinglot.manager.ParkingSlotManager;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.ticketing.TicketManager;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;

import static org.powermock.api.mockito.PowerMockito.mock;

public class ParkingLotFactoryTest {

	@Test
	public void shoouldCreateParkingLotService() throws Exception {
		SequenceParkingLotAllocationStrategy sequenceParkingLotAllocationStrategy
				= mock(SequenceParkingLotAllocationStrategy.class);
		PowerMockito.whenNew(SequenceParkingLotAllocationStrategy.class)
				.withNoArguments().thenReturn(sequenceParkingLotAllocationStrategy);

		TicketManager ticketManager = mock(TicketManager.class);
		PowerMockito.whenNew(TicketManager.class)
				.withNoArguments().thenReturn(ticketManager);

		ArrayList mockList = mock(ArrayList.class);
		PowerMockito.whenNew(ArrayList.class)
				.withNoArguments().thenReturn(mockList);

		ParkingSlotManager slotManger = mock(ParkingSlotManager.class);
		PowerMockito.whenNew(ParkingSlotManager.class)
				.withArguments(sequenceParkingLotAllocationStrategy, ticketManager, mockList).thenReturn(slotManger);

		ParkingLotFactory.createParkingSlotService();

		PowerMockito.verifyNew(ParkingLotService.class).withArguments(slotManger);
	}
}