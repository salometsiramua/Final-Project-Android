package com.example.freeuni.final_project.activities;

import com.example.freeuni.final_project.R;
import com.example.freeuni.final_project.listeners.SpeedUpListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartGameActivity extends Activity{

	private Button startNewGameButton;
	private EditText typeId;
	private Button joinButton;
	private SpeedUpListener speedUpListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_game);
		initView();
		speedUpListener = (SpeedUpListener) getApplication();
	}

	private void initView() {
		startNewGameButton = (Button) findViewById(R.id.start_new_game);
		startNewGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//speedUpListener.onWaitForFriend();
				startActivity(new Intent(getBaseContext(), ChooseYourCarActivity.class));
			}
		});
		typeId = (EditText)findViewById(R.id.write_friends_id);
		joinButton = (Button)findViewById(R.id.submit);
		joinButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//speedUpListener.onSubmitFriendsId(Integer.parseInt(typeId.getText().toString()));
				startActivity(new Intent(getBaseContext(), MainActivity.class));
			}
		});
	}
}
