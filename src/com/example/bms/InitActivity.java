package com.example.bms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.SettingBean;
import com.example.dao.BridgeDao;
import com.example.dao.UsersDao;
import com.example.dao.UsualExamDao;
import com.example.util.Common;
import com.example.util.Net;
import com.example.util.Uri;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class InitActivity extends BaseActivity {

	private Button init_btn_init, init_btn_back;
	private Handler handler;
	private HandlerThread handlerThread;
	//private ProgressBar progressBarBridgeInfo;
	//private ProgressBar progressBarBridgeUsualExam;
	//private ProgressBar progressBarCulvertInfo;
	//private ProgressBar progressBarCulvertUsualExam;
	private ImageView i1,i2,i3,i4;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);

		handlerThread = new HandlerThread("handler_thread");
		handlerThread.start();
		handler = new InitHandler(this,handlerThread.getLooper());
		//handler = new InitHandler(this);

		init_btn_init = (Button) findViewById(R.id.init_btn_init);
		init_btn_init.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displayToast("��ʼͬ��");
				handler.post(new InitThread(InitActivity.this));
			}

		});
		init_btn_back = (Button) findViewById(R.id.init_btn_back);
		init_btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handlerThread.quit();
				InitActivity.this.finish();
			}

		});
		
		//progressBarBridgeInfo = (ProgressBar)findViewById(R.id.progressBarBridgeInfo);
		//progressBarBridgeUsualExam = (ProgressBar)findViewById(R.id.progressBarBridgeUsualExam);
		//progressBarCulvertInfo = (ProgressBar)findViewById(R.id.progressBarCulvertInfo);
		//progressBarCulvertUsualExam = (ProgressBar)findViewById(R.id.progressBarCulvertUsualExam);
		i1 = (ImageView)findViewById(R.id.imageView1);
		i2 = (ImageView)findViewById(R.id.imageView2);
		i3 = (ImageView)findViewById(R.id.imageView3);
		i4 = (ImageView)findViewById(R.id.imageView4);
	}

	// �Զ����߳��࣬ʵ��Runnable�ӿ�
	class InitThread implements Runnable {
		private Context context;

		InitThread(Context context) {
			this.context = context;
		}

		@Override
		public void run() {
			if (Net.getSingle(context).netState()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("��ʾ");
				builder.setMessage("�Ḳ��ԭ������,ȷ��ͬ��������?");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Message msg = handler.obtainMessage();
								msg.what = Uri.Init.MSG_START_INIT;
								handler.sendMessage(msg);

								
								
								SettingBean settingBean = Common
										.getSetting(context);
								// ��ʼ������Ա��Ϣ
								//new UsersDao(context).init(settingBean, handler);
								//new UsualExamDao(context).initCount(settingBean,handler);

								new BridgeDao(context).initCount(settingBean, handler);
							}
						});
				builder.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.show();
			} else {
				Toast.makeText(context, "����������", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	// �Զ���handler�࣬��дhandleMessage����
	class InitHandler extends Handler {
	
		private int msg_num = 0;
		private int maxPage = 0;
		//private int progress = 0;
		//private ProgressBar progressBar;
		private Context context;

		InitHandler(Context context,Looper looper) {
			super(looper);
			this.context = context;
		}

		@Override
		public void handleMessage(Message msg) {
			boolean bl = false;
			JSONObject object = null;
			if (msg.what != Uri.Init.MSG_START_INIT
					&& msg.what != Uri.Init.MSG_END_INIT) {
				bl = msg.getData().getBoolean(Uri.KEY_FLAG);
				if (!bl) {
					JSONObject error = JSONObject.parseObject(msg.getData()
							.getString(Uri.KEY_MSG));
					Toast.makeText(context, error.getString(Uri.KEY_MSG),
							Toast.LENGTH_LONG).show();
				}
				String data = msg.getData().getString(Uri.KEY_MSG);
				object = JSONObject.parseObject(data);
			}
			
			BridgeDao bridgeDao = new BridgeDao(context);
			UsualExamDao usualExamDao = new UsualExamDao(context);
			switch (msg.what) {
			case Uri.Init.MSG_USER_INFO:
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array = JSONArray.parseArray(object
								.getString(Uri.KEY_DATA));
						BridgeDao dao = new BridgeDao(context);
						bl = dao.add(array);
					} else {
						Toast.makeText(context,
								msg.getData().getString(Uri.KEY_MSG),
								Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
					}
				}
				setMsgNum();
				break;
			case Uri.Init.MSG_USUALEXAM_INFO://�����Լ���ʼ��-���ҳ��
				maxPage = 0;
				if(bl){
					if(object.getBoolean(Uri.KEY_SUCC)){
						JSONArray array  = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						if(null != array && array.size() > 0){
							SettingBean settingBean = Common.getSetting(context);
							maxPage = array.getJSONObject(0).getInteger("total");
							//progressBar = progressBarBridgeUsualExam;
							//progressBar.setMax(maxPage);
							//progressBar.setSecondaryProgress(maxPage);
							//progress = 1;
							usualExamDao.delete();
							for(int i = 1 ; i <= maxPage ; i++){
								//if(i%5==0){
									try {
										Thread.sleep(300);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								//}
								usualExamDao.init(settingBean, this, i);
							}
						}else{
							setMsgNum();
						}
					}else{
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
						setMsgNum();
					}
				}else{
					setMsgNum();
				}
				break;	
			case Uri.Init.MSG_USUALEXAM_INFO_PAGE://�����ԙz��
				if (bl) {
					if (object.getBoolean(Uri.KEY_SUCC)) {
						JSONArray array  = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						bl = usualExamDao.add(array);
					} else {
						Toast.makeText(context,
								msg.getData().getString(Uri.KEY_MSG),
								Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
					}
				}
				maxPage--;
				//setProgress();
				System.out.println("pageCount==maxPage```" +maxPage);
				if(0 == maxPage){ 
					setMsgNum();
					//webView.loadUrl(SCRIPT_SETIMAGE+"("+ bl +",'equip_info')");
					InitActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							i2.setImageResource(R.drawable.finish);
						}
					});
				}
				break;

			case Uri.Init.MSG_BRIDGE_INFO://������Ϣ��ʼ��-���ҳ��
				maxPage = 0;
				if(bl){
					if(object.getBoolean(Uri.KEY_SUCC)){
						JSONArray array  = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
						if(null != array && array.size() > 0){
							SettingBean settingBean = Common.getSetting(context);
							maxPage = array.getJSONObject(0).getInteger("total");
							bridgeDao.delete();//ɾ����������
							//progressBarBridgeInfo.setMax(maxPage);
							//progressBarBridgeInfo.setSecondaryProgress(maxPage);
							//progress = 1;
							for(int i = 1 ; i <= maxPage ; i++){
							//	if(i%2==0){
									try {
										Thread.sleep(300);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								//}
								bridgeDao.init(settingBean, this, i);
							}
						}else{
							setMsgNum();
						}
					}else{
						Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_LONG).show();
						bl = object.getBoolean(Uri.KEY_SUCC);
						setMsgNum();
					}
				}else{
					setMsgNum();
				}
				break;
			case Uri.Init.MSG_BRIDGE_INFO_PAGE://������Ϣ��ʼ��-��ҳ��ʼ������
				
				if(object.getBoolean(Uri.KEY_SUCC)){
					JSONArray array  = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
					bl = bridgeDao.add(array);
					
				}else{
					Toast.makeText(context,msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_LONG).show();
					bl = object.getBoolean(Uri.KEY_SUCC);
				}
				maxPage--;
				//System.out.println("pageCount==maxPage```" +maxPage);
				if(0 == maxPage){ 
					InitActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							i1.setImageResource(R.drawable.finish);
						}
					});
					SettingBean settingBean = Common.getSetting(context);
					new UsualExamDao(context).initCount(settingBean,handler);
					setMsgNum();
				//	progressBarBridgeInfo.setProgress(progressBarBridgeInfo.getMax());
				//  webView.loadUrl(SCRIPT_SETIMAGE+"("+ bl +",'equip_info')");
				}
				break;

			case Uri.Init.MSG_START_INIT://���������ƣ���ʼ��ʼ��
				InitActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog = ProgressDialog.show(context, null, "������,���Ժ�...", true,false);
					}
				});
				
				msg_num = 0;
				break;
			case Uri.Init.MSG_END_INIT://���������ƣ�������ʼ��
				
				InitActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (dialog.isShowing()) {
							i3.setImageResource(R.drawable.finish);
							i4.setImageResource(R.drawable.finish);
							dialog.dismiss();
						}
					}
				});
				break;
			}

			super.handleMessage(msg);
		}

		private synchronized void setMsgNum() {//init�����õĴ���
			msg_num = msg_num + 1;
			if (msg_num == 2) {
				Toast.makeText(context, "����ͬ����ɡ�", Toast.LENGTH_LONG).show();
				Message msg = this.obtainMessage();
				msg.what = Uri.Init.MSG_END_INIT;
				this.sendMessage(msg);
			}
		}
	}

}


