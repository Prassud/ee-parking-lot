package com.ee.parkinglot.search;

import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchSlotByColorTest {

	private SearchSlotByColor searchSlotByColor;

	@Before
	public void setUp() {
		searchSlotByColor = new SearchSlotByColor();
	}

	@Test
	public void shouldGetTheParkingSlotDetailsBasedOnCarColor() {
		Car carToBeParked = new Car("abc", Car.Color.RED);
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		ParkingSlot allocatedSlot1 = expectedParkingSlots.get(0);
		ParkingSlot allocatedSlot2 = expectedParkingSlots.get(1);
		allocatedSlot1.allocatedTo(carToBeParked);
		allocatedSlot2.allocatedTo(carToBeParked);

		List<ParkingSlot> slots = searchSlotByColor.search(Car.Color.RED, expectedParkingSlots);

		assertEquals(Stream.of(allocatedSlot1, allocatedSlot2).collect(Collectors.toList()), slots);
	}

	@Test
	public void shouldReturnParkingSlotListAsEmpty() {
		Car carToBeParked = new Car("abc", Car.Color.RED);
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		ParkingSlot allocatedSlot1 = expectedParkingSlots.get(0);
		allocatedSlot1.allocatedTo(carToBeParked);
		ParkingSlot allocatedSlot2 = expectedParkingSlots.get(1);
		allocatedSlot2.allocatedTo(carToBeParked);
		List<ParkingSlot> slots = searchSlotByColor.search(Car.Color.WHITE, expectedParkingSlots);

		assertTrue(slots.isEmpty());
	}

	@Test
	public void shouldReturnParkingSlotListAsEmptyWhenNoParkingSlotIsAllocated() {
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);

		List<ParkingSlot> slots = searchSlotByColor.search(Car.Color.WHITE, expectedParkingSlots);

		assertTrue(slots.isEmpty());
	}
}