package com.example.bms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.bean.CulvertUsualExamBean;
import com.example.bean.UsualExamBean;
import com.example.dao.CulvertUsualExamDao;
import com.example.dao.UsualExamDao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class CulvertUsualExamDetailActivity extends BaseActivity implements
View.OnTouchListener{

	private EditText text_culvert_code;
	private EditText text_culvert_name;
	private EditText text_line_number;
	private EditText text_line_name;
	private EditText text_center_stake;

	private EditText text_MM_name;
	private EditText text_culvert_type;
	private EditText text_maintain_name;

	private EditText text_inlet_type_disease;
	private EditText text_inlet_region;
	private EditText text_inlet_advise;
	private EditText text_outlet_type_disease;
	private EditText text_outlet_region;
	private EditText text_outlet_advise;
	private EditText text_body_type;
	private EditText text_body_region;
	private EditText text_body_advise;
	private EditText text_top_type;
	private EditText text_top_region;
	private EditText text_top_advise;
	private EditText text_bottom_type;
	private EditText text_bottom_region;
	private EditText text_bottom_advise;
	private EditText text_fill_type;
	private EditText text_fill_region;
	private EditText text_fill_advise;
	private EditText text_lighting_type;
	private EditText text_lighting_region;
	private EditText text_lighting_advise;
	private EditText text_else_type;
	private EditText text_else_region;
	private EditText text_else_advise;
	private EditText text_remark;

	private EditText text_manager_name;
	private EditText text_noter;
	private EditText text_check_date;
	
	private int flag;
	private String usualExamId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		flag = getIntent().getIntExtra("flag", 0);//0表示详情，1表示增加，2表示修改
		usualExamId = getIntent().getStringExtra("usualExamId");
		CulvertUsualExamDao dao = new CulvertUsualExamDao(this);
		
		switch(flag){
		case 0:
			setContentView(R.layout.activity_culvert_usual_exam_detail);
			initEditText();
			CulvertUsualExamBean bean = dao.queryById(usualExamId);
			setText(bean);
			break;
		case 1:
			setContentView(R.layout.activity_culvert_usual_exam_add);
			initEditText();
			CulvertUsualExamBean beanAdd = dao.queryById(usualExamId);
			setAddText(beanAdd);
			break;
		case 2:
			
			break;
		default:
			break;
		}
		
	}
	
	

	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			View view = View.inflate(this, R.layout.date_time_dialog, null);
			final DatePicker datePicker = (DatePicker) view
					.findViewById(R.id.date_picker);
			builder.setView(view);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH), null);

			if (v.getId() == R.id.text_check_date) {
				final int inType = text_check_date.getInputType();
				text_check_date.setInputType(InputType.TYPE_NULL);
				text_check_date.onTouchEvent(event);
				text_check_date.setInputType(inType);
				text_check_date
						.setSelection(text_check_date.getText().length());

				builder.setTitle("请选择检查日期");
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d-%02d-%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
								text_check_date.setText(sb);
								dialog.cancel();
							}
						});
			}
			Dialog dialog = builder.create();
			dialog.show();
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		switch(flag){
		case 0:
			break;
		case 1:
			getMenuInflater().inflate(R.menu.usual_add, menu);//增加
			break;
		case 2:
			getMenuInflater().inflate(R.menu.usual_change, menu);//修改
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		
		if (id == R.id.usual_menu_submit) {
			CulvertUsualExamBean bean = setBean();
			CulvertUsualExamDao dao = new CulvertUsualExamDao(this);
			switch(flag){
			case 0:
				break;
			case 1:
				bean.setCulvertUsualExamId(String.valueOf(dao.getMaxId()+1));
				dao.insert(bean);
				displayToast("添加成功");
				break;
			case 2:
				bean.setCulvertUsualExamId(usualExamId);
				dao.update(bean);
				displayToast("修改成功");
				break;
			default:
				break;
			}
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void setAddText(CulvertUsualExamBean bean){
		text_culvert_code.setText("["+bean.getCulvertCode()+"]");
		text_culvert_name.setText(bean.getCulvertName());
		text_line_number.setText(bean.getLineNumber());
		text_line_name.setText(bean.getLineName());
		text_center_stake.setText(String.valueOf(bean.getCenterStake()));

		text_MM_name.setText(bean.getMmName());
		text_culvert_type.setText(bean.getCulvertType());
		text_maintain_name.setText(bean.getMaintainName());

		text_inlet_type_disease.setText(bean.getInletTypeDisease());
		text_inlet_region.setText(bean.getInletRegion());
		text_inlet_advise.setText(bean.getInletAdvise());
		text_outlet_type_disease.setText(bean.getOutletTypeDisease());
		text_outlet_region.setText(bean.getOutletRegion());
		text_outlet_advise.setText(bean.getOutletAdvise());
		text_body_type.setText(bean.getBodyType());
		text_body_region.setText(bean.getBodyRegion());
		text_body_advise.setText(bean.getBodyAdvise());
		text_top_type.setText(bean.getTopType());
		text_top_region.setText(bean.getTopRegion());
		text_top_advise.setText(bean.getTopAdvise());
		text_bottom_type.setText(bean.getBottomType());
		text_bottom_region.setText(bean.getBottomRegion());
		text_bottom_advise.setText(bean.getBottomAdvise());
		text_fill_type.setText(bean.getFillType());
		text_fill_region.setText(bean.getFillRegion());
		text_fill_advise.setText(bean.getFillAdvise());
		text_lighting_type.setText(bean.getLightingType());
		text_lighting_region.setText(bean.getLightingAdvise());
		text_lighting_advise.setText(bean.getLightingType());
		text_else_type.setText(bean.getElseType());
		text_else_region.setText(bean.getElseAdvise());
		text_else_advise.setText(bean.getElseAdvise());
		text_remark.setText(bean.getRemark());

		text_manager_name.setText(bean.getMaintainName());
		text_noter.setText(bean.getNoter());
		Date today = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		text_check_date.setText(format.format(today));
		text_check_date.setOnTouchListener(this);
	}
	
	public void setText(CulvertUsualExamBean bean){
		text_culvert_code.setText("["+bean.getCulvertCode()+"]");
		text_culvert_name.setText(bean.getCulvertName());
		text_line_number.setText(bean.getLineNumber());
		text_line_name.setText(bean.getLineName());
		text_center_stake.setText(String.valueOf(bean.getCenterStake()));

		text_MM_name.setText(bean.getMmName());
		text_culvert_type.setText(bean.getCulvertType());
		text_maintain_name.setText(bean.getMaintainName());

		text_inlet_type_disease.setText(bean.getInletTypeDisease());
		text_inlet_region.setText(bean.getInletRegion());
		text_inlet_advise.setText(bean.getInletAdvise());
		text_outlet_type_disease.setText(bean.getOutletTypeDisease());
		text_outlet_region.setText(bean.getOutletRegion());
		text_outlet_advise.setText(bean.getOutletAdvise());
		text_body_type.setText(bean.getBodyType());
		text_body_region.setText(bean.getBodyRegion());
		text_body_advise.setText(bean.getBodyAdvise());
		text_top_type.setText(bean.getTopType());
		text_top_region.setText(bean.getTopRegion());
		text_top_advise.setText(bean.getTopAdvise());
		text_bottom_type.setText(bean.getBottomType());
		text_bottom_region.setText(bean.getBottomRegion());
		text_bottom_advise.setText(bean.getBottomAdvise());
		text_fill_type.setText(bean.getFillType());
		text_fill_region.setText(bean.getFillRegion());
		text_fill_advise.setText(bean.getFillAdvise());
		text_lighting_type.setText(bean.getLightingType());
		text_lighting_region.setText(bean.getLightingAdvise());
		text_lighting_advise.setText(bean.getLightingType());
		text_else_type.setText(bean.getElseType());
		text_else_region.setText(bean.getElseAdvise());
		text_else_advise.setText(bean.getElseAdvise());
		text_remark.setText(bean.getRemark());

		text_manager_name.setText(bean.getMaintainName());
		text_noter.setText(bean.getNoter());
		text_check_date.setText(bean.getCheckDate());
	}
	
	public CulvertUsualExamBean setBean(){
		CulvertUsualExamDao dao = new CulvertUsualExamDao(this);
		CulvertUsualExamBean bean = dao.get(usualExamId);
		bean.setCulvertCode(text_culvert_code.getText().toString());
		
		bean.setCulvertName(text_culvert_name.getText().toString());
		bean.setLineNumber(text_line_number.getText().toString());
		bean.setLineName(text_line_name.getText().toString());
		bean.setCenterStake(Double.parseDouble(text_center_stake.getText().toString()));

		bean.setMmName(text_MM_name.getText().toString());
		bean.setCulvertType(text_culvert_type.getText().toString());
		bean.setMaintainName(text_maintain_name.getText().toString());

		bean.setInletTypeDisease(text_inlet_type_disease.getText().toString());
		bean.setInletRegion(text_inlet_region.getText().toString());
		bean.setInletAdvise(text_inlet_advise.getText().toString());
		bean.setOutletTypeDisease(text_outlet_type_disease.getText().toString());
		bean.setOutletRegion(text_outlet_region.getText().toString());
		bean.setOutletAdvise(text_outlet_advise.getText().toString());
		bean.setBodyType(text_body_type.getText().toString());
		bean.setBodyRegion(text_body_region.getText().toString());
		bean.setBodyAdvise(text_body_advise.getText().toString());
		bean.setTopType(text_top_type.getText().toString());
		bean.setTopRegion(text_top_region.getText().toString());
		bean.setTopAdvise(text_top_advise.getText().toString());
		bean.setBottomType(text_bottom_type.getText().toString());
		bean.setBottomRegion(text_bottom_region.getText().toString());
		bean.setBottomAdvise(text_bottom_advise.getText().toString());
		bean.setFillType(text_fill_type.getText().toString());
		bean.setFillRegion(text_fill_region.getText().toString());
		bean.setFillAdvise(text_fill_advise.getText().toString());
		bean.setLightingType(text_lighting_type.getText().toString());
		bean.setLightingAdvise(text_lighting_region.getText().toString());
		bean.setLightingType(text_lighting_advise.getText().toString());
		bean.setElseType(text_else_type.getText().toString());
		bean.setElseAdvise(text_else_region.getText().toString());
		bean.setElseAdvise(text_else_advise.getText().toString());
		bean.setRemark(text_remark.getText().toString());

		bean.setMaintainName(text_manager_name.getText().toString());
		bean.setNoter(text_noter.getText().toString());
		bean.setCheckDate(text_check_date.getText().toString());
		bean.setIsUpload("0");
		return bean;
	}

	public void initEditText() {
		text_culvert_code = (EditText) findViewById(R.id.text_culvert_code);
		text_culvert_name = (EditText) findViewById(R.id.text_culvert_name);
		text_line_number = (EditText) findViewById(R.id.text_line_number);
		text_line_name = (EditText) findViewById(R.id.text_line_name);
		text_center_stake = (EditText) findViewById(R.id.text_center_stake);

		text_MM_name = (EditText) findViewById(R.id.text_MM_name);
		text_culvert_type = (EditText) findViewById(R.id.text_culvert_type);
		text_maintain_name = (EditText) findViewById(R.id.text_maintain_name);

		text_inlet_type_disease = (EditText) findViewById(R.id.text_inlet_type_disease);
		text_inlet_region = (EditText) findViewById(R.id.text_inlet_region);
		text_inlet_advise = (EditText) findViewById(R.id.text_inlet_advise);
		text_outlet_type_disease = (EditText) findViewById(R.id.text_outlet_type_disease);
		text_outlet_region = (EditText) findViewById(R.id.text_outlet_region);
		text_outlet_advise = (EditText) findViewById(R.id.text_outlet_advise);
		text_body_type = (EditText) findViewById(R.id.text_body_type);
		text_body_region = (EditText) findViewById(R.id.text_body_region);
		text_body_advise = (EditText) findViewById(R.id.text_body_advise);
		text_top_type = (EditText) findViewById(R.id.text_top_type);
		text_top_region = (EditText) findViewById(R.id.text_top_region);
		text_top_advise = (EditText) findViewById(R.id.text_top_advise);
		text_bottom_type = (EditText) findViewById(R.id.text_bottom_type);
		text_bottom_region = (EditText) findViewById(R.id.text_bottom_region);
		text_bottom_advise = (EditText) findViewById(R.id.text_bottom_advise);
		text_fill_type = (EditText) findViewById(R.id.text_fill_type);
		text_fill_region = (EditText) findViewById(R.id.text_fill_region);
		text_fill_advise = (EditText) findViewById(R.id.text_fill_advise);
		text_lighting_type = (EditText) findViewById(R.id.text_lighting_type);
		text_lighting_region = (EditText) findViewById(R.id.text_lighting_region);
		text_lighting_advise = (EditText) findViewById(R.id.text_lighting_advise);
		text_else_type = (EditText) findViewById(R.id.text_else_type);
		text_else_region = (EditText) findViewById(R.id.text_else_region);
		text_else_advise = (EditText) findViewById(R.id.text_else_advise);
		text_remark = (EditText) findViewById(R.id.text_remark);

		text_manager_name = (EditText) findViewById(R.id.text_manager_name);
		text_noter = (EditText) findViewById(R.id.text_noter);
		text_check_date = (EditText) findViewById(R.id.text_check_date);
	}
	
}
