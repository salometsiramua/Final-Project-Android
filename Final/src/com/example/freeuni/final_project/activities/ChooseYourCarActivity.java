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
					chosenCar = carIds.get(hm.get((ImageView)v));
					app.setMyCarImageId(chosenCar);
					android.os.Process.killProcess(android.os.Process.myPid());
		            System.exit(1);
				}
			});
		}
		
	}

	private void getCars() {
		carIds.add(R.id.imageView1);
		carIds.add(R.id.imageView2);
		carIds.add(R.id.imageView3);
		carIds.add(R.id.imageView4);
		carIds.add(R.id.imageView5);
		hm = new HashMap<>();
		for(int i = 0; i < cars.size(); i++){
			ImageView img = (ImageView)findViewById(carIds.get(i));
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
