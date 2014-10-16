package com.emperises.monercat.ui.v3;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.MessageAdapter;
import com.emperises.monercat.adapter.MessageModel;
import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.utils.Util;
import com.google.gson.Gson;

@SuppressLint("NewApi")
public class ActivityFeedBack extends OtherBaseActivity {
	private ListView mListView;// 用来显示聊天信息的listview
    private Button imageB;// 聊天人物 A和B
    private EditText edittext;// 输入框
    private MessageModel modle;
    private MessageAdapter adapter;// 数据适配器
    private List<MessageModel> listmodle = new ArrayList<MessageModel>();// 存放信息的list
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		setCurrentTitle("意见反馈");
	}
	public void onHttpStart() {
		setCurrentTitle("发送中..");
	};
	@Override
	public void onFinished(String content) {
		super.onFinished(content);
		DomainObject o = new Gson().fromJson(content, DomainObject.class);
		if( o != null && HTTP_RESULE_SUCCESS.equals(o.getResultCode())){
			modle = new MessageModel();
    		modle.setA(true);
    		modle.setMessage("谢谢您的宝贵建议!我们已经收到,客服人员会仔细审查，再次感谢您!");
    		modle.setDate(Util.getDate());
		} else {
			modle = new MessageModel();
    		modle.setA(true);
    		modle.setMessage("建议提交异常!请重新提交!为您带来的不便敬请谅解!");
    		modle.setDate(Util.getDate());
    		listmodle.add(modle);
            adapter.notifyDataSetChanged();// 更新聊天内容
            mListView.setSelection(listmodle.size());// 每次发送之后将listview滑动到最低端
		}
		setCurrentTitle("意见反馈");
	}
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		super.onFail(t, errorNo, strMsg);
		setCurrentTitle("意见反馈");
		modle = new MessageModel();
		modle.setA(true);
		modle.setMessage("建议提交异常!请重新提交!为您带来的不便敬请谅解!");
		modle.setDate(Util.getDate());
		listmodle.add(modle);
        adapter.notifyDataSetChanged();// 更新聊天内容
        mListView.setSelection(listmodle.size());// 每次发送之后将listview滑动到最低端
	}
	
	private void postFeedBack(String content){
		AjaxParams params = new AjaxParams();
		params.put(POST_KEY_DEVICESID, Util.getDeviceId(getApplicationContext()));
		params.put("fcontent", content);
		startRequest(SERVER_URL_FEEDBACK, params);
	}
	@Override
	protected void initViews() {
		super.initViews();
		mListView = (ListView) findViewById(R.id.messageList);
        imageB = (Button) findViewById(R.id.imageB);
        edittext = (EditText) findViewById(R.id.feedBackEdit);
        imageB.setOnClickListener(this);
        modle = new MessageModel();
        modle.setA(true);
        modle.setMessage("您好!感谢您使用意见反馈系统!我们会对您的意见或建议进行仔细的审核,如果您的建议被采纳,将会获得招财喵神秘礼包哦!");
        modle.setDate(Util.getDate());
        listmodle.add(modle);
        adapter = new MessageAdapter(this, listmodle);
        adapter.setHeaderImageResPath(getHeadImageResUrl());
        mListView.setAdapter(adapter);
	}
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageB:
            	String content = edittext.getText().toString();
            	if(content.isEmpty()){
            		showToast("请输入反馈信息!");
            	} else {
            		modle = new MessageModel();
            		modle.setA(false);
            		modle.setMessage(content);
            		modle.setDate(Util.getDate());
            		postFeedBack(content);
            	}
                break;
            default:
                break;
        }
        listmodle.add(modle);
        adapter.notifyDataSetChanged();// 更新聊天内容
        mListView.setSelection(listmodle.size());// 每次发送之后将listview滑动到最低端
        edittext.setText("");
    }
}
