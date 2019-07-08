package com.ee.parkinglot.bean;

public class Result {

	private String message;

	private boolean isSuccess;

	public Result(String message, boolean isSuccess) {
		this.message = message;
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}
}
