package com.ee.parkinglot.utils;

import com.ee.parkinglot.model.ParkingSlot;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {

	public static List<ParkingSlot> createMultipleFreeParkingLots(int range) {
		return IntStream.range(1, range).mapToObj(
				eachIndex -> new ParkingSlot((int) eachIndex, ParkingSlot.State.FREE)).collect(Collectors.toList());
	}
}
