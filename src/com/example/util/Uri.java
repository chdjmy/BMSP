package com.example.util;


import org.apache.commons.lang.StringUtils;

import android.content.Context;

/**
 * �ӿ���
 * @author minghui.wang
 * */
public class Uri {
	
	public Uri(){
		
	}
	
	public Uri(Context context){
		this.context = context;
	}
	
	private Context context;
	
	/**
	 * ��֤KEY ��  �ӿ�KEY һ��
	 * */
	public static final String KEY = "scan";
	/**
	 * �ӿ�Ӧ��
	 * */
	public static final String WEB_APP = "BMS";
	
	public static final String KEY_SUCC = "success";
	
	public static final String KEY_MSG = "msg";

	public static final String KEY_DATA = "data";
	
	public static final String KEY_FLAG = "flag";
	
	
	public static final String TAG = "mw.scan.log";
	/**
	 * @param ip      ip 
	 * @param port    �˿�
	 * @param method  ����
	 * */
	public static String getUri(String ip, String port, String web_app, String method){
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://").append(ip);
		if(StringUtils.isNotEmpty(port)){
			buffer.append(":").append(port);
		}
		buffer.append("/").append(web_app).append("/").append(method);
		System.out.println(buffer.toString());
		return buffer.toString();
	}
	public static String getUri(String method){
		return "";
	}
	
	/**
	 * ��ʼ���ӿ�
	 * */
	public class Init{
		/**
		 * ��ȡ��ǰ�����ϵͳʱ��
		 * */
		public static final String GET_CURRENTTIME = "/json/Mobile_getCurrentTime.action";
		/**
		 * �û���Ϣ(����Ա)
		 * */
		public static final String USER_INFO = "json/Mobile_initUsers.action";
		public static final int MSG_USER_INFO = 1;
		/**
		 * �����Լ��
		 * */
		public static final String USUALEXAM_INFO = "json/Mobile_initUsualExam.action";
		public static final int MSG_USUALEXAM_INFO = 2;
		public static final String USUALEXAM_INFO_PAGE = "json/Mobile_initUsualExamPage.action";
		public static final int MSG_USUALEXAM_INFO_PAGE = 3;
		/**
		 * ����������Ϣ
		 * */
		public static final String BRIDGE_INFO = "json/Mobile_initBridge.action";
		public static final int MSG_BRIDGE_INFO = 4;
		public static final String BRIDGE_INFO_PAGE = "json/Mobile_initBridgePage.action";
		public static final int MSG_BRIDGE_INFO_PAGE = 5;
		/**
		 * ά���豸
		 * */
		public static final String EQUIPMAINTAINPLAN_INFO = "Mobile_initEquipmaintainplan.action";
		public static final int MSG_EQUIPMAINTAINPLAN_INFO = 6;
		/**
		 * ά���Ǽ�
		 * */
		public static final String MAINTAIN_INFO = "Mobile_initMaintain.action";
		public static final int MSG_MAINTAIN_INFO = 7;
		/**
		 * ά���Ǽ��豸�б�
		 * */
		public static final String MAINTAINEQUIPLIST_INFO = "Mobile_upLoadBridgeUsualExam.action";
		public static final int MSG_MAINTAINEQUIPLIST_INFO = 8;
		/**
		 * ���ϵ�
		 * */
		public static final String FAULTSHEET_INFO = "Mobile_initFaultsheet.action";
		public static final int MSG_FAULTSHEET_INFO = 9;
		/**
		 * �������
		 * */
		public static final String FAULTTYPE_INFO = "Mobile_initFaulttype.action";
		public static final int MSG_FAULTTYPE_INFO = 10;
		/**
		 * �޸��豸�б�
		 * */
		public static final String EQUIPFIXLIST_INFO = "Mobile_initEquipfixlist.action";
		public static final int MSG_EQUIPFIXLIST_INFO = 11;
		/**
		 * ��ʼ��ʼ��
		 * */
		public static final int MSG_START_INIT = 12;
		/**
		 * ������ʼ��
		 * */
		public static final int MSG_END_INIT = 13;
	}
	
	
	public class Sync{
		/**
		 * �û���Ϣ(����Ա)
		 * */
		public static final String USER_INFO = "Mobile_initUsers.action";
		public static final int MSG_USER_INFO = 1;
		/**
		 * ������Ϣ
		 * */
		public static final String BRANCH_INFO = "Mobile_initBranch.action";
		public static final int MSG_BRANCH_INFO = 2;
		/**
		 * ��ַ��Ϣ
		 * */
		public static final String ADDRESS_INFO = "Mobile_initAddress.action";
		public static final int MSG_ADDRESS_INFO = 3;
		/**
		 * �豸��Ϣ
		 * */
		public static final String EQUIP_INFO = "Mobile_initEquip.action";
		public static final int MSG_EQUIP_INFO = 4;
		/**
		 * ά���ƻ�
		 * */
		public static final String MAINTAINPLAN_INFO = "Mobile_initMaintainplan.action";
		public static final int MSG_MAINTAINPLAN_INFO = 5;
		/**
		 * ά���豸
		 * */
		public static final String EQUIPMAINTAINPLAN_INFO = "Mobile_initEquipmaintainplan.action";
		public static final int MSG_EQUIPMAINTAINPLAN_INFO = 6;
		/**
		 * ά���Ǽ�
		 * */
		public static final String MAINTAIN_INFO = "Mobile_initMaintain.action";
		public static final int MSG_MAINTAIN_INFO = 7;
		/**
		 * ά���Ǽ��豸�б�
		 * */
		public static final String MAINTAINEQUIPLIST_INFO = "Mobile_initMaintainEquipList.action";
		public static final int MSG_MAINTAINEQUIPLIST_INFO = 8;
		/**
		 * ���ϵ�
		 * */
		public static final String FAULTSHEET_INFO = "Mobile_initFaultsheet.action";
		public static final int MSG_FAULTSHEET_INFO = 9;
		/**
		 * �������
		 * */
		public static final String FAULTTYPE_INFO = "Mobile_initFaulttype.action";
		public static final int MSG_FAULTTYPE_INFO = 10;
		/**
		 * �޸��豸�б�
		 * */
		public static final String EQUIPFIXLIST_INFO = "Mobile_initEquipfixlist.action";
		public static final int MSG_EQUIPFIXLIST_INFO = 11;
		/**
		 * ��ʼ��ʼ��
		 * */
		public static final int MSG_START_SYNC = 32;
		/**
		 * ������ʼ��
		 * */
		public static final int MSG_END_SYNC = 33;
	}
	
