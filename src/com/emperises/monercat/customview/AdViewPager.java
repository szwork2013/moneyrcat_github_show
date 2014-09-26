package com.emperises.monercat.customview;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class AdViewPager extends AutoScrollViewPager{

	public AdViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public AdViewPager(Context context) {
		super(context);
	}
    PointF downPoint = new PointF();  
    OnSingleTouchListener onSingleTouchListener;  
  
    @Override  
    public boolean onTouchEvent(MotionEvent evt) {  
        switch (evt.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            downPoint.x = evt.getX();  
            downPoint.y = evt.getY();  
            if (this.getChildCount() > 1) {   
                getParent().requestDisallowInterceptTouchEvent(true);  
            }  
            break;  
        case MotionEvent.ACTION_MOVE:  
            if (this.getChildCount() > 1) {   
                getParent().requestDisallowInterceptTouchEvent(true);  
            }  
            break;  
        case MotionEvent.ACTION_UP:  
            if (PointF.length(evt.getX() - downPoint.x, evt.getY()  
                    - downPoint.y) < (float) 5.0) {  
                onSingleTouch(this);  
                return true;  
            }  
            break;  
        }  
        return super.onTouchEvent(evt);  
    }  
  
    public void onSingleTouch(View v) {  
        if (onSingleTouchListener != null) {  
            onSingleTouchListener.onSingleTouch(v);  
        }  
    }  
  
    public interface OnSingleTouchListener {  
        public void onSingleTouch(View v);  
    }  
  
    public void setOnSingleTouchListener(  
            OnSingleTouchListener onSingleTouchListener) {  
        this.onSingleTouchListener = onSingleTouchListener;  
    }

}
