package com.emperises.monercat.ui.v3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


public class ActivityMessageList extends OtherBaseActivity implements OnRefreshListener<ListView>{

	private PullToRefreshListView mPullToRefreshListView;
	private static final int REFRESH_COMPLETE = 1;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mPullToRefreshListView.onRefreshComplete();
				break;
			default:
				break;
			}
		}
	};
	class MyAdAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View v = getLayoutInflater().inflate(R.layout.list_message, null);
			return v; 
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_list);
		setCurrentTitle("消息列表");
	}
	@Override
	protected void initViews() {
		super.initViews();
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.messageListView);
		mPullToRefreshListView.setAdapter(new MyAdAdapter());
		mPullToRefreshListView.setOnRefreshListener(this);
	}
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(this, System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		mPullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SystemClock.sleep(1000);
				mHandler.sendEmptyMessage(REFRESH_COMPLETE);
			}
		}).start();		
	}
}
