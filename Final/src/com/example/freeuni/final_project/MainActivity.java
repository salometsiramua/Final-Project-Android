package com.example.freeuni.final_project;

import com.example.freeuni.final_project.listeners.SpeedUpListener;
import com.example.freeuni.final_project.model.State;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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
	private RelativeLayout panel;
	
	private Button left_wheel;
	private Button right_wheel;
	
	private ImageView car;
	
	private State state;
	private SpeedUpListener listener= null;
	
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
	        initPanel();
	 }

	private void initPanel() {
		panel = (RelativeLayout) findViewById(R.id.steering_wheel);
        
        left_wheel = (Button) findViewById(R.id.left_wheel);
        
        left_wheel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("daechira left");
				speedUp();
				left_wheel.setEnabled(false);
				right_wheel.setEnabled(true);
			}
		});
        
        right_wheel = (Button) findViewById(R.id.right_wheel);
        
        right_wheel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("daechira right");
				speedUp();
				left_wheel.setEnabled(true);
				right_wheel.setEnabled(false);
			}
		});

	}

	protected void speedUp() {
		listener.speedUpListner();
		state.setVelocity(state.getVelocity() + 1);
		
		state.setyCoord(state.getyCoord() + 1);
		
		

		System.out.println("v: " + state.getVelocity()  + " coord: " 
							+ state.getyCoord());
	}

	@Override
	public void speedUpListner() {
		speedUp();
	}
	
}
