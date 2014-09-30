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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class TasksActivity_v2 extends OtherBaseActivity implements
		OnItemClickListener, OnPageChangeListener {
	private LinearLayout mPagerIndexLayout;
	private AutoScrollViewPager mAdPager;

	private static final int REFRESH_COMPLETE = 1;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mPullListView.onRefreshComplete();
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
		setContentView(R.layout.activity_tasks_new);
	}

	@Override
	protected void initViews() {
		mErrorHit = (Button) findViewById(R.id.error_hit);
		progress = (ProgressBar) findViewById(R.id.progress);
		mHeaderPager = (LinearLayout) getLayoutInflater().inflate(
				R.layout.task_header_item_pager, null);
		mPullListView = (PullToRefreshListView) findViewById(R.id.adListView);
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				initTaskInfos();
				initViewPager();
			}
		});
		mPagerIndexLayout = (LinearLayout) mHeaderPager
				.findViewById(R.id.pageControlLayout);
		mAdListAdapter = new MyAdAdapter();
		mPullListView.setOnItemClickListener(this);
		mAdPager = (AutoScrollViewPager) mHeaderPager
				.findViewById(R.id.adPager);
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
		getHttpClient().post(SERVER_URL_ADLIST, params,
				new AjaxCallBack<String>() {
					@Override
					public void onStart() {
						progress.setVisibility(View.VISIBLE);
						super.onStart();
					}

					@Override
					public void onSuccess(String t) {
						Logger.i("TASKINFO", t);
						AdInfoV3 info = new Gson().fromJson(t, AdInfoV3.class);
						if (info != null && info.getRows() != null
								&& info.getRows().size() > 0) {
							mAdTaskInfos = info.getRows();
							mPullListView.setAdapter(mAdListAdapter);
							Logger.i("LIST", mAdTaskInfos.size() + "");
						}
						mPullListView.onRefreshComplete();
						super.onSuccess(t);
						progress.setVisibility(View.GONE);
						mErrorHit.setVisibility(View.GONE);
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						showNetErrorToast(strMsg, t);
						mPullListView.onRefreshComplete();
						mErrorHit.setVisibility(View.VISIBLE);
						progress.setVisibility(View.GONE);
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
										TasksActivity_v2.this);
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
										TasksActivity_v2.this);
								mPagerIndexLayout.addView(pageControlChild,
										params);
							}
							mAdPager.setAdapter(new ImagePagerAdapter(
									TasksActivity_v2.this, mLoopAdInfos)
									.setInfiniteLoop(true));
							mAdPager.setInterval(3000);
							if (mLoopAdInfos.size() > 1) {
								mAdPager.startAutoScroll();
							}
							mAdPager.setCurrentItem(0);
							mAdPager.setOnPageChangeListener(TasksActivity_v2.this);
							Logger.i("INFOS", infos.toString());
						}
						super.onSuccess(t);
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						showNetErrorToast(strMsg, t);
					}
				});

	}

	private static final int ITEM_TYPE_HEADER_PAGER = 0;
	private static final int ITEM_TYPE_DEFAULT = 1;

	class MyAdAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			Logger.i("SIZECAO", "tasks:" + mAdTaskInfos.size());
			return mAdTaskInfos.size() + 1;
		}

		@Override
		public Object getItem(int position) {
			return mAdTaskInfos.get(position - 1);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public int getItemViewType(int position) {
			if (position == 0) {
				return ITEM_TYPE_HEADER_PAGER;
			} else {
				return ITEM_TYPE_DEFAULT;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view = null;
			ViewHolder holder = null;
			int type = getItemViewType(position);
			if (convertView != null) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				if (type == 0) {
					view = mHeaderPager;
				} else {
					view = getLayoutInflater().inflate(R.layout.list_task_item,
							null);
					holder = new ViewHolder();
					holder.taskIcon = (ImageView) view
							.findViewById(R.id.task_icon);
					holder.taskTitle = (TextView) view
							.findViewById(R.id.taskTitle);
					view.setTag(holder);
				}
			}
			if (type == ITEM_TYPE_DEFAULT) {
				ZcmAdertising zcm = mAdTaskInfos.get(position - 1);
				holder.taskTitle.setText(zcm.getAdTitle());
				getFinalBitmap().display(holder.taskIcon, zcm.getAdIcon());
			}
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
		Intent i = new Intent(this, ActivityAdDetail_HTML5.class);
		ZcmAdertising itemInfo = (ZcmAdertising) mAdListAdapter
				.getItem(position - 1);
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
	private PullToRefreshListView mPullListView;

	private MyAdAdapter mAdListAdapter;
	private LinearLayout mHeaderPager;
	private ProgressBar progress;
	private Button mErrorHit;

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

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.error_hit:
			initTaskInfos();
			initViewPager();
			break;

		default:
			break;
		}
	}
}
