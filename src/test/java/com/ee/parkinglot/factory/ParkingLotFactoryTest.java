package com.ee.parkinglot.factory;

import com.ee.parkinglot.ParkingLotApplication;
import com.ee.parkinglot.allocation.strategy.SequenceParkingLotAllocationStrategy;
import com.ee.parkinglot.command.LeaveCommand;
import com.ee.parkinglot.command.ParkCommand;
import com.ee.parkinglot.command.SearchSNByColorCommand;
import com.ee.parkinglot.command.StatusCommand;
import com.ee.parkinglot.io.ConsoleInputProcessor;
import com.ee.parkinglot.io.InputFileProcessor;
import com.ee.parkinglot.manager.ParkingSlotManager;
import com.ee.parkinglot.search.AbstractSearchParkingSlot;
import com.ee.parkinglot.search.SearchSlotByColor;
import com.ee.parkinglot.search.SearchSlotByRegistrationNumber;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.ticketing.TicketManager;
import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {ParkingLotFactory.class, SequenceParkingLotAllocationStrategy.class, ArrayList.class, ParkingLotService.class, ParkingSlotManager.class, TicketManager.class})
public class ParkingLotFactoryTest {

	@Test
	public void shouldCreateParkingLotService() throws Exception {
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

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<AbstractSearchParkingSlot> searchSlotByRegistrationNumberCaptor = ArgumentCaptor.forClass(AbstractSearchParkingSlot.class);
		verify(slotManger, times(2)).register(captor.capture(), searchSlotByRegistrationNumberCaptor.capture());
		assertEquals(MessageConstant.GET_SLOT_BY_COLOR, captor.getAllValues().get(0));
		assertEquals(MessageConstant.GET_SLOT_BY_RN, captor.getAllValues().get(1));
		assertEquals(searchSlotByRegistrationNumberCaptor.getAllValues().get(0).getClass(), SearchSlotByColor.class);
		assertEquals(searchSlotByRegistrationNumberCaptor.getAllValues().get(1).getClass(), SearchSlotByRegistrationNumber.class);
	}
}