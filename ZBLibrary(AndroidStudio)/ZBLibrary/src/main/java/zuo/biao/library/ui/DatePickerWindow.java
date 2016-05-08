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
import java.util.Calendar;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseBottomWindow;
import zuo.biao.library.bean.Entry;
import zuo.biao.library.bean.GridPickerConfigBean;
import zuo.biao.library.ui.GridPickerView.OnTabClickListener;
import zuo.biao.library.util.StringUtil;
import zuo.biao.library.util.TimeUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**日期选择窗口
 * @author Lemon
 * @use 参考.ModelBottomWindow;
	 * @warn 和android系统SDK内一样，month从0开始
 */
public class DatePickerWindow extends BaseBottomWindow implements OnClickListener {
	private static final String TAG = "DatePickerWindow";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**
	 * @param context
	 * @param limitYearMonthDay
	 * @return
	 */
	public static Intent createIntent(Context context, int[] limitYearMonthDay) {
		return createIntent(context, limitYearMonthDay, null);
	}
	public static Intent createIntent(Context context, int[] limitYearMonthDay, int[] defaultYearMonthDay) {
		int[] selectedDate = TimeUtil.getDateDetail(System.currentTimeMillis());
		int[] minYearMonthDay = null;
		int[] maxYearMonthDay = null;
		if (TimeUtil.fomerIsBigger(limitYearMonthDay, selectedDate)) {
			minYearMonthDay = selectedDate;
			maxYearMonthDay = limitYearMonthDay;
		} else {
			minYearMonthDay = limitYearMonthDay;
			maxYearMonthDay = selectedDate;
		}
		return createIntent(context, minYearMonthDay, maxYearMonthDay, defaultYearMonthDay);
	}
	/**
	 * @param context
	 * @param minYearMonthDay
	 * @param maxYearMonthDay
	 * @return
	 */
	public static Intent createIntent(Context context, int[] minYearMonthDay, int[] maxYearMonthDay, int[] defaultYearMonthDay) {
		return new Intent(context, DatePickerWindow.class).
				putExtra(INTENT_MIN_DATE, minYearMonthDay).
				putExtra(INTENT_MAX_DATE, maxYearMonthDay).
				putExtra(INTENT_DEFAULT_DATE, defaultYearMonthDay);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.container_window);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

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
			tvContainerWindowTitle.setText("选择日期"); 
		}

		llContainerWindowContentContainer = (LinearLayout) findViewById(R.id.llContainerWindowContentContainer);
	}

	private List<Entry<Boolean, String>> list;
	private Handler getListHandler;
	private Runnable getListRunnable;
	private void setPickerView(final int tabPosition) {
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

				final ArrayList<Integer> selectedItemList = new ArrayList<Integer>();
				for (GridPickerConfigBean gpcb : configList) {
					selectedItemList.add(0 + Integer.valueOf(StringUtil.getNumber(gpcb.getSelectedItemName())));
				}

				list = getList(tabPosition, selectedItemList);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (isAlive) {
							gridPickerView.setView(tabPosition, list);
						}
					}
				});
			}
		};
		getListHandler = runThread(TAG + "onItemSelectedListener.onItemSelected.getList", getListRunnable);		
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_MIN_DATE = "INTENT_MIN_DATE";
	public static final String INTENT_MAX_DATE = "INTENT_MAX_DATE";
	public static final String INTENT_DEFAULT_DATE = "INTENT_DEFAULT_DATE";

	//	private long minDate;
	//	private long maxDate;

	private GridPickerView gridPickerView;
	private int[] minDateDetails;
	private int[] maxDateDetails;
	private int[] defaultDateDetails;

	private ArrayList<GridPickerConfigBean> configList;
	@Override
	public void initData() {//必须调用
		super.initData();

		//		minDate = getIntent().getLongExtra(INTENT_MIN_DATE, 0);
		//		maxDate = getIntent().getLongExtra(INTENT_MAX_DATE, 0);
		//		if (minDate >= maxDate) {
		//			Log.e(TAG, "initData minDate >= maxDate >> finish(); return; ");
		//			finish();
		//			return;
		//		}
		//		

		//		int[] minDateDetails = TimeUtil.getDateDetail(minDate);
		//		int[] maxDateDetails = TimeUtil.getDateDetail(maxDate);
		minDateDetails = getIntent().getIntArrayExtra(INTENT_MIN_DATE);
		maxDateDetails = getIntent().getIntArrayExtra(INTENT_MAX_DATE);
		defaultDateDetails = getIntent().getIntArrayExtra(INTENT_DEFAULT_DATE);

		if (minDateDetails == null || minDateDetails.length <= 0) {
			minDateDetails = new int[]{1970, 1, 1};
		}
		if (maxDateDetails == null || maxDateDetails.length <= 0) {
			maxDateDetails = new int[]{2020, 11, 31};
		}
		if (minDateDetails == null || minDateDetails.length <= 0
				|| maxDateDetails == null || minDateDetails.length != maxDateDetails.length) {
			finish();
			return;
		}
		if (defaultDateDetails == null || defaultDateDetails.length < 3) {
			defaultDateDetails = TimeUtil.getDateDetail(System.currentTimeMillis());
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
				configList = new ArrayList<GridPickerConfigBean>();

				final ArrayList<Integer> selectedItemList = new ArrayList<Integer>();
				selectedItemList.add(defaultDateDetails[0]);
				selectedItemList.add(defaultDateDetails[1]);
				selectedItemList.add(defaultDateDetails[2]);

				list = getList(selectedItemList.size() - 1, selectedItemList);

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


	private synchronized List<Entry<Boolean, String>> getList(int tabPosition, ArrayList<Integer> selectedItemList) {
		int level = 0 + tabPosition;
		if (selectedItemList == null || selectedItemList.size() != 3 || TimeUtil.isContainLevel(level) == false) {
			return null;
		}

		list = new ArrayList<Entry<Boolean, String>>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(selectedItemList.get(0), selectedItemList.get(1) - 1, 1);
		switch (level) {
		case TimeUtil.LEVEL_YEAR:
			for (int i = 0; i < maxDateDetails[0] - minDateDetails[0]; i++) {
				list.add(new Entry<Boolean, String>(true, String.valueOf(i + 1 + minDateDetails[0])));
			}
			break;
		case TimeUtil.LEVEL_MONTH:
			for (int i = 0; i < 12; i++) {
				list.add(new Entry<Boolean, String>(true, String.valueOf(i + 1)));
			}
			break;
		case TimeUtil.LEVEL_DAY:
			for (int i = calendar.get(Calendar.DAY_OF_WEEK) - 1; i < 7; i++) {
				list.add(new Entry<Boolean, String>(false, TimeUtil.Day.getDayNameOfWeek(i)));
			}
			for (int i = 0; i < calendar.get(Calendar.DAY_OF_WEEK) - 1; i++) {
				list.add(new Entry<Boolean, String>(false, TimeUtil.Day.getDayNameOfWeek(i)));
			}
			for (int i = 0; i < calendar.getActualMaximum(Calendar.DATE); i++) {
				list.add(new Entry<Boolean, String>(true, String.valueOf(i + 1)));
			}
			break;
			//		case TimeUtil.LEVEL_HOUR:
			//			break;
			//		case TimeUtil.LEVEL_MINUTE:
			//			break;
			//		case TimeUtil.LEVEL_SECOND:
			//			break;
		default:
			break;
		}

		if (configList == null || configList.size() != 3) {
			configList = new ArrayList<GridPickerConfigBean>();

			for (int i = 0; i < maxDateDetails[0] - minDateDetails[0]; i++) {
				if (selectedItemList.get(0) == i + 1 + minDateDetails[0]) {
					configList.add(new GridPickerConfigBean(TimeUtil.NAME_YEAR, "" + selectedItemList.get(0), i, 5, 4));
				}
			}
			for (int i = 0; i < 12; i++) {
				if (selectedItemList.get(1) == i + 1) {
					configList.add(new GridPickerConfigBean(TimeUtil.NAME_MONTH, "" + selectedItemList.get(1), i, 4, 3));
				}
			}
			for (int i = 0; i < calendar.getActualMaximum(Calendar.DATE); i++) {
				if (selectedItemList.get(2) == i + 1) {
					configList.add(new GridPickerConfigBean(TimeUtil.NAME_DAY, "" + selectedItemList.get(2), i + 7, 7, 6));
				}
			}
		}

		return list;
	}


	public static final String RESULT_DATE = "RESULT_DATE";
	public static final String RESULT_TIME_IN_MILLIS = "RESULT_TIME_IN_MILLIS";
	public static final String RESULT_DATE_DETAIL_LIST = "RESULT_DATE_DETAIL_LIST";
	/**保存并退出
	 * @warn 和android系统SDK内一样，month从0开始
	 */
	private void saveAndExit() {

		intent = new Intent();

		List<String> list = gridPickerView.getSelectedItemList();
		if (list != null && list.size() >= 3) {
			ArrayList<Integer> detailList = new ArrayList<Integer>(); 
			for (int i = 0; i < list.size(); i++) {
				detailList.add(0 + Integer.valueOf(StringUtil.getNumber(list.get(i))));
			}
			detailList.set(1, detailList.get(1) - 1);
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(detailList.get(0), detailList.get(1), detailList.get(2));
			intent.putExtra(RESULT_TIME_IN_MILLIS, calendar.getTimeInMillis());
			intent.putIntegerArrayListExtra(RESULT_DATE_DETAIL_LIST, detailList);
		}

		setResult(RESULT_OK, intent);
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
			setPickerView(tabPosition);
		}
	};

	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
			gridPickerView.doOnItemSelected(gridPickerView.getCurrentTabPosition()
					, position, gridPickerView.getCurrentSelectedItemName());
			int tabPosition = gridPickerView.getCurrentTabPosition() + 1;
			setPickerView(tabPosition);
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