package com.example.freeuni.final_project;

import java.util.Timer;

import com.example.freeuni.final_project.listeners.SpeedUpListener;
import com.example.freeuni.final_project.model.DashedView;
import com.example.freeuni.final_project.model.State;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Time;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements SpeedUpListener {

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
	
	private double movement = 100;
	
	private boolean leftClick = true;
	private boolean rightClick = true;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        state = new State(0, 0);
        listener  = (SpeedUpListener) getApplication();
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
					speedUp();
				
					leftClick = false;
					rightClick = true;;
					
					
					if(firstClick){
						movement = 100;
						continueMoving();
						firstClick = false;
					}
				}
			}
		});
        
        right_wheel = (Button) findViewById(R.id.right_wheel);
        
        right_wheel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(rightClick == true){
					speedUp();
				
					leftClick = true;
					rightClick = false;
					
					
					if(firstClick){
						movement = 100;
						continueMoving();
						firstClick = false;
					}
				}
			}
		});

	}

	protected void speedUp() {
		
		listener.speedUpListner();

		
	}

	private int res = 0;
	private void continueMoving(){
	
		
		final Handler handler = new Handler();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							res = moveCar();
							
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
	
	@Override
	public void speedUpListner() {
		speedUp();
	}
	
	private double getMovement(){
		movement -= 1;
		return movement;
	}
	private int moveCar(){
		
		double currMovement = getMovement();
		if(currMovement <= 0) return -1;
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
		
		line.setLayoutParams(params);
		return 1;
		
	}
	
	
}
