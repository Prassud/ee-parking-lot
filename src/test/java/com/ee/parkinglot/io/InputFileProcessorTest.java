package com.ee.parkinglot.io;

import com.ee.parkinglot.command.AbstractCommand;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class InputFileProcessorTest extends BaseInputProcessor {

	private InputFileProcessor inputFileProcessor;

	private Map<String, AbstractCommand> commandMap = new HashMap<>();

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		createCommands(commandMap);
		inputFileProcessor = new InputFileProcessor(commandMap);
	}

	@Test
	public void shouldExecuteCommands() throws Exception {
		String file = getClass().getClassLoader().getResource("input.text").getFile();
		String[] args = new String[] {MessageConstant.PROCESS_INPUT_FILE, file};

		//verify the commands to be executed
		inputFileProcessor.execute(args);
		verifyCommands();
	}


	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenArgsLengthIsLessThanTwo() throws Exception {
		String[] args = new String[] {MessageConstant.PROCESS_INPUT_FILE};

		//verify the commands to be executed
		inputFileProcessor.execute(args);
	}

	@Test(expected = ParkingLotException.class)
	public void shouldThrowParkingLotExceptionWhenInvalidCommandIsprovided() throws Exception {
		String file = getClass().getClassLoader().getResource("input1.text").getFile();
		String[] args = new String[] {MessageConstant.PROCESS_INPUT_FILE, file};

		//verify the commands to be executed
		inputFileProcessor.execute(args);
	}

	private void verifyCommands() {
		ArgumentCaptor<String[]> createCommandCaptor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> parkCommandCaptor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> leaveCommandCaptor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> searchRNByColorCaptor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> searchSNByColorCaptor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> searchSNByRNCaptor = ArgumentCaptor.forClass(String[].class);
		ArgumentCaptor<String[]> statusCommandCaptor = ArgumentCaptor.forClass(String[].class);

		verify(createCommand).execute(createCommandCaptor.capture());
		verify(parkCommand).execute(parkCommandCaptor.capture());
		verify(leaveCommand).execute(leaveCommandCaptor.capture());
		verify(searchRNByColorCommand).execute(searchRNByColorCaptor.capture());

		verify(searchSNByColorCommand1).execute(searchSNByColorCaptor.capture());
		verify(searchSNByRNCommand).execute(searchSNByRNCaptor.capture());
		verify(statusCommand).execute(statusCommandCaptor.capture());

		assertArrayEquals(new String[] {"create_parking_lot", "6"}, createCommandCaptor.getValue());
		assertArrayEquals(new String[] {"park", "KA-01-HH-1234", "White"}, parkCommandCaptor.getValue());
		assertArrayEquals(new String[] {"leave", "4"}, leaveCommandCaptor.getValue());
		assertArrayEquals(new String[] {"registration_numbers_for_cars_with_colour", "White"}, searchRNByColorCaptor.getValue());
		assertArrayEquals(new String[] {"slot_numbers_for_cars_with_colour", "White"}, searchSNByColorCaptor.getValue());
		assertArrayEquals(new String[] {"slot_number_for_registration_number", "MH-04-AY-1111"}, searchSNByRNCaptor.getValue());
		assertArrayEquals(new String[] {"status"}, statusCommandCaptor.getValue());
	}
}