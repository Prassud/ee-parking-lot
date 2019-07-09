package com.ee.parkinglot;

import com.ee.parkinglot.utils.MessageConstant;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
	@Test
	public void shouldTestApplicationWideScenarios() throws IOException {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		String file = getClass().getClassLoader().getResource("integration_test.txt").getFile();
		System.setOut(new PrintStream(outContent));
		ParkingLotApplication.main(new String[] {MessageConstant.PROCESS_INPUT_FILE, file});

		String actuals = new String(outContent.toByteArray());
		assertEquals("Created a parking lot with 6 slots\n" +
				"Allocated slot number: 1\n" +
				"Allocated slot number: 2\n" +
				"Allocated slot number: 3\n" +
				"Allocated slot number: 4\n" +
				"Allocated slot number: 5\n" +
				"Allocated slot number: 6\n" +
				"Slot Number 4 is free\n" +
				"Slot No. Registration No Colour\n" +
				"1         KA-01-HH-1234  White  \n" +
				"2         KA-01-HH-9999  White  \n" +
				"3         KA-01-BB-0001  Black  \n" +
				"5         KA-01-HH-2701  Blue   \n" +
				"6         KA-01-HH-3141  Black  \n" +
				"Allocated slot number: 4\n" +
				"Sorry, parking lot is full\n" +
				"KA-01-HH-1234,KA-01-HH-9999,KA-01-P-333\n" +
				"1,2,4\n" +
				"KA-01-HH-3141\n" +
				"Not found\n" , actuals);

	}
}
