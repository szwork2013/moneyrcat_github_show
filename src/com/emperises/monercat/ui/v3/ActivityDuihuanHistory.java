package com.emperises.monercat.ui.v3;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.DuihuanHistory;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class ActivityDuihuanHistory extends OtherBaseActivity {


	private ListView mListView;
	private List<DuihuanHistory> mDuihuanHistroyList = new ArrayList<DuihuanHistory>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuanhistory);
		setCurrentTitle(R.string.loading);
	}
	@Override
	protected void initViews() {
		mListView = (ListView) findViewById(R.id.duihuanListView);
		initData();
	}
	private void initData(){
		//查询兑换记录
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		startRequest(SERVER_URL_DUIHUANHISTORY, params);
	}
	@Override
	public void onHttpStart() {
		super.onHttpStart();
	}
	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		setCurrentTitle(R.string.duihuan_history);
		DuihuanHistory duihuan = new Gson().fromJson(content, DuihuanHistory.class);
		if(duihuan != null && duihuan.getRows() != null){
			mDuihuanHistroyList = duihuan.getRows();
			mListView.setAdapter(new MyAdapter());
		}
	}
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		setCurrentTitle(R.string.errortoast);
	}

	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mDuihuanHistroyList.size();
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
				holder = new ViewHolder();
				view = getLayoutInflater().inflate(R.layout.list_duihuan_history_item, null);
				holder.addr = (TextView) view.findViewById(R.id.duihuan_history_ems_addr);
				holder.createTime = (TextView) view.findViewById(R.id.duihuan_history_time);
				holder.name = (TextView) view.findViewById(R.id.duihuan_history_ems_name);
				holder.states = (TextView) view.findViewById(R.id.duihuan_history_states);
				view.setTag(holder);
			}
			DuihuanHistory history = mDuihuanHistroyList.get(position);
			holder.prodName.setText(history.getDhPname());
			holder.addr.setText("收货地址:"+history.getDhAddress());
			holder.createTime.setText(history.getDhCreateTime());
			holder.name.setText("收货人:"+history.getDhUname());
			String statesText = "";
			int states = Integer.parseInt(history.getDhStatus());
			if(states == 0){
				//审核中
				statesText = "审核中";
			} else if(states == 1){
				//审核通过
				statesText = "审核通过";
			} else if(states == 2){
				//审核未通过
				statesText = "未通过审核";
			}
			holder.states.setText(statesText);
			return view;
		}
		
	}
	static class ViewHolder {
		TextView createTime ;
		TextView prodName ;
		TextView states ;
		TextView addr ;
		TextView name ;
	}
}
