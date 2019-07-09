package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.service.ParkingLotService;

public class StatusCommand extends AbstractCommand {

	public StatusCommand(String commandName, ParkingLotService parkingLotService) {
		super(commandName, parkingLotService);
	}

	@Override
	public Result execute(String[] args) {
		return null;
	}
}
