package com.emperises.monercat.ui.v3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.customview.headerimage.CropImageActivity;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.interfacesandevents.EditMyInfoInterface;
import com.emperises.monercat.interfacesandevents.HeaderImageEvent;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

@SuppressLint("NewApi")
public class ActivityEditMyinfo extends OtherBaseActivity implements
		EditMyInfoInterface {
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
	private TextView mAddressText;
	private TextView mAgeText;
	private TextView mGenderText;
	private TextView mNicknameText;
	private ZcmUser mInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editmyinfo);
		setCurrentTitle("编辑信息");

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
				mHeaderFilePath = data.getStringExtra("path");
				Bitmap b = BitmapFactory.decodeFile(mHeaderFilePath);
				mHeadImage.setImageBitmap(b);
			}
		}
	}

	@Override
	protected void initViews() {
		super.initViews();
		ZcmUser info = getMyInfoForDatabase();
		mHeadImage = (ImageView) findViewById(R.id.editinfo_headerimage);
		int wh = Util.dip2px(50, this);
		displayHeaderImage(mHeadImage, wh, wh);
		mAddressText = (TextView) findViewById(R.id.editinfo_addresstext);
		mAgeText = (TextView) findViewById(R.id.editinfo_agetext);
		mGenderText = (TextView) findViewById(R.id.editinfo_gendertext);
		mNicknameText = (TextView) findViewById(R.id.editinfo_nicknametext);
		mInfo = getDatabaseInterface().getMyInfo();
		mCommitButton = (Button) findViewById(R.id.editinfo_done_button);
		if (info != null) {
			mAddressText.setText(info.getUaddress());
			mAgeText.setText(info.getUage());
			mGenderText.setText(info.getUsex());
			mNicknameText.setText(info.getUname());
		}
	}

	/**
	 * 选择照片
	 */
	private void showSelectedHeaderImageDialog() {
		final String[] items = getResources().getStringArray(R.array.photo);
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("请选择");
		dialog.setItems(items, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int witch) {
				if (witch == 0) {
					selectedPhoto();
				} else {
					takePhoto();
				}
			}
		});
		dialog.show();
	}

	// 拍照
	private void takePhoto() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				localTempImageFileName = "";
				localTempImageFileName = String.valueOf((new Date()).getTime())
						+ ".png";
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

	// 图库选择
	private void selectedPhoto() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, FLAG_CHOOSE_IMG);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, ActivityEditText.class);
		// mAgeText.setText(age);
		// mNicknameText.setText(nickNmae);
		// mGenderText.setText(gender);
		// mAddressText.setText(address);
		super.onClick(v);
		switch (v.getId()) {
		case R.id.editinfo_address:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_address);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mAddressText.getText().toString());
			startActivity(i);
			break;
		case R.id.editinfo_age:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_age);
			i.putExtra(INTENT_KEY_EDIT_VALUE,
					replaceAgeStringEmpty(mInfo.getUage()));
			startActivity(i);
			break;
		case R.id.editinfo_gender:
			showGenderDialog();
			break;
		case R.id.editinfo_nickname:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_nickname);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mNicknameText.getText()
					.toString());
			startActivity(i);
			break;
		case R.id.editinfo_done_button:
			String mAddress = mAddressText.getText().toString();
			String mAge = mAgeText.getText().toString();
			String mGend = mGenderText.getText().toString();
			String mNickname = mNicknameText.getText().toString();
			if (!TextUtils.isEmpty(mAddress) && !TextUtils.isEmpty(mAge) && !TextUtils.isEmpty(mGend)
					&& !TextUtils.isEmpty(mNickname)) {
				saveMyInfo();
			} else {
				showToast("您填写的信息不完整!"); 
			}
			break;
		case R.id.editinfo_headerimage_layout:
			showSelectedHeaderImageDialog();
			break;
		default:
			break;
		}

	}

	private int mCurrentGengerSelected = 0;
	private ImageView mHeadImage;
	private String mHeaderFilePath = "";
	private Button mCommitButton;

	private void showGenderDialog() {
		final String[] items = getResources().getStringArray(R.array.sexitem);
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("请选择");
		dialog.setSingleChoiceItems(items, mCurrentGengerSelected,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Logger.i("SELECTED", "current which = " + which);
						mCurrentGengerSelected = which;
						mInfo.setUsex(items[which]);
						mGenderText.setText(mInfo.getUsex());
						dialog.dismiss();
					}

				});
		dialog.show();
	}

	

	private void saveMyInfo() {

		// 提交到服务器
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		params.put("action_type", "0");
		params.put("uname", mInfo.getUname());
		params.put("usex", mInfo.getUsex());
		params.put("uadress", mInfo.getUaddress());
		params.put("uage", replaceAgeStringEmpty(mInfo.getUage()));
		try {
			params.put("file", new File(mHeaderFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		getHttpClient().post(SERVER_URL_SAVEUSERINFO, params,
				new AjaxCallBack<String>() {
					@Override
					public void onStart() {
						super.onStart();
						mCommitButton.setClickable(false);
					}

					@Override
					public void onSuccess(String t) {
						mCommitButton.setClickable(true);
						Logger.i("USERINFO", t);
						DomainObject ret = new Gson().fromJson(t,
								DomainObject.class);
						if (ret.getResultCode().equals(HTTP_RESULE_SUCCESS)) {
							showCommitOkToast();
							getDatabaseInterface().saveMyInfo(mInfo,
									ActivityEditMyinfo.this);
							setStringtForKey(LOCAL_CONFIGKEY_HEADER_PATH,
									mHeaderFilePath);
							HeaderImageEvent
									.getInstance()
									.fireHeaderChangeImageEvent(mHeaderFilePath);
							finish();
						} else {
							showNetErrorToast(ret.getResultMsg(), null);
						}
						super.onSuccess(t);
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						Logger.e("ERROR", strMsg, t);
						showNetErrorToast(strMsg, t);
						mCommitButton.setClickable(true);
					}
				});

	}

	@Override
	public void onAgeEditAfter(String age) {
		mAgeText.setText(age + "岁");
		mInfo.setUage(age);
	}

	@Override
	public void onNickNameEditAfter(String nickNmae) {
		mNicknameText.setText(nickNmae);
		mInfo.setUname(nickNmae);
	}

	@Override
	public void onGenderEditAfter(String gender) {
		mGenderText.setText(gender);
		mInfo.setUsex(gender);
	}

	@Override
	public void onAddressEditAfter(String address) {
		mAddressText.setText(address);
		mInfo.setUaddress(address);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
