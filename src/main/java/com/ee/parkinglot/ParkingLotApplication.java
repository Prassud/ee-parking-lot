package com.ee.parkinglot;

import com.ee.parkinglot.command.*;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.factory.ParkingLotFactory;
import com.ee.parkinglot.service.ParkingLotService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.ee.parkinglot.utils.MessageConstant.*;
import static java.util.Objects.isNull;

public class ParkingLotApplication {
	private Map<String, AbstractCommand> commands;

	private void initialize() {
		this.commands = new HashMap<>();


		ParkingLotService parkingLotService = ParkingLotFactory.createParkingSlotService();
		this.commands.put(CREATE_COMMAND, new CreateCommand(CREATE_COMMAND, parkingLotService));
		this.commands.put(PARK_COMMAND_NAME, new ParkCommand(PARK_COMMAND_NAME, parkingLotService));
		this.commands.put(LEAVE_COMMAND_NAME, new LeaveCommand(LEAVE_COMMAND_NAME, parkingLotService));
		this.commands.put(SEARCH_BY_COLOR_FOR_SN, new SearchSNByColorCommand(SEARCH_BY_COLOR_FOR_SN, parkingLotService));
		this.commands.put(SEARCH_BY_REGISTER_NUMBER, new SearchRNByColorCommand(SEARCH_BY_REGISTER_NUMBER, parkingLotService));
		this.commands.put(SEARCH_BY_RN_FOR_SN, new SearchSNByRNCommand(SEARCH_BY_RN_FOR_SN, parkingLotService));
		this.commands.put(STATUS_COMMAND_NAME, new StatusCommand(STATUS_COMMAND_NAME, parkingLotService));
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Invalid Input file Provided");
		}
		ParkingLotApplication app = new ParkingLotApplication();
		app.initialize();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		String input;

		while ((input = bufferedReader.readLine()) != null) {
			String[] inputParms = input.split(" ");

			AbstractCommand command = app.commands.get(inputParms[0]);
			if (isNull(command)) {
				throw new ParkingLotException("Invalid Command " + inputParms[0]);
			}
			command.execute(inputParms);
		}

	}
}
