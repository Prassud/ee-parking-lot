package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateCommandTest {
	private CreateCommand createCommand;

	@Mock
	private ParkingLotService mockedPS;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.createCommand = new CreateCommand(MessageConstant.CREATE_COMMAND, mockedPS);
	}

	@Test
	public void shouldUseParkingLotServiceToCreateParkinglotOfGivenSize() {
		String[] inputParams = new String[] {"create_parking_lot", "10"};
		Result result = createCommand.execute(inputParams);

		assertEquals("Created a parking lot with 10 slots", result.getMessage());
		verify(mockedPS).createParkingLot(10);
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenSizeIsInvalidNumber() {
		String[] inputParams = new String[] {"create_parking_lot", "abc"};
		try {
			createCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Number abc", exception.getMessage());
		}
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenSizeIsZero() {
		String[] inputParams = new String[] {"create_parking_lot", "0"};
		try {
			createCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Number 0", exception.getMessage());
		}
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenSizeIsLessThanOrEqualToNegative() {
		String[] inputParams = new String[] {"create_parking_lot", "-1"};
		try {
			createCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Number -1", exception.getMessage());
		}
	}

	@Test
	public void shouldThrowParkingLotExceptionWHenInputArgsLenghtIsNot2() {
		String[] inputParams = new String[] {"create_parking_lot", "-1", "dsdff"};
		try {
			createCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid input param size", exception.getMessage());
		}
	}
}