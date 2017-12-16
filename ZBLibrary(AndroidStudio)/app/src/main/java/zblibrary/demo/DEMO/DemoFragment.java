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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zblibrary.demo.R;
import zblibrary.demo.activity_fragment.UserActivity;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.model.Entry;


/** 使用方法：复制>粘贴>改名>改代码 */
/**fragment示例
 * @author Lemon
 * @use new DemoFragment(),具体参考.DemoFragmentActivity(initData方法内)
 */
public class DemoFragment extends BaseFragment {
	private static final String TAG = "DemoFragment";

	//与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String ARGUMENT_USER_ID = "ARGUMENT_USER_ID";
	public static final String ARGUMENT_USER_NAME = "ARGUMENT_USER_NAME";

	/**创建一个Fragment实例
	 * @param userId
	 * @return
	 */
	public static DemoFragment createInstance(long userId) {
		return createInstance(userId, null);
	}
	/**创建一个Fragment实例
	 * @param userId
	 * @param userName
	 * @return
	 */
	public static DemoFragment createInstance(long userId, String userName) {
		DemoFragment fragment = new DemoFragment();

		Bundle bundle = new Bundle();
		bundle.putLong(ARGUMENT_USER_ID, userId);
		bundle.putString(ARGUMENT_USER_NAME, userName);

		fragment.setArguments(bundle);
		return fragment;
	}

	//与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	private long userId = 0;
	private String userName = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		//TODO demo_fragment改为你所需要的layout文件
		setContentView(R.layout.demo_fragment);

		argument = getArguments();
		if (argument != null) {
			userId = argument.getLong(ARGUMENT_USER_ID, userId);
			userName = argument.getString(ARGUMENT_USER_NAME, userName);
		}

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		return view;//返回值必须为view
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	private ListView lvDemoFragment;
	//示例代码>>>>>>>>
	@Override
	public void initView() {//必须在onCreateView方法内调用

		//示例代码<<<<<<<<<<<<<<
		lvDemoFragment = findView(R.id.lvDemoFragment);
		//示例代码>>>>>>>>>>>>>>
	}

	//示例代码<<<<<<<<
	private DemoAdapter adapter;
	//示例代码>>>>>>>>
	/** 示例方法 ：显示列表内容
	 * @author author
	 * @param list
	 */
	private void setList(List<Entry<String, String>> list) {
		if (adapter == null) {
			adapter = new DemoAdapter(context);
			lvDemoFragment.setAdapter(adapter);
		}
		adapter.refresh(list);
	}

	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	private List<Entry<String, String>> list;
	//示例代码>>>>>>>>>
	@Override
	public void initData() {//必须在onCreateView方法内调用

		//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		showShortToast(TAG + ": userId = " + userId + "; userName = " + userName);

		showProgressDialog(R.string.loading);

		runThread(TAG + "initData", new Runnable() {
			@Override
			public void run() {

				list = getList(userId);
				runUiThread(new Runnable() {
					@Override
					public void run() {
						dismissProgressDialog();
						setList(list);
					}
				});
			}
		});

		//示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	}


	/**示例方法：获取号码列表
	 * @author lemon
	 * @param userId
	 * @return
	 */
	protected List<Entry<String, String>> getList(long userId) {
		list = new ArrayList<Entry<String, String>>();
		for (int i = 0; i < 64; i++) {
			list.add(new Entry<String, String>("联系人" + i , String.valueOf(1311736568 + i*i)));
		}
		return list;
	}


	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须在onCreateView方法内调用
		//示例代码<<<<<<<<<<<<<<<<<<<

		lvDemoFragment.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				toActivity(UserActivity.createIntent(context, position));//一般用id，这里position仅用于测试 id));//
			}
		});
		//示例代码>>>>>>>>>>>>>>>>>>>
	}

	//系统自带监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}