package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bean.CulvertUsualExamBean;
import com.example.bean.UsualExamBean;
import com.example.common.dao.TemplateDao;

public class CulvertUsualExamDao extends TemplateDao<CulvertUsualExamBean> {

	private Context context;

	public CulvertUsualExamDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}

	// 按页查询
	public List<CulvertUsualExamBean> findByPage(int page) {
		List<CulvertUsualExamBean> list = new ArrayList<CulvertUsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query(
					"cusual_exam",
					new String[] { "cusual_exam_id", "culvert_code",
							"culvert_name", "maintain_name", "noter",
							"check_date" },
					"cusual_exam_id>? AND cusual_exam_id<=?",
					new String[] { String.valueOf(page),
							String.valueOf(page + 30) }, null, null, null);
			while (cursor.moveToNext()) {
				CulvertUsualExamBean bean = new CulvertUsualExamBean();
				bean.setCulvertUsualExamId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setMaintainName(cursor.getString(3));
				bean.setNoter(cursor.getString(4));
				bean.setCheckDate(cursor.getString(5));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}
	
	//根据ID查询
	public CulvertUsualExamBean queryById(String id){
		CulvertUsualExamBean bean =this.get(id);
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("culvert",new String[]{"culvert_name,line_number,line_name,center_stake,MM_name,culvert_type"}, "culvert_code=?", new String[]{bean.getCulvertCode()}, null, null, null);
			while (cursor.moveToNext()) {
				bean.setCulvertName(cursor.getString(0));
				bean.setLineNumber(cursor.getString(1));
				bean.setLineName(cursor.getString(2));
				bean.setCenterStake(cursor.getDouble(3));
				bean.setMmName(cursor.getString(4));
				bean.setCulvertType(cursor.getString(5));
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

	// 按条码查找
	public List<CulvertUsualExamBean> findByBar(String str) {
		List<CulvertUsualExamBean> list = new ArrayList<CulvertUsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query("usual_exam", new String[] {
					"usual_exam_id", "bridge_code", "bridge_name", "principal",
					"noter", "check_date" },
					"usual_exam_id>? AND usual_exam_id<=?",
					new String[] { str }, null, null, null);
			while (cursor.moveToNext()) {
				CulvertUsualExamBean bean = new CulvertUsualExamBean();
				bean.setCulvertUsualExamId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setMaintainName(cursor.getString(3));
				bean.setNoter(cursor.getString(4));
				bean.setCheckDate(cursor.getString(5));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}

	// 按涵洞名查找
	public List<CulvertUsualExamBean> findByName(String str) {
		List<CulvertUsualExamBean> list = new ArrayList<CulvertUsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query(
					"cusual_exam",
					new String[] { "cusual_exam_id", "culvert_code",
							"culvert_name", "maintain_name", "noter",
							"check_date" },
							"culvert_name LIKE '%" + str + "%'",
					null, null, null, null);
			while (cursor.moveToNext()) {
				CulvertUsualExamBean bean = new CulvertUsualExamBean();
				bean.setCulvertUsualExamId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setMaintainName(cursor.getString(3));
				bean.setNoter(cursor.getString(4));
				bean.setCheckDate(cursor.getString(5));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}
	
	//按桥梁Code查询
	public List<CulvertUsualExamBean> findByCulvertCode(String str) {
		List<CulvertUsualExamBean> list = new ArrayList<CulvertUsualExamBean>();
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			Cursor cursor = db.query(
					"cusual_exam u,culvert c",
					new String[] { "u.cusual_exam_id", "u.culvert_code",
							"c.culvert_name", "u.maintain_name", "u.noter",
							"u.check_date" },
							"u.culvert_code = c.culvert_code AND u.culvert_code=?",
					new String[]{str}, null, null, null);
			while (cursor.moveToNext()) {
				CulvertUsualExamBean bean = new CulvertUsualExamBean();
				bean.setCulvertUsualExamId(cursor.getString(0));
				bean.setCulvertCode(cursor.getString(1));
				bean.setCulvertName(cursor.getString(2));
				bean.setMaintainName(cursor.getString(3));
				bean.setNoter(cursor.getString(4));
				bean.setCheckDate(cursor.getString(5));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return list;
	}		

	//获得最大的ID
	public int getMaxId(){
		int idMax = 0;
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("cusual_exam",new String[]{"MAX(cusual_exam_id)"}, null, null, null, null, null);
			while (cursor.moveToNext()) {
				idMax = cursor.getInt(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return idMax;
	}	
}
