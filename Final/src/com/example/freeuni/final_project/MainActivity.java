package com.example.freeuni.final_project;

import com.example.freeuni.final_project.listeners.SpeedUpListener;
import com.example.freeuni.final_project.model.DashedView;
import com.example.freeuni.final_project.model.State;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
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
	
	float movement = 10;
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

		System.out.println("v: " + state.getVelocity()  + " coord: " 
							+ state.getyCoord());
		
//		while(true){
//			try {
//				Thread.sleep(10);
				moveCar();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		//}
	}

	@Override
	public void speedUpListner() {
		speedUp();
	}
	
	private void moveCar(){
		
		//state.setVelocity(state.getVelocity() + 1);
		
		state.setyCoord(state.getyCoord() + movement);
		
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) line.getLayoutParams();
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		params.topMargin += movement;
		params.bottomMargin -= movement;
		if(params.topMargin >= 0){
			
			display.getSize(size);
			
			int height = size.y;
			params.topMargin = 0 - height;
		}
		
		line.setLayoutParams(params);
		
	}
}
