package com.emperises.monercat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.emperises.monercat.customview.CustomDialog.DialogClick;
import com.emperises.monercat.customview.CustomDialogConfig;
import com.emperises.monercat.customview.CustomToast;
import com.emperises.monercat.customview.DialogManager;
import com.emperises.monercat.database.DatabaseImpl;
import com.emperises.monercat.database.DatabaseInterface;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.domain.model.BalanceMode;
import com.emperises.monercat.domain.model.ZcmUser;
import com.emperises.monercat.interfacesandevents.BalanceEvent;
import com.emperises.monercat.interfacesandevents.BalanceInterface;
import com.emperises.monercat.interfacesandevents.EditMyInfoEvent;
import com.emperises.monercat.interfacesandevents.EditMyInfoInterface;
import com.emperises.monercat.interfacesandevents.HeaderImageChangeInterface;
import com.emperises.monercat.interfacesandevents.HeaderImageEvent;
import com.emperises.monercat.interfacesandevents.HttpInterface;
import com.emperises.monercat.interfacesandevents.HttpRequest;
import com.emperises.monercat.interfacesandevents.LocalConfigKey;
import com.emperises.monercat.interfacesandevents.LogTag;
import com.emperises.monercat.interfacesandevents.UrlPostInterface;
import com.emperises.monercat.ui.MingXiActivity;
import com.emperises.monercat.ui.v3.ActivityMyInfo;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

