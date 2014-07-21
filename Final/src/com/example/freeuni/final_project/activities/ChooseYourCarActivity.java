package com.example.freeuni.final_project.activities;

import java.util.ArrayList;

import com.example.freeuni.final_project.R;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseYourCarActivity extends Activity{

	private ArrayList<ImageView> cars;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_your_car);
		cars = new ArrayList<>();
		getCars();
		setListeners();
	}
	
	private void setListeners() {
		for(int i = 0; i < cars.size(); i++){
			cars.get(i).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					System.out.println("daechira");
					v.setBackgroundColor(color.holo_blue_bright);
				}
			});
		}
		
	}

	private void getCars() {
		ImageView car1 = (ImageView) findViewById(R.id.imageView1);
		ImageView car2 = (ImageView) findViewById(R.id.imageView2);
		ImageView car3 = (ImageView) findViewById(R.id.imageView3);
		ImageView car4 = (ImageView) findViewById(R.id.imageView4);
		ImageView car5 = (ImageView) findViewById(R.id.imageView5);
		cars.add(car1);
		cars.add(car2);
		cars.add(car3);
		cars.add(car4);
		cars.add(car5);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
}
