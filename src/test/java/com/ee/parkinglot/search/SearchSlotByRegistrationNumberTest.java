package com.ee.parkinglot.search;

import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class SearchSlotByRegistrationNumberTest {


	private SearchSlotByRegistrationNumber searchSlotByRegistrationNumber;

	@Before
	public void setUp() {
		searchSlotByRegistrationNumber = new SearchSlotByRegistrationNumber();
	}

	@Test
	public void shouldGetTheParkingSlotDetailsBasedOnRegistrationNumber() {
		Car carToBeParked = new Car("abc", Car.Color.RED);
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		ParkingSlot allocatedSlot1 = expectedParkingSlots.get(0);
		allocatedSlot1.allocatedTo(carToBeParked);

		List<ParkingSlot> slots = searchSlotByRegistrationNumber.search("abc", expectedParkingSlots);

		assertEquals(Stream.of(allocatedSlot1).collect(Collectors.toList()), slots);
	}

	@Test
	public void shouldReturnParkingSlotListAsEmpty() {
		Car carToBeParked = new Car("abc", Car.Color.RED);
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		ParkingSlot allocatedSlot1 = expectedParkingSlots.get(0);
		allocatedSlot1.allocatedTo(carToBeParked);

		List<ParkingSlot> slots = searchSlotByRegistrationNumber.search("", expectedParkingSlots);

		assertTrue(slots.isEmpty());
	}

	@Test
	public void shouldReturnParkingSlotListAsEmptyWhenNoParkingSlotIsAllocated() {
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);

		List<ParkingSlot> slots = searchSlotByRegistrationNumber.search("qwewe", expectedParkingSlots);

		assertTrue(slots.isEmpty());
	}
}