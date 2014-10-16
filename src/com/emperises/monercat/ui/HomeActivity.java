package com.emperises.monercat.ui;

import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
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
import android.widget.ScrollView;
import android.widget.TextView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.ImagePagerAdapter;
import com.emperises.monercat.domain.model.AdInfoV3;
import com.emperises.monercat.domain.model.UserInfoV3;
import com.emperises.monercat.domain.model.ZcmAdertising;
import com.emperises.monercat.ui.v3.ActivityAdDetail_HTML5;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class HomeActivity extends BaseActivity implements OnPageChangeListener,
		OnItemClickListener {
	private ListView mAdListView;
	private MyAdAdapter mAdListAdapter;
	private static final int REFRESH_COMPLETE = 1;
	private static final int START_AUTO_VIEWPAGER = 2;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mPullScrollView.onRefreshComplete();
				break;
			case START_AUTO_VIEWPAGER:
				mAdPager.startAutoScroll();
				break;
			default:
				break;
			}
		}
	};
	private LinearLayout mPagerIndexLayout;
	private AutoScrollViewPager mAdPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Util.checkUpdateVersion(this, SERVER_URL_UPDATE_VERSION);

	}

	@Override
	protected void initViews() {
		mPagerIndexLayout = (LinearLayout) findViewById(R.id.pageControlLayout);
		mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
		mPullScrollView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						mAdPager.stopAutoScroll();
						initAdList();
						initViewPager();
					}
				});
		mMXButton = (Button) findViewById(R.id.mingxi_button);
		mAdPager = (AutoScrollViewPager) findViewById(R.id.adPager);
		mAdListView = (ListView) findViewById(R.id.adListView);
		mAdListView.setOnItemClickListener(this);
		initMyInfo();
		initAdList();
		mAdListView.setAdapter(mAdListAdapter);
		// /////////////////////
		updateBalance();
		// 初始化广告列表
		initViewPager();
	}

	private void initViewPager() {
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		params.put("type", "0");
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
										HomeActivity.this);
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
										HomeActivity.this);
								mPagerIndexLayout.addView(pageControlChild,
										params);
							}
							mAdPager.setAdapter(new ImagePagerAdapter(
									HomeActivity.this, mLoopAdInfos)
									.setInfiniteLoop(true));
							mAdPager.setInterval(3000);
							mAdPager.startAutoScroll();
							mAdPager.setCurrentItem(0);
							mAdPager.setOnPageChangeListener(HomeActivity.this);
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

	private void initMyInfo() {
		// 初始化用户信息
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		getHttpClient().post(SERVER_URL_USERINFO, params,
				new AjaxCallBack<String>() {
					@Override
					public void onSuccess(String t) {
						super.onSuccess(t);
						Logger.i(TAG_HTTP + "INFO", t);
						UserInfoV3 user = new Gson().fromJson(t,
								UserInfoV3.class);
						if (user != null && user.getVal() != null) {
							getDatabaseInterface().saveMyInfo(user.getVal(),
									HomeActivity.this);
						}
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						showNetErrorToast(strMsg,t);
					}
				});
	}

	/**
	 * 初始化广告列表
	 */
	private void initAdList() {
		// 获取广告列表mAdInfos
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		params.put("type", "0");
		startRequest(SERVER_URL_ADLIST, params);

	}

	@Override
	public void onHttpStart() {
		super.onHttpStart();
	}
	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		Logger.i(TAG_HTTP, content);
		AdInfoV3 infos = new Gson().fromJson(content, AdInfoV3.class);
		if (infos != null && infos.getRows() != null
				&& infos.getRows().size() > 0) {
			mAdInfos = infos.getRows();
			Logger.i("INFOS", infos.toString());
			mAdListAdapter = new MyAdAdapter(mAdInfos);
			mAdListView.setAdapter(mAdListAdapter);
//			Util.setListViewHeightBasedOnChildren(mAdListView);
		}
		mPullScrollView.onRefreshComplete();

	}

	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		mPullScrollView.onRefreshComplete();
		showNetErrorToast(strMsg,t);
	}

	class MyAdAdapter extends BaseAdapter {

		private List<ZcmAdertising> mAdInfos;

		public MyAdAdapter(List<ZcmAdertising> mAdInfos) {
			this.mAdInfos = mAdInfos;
		}

		@Override
		public int getCount() {
			return mAdInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return mAdInfos.get(position);
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
				view = getLayoutInflater().inflate(R.layout.list_ad_item, null);
				holder = new ViewHolder();
				holder.adIcon = (ImageView) view.findViewById(R.id.adIcon);
				holder.adTitle = (TextView) view.findViewById(R.id.adTitle);
				holder.adDescription = (TextView) view
						.findViewById(R.id.adDescription);
//				holder.adRecommendText = (TextView) view
//						.findViewById(R.id.adRecommendText);// 推荐
				holder.adBalanceText = (TextView) view
						.findViewById(R.id.adBalanceText);// 点击
				view.setTag(holder);
			}
			ZcmAdertising info = mAdInfos.get(position);
			getFinalBitmap().display(holder.adIcon, info.getAdIcon());
			holder.adTitle.setText(info.getAdTitle());
			holder.adDescription.setText(info.getAdContent());
			holder.adBalanceText.setText("总额:" + info.getAdAward() + getString(R.string.m_gold));
			return view;
		}

	}

	static class ViewHolder {
		ImageView adIcon;
		TextView adTitle;
		TextView adDescription;
		TextView adBalanceText;
		TextView adRecommendText;

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		currentIndex++;
		if (currentIndex == mLoopAdInfos.size()) {
			currentIndex = 0;
		}
		changeIndexBg(currentIndex);
	}

	private int currentIndex = 0;
	private Button mMXButton;
	private List<ZcmAdertising> mAdInfos;
	private List<ZcmAdertising> mLoopAdInfos;
	private PullToRefreshScrollView mPullScrollView;

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
	public void onItemClick(AdapterView<?> pView, View itemView, int position,
			long id) {
		Intent i = new Intent(this, ActivityAdDetail_HTML5.class);
		ZcmAdertising itemInfo = (ZcmAdertising) mAdListAdapter
				.getItem(position);
		i.putExtra(INTENT_KEY_ADINFO, itemInfo);
		String url = itemInfo.getAdUrl();
		if(!TextUtils.isEmpty(itemInfo.getAdUrl()) || url.contains("null")){
			Logger.i("URL", "AD URL:"+itemInfo.getAdUrl());
			startActivityWithAnimation(i);
		} 

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.mingxi_button:
			startActivity(new Intent(this, DuiHuanActivity.class));
			break;
		case R.id.wodeyue:

			break;

		default:
			break;
		}
	}

}
