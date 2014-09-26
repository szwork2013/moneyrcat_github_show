package com.emperises.monercat.wxapi;

import android.widget.Toast;

import com.emperises.monercat.R;
import com.emperises.monercat.utils.Logger;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {
	@Override
	public void onResp(BaseResp resp) {
		super.onResp(resp);
		int result = 0;

		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = R.string.errcode_success;
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = R.string.errcode_cancel;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			break;
		default:
			result = R.string.errcode_unknown;
			break;
		}
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
	@Override
	public void onReq(BaseReq req) {
		super.onReq(req);
		Logger.i("share", "微信req:"+req.getType());
	}
}
