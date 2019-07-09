package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.bean.Status;
import com.ee.parkinglot.service.ParkingLotService;

import java.util.List;

public class StatusCommand extends AbstractCommand {

	public StatusCommand(String commandName, ParkingLotService parkingLotService) {
		super(commandName, parkingLotService);
	}

	@Override
	public Result execute(String[] args) {
		validateInputParamLength(args, 1);
		String header = "Slot No. Registration No Colour";
		StringBuffer buffer = new StringBuffer(10);
		buffer.append(header).append(System.lineSeparator());

		List<Status> status = parkingLotService.status();
		status.stream().forEach(eachStatus -> {
			buffer.append(eachStatus.getStatusInfo()).append(System.lineSeparator());
		});
		buffer.deleteCharAt(buffer.length() - 1);
		return new Result(buffer.toString(), true);
	}
}
