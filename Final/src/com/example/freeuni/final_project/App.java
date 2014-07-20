package com.example.freeuni.final_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.freeuni.final_project.listeners.SpeedUpListener;

import android.R.integer;
import android.app.Application;

public class App extends Application implements SpeedUpListener{
	private Socket socket;
	public static final String HOST_NAME = "192.168.0.184";
    public static final int PORT_NUMBER = 8095;
    private static final String ID_STRING ="YourId:";
    private int myId;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				startConnection();
			
				
			}

			
		}).start();
	}
	private void startConnection() {
	        try {
	           socket = new Socket(HOST_NAME, PORT_NUMBER);
	           BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
	           String inputLine;
	           while ((inputLine = in.readLine()) != null) {
	        	   if ((inputLine.length() > ID_STRING.length())&& inputLine.substring(0, ID_STRING.length()).equals(ID_STRING)) {
	        	   
	        		   String id = inputLine
								.substring(ID_STRING.length());
	        		   myId = Integer.parseInt(id);
	        		   System.out.println("my id " + myId);
	        		   break;
	        	   }
	        	   
	        	   
	           }
       
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + HOST_NAME);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	            		HOST_NAME);
	            System.exit(1);
	        }
		
	}
	@Override
	public void speedUpListner() {

	    PrintWriter out = null;
		try {
			if(socket!=null){
				
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println("speadup");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
}
