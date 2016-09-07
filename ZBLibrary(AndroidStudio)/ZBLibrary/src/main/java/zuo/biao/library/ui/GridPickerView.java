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
import zuo.biao.library.base.BaseView;
import zuo.biao.library.bean.Entry;
import zuo.biao.library.bean.GridPickerConfigBean;
import zuo.biao.library.util.ScreenUtil;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**网格选择器View
 * @author Lemon
 * @must 调用init方法
 * @use 参考 .DemoView
 */
public class GridPickerView extends BaseView<List<Entry<Integer, String>>> {
	private static final String TAG = "GridPickerView";

	/**tabs切换和gridView的内容切换
	 */
	public interface OnTabClickListener {

		void onTabClick(int tabPosition, TextView tvTab);
	}

	private OnTabClickListener onTabClickListener;
	public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
		this.onTabClickListener = onTabClickListener;
	}


	private OnItemSelectedListener onItemSelectedListener;
	public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
		this.onItemSelectedListener = onItemSelectedListener;
	}

	private int contentHeight;
	public GridPickerView(Activity context, Resources resources) {
		super(context, resources);
		contentHeight = (int) getDimension(R.dimen.grid_picker_content_height);
	}

	public LinearLayout llGridPickerViewTabContainer;
	public GridView gvGridPickerView;
	/**获取View
	 * @return 
	 */
	@SuppressLint("InflateParams")
	@Override
	public View createView(LayoutInflater inflater) {
		convertView = inflater.inflate(R.layout.grid_picker_view, null);

		llGridPickerViewTabContainer = findViewById(R.id.llGridPickerViewTabContainer);
		gvGridPickerView = findViewById(R.id.gvGridPickerView);

		return convertView;
	}


	public ArrayList<GridPickerConfigBean> getConfigList() {
		return configList;
	}

	public ArrayList<String> getSelectedItemList() {
		ArrayList<String> list = new ArrayList<String>();
		for (GridPickerConfigBean gpcb : configList) {
			list.add(gpcb.getSelectedItemName());
		}
		return list;
	}
	public int getTabCount() {
		return configList == null ? 0 : configList.size();
	}

	public boolean isOnFirstTab() {
		return getTabCount() <= 0 ? false : getCurrentTabPosition() <= 0;
	}
	public boolean isOnLastTab() {
		return getTabCount() <= 0 ? false : getCurrentTabPosition() >= getTabCount() - 1;
	}

	private int currentTabPosition;
	public int getCurrentTabPosition() {
		return currentTabPosition;
	}

	private String currentTabName;
	public String getCurrentTabName() {
		return currentTabName;
	}
	private String currentTabSuffix;
	public String getCurrentTabSuffix() {
		return currentTabSuffix;
	}


	public String currentSelectedItemName;
	public String getCurrentSelectedItemName() {
		return currentSelectedItemName;
	}
	public int currentSelectedItemPosition;
	public int getCurrentSelectedItemPosition() {
		return currentSelectedItemPosition;
	}

	public String getSelectedItemName(int tabPosition) {
		return configList.get(tabPosition).getSelectedItemName();
	}
	public int getSelectedItemPosition(int tabPosition) {
		return configList.get(tabPosition).getSelectedItemPostion();
	}


	public List<Entry<Integer, String>> getList() {
		return adapter == null ? null : adapter.getList();
	}


	@Override
	public void setView(List<Entry<Integer, String>> l){/*do nothing,必须init**/}


	/**初始化，必须使用且只能使用一次
	 * @param configList
	 * @param lastList
	 */
	public final void init(ArrayList<GridPickerConfigBean> configList, List<Entry<Integer, String>> lastList) {
		if (configList == null || configList.size() <= 0) {
			Log.e(TAG, "initTabs  (configList == null || configList.size() <= 0 " +
					">> selectedItemPostionList = new ArrayList<Integer>(); return;");
			return;
		}

		currentTabPosition = configList.size() - 1;
		currentTabName = configList.get(currentTabPosition).getTabName();

		int tabWidth = configList.size() < 4 ? ScreenUtil.getScreenWidth(context) / configList.size() : 3;
		llGridPickerViewTabContainer.removeAllViews();
		for (int i = 0; i < configList.size(); i++) {//需要重新设置来保持每个tvTab宽度一致

			addTab(i, tabWidth, StringUtil.getTrimedString(configList.get(i)));
		}
		llGridPickerViewTabContainer.getChildAt(currentTabPosition)
		.setBackgroundResource(R.color.alpha_3);

		this.configList = configList;

		setView(currentTabPosition, lastList, configList.get(currentTabPosition).getSelectedItemPostion());
	}



	/**添加tab
	 * @param tabPosition
	 * @param tabWidth
	 * @param name
	 */
	@SuppressLint("NewApi")
	private void addTab(final int tabPosition, int tabWidth, String name) {
		if (StringUtil.isNotEmpty(name, true) == false) {
			return;
		}
		name = StringUtil.getTrimedString(name);

		final TextView tvTab = new TextView(context);
		tvTab.setLayoutParams(new LayoutParams(tabWidth, LayoutParams.MATCH_PARENT));
		tvTab.setGravity(Gravity.CENTER);
		//		tvTab.setPaddingRelative(4, 12, 4, 12);
		tvTab.setTextColor(context.getResources().getColor(R.color.black));
		tvTab.setBackgroundResource(R.drawable.bg_pressed_common);
		tvTab.setTextSize(18);
		tvTab.setSingleLine(true);
		tvTab.setText(name);
		tvTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tabPosition == getCurrentTabPosition()) {
					return;
				}

				if (onTabClickListener != null) {
					onTabClickListener.onTabClick(tabPosition, tvTab);
					return;
				}
				//点击就是要切换list，这些配置都要改setView(tabSuffix, tabPosition, tabName, list, numColumns, maxShowRows, itemPosition)
			}
		});
		llGridPickerViewTabContainer.addView(tvTab);
	}



	private static final int MAX_NUM_TABS = 12;//最大标签数量

	private ArrayList<GridPickerConfigBean> configList;
	private GridPickerAdapter adapter;
	/**设置并显示内容//可能导致每次都不变
	 * @param tabPosition
	 * @param list
	 */
	public void setView(final int tabPosition, List<Entry<Integer, String>> list) {
		setView(tabPosition, list, getSelectedItemPosition(tabPosition));
	}
	/**
	 * @param tabPosition
	 * @param list
	 * @param itemPosition
	 */
	public void setView(final int tabPosition, List<Entry<Integer, String>> list, int itemPosition) {//GridView
		if (configList == null || configList.size() <= 0) {
			Log.e(TAG, "setView(final int tabPostion, final List<Entry<Integer, String>> list, final int itemPosition) {" +
					" >> configList == null || configList.size() <= 0 >> return;");
			return;
		}
		GridPickerConfigBean gpcb = configList.get(tabPosition);
		if (gpcb == null) {
			return;
		}

		if (list == null || list.size() <= 0) {
			Log.e(TAG, "setView(final int tabPostion, final List<Entry<Integer, String>> list, final int itemPosition) {" +
					" >> list == null || list.size() <= 0 >> return;");
			return;
		}
		if (tabPosition >= MAX_NUM_TABS) {
			Log.e(TAG, "setView  tabPosition >= MAX_NUM_TABS,防止恶意添加标签导致数量过多选择困难甚至崩溃 >> return;");
			return;
		}

		if (itemPosition < 0) {
			itemPosition = 0;
		} else if (itemPosition >= list.size()) {
			itemPosition = list.size() - 1;
		}

		int numColumns = gpcb.getNumColumns();
		if (numColumns <= 0) {
			numColumns = 3;
		}

		int maxShowRows = gpcb.getMaxShowRows();
		if (maxShowRows <= 0) {
			maxShowRows = 5;
		}

		//Tabs<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		doOnItemSelected(tabPosition, itemPosition, list.get(itemPosition).getValue());

		//Tabs>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


		//gridView<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		adapter = new GridPickerAdapter(context, list, itemPosition, contentHeight/maxShowRows);
		adapter.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				currentSelectedItemName = adapter.getCurrentItemName();
				if (isOnLastTab() == false && onItemSelectedListener != null) {
					onItemSelectedListener.onItemSelected(parent, view, position, id);
					return;
				}
				doOnItemSelected(tabPosition, position, adapter.getCurrentItemName());
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});

		gvGridPickerView.setNumColumns(numColumns);
		gvGridPickerView.setAdapter(adapter);
		gvGridPickerView.smoothScrollToPosition(itemPosition);

		//gridView>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	}

	/**在onItemSelected时响应,
	 * 之后可能会跳转到下一个tab，导致 tabPositionWhenItemSelect+=1; selectedItemPosition = 0;
	 * @param tabPosition
	 * @param itemPosition
	 * @param itemName
	 */
	public void doOnItemSelected(int tabPosition, int itemPosition, String itemName) {
		currentTabPosition = tabPosition < getTabCount() ? tabPosition : getTabCount() - 1;
		currentSelectedItemPosition = itemPosition;
		currentSelectedItemName = StringUtil.getTrimedString(itemName);

		configList.set(currentTabPosition, configList.get(currentTabPosition).set(currentSelectedItemName, itemPosition));

		for (int i = 0; i < llGridPickerViewTabContainer.getChildCount(); i++) {
			((TextView) llGridPickerViewTabContainer.getChildAt(i)).setText("" + configList.get(i).getTabName());
			llGridPickerViewTabContainer.getChildAt(i).setBackgroundResource(
					i == tabPosition ? R.color.alpha_3 : R.color.alpha_complete);
		}
	}


}
