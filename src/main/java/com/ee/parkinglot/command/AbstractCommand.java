package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.service.ParkingLotService;

public abstract class AbstractCommand {

	private String commandName;

	protected ParkingLotService parkingLotService;

	public AbstractCommand(String commandName, ParkingLotService parkingLotService) {
		this.commandName = commandName;
		this.parkingLotService = parkingLotService;
	}

	protected void validateInputParamLength(String[] inputPrams, int maxLen) {
		if (inputPrams.length != maxLen) {
			throw new ParkingLotException("Invalid input param size");
		}
	}

	protected Integer getInteger(String size) {
		Integer value;
		try {
			value = Integer.valueOf(size);
		} catch (NumberFormatException nfe) {
			throw new ParkingLotException("Invalid Number " + size);
		}

		if (value <= 0) {
			throw new ParkingLotException("Invalid Number " + value);
		}
		return value;
	}

	public abstract Result execute(String[] args);
}
