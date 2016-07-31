package com.example.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bean.BridgeBean;
import com.example.bean.CulvertBean;
import com.example.bms.dummy.DummyContent;
import com.example.dao.BridgeDao;
import com.example.dao.CulvertDao;

/**
 * A fragment representing a single BridgeInfo detail screen. This fragment is
 * either contained in a {@link BridgeInfoListActivity} in two-pane mode (on
 * tablets) or a {@link BridgeInfoDetailActivity} on handsets.
 */
public class BridgeInfoDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;
	
	private String mBridgeId;
	private String mCulvertId;
	/**
	 * ��������
	 */
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BridgeInfoDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
		if(getArguments().containsKey("bridgeId")){
			mBridgeId = getArguments().getString("bridgeId");
		}
		if(getArguments().containsKey("culvertId")){
			mCulvertId = getArguments().getString("culvertId");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bridgeinfo_detail,
				container, false);
		
		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			int itemNum = Integer.parseInt(mItem.id);

			Map<String, Object>[] maps;
			switch (itemNum) {
			case 1:// ʶ��
				dataDistinguish();
				break;
			case 2:// �ṹ
				dataStructure();
				break;
			case 3:// ����
				dataEconomy();
				break;
			case 4:// ����
				dataRecord();
				break;
			case 5:// ������
				dataDuty();
				break;
				
			//�����Ǻ���
			case 6://ʶ��
				culvertDistinguish();
				break;
			case 7://����
				culvertDisease();
				break;
			case 8://����
				culvertEvaluate();
				break;
			default:
				break;
			}
			ListView listView = ((ListView) rootView
					.findViewById(R.id.bridgeinfo_detail));
			SimpleAdapter listAdapter = new SimpleAdapter(getActivity(), list,
					R.layout.listitem_bridgeinfo_detail, new String[] { "name",
							"value" }, new int[] { R.id.name, R.id.value });
			listView.setAdapter(listAdapter);
		}

		return rootView;
	}

	public void dataDistinguish() {
		String[] bridgeItems = new String[] { "�������룺", "�������ƣ�", "�������룺",
				"·�ߺţ�", "·�����ƣ�", "·�����ͣ�", "��Ҫ·�ߣ�", "˳��ţ�", "���ڵأ�", "����׮�ţ�",
				"������λ��", "��Խ�������ƣ�", "��Խ�������ͣ�", "ʩ��׮�ţ�", "��·�����ȼ���",
				"�������ʣ�", "�������ࣺ", "��ƺ��صȼ���", "Ŀǰ���صȼ���", "�ϲ��ṹ���ͣ�", "�Ŷ����ͣ�",
				"��̨���ͣ�", "֧�����ͣ�", "�Ŷջ������ͣ�", "��̨�������ͣ�", "������װ���ͣ�", "���������ͣ�",
				"������;��", "����״̬��", "�����ȼ���", "��������"};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
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

	public void dataStructure() {
		String[] bridgeItems = new String[] { "�������룺", "�������ƣ�", "�羶��ϣ�",
				"���羶��", "����ȫ����", "�羶�ܳ���", "�ſ���ϣ�", "���澻��", "�Ƿ��·խ�ţ�", "�Ÿߣ�",
				"�������£�", "�Ľ����£�", "ͨ���ȼ���", "�����޸ߣ�", "���´�ֱ���ߣ�",
				"���н���ߣ�", "����ȫ��", "����ƽ���߰뾶��", "��ͷ·�澻��", "ʸ��ȣ�", "�������£�",
				"����б������", "����������", "������ʽ��"};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		
		maps[0].put("value",info.getBridgeCode());//��������
		maps[1].put("value",info.getBridgeName());//��������
		maps[2].put("value",info.getSpanCombination());//�羶���
		maps[3].put("value",info.getMaxSpan());//���羶
		maps[4].put("value",info.getBridgeLength());//����ȫ��
		maps[5].put("value",info.getSpanLength());//�羶�ܳ�
		maps[6].put("value",info.getBridgeWidthComb());//�ſ����
		maps[7].put("value",info.getClearWidth());//���澻��
		maps[8].put("value",info.getIsWroadNbridge());//�Ƿ��·խ��
		maps[9].put("value",info.getBridgeHight());//�Ÿ�
		maps[10].put("value",info.getBuildYear());//�������¡�
		maps[11].put("value",info.getRebuildYear());//�Ľ�����
		maps[12].put("value",info.getNavigationGrade());//ͨ���ȼ�
		maps[13].put("value",info.getBridgeHightLimit());//�����޸�
		maps[14].put("value",info.getVerticalClearHeight());//���´�ֱ����
		maps[15].put("value",info.getMiddleSectionHigh());//���н����
		maps[16].put("value",info.getBridgeWidth());//����ȫ��
		maps[17].put("value",info.getFlatCurveRadius());//����ƽ���߰뾶
		maps[18].put("value",info.getBriFrontRoadWidth());//��ͷ·�澻��
		maps[19].put("value",info.getSpanRatio());//ʸ���
		maps[20].put("value",info.getDeckLongitudinal());//��������
		maps[21].put("value",info.getCurvedRampFeature());//����б����
		maps[22].put("value",info.getInterchangeFeature());//��������
		maps[23].put("value",info.getCrossForm());//������ʽ
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}

	public void dataEconomy() {
		String[] bridgeItems = new String[] { "�������룺", "�������ƣ�", "����ۣ�",
				"ʩ�����ڣ�", "�������ı�ߣ�", "��ƺ�ˮƵ�ʣ�", "��Ƴ�ˢ��ߣ�", "���Ż��ױ�ߣ�", "��ʷ����ˮ��", 
				"���վ���ͨ����","�����������ͣ�", "��̨��ײ��ʩ���ͣ�", "�ػ����ʣ�", "���������", "���ϸ��裺",
				"�ָ�����", "�շ������", "���ʱ�٣�", "�����뻷��Э���ȣ�", "����������"};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//��������
		maps[1].put("value",info.getBridgeName());//��������
		maps[2].put("value",info.getTotalCost());//�����
		maps[3].put("value",info.getConstructionPeriod());//ʩ������
		maps[4].put("value",info.getDeckCenterElevation());//�������ı��
		maps[5].put("value",info.getFloodFrequency());//��ƺ�ˮƵ��
		maps[6].put("value",info.getScourElevation());//��Ƴ�ˢ���
		maps[7].put("value",info.getMainBridgeBaseElevation());//���Ż��ױ��
		maps[8].put("value",info.getHistoricalMaximumFlood());//��ʷ����ˮ
		maps[9].put("value",info.getAnnualAverageDailyTraffic());//���վ���ͨ��
		maps[10].put("value",info.getProtectionProjectType());//��������������
		maps[11].put("value",info.getPierProtectionType());//��̨��ײ��ʩ����
		maps[12].put("value",info.getGeologicalFoundation());//�ػ�����
		maps[13].put("value",info.getSeismic());//�������
		maps[14].put("value",info.getBridgeAttached());//���ϸ���
		maps[15].put("value",info.getMedian());//�ָ���
		maps[16].put("value",info.getChargeStation());//�շ����
		maps[17].put("value",info.getDesignSpeed());//���ʱ��
		maps[18].put("value",info.getEnvironmentCoordinationDegree());//�����뻷��Э����
		maps[19].put("value",info.getEnvironmentalConditions());//��������
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}

	public void dataRecord() {
		String[] bridgeItems = new String[] { "�������룺", "�������ƣ�", "������ϱ�ţ�",
				"�������ϱ�ţ�", "�������ϱ�ţ�", "���ܵ�λ��", "��ͨ���ƴ�ʩ��", "��������Ƿ�������",
				"��������깤���ڣ�", "������첿λ��","�������ʣ�", "��Ȩ��λ���ƣ�", "��Ƶ�λ��", "����ߣ�",
				"ʩ����λ��","ʩ�������ˣ�", "���ñ�׼ͼ��", "����λ��", "�������������"};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//��������
		maps[1].put("value",info.getBridgeName());//��������
		maps[2].put("value",info.getDesignDataNo());//������ϱ��
		maps[3].put("value",info.getCompletionDataNo());//�������ϱ��
		maps[4].put("value",info.getMaintainDataNo());//�������ϱ��
		maps[5].put("value",info.getStorageUnits());//���ܵ�λ
		maps[6].put("value",info.getTrafficControlMeasures());//��ͨ���ƴ�ʩ
		maps[7].put("value",info.getIsEvaluatedLast3year());//��������Ƿ�����
		maps[8].put("value",info.getLastReformCompleteDate());//��������깤����
		maps[9].put("value",info.getLatelyRemouldPart());//������첿λ
		maps[10].put("value",info.getProjectProperty());//����������
		maps[11].put("value",info.getOwnerUint());//��Ȩ��λ����
		maps[12].put("value",info.getDesignUnit());//��Ƶ�λ
		maps[13].put("value",info.getDesigner());//�����
		maps[14].put("value",info.getConstructionUnit());//ʩ����λ
		maps[15].put("value",info.getConstructionManager());//ʩ��������
		maps[16].put("value",info.getBlueprint());//���ñ�׼ͼ
		maps[17].put("value",info.getSupervisionUnit());//����λ
		maps[18].put("value",info.getCompletedView());//�����������
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}

	public void dataDuty() {
		String[] bridgeItems = new String[] { "�������룺", "�������ƣ�", "����������������",
				"���������˵绰��", "��������ʦ������", "��������ʦ�绰��", "������λ������������", "������λ�����˵绰��",
				"·�����������������", "·����������˵绰��","�й�·�ְ�Ƭ������", "�й�·�ְ�Ƭ�绰��", "�й�·�ַֹ�������",
				"�й�·�ַֹܵ绰��"};
		
		BridgeDao dao = new BridgeDao(getActivity());
		BridgeBean info = dao.get(mBridgeId);
		
		int iLength = bridgeItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", bridgeItems[i]);
		}
		maps[0].put("value",info.getBridgeCode());//��������
		maps[1].put("value",info.getBridgeName());//��������
		maps[2].put("value",info.getMaintainName());//��������������
		maps[3].put("value",info.getMaintainPhone());//
		maps[4].put("value",info.getProjectName());//��������ʦ����
		maps[5].put("value",info.getProjectPhone());//
		maps[6].put("value",info.getUnitName());//������λ����������
		maps[7].put("value",info.getUnitPhone());//
		maps[8].put("value",info.getRoadManagerName());//·���������������
		maps[9].put("value",info.getRoadManagerPhone());//
		maps[10].put("value",info.getAreaManagerName());//�й�·�ְ�Ƭ����
		maps[11].put("value",info.getAreaManagerPhone());//
		maps[12].put("value",info.getDutyManagerName());//�й�·�ַֹ�����
		maps[13].put("value",info.getDutyManagerPhone());//
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
	
	public void culvertDistinguish(){
		String[] culvertItems = new String[] { 
				"������ţ�", "�������ƣ�", "�������룺","·�ߺţ�", "·�����ƣ�",
				"������λ��", "·�����ͣ�", "˳��ţ�", "���ڵأ�", "����׮�ţ�",
				"ʩ��׮�ţ�", "�������ǣ�", "����ȫ����", "�ǰ��ܳ���", "�������ߣ�",
				"����������", "�޽����£�", "�������ͣ�", "������ƺ��أ�", "���������磺",
				"�������ͣ�","�������ͣ�", "������ʽ�룺"};
		
		CulvertDao dao = new CulvertDao(getActivity());
		CulvertBean info = dao.get(mCulvertId);
		
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
	public void culvertDisease(){
		String[] culvertItems = new String[] { 
				"������ţ�", "�������ƣ�", "��������������","��������","���ײ�����", "����������",
				"��ˮ�ڲ�����", "��ˮ�ڲ�����", "��������������", "������ǽ׶�²�����", "��������������",
				"������ˮ������", "����״����", "���״����", "������ڣ�", "�´μ�����ڣ�",
				"��ע��"};
		
		CulvertDao dao = new CulvertDao(getActivity());
		CulvertBean info = dao.get(mCulvertId);
		
		int iLength = culvertItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];
		
		for(int i=0;i<iLength;i++){
			maps[i] = new HashMap<String, Object>(); 
			maps[i].put("name", culvertItems[i]);
		}
		
		maps[0].put("value",info.getCulvertCode());//�������
		maps[1].put("value",info.getCulvertName());//��������
		maps[2].put("value",info.getBasisDisease());//������������
		maps[3].put("value",info.getBodyDisease());//������
		maps[4].put("value",info.getBottomDisease());//���ײ���
		maps[5].put("value",info.getTopDisease());//��������
		maps[6].put("value",info.getInletDisease());//��ˮ�ڲ���
		maps[7].put("value",info.getOutletDisease());//��ˮ�ڲ���
		maps[8].put("value",info.getPitchingDisease());//������������
		maps[9].put("value",info.getWingSlopeDisease());//������ǽ׶�²���
		maps[10].put("value",info.getJumpDisease());//������������
		maps[11].put("value",info.getDrainageDisease());//������ˮ����
		maps[12].put("value",info.getMaintenanceCondition());//����״��
		maps[13].put("value",info.getCleanCondition());//���״��
		maps[14].put("value",info.getCheckDate());//�������
		maps[15].put("value",info.getNextCheckDate());//�´μ������
		maps[16].put("value",info.getRemark());//��ע
		for(int i=0;i<iLength;i++){
			list.add(maps[i]);
		}
	}
	public void culvertEvaluate(){
		String[] culvertItems = new String[] { 
				"������ţ�", "�������ƣ�", "��Ӧ���ۺ����֣�","�ۺ�������"};
		
		CulvertDao dao = new CulvertDao(getActivity());
		CulvertBean info = dao.get(mCulvertId);
		
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
			list.add(maps[i]);
		}
	}
}
