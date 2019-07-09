package com.ee.parkinglot.service;

import com.ee.parkinglot.bean.Status;
import com.ee.parkinglot.manager.ParkingSlotManager;
import com.ee.parkinglot.model.Car;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ParkingLotServiceTest {
	@Mock
	private ParkingSlotManager parkingSlotManager;

	private ParkingLotService parkingLotService;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		this.parkingLotService = new ParkingLotService(parkingSlotManager);
	}

	@Test
	public void shouldInvokeParkingLotMangerOnParkingTheCar() {
		Car car = new Car("regn-number", Car.Color.White);
		parkingLotService.park(car);

		verify(parkingSlotManager).park(car);
	}

	@Test
	public void shouldInvokeParkingLotMangerOnUnParkingTheCar() {
		parkingLotService.unPark(4);

		verify(parkingSlotManager).unPark(4);
	}

	@Test
	public void shouldInvokeParkingLotMangerOnSearchingSlots() {
		parkingLotService.search("cmd", "sss");

		verify(parkingSlotManager).searchParkingSlot("cmd", "sss");
	}

	@Test
	public void shouldInvokeParkingLotMangerToCreateParkingSlots() {
		parkingLotService.createParkingLot(4);

		verify(parkingSlotManager).createParkingLot(4);
	}

	@Test
	public void shouldInvokeParkingLotMangerToGetParkingLotStatus() {
		ArrayList<Status> expectedStatusList = new ArrayList<>();
		when(parkingSlotManager.status()).thenReturn(expectedStatusList);
		List<Status> statuses = parkingLotService.status();

		verify(parkingSlotManager).status();
		assertEquals(expectedStatusList,statuses);
	}
}