@SuppressLint("NewApi")
public abstract class BaseActivity extends Activity implements OnClickListener,
		HttpInterface, LocalConfigKey, HeaderImageChangeInterface,
		BalanceInterface, EditMyInfoInterface, UrlPostInterface, LogTag {

	private FinalHttp mFinalHttp;
	private TextView titleText;
	private SharedPreferences sp;
	protected int mWindowScreenW = 0;
	protected int mWindowScreenH = 0;

	protected FinalBitmap getFinalBitmap() {
		FinalBitmap f = FinalBitmap.create(this);
		return f;
	}

	protected String replaceAgeStringEmpty(String age) {
		if (!TextUtils.isEmpty(age) && age.contains("岁")) {
			age = age.replace("岁", "");
		}
		return age;
	}

	@Override
	public void onHeaderImageChange(String path) {

		// Logger.i("HEADER", "path:"+path);
		// ImageView header = (ImageView) findViewById(R.id.myheaderimage);
		// ImageView headerInfo = (ImageView) findViewById(R.id.headerImage);
		// Bitmap image = BitmapFactory.decodeFile(path);
		// if (header != null) {
		// header.setImageBitmap(image);
		// }
		// if (headerInfo != null) {
		// headerInfo.setImageBitmap(image);
		// }
	}

	// 余额改变时
	@Override
	public void onBalanceChange(String currentBalance) {
		TextView ye = (TextView) findViewById(R.id.yue_balance);
		if (ye != null) {
			float ci = Float.parseFloat(currentBalance) / 100;
			ye.setText("余额:" + currentBalance + getString(R.string.m_gold)
					+ "(" + ci + "元)");
			Logger.i("BALANCE", "余额改变:" + currentBalance
					+ getString(R.string.m_gold) + "(" + ci + "元)");
		}
	}

	// 获得头像资源id
	// protected int getHeadImageResId() {
	// int resId = getIntValueForKey(LOCAL_CONFIGKEY_HEADER_RESID);
	// if (resId == 0) {
	// resId = R.drawable.test_headimage1;
	// setIntForKey(LOCAL_CONFIGKEY_HEADER_RESID,
	// R.drawable.test_headimage1);
	// }
	// return resId;
	// }
	protected String getHeadImageResPath() {
		return getStringValueForKey(LOCAL_CONFIGKEY_HEADER_PATH);
	}

	protected String getHeadImageResUrl() {
		return getStringValueForKey(LOCAL_CONFIGKEY_HEADER_IMAGE_URL);
	}

	// 查询当前余额
	protected String queryLocalBalance() {
		ZcmUser info = getMyInfoForDatabase();
		String currentBalance = "";
		if (info != null) {
			currentBalance = info.getBalance();
		} else {
			Logger.i("BALANCE", "queryBalance : info ＝ null");
		}
		return currentBalance;
	}

	// 获取余额并存储到本地数据库中
	public void updateBalance() {
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(this));
		getHttpClient().post(SERVER_URL_BALANCE, params,
				new AjaxCallBack<String>() {
					@Override
					public void onSuccess(String t) {
						super.onSuccess(t);
						Logger.i(TAG_HTTP, t);
						// 将余额写入数据库
						BalanceMode ret = new Gson().fromJson(t,
								BalanceMode.class);
						String currentBalance = "0.0";
						if (ret != null) {
							currentBalance = ret.getVal();
							ZcmUser info = new ZcmUser();
							info.setBalance(currentBalance);
							getDatabaseInterface().saveMyInfo(info,
									BaseActivity.this);
							// 发起余额变更事件
							BalanceEvent.getInstance().fireBalanceChange(
									currentBalance);
						}
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						showNetErrorToast(strMsg, t);
					}
				});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myheaderimage:
			startActivity(new Intent(this, ActivityMyInfo.class));
			break;
		case R.id.leftItem:
			startActivityWithAnimation(new Intent(this, MingXiActivity.class));
			break;
		case R.id.rightItem:
			showToast(R.string.signtoast);
			break;

		default:
			break;
		}
	}

	/**
	 * 获得屏幕宽高
	 * 
	 * @return
	 */
	protected int[] getWindowScreenWH(Context context) {
		android.util.DisplayMetrics dm = new android.util.DisplayMetrics();
		// 获取屏幕信息
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeigh = dm.heightPixels;
		int[] i = new int[] { screenWidth, screenHeigh };
		Logger.i("SCREEN", "宽:" + screenWidth + "--高:" + screenHeigh);
		return i;
	}

	protected SharedPreferences getSP() {
		return getSharedPreferences("config", MODE_PRIVATE);
	}

	protected void showCommitOkToast() {
		showToast(R.string.commitsuccess);
	}

	protected void showNetErrorToast(String msg, Throwable t) {
		showToast(R.string.NET_ERROR);
		Logger.e("ERROR", msg, t);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initBaseData();
		initBaseView();
		initViews();
	}

	public String getCurrentTitle() {
		String title = "";
		if (titleText != null) {
			title = titleText.getText().toString();
		}
		return title;
	}

	protected void setCurrentTitle(String title) {
		if (titleText != null) {
			titleText.setText(title);
		}
	}

	protected void startActivityWithAnimation(Intent i) {
		startActivity(i);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

	private static final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");
	private DatabaseInterface mDatabase;

	public DatabaseInterface getDatabaseInterface() {
		return mDatabase;
	}

	protected void setShareInfo() {
		setZcmShareInfo();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 getWindowScreenWH(this);
		// mWindowScreenW = s[0];
		// mWindowScreenH = s[1];
		sp = getSharedPreferences("config", MODE_PRIVATE);
		float oldVersion = getFloatValueForKey(VERSION);
		float currentVersion = Util.getLocalVersionCode(this);
		if (currentVersion > oldVersion) {
			// 判断是否是新版本
			// 如果上一个版本数据库存在就删除掉
			File mDbPath = getDatabasePath("moneycat.db");
			if (mDbPath.exists()) {
				mDbPath.delete();
			}
		}
		setShareInfo();
		super.onCreate(savedInstanceState);
		if (mDatabase == null) {
			mDatabase = new DatabaseImpl(this, null);// TODO:创建数据库
			List<Class<?>> classs = new ArrayList<Class<?>>();
			classs.add(ZcmUser.class);
			mDatabase.createTableDatabaseForListClass(classs);
		}
		// testDB(d.getDatabase());
		mFinalHttp = new FinalHttp();

		EditMyInfoEvent.getInstance().addEditInfoListener(this);
	}

	protected void showDialog(CustomDialogConfig config) {
		DialogManager.getInstance(this, config).show();
	}

	/**
	 * 显示一个标准的自定义对话框
	 * 
	 * @param title
	 * @param message
	 * @param sureClickListener
	 *            “确定”点击事件
	 * @param cancleClickListener
	 *            “取消”点击事件
	 */
	protected void showDialog(String title, String message,
			DialogClick sureClickListener, DialogClick cancleClickListener) {
		CustomDialogConfig config = new CustomDialogConfig();
		config.setCancelListener(cancleClickListener);
		config.setSureListener(sureClickListener);
		config.setMessage(message);
		config.setTitle(title);
		showDialog(config);
	}

	protected void setStringtForKey(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	protected void setBooleanForKey(String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	protected void setIntForKey(String key, int value) {
		sp.edit().putInt(key, value).commit();
	}

	protected void setFloatForKey(String key, float value) {
		sp.edit().putFloat(key, value).commit();
	}

	protected String getStringValueForKey(String key) {
		return sp.getString(key, "");
	}

	protected float getFloatValueForKey(String key) {
		return sp.getFloat(key, 0.0f);
	}

	protected boolean getBoleanValueForKey(String key) {
		return sp.getBoolean(key, false);
	}

	protected int getIntValueForKey(String key) {
		return sp.getInt(key, 0);
	}

	protected SQLiteDatabase getDatabase() {
		return mDatabase.getDatabase();

	}

	private String shareUrl;
	private String shareTitle;
	private String shareContent;
	private String shareLogoUrl;

	// 分享招财瞄
	protected void openShareForZcm() {
		shareUrl = getString(R.string.http_www_emperises_com_);
		shareTitle = getString(R.string.share_title);
		shareContent = getString(R.string.share_content);
		shareLogoUrl = "http://115.28.136.194:8086/zcm/ex/img/share_logo.png";
		resetShareSdk();
		openShare();
	}

	// 设置默认的分享信息
	protected void setZcmShareInfo() {
		shareUrl = getString(R.string.http_www_emperises_com_);
		shareTitle = getString(R.string.share_title);
		shareContent = getString(R.string.share_content);
		shareLogoUrl = "http://115.28.136.194:8086/zcm/ex/img/share_logo.png";
	}

	protected void setShareLogoUrl(String url) {
		this.shareLogoUrl = url;
	}

	protected void setShareUrl(String url) {
		this.shareUrl = url;
	}

	protected void setShareTitle(String title) {
		this.shareTitle = title;
	}

	protected void setShareContent(String content) {
		this.shareContent = content;
	}

	// 重新加载SDK
	protected void resetShareSdk() {
		// 清理接口事件
		mController.getConfig().cleanListeners();
		// QQ好友
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1101962112",
				"RY1S5XEVSVnjx3B7");
		qqSsoHandler.setTitle(shareTitle);
		qqSsoHandler.setTargetUrl(shareUrl);
		qqSsoHandler.addToSocialSDK();

		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(shareContent);
		qqShareContent.setTitle(shareTitle);
		qqShareContent.setShareImage(new UMImage(this, shareLogoUrl));
		qqShareContent.setTargetUrl(shareUrl);
		mController.setShareMedia(qqShareContent);
		// QQ空间
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
				"1101962112", "RY1S5XEVSVnjx3B7");
		QZoneShareContent content = new QZoneShareContent();
		qZoneSsoHandler.setTargetUrl(shareUrl);
		qZoneSsoHandler.addToSocialSDK();
		content.setShareContent(shareContent);
		content.setShareImage(new UMImage(this, shareLogoUrl));
		content.setTargetUrl(shareUrl);
		content.setTitle(shareTitle);
		mController.setShareMedia(content);
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appID = "wxcec4566d135405e6";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appID);
		wxHandler.setTitle(shareTitle);
		wxHandler.setTargetUrl(shareUrl);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID);
		wxCircleHandler.setTitle(shareTitle);
		wxCircleHandler.setTargetUrl(shareUrl);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		// 为了保证人人分享成功且能够在PC上正常显示，请设置website
		mController.setAppWebSite(SHARE_MEDIA.RENREN, shareUrl);
		// 设置分享到微信的内容, 图片类型
		UMImage mUMImgBitmap = new UMImage(this, shareLogoUrl);// TODO:改为网页LOGO图片
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTitle(shareTitle);
		weixinContent.setTargetUrl(shareUrl);
		weixinContent.setShareContent(shareContent);
		weixinContent.setShareImage(mUMImgBitmap);
		mController.setShareMedia(weixinContent);
		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setTitle(shareTitle);
		circleMedia.setTargetUrl(shareUrl);
		circleMedia.setShareContent(shareContent);
		circleMedia.setShareImage(new UMImage(this, shareLogoUrl));// TODO:改为网页LOGO图片
		mController.setShareMedia(circleMedia);

		// 新浪分享内容
		SinaShareContent sinaContent = new SinaShareContent();
		sinaContent.setTitle(shareTitle);
		sinaContent.setTargetUrl(shareUrl);
		sinaContent.setShareContent(shareContent + "\n详情点击:" + shareUrl);
		sinaContent.setShareImage(new UMImage(this, shareLogoUrl));
		mController.setShareMedia(sinaContent);

		TencentWbShareContent tencentContent = new TencentWbShareContent();
		tencentContent.setTitle(shareTitle);
		tencentContent.setTargetUrl(shareUrl);
		tencentContent.setShareImage(new UMImage(this, shareLogoUrl));
		// 设置分享到腾讯微博的文字内容
		tencentContent.setShareContent(shareContent + "\n详情点击:" + shareUrl);
		// 设置分享到腾讯微博的多媒体内容
		mController.setShareMedia(tencentContent);
		// 设置分享图片，参数2为图片的url.
		mController.setShareMedia(new UMImage(this, shareLogoUrl));
	}

	protected boolean isFirstRun() {
		if (!getBoleanValueForKey(LOCAL_CONFIGKEY_FIRSTRUN)) {
			setBooleanForKey(LOCAL_CONFIGKEY_FIRSTRUN, true);
			return true;
		}
		return false;

	}

	protected void openShare() {
		resetShareSdk();
		mController.openShare(this, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1,
					SocializeEntity arg2) {
			}
		});
	}

	protected void startRequest() {
	}

	protected void startRequest(String url, AjaxParams params) {
		mFinalHttp.post(url, params, new HttpRequest(this));
	}

	@Override
	public void onFinished(String content) {
	}

	public final FinalHttp getHttpClient() {
		return mFinalHttp;
	}

	abstract protected void initViews();

	private void initBaseData() {
		if (isFirstRun()) {
			// 个人信息不在数据库中不能被初始化两次
			ZcmUser z = new ZcmUser();
			z.setUdevicesId(Util.getDeviceId(this));
			List<DomainObject> objs = new ArrayList<DomainObject>();
			objs.add(z);
			getDatabaseInterface().insertDataForObjs(objs);
//			setBooleanForKey(LOCAL_CONFIGKEY_DATEBASE_INSERT_FLG, true);
		}
	}

	private void initBaseView() {
		titleText = (TextView) findViewById(R.id.titleText);
		if (findViewById(R.id.leftItem) != null
				&& findViewById(R.id.rightItem) != null) {
			Button leftButton = (Button) findViewById(R.id.leftItem);
			leftButton.setOnClickListener(this);
			Button rightButton = (Button) findViewById(R.id.rightItem);
			rightButton.setOnClickListener(this);
		}
		BalanceEvent.getInstance().addBalanceListener(this);
		setBaseHeaderInfo();

	}

	protected void displayHeaderImage(ImageView imageView, int w, int h) {
		String path = getHeadImageResPath();
		if (!TextUtils.isEmpty(path) && new File(path).exists()) {
			// 如果有本地头像
			Bitmap image = BitmapFactory.decodeFile(path);
			imageView.setImageBitmap(image);
		} else {
			// 显示网络头像
			String uri = getStringValueForKey(LOCAL_CONFIGKEY_HEADER_IMAGE_URL);
			// 默认头像
			Bitmap defaultHeader = BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_launcher);
			Bitmap onlineImageCache = getFinalBitmap().getBitmapFromDiskCache(
					uri);
			if (onlineImageCache == null) {// 如果没有本地缓存
				Logger.i("CACHE", "没有缓存" + imageView.toString());
				getFinalBitmap().display(imageView, uri, w, h, defaultHeader,
						defaultHeader);
			} else {
				Logger.i("CACHE", "有缓存：" + imageView.toString());
				imageView.setImageBitmap(onlineImageCache);
			}
		}
	}

	// 顶部信息
	private void setBaseHeaderInfo() {
		Logger.i("HEADER", "setBaseHeaderInfo");
		ImageView mHeader = (ImageView) findViewById(R.id.myheaderimage);
		TextView ye = (TextView) findViewById(R.id.yue_balance);
		TextView tel = (TextView) findViewById(R.id.yue_tel);
		TextView nickname = (TextView) findViewById(R.id.yue_nickname);
		ZcmUser info = getMyInfoForDatabase();
		if (ye != null && info != null) {
			HeaderImageEvent.getInstance().addHeaderImageListener(this);
			int mHeaderWH = Util.dip2px(35, getApplicationContext());
			displayHeaderImage(mHeader, mHeaderWH, mHeaderWH);
			mHeader.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(new Intent(BaseActivity.this,
							ActivityMyInfo.class));
				}
			});
			String currentBalance = queryLocalBalance();
 			float ci = Float.parseFloat(currentBalance) / 100;
			ye.setText("余额:" + currentBalance + getString(R.string.m_gold)
					+ "(" + ci + "元)");
			tel.setText(info.getUtelephone());
			nickname.setText(info.getUname());
		}
	}

	protected ZcmUser getMyInfoForDatabase() {

		return getDatabaseInterface().getMyInfo();
	}

	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		// mProgressDialog.dismiss();
	}

	@Override
	public void onLoading(long count, long current) {

	}

	@Override
	public void onHttpStart() {
		// mProgressDialog = showBaseProgressDialog();
	}

	protected ProgressDialog showBaseProgressDialog() {
		return ProgressDialog.show(this, getString(R.string.hit),
				getString(R.string.wait));
	}

	protected void showToast(String msg) {
		CustomToast.makeText(this, msg);
	}

	protected void showToast(int id) {
		CustomToast.makeText(this, id);
	}

	protected void showErrorToast() {
		showToast(R.string.errortoast);
	}

	public String getResString(int resId) {
		return getResources().getString(resId);
	}

	/**
	 * Convert from DIP value to Pixel value.
	 * 
	 * @param dip
	 *            Value in DIP
	 * @return Value in Pixel
	 */
	public int dip2px(float dip) {

		return Util.dip2px(dip, this);
	}

	/**
	 * Convert from Pixel value to DIP value.
	 * 
	 * @param pixel
	 *            Value in Pixel
	 * @return Value in DIP
	 */
	public int px2dip(float pixel) {
		return Util.px2dip(pixel, this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		HeaderImageEvent.getInstance().removeListener(this);
		BalanceEvent.getInstance().removeListener(this);
		EditMyInfoEvent.getInstance().removeListener(this);
	}

	@Override
	public void onMyInfoChange(ZcmUser info) {
		setBaseHeaderInfo();
		Logger.i("INFO", "onMyInfoChange");

	}

	@Override
	public void onAgeEditAfter(String age) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNickNameEditAfter(String nickNmae) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGenderEditAfter(String gender) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddressEditAfter(String address) {
		// TODO Auto-generated method stub

	}
}