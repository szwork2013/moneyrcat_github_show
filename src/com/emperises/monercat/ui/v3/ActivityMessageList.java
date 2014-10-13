package com.emperises.monercat.ui.v3;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.ZcmMessage;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


public class ActivityMessageList extends OtherBaseActivity implements OnRefreshListener<ListView>{

	private PullToRefreshListView mPullToRefreshListView;
	private List<ZcmMessage> mMessages = new ArrayList<ZcmMessage>();
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
	private void getMessageList(){
		AjaxParams params = new AjaxParams();
		params.put("devicesId", Util.getDeviceId(this));
		params.put("page", "1");
		params.put("rows", "100");
		startRequest(SERVER_URL_MESSAGELIST, params);
	}
	public void onHttpStart() {
		
	};
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		showNetErrorToast(strMsg, t);
		setCurrentTitle("加载失败");
		mPullToRefreshListView.onRefreshComplete();
	}
	@Override
	public void onFinished(String content) {
		ZcmMessage message = new Gson().fromJson(content, ZcmMessage.class);
		if(message != null && message.getRows().size() > 0){
			mMessages = message.getRows();
			mPullToRefreshListView.setAdapter(new MyAdAdapter());
		}
		setCurrentTitle("消息列表");
		mPullToRefreshListView.onRefreshComplete();
	}
	class MyAdAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mMessages.size();
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
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view = null;
			ViewHolder holder = null;
			if(convertView != null){
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = getLayoutInflater().inflate(R.layout.list_message, null);
				holder = new ViewHolder();
				holder.messageContent = (TextView) view.findViewById(R.id.message_content);
				holder.messageTime = (TextView) view.findViewById(R.id.message_time);
				holder.messageTitle = (TextView) view.findViewById(R.id.message_title);
				holder.messageImage =  (ImageView) view.findViewById(R.id.message_image);
				view.setTag(holder);
			}
			ZcmMessage ms = mMessages.get(position);
			holder.messageContent.setText(ms.getMcontent());
			holder.messageTime.setText(ms.getMcreateTime());
			holder.messageTitle.setText(ms.getMtitle());
			return view; 
		}
		
	}
	static class ViewHolder {
		ImageView messageImage;
		TextView messageTitle;
		TextView messageContent;
		TextView messageTime;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_list);
		setCurrentTitle("加载中..");
	}
	@Override
	protected void initViews() {
		super.initViews();
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.messageListView);
		mPullToRefreshListView.setAdapter(new MyAdAdapter());
		mPullToRefreshListView.setOnRefreshListener(this);
		getMessageList();
	}
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		getMessageList();
	}
}
