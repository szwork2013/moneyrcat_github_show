package com.emperises.monercat.ui.v3;

import java.io.File;
import java.io.FileNotFoundException;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.UploadImageUtil;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

public class UploadImageActivity extends OtherBaseActivity {

	private Button mSelectedImage;
	private ImageView mUploadImage;
	private Button mUploadSelected;
	private File mUploadImagePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		setCurrentTitle(R.string.upload_image_title_str);
		mAdId = getIntent().getStringExtra("adId");
		mHaveDes = getIntent().getIntExtra("des", 0);
		
	}

	@Override
	protected void initViews() {
		super.initViews();
		mDesEditText = (EditText) findViewById(R.id.desEditText);
		mUploadSelected = (Button) findViewById(R.id.upload_image_bt);
		mUploadImage = (ImageView) findViewById(R.id.uploadImage);
		mSelectedImage = (Button) findViewById(R.id.select_image_bt);
		mPorgressBar = (ProgressBar) findViewById(R.id.progressbar);
	}


	private void upload(){
		if(mUploadImagePath == null){
			showToast(R.string.please_select_image_toast);
		} else {
			//开始上传
			AjaxParams params = new AjaxParams();
			params.put("extUdevicesId", Util.getDeviceId(this));
			params.put("extAdId", mAdId);
				try {
					params.put("extImage", mUploadImagePath);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			getHttpClient().post(SERVER_URL_UPLOAD_FILE,params ,new AjaxCallBack<String>() {
				@Override
				public void onSuccess(String t) {
					Logger.i("UPLOAD", t);
					DomainObject d = new Gson().fromJson(t, DomainObject.class);
					if(d != null){
						mSelectedImage.setEnabled(true);
						mUploadSelected.setEnabled(true);
						if(HTTP_RESULE_SUCCESS.equals(d.getResultCode())){
							showToast(R.string.upload_image_loading_str);
							finish();
						} else {
							showToast(d.getResultMsg());
						}
					}
					mPorgressBar.setVisibility(View.GONE);
					super.onSuccess(t);
				}
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					showNetErrorToast(strMsg, t);
					mPorgressBar.setVisibility(View.GONE);
					mSelectedImage.setEnabled(true);
					mUploadSelected.setEnabled(true);
				}
				@Override
				public void onStart() {
					mPorgressBar.setVisibility(View.VISIBLE);
					mSelectedImage.setEnabled(false);
					mUploadSelected.setEnabled(false);
					super.onStart();
				}
				@Override
				public void onLoading(long count, long current) {
					super.onLoading(count, current);
					mPorgressBar.setMax((int) count);
					mPorgressBar.setProgress((int) current);
					Logger.i("UPLOAD", "loading -- "+ count + " -- " + current);
				}
			});
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.upload_image_bt:
			//上传图片
			upload();
			break;
		case R.id.select_image_bt:
			//选择图片
			String des = mDesEditText.getText().toString();
			if(mHaveDes > 0){
				//必须要输入描述
				if(!TextUtils.isEmpty(des)){
					selectedPhoto();
				} else {
					showToast("请您输入描述!描述是任务审核通过的关键!");
				}
			} else {
				selectedPhoto();
			}
			break;
			
		default:
			break;
		}
	}
	private static final int FLAG_CHOOSE_IMG = 5;
	private ProgressBar mPorgressBar;
	private String mAdId;
	private int mHaveDes;
	private EditText mDesEditText;

	// 图库选择
	private void selectedPhoto() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, FLAG_CHOOSE_IMG);
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
					Logger.i("PATH", "选择的图片:"+path);
					String text = mDesEditText.getText().toString();
					Bitmap image = UploadImageUtil.getSmallBitmap(path,text);
					mUploadImage.setImageBitmap(image);
					//保存图片
					mUploadImagePath = UploadImageUtil.saveImage(image, path);
				}
			}
		}
	}
}
