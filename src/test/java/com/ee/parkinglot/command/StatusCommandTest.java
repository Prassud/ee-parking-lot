package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.bean.Status;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.service.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.ee.parkinglot.utils.MessageConstant.STATUS_COMMAND_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class StatusCommandTest {

	@Mock
	private ParkingLotService parkingLotService;

	private StatusCommand statusCommand;

	@Before
	public void setUp() {
		initMocks(this);
		this.statusCommand = new StatusCommand(STATUS_COMMAND_NAME, parkingLotService);
	}

	@Test
	public void shouldReturnsStausInfoWithHeader() {
		String[] inputParams = new String[] {"status"};
		List<Status> statuses = new ArrayList<Status>(10);
		statuses.add(new Status("1         KA-01-HH-1234  White  "));
		when(parkingLotService.status()).thenReturn(statuses);

		Result result = statusCommand.execute(inputParams);

		assertEquals("Slot No. Registration No Colour\n1         KA-01-HH-1234  White  ", result.getMessage());
		verify(parkingLotService).status();
	}

	@Test
	public void shouldReturnOnlyHeaderWhenThereIsNoHeader() {
		String[] inputParams = new String[] {"status"};
		List<Status> statuses = new ArrayList<>(10);
		when(parkingLotService.status()).thenReturn(statuses);

		Result result = statusCommand.execute(inputParams);

		assertEquals("Slot No. Registration No Colour", result.getMessage());
		verify(parkingLotService).status();
	}

	@Test
	public void shouldThrowParkingLotExceptionWHenInputArgsLenghtIsNot3() {
		String[] inputParams = new String[] {"slot","sfds"};
		try {
			statusCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid input param size", exception.getMessage());
		}
	}
}