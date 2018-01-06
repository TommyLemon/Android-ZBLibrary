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

package zblibrary.demo.DEMO;

import zblibrary.demo.R;
import zuo.biao.library.base.BaseTabActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.ui.WebViewActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


/** 使用方法：复制>粘贴>改名>改代码 */
/**带标签的Activity示例
 * @author Lemon
 * @use toActivity(DemoTabActivity.createIntent(...));
 */
public class DemoTabActivity extends BaseTabActivity implements OnClickListener, OnBottomDragListener {
	//	private static final String TAG = "DemoTabActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, DemoTabActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	public Activity getActivity() {
		return this; //必须return this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, this);

		//		needReload = true;

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	//示例代码>>>>>>>>
	@Override
	public void initView() {//必须在onCreate方法内调用
		super.initView();

		//示例代码<<<<<<<<
		addTopRightButton(newTopRightImageView(context, R.drawable.add_small)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showShortToast("添加");
			}
		});
		//示例代码>>>>>>>>
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须在onCreate方法内调用
		super.initData();

	}

	@Override
	@Nullable
	public String getTitleName() {
		return "账单";
	}

	@Override
	@Nullable
	public String getReturnName() {
		return "";
	}

	@Override
	@Nullable
	public String getForwardName() {
		return "了解";
	}

	@Override
	protected String[] getTabNames() {
		return new String[] {"全部", "收入", "支出"};
	}

	@Override
	protected Fragment getFragment(int position) {
		//示例代码<<<<<<<<<<<<<<<<<<
		DemoListFragment fragment = DemoListFragment.createInstance();
		Bundle bundle = fragment.getArguments();
		if (bundle == null) {
			bundle = new Bundle();
		}
		bundle.putInt(DemoListFragment.ARGUMENT_POSITION, position);
		fragment.setArguments(bundle);
		//示例代码>>>>>>>>>>>>>>>>>>
		return fragment;
	}



	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须在onCreate方法内调用
		super.initEvent();
		topTabView.setOnTabSelectedListener(this);//覆盖super.initEvent();内的相同代码

		//示例代码:自动切换tab一个周期
		for (int i = 0; i < getCount(); i++) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					if (isRunning()) {
						selectNext();
					}
				}
			}, 1000 * (i + 1));
		}
	}

	@Override
	public void onDragBottom(boolean rightToLeft) {
		//示例代码<<<<<<<<<<<<<<<<<<
		if (rightToLeft) {
			toActivity(WebViewActivity.createIntent(context, "百度首页", "www.baidu.com"));
			return;
		}

		finish();
		//示例代码>>>>>>>>>>>>>>>>>>
	}

	@Override
	public void onTabSelected(TextView tvTab, int position, int id) {
		super.onTabSelected(tvTab, position, id);
		showShortToast("onTabSelected  position = " + position);
	}

	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}