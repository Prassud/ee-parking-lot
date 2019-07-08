package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateCommandTest {
	private CreateCommand createCommand;

	@Mock
	private ParkingLotService parkingLotService;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.createCommand = new CreateCommand(MessageConstant.CREATE_COMMAND, parkingLotService);
	}

	@Test
	public void shouldUseParkingLotServiceToCreateParkinglotOfGivenSize() {
		String[] inputParams = new String[] {"create_parking_lot", "10"};
		Result result = createCommand.execute(inputParams);

		assertEquals("Created a parking lot with 6 slots", result.getMessage());
		verify(parkingLotService).createParkingLot(10);
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenSizeIsInvalidNumber() {
		String[] inputParams = new String[] {"create_parking_lot", "abc"};
		try {
			createCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Parking Lot size", exception.getMessage());
		}

		verify(parkingLotService, never()).createParkingLot(any());
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenSizeIsZero() {
		String[] inputParams = new String[] {"create_parking_lot", "0"};
		try {
			createCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Parking Lot size", exception.getMessage());
		}

		verify(parkingLotService, never()).createParkingLot(any());
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenSizeIsLessThanOrEqualToNegative() {
		String[] inputParams = new String[] {"create_parking_lot", "-1"};
		try {
			createCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Parking Lot size", exception.getMessage());
		}

		verify(parkingLotService, never()).createParkingLot(any());
	}

	@Test
	public void shouldThrowParkingLotExceptionWHenInputArgsLenghtIsNot2() {
		String[] inputParams = new String[] {"create_parking_lot", "-1", "dsdff"};
		try {
			createCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid input param size", exception.getMessage());
		}

		verify(parkingLotService, never()).createParkingLot(any());
	}
}