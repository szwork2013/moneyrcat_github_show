package com.emperises.monercat.customview;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emperises.monercat.R;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;

public class CustomToast extends Toast {

	public CustomToast(Context context) {
		super(context);
	}

	public static void makeText(Context context, CharSequence text) {
		makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void makeText(Context context, int resId) {
		makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	public static Toast makeGoldText(Context context, int resId,
			CharSequence text) {
		Toast result = new Toast(context);

		// 获取LayoutInflater对象
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 由layout文件创建一个View对象
		View layout = inflater.inflate(R.layout.custom_toast_layout, null);

		// 实例化ImageView和TextView对象
		ImageView imageView = (ImageView) layout.findViewById(R.id.toast_icon);
		TextView textView = (TextView) layout.findViewById(R.id.toast_text);

		imageView.setImageResource(resId);
		textView.setText(text);

		result.setView(layout);
		result.setGravity(Gravity.BOTTOM, 0, Util.dip2px(50, context));
		result.setDuration(Toast.LENGTH_SHORT);
		final SoundPool soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
		final Map<Integer, Integer> soundMap = new HashMap<Integer, Integer>();  
//		soundMap.put(1, soundPool.load(context, R.raw.gold_effect, 1));
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
				Logger.i("SOUND", "音频已经加载好");
				soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);
			}
		});
		return result;
	}

}
