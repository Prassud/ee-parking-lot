package com.ee.parkinglot.processor;

import com.ee.parkinglot.allocation.strategy.ParkingLotAllocationStrategy;
import com.ee.parkinglot.exception.ParkingLotException;
import com.ee.parkinglot.model.Car;
import com.ee.parkinglot.model.ParkingSlot;
import com.ee.parkinglot.model.Ticket;
import com.ee.parkinglot.ticketing.TicketManager;
import com.ee.parkinglot.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class
ParkingSlotProcessorTest {

	@Mock
	private ParkingLotAllocationStrategy parkingLotAllocationStrategy;

	@Mock
	private TicketManager ticketManager;

	private ParkingLotProcessor parkingLotProcessor;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		parkingLotProcessor = new ParkingLotProcessor(parkingLotAllocationStrategy, ticketManager, 10);
	}

	@Test
	public void shouldCallParkingLotAllocationStrategyToGetNextAvailableTicket() {
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		Car carToBeParked = new Car("abc", Car.Color.RED);
		Ticket expectedTicket = new Ticket(expectedParkingSlots.get(0), carToBeParked);
		when(parkingLotAllocationStrategy.getNextAvailableParkingSlot(anyList())).thenReturn(expectedParkingSlots.get(0));
		when(ticketManager.issueTicket(expectedParkingSlots.get(0), carToBeParked)).thenReturn(expectedTicket);
		Ticket ticket = parkingLotProcessor.processParking(carToBeParked);

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
		parkingLotProcessor.processParking(carToBeParked);

		ArgumentCaptor<List> parkingSlotsCaptor = ArgumentCaptor.forClass(List.class);
		verify(parkingLotAllocationStrategy).getNextAvailableParkingSlot(parkingSlotsCaptor.capture());
		List<ParkingSlot> parkingSlots = parkingSlotsCaptor.getValue();
		assertEquals(expectedParkingSlots, parkingSlots);
	}

	@Test(expected = ParkingLotException.class)
	public void shouldThrowParkingLotUnAvailableExceptionWhenParkingLotIsUnavailable() {
		Car carToBeParked = new Car("abc", Car.Color.RED);
		List<ParkingSlot> expectedParkingSlots = TestUtils.createMultipleFreeParkingLots(10);
		when(parkingLotAllocationStrategy.getNextAvailableParkingSlot(anyList())).thenReturn(expectedParkingSlots.get(0));
		parkingLotProcessor.processParking(carToBeParked);
	}
}
