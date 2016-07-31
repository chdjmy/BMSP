package com.example.bean;
import com.example.common.annotation.Column;
import com.example.common.annotation.Id;
import com.example.common.annotation.Table;
/**
 * 用户信息
 * @author JMY
 * */
@Table(name="USERS")
public class UsersBean {
	@Id
	@Column(name="ID", lenght = 0, type = "BIGINT")
	private String id;        // id

	@Column(name="ACCOUNT", lenght = 32, type = "VARCHAR")
	private String account;   // 用户名
	
	@Column(name="PASSWORD", lenght = 32, type = "VARCHAR")
	private String password;  // 密码
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

