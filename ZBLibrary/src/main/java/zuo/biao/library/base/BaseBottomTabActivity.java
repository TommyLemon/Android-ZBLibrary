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

package zuo.biao.library.base;

import zuo.biao.library.util.Log;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

/**基础底部标签Activity
 * @author Lemon
 * @use extends BaseBottomTabActivity
 */
public abstract class BaseBottomTabActivity extends BaseActivity {
	private static final String TAG = "BaseBottomTabActivity";



	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	protected static int[] tabClickIds;

	protected View[] vTabClickViews;
	protected View[][] vTabSelectViews;
	@Override
	public void initView() {// 必须调用

		tabClickIds = getTabClickIds();

		vTabClickViews = new View[getCount()];
		for (int i = 0; i < getCount(); i++) {
			vTabClickViews[i] = findView(tabClickIds[i]);
		}

		int[][] tabSelectIds = getTabSelectIds();
		if (tabSelectIds != null && tabSelectIds.length > 0) {
			vTabSelectViews = new View[tabSelectIds.length][getCount()];
			for (int i = 0; i < tabSelectIds.length; i++) {
				if (tabSelectIds[i] != null) {
					for (int j = 0; j < tabSelectIds[i].length; j++) {
						vTabSelectViews[i][j] = findView(tabSelectIds[i][j]);
					}
				}
			}
		}
	}


	/**选择tab，在selectFragment里被调用
	 * @param position
	 */
	protected abstract void selectTab(int position);

	/**设置选中状态
	 * @param position 
	 */
	protected void setTabSelection(int position) {
		if (vTabSelectViews == null) {
			Log.e(TAG, "setTabSelection  vTabSelectViews == null >> return;");
			return;
		}
		for (int i = 0; i < vTabSelectViews.length; i++) {
			if (vTabSelectViews[i] == null) {
				Log.w(TAG, "setTabSelection  vTabSelectViews[" + i + "] == null >> continue;");
				continue;
			}
			for (int j = 0; j < vTabSelectViews[i].length; j++) {
				vTabSelectViews[i][j].setSelected(j == position);
			}
		}
	}

	/**
	 *  == true >> 每次点击相应tab都加载，调用getFragment方法重新对点击的tab对应的fragment赋值。
	 * 如果不希望重载，可以重写selectFragment。
	 */
	protected boolean needReload = false;
	/**
	 * 当前显示的tab所在位置，对应fragment所在位置
	 */
	protected int currentPosition = 0;
	/**选择并显示fragment
	 * @param position
	 */
	public void selectFragment(int position) {
		//tab，资源消耗很小<<<<<<
		setTabSelection(position);
		selectTab(position);
		//tab，资源消耗很小>>>>>>
		
		if (currentPosition == position) {
			if (needReload) {
				if (fragments[position] != null && fragments[position].isAdded()) {
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.remove(fragments[position]).commit();
					fragments[position] = null;
				}
			} else {
				if (fragments[position] != null && fragments[position].isVisible()) {
					Log.w(TAG, "selectFragment currentPosition == position" +
							" >> fragments[position] != null && fragments[position].isVisible()" +
							" >> return;	");
					return;
				}
			}
		}
		

		if (fragments[position] == null) {
			fragments[position] = getFragment(position);
		}

		//全局的fragmentTransaction因为already committed 崩溃
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.hide(fragments[currentPosition]);
		if (fragments[position].isAdded() == false) {
			ft.add(getFragmentContainerResId(), fragments[position]);
		}
		ft.show(fragments[position]).commit();

		this.currentPosition = position;
	}


	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	protected Fragment[] fragments;
	@Override
	public void initData() {// 必须调用

		// fragmentActivity子界面初始化<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		fragments = new Fragment[getCount()];
		selectFragment(currentPosition);

		// fragmentActivity子界面初始化>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	}


	/**获取tab内设置点击事件的View的id
	 * @param position
	 * @return
	 */
	protected abstract int[] getTabClickIds();

	/**获取tab内设置选择事件的View的id，setSelected(position == currentPositon)
	 * @return
	 * @warn 返回int[leghth0][leghth1]必须满足leghth0 >= 1 && leghth1 = getCount() = getTabClickIds().length
	 */
	protected abstract int[][] getTabSelectIds();

	/**获取Fragment容器的id
	 * @return
	 */
	public abstract int getFragmentContainerResId();
	
	/**获取新的Fragment
	 * @param position
	 * @return
	 */
	protected abstract Fragment getFragment(int position);

	/**获取Tab(或Fragment)的数量
	 * @return
	 */
	public int getCount() {
		return tabClickIds == null ? 0 :tabClickIds.length;
	}

	// Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {// 必须调用

		for (int i = 0; i < vTabClickViews.length; i++) {
			final int which = i;
			vTabClickViews[which].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					selectFragment(which);
				}
			});
		}
	}

	// 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// 类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// 类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// 系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	// Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	// 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}