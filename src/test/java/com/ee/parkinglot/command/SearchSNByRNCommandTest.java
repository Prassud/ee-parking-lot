package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.service.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.ee.parkinglot.utils.MessageConstant.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SearchSNByRNCommandTest {

	@Mock
	private ParkingLotService parkingLotService;

	private SearchSNByRNCommand searchSNByRNCommand;

	@Before
	public void setUp() {
		initMocks(this);
		this.searchSNByRNCommand = new SearchSNByRNCommand(SEARCH_BY_REGISTER_NUMBER, parkingLotService);
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

		Result result = searchSNByRNCommand.execute(inputParams);

		assertEquals("1", result.getMessage());
		verify(parkingLotService).search(GET_SLOT_BY_RN, "");
	}

	@Test
	public void shouldHandleparkingLotExceptionAndReturnResultWithErrorMessage() {
		String[] inputParams = new String[] {"slot_number_for_registration_number", "MH-04-AY-1111"};
		doThrow(new ParkingLotException("blah blah")).when(parkingLotService).search(any(), any());

		Result result = searchSNByRNCommand.execute(inputParams);

		assertEquals("blah blah", result.getMessage());
		assertFalse(result.isSuccess());
	}

	@Test
	public void shouldThrowParkingLotExceptionWHenInputArgsLenghtIsNot3() {
		String[] inputParams = new String[] {"slot_number_for_registration_number", "KA-01-P-333", "White", "sdds"};
		try {
			searchSNByRNCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid input param size", exception.getMessage());
		}

		verify(parkingLotService, never()).unPark(any());
	}
}