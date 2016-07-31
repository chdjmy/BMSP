package com.example.bms;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.SettingBean;
import com.example.bean.UsualExamBean;
import com.example.bms.InitActivity.InitThread;
import com.example.dao.UsualExamDao;
import com.example.util.Common;
import com.example.util.Net;
import com.example.util.Uri;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DataUploadActivity extends BaseActivity {

	private ListView listViewBridgeUsualExam;
	private List<UsualExamBean> listUsualExams;
	private MyListAdapter adapter;
	private UsualExamDao usualExamDao;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_upload);
		usualExamDao = new UsualExamDao(this);
		listUsualExams = usualExamDao.findToUpload();
		listViewBridgeUsualExam = (ListView)findViewById(R.id.listViewDataUploadBridgeUsualExam);
		adapter = new MyListAdapter(DataUploadActivity.this,   
                R.layout.listitem_dataupload_usual,   
                listUsualExams); 
		listViewBridgeUsualExam.setAdapter(adapter);
		
		handler = new UploadDataHandler(this);
		
	}
	private class MyListAdapter extends ArrayAdapter<UsualExamBean>{ 
	    private int resource; 
	    public MyListAdapter(Context context, int resourceId, List<UsualExamBean> objects) { 
	        super(context, resourceId, objects); 
	        // ��¼�����Ժ�ʹ�� 
	        resource = resourceId; 
	    }
	    public View getView(final int position, View convertView, ViewGroup parent) { 
	        LinearLayout listView; 
	        // ��ȡ���� 
	        UsualExamBean b = getItem(position); 
	        String bridgeCode = b.getBridgeCode();
	        String bridgeName = b.getBridgeName();
	        String lineNumber = b.getCheckDate();
	        String lineName = b.getPrincipal();
	        String lineType = b.getNoter();
	        String locationName = b.getCheckDate();
	
	        if(convertView == null) { 
	            listView = new LinearLayout(getContext()); 
	            String inflater = Context.LAYOUT_INFLATER_SERVICE; 
	            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater); 
	            vi.inflate(resource, listView, true); 
	        } else { 
	            listView = (LinearLayout)convertView; 
	        }
	
	        // ����Զ������� 
	        TextView item_bridgeCode = (TextView) listView.findViewById(R.id.bridgeCode); 
	        TextView item_bridgeName = (TextView) listView.findViewById(R.id.bridgeName); 
	        TextView item_lineNumber = (TextView) listView.findViewById(R.id.lineNumber);
	        TextView item_lineName = (TextView) listView.findViewById(R.id.lineName); 
	        TextView item_lineType = (TextView) listView.findViewById(R.id.lineType); 
	        Button btn_info = (Button) listView.findViewById(R.id.btn_info); 
	        Button btn_add = (Button) listView.findViewById(R.id.btn_add);
	        
	        //TextView item_locationName = (TextView) listView.findViewById(R.id.locationName);
	        item_bridgeCode.setText(bridgeCode);
	        item_bridgeName.setText(bridgeName);
	        item_lineNumber.setText(lineNumber);
	        item_lineName.setText(lineName);
	        item_lineType.setText(lineType);
	       // item_locationName.setText(locationName);
	        
	        btn_info.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					displayToast(listUsualExams.get(position).getBridgeName());
					
					Intent intent = new Intent();
					intent.setClass(DataUploadActivity.this,UsualExamDetailActivity.class);
					intent.putExtra("usualExamId", listUsualExams.get(position).getUsualExamId());
					startActivity(intent);
				}
			});
	        btn_add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					displayToast(listUsualExams.get(position).getBridgeCode());
					Intent intent = new Intent();
					intent.setClass(DataUploadActivity.this,UsualExamAddActivity.class);
					intent.putExtra("usualExamId", listUsualExams.get(position).getUsualExamId());
					intent.putExtra("interType", 1);
					startActivity(intent);
				}
			});
	   
	        int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB��ɫ
	        listView.setBackgroundColor(colors[position % 2]);// ÿ��item֮����ɫ��ͬ
	
	        return listView; 
	    }
	}
	//�˵� �ϴ�����
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.data_upload, menu);//�޸�
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.data_menu_submit) {
			handler.post(new uploadThread());
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	class uploadThread implements Runnable{
		@Override
		public void run() {
			if(Net.getSingle(DataUploadActivity.this).netState()){
				AlertDialog.Builder builder = new AlertDialog.Builder(DataUploadActivity.this);
				builder.setTitle("��ʾ");
				builder.setMessage("ȷ���ϴ�������?");
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Message msg = handler.obtainMessage();
						msg.what = Uri.Upload.MSG_START_UPLOAD;
						handler.sendMessage(msg);
						
						uploadD();
					}
				});
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();					
					}
				});
				builder.show();
			}else{
				Toast.makeText(DataUploadActivity.this,"����������", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	private void uploadD(){
		// ��ȡ��������
		SettingBean settingBean = Common.getSetting(this);
		
		new UsualExamDao(this).upload(settingBean, handler);
		
		//new MaintainDao(context).upload(settingBean, handler);
		
		//new FaultsheetDao(context).uploadWh(settingBean, handler);
		
		//new FaultsheetDao(context).uploadGz(settingBean, handler);
	}
	
	
	//handler
	class UploadDataHandler extends Handler{
		

		private boolean maintain_data_flag = true;
		private boolean faultsheet_data_flag = true;
		
		private ProgressDialog dialog;
		private int msg_num = 0 ;
		private Context context;
		
		//private WebView webView;
		public UploadDataHandler(Context context){
			this.context = context;
		}
		
		@Override
		public void handleMessage(Message msg) {
			
			if(msg.what == Uri.Upload.MSG_UPLOAD_NUM){//Ҫ�ϴ����ݵ�����������ǰ̨��ʾ
				//webView.loadUrl("javascript:setNum("+msg.getData().getInt("maintainNum")+","+msg.getData().getInt("faultsheetWhNum")+","+msg.getData().getInt("faultsheetGzNum")+","+msg.getData().getInt("usersNum")+")");
				return;
			}
			
			// http ��������ʶ
			boolean bl = false;
			// ����ӿ����ݽ����ʶ
			boolean dataBl = false;
			JSONObject object = null;
			JSONArray array = null;
			if(msg.what != Uri.Upload.MSG_START_UPLOAD && 
			   msg.what != Uri.Upload.MSG_END_UPLOAD &&
			   msg.what != Uri.Init.MSG_END_INIT){
				bl = msg.getData().getBoolean(Uri.KEY_FLAG);//�����Ϣ��KEY_FLAG
				if(!bl){
					JSONObject error = JSONObject.parseObject(msg.getData().getString(Uri.KEY_MSG));
					Toast.makeText(context,  error.getString(Uri.KEY_MSG), Toast.LENGTH_LONG).show();
				}else{
					String data = msg.getData().getString(Uri.KEY_MSG);
					try {
						object = JSONObject.parseObject(data);
					} catch (Exception e) {
						object = null;
					}
					try {
						dataBl = object.getBoolean(Uri.KEY_SUCC);
					} catch (Exception e) {
						dataBl = false;
					}
					if(dataBl){
						array = JSONArray.parseArray(object.getString(Uri.KEY_DATA));
					}
				}
			}
		
			
			switch (msg.what) {
				case Uri.Upload.MSG_FAULTSHEET_WH_INFO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'faultsheet_wh_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'faultsheet_wh_info')");
					}
					faultsheet_data_flag = msg.getData().getBoolean("data_flag");
					if(faultsheet_data_flag){
						Toast.makeText(context,  msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_LONG).show();
					}
					setUploadNum();
					break;
				case Uri.Upload.MSG_FAULTSHEET_GZ_INFO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'faultsheet_gz_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'faultsheet_gz_info')");
					}
					faultsheet_data_flag = msg.getData().getBoolean("data_flag");
					if(faultsheet_data_flag){
						Toast.makeText(context,  msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_LONG).show();
					}
					setUploadNum();
					break;
				case Uri.Upload.MSG_MAINTAIN_INFO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'maintain_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'maintain_info')");
					}
					maintain_data_flag = msg.getData().getBoolean("data_flag");
					if(maintain_data_flag){
						Toast.makeText(context,  msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_LONG).show();
					}
					setUploadNum();
					break;
					
					
				//�����Լ��������ϴ��󷵻صĽ��*************************
				case Uri.Upload.MSG_BRIDGEUSUALEXAM_INFO:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'user_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'user_info')");
					}
					if(msg.getData().getBoolean("data_flag")){//�ж���û����Ҫ�ϴ������ݣ�û�д��ϴ�����Ϊtrue
						Toast.makeText(context,  msg.getData().getString(Uri.KEY_MSG), Toast.LENGTH_SHORT).show();
					}else{
						if(null != array && array.size() > 0){
							//SettingBean settingBean = Common.getSetting(context);
							int serverMaxId = array.getJSONObject(0).getInteger("serverMaxId");
							UsualExamDao dao = new UsualExamDao(context);
							if(serverMaxId==dao.getMaxId()){
								dao.updateUpload();//��isupLoad��־λ��Ϊ1
								setUploadNum();
							}else{
								dao.deleteUpload();//ɾ��isupLoad��־λ��0�ļ�¼
								SettingBean settingBean = Common.getSetting(context);
								dao.download(settingBean, this,dao.getClientMaxId());
								Toast.makeText(context,"�����Լ�����ݸ���", Toast.LENGTH_LONG).show();
							}
						}	
					}
					//setUploadNum();
					break;
				case Uri.Upload.MSG_BRIDGEUSUALEXAM_DOWNLOAD:
					if(bl && dataBl){
						//webView.loadUrl("javascript:setDataSuccImage("+ true +",'user_info')");
					}else{
						//webView.loadUrl("javascript:setDataSuccImage("+ false +",'user_info')");
					}
					if(null != array && array.size() > 0){
						//int serverMaxId = array.getJSONObject(0).getInteger("serverMaxId");
						UsualExamDao dao = new UsualExamDao(context);
						dao.add(array);
					}		
					setUploadNum();
					break;
					
					
				case Uri.Upload.MSG_START_UPLOAD:
					dialog = ProgressDialog.show(context, null,"������,���Ժ�...", true, false);
					msg_num = 0;
				//	webView.loadUrl("javascript:startInit();");
					break;
				case Uri.Upload.MSG_END_UPLOAD:
					
					Toast.makeText(context,"�ϴ��������", Toast.LENGTH_LONG).show();
					if(dialog.isShowing()){
						dialog.dismiss();
					}
					listUsualExams.clear();
					adapter.notifyDataSetChanged();
					break;
					/*
					System.out.println("**************msg_end_upload*********");
					System.out.println("maintain_data_flag:" + maintain_data_flag);
					System.out.println("faultsheet_data_flag:" + faultsheet_data_flag);
					
					// ά���Ǽǵ��������ϴ�   ��ӷ�������������ȡ  maintain ��  maintainequiplist ��
					if(!maintain_data_flag){
						//new MaintainDao(context).init(Common.getSetting(context), this);
						//new MaintainequiplistDao(context).init(Common.getSetting(context), this);
					}
					// ���ϵ��������ϴ� ��ӷ�������������ȡ faultsheet ��    equipfixlist ��
					if(!faultsheet_data_flag){
						//new FaultsheetDao(context).init(Common.getSetting(context), this);
						//new EquipfixlistDao(context).init(Common.getSetting(context), this);
					}
					if(maintain_data_flag && faultsheet_data_flag){
						//webView.loadUrl("javascript:endInit();");
						if(dialog.isShowing()){
							dialog.dismiss();
					*/
					
				case Uri.Init.MSG_END_INIT:
					//webView.loadUrl("javascript:endInit();");
					if(dialog.isShowing()){
						dialog.dismiss();
					}
					break;
				case Uri.Init.MSG_MAINTAIN_INFO:
					if(bl && dataBl){
						//MaintainDao maintainDao = new MaintainDao(context);
						//bl = maintainDao.add(array);
					}
					setMsgNum();
					break;
				case Uri.Init.MSG_MAINTAINEQUIPLIST_INFO:
					if(bl && dataBl){
						//MaintainequiplistDao maintainequiplistDao = new MaintainequiplistDao(context);
						//bl = maintainequiplistDao.add(array);
					}
					setMsgNum();
					break;
				case Uri.Init.MSG_FAULTSHEET_INFO:
					if(bl && dataBl){
						//FaultsheetDao faultsheetDao = new FaultsheetDao(context);
						//bl = faultsheetDao.add(array);
					}
					setMsgNum();
					break;
				case Uri.Init.MSG_EQUIPFIXLIST_INFO:
					if(bl && dataBl){
						//EquipfixlistDao equipfixlistDao = new EquipfixlistDao(context);
						//bl = equipfixlistDao.add(array);
					}
					setMsgNum();
					break;
		
				default:
					break;
				
			}
			
			super.handleMessage(msg);
		}
		
		private synchronized void setUploadNum(){
			msg_num = msg_num + 1;
			System.out.println("msg_num:"+msg_num);
			if(msg_num == 1){
				msg_num = 0;
				Message msg = this.obtainMessage();
				msg.what = Uri.Upload.MSG_END_UPLOAD;
				this.sendMessage(msg);
			}
		}
		
		private synchronized void setMsgNum(){
			int uploadNum = 0;
			if(!maintain_data_flag){
				uploadNum = uploadNum + 2;
			}
			if(!faultsheet_data_flag){
				uploadNum = uploadNum + 2;
			}
			if(!maintain_data_flag || !faultsheet_data_flag){
				msg_num = msg_num + 1;	
			}
			if(msg_num == uploadNum){
				Toast.makeText(context,"�ϴ��������", Toast.LENGTH_LONG).show();
				Message msg = this.obtainMessage();
				msg.what = Uri.Init.MSG_END_INIT;
				this.sendMessage(msg);
			}
		}
		
		
		
	}
	
}



