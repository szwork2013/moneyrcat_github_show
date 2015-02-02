package com.emperises.monercat.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emperises.monercat.R;

@SuppressLint("UseSparseArrays")
public class TurntableActivity extends Activity implements OnLongClickListener,SoundPool.OnLoadCompleteListener {
	private static final int DEFAULT_TEST_BALANCE = 15000;
	private Map<String , Integer> mSoundMap = new HashMap<String, Integer>();
	private Map<Integer, List<Integer>> mRetMap = new HashMap<Integer, List<Integer>>();
	private ImageView mLightImageView;
	private Button mPointView;
	private boolean isFisrtStartGame = true;
	private BackgroundMusic mBackgroundMusic;
	private CheckBox mPlayButton;
	private static final int NONE = 0;
	private static final int ERBAI = 2;
	private static final int SANBEI = 3;
	private static final int WUBEI = 5;
	private static final int SHIBEI = 10;
	private static final int ERSHIBEI = 20;
	private TextView mBalanceText;
	private Button mErbeiButton;
	private Button mSanbeiButton;
	private Button mWubeiButton;
	private Button mShibeiButton;
	private Button mErshibeiButton;
	private EditText mErbeiEdit;
	private EditText mSanbeiText;
	private EditText mWubeiText;
	private boolean mLightsOn = true;
	private EditText mShibeiText;
	private EditText mErshibeiText;
	private static final long ONE_WHEEL_TIME = 300;
	private static final int LAPS = 15; 
	private int mStartDegree = 0;
	private int mBaseAngles = 36;
	private int[] mRetArray = {0,20,2,3,0,10,0,3,2,5};
	private SoundPool mSoundPool;
	private int mBalance = 0;
	private void flashLights() {

		Timer timer = new Timer();
		TimerTask tt = new TimerTask() {

			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);

			}
		};

		timer.schedule(tt, 0, ONE_WHEEL_TIME);
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if (mLightsOn) {
					mLightImageView.setVisibility(View.INVISIBLE);
					mLightsOn = false;
				} else {
					mLightImageView.setVisibility(View.VISIBLE);
					mLightsOn = true;
				}
				break;

			default:
				break;
			}
		};

	};
	

	@Override
	protected void onPause() {
		mBackgroundMusic.pauseBackgroundMusic();
		super.onPause();
	}
	@Override
	protected void onResume() {
		mBackgroundMusic.resumeBackgroundMusic();
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mSoundPool.release();
	}
	
	private int setAllEditTextToInt(int tag){
		int num = 0;
		int ret = 0;
		for (int i = 0; i < mEditGroups.getChildCount(); i++) {
			EditText m = (EditText) mEditGroups.getChildAt(i);
			if((Integer)m.getTag() != tag){
				int cm = Integer.parseInt(m.getText().toString());
				num = num + cm;
			} else {
				ret = Integer.parseInt(m.getText().toString()) * (Integer)m.getTag();//����н��Ľ���
			}
		}
		mBalance = mBalance - num + ret;
		mBalanceText.setText(mBalance +"");
		return num;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_turnplate); 
		mEditGroups = (LinearLayout) findViewById(R.id.editTextGroups);
		mBalanceText = (TextView) findViewById(R.id.balanceText);
		mBalance = Integer.parseInt(mBalanceText.getText().toString());
		mBalance = DEFAULT_TEST_BALANCE;
		mBalanceText.setText(mBalance+"");
		List<Integer> zero = new ArrayList<Integer>();
		zero.add(360);
		zero.add(mBaseAngles * 4);
		zero.add(mBaseAngles * 6);
		List<Integer> two = new ArrayList<Integer>();
		two.add(mBaseAngles * 2);
		two.add(mBaseAngles * 8);
		List<Integer> three = new ArrayList<Integer>();
		three.add(mBaseAngles * 3);
		three.add(mBaseAngles * 7);
		List<Integer> five = new ArrayList<Integer>();
		five.add(mBaseAngles * 9);
		List<Integer> ten = new ArrayList<Integer>();
		ten.add(mBaseAngles * 5);
		List<Integer> ershi = new ArrayList<Integer>();
		ershi.add(mBaseAngles * 1);
		mRetMap.put(0, zero);
		mRetMap.put(2, two);
		mRetMap.put(3, three);
		mRetMap.put(5, five);
		mRetMap.put(10, ten);
		mRetMap.put(20, ershi);
		mSoundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
		mSoundPool.setOnLoadCompleteListener(this);
		mSoundMap.put("buttonEffect", mSoundPool.load(this, R.raw.button_effect, 1));
		mSoundMap.put("haobang", mSoundPool.load(this, R.raw.haobang, 1));
		mSoundMap.put("yanhua", mSoundPool.load(this, R.raw.yanhua, 1));
		mSoundMap.put("zhenkexi", mSoundPool.load(this, R.raw.zhenkexi, 1));
		mSoundMap.put("zhongle", mSoundPool.load(this, R.raw.zhongle, 1));
		mSoundMap.put("zhuanpan", mSoundPool.load(this, R.raw.xuanzhuanbg, 1));
		mSoundMap.put("one", mSoundPool.load(this, R.raw.m_one, 1));
		mSoundMap.put("two", mSoundPool.load(this, R.raw.m_two, 1));
		mSoundMap.put("three", mSoundPool.load(this, R.raw.m_three, 1));
		mSoundMap.put("four", mSoundPool.load(this, R.raw.m_four, 1));
		mSoundMap.put("five", mSoundPool.load(this, R.raw.m_five, 1));
		mBackgroundMusic = new BackgroundMusic(this);
		mBackgroundMusic.playBackgroundMusic("bg_music_1.ogg", true);
		mLightImageView = (ImageView) findViewById(R.id.light);
		mPointView =  (Button) findViewById(R.id.point);
		mPlayButton = (CheckBox) findViewById(R.id.playButton);
		mPlayButton.setChecked(false);
		mErbeiButton = (Button) findViewById(R.id.erbeiButton);
		mErbeiButton.setOnLongClickListener(this);
		mSanbeiButton = (Button) findViewById(R.id.sanbeiButton);
		mSanbeiButton.setOnLongClickListener(this);
		mWubeiButton = (Button) findViewById(R.id.wubeiButton);
		mWubeiButton.setOnLongClickListener(this);
		mShibeiButton = (Button) findViewById(R.id.shibeiButton);
		mShibeiButton.setOnLongClickListener(this);
		mErshibeiButton = (Button) findViewById(R.id.ershibeiButton);
		mErshibeiButton.setOnLongClickListener(this);
		mErbeiEdit = (EditText) findViewById(R.id.erbeiEdit);
		mErbeiEdit.setTag(2);
		mSanbeiText = (EditText) findViewById(R.id.sanbeiText);
		mSanbeiText.setTag(3);
		mWubeiText = (EditText) findViewById(R.id.wubeiText);
		mWubeiText.setTag(5);
		mShibeiText = (EditText) findViewById(R.id.shibeiText);
		mShibeiText.setTag(10);
		mErshibeiText = (EditText) findViewById(R.id.ershibeiText);
		mErshibeiText.setTag(20);
		Toast.makeText(this, "长按下注按钮,可取消下注", Toast.LENGTH_SHORT).show();
		if (mBalance >= 5) {
//			balance = balance - 5;
//			balanceText.setText(balance + "");
//			erbeiEdit.setText("5");
			setDefaultStartGameSetText();
		} else {
			Toast.makeText(this, "余额不足!", Toast.LENGTH_SHORT).show();
		}
		flashLights();
		
	}

	private AnimationListener al = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			int finalRet = mRetArray[mStartDegree % 360 / 36];
			mPlayButton.setChecked(false);
			switch (finalRet) {
			case NONE :
				mSoundPool.play(mSoundMap.get("zhenkexi"), 1, 1, 0, 0, 1);
				setAllEditTextToInt(-1);
//				clearEditContent();
				break;
			case ERBAI:
				int ebYz = Integer.parseInt(mErbeiEdit.getText().toString());
				if(ebYz != 0){
					mSoundPool.play(mSoundMap.get("zhongle"), 1, 1, 0, 0, 1);
					
					setAllEditTextToInt((Integer)mErbeiEdit.getTag());
//					balance = balance + (ebYz * 2);
					clearEditContent();
				} else {
					mSoundPool.play(mSoundMap.get("zhenkexi"), 1, 1, 0, 0, 1);
					setAllEditTextToInt(-1);
				}
				
				break;
			case SANBEI:
				int sanYz = Integer.parseInt(mSanbeiText.getText().toString());
				if(sanYz != 0 ){
					mSoundPool.play(mSoundMap.get("zhongle"), 1, 1, 0, 0, 1);
//					balance = balance + sanYz * 3;
					setAllEditTextToInt((Integer)mSanbeiText.getTag());
					clearEditContent();
				}else {
					mSoundPool.play(mSoundMap.get("zhenkexi"), 1, 1, 0, 0, 1);
					setAllEditTextToInt(-1);
				}
				break;
			case WUBEI:
				int wuYz = Integer.parseInt(mWubeiText.getText().toString());
				if(wuYz != 0){
					mSoundPool.play(mSoundMap.get("zhongle"), 1, 1, 0, 0, 1);
//					balance = balance + wuYz * 5;
					setAllEditTextToInt((Integer)mWubeiText.getTag());
					clearEditContent();
				}else {
					mSoundPool.play(mSoundMap.get("zhenkexi"), 1, 1, 0, 0, 1);
					setAllEditTextToInt(-1);
				}
				break;
			case SHIBEI:
				int shiYz = Integer.parseInt(mShibeiText.getText().toString());
				if(shiYz != 0){
					mSoundPool.play(mSoundMap.get("zhongle"), 1, 1, 0, 0, 1);
//					balance = balance + shiYz * 10;
					setAllEditTextToInt((Integer)mShibeiText.getTag());
					clearEditContent();
				}else {
					mSoundPool.play(mSoundMap.get("zhenkexi"), 1, 1, 0, 0, 1);
					setAllEditTextToInt(-1);
				}
				break;
			case ERSHIBEI:
				int ershiYz = Integer.parseInt(mErshibeiText.getText().toString());
				if(ershiYz != 0){
					mSoundPool.play(mSoundMap.get("zhongle"), 1, 1, 0, 0, 1);
//					balance = balance + ershiYz * 100;
					setAllEditTextToInt((Integer)mErshibeiText.getTag());
					clearEditContent();
				}else {
					mSoundPool.play(mSoundMap.get("zhenkexi"), 1, 1, 0, 0, 1);
					setAllEditTextToInt(-1);
				}
				break;

			default:
				break;
			}
			mBalanceText.setText(mBalance + "");
		}
	};
	private LinearLayout mEditGroups;
	private void clearEditContent(){
//		erbeiEdit.setText("0");
//		sanbeiText.setText("0");
//		wubeiText.setText("0");
//		shibeiText.setText("0");
//		ershibeiText.setText("0");
		for (int i = 0; i < mEditGroups.getChildCount(); i++) {
			EditText e = (EditText) mEditGroups.getChildAt(i);
			e.setText("0");
		}
//		setDefaultStartGameSetText();
//		int num = 0;
//		for (int i = 0; i < mEditGroups.getChildCount(); i++) {
//			EditText r = (EditText) mEditGroups.getChildAt(i);
//			num = Integer.parseInt(r.getText().toString()) + num;
//		}
//		balance = balance - num;
//		balanceText.setText(balance + "");
	}
	
	private int getResult(){
		int ret  = RandomNum.getRandomNum();
		int angle = 0;
		if(ret == 0){ 
			angle = Integer.parseInt(mRetMap.get(ret).get(new Random().nextInt(3))+"");
		}  else if(ret == 2 || ret == 3) {
			angle = Integer.parseInt(mRetMap.get(ret).get(new Random().nextInt(2))+"");
		} else {
			angle = Integer.parseInt(mRetMap.get(ret).get(0)+"");
		}
		return angle; 
	}
	
	private void setDefaultStartGameSetText(){
		if(mBalance < 5){
			Toast.makeText(this, "����!", Toast.LENGTH_SHORT).show();
			return;
		}
		int a = Integer.parseInt(mErbeiEdit.getText().toString());
		int b = Integer.parseInt(mSanbeiText.getText().toString());
		int c = Integer.parseInt(mWubeiText.getText().toString());
		int d = Integer.parseInt(mShibeiText.getText().toString());
		int e = Integer.parseInt(mErshibeiText.getText().toString());
		if(a == 0 && b == 0 && c == 0 && d == 0 && e == 0){
			mBalanceText.setText(mBalance - 5+"");
			mErbeiEdit.setText("5");
			mBalance = mBalance - 5;
		} 
	}
	private void startGame() {
		if(mBalance < 5){
			return;
		}
		if(!mPlayButton.isChecked()){
			return;
		}
		setDefaultStartGameSetText();
		mSoundPool.play(mSoundMap.get("zhuanpan"), 1, 1, 0, 0, 1);
		mPlayButton.setChecked(true);
		int lastAngle = 0;
		int angle = 0;
		int retAngle = getResult();
		if(!isFisrtStartGame){
			lastAngle = (mStartDegree-(LAPS * 360));
			angle = 360 - lastAngle + retAngle;
		} else {
			angle = retAngle;
		}
		int increaseDegree = LAPS * 360 + angle;
		RotateAnimation rotateAnimation = new RotateAnimation(lastAngle,
				mStartDegree + increaseDegree, RotateAnimation.RELATIVE_TO_SELF,
				0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		isFisrtStartGame = false;
		mStartDegree += increaseDegree;
		rotateAnimation.setDuration(3000);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setInterpolator(TurntableActivity.this,
				android.R.anim.accelerate_decelerate_interpolator);
		rotateAnimation.setAnimationListener(al);
		mPointView.startAnimation(rotateAnimation);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.erbeiButton:
			if(mPlayButton.isChecked()){
				return;
			}
			mSoundPool.play(mSoundMap.get("one"), 1, 1, 0, 0, 1);
			int cn = Integer.parseInt(mErbeiEdit.getText().toString());
			if (cn >= 100 || mBalance < 5) {
				break;
			}
			int b = cn + 5;
			mErbeiEdit.setText(b + "");
			mBalanceText.setText(mBalance - 5 + "");
			mBalance = mBalance - 5;
			break;
		case R.id.sanbeiButton:
			if(mPlayButton.isChecked()){
				return;
			}
			mSoundPool.play(mSoundMap.get("two"), 1, 1, 0, 0, 1);
			int j = Integer.parseInt(mSanbeiText.getText().toString());
			if (j >= 500 || mBalance < 5) {
				break;
			}
			int b1 = j + 5;
			mSanbeiText.setText(b1 + "");
			mBalanceText.setText(mBalance - 5 + "");
			mBalance = mBalance - 5;
			break;
		case R.id.wubeiButton:
			if(mPlayButton.isChecked()){
				return;
			}
			mSoundPool.play(mSoundMap.get("three"), 1, 1, 0, 0, 1);
			int k = Integer.parseInt(mWubeiText.getText().toString());
			if (k >= 1000 || mBalance < 5) {
				break;
			}
			int b2 = k + 5;
			mWubeiText.setText(b2 + "");
			mBalanceText.setText(mBalance - 5 + "");
			mBalance = mBalance - 5;
			break;
		case R.id.shibeiButton:
			if(mPlayButton.isChecked()){
				return;
			}
			mSoundPool.play(mSoundMap.get("four"), 1, 1, 0, 0, 1);
			int l = Integer.parseInt(mShibeiText.getText().toString());
			if (l >= 2000 || mBalance < 5) {
				break;
			}
			int b3 = l + 5;
			mShibeiText.setText(b3 + "");
			mBalanceText.setText(mBalance - 5 + "");
			mBalance = mBalance - 5;
			break;
		case R.id.ershibeiButton:
			if(mPlayButton.isChecked()){
				return;
			}
			mSoundPool.play(mSoundMap.get("five"), 1, 1, 0, 0, 1);
			int m = Integer.parseInt(mErshibeiText.getText().toString());
			if (m > 3000 || mBalance < 5) {
				break;
			}
			int b4 = m + 5;
			mErshibeiText.setText(b4 + "");
			mBalanceText.setText(mBalance - 5 + "");
			mBalance = mBalance - 5;
			break;
		case R.id.playButton:
			startGame();
			break;
		case R.id.point:
//			mPlayButton.setChecked(true);
//			startGame();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onLongClick(View view) {
		int dec = 0;
		switch (view.getId()) {
		case R.id.erbeiButton:
			dec = Integer.parseInt(mErbeiEdit.getText().toString());
			mErbeiEdit.setText("0");
			break;
		case R.id.sanbeiButton:
			dec = Integer.parseInt(mSanbeiText.getText().toString());
			mSanbeiText.setText("0");
			break;
		case R.id.wubeiButton:
			dec = Integer.parseInt(mWubeiText.getText().toString());
			mWubeiText.setText("0");
			break;
		case R.id.shibeiButton:
			dec = Integer.parseInt(mShibeiText.getText().toString());
			mShibeiText.setText("0");
			break;
		case R.id.ershibeiButton:
			dec = Integer.parseInt(mErshibeiText.getText().toString());
			mErshibeiText.setText("0");
			break;
			
		default:
			break;
		}
		mBalance = mBalance + dec;
		mBalanceText.setText(mBalance + "");
		return true;
	}
	@Override
	public void onLoadComplete(SoundPool sound, int sampleId, int state) {
		Log.i("LOAD", "�������:id"+sampleId);
		
	}

}
