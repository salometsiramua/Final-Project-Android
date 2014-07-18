package com.example.freeuni.final_project.model;

import java.util.Date;

import android.text.format.Time;

public class CarPhysics {

	private float positionYchange = 0;
	private float positionY = 0;
	private float velocityY = 100;
	private float accelerationY = 10;


	public void setAccelerationY(float accelerationY) {
		this.accelerationY = accelerationY;
	}
	
	private float dt = 0.0F;
	private long previousCallTimeStamp = 0;

	public  float CalculateYPosition()
	{
		long now  = new Date().getTime();
		
		this.dt = ((now - this.previousCallTimeStamp ) / 1000.0F);
		
		this.positionYchange = dt * (this.velocityY + dt * this.accelerationY / 2.0F);
		this.positionY += positionYchange;
		
		System.out.println("dt: " + dt);
		this.previousCallTimeStamp = now;
		
		return positionYchange;
	}
	
	

}
