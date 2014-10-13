package com.emperises.monercat.ui.v3;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.emperises.monercat.MainActivity;
import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.ui.SplashActivity;

public class WelcomeActivity extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewPager pager = new ViewPager(this);
		ImageView nav_1 = new ImageView(this);
		nav_1.setBackgroundResource(R.drawable.w1);
		ImageView nav_2 = new ImageView(this);
		nav_2.setBackgroundResource(R.drawable.w2);
		ImageView nav_3 = new ImageView(this);
		nav_3.setBackgroundResource(R.drawable.w3);
		ImageView nav_4 = new ImageView(this);
		nav_4.setBackgroundResource(R.drawable.w4);
		nav_4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
				startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
			}
		});
		List<View> list = new ArrayList<View>();
		list.add(nav_1);
		list.add(nav_2);
		list.add(nav_3);
		list.add(nav_4);
		pager.setAdapter(new MyPagerAdapter(list));
		setContentView(pager);
	}
	/**
     * ViewPager适配器
*/
    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }
	
}
