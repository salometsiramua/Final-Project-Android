package com.example.freeuni.final_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private RelativeLayout layout;
	private View rightLine;
	private View leftLine;
	private RelativeLayout panel;
	
	private Button left_wheel;
	private Button right_wheel;
	
	private ImageView car;
	 Socket kkSocket;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread t  = new Thread(new Runnable() {
			
			@Override
			public void run() {
				startConnection();
				
			}
		});
        t.start();
   
        setContentView(R.layout.activity_main);
        initView();
        
    }
    // es aq ar unda ityos prosta satestod chavagde
	
	protected void startConnection() {
		String hostName = "192.168.1.101";
	       int portNumber = 8090;

	        try {
	           kkSocket = new Socket(hostName, portNumber);
	       
       
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	                hostName);
	            System.exit(1);
	        }
		
		
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
				left_wheel.setEnabled(false);
				right_wheel.setEnabled(true);
			}
		});
        
        right_wheel = (Button) findViewById(R.id.right_wheel);
        
        right_wheel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				    PrintWriter out = null;
					try {
						if(kkSocket!=null){
							System.out.println("AAAAA");
							out = new PrintWriter(kkSocket.getOutputStream(), true);
						}else{
							System.out.println("null");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(out==null);	
						            out.println("right");

				System.out.println("daechira right");
				left_wheel.setEnabled(true);
				right_wheel.setEnabled(false);
			}
		});

	}

	
	
	
}
