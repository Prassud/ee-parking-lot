package com.ee.parkinglot;

import com.ee.parkinglot.command.*;
import com.ee.parkinglot.factory.ParkingLotFactory;
import com.ee.parkinglot.io.ConsoleInputProcessor;
import com.ee.parkinglot.io.InputFileProcessor;
import com.ee.parkinglot.service.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.ee.parkinglot.utils.MessageConstant.PROCESS_CONSOLE_COMMMANDS;
import static com.ee.parkinglot.utils.MessageConstant.PROCESS_INPUT_FILE;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {ParkingLotApplication.class, InputFileProcessor.class, ConsoleInputProcessor.class, ParkingLotFactory.class, ParkingLotService.class, ParkCommand.class, String.class, LeaveCommand.class, StatusCommand.class, SearchSNByColorCommand.class})
public class ParkingLotApplicationTest {

	@Mock
	private CreateCommand createCommand;

	@Mock
	private ParkingLotService parkingLotService;

	@Mock
	private ParkCommand parkCommand;

	@Mock
	private LeaveCommand leaveCommand;
	@Mock
	private StatusCommand statusCommand;

	@Mock
	private SearchRNByColorCommand searchRNByColorCommand;

	@Mock
	private SearchSNByRNCommand searchSNByRNCommand;

	@Mock
	private SearchSNByColorCommand searchSNByColorCommand1;

	@Mock
	private InputFileProcessor inputFIleProcessor;

	@Mock
	private ConsoleInputProcessor consoleInputProcessor;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void shouldExecutesInputProcessor() throws Exception {
		String inputProcessorName = PROCESS_INPUT_FILE;
		String file = getClass().getClassLoader().getResource("input.text").getFile();
		String[] args = new String[] {inputProcessorName, file};
		ArgumentCaptor<String[]> actuals = ArgumentCaptor.forClass(String[].class);
		PowerMockito.mockStatic(ParkingLotFactory.class);
		BDDMockito.given(ParkingLotFactory.createParkingSlotService()).willReturn(parkingLotService);

		//verify the commands to be executed
		mockCommand();
		ParkingLotApplication.main(args);
		verifyCommands();
		verify(inputFIleProcessor).execute(actuals.capture());
		verify(consoleInputProcessor, never()).execute(any());
		assertArrayEquals(new String[] {PROCESS_INPUT_FILE, file}, actuals.getValue());
	}

	@Test
	public void shouldExecutesConsoleInputProcessor() throws Exception {
		String inputProcessorName = PROCESS_CONSOLE_COMMMANDS;
		String[] args = new String[] {inputProcessorName};
		ArgumentCaptor<String[]> actuals = ArgumentCaptor.forClass(String[].class);
		PowerMockito.mockStatic(ParkingLotFactory.class);
		BDDMockito.given(ParkingLotFactory.createParkingSlotService()).willReturn(parkingLotService);

		//verify the commands to be executed
		mockCommand();
		ParkingLotApplication.main(args);
		verifyCommands();
		verify(consoleInputProcessor).execute(actuals.capture());
		verify(inputFIleProcessor, never()).execute(any());
		assertArrayEquals(new String[] {PROCESS_CONSOLE_COMMMANDS}, actuals.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenInputProcessingTypeIsInvalid() throws Exception {
		String inputProcessorName = "abc";
		String[] args = new String[] {inputProcessorName};
		ArgumentCaptor<String[]> actuals = ArgumentCaptor.forClass(String[].class);
		PowerMockito.mockStatic(ParkingLotFactory.class);
		BDDMockito.given(ParkingLotFactory.createParkingSlotService()).willReturn(parkingLotService);

		//verify the commands to be executed
		mockCommand();
		ParkingLotApplication.main(args);
		verifyCommands();
		verify(consoleInputProcessor, never()).execute(any());
		verify(inputFIleProcessor, never()).execute(any());
	}

	private void mockCommand() throws Exception {
		PowerMockito.whenNew(CreateCommand.class).withArguments("create_parking_lot", parkingLotService).thenReturn(createCommand);
		PowerMockito.whenNew(ParkCommand.class).withArguments("park", parkingLotService).thenReturn(parkCommand);
		PowerMockito.whenNew(LeaveCommand.class).withArguments("leave", parkingLotService).thenReturn(leaveCommand);
		PowerMockito.whenNew(SearchRNByColorCommand.class).withArguments("registration_numbers_for_cars_with_colour", parkingLotService).thenReturn(searchRNByColorCommand);
		PowerMockito.whenNew(SearchSNByColorCommand.class).withArguments("slot_numbers_for_cars_with_colour", parkingLotService).thenReturn(searchSNByColorCommand1);
		PowerMockito.whenNew(SearchSNByRNCommand.class).withArguments("slot_number_for_registration_number", parkingLotService).thenReturn(searchSNByRNCommand);
		PowerMockito.whenNew(StatusCommand.class).withArguments("status", parkingLotService).thenReturn(statusCommand);
		PowerMockito.whenNew(InputFileProcessor.class).withAnyArguments().thenReturn(inputFIleProcessor);
		PowerMockito.whenNew(ConsoleInputProcessor.class).withArguments(anyMap()).thenReturn(consoleInputProcessor);
	}

	private void verifyCommands() throws Exception {
		Map<String, AbstractCommand> commandMap = getCommandMap();
		PowerMockito.verifyNew(InputFileProcessor.class).withArguments(commandMap);
		PowerMockito.verifyNew(ConsoleInputProcessor.class).withArguments(commandMap);
	}

	private Map<String, AbstractCommand> getCommandMap() {
		Map<String, AbstractCommand> commandMap = new HashMap<>();
		commandMap.put("create_parking_lot", createCommand);
		commandMap.put("park", parkCommand);
		commandMap.put("leave", leaveCommand);
		commandMap.put("registration_numbers_for_cars_with_colour", searchRNByColorCommand);
		commandMap.put("slot_numbers_for_cars_with_colour", searchSNByColorCommand1);
		commandMap.put("slot_number_for_registration_number", searchSNByRNCommand);
		commandMap.put("status", statusCommand);
		return commandMap;
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenArgsLengthIsNotEqualToOne() throws IOException {
		String[] args = new String[] {};
		ParkingLotApplication.main(args);
	}
}