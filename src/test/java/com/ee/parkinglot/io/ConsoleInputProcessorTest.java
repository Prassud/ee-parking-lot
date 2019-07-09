package com.ee.parkinglot.io;

import com.ee.parkinglot.command.AbstractCommand;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConsoleInputProcessorTest extends BaseInputProcessor {

	private ConsoleInputProcessor consoleInputProcessor;

	private Map<String, AbstractCommand> commandMap = new HashMap<>();

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		createCommands(commandMap);
		consoleInputProcessor = new ConsoleInputProcessor(commandMap);
	}

	@Test
	public void shouldExecuteCreateCommands() throws Exception {
		String[] args = new String[] {MessageConstant.PROCESS_CONSOLE_COMMMANDS};
		ByteArrayInputStream bais = new ByteArrayInputStream("create_parking_lot 6".getBytes());
		System.setIn(bais);
		//verify the commands to be executed
		consoleInputProcessor.execute(args);

		ArgumentCaptor<String[]> commandCapto = ArgumentCaptor.forClass(String[].class);
		verify(createCommand).execute(commandCapto.capture());
		assertArrayEquals(new String[] {"create_parking_lot", "6"}, commandCapto.getValue());
	}

	@Test
	public void shouldExecuteParkCommands() throws Exception {
		String[] args = new String[] {MessageConstant.PROCESS_CONSOLE_COMMMANDS};
		ByteArrayInputStream bais = new ByteArrayInputStream("park KA-01-HH-1234 White".getBytes());
		System.setIn(bais);
		//verify the commands to be executed
		consoleInputProcessor.execute(args);

		ArgumentCaptor<String[]> commandCapto = ArgumentCaptor.forClass(String[].class);
		verify(parkCommand).execute(commandCapto.capture());
		assertArrayEquals(new String[] {"park", "KA-01-HH-1234", "White"}, commandCapto.getValue());
	}

	@Test
	public void shouldExecuteLeaveCommands() throws Exception {
		String[] args = new String[] {MessageConstant.PROCESS_CONSOLE_COMMMANDS};
		ByteArrayInputStream bais = new ByteArrayInputStream("leave 4".getBytes());
		System.setIn(bais);
		//verify the commands to be executed
		consoleInputProcessor.execute(args);

		ArgumentCaptor<String[]> commandCapto = ArgumentCaptor.forClass(String[].class);
		verify(leaveCommand).execute(commandCapto.capture());
		assertArrayEquals(new String[] {"leave", "4"}, commandCapto.getValue());
	}

	@Test
	public void shouldExecuteStatusCommands() throws Exception {
		String[] args = new String[] {MessageConstant.PROCESS_CONSOLE_COMMMANDS};
		ByteArrayInputStream bais = new ByteArrayInputStream("status".getBytes());
		System.setIn(bais);
		//verify the commands to be executed
		consoleInputProcessor.execute(args);

		ArgumentCaptor<String[]> commandCapto = ArgumentCaptor.forClass(String[].class);
		verify(statusCommand).execute(commandCapto.capture());
		assertArrayEquals(new String[] {"status"}, commandCapto.getValue());
	}

	@Test
	public void shouldExecuteSearnRnbyColorCommands() throws Exception {
		String[] args = new String[] {MessageConstant.PROCESS_CONSOLE_COMMMANDS};
		ByteArrayInputStream bais = new ByteArrayInputStream("registration_numbers_for_cars_with_colour White".getBytes());
		System.setIn(bais);
		//verify the commands to be executed
		consoleInputProcessor.execute(args);

		ArgumentCaptor<String[]> commandCapto = ArgumentCaptor.forClass(String[].class);
		verify(searchRNByColorCommand).execute(commandCapto.capture());
		assertArrayEquals(new String[] {"registration_numbers_for_cars_with_colour", "White"}, commandCapto.getValue());
	}

	@Test
	public void shouldExecuteSearnSnbyColorCommands() throws Exception {


		String[] args = new String[] {MessageConstant.PROCESS_CONSOLE_COMMMANDS};
		ByteArrayInputStream bais = new ByteArrayInputStream("slot_numbers_for_cars_with_colour White".getBytes());
		System.setIn(bais);
		//verify the commands to be executed
		consoleInputProcessor.execute(args);

		ArgumentCaptor<String[]> commandCapto = ArgumentCaptor.forClass(String[].class);
		verify(searchSNByColorCommand1).execute(commandCapto.capture());
		assertArrayEquals(new String[] {"slot_numbers_for_cars_with_colour", "White"}, commandCapto.getValue());
	}

	@Test
	public void shouldExecuteSearchSnbyRNCommands() throws Exception {
		String[] args = new String[] {MessageConstant.PROCESS_CONSOLE_COMMMANDS};
		ByteArrayInputStream bais = new ByteArrayInputStream("slot_number_for_registration_number MH-04-AY-1111".getBytes());
		System.setIn(bais);
		//verify the commands to be executed
		consoleInputProcessor.execute(args);

		ArgumentCaptor<String[]> commandCapto = ArgumentCaptor.forClass(String[].class);
		verify(searchSNByRNCommand).execute(commandCapto.capture());
		assertArrayEquals(new String[] {"slot_number_for_registration_number", "MH-04-AY-1111"}, commandCapto.getValue());
	}

	@Test
	public void ShouldNotExecuteCommandOnExit() throws Exception {
		String[] args = new String[] {MessageConstant.PROCESS_CONSOLE_COMMMANDS};
		ByteArrayInputStream bais = new ByteArrayInputStream("exit".getBytes());
		System.setIn(bais);
		//verify the commands to be executed
		consoleInputProcessor.execute(args);


		verify(createCommand, never()).execute(any());
		verify(parkCommand, never()).execute(any());
		verify(leaveCommand, never()).execute(any());
		verify(statusCommand, never()).execute(any());
		verify(searchSNByRNCommand, never()).execute(any());
		verify(searchSNByColorCommand1, never()).execute(any());
		verify(searchRNByColorCommand, never()).execute(any());
	}

	@Test(expected = ParkingLotException.class)
	public void shouldThrowInvalidExceptionWhenInvalidCommandIsProvide() throws Exception {
		String[] args = new String[] {MessageConstant.PROCESS_CONSOLE_COMMMANDS};
		ByteArrayInputStream bais = new ByteArrayInputStream("shfdkjfdsjfds".getBytes());
		System.setIn(bais);
		//verify the commands to be executed
		consoleInputProcessor.execute(args);


		verify(createCommand, never()).execute(any());
		verify(parkCommand, never()).execute(any());
		verify(leaveCommand, never()).execute(any());
		verify(statusCommand, never()).execute(any());
		verify(searchSNByRNCommand, never()).execute(any());
		verify(searchSNByColorCommand1, never()).execute(any());
		verify(searchRNByColorCommand, never()).execute(any());
	}
}