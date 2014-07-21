package com.example.freeuni.final_project.activities;

import com.example.freeuni.final_project.App;
import com.example.freeuni.final_project.R;
import com.example.freeuni.final_project.listeners.SpeedUpListener;
import com.example.freeuni.final_project.listeners.StartGameListener;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class StartGameActivity extends Activity implements StartGameListener{

	private Button startNewGameButton;
	private EditText typeId;
	private Button joinButton;
	private SpeedUpListener speedUpListener;
	private TextView yourId;
	private LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		((App)getApplication()).setStartGameListener(this);
		speedUpListener = (SpeedUpListener) getApplication();
		speedUpListener.onStartConnection();
		setContentView(R.layout.start_game);
		layout = (LinearLayout) findViewById(R.id.startLayout);
		layout.setBackgroundColor(Color.WHITE);
		initView();
		
		
	}

	private void initView() {
		startNewGameButton = (Button) findViewById(R.id.start_new_game);
		startNewGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				speedUpListener.onWaitForFriend();
				
				
				yourId = new TextView(getApplicationContext());
				yourId.setText("your id is " + ((App)getApplication()).getMyId() + " invite your friends :)");
				yourId.setTextColor(Color.BLACK);
				yourId.setTextSize(20);
				LayoutParams params = new LayoutParams(600, 200);
				//params.
				params.setMargins(80, -600, 20, 400);
				yourId.setLayoutParams(params);
				layout.addView(yourId);
				
				
			}
		});
		//startNewGameButton.setBackgroundColor(Color.WHITE);
		typeId = (EditText)findViewById(R.id.write_friends_id);
		//typeId.setBackgroundColor(Color.WHITE);
		joinButton = (Button)findViewById(R.id.submit);
		joinButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				speedUpListener.onSubmitFriendsId(Integer.parseInt(typeId.getText().toString()));
				
			}
		});
		//joinButton.setBackgroundColor(Color.WHITE);
	}

	@Override
	public void onGameStart() {
		Intent in = new Intent(getApplicationContext(), MainActivity.class);
		in.putExtra("one_or_two", "two");
		startActivity(in);
		
	}
}
