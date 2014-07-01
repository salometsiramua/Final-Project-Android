package com.example.freeuni.final_project;

import android.os.Bundle;
import android.provider.LiveFolders;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private RelativeLayout layout;
	private View rightLine;
	private View leftLine;
//	private RelativeLayout line;
	private View line;
	private static final double lineLenght = 100.0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        
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
	        line =  findViewById(R.id.line);
	        
	        rightLine.setBackgroundColor(Color.WHITE);
	        leftLine.setBackgroundColor(Color.WHITE);
	      
		}

	
	
	
}
