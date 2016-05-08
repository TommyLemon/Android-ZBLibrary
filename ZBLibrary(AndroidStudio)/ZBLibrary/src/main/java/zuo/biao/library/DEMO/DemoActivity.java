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
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.bean.Entry;
import zuo.biao.library.interfaces.OnFinishListener;
import zuo.biao.library.util.StringUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**使用方法：复制>粘贴>改名>改代码  */
/**activity示例；如果是FragmentActivity应该继承BaseFragmentActivity
 * @author Lemon
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use toActivity(DemoActivity.createIntent(...));
 */
public class DemoActivity extends BaseActivity implements OnClickListener, OnFinishListener {
	private static final String TAG = "DemoActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_USER_ID = "INTENT_USER_ID";

	public static final String RESULT_CLICKED_ITEM = "RESULT_CLICKED_ITEM";
	
	/**启动这个Activity的Intent
	 * @param context
	 * @param userId
	 * @return
	 */
	public static Intent createIntent(Context context, long userId) {
		return new Intent(context, DemoActivity.class).putExtra(INTENT_USER_ID, userId);
	}
	
	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	private long userId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO demo_activity改为你所需要的layout文件；传this是为了全局滑动返回
		setContentView(R.layout.demo_activity, this);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		intent = getIntent();
		userId = intent.getLongExtra(INTENT_USER_ID, userId);
		
		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	private TextView tvDemoTitle;
	private ListView lvDemo;
	//示例代码>>>>>>>>
	@Override
	public void initView() {//必须在onCreate方法内调用

		//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		tvDemoTitle = (TextView) findViewById(R.id.tvDemoTitle);

		lvDemo = (ListView) findViewById(R.id.lvDemo);

		//示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	}

	//示例代码<<<<<<<<
	private DemoAdapter adapter;
	//示例代码>>>>>>>>
	/** 示例方法 ：显示列表内容
	 * @author author
	 * @param list
	 */
	private void setList(List<Entry<String, String>> list) {
		if (list == null || list.size() <= 0) {
			Log.i(TAG, "setList list == null || list.size() <= 0 >> lvDemo.setAdapter(null); return;");
			adapter = null;
			lvDemo.setAdapter(null);
			return;
		}

		if (adapter == null) {
			adapter = new DemoAdapter(context, list);
			lvDemo.setAdapter(adapter);
		} else {
			adapter.refresh(list);
		}
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	private List<Entry<String, String>> list;
	//示例代码>>>>>>>>>
	@Override
	public void initData() {//必须在onCreate方法内调用

		//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		if (StringUtil.isNotEmpty(getIntent().getStringExtra(INTENT_TITLE), false)) {
			tvDemoTitle.setText(StringUtil.getCurrentString());
		}
		
		showProgressDialog(R.string.loading);

		runThread(TAG + "initData", new Runnable() {
			@Override
			public void run() {

				list = getList(userId);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (isAlive == true) {//isAlive已在BaseActivity中新建
							dismissProgressDialog();
							setList(list);
						}
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
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		for (int i = 0; i < 64; i++) {
			list.add(new Entry<String, String>("联系人" + i + userId, String.valueOf(1311736568 + i*i + userId)));
		}
		return list;
	}


	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须在onCreate方法内调用
		//示例代码<<<<<<<<<<<<<<<<<<<
		findViewById(R.id.tvDemoReturn).setOnClickListener(this);
		findViewById(R.id.tvDemoForward).setOnClickListener(this);

		lvDemo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				setResult(RESULT_OK, new Intent().putExtra(RESULT_CLICKED_ITEM, position));
				finish();
			}
		});
		//示例代码>>>>>>>>>>>>>>>>>>>
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//示例代码<<<<<<<<<<<<<<<<<<<
	//	@Override
	//	public void onClick(View v) {
	//		switch (v.getId()) {
	//			case R.id.tvDemoReturn:
	//				finish();
	//				break;
	//			case R.id.ivDemoForward:
	//				toActivity(WebViewActivity.createIntent(context, "了解", "www.baidu.com"));
	//				break;
	//			default:
	//				break;
	//		}
	//	}
	//Library内switch方法中case R.id.idx会报错
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvDemoReturn) {
			finish();
		} else if (v.getId() == R.id.tvDemoForward) {
			int formerCout = adapter == null ? 0 : adapter.getCount() - 1;

			userId = 2 * (userId + 1);
			if (list == null) {
				list = new ArrayList<>();
			}
			list.addAll(getList(userId));
			adapter.refresh(list);

			lvDemo.smoothScrollToPosition(formerCout);
		}
	}
	//示例代码>>>>>>>>>>>>>>>>>>>


	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}