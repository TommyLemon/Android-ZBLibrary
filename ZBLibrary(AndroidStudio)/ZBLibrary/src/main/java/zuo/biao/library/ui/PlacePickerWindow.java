/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package zuo.biao.library.ui;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseBottomWindow;
import zuo.biao.library.bean.Entry;
import zuo.biao.library.bean.GridPickerConfigBean;
import zuo.biao.library.manager.CityDB;
import zuo.biao.library.ui.GridPickerView.OnTabClickListener;
import zuo.biao.library.util.PlaceUtil;
import zuo.biao.library.util.StringUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**地址选择弹窗
 * @author Lemon
 * @use toActivity(PlacePickerWindow.createIntent);
 *      然后在onActivityResult方法内获取data.getStringExtra(PlacePickerWindow.RESULT_PLACE);
 */
public class PlacePickerWindow extends BaseBottomWindow implements OnClickListener {
	private static final String TAG = "PlacePickerWindow";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**
	 * @param context
	 * @param limitLevel
	 * @return
	 */
	public static Intent createIntent(Context context, String packageName, int maxLevel) {
		return createIntent(context, packageName, 0, maxLevel);
	}
	/**
	 * @param context
	 * @param minLevel
	 * @param maxLevel
	 * @return
	 */
	public static Intent createIntent(Context context, String packageName, int minLevel, int maxLevel) {
		return new Intent(context, PlacePickerWindow.class).
				putExtra(INTENT_PACKAGE_NAME, packageName).
				putExtra(INTENT_MIN_LEVEL, minLevel).
				putExtra(INTENT_MAX_LEVEL, maxLevel);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public static final String INTENT_PACKAGE_NAME = "INTENT_PACKAGE_NAME";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.container_window);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		cityDB = CityDB.getInstance(context, StringUtil.getTrimedString(getIntent().getStringExtra(INTENT_PACKAGE_NAME)));

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	private TextView tvContainerWindowTitle;
	private LinearLayout llContainerWindowContentContainer;
	@Override
	public void initView() {//必须调用
		super.initView();

		tvContainerWindowTitle = (TextView) findViewById(R.id.tvContainerWindowTitle);
		tvContainerWindowTitle.setVisibility(View.VISIBLE);
		if (StringUtil.isNotEmpty(getIntent().getStringExtra(INTENT_TITLE), true)) {
			tvContainerWindowTitle.setText(StringUtil.getCurrentString()); 
		} else {
			tvContainerWindowTitle.setText("选择地区");
		}

		llContainerWindowContentContainer = (LinearLayout) findViewById(R.id.llContainerWindowContentContainer);
	}


