package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.model.Ticket;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ParkCommandTest {
	@Mock
	private ParkingLotService parkingLotService;

	private ParkCommand parkCommand;

	@Before
	public void setUp() {
		initMocks(this);
		this.parkCommand = new ParkCommand(MessageConstant.PARK_COMMAND_NAME, parkingLotService);
	}

	@Test
	public void shouldUseParkingLotServiceToParkTheCar() {
		String[] inputParams = new String[] {"park", "KA-01-P-333", "White"};
		ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);
		ParkingSlot slot = new ParkingSlot(1, ParkingSlot.State.FREE);
		Car car = new Car("", Car.Color.White);
		Ticket ticket = new Ticket(slot, car);
		when(parkingLotService.park(any())).thenReturn(ticket);

		Result result = parkCommand.execute(inputParams);

		assertEquals("Allocated slot number: 1", result.getMessage());
		verify(parkingLotService).park(captor.capture());

		Car car1 = captor.getValue();
		assertEquals("KA-01-P-333", car1.getRegistrationNumber());
		assertEquals(Car.Color.valueOf("White"), car1.getColor());
		assertEquals("Allocated slot number: 1", result.getMessage());
	}

	@Test
	public void shouldThrowParkingLotExceptionWHenInputArgsLenghtIsNot3() {
		String[] inputParams = new String[] {"park", "KA-01-P-333", "White", "sdds"};
		try {
			parkCommand.execute(inputParams);
		} catch (ParkingLotException exception) {
			assertEquals("Invalid input param size", exception.getMessage());
		}

		verify(parkingLotService, never()).park(any());
	}

	@Test
	public void shouldHandleparkingLotExceptionAndReturnResultWithErrorMessage() {
		String[] inputParams = new String[] {"park", "KA-01-P-333", "White"};
		doThrow(new ParkingLotException("blah blah")).when(parkingLotService).park(any());

		Result result = parkCommand.execute(inputParams);

		assertEquals("blah blah", result.getMessage());
	}
}