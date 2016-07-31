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
 * �ź���tab
 * @author JMY
 */
public class TabActivity extends Activity {
	private ViewPagerIndicatorView viewPagerIndicatorView;
	
	
	//������ϢTab
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
	//���������Լ��
	private ListView listViewBridgeUsualExam;
	private List<UsualExamBean> listUsualExams;
	private UsualExamDao usualExamDao;
	private MyListAdapter adapter;
	private String bridgeId;
	private String bridgeCode;
	
	//��������
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
		
		//������Ϣ��view
		View viewBridgeInfo = LayoutInflater.from(this).inflate(R.layout.fragment_bridgeinfo_detail,
				null);
		ListView listView = ((ListView) viewBridgeInfo
				.findViewById(R.id.bridgeinfo_detail));
		dataDistinguish();
		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.listitem_bridgeinfo_detail, new String[] { "name",
						"value" }, new int[] { R.id.name, R.id.value });
		listView.setAdapter(listAdapter);
		
		//�����Լ���view
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
		
		
		//�������̵�view
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
		textList.add("������Ϣ");
		viewList.add(viewBridgeInfo);
		textList.add("�����Լ��");
		viewList.add(viewUsuslExam);
		textList.add("��������");
		viewList.add(viewProject);
		this.viewPagerIndicatorView.setupLayout(textList,viewList);
			
		
	}
	
	public void dataDistinguish() {
		String[] bridgeItems = new String[] { "�������룺", "�������ƣ�", "�������룺",
				"·�ߺţ�", "·�����ƣ�", "·�����ͣ�", "��Ҫ·�ߣ�", "˳��ţ�", "���ڵأ�", "����׮�ţ�",
				"������λ��", "��Խ�������ƣ�", "��Խ�������ͣ�", "ʩ��׮�ţ�", "��·�����ȼ���",
				"�������ʣ�", "�������ࣺ", "��ƺ��صȼ���", "Ŀǰ���صȼ���", "�ϲ��ṹ���ͣ�", "�Ŷ����ͣ�",
				"��̨���ͣ�", "֧�����ͣ�", "�Ŷջ������ͣ�", "��̨�������ͣ�", "������װ���ͣ�", "���������ͣ�",
				"������;��", "����״̬��", "�����ȼ���", "��������"};
		
		BridgeDao dao = new BridgeDao(this);
		//BridgeBean info = dao.get(mBridgeId);
		BridgeBean info = dao.get(bridgeId);
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//��������
		maps[1].put("value",info.getBridgeName());//��������
		maps[2].put("value",info.getBarCode());//��������
		maps[3].put("value",info.getLineNumber());//·�ߺ�
		maps[4].put("value",info.getLineName());//·������
		maps[5].put("value",info.getLineType());//·������
		maps[6].put("value",info.getSecondaryLine());//��Ҫ·��
		maps[7].put("value",info.getSeqNumber());//˳���
		maps[8].put("value",info.getLocationName());//���ڵ�
		maps[9].put("value",info.getCenterStake());//����׮��
		maps[10].put("value",info.getMmName());//������λ��
		maps[11].put("value",info.getCrossName());//��Խ��������
		maps[12].put("value",info.getCrossType());//��Խ��������
		maps[13].put("value",info.getConstructionStake());//ʩ��׮��
		maps[14].put("value",info.getRoadTechnicalGrade());//��·�����ȼ�
		maps[15].put("value",info.getBridgeProperty());//��������
		maps[16].put("value",info.getBridgeType());//��������
		maps[17].put("value",info.getDesignLoad());//��ƺ��صȼ�
		maps[18].put("value",info.getNowLoad());//Ŀǰ���صȼ�
		maps[19].put("value",info.getSuperstructureForm());//�ϲ��ṹ����
		maps[20].put("value",info.getBridgePierType());//�Ŷ����͡�
		maps[21].put("value",info.getAbutmentType());//��̨����
		maps[22].put("value",info.getSupportsType());//֧������
		maps[23].put("value",info.getBridgePierBaseType());//�Ŷջ�������
		maps[24].put("value",info.getAbutmentBaseType());//��̨��������
		maps[25].put("value",info.getBridgeDeckType());//������װ����
		maps[26].put("value",info.getExpansionJointsType());//����������
		maps[27].put("value",info.getBridgeUse());//������;
		maps[28].put("value",info.getBridgeState());//����״̬
		maps[29].put("value",info.getAdministrativeLevel());//�����ȼ�
		maps[30].put("value",info.getBridgeTown());//��������
		
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
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
	        // ��¼�����Ժ�ʹ�� 
	        resource = resourceId; 
	    }
	    public View getView(int position, View convertView, ViewGroup parent) { 
	        LinearLayout listView; 
	        // ��ȡ���� 
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
	            // ����ֻ���û�����ù� 
	            listView = (LinearLayout)convertView; 
	            Log.d("Adapter", "convertView is not null now"); 
	        }
	
	        // ����Զ������� 
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
