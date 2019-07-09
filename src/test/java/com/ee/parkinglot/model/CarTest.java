package com.ee.parkinglot.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {

	private Car car;

	@Before
	public void setUp() throws Exception {
		car = new Car("registration_number", Car.Color.White);
	}

	@Test
	public void shouldReturnCarDetailsForACarAsExpected() {
		assertEquals("registration_number", car.getRegistrationNumber());
		assertEquals(Car.Color.White, car.getColor());

		car = new Car("registration_number2", Car.Color.Red);
		assertEquals("registration_number2", car.getRegistrationNumber());
		assertEquals(Car.Color.Red, car.getColor());
	}
}
