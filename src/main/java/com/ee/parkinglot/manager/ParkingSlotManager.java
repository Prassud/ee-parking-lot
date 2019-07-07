package com.ee.parkinglot.manager;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.model.Ticket;
import com.ee.parkinglot.search.AbstractSearchParkingSlot;
import com.ee.parkinglot.ticketing.TicketManager;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class ParkingSlotManager {

	private final List<ParkingSlot> parkingSlots;

	private Map<String, AbstractSearchParkingSlot> searchCommands;

	private final ParkingLotAllocationStrategy parkingLotAllocationStrategy;

	private final TicketManager ticketManager;

	public ParkingSlotManager(ParkingLotAllocationStrategy parkingLotAllocationStrategy, TicketManager ticketManager, int range) {
		this.parkingLotAllocationStrategy = parkingLotAllocationStrategy;
		this.ticketManager = ticketManager;
		if (range <= 0) {
			new ParkingLotException("Invalid Range on creating Parking slots");
		}
		this.parkingSlots = IntStream.range(1, range).mapToObj(
				eachIndex -> new ParkingSlot(eachIndex, ParkingSlot.State.FREE)).collect(Collectors.toList());
	}

	public Ticket processParking(Car car) {
		ParkingSlot parkingSlot = parkingLotAllocationStrategy.getNextAvailableParkingSlot(parkingSlots);
		if (isNull(parkingSlot)) {
			throw new ParkingLotException("No Free Slot is Available");
		}
		parkingSlot.allocatedTo(car);
		return ticketManager.issueTicket(parkingSlot, car);
	}

	public ParkingSlot getAllocatedParkingSlotByCarRegistrationNumber(String registrationNumber) {
		Optional<ParkingSlot> parkingSlot = getAllocatedParkingSlots()
				.filter((eachSlot) -> eachSlot.getParkedCarRegistrationNumber().equals(registrationNumber))
				.findFirst();

		if (!parkingSlot.isPresent()) {
			throw new ParkingLotException("Parking slot not found");
		}
		return parkingSlot.get();
	}


	public ParkingSlot getAllocatedParkingSlotByCarColor(Car.Color color) {
		Optional<ParkingSlot> parkingSlot = getAllocatedParkingSlots().filter((eachSlot) -> eachSlot.getParkedCarColor().equals(color))
				.findFirst();

		if (!parkingSlot.isPresent()) {
			throw new ParkingLotException("Parking slot not found");
		}
		return parkingSlot.get();
	}

	private Stream<ParkingSlot> getAllocatedParkingSlots() {
		return this.parkingSlots.stream().filter(ParkingSlot::isAllocated);
	}

	public ParkingSlot searchParkingSlot(String searchCommandName, Object searchParam) {
		AbstractSearchParkingSlot searchCommand = this.searchCommands.get(searchCommandName);
		if (isNull(searchCommand)) {
			throw new ParkingLotException("Invalid Search Command");
		}
		ParkingSlot parkingSlot = searchCommand.search(searchParam);

		if (isNull(parkingSlot)) {
			throw new ParkingLotException("Parking slot not found");
		}

		return parkingSlot;
	}

	public void register(String searchCommandName, AbstractSearchParkingSlot searchCommand) {
		this.searchCommands.put(searchCommandName, searchCommand);
	}
}
