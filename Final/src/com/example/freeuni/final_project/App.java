package com.example.freeuni.final_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.freeuni.final_project.listeners.SpeedUpListener;

import android.app.Application;

public class App extends Application implements SpeedUpListener{
	private Socket socket;
	public static final String HOST_NAME = "192.168.1.101";
    public static final int PORT_NUMBER = 8090;
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
