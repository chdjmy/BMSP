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
 * �ź���tab
 * @author JMY
 */
public class TabCulvertActivity extends Activity {
	private ViewPagerIndicatorView viewPagerIndicatorView;
	
	
	//������ϢTab
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> listEvaluate = new ArrayList<Map<String, Object>>();
	
	//���������Լ��
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
		
		//������Ϣ��view
		View viewCulvertInfo = LayoutInflater.from(this).inflate(R.layout.fragment_bridgeinfo_detail,
				null);
		ListView listView = ((ListView) viewCulvertInfo
				.findViewById(R.id.bridgeinfo_detail));
		culvertDistinguish();
		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.listitem_bridgeinfo_detail, new String[] { "name",
						"value" }, new int[] { R.id.name, R.id.value });
		listView.setAdapter(listAdapter);
		
		//�������۵�view
		View viewCulvertEvaluate = LayoutInflater.from(this).inflate(R.layout.fragment_bridgeinfo_detail,
				null);
		ListView listViewEvaluate = ((ListView) viewCulvertEvaluate
				.findViewById(R.id.bridgeinfo_detail));
		culvertEvaluate();
		SimpleAdapter listAdapterEvaluate = new SimpleAdapter(this, listEvaluate,
				R.layout.listitem_bridgeinfo_detail, new String[] { "name",
						"value" }, new int[] { R.id.name, R.id.value });
		listViewEvaluate.setAdapter(listAdapterEvaluate);		
		
		//�����Լ���view
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
		textList.add("������Ϣ");
		viewList.add(viewCulvertInfo);
		textList.add("�����Լ��");
		viewList.add(viewUsuslExam);
		textList.add("��������");
		viewList.add(viewCulvertEvaluate);
		this.viewPagerIndicatorView.setupLayout(textList,viewList);
			
		
	}
	
	public void culvertDistinguish(){
		String[] culvertItems = new String[] { 
				"������ţ�", "�������ƣ�", "�������룺","·�ߺţ�", "·�����ƣ�",
				"������λ��", "·�����ͣ�", "˳��ţ�", "���ڵأ�", "����׮�ţ�",
				"ʩ��׮�ţ�", "�������ǣ�", "����ȫ����", "�ǰ��ܳ���", "�������ߣ�",
				"����������", "�޽����£�", "�������ͣ�", "������ƺ��أ�", "���������磺",
				"�������ͣ�","�������ͣ�", "������ʽ�룺"};
		
		CulvertDao dao = new CulvertDao(this);
		info = dao.get(culvertId);
		
		int iLength = culvertItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", culvertItems[i]);
		}
		maps[0].put("value",info.getCulvertCode());//�������
		maps[1].put("value",info.getCulvertName());//��������
		maps[2].put("value",info.getBarCode());//��������
		maps[3].put("value",info.getLineNumber());//·�ߺ�
		maps[4].put("value",info.getLineName());//·������
		maps[5].put("value",info.getMmName());//������λ��
		maps[6].put("value",info.getLineType());//·������
		maps[7].put("value",info.getSerialNumber());//˳���
		maps[8].put("value",info.getLocationName());//���ڵ�
		maps[9].put("value",info.getCenterStake());//����׮��
		maps[10].put("value",info.getMmName());//ʩ��׮��
		maps[11].put("value",info.getCulvertAngle());//��������
		maps[12].put("value",info.getCulvertLength());//����ȫ��
		maps[13].put("value",info.getCoverLength());//�ǰ��ܳ�
		maps[14].put("value",info.getCulvertHeight());//��������
		maps[15].put("value",info.getFillDepth());//����������
		maps[16].put("value",info.getConstructDate());//�޽�����
		maps[17].put("value",info.getCulvertType());//��������
		maps[18].put("value",info.getDesignLoad());//������ƺ���
		maps[19].put("value",info.getHoleSpan());//����������
		maps[20].put("value",info.getInletType());//�������͡�
		maps[21].put("value",info.getOutletType());//��������
		maps[22].put("value",info.getManageCode());//������ʽ��
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
	
	public void culvertEvaluate(){
		String[] culvertItems = new String[] { 
				"������ţ�", "�������ƣ�", "��Ӧ���ۺ����֣�","�ۺ�������"};
		
		int iLength = culvertItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", culvertItems[i]);
		}
		
		maps[0].put("value",info.getCulvertCode());//�������
		maps[1].put("value",info.getCulvertName());//��������
		maps[2].put("value",info.getAdaptiveScore());//��Ӧ���ۺ�����
		maps[3].put("value",info.getCompositeRating());//�ۺ�����
		for(int i=0;i<iLength;i++){
			listEvaluate.add(maps[i]);
		}
	}
	
	
	private class MyListAdapter extends ArrayAdapter<CulvertUsualExamBean>{ 
	    private int resource; 
	    public MyListAdapter(Context context, int resourceId, List<CulvertUsualExamBean> objects) { 
	        super(context, resourceId, objects); 
	        // ��¼�����Ժ�ʹ�� 
	        resource = resourceId; 
	    }
	    public View getView(final int position, View convertView, ViewGroup parent) { 
	        LinearLayout listView; 
	        // ��ȡ���� 
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
	
	        // ����Զ������� 
	        TextView item_bridgeCode = (TextView) listView.findViewById(R.id.bridgeCode); 
	        TextView item_bridgeName = (TextView) listView.findViewById(R.id.bridgeName); 
	        TextView item_lineNumber = (TextView) listView.findViewById(R.id.lineNumber);
	        item_bridgeCode.setText(bridgeCode);
	        item_bridgeName.setText(bridgeName);
	        item_lineNumber.setText(lineNumber);
	   
	      //  int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB��ɫ
	     //   listView.setBackgroundColor(colors[position % 2]);// ÿ��item֮����ɫ��ͬ
	
	        return listView; 
	    }
	}

}
