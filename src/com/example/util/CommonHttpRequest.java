package com.example.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.bean.SettingBean;
import com.example.http.AsyncHttpRequest;
import com.example.http.RequestParams;
import com.example.http.RequestResultCallback;
import com.example.http.ThreadPool;

/**
 * http请求
 * */
public class CommonHttpRequest {

	/**
	 * @param context
	 *            上下文
	 * @param settingBean
	 *            设置bean
	 * @param handler
	 * @param method
	 * @param json
	 * */
	public void request(final Context context, final SettingBean settingBean,
			final Handler handler, final int methodId, final String json) {
		System.out.println("---------request------");
		System.out.println("methodId:" + methodId);
		System.out.println("json:" + json);
		AsyncHttpRequest currentTime = new AsyncHttpRequest(Uri.getUri(
				settingBean.getIp(), settingBean.getPort(),
				settingBean.getWeb_app(), Uri.Init.GET_CURRENTTIME), null,
				new RequestResultCallback() {
					@Override
					public void onSuccess(Object paramObject) {
						MD5 md5 = new MD5();
						List<RequestParams> params = new ArrayList<RequestParams>();
						// long currentTime = System.currentTimeMillis();
						params.add(new RequestParams("time", String
								.valueOf(paramObject)));
						params.add(new RequestParams("v", md5
								.getMD5ofStr(Uri.KEY + " "
										+ String.valueOf(paramObject))));
						if (StringUtils.isNotEmpty(json)) {
							params.add(new RequestParams("json", json));
						}
						// url
						String url = getUrl(settingBean, methodId);
						AsyncHttpRequest request = new AsyncHttpRequest(url,
								params, new RequestResultCallback() {
									@Override
									public void onSuccess(Object paramObject) {
										if (null != handler) {
											Message msg = handler
													.obtainMessage();
											Bundle data = new Bundle();
											data.putBoolean(Uri.KEY_FLAG, true);
											data.putString(Uri.KEY_MSG,
													paramObject.toString());
											msg.setData(data);
											msg.what = methodId;

											handler.sendMessage(msg);
										}
									}

									@Override
									public void onFail(String paramException) {
										if (null != handler) {
											Message msg = handler
													.obtainMessage();
											Bundle data = new Bundle();
											data.putBoolean(Uri.KEY_FLAG, false);
											data.putString(Uri.KEY_MSG,
													paramException);
											msg.setData(data);
											msg.what = methodId;
											handler.sendMessage(msg);
										}
										// Toast.makeText(context,paramException,
										// Toast.LENGTH_LONG).show();
									}

								});
						ThreadPool.getSingle().execute(request);
					}

					@Override
					public void onFail(String paramException) {
						if (null != handler) {
							Message msg = handler.obtainMessage();
							Bundle data = new Bundle();
							data.putBoolean(Uri.KEY_FLAG, false);
							data.putString(Uri.KEY_MSG, paramException);
							msg.setData(data);
							msg.what = methodId;
							handler.sendMessage(msg);
						}
					}
				});
		ThreadPool.getSingle().execute(currentTime);

	}

	private String getUrl(SettingBean settingBean,int methodId){
		String ret = "";
		switch (methodId) {
			case Uri.Init.MSG_USUALEXAM_INFO:
				ret = Uri.Init.USUALEXAM_INFO;
				break;
			case Uri.Init.MSG_USUALEXAM_INFO_PAGE:
				ret = Uri.Init.USUALEXAM_INFO_PAGE;
				break;
			case Uri.Init.MSG_BRIDGE_INFO:
				ret = Uri.Init.BRIDGE_INFO;
				break;
			case Uri.Init.MSG_BRIDGE_INFO_PAGE:
				ret = Uri.Init.BRIDGE_INFO_PAGE;
				break;
			case Uri.Init.MSG_USER_INFO:
				ret = Uri.Init.USER_INFO;
				break;
					
				
			case Uri.Init.MSG_EQUIPFIXLIST_INFO:
				ret = Uri.Init.EQUIPFIXLIST_INFO;
				break;
			case Uri.Init.MSG_EQUIPMAINTAINPLAN_INFO:
				ret = Uri.Init.EQUIPMAINTAINPLAN_INFO;
				break;
			case Uri.Init.MSG_FAULTSHEET_INFO:
				ret = Uri.Init.FAULTSHEET_INFO;
				break;
			case Uri.Init.MSG_FAULTTYPE_INFO:
				ret = Uri.Init.FAULTTYPE_INFO;
				break;
			case Uri.Init.MSG_MAINTAIN_INFO:
				ret = Uri.Init.MAINTAIN_INFO;
				break;
			case Uri.Init.MSG_MAINTAINEQUIPLIST_INFO:
				ret = Uri.Init.MAINTAINEQUIPLIST_INFO;
				break;
			case Uri.Upload.MSG_BRIDGEUSUALEXAM_INFO:
				ret = Uri.Upload.BRIDGEUSUALEXAM_INFO;
				break;
			case Uri.Upload.MSG_BRIDGEUSUALEXAM_DOWNLOAD:	
				ret = Uri.Upload.BRIDGEUSUALEXAM_DOWNLOAD;
				break;
				
			case Uri.Upload.MSG_MAINTAIN_INFO:
				ret = Uri.Upload.MAINTAIN_INFO;
				break;
			case Uri.Upload.MSG_FAULTSHEET_WH_INFO:
				ret = Uri.Upload.FAULTSHEET_INFO;
				break;
			case Uri.Upload.MSG_FAULTSHEET_GZ_INFO:
				ret = Uri.Upload.FAULTSHEET_INFO;
				break;
			default:
				ret = "";
				break;
		}
		
		ret = Uri.getUri(settingBean.getIp(), settingBean.getPort(), settingBean.getWeb_app(),ret);
		return ret;
	}

	public static CommonHttpRequest getSingle() {
		if (null == request) {
			request = new CommonHttpRequest();
		}
		return request;
	}

	private CommonHttpRequest() {

	}

	private static CommonHttpRequest request;
}
