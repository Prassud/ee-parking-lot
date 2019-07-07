package com.ee.parkinglot;

import com.ee.parkinglot.command.*;
import com.ee.parkinglot.exception.ParkingLotException;
import sun.security.pkcs.ParsingException;

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

		this.commands.put(CREATE_COMMAND, new CreateCommand(CREATE_COMMAND));
		this.commands.put(PARK_COMMAND_NAME, new ParkCommand(PARK_COMMAND_NAME));
		this.commands.put(LEAVE_COMMAND_NAME, new LeaveCommand(LEAVE_COMMAND_NAME));
		this.commands.put(SEARCH_BY_COLOR, new SearchCommand(SEARCH_BY_COLOR));
		this.commands.put(SEARCH_BY_REGISTER_NUMBER, new SearchCommand(SEARCH_BY_REGISTER_NUMBER));
		this.commands.put(STATUS_COMMAND_NAME, new StatusCommand(STATUS_COMMAND_NAME));
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
