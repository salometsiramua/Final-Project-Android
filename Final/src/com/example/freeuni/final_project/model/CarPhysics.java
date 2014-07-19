package com.example.freeuni.final_project.model;

import java.util.Date;

import android.text.format.Time;

public class CarPhysics {

	private static final int CLICK_INTERVAL = 500;
	private static final float MAX_VELOCITY = 1000;
	
	private float positionYchange = 0;
	private float positionY = 0;
	private float velocityY = 0;
	//private float accelerationY = 10;

	private long lastClickTime = 0;
	
	
	public synchronized void changeVelocityY(float velocityY) {
		this.velocityY += velocityY;
		
		System.out.println(this.velocityY);
		
	}
	
	public boolean isMoving(){
		if(velocityY <= 0) return false;
		else return true;
	}
	
	private float dt = 0.0F;
	private long previousCallTimeStamp = 0;

	public  float CalculateYPositionChange()
	{
		long now  = new Date().getTime();
		
		if(previousCallTimeStamp == 0){
			this.dt = ((now - (now - 33.0F) ) / 1000.0F);
		}else 
		
			this.dt = ((now - this.previousCallTimeStamp ) / 1000.0F);
		
		
		
		this.positionYchange = dt * this.velocityY;
		this.positionY += positionYchange;
		
		this.previousCallTimeStamp = now;
		
		return positionYchange;
	}

	public void increaseVelocity() {
		long now  = new Date().getTime();
		if(lastClickTime == 0){
			System.out.println("Aq shemovida");
			changeVelocityY(100);
		}else{
			long diff = lastClickTime - now;
			if(diff<0){
				System.out.println("Awdsf");
				lastClickTime = now + CLICK_INTERVAL;
				return;
			}
			float velocityChange = velocityY * (CLICK_INTERVAL - diff)/CLICK_INTERVAL;
			if(velocityChange + velocityY > MAX_VELOCITY){
				velocityChange = MAX_VELOCITY - velocityY;
			}
			changeVelocityY(velocityChange);
			
		}
		lastClickTime = now+CLICK_INTERVAL;
	}

	public void setLastClickTimeZero() {
		lastClickTime = 0;
		
	}
	
	

}
