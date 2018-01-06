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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import zblibrary.demo.DEMO.DemoTabFragment;
import zblibrary.demo.R;
import zuo.biao.library.base.BaseBottomTabActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;

/**应用主页
 * @author Lemon
 * @use MainTabActivity.createIntent(...)
 */
public class MainTabActivity extends BaseBottomTabActivity implements OnBottomDragListener {
	//	private static final String TAG = "MainTabActivity";


	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, MainTabActivity.class);
	}


	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	public Activity getActivity() {
		return this; //必须return this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_activity, this);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>
	}



	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	private DemoTabFragment demoTabFragment;
	@Override
	public void initView() {// 必须调用
		super.initView();
		exitAnim = R.anim.bottom_push_out;

		demoTabFragment = DemoTabFragment.createInstance("杭州");
	}


	@Override
	protected int[] getTabClickIds() {
		return new int[]{R.id.llBottomTabTab0, R.id.llBottomTabTab1, R.id.llBottomTabTab2, R.id.llBottomTabTab3};
	}

	@Override
	protected int[][] getTabSelectIds() {
		return new int[][]{
				new int[]{R.id.ivBottomTabTab0, R.id.ivBottomTabTab1, R.id.ivBottomTabTab2, R.id.ivBottomTabTab3},//顶部图标
				new int[]{R.id.tvBottomTabTab0, R.id.tvBottomTabTab1, R.id.tvBottomTabTab2, R.id.tvBottomTabTab3}//底部文字
		};
	}

	@Override
	public int getFragmentContainerResId() {
		return R.id.flMainTabFragmentContainer;
	}

	@Override
	protected Fragment getFragment(int position) {
		switch (position) {
		case 1:
			return UserRecyclerFragment.createInstance(UserListFragment.RANGE_RECOMMEND);
		case 2:
			return demoTabFragment;
		case 3:
			return SettingFragment.createInstance();
		default:
			return UserListFragment.createInstance(UserListFragment.RANGE_ALL);
		}
	};

	private static final String[] TAB_NAMES = {"主页", "消息", "发现", "设置"};
	@Override
	protected void selectTab(int position) {
		//导致切换时闪屏，建议去掉BottomTabActivity中的topbar，在fragment中显示topbar
		//		rlBottomTabTopbar.setVisibility(position == 2 ? View.GONE : View.VISIBLE);

		tvBaseTitle.setText(TAB_NAMES[position]);

		//点击底部tab切换顶部tab，非必要
		if (position == 2 && position == currentPosition && demoTabFragment != null) {
			demoTabFragment.selectNext();
		}
	}


	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




	@Override
	public void initData() {// 必须调用
		super.initData();

	}



	// Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {// 必须调用
		super.initEvent();

	}

	@Override
	public void onDragBottom(boolean rightToLeft) {
		//将Activity的onDragBottom事件传递到Fragment，非必要<<<<<<<<<<<<<<<<<<<<<<<<<<<
		switch (currentPosition) {
		case 2:
			if (demoTabFragment != null) {
				if (rightToLeft) {
					demoTabFragment.selectMan();
				} else {
					demoTabFragment.selectPlace();
				}
			}
			break;
		default:
			break;
		}
		//将Activity的onDragBottom事件传递到Fragment，非必要>>>>>>>>>>>>>>>>>>>>>>>>>>>
	}


	// 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//双击手机返回键退出<<<<<<<<<<<<<<<<<<<<<
	private long firstTime = 0;//第一次返回按钮计时
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			long secondTime = System.currentTimeMillis();
			if(secondTime - firstTime > 2000){
				showShortToast("再按一次退出");
				firstTime = secondTime;
			} else {//完全退出
				moveTaskToBack(false);//应用退到后台
				System.exit(0);
			}
			return true;
		}

		return super.onKeyUp(keyCode, event);
	}
	//双击手机返回键退出>>>>>>>>>>>>>>>>>>>>>

	// 类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// 类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// 系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	// Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	// 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}