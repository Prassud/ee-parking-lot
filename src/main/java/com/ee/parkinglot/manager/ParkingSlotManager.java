package com.ee.parkinglot.manager;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.model.Ticket;
import com.ee.parkinglot.ticketing.TicketManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

public class ParkingSlotManager {

	private final List<ParkingSlot> parkingSlots;

	private final ParkingLotAllocationStrategy parkingLotAllocationStrategy;

	private final TicketManager ticketManager;

	public ParkingSlotManager(ParkingLotAllocationStrategy parkingLotAllocationStrategy, TicketManager ticketManager, int range) {
		this.parkingLotAllocationStrategy = parkingLotAllocationStrategy;
		this.ticketManager = ticketManager;
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

	public ParkingSlot getAllocatedParkingSlotBySlotNumber(int slotNumber) {
		if (this.parkingSlots.size() > slotNumber) {
			throw new ParkingLotException("Parkingslot exceeds the maximum size");
		}
		ParkingSlot parkingSlot = this.parkingSlots.get(slotNumber - 1);
		if (parkingSlot.isFree()) {
			throw new ParkingLotException("Parking Lot is not allocated");
		}
		return parkingSlot;
	}
}
