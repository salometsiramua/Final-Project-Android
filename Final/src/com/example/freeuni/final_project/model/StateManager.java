package com.example.freeuni.final_project.model;

import java.util.Date;

public class StateManager {
	private long TimeInMiliSeconds;
	private long accTimeOutPoint;
	private double acceleration;
	private State myState;
	private long previousCallTime=0;
	
	public StateManager(){
		Date d  = new Date();
		TimeInMiliSeconds = d.getTime();
		accTimeOutPoint  =0;
		myState = new State(0,0);
		acceleration = 10;
		
		
	}
	public long getTimeInMiliSeconds() {
		return TimeInMiliSeconds;
	}
	public void setTimeInMiliSeconds(long timeInMiliSeconds) {
		TimeInMiliSeconds = timeInMiliSeconds;
	}
	public long getAccTimeOutPoint() {
		return accTimeOutPoint;
	}
	public void setAccTimeOutPoint(long accTimeOutPoint) {
		this.accTimeOutPoint = accTimeOutPoint;
	}
	public double getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	public State getMyState() {
		return myState;
	}
	public void setMyState(State myState) {
		this.myState = myState;
	}
	public long getPreviousCallTime() {
		return previousCallTime;
	}
	public void setPreviousCallTime(long previousCallTime) {
		this.previousCallTime = previousCallTime;
	}
	
	
	
}
