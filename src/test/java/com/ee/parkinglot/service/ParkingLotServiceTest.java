package com.ee.parkinglot.service;

import com.ee.parkinglot.manager.ParkingSlotManager;
import com.ee.parkinglot.model.Car;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class ParkingSlotServiceTest {
	@Mock
	private ParkingSlotManager parkingSlotManager;

	private ParkingSlotService parkingSlotService;

	@Before
	public void setUp() throws Exception {
		this.parkingSlotService = new ParkingSlotService(parkingSlotManager);
	}

	@Test
	public void shouldInvokeParkingLotMangerOnParkingTheCar() {
		Car car = new Car("regn-number", Car.Color.WHITE);
		parkingSlotService.park(car);
		verify(parkingSlotManager).park(car);
	}
}