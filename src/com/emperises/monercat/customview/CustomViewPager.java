package com.emperises.monercat.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.emperises.monercat.utils.Util;

public class CustomViewPager extends ViewPager{

	private Context mContext;
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			float y = event.getY();
			if(y > Util.dip2px(100, mContext)){
				return true;
			}
			break;

		default:
			break;
		}
		return super.onInterceptTouchEvent(event);
	}

}
