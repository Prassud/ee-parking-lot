package com.ee.parkinglot.io;

import com.ee.parkinglot.command.AbstractCommand;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class ConsoleInputProcessor extends AbstractInputProcessor {

	public ConsoleInputProcessor(Map<String, AbstractCommand> commands) {
		super(commands);
	}

	@Override
	public void execute(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Invalid Input file Provided");
		}
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String input;
		while ((input = bufferedReader.readLine()) != null && !"exit".equals(input)) {
			String[] inputParams = input.split(" ");
			process(inputParams);
		}
	}
}
