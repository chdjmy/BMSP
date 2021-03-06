package com.example.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.BridgeBean;
import com.example.bean.SettingBean;
import com.example.common.dao.TemplateDao;
import com.example.http.AsyncHttpRequest;
import com.example.http.RequestParams;
import com.example.http.ThreadPool;
import com.example.util.CommonHttpRequest;
import com.example.util.MD5;
import com.example.util.Uri;
import com.example.http.RequestResultCallback;

/**
 * 桥梁基本信息的DAO
 * 
 * @author JMY
 * 
 */
public class BridgeDao extends TemplateDao<BridgeBean> {

	private Context context;

	public BridgeDao(Context context) {
		super(new SqlLiteHelper(context));
		this.context = context;
	}

	/*
	public void init(SettingBean settingBean, final Handler handler) {
		CommonHttpRequest.getSingle().request(context, settingBean, handler,
				Uri.Init.MSG_EQUIP_INFO, null);
	}*/
	
	//分页查找
	public List<BridgeBean> findByPage(int page){
		List<BridgeBean> list = new ArrayList<BridgeBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("bridge",new String[]{"bridge_id","bridge_code","bridge_name","line_number","line_name","line_type","location_name"}, "bridge_id>? AND bridge_id<=?", new String[]{String.valueOf(page),String.valueOf(page+30)}, null, null, null);
			while (cursor.moveToNext()) {
				BridgeBean bean = new BridgeBean();
				bean.setBridgeId(cursor.getString(0));
				bean.setBridgeCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
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
	public List<BridgeBean> findByBar(String str){
		List<BridgeBean> list = new ArrayList<BridgeBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("bridge",new String[]{"bridge_id","bridge_code","bridge_name","line_number","line_name","line_type","location_name"}, "bar_code=?", new String[]{str}, null, null, null);
			while (cursor.moveToNext()) {
				BridgeBean bean = new BridgeBean();
				bean.setBridgeId(cursor.getString(0));
				bean.setBridgeCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
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
	public List<BridgeBean> findByName(String str){
		List<BridgeBean> list = new ArrayList<BridgeBean>();
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			Cursor cursor = db.query("bridge",new String[]{"bridge_id","bridge_code","bridge_name","line_number","line_name","line_type","location_name"}, "bridge_name LIKE '%"+str+"%'", null, null, null, null);
			while (cursor.moveToNext()) {
				BridgeBean bean = new BridgeBean();
				bean.setBridgeId(cursor.getString(0));
				bean.setBridgeCode(cursor.getString(1));
				bean.setBridgeName(cursor.getString(2));
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
	
	//数据初始化获得页数
	public void initCount(SettingBean settingBean,final Handler handler){
		CommonHttpRequest.getSingle().request(context, settingBean, handler, Uri.Init.MSG_BRIDGE_INFO, null);
	}
	//分页初始化
	public synchronized void init(final SettingBean settingBean,final Handler handler,final int pageNum){
			System.out.println("---------init equip ------");
			System.out.println("pageNum==>" + pageNum);
			AsyncHttpRequest currentTime = new AsyncHttpRequest(Uri.getUri(settingBean.getIp(), settingBean.getPort(), settingBean.getWeb_app(), Uri.Init.GET_CURRENTTIME),null,
					new RequestResultCallback(){
						@Override
						public void onSuccess(Object paramObject) {
							MD5 md5 = new MD5();
							List<RequestParams> params = new ArrayList<RequestParams>();
							//long currentTime = System.currentTimeMillis();
							params.add(new RequestParams("time",String.valueOf(paramObject)));
							params.add(new RequestParams("v",md5.getMD5ofStr(Uri.KEY + " " + String.valueOf(paramObject))));
							params.add(new RequestParams("pageNum",String.valueOf(pageNum)));
							// url
							String url = Uri.getUri(settingBean.getIp(), settingBean.getPort(), settingBean.getWeb_app(), Uri.Init.BRIDGE_INFO_PAGE);
							AsyncHttpRequest request = new AsyncHttpRequest(url, params, new RequestResultCallback(){
								@Override
								public void onSuccess(Object paramObject) {
									if(null != handler){
										Message msg = handler.obtainMessage();
										Bundle data = new Bundle();
										data.putBoolean(Uri.KEY_FLAG, true);
										data.putString(Uri.KEY_MSG, paramObject.toString());
										msg.setData(data);
										msg.what = Uri.Init.MSG_BRIDGE_INFO_PAGE;
										
										handler.sendMessage(msg);
									}
								}

								@Override
								public void onFail(String paramException) {
									if(null != handler){
										Message msg = handler.obtainMessage();
										Bundle data = new Bundle();
										data.putBoolean(Uri.KEY_FLAG, false);
										data.putString(Uri.KEY_MSG, paramException);
										msg.setData(data);
										msg.what = Uri.Init.MSG_BRIDGE_INFO_PAGE;
										handler.sendMessage(msg);	
									}
									//Toast.makeText(context,paramException, Toast.LENGTH_LONG).show();
								}
								
							} );
							ThreadPool.getSingle().execute(request);
						}

						@Override
						public void onFail(String paramException) {
							if(null != handler){
								Message msg = handler.obtainMessage();
								Bundle data = new Bundle();
								data.putBoolean(Uri.KEY_FLAG, false);
								data.putString(Uri.KEY_MSG, paramException);
								msg.setData(data);
								msg.what = Uri.Init.MSG_BRIDGE_INFO_PAGE;
								handler.sendMessage(msg);
							}
						}});
			ThreadPool.getSingle().execute(currentTime);
			
	}

	public synchronized boolean add(JSONArray array) {
		if (null != array) {

			SQLiteDatabase db = null;

			try {

				db = this.getWritableDatabase();

				db.beginTransaction();

			//	db.delete("Bridge", null, null);

				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);

					Integer bridgeId = obj.getIntValue("bridgeId");
					String barCode = obj.getString("barCode");
					Double techStation = obj.getDoubleValue("techStation");
					String bridgeCode = obj.getString("bridgeCode");
					String bridgeName = obj.getString("bridgeName");
					String lineNumber = obj.getString("lineNumber");
					String lineName = obj.getString("lineName");
					String lineType = obj.getString("lineType");
					String secondaryLine = obj.getString("secondaryLine");
					String seqNumber = obj.getString("seqNumber");
					String locationName = obj.getString("locationName");
					String bridgeType = obj.getString("bridgeType");
					Double centerStake = obj.getDouble("centerStake");
					String mmName = obj.getString("mmName");
					String crossName = obj.getString("crossName");
					String crossType = obj.getString("crossType");
					Double constructionStake = obj.getDouble("constructionStake");
					String roadTechnicalGrade = obj.getString("roadTechnicalGrade");
					String bridgeProperty = obj.getString("bridgeProperty");
					String spanClassify = obj.getString("spanClassify");
					String designLoad = obj.getString("designLoad");
					String nowLoad = obj.getString("nowLoad");
					String superstructureForm = obj.getString("superstructureForm");
					String bridgePierType = obj.getString("bridgePieType");
					String abutmentType = obj.getString("abutmentType");
					String supportsType = obj.getString("supportsType");
					String bridgePierBaseType = obj.getString("bridgePierBaseType");
					String abutmentBaseType = obj.getString("abutmentBaseType");
					String bridgeDeckType = obj.getString("bridgeDeckType");
					String expansionJointsType = obj.getString("expansionJointsType");
					String bridgeUse = obj.getString("bridgeUse");
					String bridgeState = obj.getString("bridgeState");
					String administrativeLevel = obj.getString("administrativeLevel");
					String bridgeTown = obj.getString("bridgeTown");
					String manageCode = obj.getString("manageCode");
					Double bridgeLength = obj.getDouble("bridgeLength");
					String spanCombination = obj.getString("spanCombination");
					Double maxSpan = obj.getDouble("maxSpan");
					Double spanLength = obj.getDouble("spanLength");
					String bridgeWidthComb = obj.getString("bridgeWidthComb");
					String isWroadNbridge = obj.getString("isWroadNbridge");
					Double clearWidth = obj.getDouble("cleafWidth");
					Double bridgeHight = obj.getDouble("bridgeHeight");
					String buildYear = obj.getString("buildYear");
					String rebuildYear = obj.getString("rebuildYear");
					String navigationGrade = obj.getString("navigationGrade");
					Double bridgeHightLimit = obj.getDouble("bridgeHeightLimit");
					Double verticalClearHeight = obj.getDouble("verticalClearHeight");
					String middleSectionHigh = obj.getString("middleSectionHigh");
					Double bridgeWidth = obj.getDouble("bridgeWidth");
					Double flatCurveRadius = obj.getDouble("flatCurveRadius");
					Double briFrontRoadWidth = obj.getDouble("briFrontRoadWidth");
					String spanRatio = obj.getString("spanRatio");
					String deckLongitudinal = obj.getString("deckLongitudinal");
					String curvedRampFeature = obj.getString("curvedRamdFeature");
					String interchangeFeature = obj.getString("interchangeFeature");
					String crossForm = obj.getString("crossForm");
					Double totalCost = obj.getDouble("totalCost");
					Double constructionPeriod = obj.getDouble("constructionPeriod");
					Double deckCenterElevation = obj.getDouble("deckCenterElevation");
					String floodFrequency = obj.getString("floodFrequency");
					Double scourElevation = obj.getDouble("scourElevation");
					Double mainBridgeBaseElevation = obj.getDouble("mainBridgeBaseElevation");
					Double historicalMaximumFlood = obj.getDouble("historicalMaximumFlood");
					String annualAverageDailyTraffic = obj.getString("annualAverageDailyTraffic");
					String protectionProjectType = obj.getString("protectionProjectType");
					String pierProtectionType = obj.getString("pieProtectionType");
					String geologicalFoundation = obj.getString("geologicalFoundation");
					String seismic = obj.getString("seismic");
					String bridgeAttached = obj.getString("bridgeAttached");
					String median = obj.getString("median");
					String chargeStation = obj.getString("chargeStation");
					Double designSpeed = obj.getDouble("designSpeed");
					Double environmentCoordinationDegree = obj.getDouble("environmentCoordinationDegree");
					String environmentalConditions = obj.getString("environmentalConditions");
					String designDataNo = obj.getString("designDataNo");
					String completionDataNo = obj.getString("completionDataNo");
					String maintainDataNo = obj.getString("maintainDataNo");
					String storageUnits = obj.getString("storageUnits");
					String trafficControlMeasures = obj.getString("trafficControlMeasures");
					String isEvaluatedLast3year = obj.getString("isEvaluatedLast3year");
					
					JSONObject t = obj.getJSONObject("lastReformCompleteDate");
					String lastReformCompleteDate;
					if(t!=null){
						Timestamp tp = new Timestamp(t.getIntValue("time"));
						lastReformCompleteDate = tp.toLocaleString();  
					}else{
						lastReformCompleteDate = null;
					}
					
					
					String latelyRemouldPart = obj.getString("latelyRemouldPart");
					String projectProperty = obj.getString("projectProperty");
					String ownerUint = obj.getString("ownerUint");
					String designUnit = obj.getString("designUint"); 
					String designer = obj.getString("designer");
					String constructionUnit = obj.getString("constructionUnit");
					String constructionManager = obj.getString("constructionManager");
					String blueprint = obj.getString("blueprint");
					String supervisionUnit = obj.getString("supervisionUint");
					String completedView = obj.getString("completedView");
					String maintainName = obj.getString("maintainName");
					String maintainPhone  = obj.getString("maintainPhone");
					String projectName = obj.getString("projectName");
					String projectPhone = obj.getString("projectPhone");
					String unitName = obj.getString("unitName");
					String unitPhone = obj.getString("unitPhone");
					String roadManagerName = obj.getString("roadManagerName");
					String roadManagerPhone = obj.getString("roadManagerPhone");
					String areaManagerName = obj.getString("areaManagerName");
					String areaManagerPhone = obj.getString("areaManagerPhone");
					String dutyManagerName = obj.getString("dutyManagerName");
					String dutyManagerPhone = obj.getString("dutyManagerPhone");
					String frontPhotoAddr = obj.getString("frontPhotoAddr");
					String sidePhotoAddr = obj.getString("sidePhotoAddr");
					Double latitude  = obj.getDouble("latitude");
					Double longitude = obj.getDouble("longitude");
					

					ContentValues values = new ContentValues();
					values.put("bridge_id", bridgeId);
					values.put("bar_code", barCode);
					values.put("tech_station", techStation);
					values.put("bridge_code", bridgeCode);
					values.put("bridge_name", bridgeName);
					values.put("line_number", lineNumber);
					values.put("line_name", lineName);
					values.put("line_type", lineType);
					values.put("secondary_line", secondaryLine);
					values.put("seq_number", seqNumber);
					values.put("location_name", locationName);
					values.put("bridge_type", bridgeType);
					values.put("center_stake", centerStake);
					values.put("MM_name", mmName);
					values.put("cross_name",crossName );
					values.put("cross_type",crossType );
					values.put("construction_stake",constructionStake );
					values.put("road_technical_grade",roadTechnicalGrade );
					values.put("bridge_property", bridgeProperty);
					values.put("span_classify",spanClassify );
					values.put("design_load",designLoad );
					values.put("now_load", nowLoad);
					values.put("superstructure_form",superstructureForm );
					values.put("bridge_pier_type",bridgePierType );
					values.put("abutment_type",abutmentType );
					values.put("supports_type",supportsType );
					values.put("bridge_pier_base_type", bridgePierBaseType);
					values.put("abutment_base_type", abutmentBaseType);
					values.put("bridge_deck_type", bridgeDeckType);
					values.put("expansion_joints_type",expansionJointsType );
					values.put("bridge_use",bridgeUse );
					values.put("bridge_state",bridgeState );
					values.put("administrative_level", administrativeLevel);
					values.put("bridge_town",bridgeTown );
					values.put("manage_code", manageCode);
					values.put("bridge_length",bridgeLength );
					values.put("span_combination",spanCombination );
					values.put("max_span",maxSpan );
					values.put("span_length",spanLength );
					values.put("bridge_width_comb",bridgeWidthComb );
					values.put("isWroadNbridge", isWroadNbridge);
					values.put("clear_width",clearWidth );
					values.put("bridge_hight", bridgeHight);
					values.put("build_year",buildYear );
					values.put("rebuild_year",rebuildYear );
					values.put("navigation_grade",navigationGrade );
					values.put("bridge_hight_limit",bridgeHightLimit );
					values.put("vertical_clear_height", verticalClearHeight);
					values.put("middle_section_high", middleSectionHigh);
					values.put("bridge_width",bridgeWidth );
					values.put("flat_curve_radius", flatCurveRadius);
					values.put("bri_front_road_width",briFrontRoadWidth );
					values.put("span_ratio",spanRatio );
					values.put("deck_longitudinal", deckLongitudinal);
					values.put("curved_ramp_feature",curvedRampFeature );
					values.put("interchange_feature", interchangeFeature);
					values.put("cross_form", crossForm);
					values.put("total_cost",totalCost );
					values.put("construction_period", constructionPeriod);
					values.put("deck_center_elevation", deckCenterElevation);
					values.put("flood_frequency",floodFrequency );
					values.put("scour_elevation", scourElevation);
					values.put("main_bridge_base_elevation", mainBridgeBaseElevation);
					values.put("historical_maximum_flood", historicalMaximumFlood);
					values.put("annual_average_daily_traffic", annualAverageDailyTraffic);
					values.put("protection_project_type",protectionProjectType );
					values.put("pier_protection_type", pierProtectionType);
					values.put("geological_foundation", geologicalFoundation);
					values.put("seismic",seismic );
					values.put("bridge_attached", bridgeAttached);
					values.put("median", median);
					values.put("charge_station",chargeStation );
					values.put("design_speed", designSpeed);
					values.put("environment_coordination_degree", environmentCoordinationDegree);
					values.put("environmental_conditions", environmentalConditions);
					values.put("design_data_No", designDataNo);
					values.put("completion_data_No",completionDataNo );
					values.put("maintain_data_No", maintainDataNo);
					values.put("storage_units",storageUnits );
					values.put("traffic_control_measures", trafficControlMeasures);
					values.put("is_evaluated_last3year", isEvaluatedLast3year);
					values.put("last_reform_complete_date",lastReformCompleteDate );
					values.put("lately_remould_part", latelyRemouldPart);
					values.put("project_property", projectProperty);
					values.put("owner_uint", ownerUint);
					values.put("design_unit", designUnit);
					values.put("designer", designer);
					values.put("construction_unit", constructionUnit);
					values.put("construction_manager",constructionManager );
					values.put("blueprint",blueprint );
					values.put("supervision_unit",supervisionUnit );
					values.put("completed_view",completedView );
					values.put("maintain_name",maintainName );
					values.put("maintain_phone", maintainPhone);
					values.put("project_name",projectName );
					values.put("project_phone", projectPhone);
					values.put("unit_name", unitName);
					values.put("unit_phone", unitPhone);
					values.put("road_manager_name",roadManagerName );
					values.put("road_manager_phone", roadManagerPhone);
					values.put("area_manager_name", areaManagerName);
					values.put("area_manager_phone", areaManagerPhone);
					values.put("duty_manager_name", dutyManagerName);
					values.put("duty_manager_phone", dutyManagerPhone);
					values.put("front_photo_addr", frontPhotoAddr);
					values.put("side_photo_addr",sidePhotoAddr );
					values.put("latitude",latitude );
					values.put("longitude",longitude );

					db.insert("bridge", null, values);
				}
				db.setTransactionSuccessful();

				db.endTransaction();

				return true;

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != db) {
					db.close();
				}
			}
		}

		return false;
	}
}
