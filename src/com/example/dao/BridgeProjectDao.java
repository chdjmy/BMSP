package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bean.BridgeBean;
import com.example.bean.BridgeProjectBean;
import com.example.common.dao.TemplateDao;

public class BridgeProjectDao extends TemplateDao<BridgeProjectBean>{
	private Context context;
	public BridgeProjectDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}
	
	//分页查找
	public List<BridgeProjectBean> findByPage(int page){
		List<BridgeProjectBean> list = new ArrayList<BridgeProjectBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "project_id>? AND project_id<=? AND p.bridge_code=b.bridge_code", new String[]{String.valueOf(page),String.valueOf(page+30)}, null, null, null);
			while (cursor.moveToNext()) {
				BridgeProjectBean bean = new BridgeProjectBean();
				bean.setProjectId(cursor.getInt(0));
				bean.setBridgeId(cursor.getString(1));
				bean.setBridgeCode(cursor.getString(2));
				bean.setBridgeName(cursor.getString(3));
				bean.setProjectDate(cursor.getString(4));
				bean.setBudgetCost(cursor.getDouble(5));
				list.add(bean);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}	
	
	//按桥梁Code查找
	public List<BridgeProjectBean> findByCode(String code){
		List<BridgeProjectBean> list = new ArrayList<BridgeProjectBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "p.bridge_code=b.bridge_code AND b.bridge_code=?", new String[]{code}, null, null, null);
			while (cursor.moveToNext()) {
				BridgeProjectBean bean = new BridgeProjectBean();
				bean.setProjectId(cursor.getInt(0));
				bean.setBridgeId(cursor.getString(1));
				bean.setBridgeCode(cursor.getString(2));
				bean.setBridgeName(cursor.getString(3));
				bean.setProjectDate(cursor.getString(4));
				bean.setBudgetCost(cursor.getDouble(5));
				list.add(bean);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}	
		
	//按照工程ID获得bean	
	public BridgeProjectBean queryById(int id){
		BridgeProjectBean bean =this.get(id);
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("bridge",new String[]{"bridge_name"}, "bridge_code=?", new String[]{bean.getBridgeCode()}, null, null, null);
			while (cursor.moveToNext()) {
				bean.setBridgeName(cursor.getString(0));
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		
		return bean;
	}	
	
	//按桥梁名字查找
	public List<BridgeProjectBean> findByName(String name){
		List<BridgeProjectBean> list = new ArrayList<BridgeProjectBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "p.bridge_code=b.bridge_code AND b.bridge_name LIKE '%"+name+"%'", null, null, null, null);
			while (cursor.moveToNext()) {
				BridgeProjectBean bean = new BridgeProjectBean();
				bean.setProjectId(cursor.getInt(0));
				bean.setBridgeId(cursor.getString(1));
				bean.setBridgeCode(cursor.getString(2));
				bean.setBridgeName(cursor.getString(3));
				bean.setProjectDate(cursor.getString(4));
				bean.setBudgetCost(cursor.getDouble(5));
				list.add(bean);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}
	
	//按桥梁条码查找
	public List<BridgeProjectBean> findByBar(String bar){
		List<BridgeProjectBean> list = new ArrayList<BridgeProjectBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("project p, bridge b",new String[]{"p.project_id,b.bridge_id","b.bridge_code","b.bridge_name","p.project_date","p.budget_cost"}, "p.bridge_code=b.bridge_code AND b.bar_code = ?", new String[]{bar}, null, null, null);
			while (cursor.moveToNext()) {
				BridgeProjectBean bean = new BridgeProjectBean();
				bean.setProjectId(cursor.getInt(0));
				bean.setBridgeId(cursor.getString(1));
				bean.setBridgeCode(cursor.getString(2));
				bean.setBridgeName(cursor.getString(3));
				bean.setProjectDate(cursor.getString(4));
				bean.setBudgetCost(cursor.getDouble(5));
				list.add(bean);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}	
		
}
