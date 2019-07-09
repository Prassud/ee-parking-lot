package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.ee.parkinglot.utils.MessageConstant.SEARCH_BY_COLOR_FOR_SN;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SearchRNByColorCommandTest {
	@Mock
	private ParkingLotService parkingLotService;

	private SearchRNByColorCommand searchRNByColorCommand;

	@Before
	public void setUp() {
		initMocks(this);
		this.searchRNByColorCommand = new SearchRNByColorCommand(MessageConstant.SEARCH_BY_RN_FOR_SN, parkingLotService);
	}

	@Test
	public void shouldSearchParkingLotAndReturnRegistrationNumberNumbersInCommaSeparated() {
		String[] inputParams = new String[] {"slot_number_for_registration_number", "MH-04-AY-1111"};
		List<ParkingSlot> parkingSlots = new ArrayList<>();
		ParkingSlot slot = new ParkingSlot(1, ParkingSlot.State.FREE);
		slot.allocatedTo(new Car("jjjj", Car.Color.WHITE));
		parkingSlots.add(slot);
		parkingSlots.add(slot);
		when(parkingLotService.search(any(), any())).thenReturn(parkingSlots);

		Result result = searchRNByColorCommand.execute(inputParams);

		assertEquals("jjjj,jjjj", result.getMessage());
		verify(parkingLotService).search(SEARCH_BY_COLOR_FOR_SN, Car.Color.WHITE);
	}

	@Test
	public void shouldThrowParkingLotExceptionWHenInputArgsLenghtIsNot3() {
		String[] inputParams = new String[] {"slot_number_for_registration_number", "KA-01-P-333", "White", "sdds"};
		try {
			searchRNByColorCommand
					.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid input param size", exception.getMessage());
		}

		verify(parkingLotService, never()).unPark(any());
	}

	@Test
	public void shouldHandleparkingLotExceptionAndReturnResultWithErrorMessage() {
		String[] inputParams = new String[] {"slot_number_for_registration_number", "MH-04-AY-1111"};
		doThrow(new ParkingLotException("blah blah")).when(parkingLotService).search(any(), any());

		Result result = searchRNByColorCommand.execute(inputParams);

		assertEquals("blah blah", result.getMessage());
	}
}