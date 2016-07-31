package com.example.http;

/**
 * 请求结果回调函数接口
 * @author minghui.wang
 * */
public interface RequestResultCallback {
	/**
	 * 成功回调函数
	 * @param paramObject 返回的结果
	 * */
	public abstract void onSuccess(Object paramObject);
	/**
	 * 失败回调函数
	 * @param paramException 返回异常信息
	 * */
	public abstract void onFail(String paramException);
}
