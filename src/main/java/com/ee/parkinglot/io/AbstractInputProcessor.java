package com.ee.parkinglot.io;

import com.ee.parkinglot.command.AbstractCommand;
import com.ee.parkinglot.exception.ParkingLotException;

import java.io.IOException;
import java.util.Map;

import static java.util.Objects.isNull;

public abstract class AbstractInputProcessor {
	private Map<String, AbstractCommand> commands;

	public AbstractInputProcessor(Map<String, AbstractCommand> commands) {
		this.commands = commands;
	}

	public void process(String[] inputParms) {
		AbstractCommand command = commands.get(inputParms[0]);
		if (isNull(command)) {
			throw new ParkingLotException("Invalid Command " + inputParms[0]);
		}
		command.execute(inputParms);
	}

	public abstract void execute(String[] args) throws IOException;
}