	private List<Entry<Boolean, String>> list;
	private Handler getListHandler;
	private Runnable getListRunnable;
	private void setPickerView(final int tabPosition, final int itemPositon) {
		if (getListHandler != null && getListRunnable != null) {
			try {
				getListHandler.removeCallbacks(getListRunnable);
			} catch (Exception e) {
				Log.e(TAG, "onItemSelectedListener.onItemSelected   try {getListHandler.removeCallbacks(getListRunnable); " +
						"} catch (Exception e) { \n" + e.getMessage());
			}
		}
		getListRunnable = new Runnable() {
			@Override
			public void run() {

				list = getList(tabPosition, gridPickerView.getSelectedItemList());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (isAlive) {
							gridPickerView.setView(tabPosition, list, itemPositon);
						}
					}
				});
			}
		};
		getListHandler = runThread(TAG + "onItemSelectedListener.onItemSelected.getList", getListRunnable);		
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_MIN_LEVEL = "INTENT_MIN_LEVEL";//最小深度。 省/... - minLevel = 0; 市/... - minLevel = 1;
	public static final String INTENT_MAX_LEVEL = "INTENT_MAX_LEVEL";//最大深度。 ...市/ - maxLevel = 1;  .../乡(街) - maxLevel = 3;

	private int minLevel;
	private int maxLevel;

	private CityDB cityDB;
	private GridPickerView gridPickerView;
	@Override
	public void initData() {//必须调用
		super.initData();

		minLevel = getIntent().getIntExtra(INTENT_MIN_LEVEL, 0);
		maxLevel = getIntent().getIntExtra(INTENT_MAX_LEVEL, 2);
		if (maxLevel < 0 || minLevel > maxLevel) {
			Log.e(TAG, "initData maxLevel < 0 || minLevel > maxLevel >> finish(); return; ");
			finish();
			return;
		}
		if (minLevel < 0) {
			minLevel = 0;
		}

		llContainerWindowContentContainer.removeAllViews();
		if (gridPickerView == null) {
			gridPickerView = new GridPickerView(context, getLayoutInflater());
			llContainerWindowContentContainer.addView(gridPickerView.getView());
		}
		gridPickerView.setView(null);

		runThread(TAG + "initData", new Runnable() {

			@Override
			public void run() {
				final ArrayList<GridPickerConfigBean> configList = new ArrayList<GridPickerConfigBean>();
				configList.add(new GridPickerConfigBean("", "浙江", 10));
				configList.add(new GridPickerConfigBean("", "杭州", 0));

				final ArrayList<String> selectedItemNameList = new ArrayList<String>();
				for (GridPickerConfigBean gpcb : configList) {
					selectedItemNameList.add(gpcb.getSelectedItemName());
				}
				
				list = getList(selectedItemNameList.size() - 1, selectedItemNameList);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (isAlive) {
							gridPickerView.init(configList, list);
						}
					}
				});
			}
		});

	}
	
	@Override
	@Nullable
	protected String getTitleName() {
		return getIntent().getStringExtra(INTENT_TITLE);
	}


	private synchronized List<Entry<Boolean, String>> getList(int tabPosition, ArrayList<String> selectedItemList) {
		int level = minLevel + tabPosition;
		if (selectedItemList == null || selectedItemList.size() <= 0 || PlaceUtil.isContainLevel(level) == false) {
			return null;
		}

		list = new ArrayList<Entry<Boolean, String>>();
		List<String> cityNameList = null;
		switch (level) {
		case PlaceUtil.LEVEL_PROVINCE:
			cityNameList = cityDB.getAllProvince();
			break;
		case PlaceUtil.LEVEL_CITY:
			cityNameList = cityDB.getProvinceAllCity(StringUtil.getTrimedString(selectedItemList.get(0)));
			break;
		case PlaceUtil.LEVEL_DISTRICT:
			break;
		case PlaceUtil.LEVEL_TOWN:
			break;
		case PlaceUtil.LEVEL_ROAD:
			break;
		default:
			break;
		}

		if (cityNameList != null) {
			for (String name : cityNameList) {
				list.add(new Entry<Boolean, String>(true, name));
			}
		}
		return list;
	}


	public static final String RESULT_PLACE_LIST = "RESULT_PLACE_LIST";
	/**保存并退出
	 */
	private void saveAndExit() {

		setResult(RESULT_OK, new Intent().putStringArrayListExtra(RESULT_PLACE_LIST, gridPickerView.getSelectedItemList()));
		finish();
	}


	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须调用
		super.initListener();

		findViewById(R.id.tvContainerWindowReturn).setOnClickListener(this);
		findViewById(R.id.tvContainerWindowSave).setOnClickListener(this);

		gridPickerView.setOnTabClickListener(onTabClickListener);
		gridPickerView.setOnItemSelectedListener(onItemSelectedListener);
	}


	private OnTabClickListener onTabClickListener = new OnTabClickListener() {

		@Override
		public void onTabClick(int tabPosition, TextView tvTab) {
			setPickerView(tabPosition, gridPickerView.getSelectedItemPosition(tabPosition));
		}
	};

	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
			gridPickerView.doOnItemSelected(gridPickerView.getCurrentTabPosition()
					, position, gridPickerView.getCurrentSelectedItemName());
			setPickerView(gridPickerView.getCurrentTabPosition() + 1, 0);
		}
		@Override
		public void onNothingSelected(AdapterView<?> parent) { }
	};


	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//	@Override
	//	public void onClick(View v) {
	//		switch (v.getId()) {
	//		case R.id.tvContainerWindowReturn:
	//			finish();
	//			break;
	//		case R.id.tvContainerWindowSave:
	//			saveAndExit();
	//			break;
	//		default:
	//			break;
	//		}
	//	}
	//Library内switch方法中case R.id.idx会报错
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvContainerWindowReturn) {
			finish();
		} else if (v.getId() == R.id.tvContainerWindowSave) {
			saveAndExit();
		}
	}





	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}