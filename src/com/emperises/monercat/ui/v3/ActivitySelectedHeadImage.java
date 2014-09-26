package com.emperises.monercat.ui.v3;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class ActivitySelectedHeadImage extends OtherBaseActivity{

	private GridView mHeaderImageSelectGridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_headimageselect);
	}
	@Override
	protected void initViews() {
		super.initViews();
		mHeaderImageSelectGridView = (GridView) findViewById(R.id.headerImageSelectGridView);
		List<Integer> mHeaders = new ArrayList<Integer>();
		mHeaders.add(R.drawable.test_headimage1);
		mHeaders.add(R.drawable.test_headimage2);
		mHeaders.add(R.drawable.test_headimage3);
		mHeaders.add(R.drawable.test_headimage4);
		mHeaders.add(R.drawable.test_headimage5);
		mHeaders.add(R.drawable.test_headimage6);
		mHeaderImageSelectGridView.setAdapter(new MyAdaprer(mHeaders));
	}
	
	class MyAdaprer extends BaseAdapter{
		private List<Integer> mHeaders = new ArrayList<Integer>();
		public MyAdaprer(List<Integer> mHeaders) {
			this.mHeaders = mHeaders; 
		}
		@Override
		public int getCount() {
			return mHeaders.size();
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
			ImageView i = new ImageView(ActivitySelectedHeadImage.this);
			i.setBackgroundResource(mHeaders.get(position));
			return i;
		}
		
	}
}
