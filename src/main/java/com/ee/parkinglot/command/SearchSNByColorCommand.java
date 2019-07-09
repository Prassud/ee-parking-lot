package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.service.ParkingLotService;
import com.ee.parkinglot.utils.MessageConstant;

import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class SearchSNByColorCommand extends AbstractCommand {
	public SearchSNByColorCommand(String commandName, ParkingLotService parkingLotService) {
		super(commandName, parkingLotService);
	}


	@Override
	public Result execute(String[] args) {
		validateInputParamLength(args, 2);
		Car.Color color = Car.Color.valueOf(args[1].toUpperCase());
		List<ParkingSlot> parkingSlots;
		try {
			parkingSlots = parkingLotService.search(MessageConstant.GET_SLOT_BY_COLOR, color);
		} catch (PatternSyntaxException ex) {
			return new Result(ex.getMessage(), true);
		}

		List<String> slotNumbers = parkingSlots.stream().
				map(eachSlot -> String.valueOf(eachSlot.getSlotNumber())).collect(Collectors.toList());
		Result result = new Result(String.join(",", slotNumbers), true);
		return result;
	}
}
