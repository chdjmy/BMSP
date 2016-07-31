package com.example.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bean.CulvertBean;
import com.example.bean.MemberBean;
import com.example.dao.CulvertDao;
import com.example.dao.MemberDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PersonalInfoActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);
		
		String[] memberItems = new String[] { 
				"登录名：", "登录密码：", "中文全名：","部门ID：","职位：", "人员编码：",
				"性别：", "身份证号：", "生日：", "QQ号码：", "电话号码：",
				"地址：", "状态：", "备注："};//"邮箱：", 
		
		MemberDao dao = new MemberDao(this);
		MemberBean info = dao.get("1");
		
		int iLength = memberItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", memberItems[i]);
		}
		
		maps[0].put("value",info.getUsername());//登录名
		maps[1].put("value",info.getPassword());//登录密码
		maps[2].put("value",info.getName());//中文全名
		maps[3].put("value",info.getDepId());//部门ID
		maps[4].put("value",info.getPositionId());//职位
		maps[5].put("value",info.getUserCode());//人员编码
		maps[6].put("value",info.getSex());//性别
		maps[7].put("value",info.getIdNumber());//身份证号
		maps[8].put("value",info.getBirthday());//生日
		maps[9].put("value",info.getQq());//QQ号码
		maps[10].put("value",info.getPhone());//电话号码
		//maps[11].put("value",info.);//邮箱
		maps[11].put("value",info.getAddress());//地址
		maps[12].put("value",info.getState());//状态
		maps[13].put("value",info.getBak());//备注
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
		
		ListView listView = ((ListView)findViewById(R.id.list_personal_info));
		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.listitem_bridgeinfo_detail, new String[] { "name",
						"value" }, new int[] { R.id.name, R.id.value });
		listView.setAdapter(listAdapter);
	}
}
