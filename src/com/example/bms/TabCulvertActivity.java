package com.example.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bean.BridgeBean;
import com.example.bean.CulvertBean;
import com.example.bean.CulvertUsualExamBean;
import com.example.bean.UsualExamBean;
import com.example.common.tab.ViewPagerIndicatorView;
import com.example.dao.BridgeDao;
import com.example.dao.CulvertDao;
import com.example.dao.CulvertUsualExamDao;
import com.example.dao.UsualExamDao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


/**
 * 桥涵的tab
 * @author JMY
 */
public class TabCulvertActivity extends Activity {
	private ViewPagerIndicatorView viewPagerIndicatorView;
	
	
	//桥梁信息Tab
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> listEvaluate = new ArrayList<Map<String, Object>>();
	
	//桥梁经常性检查
	private ListView listViewCulvertUsualExam;
	private List<CulvertUsualExamBean> listUsualExams;
	private CulvertUsualExamDao usualExamDao;
	private MyListAdapter adapter;
	private String culvertId;
	private String culvertCode;
	private CulvertBean info;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_culvert);
		
		culvertId = getIntent().getStringExtra("culvertId");
		culvertCode = getIntent().getStringExtra("culvertCode");
		
		//涵洞信息的view
		View viewCulvertInfo = LayoutInflater.from(this).inflate(R.layout.fragment_bridgeinfo_detail,
				null);
		ListView listView = ((ListView) viewCulvertInfo
				.findViewById(R.id.bridgeinfo_detail));
		culvertDistinguish();
		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.listitem_bridgeinfo_detail, new String[] { "name",
						"value" }, new int[] { R.id.name, R.id.value });
		listView.setAdapter(listAdapter);
		
		//涵洞评价的view
		View viewCulvertEvaluate = LayoutInflater.from(this).inflate(R.layout.fragment_bridgeinfo_detail,
				null);
		ListView listViewEvaluate = ((ListView) viewCulvertEvaluate
				.findViewById(R.id.bridgeinfo_detail));
		culvertEvaluate();
		SimpleAdapter listAdapterEvaluate = new SimpleAdapter(this, listEvaluate,
				R.layout.listitem_bridgeinfo_detail, new String[] { "name",
						"value" }, new int[] { R.id.name, R.id.value });
		listViewEvaluate.setAdapter(listAdapterEvaluate);		
		
		//经常性检查的view
		View viewUsuslExam = LayoutInflater.from(this).inflate(R.layout.activity_culvert_usual_exam,
				null);
		listViewCulvertUsualExam = (ListView) viewUsuslExam.findViewById(R.id.listViewCulvertUsualExam);
		usualExamDao = new CulvertUsualExamDao(this);
		listUsualExams = usualExamDao.findByCulvertCode(culvertCode);
		adapter = new MyListAdapter(TabCulvertActivity.this,   
                R.layout.listitem_bridge_usualexam,   
                listUsualExams); 
		listViewCulvertUsualExam.setAdapter(adapter);
		listViewCulvertUsualExam.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(TabCulvertActivity.this,CulvertUsualExamDetailActivity.class);
				intent.putExtra("usualExamId", listUsualExams.get(position).getCulvertUsualExamId());
				startActivity(intent);
			}
			
		});
		
		
		
		
		//set ViewPagerIndicatorView
		this.viewPagerIndicatorView = (ViewPagerIndicatorView) findViewById(R.id.viewpager_indicator_view);
		final List<String> textList = new ArrayList<String>();
		final List<View> viewList = new ArrayList<View>();
		textList.add("涵洞信息");
		viewList.add(viewCulvertInfo);
		textList.add("经常性检查");
		viewList.add(viewUsuslExam);
		textList.add("涵洞评价");
		viewList.add(viewCulvertEvaluate);
		this.viewPagerIndicatorView.setupLayout(textList,viewList);
			
		
	}
	
	public void culvertDistinguish(){
		String[] culvertItems = new String[] { 
				"涵洞编号：", "涵洞名称：", "涵洞条码：","路线号：", "路线名称：",
				"管养单位：", "路线类型：", "顺序号：", "所在地：", "中心桩号：",
				"施工桩号：", "涵洞交角：", "涵洞全长：", "盖板总长：", "涵洞净高：",
				"涵顶填土厚：", "修建年月：", "涵洞类型：", "涵洞设计荷载：", "孔数及净跨：",
				"进口类型：","出口类型：", "管理形式码："};
		
		CulvertDao dao = new CulvertDao(this);
		info = dao.get(culvertId);
		
		int iLength = culvertItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", culvertItems[i]);
		}
		maps[0].put("value",info.getCulvertCode());//涵洞编号
		maps[1].put("value",info.getCulvertName());//涵洞名称
		maps[2].put("value",info.getBarCode());//涵洞条码
		maps[3].put("value",info.getLineNumber());//路线号
		maps[4].put("value",info.getLineName());//路线名称
		maps[5].put("value",info.getMmName());//管养单位　
		maps[6].put("value",info.getLineType());//路线类型
		maps[7].put("value",info.getSerialNumber());//顺序号
		maps[8].put("value",info.getLocationName());//所在地
		maps[9].put("value",info.getCenterStake());//中心桩号
		maps[10].put("value",info.getMmName());//施工桩号
		maps[11].put("value",info.getCulvertAngle());//涵洞交角
		maps[12].put("value",info.getCulvertLength());//涵洞全长
		maps[13].put("value",info.getCoverLength());//盖板总长
		maps[14].put("value",info.getCulvertHeight());//涵洞净高
		maps[15].put("value",info.getFillDepth());//涵顶填土厚
		maps[16].put("value",info.getConstructDate());//修建年月
		maps[17].put("value",info.getCulvertType());//涵洞类型
		maps[18].put("value",info.getDesignLoad());//涵洞设计荷载
		maps[19].put("value",info.getHoleSpan());//孔数及净跨
		maps[20].put("value",info.getInletType());//进口类型　
		maps[21].put("value",info.getOutletType());//出口类型
		maps[22].put("value",info.getManageCode());//管理形式码
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
	
	public void culvertEvaluate(){
		String[] culvertItems = new String[] { 
				"涵洞编号：", "涵洞名称：", "适应性综合评分：","综合评级："};
		
		int iLength = culvertItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", culvertItems[i]);
		}
		
		maps[0].put("value",info.getCulvertCode());//涵洞编号
		maps[1].put("value",info.getCulvertName());//涵洞名称
		maps[2].put("value",info.getAdaptiveScore());//适应性综合评分
		maps[3].put("value",info.getCompositeRating());//综合评级
		for(int i=0;i<iLength;i++){
			listEvaluate.add(maps[i]);
		}
	}
	
	
	private class MyListAdapter extends ArrayAdapter<CulvertUsualExamBean>{ 
	    private int resource; 
	    public MyListAdapter(Context context, int resourceId, List<CulvertUsualExamBean> objects) { 
	        super(context, resourceId, objects); 
	        // 记录下来稍后使用 
	        resource = resourceId; 
	    }
	    public View getView(final int position, View convertView, ViewGroup parent) { 
	        LinearLayout listView; 
	        // 获取数据 
	        CulvertUsualExamBean b = getItem(position); 
	        String bridgeCode = b.getCulvertCode();
	        String bridgeName = b.getCulvertName();
	        String lineNumber = b.getCheckDate();
	        if(convertView == null) { 
	            listView = new LinearLayout(getContext()); 
	            String inflater = Context.LAYOUT_INFLATER_SERVICE; 
	            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater); 
	            vi.inflate(resource, listView, true); 
	        } else { 
	            listView = (LinearLayout)convertView; 
	        }
	
	        // 填充自定义数据 
	        TextView item_bridgeCode = (TextView) listView.findViewById(R.id.bridgeCode); 
	        TextView item_bridgeName = (TextView) listView.findViewById(R.id.bridgeName); 
	        TextView item_lineNumber = (TextView) listView.findViewById(R.id.lineNumber);
	        item_bridgeCode.setText(bridgeCode);
	        item_bridgeName.setText(bridgeName);
	        item_lineNumber.setText(lineNumber);
	   
	      //  int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色
	     //   listView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同
	
	        return listView; 
	    }
	}

}
