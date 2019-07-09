package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LeaveCommandTest {
	@Mock
	private ParkingLotService parkingLotService;

	private LeaveCommand leaveCommand;

	@Before
	public void setUp() {
		initMocks(this);
		this.leaveCommand = new LeaveCommand(MessageConstant.LEAVE_COMMAND_NAME, parkingLotService);
	}

	@Test
	public void shouldUseParkingLotServiceToLeaveFromParkingLot() {
		String[] inputParams = new String[] {"leave", "4"};
		Result result = leaveCommand.execute(inputParams);

		assertEquals("Slot Number 4 is free", result.getMessage());
		verify(parkingLotService).unPark(4);
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenSizeIsInvalidNumber() {
		String[] inputParams = new String[] {"leave", "aaaa"};
		try {
			leaveCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Number aaaa", exception.getMessage());
		}
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenSizeIsZero() {
		String[] inputParams = new String[] {"create_parking_lot", "0"};
		try {
			leaveCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Number 0", exception.getMessage());
		}
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenSizeIsLessThanOrEqualToNegative() {
		String[] inputParams = new String[] {"create_parking_lot", "-1"};
		try {
			leaveCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Number -1", exception.getMessage());
		}
	}

	@Test
	public void shouldThrowParkingLotExceptionWHenInputArgsLenghtIsNot2() {
		String[] inputParams = new String[] {"leave", "4", "dsdff"};
		try {
			leaveCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid input param size", exception.getMessage());
		}
	}
}