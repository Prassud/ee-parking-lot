package com.ee.parkinglot.utils;

public interface MessageConstant {
	String PARK_COMMAND_NAME = "park";
	String LEAVE_COMMAND_NAME = "leave";
	String STATUS_COMMAND_NAME = "status";
	String SEARCH_BY_REGISTER_NUMBER = "registration_numbers_for_cars_with_colour";
	String SEARCH_BY_COLOR_FOR_SN = "slot_numbers_for_cars_with_colour";
	String SEARCH_BY_RN_FOR_SN = "slot_number_for_registration_number";
	String CREATE_COMMAND = "create_parking_lot";

	String GET_SLOT_BY_COLOR = "GET_SLOT_BY_COLOR";
	String GET_SLOT_BY_RN = "GET_SLOT_BY_RN";
	String PROCESS_INPUT_FILE = "process_from_file";
	String PROCESS_CONSOLE_COMMMANDS = "process_from_CONSOLE";
}
