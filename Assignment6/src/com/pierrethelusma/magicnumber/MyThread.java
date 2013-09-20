package com.pierrethelusma.magicnumber;

public class MyThread
{
	private int id;
	private String name;
	private int randomNumber;
	
	public MyThread(int id, String name, int randomNumber)
	{
		this.id = id;
		this.name = name;
		this.randomNumber = randomNumber;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getRandomNumber() {
		return randomNumber;
	}
	
}