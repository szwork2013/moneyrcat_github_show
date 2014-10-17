package com.emperises.monercat;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emperises.monercat.adapter.HomeViewPagerAdapter;
import com.emperises.monercat.customview.MainViewPager;
import com.emperises.monercat.ui.HomeActivity_v2;
import com.emperises.monercat.ui.MingXiActivity;
import com.emperises.monercat.ui.MoreActivity;
import com.emperises.monercat.ui.TasksActivity_v2;
import com.emperises.monercat.ui.WoDeTabActivity;
import com.emperises.monercat.ui.v3.ActivityMessageList;
import com.emperises.monercat.utils.PushUtils;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup implements OnClickListener {

	private MainViewPager mHomePager;
	private String[] titleIds = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PushUtils.startPush(this);
		titleIds = new String[] { getResources().getString(R.string.tasktitle), getResources().getString(R.string.tasktitle_v2),getResources().getString(R.string.my), getResources().getString(R.string.more) };
		mHomePager = (MainViewPager) findViewById(R.id.homePager);
		mHomePager.setOffscreenPageLimit(4);
		View home = getLocalActivityManager().startActivity("index",
				new Intent(this, HomeActivity_v2.class)).getDecorView();
		View recommend = getLocalActivityManager().startActivity("tasks",
				new Intent(this, TasksActivity_v2.class)).getDecorView();
		View my = getLocalActivityManager().startActivity("wode", 
				new Intent(this, WoDeTabActivity.class)).getDecorView();
		View more = getLocalActivityManager().startActivity("more",
				new Intent(this, MoreActivity.class)).getDecorView();
		List<View> mListViews = new ArrayList<View>();
		mListViews.add(home);
		mListViews.add(recommend);
		mListViews.add(my);
		mListViews.add(more);
		HomeViewPagerAdapter mAdapter = new HomeViewPagerAdapter(mListViews);
		 titleText = (TextView) findViewById(R.id.titleText);
		mHomePager.setAdapter(mAdapter);
		LinearLayout tab = (LinearLayout) findViewById(R.id.bottomlayout);
		mTableButtonList = new ArrayList<Button>();
		for (int i = 0; i < tab.getChildCount(); i++) {
			Button b = (Button) tab.getChildAt(i);
			mTableButtonList.add(b);
			b.setOnClickListener(this);
		}
		mHomePager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				titleText.setText(titleIds[position]);
				changeButtonColor(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		mHomePager.setCurrentItem(0, true);
		changeButtonColor(0);
		findViewById(R.id.leftItem).setOnClickListener(this);
		findViewById(R.id.rightItem).setOnClickListener(this);
		
	}

	
	private List<Button> mTableButtonList;
	private TextView titleText;

	private void changeButtonColor(int position) {
		switch (position) {
		case 0:
			
			mTableButtonList.get(0).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_home_selected), null, null);
			mTableButtonList.get(1).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_recommed_normal), null, null);
			mTableButtonList.get(2).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_my_normal), null, null);
			mTableButtonList.get(3).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_more_normal), null, null);
			mTableButtonList.get(0).setTextColor(Color.parseColor("#ad1f24"));
			mTableButtonList.get(1).setTextColor(Color.parseColor("#777777"));
			mTableButtonList.get(2).setTextColor(Color.parseColor("#777777"));
			mTableButtonList.get(3).setTextColor(Color.parseColor("#777777"));
			break;
		case 1:
			mTableButtonList.get(0).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_home_normal), null, null);
			mTableButtonList.get(1).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_recommed_selected), null, null);
			mTableButtonList.get(2).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_my_normal), null, null);
			mTableButtonList.get(3).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_more_normal), null, null);
			mTableButtonList.get(0).setTextColor(Color.parseColor("#777777"));
			mTableButtonList.get(1).setTextColor(Color.parseColor("#ad1f24"));
			mTableButtonList.get(2).setTextColor(Color.parseColor("#777777"));
			mTableButtonList.get(3).setTextColor(Color.parseColor("#777777"));
			break;
		case 2:
			mTableButtonList.get(0).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_home_normal), null, null);
			mTableButtonList.get(1).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_recommed_normal), null, null);
			mTableButtonList.get(2).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_my_selected), null, null);
			mTableButtonList.get(3).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_more_normal), null, null);
			mTableButtonList.get(0).setTextColor(Color.parseColor("#777777"));
			mTableButtonList.get(1).setTextColor(Color.parseColor("#777777"));
			mTableButtonList.get(2).setTextColor(Color.parseColor("#ad1f24"));
			mTableButtonList.get(3).setTextColor(Color.parseColor("#777777"));
			break;
		case 3:
			mTableButtonList.get(0).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_home_normal), null, null);
			mTableButtonList.get(1).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_recommed_normal), null, null);
			mTableButtonList.get(2).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_my_normal), null, null);
			mTableButtonList.get(3).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.bottom_more_selected), null, null);
			mTableButtonList.get(0).setTextColor(Color.parseColor("#777777"));
			mTableButtonList.get(1).setTextColor(Color.parseColor("#777777"));
			mTableButtonList.get(2).setTextColor(Color.parseColor("#777777"));
			mTableButtonList.get(3).setTextColor(Color.parseColor("#ad1f24"));
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tab_index:
			mHomePager.setCurrentItem(0, true);
			changeButtonColor(0);
			break;
		case R.id.tab_recommend:
			mHomePager.setCurrentItem(1, true);
			changeButtonColor(1);
			break;
		case R.id.tab_my:
			mHomePager.setCurrentItem(2, true);
			changeButtonColor(2);
			break;
		case R.id.tab_more:
			mHomePager.setCurrentItem(3, true);
			changeButtonColor(3);
			break;
		case R.id.leftItem:
			startActivity(new Intent(this , ActivityMessageList.class));
			break;
		case R.id.rightItem:
			startActivity(new Intent(this , MingXiActivity.class));
			break;
		default:
			break;
		}
	}

}
