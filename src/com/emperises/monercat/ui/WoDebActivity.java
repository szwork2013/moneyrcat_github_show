package com.emperises.monercat.ui;

import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.AdInfoV3;
import com.emperises.monercat.domain.model.ZcmAdertising;
import com.emperises.monercat.ui.HomeActivity.ViewHolder;
import com.emperises.monercat.ui.v3.ActivityAdDetail_HTML5;
import com.emperises.monercat.ui.v3.ActivityShenLinJiLu;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class WoDebActivity extends OtherBaseActivity implements
		OnItemClickListener {

	private ListView mAdListView;
	private Button mMXBtutton;
	private MyAdAdapter mAdListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		setCurrentTitle(getString(R.string.loading));
	}
	
	@Override
	protected void initViews() {
		mMXBtutton = (Button) findViewById(R.id.mingxi_button);
		mMXBtutton.setText("申领记录");
		mMXBtutton.setVisibility(View.GONE);
		mAdListView = (ListView) findViewById(R.id.adListView);
		mAdListView.setOnItemClickListener(this);
		initAdList();
	}

	private void initAdList() {
		// 获取广告列表mAdInfos
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		startRequest(SERVER_URL_AD_HISTORY, params);

	}
	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		setCurrentTitle(getString(R.string.myad));
		Logger.i(TAG_HTTP, content);
		AdInfoV3 infos = new Gson().fromJson(content, AdInfoV3.class);
		if (infos != null && infos.getRows() != null
				&& infos.getRows().size() > 0) {
			mAdInfos = infos.getRows();
			Logger.i("INFOS", infos.toString());
			mAdListAdapter = new MyAdAdapter(mAdInfos);
			mAdListView.setAdapter(mAdListAdapter);
		}
	}
	private List<ZcmAdertising> mAdInfos;
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		Logger.e("error", strMsg, t);
		setCurrentTitle(getString(R.string._get_fail));
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
			// TODO Auto-generated method stub
			return mAdInfos.get(position);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
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
				view = getLayoutInflater().inflate(R.layout.list_ad_item_wode, null);
				holder = new ViewHolder();
				holder.adIcon = (ImageView) view.findViewById(R.id.adIcon);
				holder.adTitle = (TextView) view.findViewById(R.id.adTitle);
				view.setTag(holder);
			}
			ZcmAdertising info = mAdInfos.get(position);
			getFinalBitmap().display(holder.adIcon, info.getAdIcon());
			holder.adTitle.setText(info.getAdTitle());
			return view;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent i = new Intent(this, ActivityAdDetail_HTML5.class);
		ZcmAdertising itemInfo = (ZcmAdertising) mAdListAdapter
				.getItem(position);
		String url = itemInfo.getAdUrl();
		StringBuffer sb = new StringBuffer(url);
		String info =sb.delete(url.lastIndexOf("/")+1, url.length()).append("info.html").toString();
		Logger.i("TURL", info);
		itemInfo.setAdUrl(info);
		i.putExtra(INTENT_KEY_ADINFO, itemInfo);
		startActivityWithAnimation(i);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mingxi_button:
			startActivity(new Intent(this, ActivityShenLinJiLu.class));
			break;
		default:
			break;
		}
	}
}
