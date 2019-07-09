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

import static com.ee.parkinglot.utils.MessageConstant.GET_SLOT_BY_COLOR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
		String[] inputParams = new String[] {"slot_number_for_registration_number", "White"};
		List<ParkingSlot> parkingSlots = new ArrayList<>();
		ParkingSlot slot = new ParkingSlot(1, ParkingSlot.State.FREE);
		slot.allocatedTo(new Car("jjjj", Car.Color.White));
		parkingSlots.add(slot);
		parkingSlots.add(slot);
		when(parkingLotService.search(any(), any())).thenReturn(parkingSlots);

		Result result = searchRNByColorCommand.execute(inputParams);

		assertEquals("jjjj,jjjj", result.getMessage());
		verify(parkingLotService).search(GET_SLOT_BY_COLOR, Car.Color.White);
	}

	@Test
	public void shouldThrowParkingLotExceptionWHenInputArgsLenghtIsNot3() {
		String[] inputParams = new String[] {"registration_numbers_for_cars_with_colour", "KA-01-P-333", "White", "sdds"};
		try {
			searchRNByColorCommand
					.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid input param size", exception.getMessage());
		}
	}
}