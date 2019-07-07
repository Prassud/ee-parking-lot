package com.ee.parkinglot.utils;

import com.ee.parkinglot.model.ParkingLot;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {

	public static List<ParkingLot> createMultipleFreeParkingLots(int range) {
		return IntStream.range(1, range).mapToObj(
				eachIndex -> new ParkingLot((int) eachIndex, ParkingLot.State.FREE)).collect(Collectors.toList());
	}
}
