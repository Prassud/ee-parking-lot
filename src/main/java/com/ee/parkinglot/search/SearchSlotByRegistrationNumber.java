package com.ee.parkinglot.search;

import com.ee.parkinglot.model.ParkingSlot;

import java.util.List;
import java.util.stream.Collectors;

public class SearchSlotByRegistrationNumber extends AbstractSearchParkingSlot {

	@Override
	public List<ParkingSlot> search(Object searchParam, List<ParkingSlot> parkingSlots) {
		return getAllocatedParkingSlots(parkingSlots)
				.filter(eachSlot -> eachSlot.getParkedCarRegistrationNumber().equals(searchParam))
				.collect(Collectors.toList());
	}
}
