package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;

public abstract class AbstractCommand {

	private String commandName;

	public AbstractCommand(String commandName) {
		this.commandName = commandName;
	}

	public abstract Result execute(String[] args);
}
