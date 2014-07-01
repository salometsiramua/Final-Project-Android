package com.example.freeuni.final_project.model;

public class State {
	
	private double velocity;
	private double yCoord;
	
	
	public State(double velocity,double yCoord){
		
		this.velocity = velocity;
		this.yCoord = yCoord;
	}
	public double getVelocity() {
		return velocity;
	}
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	public double getyCoord() {
		return yCoord;
	}
	public void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}
	

}
