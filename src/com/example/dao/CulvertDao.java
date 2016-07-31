package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bean.BridgeBean;
import com.example.bean.CulvertBean;
import com.example.common.dao.TemplateDao;

public class CulvertDao extends TemplateDao<CulvertBean> {

	private Context context;

	public CulvertDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}
	
	//分页查找
	public List<CulvertBean> findByPage(int page){
		List<CulvertBean> list = new ArrayList<CulvertBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("culvert",new String[]{"culvert_id","culvert_code","culvert_name","line_number","line_name","line_type","location_name"}, "culvert_id>? AND culvert_id<=?", new String[]{String.valueOf(page),String.valueOf(page+30)}, null, null, null);
			while (cursor.moveToNext()) {
				CulvertBean bean = new CulvertBean();
				bean.setCulvertId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setLineNumber(cursor.getString(3));
				bean.setLineName(cursor.getString(4));
				bean.setLineType(cursor.getString(5));
				bean.setLocationName(cursor.getString(6));
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
	
	//按条码查找
	public List<CulvertBean> findByBar(String str){
		List<CulvertBean> list = new ArrayList<CulvertBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("culvert",new String[]{"culvert_id","culvert_code","culvert_name","line_number","line_name","line_type","location_name"}, "culvert_id>? AND culvert_id<=?", null, null, null, null);
			while (cursor.moveToNext()) {
				CulvertBean bean = new CulvertBean();
				bean.setCulvertId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setLineNumber(cursor.getString(3));
				bean.setLineName(cursor.getString(4));
				bean.setLineType(cursor.getString(5));
				bean.setLocationName(cursor.getString(6));
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
	
	//按桥名查找
	public List<CulvertBean> findByName(String str){
		List<CulvertBean> list = new ArrayList<CulvertBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("culvert",new String[]{"culvert_id","culvert_code","culvert_name","line_number","line_name","line_type","location_name"}, "culvert_name LIKE '%"+str+"%'",null, null, null, null);
			while (cursor.moveToNext()) {
				CulvertBean bean = new CulvertBean();
				bean.setCulvertId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setLineNumber(cursor.getString(3));
				bean.setLineName(cursor.getString(4));
				bean.setLineType(cursor.getString(5));
				bean.setLocationName(cursor.getString(6));
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
