package com.example.bms;

import java.util.List;

import com.example.bean.SettingBean;
import com.example.dao.SettingDao;
import com.example.util.Common;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NetSettingsActivity extends BaseActivity {

	private Button buttonSaveNet;
	private Button buttonCancelNet;
	private EditText edit_net_ip,edit_net_port,edit_net_webapp,edit_net_timeout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_settings);
		edit_net_ip = (EditText)findViewById(R.id.edit_net_ip);
		edit_net_port = (EditText)findViewById(R.id.edit_net_port);
		edit_net_webapp = (EditText)findViewById(R.id.edit_net_webapp);
		edit_net_timeout = (EditText)findViewById(R.id.edit_net_timeout);
		
		
		SettingBean net = Common.getSetting(this);
		edit_net_ip.setText(net.getIp());
		edit_net_port.setText(net.getPort());
		edit_net_webapp.setText(net.getWeb_app());
		edit_net_timeout.setText(String.valueOf(net.getTimeout()));
	
		buttonSaveNet = (Button)findViewById(R.id.buttonSaveNet);
		buttonSaveNet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SettingBean net = new SettingBean();
				net.setIp(edit_net_ip.getText().toString());
				net.setPort(edit_net_port.getText().toString());
				net.setWeb_app(edit_net_webapp.getText().toString());
				net.setTimeout(Integer.parseInt(edit_net_timeout.getText().toString()));
				SettingDao settingDao = new SettingDao(NetSettingsActivity.this);
				settingDao.updateSetting(net);
				displayToast("…Ë÷√≥…π¶");
				NetSettingsActivity.this.finish();
				
			}
			
		});
		
		buttonCancelNet = (Button)findViewById(R.id.buttonCancelNet);
		buttonCancelNet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NetSettingsActivity.this.finish();
			}
			
		});
	}
}
