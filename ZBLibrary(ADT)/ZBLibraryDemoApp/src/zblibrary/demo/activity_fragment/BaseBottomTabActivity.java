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

package zblibrary.demo.activity_fragment;

import zblibrary.demo.R;
import zuo.biao.library.base.BaseActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**基础底部标签Activity
 * @author Lemon
 * @use extends BaseBottomTabActivity
 */
public abstract class BaseBottomTabActivity extends BaseActivity {
	private static final String TAG = "BaseBottomTabActivity";



	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	protected static int[] tabIds;
	
	protected View[] vTabs;
	@Override
	public void initView() {// 必须调用

		tabIds = getTabIds();
		
		vTabs = new View[tabIds.length];
		for (int i = 0; i < tabIds.length; i++) {
			vTabs[i] = findViewById(tabIds[i]);
		}

	}


	/**选择tab，在selectFragment里被调用
	 * @param position
	 */
	protected abstract void selectTab(int position);

	protected int currentPosition = 0;
	/**选择并显示fragment
	 * @param position
	 */
	public void selectFragment(int position) {
		if (currentPosition == position) {
			if (fragments[position] != null && fragments[position].isVisible()) {
				Log.e(TAG, "selectFragment currentPosition == position" +
						" >> fragments[position] != null && fragments[position].isVisible()" +
						" >> return;	");
				return;
			}
		}

		selectTab(position);

		if (fragments[position] == null) {
			fragments[position] = getFragment(position);
		}

		// 用全局的fragmentTransaction因为already committed 崩溃
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.hide(fragments[currentPosition]);
		if (fragments[position].isAdded() == false) {
			fragmentTransaction.add(R.id.flBottomTabFragmentContainer, fragments[position]);
		}
		fragmentTransaction.show(fragments[position]).commit();

		this.currentPosition = position;
	};


	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




	protected Fragment[] fragments;
	@Override
	public void initData() {// 必须调用


		// fragmentActivity子界面初始化<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		fragments = new Fragment[tabIds.length];
		selectFragment(currentPosition);

		// fragmentActivity子界面初始化>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	}
	
	
	/**获取tab的id
	 * @param position
	 * @return
	 */
	protected abstract int[] getTabIds();

	/**获取新的Fragment
	 * @param position
	 * @return
	 */
	protected abstract Fragment getFragment(int position);

	/**获取Tab(或Fragment)的数量
	 * @return
	 */
	public int getCount() {
		return tabIds == null ? 0 : tabIds.length;
	}

	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {// 必须调用

		for (int i = 0; i < vTabs.length; i++) {
			final int which = i;
			vTabs[which].setOnClickListener(new OnClickListener() {

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


	// listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	// 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}