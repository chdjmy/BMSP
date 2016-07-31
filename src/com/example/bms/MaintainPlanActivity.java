package com.example.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bean.BridgeBean;
import com.example.bean.MemberBean;
import com.example.dao.BridgeDao;
import com.example.dao.MemberDao;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class MaintainPlanActivity extends BaseActivity {

	private ListView listViewBridgeInfo;
	private ListView listViewNewPlan;// 维修计划输入列表
	private ListView listViewMaterial;
	private List<String> materials;
	private List<Map<String, Object>> listPlanItems;
	private SimpleAdapter listPlanItemsAdapter;
	
	private LinearLayout layout_bridge_info;
	private LinearLayout layout_plan_add;
	private LinearLayout layout_plan_list;
	
	private Button buttonBack, buttonSave;

	private List<BridgeBean> listBridges;
	private int page;
	private boolean isLoading;
	private BridgeDao bridgeDao;
	private MyListAdapter adapter;

	private Button buttonScan;
	private Button buttonSearch;
	private EditText editSearch;
	private ImageView imageClear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain_plan);
		layout_bridge_info = (LinearLayout) findViewById(R.id.layout_bridge_info);
		layout_plan_add = (LinearLayout) findViewById(R.id.layout_plan_add);
		layout_plan_list = (LinearLayout) findViewById(R.id.layout_plan_list);
		
		buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout_plan_add.setVisibility(View.GONE);
				layout_bridge_info.setVisibility(View.VISIBLE);
				layout_plan_list.setVisibility(View.VISIBLE);
			}

		});
		buttonSave = (Button) findViewById(R.id.buttonSave);

		initViewBridgeInfo();

		String[] memberItems = new String[] { "桥梁名称：", "桩号：", "结构形式：",
				"病害名称：", "病害部位：", "病害数量：", "单位：", "拟维修方案：","备注：","维修材料："
				 };// "邮箱：",

		MemberDao dao = new MemberDao(this);
		MemberBean info = dao.get("1");

		int iLength = memberItems.length;
		Map<String, Object>[] maps = new HashMap[iLength];

		for (int i = 0; i < iLength; i++) {
			maps[i] = new HashMap<String, Object>();
			maps[i].put("name", memberItems[i]);
		}

		maps[0].put("value", info.getUsername());// 登录名
		maps[1].put("value", info.getPassword());// 登录密码
		maps[2].put("value", info.getName());// 中文全名
		maps[3].put("value", info.getDepId());// 部门ID
		maps[4].put("value", info.getPositionId());// 职位
		maps[5].put("value", info.getUserCode());// 人员编码
		maps[6].put("value", info.getSex());// 性别
		maps[7].put("value", info.getIdNumber());// 身份证号
		maps[8].put("value", info.getBirthday());// 生日
		maps[9].put("value", info.getQq());// QQ号码

		listPlanItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < iLength; i++) {
			listPlanItems.add(maps[i]);
		}

		listViewNewPlan = (ListView) findViewById(R.id.list_plan_add);
		listPlanItemsAdapter = new MySimpleListAdapter(this, listPlanItems,
				R.layout.listitem_plan_add, new String[] { "name", "value" },
				new int[] { R.id.name, R.id.value });
		listViewNewPlan.setAdapter(listPlanItemsAdapter);
		layout_plan_add.setVisibility(View.GONE);
		
		materials = new ArrayList<String>();
		materials.add("速凝混凝土");
		materials.add("钢纤混凝土");
		materials.add("沥青混凝土");
		materials.add("铺装钢筋");
		materials.add("拆除圬工");
		materials.add("植筋");
		listViewMaterial = (ListView) findViewById(R.id.list_material);
		listViewMaterial.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,materials));
		listViewMaterial.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Map<String, Object> map= new HashMap<String, Object>();
				map.put("name",materials.get(position));
				map.put("value", "");
				listPlanItems.add(map);
				listPlanItemsAdapter.notifyDataSetChanged();
			}
		});

	}

	private class MySimpleListAdapter extends SimpleAdapter {
		public MySimpleListAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = super.getView(position, convertView, parent);
			TextView name = (TextView) v.findViewById(R.id.name);
			EditText value = (EditText) v.findViewById(R.id.value);
			String itemName = name.getText().toString();
			if(itemName.equals("桥梁名称：")
					||itemName.equals("桩号：")
					||itemName.equals("结构形式：")){
				value.setEnabled(false);
			}
			return v;
		}
	}

	private void initViewBridgeInfo() {
		listViewBridgeInfo = (ListView) findViewById(R.id.listViewBridgeInfo);
		bridgeDao = new BridgeDao(this);
		page = 0;
		isLoading = false;
		listBridges = bridgeDao.findByPage(page);
		page = page + 30;
		adapter = new MyListAdapter(MaintainPlanActivity.this,
				R.layout.listitem_maintain_plan, listBridges);
		listViewBridgeInfo.setAdapter(adapter);
		listViewBridgeInfo.setOnScrollListener(new MyOnScrollListener());

		// 搜索功能
		editSearch = (EditText) findViewById(R.id.editSearch);
		buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
		buttonSearch = (Button) findViewById(R.id.buttonSearch);
		buttonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = editSearch.getText().toString();
				if (str == "") {
					displayToast("您输入的检索信息为空");
				} else if (str.matches("[0-9]+") && str.length() == 15) {
					listBridges.clear();
					listBridges.addAll(0, bridgeDao.findByBar(str));
					adapter.notifyDataSetChanged();
					isLoading = true;
				} else {
					listBridges.clear();
					listBridges.addAll(0, bridgeDao.findByName(str));
					adapter.notifyDataSetChanged();
					isLoading = true;
				}

			}

		});

		imageClear = (ImageView) findViewById(R.id.imageClear);
		imageClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editSearch.setText("");
				page = 0;
				isLoading = false;
				listBridges.clear();
				listBridges.addAll(bridgeDao.findByPage(page));
				page = page + 30;
				adapter.notifyDataSetChanged();
			}

		});
	}

	private class MyListAdapter extends ArrayAdapter<BridgeBean> {
		private int resource;

		public MyListAdapter(Context context, int resourceId,
				List<BridgeBean> objects) {
			super(context, resourceId, objects);
			// 记录下来稍后使用
			resource = resourceId;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout listView;
			// 获取数据
			BridgeBean b = getItem(position);
			String bridgeCode = b.getBridgeCode();
			String bridgeName = b.getBridgeName();
			String lineNumber = b.getLineNumber();
			String lineName = b.getLineName();

			if (convertView == null) {
				listView = new LinearLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater vi = (LayoutInflater) getContext()
						.getSystemService(inflater);
				vi.inflate(resource, listView, true);
				Log.d("Adapter", "convertView is null now");
			} else {
				// 很奇怪基本没被调用过
				listView = (LinearLayout) convertView;
				Log.d("Adapter", "convertView is not null now");
			}

			// 填充自定义数据
			TextView item_bridgeCode = (TextView) listView
					.findViewById(R.id.bridgeCode);
			TextView item_bridgeName = (TextView) listView
					.findViewById(R.id.bridgeName);
			TextView item_lineNumber = (TextView) listView
					.findViewById(R.id.lineNumber);
			TextView item_lineName = (TextView) listView
					.findViewById(R.id.lineName);
			Button btn_add = (Button) listView.findViewById(R.id.btn_add);

			item_bridgeCode.setText(bridgeCode);
			item_bridgeName.setText(bridgeName);
			item_lineNumber.setText(lineNumber);
			item_lineName.setText(lineName);
			btn_add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					layout_plan_add.setVisibility(View.VISIBLE);
					layout_bridge_info.setVisibility(View.GONE);
					layout_plan_list.setVisibility(View.GONE);
				}
			});

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色
			listView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同

			return listView;
		}
	}

	private class MyOnScrollListener implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			if (firstVisibleItem + visibleItemCount == totalItemCount
					&& totalItemCount != 0) {
				if (isLoading || page == -1) {
					return;
				}
				// 异步刷新
				new AsyncTask<Void, String, List<BridgeBean>>() {
					protected List<BridgeBean> doInBackground(Void... params) {
						List<BridgeBean> listNewBridges = null;
						try {
							listNewBridges = bridgeDao.findByPage(page);
							page = page + 30;

						} catch (Exception e) {
							publishProgress("已显示全部桥梁");
							e.printStackTrace();
						}
						return listNewBridges;
					}

					protected void onPreExecute() {
						// ll_loading.setVisibility(View.VISIBLE);
						isLoading = true;
						super.onPreExecute();
					}

					protected void onPostExecute(List<BridgeBean> result) {
						if (result.size() == 0) {
							page = -1;
						} else {
							listBridges.addAll(result);
							adapter.notifyDataSetChanged();
						}

						// ll_loading.setVisibility(View.GONE);
						isLoading = false;
						super.onPostExecute(result);
					}

					protected void onProgressUpdate(String... values) {
						super.onProgressUpdate(values);
					}

				}.execute();
			}

		}

	}

}
