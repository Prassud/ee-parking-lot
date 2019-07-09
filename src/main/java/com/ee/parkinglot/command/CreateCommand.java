package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.service.ParkingLotService;

public class CreateCommand extends AbstractCommand {

	public CreateCommand(String commandName, ParkingLotService parkingLotService) {
		super(commandName, parkingLotService);
	}

	@Override
	public Result execute(String[] args) {
		validateInputParamLength(args,2);
		String size = args[1];
		Integer value = getInteger(size);

		parkingLotService.createParkingLot(value);
		return new Result(String.format("Created a parking lot with %d slots", value), true);
	}
}
