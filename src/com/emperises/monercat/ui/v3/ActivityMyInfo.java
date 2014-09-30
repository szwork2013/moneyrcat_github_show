package com.emperises.monercat.ui.v3;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.customview.headerimage.CropImageActivity;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.interfacesandevents.HeaderImageEvent;
import com.emperises.monercat.ui.BindActivity;
import com.emperises.monercat.ui.MingXiActivity;

@SuppressLint("NewApi")
public class ActivityMyInfo extends OtherBaseActivity {
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	public static final File FILE_SDCARD = Environment
			.getExternalStorageDirectory();
	public static final String IMAGE_PATH = "moneycat";
	public static final File FILE_LOCAL = new File(FILE_SDCARD, IMAGE_PATH);
	public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
			"images/screenshots");
	private static String localTempImageFileName = "";
	private ImageView mHeadImage;
	private PopupWindow mPopupWindow;
	private TextView mInfoNicknameText;
	private TextView mCurrentBalance;
	private TextView mGenderAgeAddr;
	private TextView mTel;
	private TextView mRecommendCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		setCurrentTitle("我的信息");
	}

	@Override
	protected void initViews() {
		super.initViews();
		mHeadImage = (ImageView) findViewById(R.id.headerImage);
		mHeadImage.setBackgroundResource(getHeadImageResId());
		mInfoNicknameText = (TextView) findViewById(R.id.myinfo_nickname);
		mCurrentBalance = (TextView) findViewById(R.id.myinfo_balance);
		mCurrentBalance.setText(queryLocalBalance()+getString(R.string.m_gold));
		mGenderAgeAddr = (TextView) findViewById(R.id.myinfo_normalinfo);
		mRecommendCode = (TextView) findViewById(R.id.myinfo_recommend_code);
		mTel = (TextView) findViewById(R.id.myinfo_tel_text);
		setMyInfo();
	}

	private void setMyInfo() {
		ZcmUser info = getMyInfoForDatabase();
		if(info != null){
			String gender = info.getUsex();
			String age = info.getUage();
			String addr = info.getUaddress();
			mRecommendCode.setText(info.getUtgm());
			mGenderAgeAddr.setText(gender+age+addr);
			mInfoNicknameText.setText(info.getUname());
			mTel.setText(info.getUtelephone());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
			if (data != null) {
				Uri uri = data.getData();
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					Cursor cursor = getContentResolver().query(uri,
							new String[] { MediaStore.Images.Media.DATA },
							null, null, null);
					if (null == cursor) {
						showToast(R.string.picture_nofound);
						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					cursor.close();
					Intent intent = new Intent(
							this,
							com.emperises.monercat.customview.headerimage.CropImageActivity.class);
					intent.putExtra("path", path);
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				} else {
					Intent intent = new Intent(
							this,
							com.emperises.monercat.customview.headerimage.CropImageActivity.class);
					intent.putExtra("path", uri.getPath());
					startActivityForResult(intent, FLAG_MODIFY_FINISH);
				}
			}
		} else if (requestCode == FLAG_CHOOSE_PHONE && resultCode == RESULT_OK) {
			File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);
			Intent intent = new Intent(this, CropImageActivity.class);
			intent.putExtra("path", f.getAbsolutePath());
			startActivityForResult(intent, FLAG_MODIFY_FINISH);
		} else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
			if (data != null) {
				final String path = data.getStringExtra("path");
				Bitmap b = BitmapFactory.decodeFile(path);
				mHeadImage.setImageBitmap(b);
			}
		}
	}
	//拍照
	private void takePhoto(){
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				localTempImageFileName = "";
				localTempImageFileName = String.valueOf((new Date())
						.getTime()) + ".png";
				File filePath = FILE_PIC_SCREENSHOT;
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				File f = new File(filePath, localTempImageFileName);
				// localTempImgDir和localTempImageFileName是自己定义的名字
				Uri u = Uri.fromFile(f);
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
				startActivityForResult(intent, FLAG_CHOOSE_PHONE);
			} catch (ActivityNotFoundException e) {
				//
			}
		}
	}
	//图库选择
	private void selectedPhoto(){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, FLAG_CHOOSE_IMG);
	}
	private void showSelectedImagePopupWindow(){
		GridView headerLayout = (GridView) getLayoutInflater().inflate(R.layout.activity_headimageselect, null);
		final List<Integer> mHeaders = new ArrayList<Integer>();
		mHeaders.add(R.drawable.test_headimage1);
		mHeaders.add(R.drawable.test_headimage2);
		mHeaders.add(R.drawable.test_headimage3);
		mHeaders.add(R.drawable.test_headimage4);
		mHeaders.add(R.drawable.test_headimage5);
		mHeaders.add(R.drawable.test_headimage6);
		headerLayout.setAdapter(new MyAdaprer(mHeaders));
		mPopupWindow = new PopupWindow(headerLayout,MarginLayoutParams.WRAP_CONTENT,MarginLayoutParams.WRAP_CONTENT,true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(-000000));
		mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
		mPopupWindow.showAsDropDown(mHeadImage);
		headerLayout.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				mPopupWindow.dismiss();
				mHeadImage.setBackgroundResource(mHeaders.get(position));
				HeaderImageEvent.getInstance().fireHeaderChangeImageEvent(mHeaders.get(position));
				setIntForKey(LOCAL_CONFIGKEY_HEADER_RESID, mHeaders.get(position));
			}
		});
		
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.headerImage:
			//选择默认头像，弹出PopupWindow
			showSelectedImagePopupWindow();
			break;
		case R.id.myinfo_yue:
			startActivity(new Intent(this , MingXiActivity.class));
			break;
		case R.id.myinfo_mylink:
			showToast("已经复制到剪切板");
			break;
		case R.id.myinfo_erweima:
			startActivity(new Intent(this , ActivityQRCode.class));
			break;
		case R.id.myinfo_edit:
			startActivity(new Intent(this , ActivityEditMyinfo.class));
			break;
		case R.id.myinfo_tel:
			String tel = mTel.getText().toString();
			if(!tel.isEmpty() && tel.contains("未绑定")){
				startActivity(new Intent(this , BindActivity.class));
			}
			break;
		default:
			break;
		}
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
//			RelativeLayout i = (RelativeLayout) getLayoutInflater().inflate(R.layout.girdview_headimage, null);
//			ImageView h = (ImageView) i.findViewById(R.id.headerimageitem);
			ImageView h = new ImageView(ActivityMyInfo.this);
			h.setBackgroundResource(mHeaders.get(position));
			return h;
		}
		
	}
	@Override
	public void onMyInfoChange(ZcmUser info) {
		setMyInfo();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
