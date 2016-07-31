package com.example.bean;

import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;
import com.example.util.Uri;

/**
 * ��������
 * @author minghui.wang
 * */
@Table(name = "SETTINGS")
public class SettingBean {
	@Id
	@Column(name="IP", lenght = 30, type = "VARCHAR" )
	private String ip = "192.168.155.1";     // ip
	
	@Column(name="PORT", lenght = 10, type = "VARCHAR" )
	private String port = "8080";            // �˿�
	
	@Column(name="WEB_APP", lenght = 100, type = "VARCHAR" )
	private String web_app = Uri.WEB_APP;     // web ϵͳ��
	
	@Column(name="TIMEOUT", lenght = 0, type = "int" )
	private int timeout = 30;
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getWeb_app() {
		return web_app;
	}
	public void setWeb_app(String web_app) {
		this.web_app = web_app;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
}
