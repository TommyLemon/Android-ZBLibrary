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

package zuo.biao.library.DEMO;

import java.util.ArrayList;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseTabFragment;
import zuo.biao.library.ui.PlacePickerWindow;
import zuo.biao.library.util.PlaceUtil;
import zuo.biao.library.util.StringUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**使用方法：复制>粘贴>改名>改代码  */
/**带标签的Fragment示例
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @author Lemon
 * @use new DemoTabFragment(),具体参考.DemoFragmentActivity(initData方法内)
 */
public class DemoTabFragment extends BaseTabFragment implements OnClickListener {
	//	private static final String TAG = "DemoTabFragment";

	//与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	public static final String ARGUMENT_CITY = "ARGUMENT_CITY";


	//与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	private String city;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState, R.layout.demo_tab_fragment);
		//		needReload = true;

		argument = getArguments();
		if (argument != null) {
			city = argument.getString(ARGUMENT_CITY);
		}

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

		return view;
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView tvDemoTabLeft;
	@Override
	public void initView() {//必须在onCreate方法内调用
		super.initView();

		tvDemoTabLeft = (TextView) findViewById(R.id.tvDemoTabLeft);
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须在onCreate方法内调用
		super.initData();

		tvDemoTabLeft.setText(StringUtil.isNotEmpty(city, true) ? StringUtil.getTrimedString(city) : "杭州");
	}

	@Override
	@Nullable
	protected String getTitleName() {
		return null;
	}

	@Override
	@Nullable
	protected String getTopReturnButtonName() {
		return null;
	}

	@Override
	protected String[] getTabNames() {
		return new String[] {"附近", "热门"};
	}

	@Override
	protected Fragment getFragment(int position) {
		DemoFragment fragment = new DemoFragment();
		bundle = new Bundle();
		bundle.putLong(DemoFragment.ARGUMENT_USER_ID, position);
		fragment.setArguments(bundle);

		return fragment;
	}



	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须在onCreate方法内调用
		super.initListener();

		tvDemoTabLeft.setOnClickListener(this);
		findViewById(R.id.tvDemoTabRight).setOnClickListener(this);
	}


	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//	@Override
	//	public void onClick(View v) {
	//		switch (v.getId()) {
	//		case R.id.tvDemoTabLeft:
	//			toActivity(PlacePickerWindow.createIntent(context, context.getPackageName(), 1, 2)
	//					, REQUEST_TO_PLACE_PICKER, false);
	//			break;
	//		case R.id.tvDemoTabRight:
	//			showShortToast(StringUtil.getTrimedString((TextView) v));
	//			break;
	//		default:
	//			super.onClick(v);
	//		}
	//	}
	//Library内switch方法中case R.id.idx:报错
	@Override
	public void onClick(View v) {//直接调用不会显示v被点击效果
		if (v.getId() == R.id.tvDemoTabLeft) {
			toActivity(PlacePickerWindow.createIntent(context, context.getPackageName(), 2)
					, REQUEST_TO_PLACE_PICKER, false);
		} else if (v.getId() == R.id.tvDemoTabRight) {
			showShortToast(StringUtil.getTrimedString((TextView) v));
		}
	}


	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private static final int REQUEST_TO_PLACE_PICKER = 10;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case REQUEST_TO_PLACE_PICKER:
			if (data != null) {
				ArrayList<String> placeList = data.getStringArrayListExtra(PlacePickerWindow.RESULT_PLACE_LIST);
				if (placeList != null && placeList.size() > PlaceUtil.LEVEL_CITY) {
					tvDemoTabLeft.setText(StringUtil.getTrimedString(placeList.get(PlaceUtil.LEVEL_CITY)));
				}
			}
			break;
		default:
			break;
		}
	}
	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}