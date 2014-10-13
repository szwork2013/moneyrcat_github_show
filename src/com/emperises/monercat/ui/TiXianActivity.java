package com.emperises.monercat.ui;

import java.util.ArrayList;
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
import android.widget.ListView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.model.ZcmProduct;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class TiXianActivity extends OtherBaseActivity implements OnItemClickListener{

	private Button mMXButton;
	private List<ZcmProduct> mProducts = new ArrayList<ZcmProduct>();
	private ListView mTixianListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian);
		setCurrentTitle("加载中..");
		
	}

	private void getProductList(){
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		params.put("type", "1");
		startRequest(SERVER_URL_PRODUCT, params);
	}
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		showNetErrorToast(strMsg, t);
		setCurrentTitle(getString(R.string._get_fail));
	}
	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		ZcmProduct p = new Gson().fromJson(content, ZcmProduct.class);
		if( p!=null && p.getRows().size() > 0){
			mProducts = p.getRows();
			MyAdapter mAdapter = new MyAdapter();
			mTixianListView.setOnItemClickListener(this);
			mTixianListView.setAdapter(mAdapter);
		}
		setCurrentTitle(getString(R.string.woyaotixian));
	}
	
	@Override
	protected void initViews() {
		super.initViews();
		mMXButton = (Button) findViewById(R.id.mingxi_button);
		mMXButton.setText("提现记录");
		mMXButton.setVisibility(View.GONE);
		mTixianListView = (ListView) findViewById(R.id.tixianlist);
		getProductList();
	}

	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mProducts.size();
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
				view = getLayoutInflater().inflate(R.layout.list_tixian_item, null);
				holder = new ViewHolder();
				holder.tixianDes = (TextView) view.findViewById(R.id.tixian_des);
				holder.tixianNum = (TextView) view.findViewById(R.id.tixian_number);
				holder.tixianTitle = (TextView) view.findViewById(R.id.tixian_title);
				view.setTag(holder);
			}
			ZcmProduct ms = mProducts.get(position);
			holder.tixianDes.setText(ms.getPdesc());
			holder.tixianNum.setText("(剩余:"+ms.getPnum()+"份)");
			holder.tixianTitle.setText(ms.getPname());
			return view; 
		}
		
	}
	static class ViewHolder {
		TextView tixianTitle;
		TextView tixianDes;
		TextView tixianNum;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent i = new Intent(this , TiXianDialogActivity.class);
		ZcmProduct p = mProducts.get(position);
		float currentBalance = Float.parseFloat(queryLocalBalance());
		float tixianPrice = Float.parseFloat(p.getPprice());
		if((currentBalance/100) < tixianPrice){
			showToast(R.string.yuebuzu);
		} else {
			i.putExtra(INTENT_KEY_TIXIAN_ID, mProducts.get(position).getPid());
			startActivity(i);
		}
	}

}
