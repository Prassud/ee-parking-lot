package com.ee.parkinglot.search;

import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;

import java.util.List;
import java.util.stream.Collectors;

public class SearchSlotByColor extends AbstractSearchParkingSlot {

	@Override
	public List<ParkingSlot> search(Object searchParam, List<ParkingSlot> parkingSlots) {
		Car.Color color = (Car.Color) searchParam;
		return getAllocatedParkingSlots(parkingSlots)
				.filter(eachParkingSlot -> eachParkingSlot.getParkedCarColor().equals(color)).collect(Collectors.toList());
	}
}
