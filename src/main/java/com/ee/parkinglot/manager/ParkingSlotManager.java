package com.ee.parkinglot.manager;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.model.Ticket;
import com.ee.parkinglot.search.AbstractSearchParkingSlot;
import com.ee.parkinglot.ticketing.TicketManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

public class ParkingSlotManager {

	private final List<ParkingSlot> parkingSlots;

	private Map<String, AbstractSearchParkingSlot> searchCommands = new HashMap<>();

	private final ParkingLotAllocationStrategy parkingLotAllocationStrategy;

	private final TicketManager ticketManager;

	public ParkingSlotManager(ParkingLotAllocationStrategy parkingLotAllocationStrategy,
	                          TicketManager ticketManager, List<ParkingSlot> parkingSlots) {
		this.parkingLotAllocationStrategy = parkingLotAllocationStrategy;
		this.ticketManager = ticketManager;
		this.parkingSlots = parkingSlots;
	}

	public Ticket park(Car car) {
		ParkingSlot parkingSlot = parkingLotAllocationStrategy.getNextAvailableParkingSlot(parkingSlots);
		if (isNull(parkingSlot)) {
			throw new ParkingLotException("No Free Slot is Available");
		}
		parkingSlot.allocatedTo(car);
		return ticketManager.issueTicket(parkingSlot, car);
	}

	public List<ParkingSlot> searchParkingSlot(String searchCommandName, Object searchParam) {
		AbstractSearchParkingSlot searchCommand = this.searchCommands.get(searchCommandName);
		if (isNull(searchCommand)) {
			throw new ParkingLotException("Invalid Search Command");
		}
		List<ParkingSlot> parkingSlots = searchCommand.search(searchParam, this.parkingSlots);

		if (parkingSlots.isEmpty()) {
			throw new ParkingLotException("Parking slot not found");
		}

		return parkingSlots;
	}

	public void register(String searchCommandName, AbstractSearchParkingSlot searchCommand) {
		this.searchCommands.put(searchCommandName, searchCommand);
	}

	public Car unPark(int slotNumber) {
		Optional<ParkingSlot> parkingSlot = this.parkingSlots.stream().filter(eachParkingSlot ->
				eachParkingSlot.getSlotNumber() == slotNumber).findFirst();

		if (!parkingSlot.isPresent()) {
			throw new ParkingLotException("ParkingSlot NotFound");
		}
		return parkingSlot.get().free();
	}
}
