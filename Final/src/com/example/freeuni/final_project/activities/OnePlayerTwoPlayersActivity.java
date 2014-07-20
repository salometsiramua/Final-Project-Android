package com.example.freeuni.final_project.activities;

import com.example.freeuni.final_project.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OnePlayerTwoPlayersActivity extends Activity{

	private Button onePlayerButton;
	private Button twoPlayersButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_or_two);
		initButtons();
	}
	private void initButtons() {
		onePlayerButton = (Button) findViewById(R.id.one_player);
		onePlayerButton.setBackgroundColor(Color.WHITE);
		onePlayerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(), MainActivity.class);
				in.putExtra("one_or_two", "one");
				startActivity(in);
				
			}
		});
		twoPlayersButton = (Button) findViewById(R.id.two_players);
		twoPlayersButton.setBackgroundColor(Color.WHITE);
		twoPlayersButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getBaseContext(), StartGameActivity.class);
				
				startActivity(in);
			}
		});
	}
}
