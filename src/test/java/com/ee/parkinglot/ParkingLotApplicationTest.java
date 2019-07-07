package com.ee.parkinglot;

import com.ee.parkinglot.command.*;
import com.ee.parkinglot.exception.ParkingLotException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {ParkingLotApplication.class, ParkCommand.class, String.class, LeaveCommand.class, StatusCommand.class, SearchCommand.class})
public class ParkingLotApplicationTest {

	@Mock
	private CreateCommand createCommand;

	@Mock
	private ParkCommand parkCommand;

	@Mock
	private LeaveCommand leaveCommand;
	@Mock
	private StatusCommand statusCommand;

	@Mock
	private SearchCommand searchCommand;

	@Mock
	private SearchCommand searchCommand1;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void shouldExecuteCommands() throws Exception {
		String file = getClass().getClassLoader().getResource("input.text").getFile();
		String[] args = new String[] {file};

		//verify the commands to be executed
		mockCommand();
		ParkingLotApplication.main(args);
		verifyCommands();
	}

	private void mockCommand() throws Exception {
		PowerMockito.whenNew(CreateCommand.class).withArguments("create_parking_lot").thenReturn(createCommand);
		PowerMockito.whenNew(ParkCommand.class).withArguments("park").thenReturn(parkCommand);
		PowerMockito.whenNew(LeaveCommand.class).withArguments("leave").thenReturn(leaveCommand);
		PowerMockito.whenNew(SearchCommand.class).withArguments("registration_numbers_for_cars_with_colour").thenReturn(searchCommand);
		PowerMockito.whenNew(SearchCommand.class).withArguments("slot_numbers_for_cars_with_colour").thenReturn(searchCommand1);
		PowerMockito.whenNew(StatusCommand.class).withArguments("status").thenReturn(statusCommand);
	}

	private void verifyCommands() {
		ArgumentCaptor<String[]> parkCommandCaptor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> leaveCommandCaptor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> searchCommandCaptor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> searchCommand1Captor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> statusCommandCaptor = ArgumentCaptor.forClass(String[].class);

		verify(parkCommand).execute(parkCommandCaptor.capture());
		verify(leaveCommand).execute(leaveCommandCaptor.capture());
		verify(searchCommand).execute(searchCommandCaptor.capture());
		verify(searchCommand1).execute(searchCommand1Captor.capture());
		verify(statusCommand).execute(statusCommandCaptor.capture());

		assertArrayEquals(new String[] {"park", "KA-01-HH-1234", "White"}, parkCommandCaptor.getValue());
		assertArrayEquals(new String[] {"leave", "4"}, leaveCommandCaptor.getValue());
		assertArrayEquals(new String[] {"registration_numbers_for_cars_with_colour", "White"}, searchCommandCaptor.getValue());
		assertArrayEquals(new String[] {"slot_numbers_for_cars_with_colour", "White"}, searchCommand1Captor.getValue());
		assertArrayEquals(new String[] {"status"}, statusCommandCaptor.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenArgsLengthIsNotEqualToOne() throws IOException {
		String[] args = new String[] {};
		ParkingLotApplication.main(args);
	}

	@Test(expected = ParkingLotException.class)
	public void shouldThrowParkingLotExceptionIfCommandIsNotPresent() throws IOException {
		String file = getClass().getClassLoader().getResource("input1.text").getFile();
		String[] args = new String[] {file};

		ParkingLotApplication.main(args);
	}
}