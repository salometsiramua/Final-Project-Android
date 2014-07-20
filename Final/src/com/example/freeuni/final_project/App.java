package com.example.freeuni.final_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.freeuni.final_project.listeners.SpeedUpListener;
import com.example.freeuni.final_project.model.CarPhysics;

import android.R.integer;
import android.app.Application;

public class App extends Application implements SpeedUpListener {
	private Socket socket;
	public static final String HOST_NAME = "192.168.0.184";
	public static final int PORT_NUMBER = 9090;
	private static final String ID_STRING = "YourId:";
	private static final String CONNECTION_STRING = "connect:";
	private static final String WANTS_CONNECTION_STRING = "wantsConnection:";
	private static final String GAME_STARTED = "gameStarted:";
	private int myId;
	private int theirId;
	private static final String SPEED_UP = "speedUp:";
	private CarPhysics myCarPhysics;
	private CarPhysics theirCarPhysics;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setMyCarPhysics(new  CarPhysics());
		setTheirCarPhysics(new CarPhysics());
	    //theirCarPhysics.changeVelocityY(200);
		new Thread(new Runnable() {

			@Override
			public void run() {
				startConnection();
				
			}

		}).start();
	}

	protected void waitForGameToStart() {
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				if ((inputLine.length() > GAME_STARTED.length())
						&& inputLine.substring(0, GAME_STARTED.length()).equals(
								GAME_STARTED)) {
					startListeningSpeedUpNotifications();
					
					//start game
					break;
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void startListeningSpeedUpNotifications() {
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				if ((inputLine.length() > SPEED_UP.length())
						&& inputLine.substring(0, SPEED_UP.length()).equals(
								SPEED_UP)) {
				
					
					Float speed = Float.parseFloat(inputLine.substring(SPEED_UP.length()));
					theirCarPhysics.setVelocityY(speed);
					
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void startConnection() {
		System.out.println("SDFDFSDF");
		try {
			socket = new Socket(HOST_NAME, PORT_NUMBER);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				if ((inputLine.length() > ID_STRING.length())
						&& inputLine.substring(0, ID_STRING.length()).equals(
								ID_STRING)) {

					String id = inputLine.substring(ID_STRING.length());
					myId = Integer.parseInt(id);
					waitForGameToStart();
					System.out.println("my id " + myId);
					break;
				}

			}

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + HOST_NAME);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to "
					+ HOST_NAME);
			System.exit(1);
		}

	}

	@Override
	public void speedUpListner(final float speed) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				PrintWriter out = null;
				try {
					if (socket != null) {

						out = new PrintWriter(socket.getOutputStream(), true);
						out.println(SPEED_UP+speed);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
	

	}

	@Override
	public void onSubmitFriendsId(int id) {

		PrintWriter out = null;
		try {
			if (socket != null) {

				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(CONNECTION_STRING + id);
				theirId = id;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onWaitForFriend() {
		System.out.println("shemovida");
		new Thread(new Runnable() {

			@Override
			public void run() {
				BufferedReader in = null;
				try {
					in = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
					
						if ((inputLine.length() > WANTS_CONNECTION_STRING.length())&& inputLine.substring(0,WANTS_CONNECTION_STRING.length()).equals(WANTS_CONNECTION_STRING)) {

							String theirIdStr = inputLine.substring(WANTS_CONNECTION_STRING.length());
							theirId = Integer.parseInt(theirIdStr);
							System.out.println("theirId"+theirIdStr);
							waitForGameToStart();
							break;
						}

					}
					
					
						

					
					System.out.println("gavida");
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("cats");
			}

		}).start();
		
		}

	public CarPhysics getMyCarPhysics() {
		return myCarPhysics;
	}

	public void setMyCarPhysics(CarPhysics myCarPhysics) {
		this.myCarPhysics = myCarPhysics;
	}

	public CarPhysics getTheirCarPhysics() {
		return theirCarPhysics;
	}

	public void setTheirCarPhysics(CarPhysics theirCarPhysics) {
		this.theirCarPhysics = theirCarPhysics;
	}

}
