package com.example.bms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.bean.UsualExamBean;
import com.example.dao.UsualExamDao;

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

public class UsualExamAddActivity extends BaseActivity implements
		View.OnTouchListener {

	private EditText text_manager_uint;

	private EditText text_line_number;
	private EditText text_line_name;
	private EditText text_center_stake;

	private EditText text_bridge_code;
	private EditText text_bridge_name;
	private EditText text_mm_name;

	private EditText text_principal;
	private EditText text_noter;
	private EditText text_check_date;

	private EditText text_wall_advice;
	private EditText text_wall_type;
	private EditText text_wall_region;

	private EditText text_slope_advice;
	private EditText text_slope_type;
	private EditText text_slope_region;

	private EditText text_abutment_advice;
	private EditText text_abutment_type;
	private EditText text_abutment_region;

	private EditText text_pier_advice;
	private EditText text_pier_type;
	private EditText text_pier_region;

	private EditText text_foundation_advice;
	private EditText text_foundation_type;
	private EditText text_foundation_region;

	private EditText text_supports_advice;
	private EditText text_supports_type;
	private EditText text_supports_region;

	private EditText text_superstructure_advice;
	private EditText text_superstructure_type;
	private EditText text_superstructure_region;

	private EditText text_approach_advice;
	private EditText text_approach_type;
	private EditText text_approach_region;

	private EditText text_expansion_advice;
	private EditText text_expansion_type;
	private EditText text_expansion_region;

	private EditText text_deck_advice;
	private EditText text_deck_type;
	private EditText text_deck_region;

	private EditText text_sidewalk_advice;
	private EditText text_sidewalk_type;
	private EditText text_sidewalk_region;

	private EditText text_guard_advice;
	private EditText text_guard_type;
	private EditText text_guard_region;

	private EditText text_sign_advice;
	private EditText text_sign_type;
	private EditText text_sign_region;

	private EditText text_waterproof_advice;
	private EditText text_waterproof_type;
	private EditText text_waterproof_region;

	private EditText text_lighting_advice;
	private EditText text_lighting_type;
	private EditText text_lighting_region;

	private EditText text_clean_advice;
	private EditText text_clean_type;
	private EditText text_clean_region;

	private EditText text_regulating_advice;
	private EditText text_regulating_type;
	private EditText text_regulating_region;

	private EditText text_else_advice;
	private EditText text_else_type;
	private EditText text_else_region;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usual_exam_add);
		setText();
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

			if (v.getId() == R.id.date_check_date) {
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
		if(getIntent().getIntExtra("interType",0)==0){
			getMenuInflater().inflate(R.menu.usual_add, menu);//增加
		}else{
			getMenuInflater().inflate(R.menu.usual_change, menu);//修改
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
			// Intent intent = new Intent();
			// intent.setClass(UsualExamAddActivity.this,
			// NetSettingsActivity.class);
			// startActivity(intent);
			UsualExamBean bean = setBean();
			UsualExamDao dao = new UsualExamDao(this);
			if(getIntent().getIntExtra("interType",0)==0){
				bean.setUsualExamId(String.valueOf(dao.getMaxId()+1));
				dao.insert(bean);
				displayToast("添加成功,已添加到数据上传列表");
				this.finish();
			}else{
				bean.setUsualExamId(getIntent().getStringExtra("usualExamId"));
				dao.update(bean);
				displayToast("修改成功");
				this.finish();
			}
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setText(){
		String usualExamId = getIntent().getStringExtra("usualExamId");
		UsualExamDao dao = new UsualExamDao(this);
		UsualExamBean bean = dao.get(usualExamId);

		text_manager_uint = (EditText) findViewById(R.id.text_manager_uint);

		text_line_number = (EditText) findViewById(R.id.text_line_number);
		text_line_name = (EditText) findViewById(R.id.text_line_name);
		text_center_stake = (EditText) findViewById(R.id.text_center_stake);

		text_bridge_code = (EditText) findViewById(R.id.text_bridge_code);
		text_bridge_name = (EditText) findViewById(R.id.text_bridge_name);
		text_mm_name = (EditText) findViewById(R.id.text_mm_name);

		text_principal = (EditText) findViewById(R.id.text_principal);
		text_noter = (EditText) findViewById(R.id.text_noter);
		text_check_date = (EditText) findViewById(R.id.date_check_date);

		text_wall_advice = (EditText) findViewById(R.id.text_wall_advice);
		text_wall_type = (EditText) findViewById(R.id.text_wall_type);
		text_wall_region = (EditText) findViewById(R.id.text_wall_region);

		text_slope_advice = (EditText) findViewById(R.id.text_slope_advice);
		text_slope_type = (EditText) findViewById(R.id.text_slope_type);
		text_slope_region = (EditText) findViewById(R.id.text_slope_region);

		text_abutment_advice = (EditText) findViewById(R.id.text_abutment_advice);
		text_abutment_type = (EditText) findViewById(R.id.text_abutment_type);
		text_abutment_region = (EditText) findViewById(R.id.text_abutment_region);

		text_pier_advice = (EditText) findViewById(R.id.text_pier_advice);
		text_pier_type = (EditText) findViewById(R.id.text_pier_type);
		text_pier_region = (EditText) findViewById(R.id.text_pier_region);

		text_foundation_advice = (EditText) findViewById(R.id.text_foundation_advice);
		text_foundation_type = (EditText) findViewById(R.id.text_foundation_type);
		text_foundation_region = (EditText) findViewById(R.id.text_foundation_region);

		text_supports_advice = (EditText) findViewById(R.id.text_supports_advice);
		text_supports_type = (EditText) findViewById(R.id.text_supports_type);
		text_supports_region = (EditText) findViewById(R.id.text_supports_region);

		text_superstructure_advice = (EditText) findViewById(R.id.text_superstructure_advice);
		text_superstructure_type = (EditText) findViewById(R.id.text_superstructure_type);
		text_superstructure_region = (EditText) findViewById(R.id.text_superstructure_region);

		text_approach_advice = (EditText) findViewById(R.id.text_approach_advice);
		text_approach_type = (EditText) findViewById(R.id.text_approach_type);
		text_approach_region = (EditText) findViewById(R.id.text_approach_region);

		text_expansion_advice = (EditText) findViewById(R.id.text_expansion_advice);
		text_expansion_type = (EditText) findViewById(R.id.text_expansion_type);
		text_expansion_region = (EditText) findViewById(R.id.text_expansion_region);

		text_deck_advice = (EditText) findViewById(R.id.text_deck_advice);
		text_deck_type = (EditText) findViewById(R.id.text_deck_type);
		text_deck_region = (EditText) findViewById(R.id.text_deck_region);

		text_sidewalk_advice = (EditText) findViewById(R.id.text_sidewalk_advice);
		text_sidewalk_type = (EditText) findViewById(R.id.text_sidewalk_type);
		text_sidewalk_region = (EditText) findViewById(R.id.text_sidewalk_region);

		text_guard_advice = (EditText) findViewById(R.id.text_guard_advice);
		text_guard_type = (EditText) findViewById(R.id.text_guard_type);
		text_guard_region = (EditText) findViewById(R.id.text_guard_region);

		text_sign_advice = (EditText) findViewById(R.id.text_sign_advice);
		text_sign_type = (EditText) findViewById(R.id.text_sign_type);
		text_sign_region = (EditText) findViewById(R.id.text_sign_region);

		text_waterproof_advice = (EditText) findViewById(R.id.text_waterproof_advice);
		text_waterproof_type = (EditText) findViewById(R.id.text_waterproof_type);
		text_waterproof_region = (EditText) findViewById(R.id.text_waterproof_region);

		text_lighting_advice = (EditText) findViewById(R.id.text_lighting_advice);
		text_lighting_type = (EditText) findViewById(R.id.text_lighting_type);
		text_lighting_region = (EditText) findViewById(R.id.text_lighting_region);

		text_clean_advice = (EditText) findViewById(R.id.text_clean_advice);
		text_clean_type = (EditText) findViewById(R.id.text_clean_type);
		text_clean_region = (EditText) findViewById(R.id.text_clean_region);

		text_regulating_advice = (EditText) findViewById(R.id.text_regulating_advice);
		text_regulating_type = (EditText) findViewById(R.id.text_regulating_type);
		text_regulating_region = (EditText) findViewById(R.id.text_regulating_region);

		text_else_advice = (EditText) findViewById(R.id.text_else_advice);
		text_else_type = (EditText) findViewById(R.id.text_else_type);
		text_else_region = (EditText) findViewById(R.id.text_else_region);

		// 设置内容
		text_manager_uint.setText(bean.getManagerUnit());
		text_line_number.setText(bean.getLineNumber());
		text_line_name.setText(bean.getLineName());
		text_center_stake.setText(String.valueOf(bean.getCenterStake()));
		text_bridge_code.setText(bean.getBridgeCode());
		text_bridge_name.setText(bean.getBridgeName());
		text_mm_name.setText(bean.getMmName());
		text_principal.setText(bean.getPrincipal());
		text_noter.setText(bean.getNoter());
		
		if(getIntent().getIntExtra("interType",0)==0){
			Date today = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
			text_check_date.setText(format.format(today));
		}else{
			text_check_date.setText(bean.getCheckDate());
		}
		text_check_date.setOnTouchListener(this);//设置OnTouch监听
		
		text_wall_advice.setText(bean.getWallAdvise());
		text_wall_type.setText(bean.getWallType());
		text_wall_region.setText(bean.getWallRegion());
		text_slope_advice.setText(bean.getSlopeAdvise());
		text_slope_type.setText(bean.getSlopeType());
		text_slope_region.setText(bean.getSlopeRegion());
		text_abutment_advice.setText(bean.getAbutmentAdvise());
		text_abutment_type.setText(bean.getAbutmentType());
		text_abutment_region.setText(bean.getAbutmentRegion());
		text_pier_advice.setText(bean.getPierRegion());
		text_pier_type.setText(bean.getPierType());
		text_pier_region.setText(bean.getPierRegion());
		text_foundation_advice.setText(bean.getFoundationAdvise());
		text_foundation_type.setText(bean.getFoundationType());
		text_foundation_region.setText(bean.getFoundationRegion());
		text_supports_advice.setText(bean.getSupportsAdvise());
		text_supports_type.setText(bean.getSupportsType());
		text_supports_region.setText(bean.getSupportsRegion());
		text_superstructure_advice.setText(bean.getSuperstructureAdvise());
		text_superstructure_type.setText(bean.getSuperstructureType());
		text_superstructure_region.setText(bean.getSuperstructureRegion());
		text_approach_advice.setText(bean.getApproachAdvise());
		text_approach_type.setText(bean.getApproachType());
		text_approach_region.setText(bean.getApproachRegion());
		text_expansion_advice.setText(bean.getExpansionAdvise());
		text_expansion_type.setText(bean.getExpansionType());
		text_expansion_region.setText(bean.getExpansionRegion());
		text_deck_advice.setText(bean.getDeckAdvise());
		text_deck_type.setText(bean.getDeckType());
		text_deck_region.setText(bean.getDeckRegion());
		text_sidewalk_advice.setText(bean.getSidewalkAdvise());
		text_sidewalk_type.setText(bean.getSidewalkType());
		text_sidewalk_region.setText(bean.getSidewalkRegion());
		text_guard_advice.setText(bean.getGuardAdvise());
		text_guard_type.setText(bean.getGuardType());
		text_guard_region.setText(bean.getGuardRegion());
		text_sign_advice.setText(bean.getSignAdvise());
		text_sign_type.setText(bean.getSignType());
		text_sign_region.setText(bean.getSignRegion());
		text_waterproof_advice.setText(bean.getWaterproofAdvise());
		text_waterproof_type.setText(bean.getWaterproofType());
		text_waterproof_region.setText(bean.getWaterproofRegion());
		text_lighting_advice.setText(bean.getLightingAdvise());
		text_lighting_type.setText(bean.getLightingType());
		text_lighting_region.setText(bean.getLightingRegion());
		text_clean_advice.setText(bean.getCleanAdvise());
		text_clean_type.setText(bean.getCleanType());
		text_clean_region.setText(bean.getCleanRegion());
		text_regulating_advice.setText(bean.getRegulatingAdvise());
		text_regulating_type.setText(bean.getRegulatingType());
		text_regulating_region.setText(bean.getRegulatingRegion());
		text_else_advice.setText(bean.getElseAdvise());
		text_else_type.setText(bean.getElseType());
		text_else_region.setText(bean.getElseRegion());
	}

	private UsualExamBean setBean() {
		String usualExamId = getIntent().getStringExtra("usualExamId");
		UsualExamDao dao = new UsualExamDao(this);
		UsualExamBean bean = dao.get(usualExamId);
		// 获取内容
		bean.setManagerUnit(text_manager_uint.getText().toString());
		bean.setLineNumber(text_line_number.getText().toString());
		bean.setLineName(text_line_name.getText().toString());
		bean.setCenterStake(Double.parseDouble(text_center_stake.getText().toString()));
		bean.setBridgeCode(text_bridge_code.getText().toString());
		bean.setBridgeName(text_bridge_name.getText().toString());
		bean.setMmName(text_mm_name.getText().toString());
		bean.setPrincipal(text_principal.getText().toString());
		bean.setNoter(text_noter.getText().toString());
		bean.setCheckDate(text_check_date.getText().toString());
		bean.setWallAdvise(text_wall_advice.getText().toString());
		bean.setWallType(text_wall_type.getText().toString());
		bean.setWallRegion(text_wall_region.getText().toString());
		bean.setSlopeAdvise(text_slope_advice.getText().toString());
		bean.setSlopeType(text_slope_type.getText().toString());
		bean.setSlopeRegion(text_slope_region.getText().toString());
		bean.setAbutmentAdvise(text_abutment_advice.getText().toString());
		bean.setAbutmentType(text_abutment_type.getText().toString());
		bean.setAbutmentRegion(text_abutment_region.getText().toString());
		bean.setPierRegion(text_pier_advice.getText().toString());
		bean.setPierType(text_pier_type.getText().toString());
		bean.setPierRegion(text_pier_region.getText().toString());
		bean.setFoundationAdvise(text_foundation_advice.getText().toString());
		bean.setFoundationType(text_foundation_type.getText().toString());
		bean.setFoundationRegion(text_foundation_region.getText().toString());
		bean.setSupportsAdvise(text_supports_advice.getText().toString());
		bean.setSupportsType(text_supports_type.getText().toString());
		bean.setSupportsRegion(text_supports_region.getText().toString());
		bean.setSuperstructureAdvise(text_superstructure_advice.getText().toString());
		bean.setSuperstructureType(text_superstructure_type.getText().toString());
		bean.setSuperstructureRegion(text_superstructure_region.getText().toString());
		bean.setApproachAdvise(text_approach_advice.getText().toString());
		bean.setApproachType(text_approach_type.getText().toString());
		bean.setApproachRegion(text_approach_region.getText().toString());
		bean.setExpansionAdvise(text_expansion_advice.getText().toString());
		bean.setExpansionType(text_expansion_type.getText().toString());
		bean.setExpansionRegion(text_expansion_region.getText().toString());
		bean.setDeckAdvise(text_deck_advice.getText().toString());
		bean.setDeckType(text_deck_type.getText().toString());
		bean.setDeckRegion(text_deck_region.getText().toString());
		bean.setSidewalkAdvise(text_sidewalk_advice.getText().toString());
		bean.setSidewalkType(text_sidewalk_type.getText().toString());
		bean.setSidewalkRegion(text_sidewalk_region.getText().toString());
		bean.setGuardAdvise(text_guard_advice.getText().toString());
		bean.setGuardType(text_guard_type.getText().toString());
		bean.setGuardRegion(text_guard_region.getText().toString());
		bean.setSignAdvise(text_sign_advice.getText().toString());
		bean.setSignType(text_sign_type.getText().toString());
		bean.setSignRegion(text_sign_region.getText().toString());
		bean.setWaterproofAdvise(text_waterproof_advice.getText().toString());
		bean.setWaterproofType(text_waterproof_type.getText().toString());
		bean.setWaterproofRegion(text_waterproof_region.getText().toString());
		bean.setLightingAdvise(text_lighting_advice.getText().toString());
		bean.setLightingType(text_lighting_type.getText().toString());
		bean.setLightingRegion(text_lighting_region.getText().toString());
		bean.setCleanAdvise(text_clean_advice.getText().toString());
		bean.setCleanType(text_clean_type.getText().toString());
		bean.setCleanRegion(text_clean_region.getText().toString());
		bean.setRegulatingAdvise(text_regulating_advice.getText().toString());
		bean.setRegulatingType(text_regulating_type.getText().toString());
		bean.setRegulatingRegion(text_regulating_region.getText().toString());
		bean.setElseAdvise(text_else_advice.getText().toString());
		bean.setElseType(text_else_type.getText().toString());
		bean.setElseRegion(text_else_region.getText().toString());
		bean.setIsUpload("0");
		return bean;
	}
	
	
}
