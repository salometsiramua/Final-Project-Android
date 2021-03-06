package com.example.freeuni.final_project.activities;

import java.util.Date;
import java.util.Timer;

import javax.print.attribute.standard.MediaSize.Other;
import javax.xml.soap.Text;

import com.example.freeuni.final_project.App;
import com.example.freeuni.final_project.R;
import com.example.freeuni.final_project.activities.RestartGameDialog.RestartGameListener;
import com.example.freeuni.final_project.listeners.SpeedChangeListener;
import com.example.freeuni.final_project.listeners.SpeedUpListener;
import com.example.freeuni.final_project.model.CarPhysics;
import com.example.freeuni.final_project.model.DashedView;
import com.example.freeuni.final_project.model.State;
import com.example.freeuni.final_project.model.StateManager;

import android.R.color;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SpeedChangeListener, RestartGameListener{

	private static final int SLEEP_INTERVAL = 33;
	private static final int DECREASE_INTERVAL = 1000;
	private RelativeLayout layout;
	private View rightLine;
	private View leftLine;
	
	private App app;
	private DashedView line;
	private RelativeLayout panel;
	
	private Button left_wheel;
	private Button right_wheel;
	
	private ImageView myCar;
	private ImageView theirCar;
	private TextView mySpeed;
	private State state;
	private SpeedUpListener listener= null;
	
	private boolean firstClick = true;
	
	//private double movement = 100;
	
	private boolean leftClick = true;
	private boolean rightClick = true;
	
	private StateManager stateManager;
	private CarPhysics myCarPhysics;
	private CarPhysics theirCarPhysics;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();
        Bundle extras = getIntent().getExtras();
        String oneOrTwo =extras.getString("one_or_two");
        setContentView(R.layout.activity_main);
        initView();
        state = new State(10, 0);
        stateManager = new StateManager();
        if(oneOrTwo.equals("one")) listener = null;
        else listener  = (SpeedUpListener) getApplication();
       
        myCarPhysics = app.getMyCarPhysics();
        myCarPhysics.speedChangeListener = this;
        theirCarPhysics = app.getTheirCarPhysics();
      
        Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Go!", Toast.LENGTH_SHORT).show();
        try {
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(oneOrTwo.equals("one")) theirCarPhysics.startComputerPlaying();
        continueMoving();
        
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

	       // myCar.setImageResource(R.drawable.purple_car);
	        
	        myCar.setImageResource(app.getMyCarImageId());
	        
	        System.out.println("id dabechdos: " + app.getMyCarImageId());
	        theirCar = (ImageView)findViewById(R.id.their_car);
	        line = (DashedView) findViewById(R.id.line);
	        initLine();
	        initPanel();
	        
	       
	        
	        mySpeed = (TextView)findViewById(R.id.speedometer);
	        mySpeed.setBackgroundColor(Color.argb(100, 255, 255, 255));
	        LayoutParams params = (LayoutParams) mySpeed.getLayoutParams();
	        params.setMargins(700, 10, 20, 300);
	        mySpeed.setLayoutParams(params);
	        mySpeed.setText("speed: 0");
//	        mySpeed.
	        
	        mySpeed.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//	        layout.addView(mySpeed);
	        
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
				
			//	if(rightClick == true){
				
					leftClick = true;
					rightClick = false;
					
					changeMovement();
				//}
			}
		});

	}
	
	
	private void changeMovement(){
		myCarPhysics.increaseVelocity();
		
		if(firstClick){
			
			
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
					
					
					
						myCarPhysics.changeVelocityY(-50);
						if(!myCarPhysics.isMoving()){
							firstClick = true;
							myCarPhysics.setLastClickTimeZero();
							break;
						}
					
				}
			}
		}).start();
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
					if(!myCarPhysics.isMoving()){//&&!theirCarPhysics.isMoving()){
						firstClick = true;
						myCarPhysics.setLastClickTimeZero();
						//break;
					}
					
					if(myCarPhysics.isWinner()){
						finishGame("Congratulations! You won! :)");
						break;
					}else if (theirCarPhysics.isWinner()){
						finishGame("Sorry, you lost :(");
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

	private Handler handler = new Handler();
	@Override
	public void speedChanged(final float currSpeed) {
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				mySpeed.setText("speed: "+ currSpeed);
				if(listener!=null) listener.speedUpListner(currSpeed);
			}
		});
		
	}

	public void finishGame(final String toast){
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
				
			}
		});
		
		new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	//				startActivity(new Intent(getBaseContext(), StartGameActivity.class));
					RestartGameDialog dialog = new RestartGameDialog();
					
					dialog.listener = (RestartGameListener)MainActivity.this;
					dialog.setCancelable(false);
					dialog.show(getFragmentManager(), "");
	
				}
			}).start();
	}
	@Override
	public void finishLineCrossed() {
		
		myCarPhysics.setWinner();
		if(listener!=null) listener.finishedPlaying(App.PLAYER_WON);
		leftClick = false;
		rightClick = false;
		
	
		
		
	}

	@Override
	public void setOnAnswerListener(String answer) {
		if(answer.equals("Exit")){
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			System.exit(0);
		}else{
			android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
		}
	}
	@Override
	public void onBackPressed() {
		if(listener!=null) listener.finishedPlaying(App.PLAYER_LOST);
		android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
	}
	
}
