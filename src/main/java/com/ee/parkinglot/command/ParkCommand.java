package com.ee.parkinglot.command;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.Ticket;
import com.ee.parkinglot.service.ParkingLotService;

public class ParkCommand extends AbstractCommand {
	public ParkCommand(String commandName, ParkingLotService parkingLotService) {
		super(commandName, parkingLotService);
	}

	@Override
	public Result execute(String[] args) {
		validateInputParamLength(args, 3);
		String registrationNumber = args[1];
		String color = args[2];

		Car car = new Car(registrationNumber, Car.Color.valueOf(color));
		Ticket ticket;
		try {
			ticket = parkingLotService.park(car);
		} catch (ParkingLotException ex) {
			return new Result(ex.getMessage(), false);
		}

		return new Result(String.format("Allocated slot number: %d", ticket.getParkingSlotNumber()), true);
	}
}
