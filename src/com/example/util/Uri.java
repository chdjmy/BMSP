package com.example.util;


import org.apache.commons.lang.StringUtils;

import android.content.Context;

/**
 * 接口类
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
	 * 验证KEY 与  接口KEY 一致
	 * */
	public static final String KEY = "scan";
	/**
	 * 接口应用
	 * */
	public static final String WEB_APP = "BMS";
	
	public static final String KEY_SUCC = "success";
	
	public static final String KEY_MSG = "msg";

	public static final String KEY_DATA = "data";
	
	public static final String KEY_FLAG = "flag";
	
	
	public static final String TAG = "mw.scan.log";
	/**
	 * @param ip      ip 
	 * @param port    端口
	 * @param method  方法
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
	 * 初始化接口
	 * */
	public class Init{
		/**
		 * 获取当前服务端系统时间
		 * */
		public static final String GET_CURRENTTIME = "/json/Mobile_getCurrentTime.action";
		/**
		 * 用户信息(操作员)
		 * */
		public static final String USER_INFO = "json/Mobile_initUsers.action";
		public static final int MSG_USER_INFO = 1;
		/**
		 * 经常性检查
		 * */
		public static final String USUALEXAM_INFO = "json/Mobile_initUsualExam.action";
		public static final int MSG_USUALEXAM_INFO = 2;
		public static final String USUALEXAM_INFO_PAGE = "json/Mobile_initUsualExamPage.action";
		public static final int MSG_USUALEXAM_INFO_PAGE = 3;
		/**
		 * 桥梁基本信息
		 * */
		public static final String BRIDGE_INFO = "json/Mobile_initBridge.action";
		public static final int MSG_BRIDGE_INFO = 4;
		public static final String BRIDGE_INFO_PAGE = "json/Mobile_initBridgePage.action";
		public static final int MSG_BRIDGE_INFO_PAGE = 5;
		/**
		 * 维护设备
		 * */
		public static final String EQUIPMAINTAINPLAN_INFO = "Mobile_initEquipmaintainplan.action";
		public static final int MSG_EQUIPMAINTAINPLAN_INFO = 6;
		/**
		 * 维护登记
		 * */
		public static final String MAINTAIN_INFO = "Mobile_initMaintain.action";
		public static final int MSG_MAINTAIN_INFO = 7;
		/**
		 * 维护登记设备列表
		 * */
		public static final String MAINTAINEQUIPLIST_INFO = "Mobile_upLoadBridgeUsualExam.action";
		public static final int MSG_MAINTAINEQUIPLIST_INFO = 8;
		/**
		 * 故障单
		 * */
		public static final String FAULTSHEET_INFO = "Mobile_initFaultsheet.action";
		public static final int MSG_FAULTSHEET_INFO = 9;
		/**
		 * 故障类别
		 * */
		public static final String FAULTTYPE_INFO = "Mobile_initFaulttype.action";
		public static final int MSG_FAULTTYPE_INFO = 10;
		/**
		 * 修复设备列表
		 * */
		public static final String EQUIPFIXLIST_INFO = "Mobile_initEquipfixlist.action";
		public static final int MSG_EQUIPFIXLIST_INFO = 11;
		/**
		 * 开始初始化
		 * */
		public static final int MSG_START_INIT = 12;
		/**
		 * 结束初始化
		 * */
		public static final int MSG_END_INIT = 13;
	}
	
	
	public class Sync{
		/**
		 * 用户信息(操作员)
		 * */
		public static final String USER_INFO = "Mobile_initUsers.action";
		public static final int MSG_USER_INFO = 1;
		/**
		 * 部门信息
		 * */
		public static final String BRANCH_INFO = "Mobile_initBranch.action";
		public static final int MSG_BRANCH_INFO = 2;
		/**
		 * 地址信息
		 * */
		public static final String ADDRESS_INFO = "Mobile_initAddress.action";
		public static final int MSG_ADDRESS_INFO = 3;
		/**
		 * 设备信息
		 * */
		public static final String EQUIP_INFO = "Mobile_initEquip.action";
		public static final int MSG_EQUIP_INFO = 4;
		/**
		 * 维护计划
		 * */
		public static final String MAINTAINPLAN_INFO = "Mobile_initMaintainplan.action";
		public static final int MSG_MAINTAINPLAN_INFO = 5;
		/**
		 * 维护设备
		 * */
		public static final String EQUIPMAINTAINPLAN_INFO = "Mobile_initEquipmaintainplan.action";
		public static final int MSG_EQUIPMAINTAINPLAN_INFO = 6;
		/**
		 * 维护登记
		 * */
		public static final String MAINTAIN_INFO = "Mobile_initMaintain.action";
		public static final int MSG_MAINTAIN_INFO = 7;
		/**
		 * 维护登记设备列表
		 * */
		public static final String MAINTAINEQUIPLIST_INFO = "Mobile_initMaintainEquipList.action";
		public static final int MSG_MAINTAINEQUIPLIST_INFO = 8;
		/**
		 * 故障单
		 * */
		public static final String FAULTSHEET_INFO = "Mobile_initFaultsheet.action";
		public static final int MSG_FAULTSHEET_INFO = 9;
		/**
		 * 故障类别
		 * */
		public static final String FAULTTYPE_INFO = "Mobile_initFaulttype.action";
		public static final int MSG_FAULTTYPE_INFO = 10;
		/**
		 * 修复设备列表
		 * */
		public static final String EQUIPFIXLIST_INFO = "Mobile_initEquipfixlist.action";
		public static final int MSG_EQUIPFIXLIST_INFO = 11;
		/**
		 * 开始初始化
		 * */
		public static final int MSG_START_SYNC = 32;
		/**
		 * 结束初始化
		 * */
		public static final int MSG_END_SYNC = 33;
	}
	
	/**
	 * 上传消息
	 * */
	public class Upload{
		/**
		 * 经常性检查数据上传
		 * */
		public static final String BRIDGEUSUALEXAM_INFO = "json/Mobile_upLoadBridgeUsualExam.action";
		public static final int MSG_BRIDGEUSUALEXAM_INFO = 14;
		
		/**
		 * 经常性检查数据上传后获取最新数据
		 * */
		public static final String BRIDGEUSUALEXAM_DOWNLOAD = "json/Mobile_downLoadBridgeUsualExam.action";
		public static final int MSG_BRIDGEUSUALEXAM_DOWNLOAD = 15;
		
		/**
		 * 维护登记
		 * */
		public static final String MAINTAIN_INFO = "Mobile_uploadMaintain.action";
		public static final int MSG_MAINTAIN_INFO = 16;
		
		/**
		 * 维护登记设备列表
		 * */
		public static final String MAINTAINEQUIPLIST_INFO = "Mobile_uploadMaintainEquipList.action";
		public static final int MSG_MAINTAINEQUIPLIST_INFO = 17;
		
		/**
		 * 故障单
		 * */
		public static final String FAULTSHEET_INFO = "Mobile_uploadFaultsheet.action";
		public static final int MSG_FAULTSHEET_GZ_INFO = 18;
		public static final int MSG_FAULTSHEET_WH_INFO = 23;
		
		/**
		 * 修复设备列表
		 * */
		public static final String EQUIPFIXLIST_INFO = "Mobile_uploadEquipfixlist.action";
		public static final int MSG_EQUIPFIXLIST_INFO = 19;
		
		
		public static final int MSG_START_UPLOAD = 20;
		public static final int MSG_END_UPLOAD = 21;
		
		public static final int MSG_UPLOAD_NUM = 22;
	}
	
	public class App{
		/**
		 * 维护计划列表
		 * */
		public static final int MSG_MAINTAINPLAN_LIST = 14;
		
		/**
		 * 设备列表
		 */
		public static final int MSG_EQUIP_LIST = 15;
		/**
		 * 我的信息
		 * */
		public static final int MSG_MYINFO = 16;
		/**
		 * 设备详情
		 */
		public static final int MSG_EQUIP_DETAIL = 17;
		/**
		 * 维护登记列表
		 * */
		public static final int MSG_MAINTAIN_LIST = 18;
		/**
		 * 维护登记设备列表
		 * */
		public static final int MSG_MAINTAINEQUIP_LIST = 19;
		/**
		 * 维护登记增加
		 * */
		public static final int MSG_MAINTAIN_ADD = 20;
		/**
		 * 维护登记修改
		 * */
		public static final int MSG_MAINTAIN_UPDATE = 21;
		/**
		 * 维护登记设备修改
		 * */
		public static final int MSG_MAINTAINEQUIP_UPDATE = 22;
		/**
		 * 故障单 维护列表
		 * */
		public static final int MSG_WH_FAULTSHEET_LIST = 23;
		/**
		 * 故障单 故障列表
		 * */
		public static final int MSG_GZ_FAULTSHEET_LIST = 24;
		
		/**
		 * 故障单 故障 详情
		 */
		public static final int MSG_GZ_FAULTSHEET_DETAIL = 25;
		
		
		/**
		 * 故障待修 设备
		 */
		public static final int MSG_EQUIP_GZDXLIST = 26;
		/**
		 * 维修登记(工作单)
		 * */
		public static final int MSG_WXDJ_MAINTAIN = 27;
		/**
		 * 故障报修(工作单)
		 * */
		public static final int MSG_GZBX_MAINTAIN = 28;
		/**
		 * 维修单列表  维修登记  提交
		 */
		public static final int MSG_GZ_FAULTSHEET_COMMIT = 29;
		/**
		 * 维护登记 增加(工作单)
		 * */
		public static final int MSG_WXDJ_ADD_MAINTAIN = 30;
		/**
		 * 故障报修 增加（工作单）
		 * */
		public static final int MSG_GZBX_ADD_MAINTAIN = 31;
		/**
		 *  详情
		 */
		public static final int MSG_GZGL_FAULTSHEET_DETAIL = 32;
		/**
		 * 故障单 故障待修 设备
		 */
		public static final int MSG_GZ_EQUIP_GZDXLIST = 33;
		/**
		 * 维修单列表 维修登记
		 */
		public static final int MSG_WXD_ADDWXDJ = 34;
		/**
		 * 维修单列表 维修登记 待修设备
		 */
		public static final int MSG_WX_EQUIP_GZDXLIST = 35;
		/**
		 * 故障单列表 故障报修
		 */
		public static final int MSG_GZ_ADDGZBX = 36;
		/**
		 * 故障单列表 故障报修 待修设备
		 */
		public static final int MSG_GZ_EQUIP_GZBXLIST = 37;
		
		/**
		 * 打开dialog
		 * */
		public static final int MSG_OPEN_DIALOG = 38;
		/**
		 * 关闭dialog
		 * */
		public static final int MSG_CLOSE_DIALOG = 39;
		/**
		 * 修改 我的信息
		 */
		public static final int MSG_MYINFO_UPDATE = 40;
		/**
		 * 修改密码
		 */
		public static final int MSG_MYINFO_CHANGEMM = 41;
	}
	
	/**
	 * 上报接口类
	 * */
	public class Report{
		public static final String FAULTSHEET = "Mobile_reportFaultsheet.action";
	}
	
	
	
}
