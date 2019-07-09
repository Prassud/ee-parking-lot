package com.ee.parkinglot;

import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Test;

import java.io.IOException;

public class IntegrationTest {
	@Test
	public void shouldTestApplicationWideScenarios() throws IOException {
		String file = getClass().getClassLoader().getResource("integration_test.txt").getFile();
		ParkingLotApplication.main(new String[] {MessageConstant.PROCESS_INPUT_FILE, file});
	}
}
