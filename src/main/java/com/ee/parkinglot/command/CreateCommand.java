package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.service.ParkingLotService;

public class CreateCommand extends AbstractCommand {

	private ParkingLotService parkingLotService;

	public CreateCommand(String commandName, ParkingLotService parkingLotService) {
		super(commandName);
		this.parkingLotService = parkingLotService;
	}

	@Override
	public Result execute(String[] args) {
		if (args.length != 2) {
			throw new ParkingLotException("Invalid Parking Lot size");
		}
		String size = args[1];
		Integer value;

		try {
			value = Integer.valueOf(size);
		} catch (NumberFormatException nfe) {
			throw new ParkingLotException("Invalid Parking Lot size");
		}

		if (value <= 0) {
			throw new ParkingLotException("Invalid Parking Lot size");
		}

		parkingLotService.createParkingLot(value);
		return new Result(String.format("Created a parking lot with %d slots", value), true);
	}
}
