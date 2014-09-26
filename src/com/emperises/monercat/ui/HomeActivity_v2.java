package com.emperises.monercat.ui;

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

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.ImagePagerAdapter;
import com.emperises.monercat.domain.model.AdInfoV3;
import com.emperises.monercat.domain.model.UserInfoV3;
import com.emperises.monercat.domain.model.ZcmAdertising;
import com.emperises.monercat.interfacesandevents.HeaderImageEvent;
import com.emperises.monercat.ui.v3.ActivityAdDetail_HTML5;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class HomeActivity_v2 extends BaseActivity implements
		OnPageChangeListener, OnItemClickListener {
	private MyAdAdapter mAdListAdapter;
	private static final int REFRESH_COMPLETE = 1;
	private static final int START_AUTO_VIEWPAGER = 2;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mPullListView.onRefreshComplete();
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
		Logger.i("Activity", "HomeActivity_v2");
		setContentView(R.layout.activity_home_v2);
		Util.checkUpdateVersion(this, SERVER_URL_UPDATE_VERSION);

	}

	@Override
	protected void initViews() {
		mErrorHit = (Button) findViewById(R.id.error_hit);
		HeaderImageEvent.getInstance().addHeaderImageListener(this);
		mProgressBar = (ProgressBar) findViewById(R.id.progress);
		homeHeaderItem = (LinearLayout) getLayoutInflater().inflate(
				R.layout.home_header_item, null);
		mPagerIndexLayout = (LinearLayout) homeHeaderItem
				.findViewById(R.id.pageControlLayout);
		mPullListView = (PullToRefreshListView) findViewById(R.id.adListView);
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				mAdPager.stopAutoScroll();
				initAdList();
				initViewPager();
			}
		});
		mMXButton = (Button) homeHeaderItem.findViewById(R.id.mingxi_button);
		mAdPager = (AutoScrollViewPager) homeHeaderItem
				.findViewById(R.id.adPager);
		mPullListView.setOnItemClickListener(this);
		initMyInfo();
		initAdList();
		// /////////////////////
		updateBalance();
		// 初始化广告列表
		initViewPager();
	}

	private void initViewPager() {
		AjaxParams params = new AjaxParams();
		params.put("udevicesId", Util.getDeviceId(this));
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
										HomeActivity_v2.this);
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
										HomeActivity_v2.this);
								mPagerIndexLayout.addView(pageControlChild,
										params);
							}
							mAdPager.setAdapter(new ImagePagerAdapter(
									HomeActivity_v2.this, mLoopAdInfos)
									.setInfiniteLoop(true));
							mAdPager.setInterval(3000);
							if (mLoopAdInfos.size() > 1) {
								mAdPager.startAutoScroll();
							}
							mAdPager.setCurrentItem(0);
							mAdPager.setOnPageChangeListener(HomeActivity_v2.this);
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

	private void initMyInfo() {
		// 初始化用户信息
		AjaxParams params = new AjaxParams();
		params.put("udevicesId", Util.getDeviceId(this));
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
									HomeActivity_v2.this);
						}
						// 初始化控件的值
						TextView nickName = (TextView) homeHeaderItem
								.findViewById(R.id.yue_nickname);
						TextView tel = (TextView) homeHeaderItem
								.findViewById(R.id.yue_tel);
						nickName.setText(user.getVal().getUname());
						tel.setText(user.getVal().getUtelephone());
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						showNetErrorToast(strMsg, t);
					}
				});
		int resId = getHeadImageResId();
		ImageView header = (ImageView) homeHeaderItem
				.findViewById(R.id.myheaderimage);
		header.setBackgroundResource(resId);
	}

	@Override
	public void onHeaderImageChange(int resId) {
		Logger.i("MYINFO", "头像变更!");
		ImageView header = (ImageView) homeHeaderItem
				.findViewById(R.id.myheaderimage);
		header.setBackgroundResource(resId);
	}

	@Override
	public void onBalanceChange(String currentBalance) {
		super.onBalanceChange(currentBalance);
		TextView balance = (TextView) homeHeaderItem
				.findViewById(R.id.yue_balance);
		balance.setText("余额:" + currentBalance + "喵币");
	}

	/**
	 * 初始化广告列表
	 */
	private void initAdList() {
		// 获取广告列表mAdInfos
		AjaxParams params = new AjaxParams();
		params.put("udevicesId", Util.getDeviceId(this));
		params.put("type", "0");
		startRequest(SERVER_URL_ADLIST, params);

	}

	@Override
	public void onHttpStart() {
		super.onHttpStart();
		mProgressBar.setVisibility(View.VISIBLE);
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
			mPullListView.setAdapter(mAdListAdapter);
			Logger.i("ADAPTER", "设置适配器");
		}
		mPullListView.onRefreshComplete();
		mProgressBar.setVisibility(View.GONE);
		mErrorHit.setVisibility(View.GONE);
	}

	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		mPullListView.onRefreshComplete();
		showNetErrorToast(strMsg, t);
		mErrorHit.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		
	}

	private final static int ITEM_VIEW_TYPE_DEFAULT = 0;
	private final static int ITEM_VIEW_TYPE_PAGER = 1;

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
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getItemViewType(int position) {
			if (position == 0) {
				return ITEM_VIEW_TYPE_PAGER;
			} else {
				return ITEM_VIEW_TYPE_DEFAULT;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view = null;
			ViewHolder holder = null;
			if (convertView != null) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				if (getItemViewType(position) == ITEM_VIEW_TYPE_DEFAULT) {

					view = getLayoutInflater().inflate(R.layout.list_ad_item,
							null);
					holder = new ViewHolder();
					holder.adIcon = (ImageView) view.findViewById(R.id.adIcon);
					holder.adTitle = (TextView) view.findViewById(R.id.adTitle);
					holder.adDescription = (TextView) view
							.findViewById(R.id.adDescription);
					holder.adRecommendText = (TextView) view
							.findViewById(R.id.adRecommendText);// 推荐
					holder.adBalanceText = (TextView) view
							.findViewById(R.id.adBalanceText);// 点击
					view.setTag(holder);

				} else if (getItemViewType(position) == ITEM_VIEW_TYPE_PAGER) {
					view = homeHeaderItem;
				}
			}
			if (getItemViewType(position) == 0) {
				ZcmAdertising info = mAdInfos.get(position);
				getFinalBitmap().display(holder.adIcon, info.getAdIcon());
				holder.adTitle.setText(info.getAdTitle());
				holder.adDescription.setText(info.getAdContent());
				holder.adBalanceText.setText("总额:" + info.getAdAward()
						+ getString(R.string.m_gold));
			}
			Logger.i("VIEW", "getView");
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
	private PullToRefreshListView mPullListView;
	private LinearLayout homeHeaderItem;
	private ProgressBar mProgressBar;
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
	public void onItemClick(AdapterView<?> pView, View itemView, int position,
			long id) {
		Intent i = new Intent(this, ActivityAdDetail_HTML5.class);
		ZcmAdertising itemInfo = (ZcmAdertising) mAdListAdapter
				.getItem(position);
		i.putExtra(INTENT_KEY_ADINFO, itemInfo);
		startActivityWithAnimation(i);

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
		case R.id.error_hit:
			mErrorHit.setVisibility(View.GONE);
			initAdList();
			initViewPager();
			break;

		default:
			break;
		}
	}

}
