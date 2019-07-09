package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.utils.MessageConstant;

import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class SearchSNByRNCommand extends AbstractCommand {
	public SearchSNByRNCommand(String commandName, ParkingLotService parkingLotService) {
		super(commandName, parkingLotService);
	}


	@Override
	public Result execute(String[] args) {
		validateInputParamLength(args, 2);
		String registrationNumber = args[1];
		List<ParkingSlot> parkingSlots;
		try {
			parkingSlots = parkingLotService.search(MessageConstant.GET_SLOT_BY_RN, registrationNumber);
		} catch (ParkingLotException ex) {
			return new Result(ex.getMessage(), false);
		}

		List<String> registrationNumbers = parkingSlots.stream().
				map(eachSlot -> String.valueOf(eachSlot.getParkedCarRegistrationNumber())).collect(Collectors.toList());
		Result result = new Result(String.join(",", registrationNumbers), true);
		return result;
	}
}
