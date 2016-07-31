package com.example.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bean.BridgeBean;
import com.example.bean.BridgeProjectBean;
import com.example.bean.UsualExamBean;
import com.example.common.tab.ViewPagerIndicatorView;
import com.example.dao.BridgeDao;
import com.example.dao.BridgeProjectDao;
import com.example.dao.UsualExamDao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
public class TabActivity extends Activity {
	private ViewPagerIndicatorView viewPagerIndicatorView;
	
	
	//桥梁信息Tab
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
	//桥梁经常性检查
	private ListView listViewBridgeUsualExam;
	private List<UsualExamBean> listUsualExams;
	private UsualExamDao usualExamDao;
	private MyListAdapter adapter;
	private String bridgeId;
	private String bridgeCode;
	
	//养护工程
	private ListView listViewBridgeInfo;
	private List<BridgeProjectBean> listBridges;
	private BridgeProjectDao bridgeDao;
	private ProjectListAdapter adapterProject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		
		bridgeId = getIntent().getStringExtra("bridgeId");
		bridgeCode = getIntent().getStringExtra("bridgeCode");
		
		//桥梁信息的view
		View viewBridgeInfo = LayoutInflater.from(this).inflate(R.layout.fragment_bridgeinfo_detail,
				null);
		ListView listView = ((ListView) viewBridgeInfo
				.findViewById(R.id.bridgeinfo_detail));
		dataDistinguish();
		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.listitem_bridgeinfo_detail, new String[] { "name",
						"value" }, new int[] { R.id.name, R.id.value });
		listView.setAdapter(listAdapter);
		
		//经常性检查的view
		View viewUsuslExam = LayoutInflater.from(this).inflate(R.layout.activity_bridge_usual_exam,
				null);
		listViewBridgeUsualExam = (ListView) viewUsuslExam.findViewById(R.id.listViewBridgeUsualExam);
		usualExamDao = new UsualExamDao(this);
		listUsualExams = usualExamDao.findByBridgeCode(bridgeCode);
		usualExamDao = new UsualExamDao(this);
		adapter = new MyListAdapter(TabActivity.this,   
                R.layout.listitem_bridge_usualexam,   
                listUsualExams); 
		listViewBridgeUsualExam.setAdapter(adapter);
		listViewBridgeUsualExam.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(TabActivity.this,UsualExamDetailActivity.class);
				intent.putExtra("usualExamId", listUsualExams.get(position).getUsualExamId());
				startActivity(intent);
			}
			
		});
		
		
		//养护工程的view
		View viewProject = LayoutInflater.from(this).inflate(R.layout.activity_bridge_project,
				null);
		
		listViewBridgeInfo = (ListView)viewProject.findViewById(R.id.listViewBridgeInfo);
		bridgeDao = new BridgeProjectDao(this);
		listBridges = bridgeDao.findByCode(bridgeCode);
		adapterProject = new ProjectListAdapter(TabActivity.this,   
                 R.layout.listitem_bridge_usualexam,   
                 listBridges); 
		listViewBridgeInfo.setAdapter(adapterProject);
		listViewBridgeInfo.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("projectId", listBridges.get(position).getProjectId());
				intent.setClass(TabActivity.this,ProjectDetailActivity.class);
				startActivity(intent);
			}
			
		});		
		
		
		//set ViewPagerIndicatorView
		this.viewPagerIndicatorView = (ViewPagerIndicatorView) findViewById(R.id.viewpager_indicator_view);
		final List<String> textList = new ArrayList<String>();
		final List<View> viewList = new ArrayList<View>();
		textList.add("桥梁信息");
		viewList.add(viewBridgeInfo);
		textList.add("经常性检查");
		viewList.add(viewUsuslExam);
		textList.add("养护工程");
		viewList.add(viewProject);
		this.viewPagerIndicatorView.setupLayout(textList,viewList);
			
		
	}
	
	public void dataDistinguish() {
		String[] bridgeItems = new String[] { "桥梁代码：", "桥梁名称：", "桥梁条码：",
				"路线号：", "路线名称：", "路线类型：", "次要路线：", "顺序号：", "所在地：", "中心桩号：",
				"管养单位：", "跨越地物名称：", "跨越地物类型：", "施工桩号：", "公路技术等级：",
				"桥梁性质：", "桥梁分类：", "设计荷载等级：", "目前荷载等级：", "上部结构类型：", "桥墩类型：",
				"桥台类型：", "支座类型：", "桥墩基础类型：", "桥台基础类型：", "桥面铺装类型：", "伸缩缝类型：",
				"桥梁用途：", "桥梁状态：", "行政等级：", "所属乡镇："};
		
		BridgeDao dao = new BridgeDao(this);
		//BridgeBean info = dao.get(mBridgeId);
		BridgeBean info = dao.get(bridgeId);
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//桥梁代码
		maps[1].put("value",info.getBridgeName());//桥梁名称
		maps[2].put("value",info.getBarCode());//桥梁条码
		maps[3].put("value",info.getLineNumber());//路线号
		maps[4].put("value",info.getLineName());//路线名称
		maps[5].put("value",info.getLineType());//路线类型
		maps[6].put("value",info.getSecondaryLine());//次要路线
		maps[7].put("value",info.getSeqNumber());//顺序号
		maps[8].put("value",info.getLocationName());//所在地
		maps[9].put("value",info.getCenterStake());//中心桩号
		maps[10].put("value",info.getMmName());//管养单位　
		maps[11].put("value",info.getCrossName());//跨越地物名称
		maps[12].put("value",info.getCrossType());//跨越地物类型
		maps[13].put("value",info.getConstructionStake());//施工桩号
		maps[14].put("value",info.getRoadTechnicalGrade());//公路技术等级
		maps[15].put("value",info.getBridgeProperty());//桥梁性质
		maps[16].put("value",info.getBridgeType());//桥梁分类
		maps[17].put("value",info.getDesignLoad());//设计荷载等级
		maps[18].put("value",info.getNowLoad());//目前荷载等级
		maps[19].put("value",info.getSuperstructureForm());//上部结构类型
		maps[20].put("value",info.getBridgePierType());//桥墩类型　
		maps[21].put("value",info.getAbutmentType());//桥台类型
		maps[22].put("value",info.getSupportsType());//支座类型
		maps[23].put("value",info.getBridgePierBaseType());//桥墩基础类型
		maps[24].put("value",info.getAbutmentBaseType());//桥台基础类型
		maps[25].put("value",info.getBridgeDeckType());//桥面铺装类型
		maps[26].put("value",info.getExpansionJointsType());//伸缩缝类型
		maps[27].put("value",info.getBridgeUse());//桥梁用途
		maps[28].put("value",info.getBridgeState());//桥梁状态
		maps[29].put("value",info.getAdministrativeLevel());//行政等级
		maps[30].put("value",info.getBridgeTown());//所属乡镇
		
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
	
	
	private class MyListAdapter extends ArrayAdapter<UsualExamBean>{ 
	    private int resource; 
	    public MyListAdapter(Context context, int resourceId, List<UsualExamBean> objects) { 
	        super(context, resourceId, objects); 
	        // 记录下来稍后使用 
	        resource = resourceId; 
	    }
	    public View getView(final int position, View convertView, ViewGroup parent) { 
	        LinearLayout listView; 
	        // 获取数据 
	        UsualExamBean b = getItem(position); 
	        String bridgeCode = b.getBridgeCode();
	        String bridgeName = b.getBridgeName();
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
	        return listView; 
	    }
	}
	
	
	private class ProjectListAdapter extends ArrayAdapter<BridgeProjectBean>{ 
	    private int resource; 
	    public ProjectListAdapter(Context context, int resourceId, List<BridgeProjectBean> objects) { 
	        super(context, resourceId, objects); 
	        // 记录下来稍后使用 
	        resource = resourceId; 
	    }
	    public View getView(int position, View convertView, ViewGroup parent) { 
	        LinearLayout listView; 
	        // 获取数据 
	        BridgeProjectBean b = getItem(position); 
	        String bridgeCode = b.getBridgeCode();
	        String bridgeName = b.getBridgeName();
	        String lineNumber = b.getProjectDate().substring(0,10);
	
	        if(convertView == null) { 
	            listView = new LinearLayout(getContext()); 
	            String inflater = Context.LAYOUT_INFLATER_SERVICE; 
	            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater); 
	            vi.inflate(resource, listView, true); 
	            Log.d("Adapter", "convertView is null now"); 
	        } else { 
	            // 很奇怪基本没被调用过 
	            listView = (LinearLayout)convertView; 
	            Log.d("Adapter", "convertView is not null now"); 
	        }
	
	        // 填充自定义数据 
	        TextView item_bridgeCode = (TextView) listView.findViewById(R.id.bridgeCode); 
	        TextView item_bridgeName = (TextView) listView.findViewById(R.id.bridgeName); 
	        TextView item_lineNumber = (TextView) listView.findViewById(R.id.lineNumber);
	        item_bridgeCode.setText(bridgeCode);
	        item_bridgeName.setText(bridgeName);
	        item_lineNumber.setText(lineNumber);
	        return listView; 
	    }
	}

}
