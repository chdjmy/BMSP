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
				"��¼����", "��¼���룺", "����ȫ����","����ID��","ְλ��", "��Ա���룺",
				"�Ա�", "���֤�ţ�", "���գ�", "QQ���룺", "�绰���룺",
				"��ַ��", "״̬��", "��ע��"};//"���䣺", 
		
		MemberDao dao = new MemberDao(this);
		MemberBean info = dao.get("1");
		
		int iLength = memberItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", memberItems[i]);
		}
		
		maps[0].put("value",info.getUsername());//��¼��
		maps[1].put("value",info.getPassword());//��¼����
		maps[2].put("value",info.getName());//����ȫ��
		maps[3].put("value",info.getDepId());//����ID
		maps[4].put("value",info.getPositionId());//ְλ
		maps[5].put("value",info.getUserCode());//��Ա����
		maps[6].put("value",info.getSex());//�Ա�
		maps[7].put("value",info.getIdNumber());//���֤��
		maps[8].put("value",info.getBirthday());//����
		maps[9].put("value",info.getQq());//QQ����
		maps[10].put("value",info.getPhone());//�绰����
		//maps[11].put("value",info.);//����
		maps[11].put("value",info.getAddress());//��ַ
		maps[12].put("value",info.getState());//״̬
		maps[13].put("value",info.getBak());//��ע
		
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
