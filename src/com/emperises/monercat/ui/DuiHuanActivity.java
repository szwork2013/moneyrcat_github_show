package com.emperises.monercat.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;
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
import com.emperises.monercat.domain.model.ZcmProduct;
import com.emperises.monercat.ui.v3.ActivityDuiHuanJiLu;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class DuiHuanActivity extends OtherBaseActivity implements
		OnItemClickListener {

	private ListView mDuiHuanListView;
	private Button mMXButton;
	private List<ZcmProduct> mProductInfos = new ArrayList<ZcmProduct>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuan);
		setCurrentTitle(getString(R.string.loading));
		initProductData();
	}

	private MyAdapter mProductAdapter;
	private TextView mDefaultLable;
	private void initProductData() {
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		getHttpClient().post(SERVER_URL_PRODUCT,params, new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				setCurrentTitle(getString(R.string.chaozhiduihuan));
				Logger.i(TAG_HTTP+"PRODUCT", t);
				ZcmProduct product = new Gson().fromJson(t, ZcmProduct.class);
				if(product != null && product.getRows() != null && product.getRows().size() > 0 ){
					mProductInfos = product.getRows();
					mProductAdapter = new MyAdapter();
					mDuiHuanListView.setAdapter(mProductAdapter);
				}
				setDefaultLableState();
				super.onSuccess(t);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				setCurrentTitle(getString(R.string._get_fail));
				setDefaultLableState();
			}
		});
	}

	private void setDefaultLableState() {
		if(mProductInfos.size() > 0 ){
			mDefaultLable.setVisibility(View.GONE);
		} else {
			mDefaultLable.setVisibility(View.VISIBLE);
		}
	}
	@Override
	protected void initViews() {
		mDuiHuanListView = (ListView) findViewById(R.id.duihuanListView);
		mDuiHuanListView.setAdapter(new MyAdapter());
		mDuiHuanListView.setOnItemClickListener(this);
		mMXButton = (Button) findViewById(R.id.mingxi_button);
		mMXButton.setText("兑换记录");
		mMXButton.setVisibility(View.GONE);
		mDefaultLable = (TextView) findViewById(R.id.default_lable);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mProductInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return mProductInfos.get(position);
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
			}else{
				view = getLayoutInflater().inflate(R.layout.list_duihuan_item, null);
				holder = new ViewHolder();
				holder.duihuanIcon = (ImageView) view.findViewById(R.id.duihuan_icon);
				holder.duihuanTitle = (TextView) view.findViewById(R.id.duihuan_title);
				holder.duihuanCount = (TextView) view.findViewById(R.id.duihuanCount);
				holder.duihuanBalance = (TextView) view.findViewById(R.id.duihuanBalance);
				view.setTag(holder);
			}
			ZcmProduct pro = mProductInfos.get(position);
			getFinalBitmap().display(holder.duihuanIcon, pro.getPlogo());
			holder.duihuanTitle.setText(pro.getPname());
			holder.duihuanCount.setText("剩余:"+pro.getPnum());
			holder.duihuanBalance.setText(pro.getPprice()+getString(R.string.m_gold));
			return view;
		}

	}

	static class ViewHolder{
		ImageView duihuanIcon;
		TextView duihuanTitle;
		TextView duihuanCount;
		TextView duihuanBalance;
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent i  =  new Intent(this, DuiHuanDialogActivity.class);
		ZcmProduct pro = (ZcmProduct) mProductAdapter.getItem(position);
		i.putExtra(INTENT_KEY_PRODUCTID, pro.getPid());
		startActivity(i);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mingxi_button:
			startActivity(new Intent(this, ActivityDuiHuanJiLu.class));
			break;

		default:
			break;
		}
	}
}
