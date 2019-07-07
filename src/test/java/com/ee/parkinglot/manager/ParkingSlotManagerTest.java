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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
		Ticket ticket = parkingSlotManager.processParking(carToBeParked);

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
		parkingSlotManager.processParking(carToBeParked);

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
			parkingSlotManager.processParking(carToBeParked);
			parkingSlotManager.processParking(carToBeParked);
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
			parkingSlotManager.processParking(carToBeParked);
		} catch (ParkingLotException ex) {
			assertEquals("Invalid Range on creating Parking slots", ex.getMessage());
		}
	}

	@Test
	public void shouldGetTheParkingSlotDetailsFromAbstractSearchParkingSlot() {
		ParkingSlot parkingSlot = new ParkingSlot(4, ParkingSlot.State.FREE);
		when(abstractSearchParkingSlot.search(any())).thenReturn(parkingSlot);

		ParkingSlot retrievedParkingSlot = parkingSlotManager.searchParkingSlot("get_by_color", "abc");
		verify(abstractSearchParkingSlot).search("abc");

		assertEquals(parkingSlot, retrievedParkingSlot);
	}

	@Test
	public void shouldThrowParkingLotExceptionWhenGetTheParkingSlotDetailsFromAbstractSearchParkingSlotIsNotPresent() {
		when(abstractSearchParkingSlot.search(any())).thenReturn(null);

		try {
			parkingSlotManager.searchParkingSlot("abc", "");
			verify(abstractSearchParkingSlot).search("abc");
		} catch (ParkingLotException ex) {
			assertEquals("Parking slot not found", ex.getMessage());
		}
	}

	@Test(expected = ParkingLotException.class)
	public void shouldThrowParkingLotExceptionSearchCommandIsNotPresent() {
		ParkingSlot parkingSlot = new ParkingSlot(4, ParkingSlot.State.FREE);
		try {
			parkingSlotManager.searchParkingSlot("abc", "");
		} catch (ParkingLotException exception) {
			assertEquals("Invalid Search Command", exception.getMessage());
		}
	}

	@Test(expected = ParkingLotException.class)
	public void shouldThrowExceptionGetTheParkingSlotDetailsBasedOnCarRegistrationNumberIsNotPresent() {
		ParkingSlot parkingSlot = new ParkingSlot(4, ParkingSlot.State.FREE);
		Car carToBeParked = new Car("abc", Car.Color.RED);
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		when(parkingLotAllocationStrategy.getNextAvailableParkingSlot(anyList())).thenReturn(expectedParkingSlots.get(0));
		parkingSlotManager.processParking(carToBeParked);

		ParkingSlot retrievedParkingSlot = parkingSlotManager.getAllocatedParkingSlotByCarRegistrationNumber("abc123");
		assertEquals(parkingSlot, retrievedParkingSlot);
	}

}
