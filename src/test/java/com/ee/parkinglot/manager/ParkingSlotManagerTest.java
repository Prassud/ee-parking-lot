package com.ee.parkinglot.manager;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.model.Ticket;
import com.ee.parkinglot.search.AbstractSearchParkingSlot;
import com.ee.parkinglot.ticketing.TicketManager;
import com.ee.parkinglot.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class ParkingSlotManagerTest {

	@Mock
	private ParkingLotAllocationStrategy parkingLotAllocationStrategy;

	@Mock
	private TicketManager ticketManager;

	private ParkingSlotManager parkingSlotManager;

	@Mock
	private AbstractSearchParkingSlot abstractSearchParkingSlot;

	@Before
	public void setUp() {
		initMocks(this);
		parkingSlotManager = new ParkingSlotManager(parkingLotAllocationStrategy, ticketManager, 10);
		parkingSlotManager.register("get_by_color", abstractSearchParkingSlot);
	}

	@Test
	public void shouldCallParkingLotAllocationStrategyToGetNextAvailableTicket() {
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		Car carToBeParked = new Car("abc", Car.Color.RED);
		Ticket expectedTicket = new Ticket(expectedParkingSlots.get(0), carToBeParked);
		when(parkingLotAllocationStrategy.getNextAvailableParkingSlot(anyList())).thenReturn(expectedParkingSlots.get(0));
		when(ticketManager.issueTicket(expectedParkingSlots.get(0), carToBeParked)).thenReturn(expectedTicket);
		Ticket ticket = parkingSlotManager.park(carToBeParked);

		ArgumentCaptor<List> parkingSlotsCaptor = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<ParkingSlot> parkingLotCaptor = ArgumentCaptor.forClass(ParkingSlot.class);
		ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
		verify(parkingLotAllocationStrategy).getNextAvailableParkingSlot(parkingSlotsCaptor.capture());
		verify(ticketManager).issueTicket(parkingLotCaptor.capture(), carArgumentCaptor.capture());

		List<ParkingSlot> parkingSlots = parkingSlotsCaptor.getValue();
		Car ticketIssuedCar = carArgumentCaptor.getValue();
		ParkingSlot parkingSlot = parkingLotCaptor.getValue();

		assertEquals(expectedParkingSlots, parkingSlots);
		assertEquals(expectedParkingSlots.get(0), parkingSlot);
		assertEquals(carToBeParked, ticketIssuedCar);
		assertEquals(expectedTicket, ticket);
	}

	@Test
	public void shouldCreateTicketUsingTicketManager() {
		Car carToBeParked = new Car("abc", Car.Color.RED);
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		when(parkingLotAllocationStrategy.getNextAvailableParkingSlot(anyList())).thenReturn(expectedParkingSlots.get(0));
		parkingSlotManager.park(carToBeParked);

		ArgumentCaptor<List> parkingSlotsCaptor = ArgumentCaptor.forClass(List.class);
		verify(parkingLotAllocationStrategy).getNextAvailableParkingSlot(parkingSlotsCaptor.capture());
		List<ParkingSlot> parkingSlots = parkingSlotsCaptor.getValue();
		assertEquals(expectedParkingSlots, parkingSlots);
		assertEquals(carToBeParked.getRegistrationNumber(), expectedParkingSlots.get(0).getParkedCarRegistrationNumber());
	}

	@Test
	public void shouldThrowParkingLotUnAvailableExceptionWhenParkingLotIsUnavailable() {
		ParkingSlotManager parkingSlotManager = new ParkingSlotManager(parkingLotAllocationStrategy, ticketManager, 1);
		Car carToBeParked = new Car("abc", Car.Color.RED);
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		when(parkingLotAllocationStrategy.getNextAvailableParkingSlot(anyList())).thenReturn(expectedParkingSlots.get(0));
		try {
			parkingSlotManager.park(carToBeParked);
			parkingSlotManager.park(carToBeParked);
		} catch (ParkingLotException ex) {
			assertEquals("No Free Slot is Available", ex.getMessage());
		}
	}

	@Test
	public void shouldThrowParkingLotUnAvailableExceptionWhenParkingSLotsIsZero() {
		ParkingSlotManager parkingSlotManager = new ParkingSlotManager(parkingLotAllocationStrategy, ticketManager, 0);
		Car carToBeParked = new Car("abc", Car.Color.RED);
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		when(parkingLotAllocationStrategy.getNextAvailableParkingSlot(anyList())).thenReturn(expectedParkingSlots.get(0));
		try {
			parkingSlotManager.park(carToBeParked);
		} catch (ParkingLotException ex) {
			assertEquals("Invalid Range on creating Parking slots", ex.getMessage());
		}
	}

	@Test
	public void shouldGetTheParkingSlotDetailsFromAbstractSearchParkingSlot() {
		List<ParkingSlot> slots = Collections.singletonList(new ParkingSlot(4, ParkingSlot.State.FREE));
		when(abstractSearchParkingSlot.search(any(), anyList())).thenReturn(slots);

		List<ParkingSlot> retrievedParkingSlot = parkingSlotManager.searchParkingSlot("get_by_color", "abc");
		ArgumentCaptor<Object> searchParam = ArgumentCaptor.forClass(Object.class);
		ArgumentCaptor<List> parkingSlots = ArgumentCaptor.forClass(List.class);
		verify(abstractSearchParkingSlot).search(searchParam.capture(), parkingSlots.capture());

		assertEquals("abc",searchParam.getValue());
		assertFalse(parkingSlots.getValue().isEmpty());
		assertEquals(slots, retrievedParkingSlot);
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenGetTheParkingSlotDetailsFromAbstractSearchParkingSlotIsNotPresent() {
		when(abstractSearchParkingSlot.search(any(), anyList())).thenReturn(new ArrayList<>());

		try {
			parkingSlotManager.searchParkingSlot("get_by_colorx", "");
			verify(abstractSearchParkingSlot, never()).search(any(), any());
		} catch (ParkingLotException ex) {
			assertEquals("Parking slot not found", ex.getMessage());
		}
	}

	@Test
	public void shouldThrowParkingLotExceptionSearchCommandIsNotPresent() {
		try {
			parkingSlotManager.searchParkingSlot("abc", "");
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Search Command", exception.getMessage());
		}
	}
}
