package com.example.freeuni.final_project.model;

import java.text.AttributedCharacterIterator.Attribute;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

public class DashedView extends View{

	public DashedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		dashes(canvas);
	}
	
	
	private void dashes(Canvas canvas){
		
		Paint paint = new Paint();
		paint.setARGB(255, 255, 255, 255);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(150);
		paint.setPathEffect(new DashPathEffect(new float[] {200,500}, 0));
		canvas.drawPaint(paint);
		
	}

}