	/**
	 * �ϴ���Ϣ
	 * */
	public class Upload{
		/**
		 * �����Լ�������ϴ�
		 * */
		public static final String BRIDGEUSUALEXAM_INFO = "json/Mobile_upLoadBridgeUsualExam.action";
		public static final int MSG_BRIDGEUSUALEXAM_INFO = 14;
		
		/**
		 * �����Լ�������ϴ����ȡ��������
		 * */
		public static final String BRIDGEUSUALEXAM_DOWNLOAD = "json/Mobile_downLoadBridgeUsualExam.action";
		public static final int MSG_BRIDGEUSUALEXAM_DOWNLOAD = 15;
		
		/**
		 * ά���Ǽ�
		 * */
		public static final String MAINTAIN_INFO = "Mobile_uploadMaintain.action";
		public static final int MSG_MAINTAIN_INFO = 16;
		
		/**
		 * ά���Ǽ��豸�б�
		 * */
		public static final String MAINTAINEQUIPLIST_INFO = "Mobile_uploadMaintainEquipList.action";
		public static final int MSG_MAINTAINEQUIPLIST_INFO = 17;
		
		/**
		 * ���ϵ�
		 * */
		public static final String FAULTSHEET_INFO = "Mobile_uploadFaultsheet.action";
		public static final int MSG_FAULTSHEET_GZ_INFO = 18;
		public static final int MSG_FAULTSHEET_WH_INFO = 23;
		
		/**
		 * �޸��豸�б�
		 * */
		public static final String EQUIPFIXLIST_INFO = "Mobile_uploadEquipfixlist.action";
		public static final int MSG_EQUIPFIXLIST_INFO = 19;
		
		
		public static final int MSG_START_UPLOAD = 20;
		public static final int MSG_END_UPLOAD = 21;
		
