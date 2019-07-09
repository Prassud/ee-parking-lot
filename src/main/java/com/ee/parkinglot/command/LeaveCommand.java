package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.service.ParkingLotService;

public class LeaveCommand extends AbstractCommand {

	public LeaveCommand(String commandName, ParkingLotService parkingLotService) {
		super(commandName, parkingLotService);
	}

	@Override
	public Result execute(String[] args) {
		validateInputParamLength(args, 2);
		Integer slotNumber = getInteger(args[1]);
		try {
			parkingLotService.unPark(slotNumber);
		} catch (ParkingLotException ex) {
			return new Result(String.format("Slot Number %d is free", slotNumber), false);
		}

		return new Result(String.format("Slot Number %d is free", slotNumber), true);
	}
}
