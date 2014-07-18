package com.example.freeuni.final_project;

import java.util.Date;
import java.util.Timer;

import com.example.freeuni.final_project.listeners.SpeedUpListener;
import com.example.freeuni.final_project.model.CarPhysics;
import com.example.freeuni.final_project.model.DashedView;
import com.example.freeuni.final_project.model.State;
import com.example.freeuni.final_project.model.StateManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Time;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private static final int SLEEP_INTERVAL = 33;
	private RelativeLayout layout;
	private View rightLine;
	private View leftLine;
	
	private DashedView line;
	private RelativeLayout panel;
	
	private Button left_wheel;
	private Button right_wheel;
	
	private ImageView car;
	
	private State state;
	private SpeedUpListener listener= null;
	
	private boolean firstClick = true;
	
	//private double movement = 100;
	
	private boolean leftClick = true;
	private boolean rightClick = true;
	
	private StateManager stateManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        state = new State(10, 0);
        stateManager = new StateManager();
        listener  = (SpeedUpListener) getApplication();
        Button chooseYourCar = new Button(this);
        chooseYourCar.setText("choose your car");
        chooseYourCar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent in = new Intent(getBaseContext(), ChooseYourCarActivity.class);
				
				
				//in.putExtra("Name", clickedCategory.getCategoryName());
				//App.transactionApater.setCategory(clickedCategory.getCategoryName());
				//App.transactionApater.refresh();
				startActivity(in);
			}
		});
        layout.addView(chooseYourCar);
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	
	 private void initView() {
	    	layout = (RelativeLayout) findViewById(R.id.road);
	        layout.setBackgroundColor(Color.DKGRAY);
	        
	        rightLine = findViewById(R.id.rightLine);
	        leftLine = findViewById(R.id.leftLine);
	       
	        rightLine.setBackgroundColor(Color.WHITE);
	        leftLine.setBackgroundColor(Color.WHITE);
	     
	        car = (ImageView) findViewById(R.id.my_car);
	        line = (DashedView) findViewById(R.id.line);
	        initLine();
	        initPanel();
	 }

	private void initLine() {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) line.getLayoutParams();
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		int height = size.y;
		params.topMargin = 0 - height;
		params.bottomMargin = 0;
		params.height = height*2;
		line.setLayoutParams(params);
	}

	private void initPanel() {
		panel = (RelativeLayout) findViewById(R.id.steering_wheel);
        
        left_wheel = (Button) findViewById(R.id.left_wheel);
        
        left_wheel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(leftClick == true){
					
				
					leftClick = false;
					rightClick = true;;
					long now  = new Date().getTime();
					stateManager.setAccTimeOutPoint(now + 400);
					
					if(firstClick){
					//	movement = 100;
						continueMoving();
						firstClick = false;
					}
//					}else{
//						speedUp();
//					}
				}
			}
		});
        
        right_wheel = (Button) findViewById(R.id.right_wheel);
        
        right_wheel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(rightClick == true){
					
					leftClick = true;
					rightClick = false;
					long now  =new Date().getTime();
					stateManager.setAccTimeOutPoint(now + 400);
					
					if(firstClick){
					//	movement = 100;
						continueMoving();
						firstClick = false;
					}
//					}else{
//						speedUp();
//					}
				}
			}
		});

	}

	protected void speedUp() {
		
		listener.speedUpListner();
	//	changeMovement(10);
//		movement += 100;
		
	}
	
	private synchronized void changeMovement(int change){
	//	movement += change;
	}

	private int res = 0;
	private void continueMoving(){
	
		
		final Handler handler = new Handler();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(SLEEP_INTERVAL);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							res = moveCar();
							System.out.println("res " + res);
						}
					});
					if(res == -1){
						firstClick = true;
						
						break;
					}
				}
			}
		}).start();
	}
	
	
	
//	private double getMovement(){
//		changeMovement(-10);
//		return movement;
//	}
	
	
	private CarPhysics carPhysics = new CarPhysics();
	private int moveCar(){
//		long now  = new Date().getTime();
//		if(now > stateManager.getAccTimeOutPoint()){
//			stateManager.setAcceleration(stateManager.getAcceleration() - 1);
//		}else{
//			double diff = stateManager.getAccTimeOutPoint() - now;
//			double change = stateManager.getAcceleration() + 10*((400-diff)/400);
//			//double change = 20;
//			if(change >= 20) change = 20;
//			stateManager.setAcceleration(change);
	//	}
		
		
//		long dt;
//		long prev = stateManager.getPreviousCallTime();
//		if(prev == 0) dt = 33;
//		else dt =  new Date().getTime() - prev;
		
		//dt = dt/1000;
		//double dtdouble = dt/1000;
		
		//System.out.println(dtdouble);
	//	System.out.println("dt " + dt + "  acc "+ stateManager.getAcceleration());
		
		
//		double currMovement = dt*state.getVelocity() + dt*dt*stateManager.getAcceleration()/2;
		
		double currMovement = carPhysics.CalculateYPosition() ;
	
	
		//double currMovement = dt*state.getVelocity()/1000 + dt*dt*stateManager.getAcceleration()/1000000;
		
		//currMovement = 100;
	//	System.out.println(state.getVelocity());
		System.out.println("movement " + currMovement);
		
		//if(currMovement <= 0) return -1;
		state.setyCoord(state.getyCoord() + currMovement);
		
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) line.getLayoutParams();
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		params.topMargin += currMovement;
		params.bottomMargin -= currMovement;
		if(params.topMargin >= 0){
			
			display.getSize(size);
			
			int height = size.y;
			params.topMargin = 0 - height;
		}
		stateManager.setPreviousCallTime(new Date().getTime());
		line.setLayoutParams(params);

		return 1;
	}
	
	
}
