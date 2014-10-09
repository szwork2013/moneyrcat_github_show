package com.emperises.monercat.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.TransInfoV3;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class MingXiActivity extends OtherBaseActivity {

	private ListView mMinxiListView;
	private MyAdapter mTransAdapter;
	private List<TransInfoV3> mTransInfos = new ArrayList<TransInfoV3>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mingxi);
		setCurrentTitle(getString(R.string.loading));
		
	}
	@Override
	protected void initViews() {
		findViewById(R.id.mingxi_button).setVisibility(View.GONE);;
		mMinxiListView = (ListView) findViewById(R.id.mingxiListView);
		initTransData();
//		mMinxiListView.setAdapter(new MyAdapter());
	}
	/**
	 * 获得交易记录
	 */
	private void initTransData() {
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		getHttpClient().post(SERVER_URL_TRADE_DETAIL, params,new AjaxCallBack<String>() {
			public void onSuccess(String t) {
				Logger.i("TRANS", t);
				setCurrentTitle(getString(R.string.shouzhimingxi));
				TransInfoV3 zt = new Gson().fromJson(t, TransInfoV3.class);
				if(zt != null && zt.getRows() != null && zt.getRows().size() > 0){
					mTransInfos = zt.getRows();
					mTransAdapter = new MyAdapter();
					mMinxiListView.setAdapter(mTransAdapter);
				}
			};
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				setCurrentTitle(getString(R.string._get_fail));
				showNetErrorToast(strMsg,t);
			}
		});
	}
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mTransInfos.size();
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
				view = getLayoutInflater().inflate(R.layout.list_mingxi_item_v3, null);
				holder = new ViewHolder();
				holder.transBalance = (TextView) view.findViewById(R.id.transBalance);
				holder.transCurrentBalance = (TextView) view.findViewById(R.id.transCurrentBalance);
				holder.transDate = (TextView) view.findViewById(R.id.transDate);
				holder.transTitle = (TextView) view.findViewById(R.id.transTypeTitle);
				view.setTag(holder);
			}
			TransInfoV3 trans = mTransInfos.get(position);
			holder.transTitle.setText(trans.getTrRemark());
			holder.transBalance.setText("余额:"+trans.getTrBalance()+getString(R.string.m_gold));
			holder.transDate.setText(trans.getTrBeginTime());
			if(trans.getTrInOrOut().equals("1")){
				//如果是收入
				holder.transCurrentBalance.setTextColor(Color.parseColor("#4CAB59"));
				holder.transCurrentBalance.setText("+"+trans.getTrAmount());
			} else if (trans.getTrInOrOut().equals("2")) {
				//如果是支出
				holder.transCurrentBalance.setTextColor(Color.parseColor("#AB4D59"));
				holder.transCurrentBalance.setText("-"+trans.getTrAmount());
			}
			
			return view;
		}
		
	}
	static class ViewHolder {
		TextView transTitle;
		TextView transBalance;
		TextView transDate;
		TextView transCurrentBalance;
	}
}
