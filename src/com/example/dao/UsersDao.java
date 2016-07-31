package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.UsersBean;
import com.example.common.dao.TemplateDao;
import com.example.util.CommonHttpRequest;
import com.example.util.Uri;
import com.example.bean.SettingBean;

public class UsersDao extends TemplateDao<UsersBean> {

	private Context context;
	public UsersDao( Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}

	
	
	public void init(SettingBean settingBean,final Handler handler){
		CommonHttpRequest.getSingle().request(context, settingBean, handler, Uri.Init.MSG_USER_INFO, null);
	}
	public boolean add(JSONArray array){
		if(null != array){

			SQLiteDatabase db = null;
			
			try {
				
				db = this.getWritableDatabase();
				
				db.beginTransaction();
				
				db.delete("USERS", null, null);
				
				for(int i = 0 ; i < array.size() ; i++ ){
					JSONObject obj = array.getJSONObject(i);
					String id = obj.getString("id");
					String pos_id = obj.getString("pos_id");
					String coo_id = obj.getString("coo_id");
					String bra_id = obj.getString("bra_id");
					String account = obj.getString("account");
					String password = obj.getString("password");
					String state = obj.getString("state");
					String name = obj.getString("name");
					String sex = obj.getString("sex");
					String birthday = obj.getString("birthday");
					String workid = obj.getString("workid");
					String idnumber = obj.getString("idnumber");
					String phone = obj.getString("phone");
					String address = obj.getString("address");
					String email = obj.getString("email");
					String qq = obj.getString("qq");
					String bak = obj.getString("bak");
					
					ContentValues values = new ContentValues();
					values.put("id", id);
					values.put("pos_id", pos_id);
					values.put("coo_id", coo_id);
					values.put("bra_id", bra_id);
					values.put("account", account);
					values.put("password", password);
					values.put("state", state);
					values.put("name", name);
					values.put("sex", sex);
					values.put("birthday", birthday);
					values.put("workid", workid);
					values.put("idnumber", idnumber);
					values.put("phone", phone);
					values.put("address", address);
					values.put("email", email);
					values.put("qq", qq);
					values.put("bak", bak);
					
					db.insert("USERS", null, values);
				}
				db.setTransactionSuccessful();
				
				db.endTransaction();
				
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(null != db){
					db.close();
				}
			}
		}
		
		return false;
	}
}
