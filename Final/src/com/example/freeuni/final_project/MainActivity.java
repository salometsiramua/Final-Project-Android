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
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {

	private static final int SLEEP_INTERVAL = 33;
	private static final int DECREASE_INTERVAL = 1000;
	private RelativeLayout layout;
	private View rightLine;
	private View leftLine;
	
	private DashedView line;
	private RelativeLayout panel;
	
	private Button left_wheel;
	private Button right_wheel;
	
	private ImageView myCar;
	private ImageView theirCar;
	
	private State state;
	private SpeedUpListener listener= null;
	
	private boolean firstClick = true;
	
	//private double movement = 100;
	
	private boolean leftClick = true;
	private boolean rightClick = true;
	
	private StateManager stateManager;
	
	private CarPhysics myCarPhysics = new CarPhysics();
	private CarPhysics theirCarPhysics;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        state = new State(10, 0);
        stateManager = new StateManager();
        listener  = (SpeedUpListener) getApplication();
        theirCarPhysics =  new CarPhysics();
        theirCarPhysics.changeVelocityY(200);
        
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
	     
	        myCar = (ImageView) findViewById(R.id.my_car);
	        theirCar = (ImageView)findViewById(R.id.their_car);
	        line = (DashedView) findViewById(R.id.line);
	        initLine();
	        initPanel();
	        
	        Button chooseYourCar = new Button(this);
	        chooseYourCar.setText("choose your car");
	        chooseYourCar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(getBaseContext(), ChooseYourCarActivity.class));
				}
			});
	        layout.addView(chooseYourCar);
	 }

	private void initLine() {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) line.getLayoutParams();
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		int height = size.y;
		params.topMargin = 0 - height;
		params.bottomMargin = 0;
//		params.bottomMargin = -2147483648;
		params.height = height * 2;
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
					
					changeMovement();
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
					
					changeMovement();
				}
			}
		});

	}
	
	
	private void changeMovement(){
		myCarPhysics.increaseVelocity();
		
		if(firstClick){
			
			continueMoving();
			decreaseVelocityThread();
			firstClick = false;
		}
	}
	

	protected void decreaseVelocityThread() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(DECREASE_INTERVAL);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
					if(myCarPhysics.isMoving()){
						myCarPhysics.changeVelocityY(-50);
					}else{
						firstClick = true;
						myCarPhysics.setLastClickTimeZero();
						break;
					}
					
				}
			}
		}).start();
	}
	

	protected void speedUp() {
		
		listener.speedUpListner();

	}
	
	
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
							moveCar();
						}
					});
					if(!myCarPhysics.isMoving()){
						firstClick = true;
						myCarPhysics.setLastClickTimeZero();
						break;
					}
				}
			}
		}).start();
	}
	

	
	private void moveCar(){

		double currMovement = myCarPhysics.CalculateYPositionChange() ;
	
		double theirMovement = theirCarPhysics.CalculateYPositionChange();
		System.out.println("their movement: " + theirMovement);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) line.getLayoutParams();
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		params.setMargins(params.leftMargin, (params.topMargin + (int)(currMovement)), params.rightMargin, (params.bottomMargin - (int)(currMovement)));
	    
//		params.topMargin += currMovement;
//		params.bottomMargin -= currMovement;
		
		LayoutParams layoutParams = (LayoutParams) theirCar.getLayoutParams();
	    layoutParams.setMargins(layoutParams.leftMargin, (layoutParams.topMargin + (int)(currMovement-theirMovement)), layoutParams.rightMargin, (layoutParams.bottomMargin - (int)(currMovement - theirMovement)));
	    
	    theirCar.setLayoutParams(layoutParams);
	    
		if(params.topMargin >= 0){
			
			display.getSize(size);
			
			int height = size.y;
			params.topMargin = 0 - height;
			params.bottomMargin = 0;
		}
		
		line.setLayoutParams(params);

	}
	
	
}
