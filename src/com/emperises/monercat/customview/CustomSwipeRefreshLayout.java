package com.emperises.monercat.customview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

	private float x = 0;
	private float y = 0;
	private boolean touch = true;
	public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()){  
            case MotionEvent.ACTION_DOWN:
            	x = event.getX();  
            	y = event.getY();  
                break;  
            case MotionEvent.ACTION_MOVE:
            	float cx = Math.abs(event.getX());
            	float cy = Math.abs(event.getY());
            	float dx = Math.abs(cx - x);
            	float dy = Math.abs(cy - y);
            	float e = dx - dy;
            	if(e > 10){
            		return false;
            	}
                break;  
        }  
		return super.onInterceptTouchEvent(event);
	}

}
