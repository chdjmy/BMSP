package com.example.bms;

import com.example.bean.SettingBean;
import com.example.dao.BridgeDao;
import com.example.util.Common;
import com.example.util.Uri;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
/**
 * 系统主页面
 * @author JMY
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener{
	

	private TextView main_bridge_info;
	//private TextView main_bridge_check;
	//private TextView main_bridge_audit;
	//private TextView main_bridge_evaluate;
	private TextView main_culvert_info;
	//private TextView main_culvert_check;
	
	//private TextView main_maintenance_plan;
	//private TextView main_maintenance_task;
	//private TextView main_data_upload;
	//private TextView main_data_synchronization;
	private TextView main_personal_info;
	private TextView main_network_settings;
	
	private ImageButton imageButtonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_bridge_info = (TextView)findViewById(R.id.main_bridge_info);
        main_culvert_info = (TextView)findViewById(R.id.main_culvert_info);
        
       // main_maintenance_plan = (TextView)findViewById(R.id.main_maintenance_plan);
       // main_data_synchronization = (TextView)findViewById(R.id.main_data_synchronization);
        main_personal_info = (TextView)findViewById(R.id.main_personal_info);
        main_network_settings = (TextView)findViewById(R.id.main_network_settings);
        
        imageButtonExit = (ImageButton)findViewById(R.id.imageButtonExit);
        
        main_bridge_info.setOnClickListener(this);
        main_culvert_info.setOnClickListener(this);
        
       // main_maintenance_plan.setOnClickListener(this);
       // main_data_synchronization.setOnClickListener(this);
        main_personal_info.setOnClickListener(this);
        main_network_settings.setOnClickListener(this);
        
        imageButtonExit.setOnClickListener(this);
        
        
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.main_bridge_info:
			startActivity(new Intent(MainActivity.this,BridgeInfoActivity.class));
			break;
			
		case R.id.main_culvert_info:
			startActivity(new Intent(MainActivity.this, CulvertInfoActivity.class));
			break;
			
			
//		case R.id.main_maintenance_plan:
//			startActivity(new Intent(MainActivity.this, MaintainPlanActivity.class));
//			break;
//			
//			
//		case R.id.main_data_synchronization:
//			startActivity(new Intent(MainActivity.this, InitActivity.class));
//			break;
//			
		case R.id.main_personal_info:
			startActivity(new Intent(MainActivity.this, PersonalInfoActivity.class));
			break;
			
		case R.id.main_network_settings:
			startActivity(new Intent(MainActivity.this, NetSettingsActivity.class));
			break;
			
		case R.id.imageButtonExit:
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setTitle("提示");
			builder.setMessage("确认退出系统吗?");
			builder.setPositiveButton("退出",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							System.exit(0);
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
						}
					});
			builder.show();
			
			break;
			
		default:
			break;
		
		}
		
	}
}
