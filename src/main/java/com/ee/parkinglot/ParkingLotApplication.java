package com.ee.parkinglot;

import com.ee.parkinglot.command.*;
import com.ee.parkinglot.factory.ParkingLotFactory;
import com.ee.parkinglot.io.AbstractInputProcessor;
import com.ee.parkinglot.io.ConsoleInputProcessor;
import com.ee.parkinglot.io.InputFileProcessor;
import com.ee.parkinglot.service.ParkingLotService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.ee.parkinglot.utils.MessageConstant.*;
import static java.util.Objects.isNull;

public class ParkingLotApplication {
	private Map<String, AbstractInputProcessor> inputProcessors;

	private void initialize() {
		Map<String, AbstractCommand> commands = new HashMap<>();
		ParkingLotService parkingLotService = ParkingLotFactory.createParkingSlotService();
		createCommands(commands, parkingLotService);

		//input processors
		inputProcessors = new HashMap<>(10);
		inputProcessors.put(PROCESS_INPUT_FILE, new InputFileProcessor(commands));
		inputProcessors.put(PROCESS_CONSOLE_COMMMANDS, new ConsoleInputProcessor(commands));
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Invalid Input option Provided");
		}

		ParkingLotApplication app = new ParkingLotApplication();
		app.initialize();

		AbstractInputProcessor abstractInputProcessor = app.inputProcessors.get(args[0]);
		if (isNull(abstractInputProcessor)) {
			throw new IllegalArgumentException("Invalid option to process");
		}
		abstractInputProcessor.execute(args);
	}

	private void createCommands(Map<String, AbstractCommand> commands, ParkingLotService parkingLotService) {
		commands.put(CREATE_COMMAND, new CreateCommand(CREATE_COMMAND, parkingLotService));
		commands.put(PARK_COMMAND_NAME, new ParkCommand(PARK_COMMAND_NAME, parkingLotService));
		commands.put(LEAVE_COMMAND_NAME, new LeaveCommand(LEAVE_COMMAND_NAME, parkingLotService));
		commands.put(SEARCH_BY_COLOR_FOR_SN, new SearchSNByColorCommand(SEARCH_BY_COLOR_FOR_SN, parkingLotService));
		commands.put(SEARCH_BY_REGISTER_NUMBER, new SearchRNByColorCommand(SEARCH_BY_REGISTER_NUMBER, parkingLotService));
		commands.put(SEARCH_BY_RN_FOR_SN, new SearchSNByRNCommand(SEARCH_BY_RN_FOR_SN, parkingLotService));
		commands.put(STATUS_COMMAND_NAME, new StatusCommand(STATUS_COMMAND_NAME, parkingLotService));
	}
}
