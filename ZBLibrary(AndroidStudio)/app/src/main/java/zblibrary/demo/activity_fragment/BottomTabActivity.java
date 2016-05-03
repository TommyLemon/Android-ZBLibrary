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
import zuo.biao.library.DEMO.DemoFragment;
import zuo.biao.library.DEMO.DemoTabFragment;
import zuo.biao.library.base.BaseFragmentActivity;
import zuo.biao.library.interfaces.OnFinishListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**应用主页
 * @author Lemon
 * @use BottomTabActivity.createIntent(...)
 */
public class BottomTabActivity extends BaseFragmentActivity implements OnFinishListener {
	private static final String TAG = "BottomTabActivity";


	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, BottomTabActivity.class);
	}


	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	/**
	 * 每次点击相应tab都加载，调用getFragment方法重新对点击的tab对应的fragment赋值。
	 * 如果不希望重载，可以setOnTabSelectedListener，然后在onTabSelected内重写点击tab事件。
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottom_tab_activity, this);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isAlive = true;
		fragmentManager = getSupportFragmentManager();
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>
	}



	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@SuppressWarnings("unused")
	private View rlBottomTabTopbar;
	private TextView tvBottomTabTitle;	
	private View[] llBottomTabTabs;
	@Override
	public void initView() {// 必须调用
		exitAnim = R.anim.bottom_push_out;

		rlBottomTabTopbar = findViewById(R.id.rlBottomTabTopbar);
		tvBottomTabTitle = (TextView) findViewById(R.id.tvBottomTabTitle);

		llBottomTabTabs = new View[4];
		llBottomTabTabs[0] = findViewById(R.id.llBottomTabTab0);
		llBottomTabTabs[1] = findViewById(R.id.llBottomTabTab1);
		llBottomTabTabs[2] = findViewById(R.id.llBottomTabTab2);
		llBottomTabTabs[3] = findViewById(R.id.llBottomTabTab3);

	}

	/**获取新的Fragment
	 * @param position
	 * @return
	 */
	protected Fragment getFragment(int position) {
		bundle = new Bundle();
		switch (position) {
		case 1:
			return new DemoFragment();
		case 2:
			return new DemoTabFragment();
		case 3:
			return new SettingFragment();
		default:
			UserListFragment fragment = new UserListFragment();
			bundle.putInt(UserListFragment.ARGUMENT_RANGE, UserListFragment.RANGE_ALL);
			return fragment;
		}
	};


	private static final String[] TABS = {"主页", "消息", "发现", "设置"};

	/**选择并显示fragment
	 * @param position
	 */
	public void selectFragment(int position) {
		if (currentPosition == position) {
			Log.i(TAG, "onSelectFragment currentPosition == position >> return;	");
			return;
		}

		//导致切换时闪屏，建议去掉BottomTabActivity中的topbar，在fragment中显示topbar
		//		rlBottomTabTopbar.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
		
		tvBottomTabTitle.setText(TABS[position]);

		for (int i = 0; i < llBottomTabTabs.length; i++) {
			llBottomTabTabs[i].setBackgroundResource(i == position ? R.color.topbar_bg : R.color.white);
		}

		if (fragments[position] == null) {
			fragments[position] = getFragment(position);
		}

		// 用全局的fragmentTransaction因为already committed 崩溃
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.hide(fragments[currentPosition]);
		if (!fragments[position].isAdded()) {
			fragmentTransaction.add(R.id.flBottomTabFragmentContainer, fragments[position]);
		}
		fragmentTransaction.show(fragments[position]).commit();

		this.currentPosition = position;
	};


	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private Fragment[] fragments;
	@Override
	public void initData() {// 必须调用


		// fragmentActivity子界面初始化<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		fragments = new Fragment[getCount()];
		fragments[currentPosition] = getFragment(currentPosition);
		fragmentManager
		.beginTransaction()
		.add(R.id.flBottomTabFragmentContainer, fragments[currentPosition])
		.show(fragments[currentPosition])
		.commit();

		// fragmentActivity子界面初始化>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	}

	public int getCount() {
		return 4;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}


	public Fragment getCurrentFragment() {
		return fragments[currentPosition];
	};



	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	protected int currentPosition = 0;
	@Override
	public void initListener() {// 必须调用

		for (int i = 0; i < llBottomTabTabs.length; i++) {
			final int which = i;
			llBottomTabTabs[which].setOnClickListener(new OnClickListener() {

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