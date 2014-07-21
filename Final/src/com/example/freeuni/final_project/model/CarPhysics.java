package com.example.freeuni.final_project.model;

import java.util.Date;
import java.util.Random;

import com.example.freeuni.final_project.listeners.SpeedChangeListener;

import android.content.res.Resources.Theme;
import android.text.format.Time;

public class CarPhysics {

	private static final int CLICK_INTERVAL = 500;
	private static final float MAX_VELOCITY = 1000;
	private static final float FINISH_COORD = 1000;
	private static final int DECREASE_INTERVAL = 1000;
	
	private float positionYchange = 0;
	private float positionY = 0;
	private float velocityY = 0;
	int winner = -1;
	//private float accelerationY = 10;

	private boolean finished = false;
	
	public SpeedChangeListener speedChangeListener;
	
	private long lastClickTime = 0;
	
	
	public synchronized void changeVelocityY(float velocityY) {
		if(this.velocityY+velocityY < 0) this.velocityY = 0;
		else this.velocityY += velocityY;
		if(speedChangeListener != null){
			speedChangeListener.speedChanged(this.velocityY);
			
			
		}
		System.out.println(this.velocityY);
		
	}
	public  void startComputerPlaying(){
		new Thread(new Runnable() {
			
			
			@Override
			public void run() {
				try {
					Thread.sleep(3*DECREASE_INTERVAL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				while(true){
					Random r = new Random();
					float velocity =-50+r.nextFloat()*(200+50);
					//long timeForSleep = (long) ((CLICK_INTERVAL+CLICK_INTERVAL)*f);
				
					try {
						Thread.sleep(DECREASE_INTERVAL);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("vlooo " +velocityY);
					if(velocityY + velocity > MAX_VELOCITY) changeVelocityY(MAX_VELOCITY - velocityY);
					else changeVelocityY(velocity);
				}
				
			}
		}).start();
		
	}
	public boolean isMoving(){
		if(velocityY <= 0){
			velocityY = 0;
			return false;
		}
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
		
		if(this.positionY >= FINISH_COORD){
			this.setWinner();
			if(speedChangeListener != null && finished == false){
				speedChangeListener.finishLineCrossed();
//				speedChangeListener = null;
				finished = true;
				
			}
		}
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
			System.out.println("vel "+velocityY);
		}
		lastClickTime = now + CLICK_INTERVAL;
	}

	public void setLastClickTimeZero() {
		lastClickTime = 0;
		
	}

	public void setVelocityY(Float speed) {
		this.velocityY = speed;
		
	}
	public void setWinner() {
		winner = 1;
		
	}
	public boolean isWinner(){
		if(winner==1) return true;
		return false;
	}
	

}