		public static final int MSG_UPLOAD_NUM = 22;
	}
	
	public class App{
		/**
		 * ά���ƻ��б�
		 * */
		public static final int MSG_MAINTAINPLAN_LIST = 14;
		
		/**
		 * �豸�б�
		 */
		public static final int MSG_EQUIP_LIST = 15;
		/**
		 * �ҵ���Ϣ
		 * */
		public static final int MSG_MYINFO = 16;
		/**
		 * �豸����
		 */
		public static final int MSG_EQUIP_DETAIL = 17;
		/**
		 * ά���Ǽ��б�
		 * */
		public static final int MSG_MAINTAIN_LIST = 18;
		/**
		 * ά���Ǽ��豸�б�
		 * */
		public static final int MSG_MAINTAINEQUIP_LIST = 19;
		/**
		 * ά���Ǽ�����
		 * */
		public static final int MSG_MAINTAIN_ADD = 20;
		/**
		 * ά���Ǽ��޸�
		 * */
		public static final int MSG_MAINTAIN_UPDATE = 21;
		/**
		 * ά���Ǽ��豸�޸�
		 * */
		public static final int MSG_MAINTAINEQUIP_UPDATE = 22;
		/**
		 * ���ϵ� ά���б�
		 * */
		public static final int MSG_WH_FAULTSHEET_LIST = 23;
		/**
		 * ���ϵ� �����б�
		 * */
		public static final int MSG_GZ_FAULTSHEET_LIST = 24;
		
		/**
		 * ���ϵ� ���� ����
		 */
		public static final int MSG_GZ_FAULTSHEET_DETAIL = 25;
		
		
		/**
		 * ���ϴ��� �豸
		 */
		public static final int MSG_EQUIP_GZDXLIST = 26;
		/**
		 * ά�޵Ǽ�(������)
		 * */
		public static final int MSG_WXDJ_MAINTAIN = 27;
		/**
		 * ���ϱ���(������)
		 * */
		public static final int MSG_GZBX_MAINTAIN = 28;
		/**
		 * ά�޵��б�  ά�޵Ǽ�  �ύ
		 */
		public static final int MSG_GZ_FAULTSHEET_COMMIT = 29;
		/**
		 * ά���Ǽ� ����(������)
		 * */
		public static final int MSG_WXDJ_ADD_MAINTAIN = 30;
		/**
		 * ���ϱ��� ���ӣ���������
		 * */
		public static final int MSG_GZBX_ADD_MAINTAIN = 31;
		/**
		 *  ����
		 */
		public static final int MSG_GZGL_FAULTSHEET_DETAIL = 32;
		/**
		 * ���ϵ� ���ϴ��� �豸
		 */
		public static final int MSG_GZ_EQUIP_GZDXLIST = 33;
		/**
		 * ά�޵��б� ά�޵Ǽ�
		 */
		public static final int MSG_WXD_ADDWXDJ = 34;
		/**
		 * ά�޵��б� ά�޵Ǽ� �����豸
		 */
		public static final int MSG_WX_EQUIP_GZDXLIST = 35;
		/**
		 * ���ϵ��б� ���ϱ���
		 */
		public static final int MSG_GZ_ADDGZBX = 36;
		/**
		 * ���ϵ��б� ���ϱ��� �����豸
		 */
		public static final int MSG_GZ_EQUIP_GZBXLIST = 37;
		
		/**
		 * ��dialog
		 * */
		public static final int MSG_OPEN_DIALOG = 38;
		/**
		 * �ر�dialog
		 * */
		public static final int MSG_CLOSE_DIALOG = 39;
		/**
		 * �޸� �ҵ���Ϣ
		 */
		public static final int MSG_MYINFO_UPDATE = 40;
		/**
		 * �޸�����
		 */
		public static final int MSG_MYINFO_CHANGEMM = 41;
	}
	
	/**
	 * �ϱ��ӿ���
	 * */
	public class Report{
		public static final String FAULTSHEET = "Mobile_reportFaultsheet.action";
	}
	
	
	
}
