package com.ee.parkinglot.command;

public abstract class AbstractCommand {

	private String commandName;

	public AbstractCommand(String commandName) {
		this.commandName = commandName;
	}

	public abstract void execute(String[] args);
}
