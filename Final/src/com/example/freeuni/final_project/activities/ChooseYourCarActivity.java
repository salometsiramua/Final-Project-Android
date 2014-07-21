package com.example.freeuni.final_project.activities;

import java.util.ArrayList;
import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;

import com.example.freeuni.final_project.App;
import com.example.freeuni.final_project.R;

import android.R.color;
import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseYourCarActivity extends Activity{

	private ArrayList<Integer> carIds;
	private ArrayList<ImageView> cars;
	private HashMap<ImageView, Integer> hm;
	private int chosenCar;
	private App app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_your_car);
		cars = new ArrayList<>();
		carIds = new ArrayList<>();
		app = (App)getApplication();
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
					chosenCar = hm.get((ImageView)v);
					app.setMyCarImageId(chosenCar);
					System.out.println("chosenCar " + chosenCar);
				
		            finish();
				}
			});
		}
		
	}

	private void getCars() {
		
		
		ArrayList<Integer> carsFrom = new ArrayList<>();
		carsFrom.add(R.id.imageView1);
		carsFrom.add(R.id.imageView2);
		carsFrom.add(R.id.imageView3);
		carsFrom.add(R.id.imageView4);
		carsFrom.add(R.id.imageView5);
		
		carIds.add(R.drawable.green_car);
		
		carIds.add(R.drawable.red_car);
		carIds.add(R.drawable.lighth_green_car);
		carIds.add(R.drawable.purple_car);
		carIds.add(R.drawable.yellow_car);
		hm = new HashMap<>();
		
		for(int i = 0; i < carsFrom.size(); i++){
			ImageView img = (ImageView)findViewById(carsFrom.get(i));
			cars.add(img);
			hm.put(img, carIds.get(i));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
}
