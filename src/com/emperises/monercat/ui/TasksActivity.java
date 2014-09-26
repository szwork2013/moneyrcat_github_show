package com.emperises.monercat.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.ImagePagerAdapter;
import com.emperises.monercat.domain.model.AdInfoV3;
import com.emperises.monercat.domain.model.ZcmAdertising;
import com.emperises.monercat.ui.v3.ActivityAdDetail_HTML5;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class TasksActivity extends OtherBaseActivity implements
		OnItemClickListener, OnPageChangeListener {
	private LinearLayout mPagerIndexLayout;
	private AutoScrollViewPager mAdPager;
	private ListView mTaskListView;
	private static final int REFRESH_COMPLETE = 1;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mPullScrollView.onRefreshComplete();
				break;
			default:
				break;
			}
		}
	};
	private List<ZcmAdertising> mLoopAdInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasks);
	}

	@Override
	protected void initViews() {
		mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
		mPullScrollView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						initTaskInfos();
						initViewPager();
					}
				});
//		mPagerIndexLayout = (LinearLayout) findViewById(R.id.pagerindex);
		mTaskListView = (ListView) findViewById(R.id.taskListView);
		mAdListAdapter = new MyAdAdapter();
		mTaskListView.setOnItemClickListener(this);
		mAdPager = (AutoScrollViewPager) findViewById(R.id.adPager);
		initViewPager();
		initTaskInfos();
	}

	/**
	 * 初始化任务列表
	 */
	private List<ZcmAdertising> mAdTaskInfos = new ArrayList<ZcmAdertising>();
	private void initTaskInfos() {
		AjaxParams params = new AjaxParams();
		params.put("udevicesId", Util.getDeviceId(this));
		params.put("type", "1");
		getHttpClient().post(SERVER_URL_ADLIST, params,new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				Logger.i("TASKINFO", t);
				AdInfoV3 info = new Gson().fromJson(t, AdInfoV3.class);
				if(info != null && info.getRows() != null && info.getRows().size() > 0){
					mAdTaskInfos = info.getRows();
					mTaskListView.setAdapter(mAdListAdapter);
					Util.setListViewHeightBasedOnChildren(mTaskListView);
					Logger.i("LIST", mAdTaskInfos.size()+"");
				}
				mPullScrollView.onRefreshComplete();
				super.onSuccess(t);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				showNetErrorToast(strMsg,t);
				mPullScrollView.onRefreshComplete();
			}
		});
	}

	private void initViewPager() {
		AjaxParams params = new AjaxParams();
		params.put("udevicesId", Util.getDeviceId(this));
		params.put("type", "1");
		getHttpClient().post(SERVER_URL_LOOPAD, params,
				new AjaxCallBack<String>() {

					@Override
					public void onSuccess(String t) {
						Logger.i(TAG_HTTP + "LOOP", t);
						AdInfoV3 infos = new Gson().fromJson(t, AdInfoV3.class);
						if (infos != null && infos.getRows() != null
								&& infos.getRows().size() > 0) {
							// 获得首页广告轮询列表
							mLoopAdInfos = infos.getRows();
							mPagerIndexLayout.removeAllViews();
							for (int i = 0; i < mLoopAdInfos.size(); i++) {
								// 添加小圆点
								ImageView pageControlChild = new ImageView(
										TasksActivity.this);
								if (i == 0) {
									pageControlChild
											.setBackgroundResource(R.drawable.circle_selected);
								} else {
									pageControlChild
											.setBackgroundResource(R.drawable.circle_noraml);
								}
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
										LayoutParams.WRAP_CONTENT,
										LayoutParams.WRAP_CONTENT);
								params.leftMargin = Util.px2dip(20,
										TasksActivity.this);
								mPagerIndexLayout.addView(pageControlChild,
										params);
							}
							mAdPager.setAdapter(new ImagePagerAdapter(
									TasksActivity.this, mLoopAdInfos)
									.setInfiniteLoop(true));
							mAdPager.setInterval(3000);
							mAdPager.startAutoScroll();
							mAdPager.setCurrentItem(0);
							mAdPager.setOnPageChangeListener(TasksActivity.this);
							Logger.i("INFOS", infos.toString());
						}
						super.onSuccess(t);
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						showNetErrorToast(strMsg,t);
					}
				});

	}

	class MyAdAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			Logger.i("SIZECAO", "tasks:"+mAdTaskInfos.size());
			return mAdTaskInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return mAdTaskInfos.get(position);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view = null;
			ViewHolder holder = null;
			if (convertView != null) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = getLayoutInflater().inflate(R.layout.list_task_item,
						null);
				holder = new ViewHolder();
				holder.taskIcon = (ImageView) view.findViewById(R.id.task_icon);
				holder.taskTitle = (TextView) view.findViewById(R.id.taskTitle);
				view.setTag(holder);
			} 
			ZcmAdertising zcm = mAdTaskInfos.get(position);
			Logger.i("view", "getView :"+zcm.getAdTitle());
			Logger.i("POSITION", "listview current position = " + position);
			holder.taskTitle.setText(zcm.getAdTitle());
			getFinalBitmap().display(holder.taskIcon, zcm.getAdIcon()); 
			return view;
		}

	}

	static class ViewHolder {
		ImageView taskIcon;
		TextView taskTitle;
		TextView textBalance;
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
			Intent i = new Intent(this,ActivityAdDetail_HTML5.class);
			ZcmAdertising itemInfo = (ZcmAdertising) mAdListAdapter.getItem(position);
			i.putExtra(INTENT_KEY_ADINFO, itemInfo);
			startActivityWithAnimation(i);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		currentIndex++;
		if (currentIndex == mLoopAdInfos.size()) {
			currentIndex = 0;
		}
		changeIndexBg(currentIndex);
	}

	private int currentIndex = 0;
	private PullToRefreshScrollView mPullScrollView;
	private MyAdAdapter mAdListAdapter;

	private void changeIndexBg(int currentPosition) {
		for (int i = 0; i < mPagerIndexLayout.getChildCount(); i++) {
			ImageView bg = (ImageView) mPagerIndexLayout.getChildAt(i);
			if (i == currentPosition) {
				bg.setBackgroundResource(R.drawable.circle_selected);
			} else {
				bg.setBackgroundResource(R.drawable.circle_noraml);
			}
		}
	}

}
