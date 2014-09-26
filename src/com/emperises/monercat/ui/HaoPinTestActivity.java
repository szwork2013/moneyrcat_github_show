package com.emperises.monercat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.emperises.monercat.R;

public class HaoPinTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView i = new ImageView(this);
		i.setBackgroundResource(R.drawable.haopingtest);
		setContentView(i);
	}
	
}
