package com.ee.parkinglot.model;

public class Car {
	private String registrationNumber;

	private Color color;

	public Car(String registrationNumber, Color color) {
		this.registrationNumber = registrationNumber;
		this.color = color;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public Color getColor() {
		return color;
	}

	public enum Color {
		Red, White, Black, Blue
	}
}
