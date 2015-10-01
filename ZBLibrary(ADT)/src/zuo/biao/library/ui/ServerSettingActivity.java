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

import zuo.biao.library.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnPageReturnListener;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.StringUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**服务器设置activity
 * @author Lemon
 * @use toActivity(ServerSettingActivity.createIntent(...));
 */
public class ServerSettingActivity extends BaseActivity implements OnClickListener, OnPageReturnListener {
	//	private static final String TAG = "ServerSettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_NORMAL_ADDRESS = "INTENT_NORMAL_ADDRESS";
	public static final String INTENT_TEST_ADDRESS = "INTENT_TEST_ADDRESS";
	public static final String INTENT_SHARED_PREFERENCES_PATH = "INTENT_SHARED_PREFERENCES_PATH";
	public static final String INTENT_PATH_MODE = "INTENT_PATH_MODE";
	public static final String INTENT_NORMAL_KEY = "INTENT_NORMAL_KEY";
	public static final String INTENT_TEST_KEY = "INTENT_TEST_KEY";

	public static final String RESULT_NORMAL_ADDRESS = "RESULT_NORMAL_ADDRESS";
	public static final String RESULT_TEST_ADDRESS = "RESULT_TEST_ADDRESS";

	/**启动这个Activity的Intent
	 * 通过setResult返回结果,而不是直接在这个界面保存设置
	 * @param context
	 * @param normalAddress
	 * @param testAddress
	 * @return
	 */
	public static Intent createIntent(Context context, String normalAddress, String testAddress) {
		return createIntent(context, normalAddress, testAddress, null, 0, null, null);
	}
	/**启动这个Activity的Intent
	 * 只有一个服务器
	 * @param context
	 * @param address
	 * @param sharedPreferencesPath
	 * @param pathMode
	 * @param key
	 * @return
	 */
	public static Intent createIntent(Context context, String address, String sharedPreferencesPath, int pathMode, String key) {
		return createIntent(context, address, null, sharedPreferencesPath, pathMode, key, null);
	}
	/**启动这个Activity的Intent
	 * @param context
	 * @param normalAddress
	 * @param testAddress
	 * @param sharedPreferencesPath
	 * @param pathMode
	 * @param normalKey
	 * @param testKey
	 * @return
	 */
	public static Intent createIntent(Context context, String normalAddress, String testAddress
			, String sharedPreferencesPath, int pathMode, String normalKey, String testKey) {
		return new Intent(context, ServerSettingActivity.class).
				putExtra(INTENT_NORMAL_ADDRESS, normalAddress).
				putExtra(INTENT_TEST_ADDRESS, testAddress).
				putExtra(INTENT_SHARED_PREFERENCES_PATH, sharedPreferencesPath).
				putExtra(INTENT_PATH_MODE, pathMode).
				putExtra(INTENT_NORMAL_KEY, normalKey).
				putExtra(INTENT_TEST_KEY, testKey);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	private String normalAddress;
	private String testAddress;
	private String sharedPreferencesPath;
	private int pathMode = Context.MODE_PRIVATE;
	private String normalKey;
	private String testKey;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//model_activity改为你所需要的layout文件；传this是为了全局滑动返回
		setContentView(R.layout.server_setting_activity, this);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isActivityAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		intent = getIntent();
		normalAddress = intent.getStringExtra(INTENT_NORMAL_ADDRESS);
		testAddress = intent.getStringExtra(INTENT_TEST_ADDRESS);
		sharedPreferencesPath = intent.getStringExtra(INTENT_SHARED_PREFERENCES_PATH);
		pathMode = intent.getIntExtra(INTENT_PATH_MODE, pathMode);
		normalKey = intent.getStringExtra(INTENT_NORMAL_KEY);
		testKey = intent.getStringExtra(INTENT_TEST_KEY);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	private EditText etServerSettingNormal;
	private EditText etServerSettingTest;
	@Override
	public void initView() {//必须调用

		etServerSettingNormal = (EditText) findViewById(R.id.etServerSettingNormal);
		etServerSettingTest = (EditText) findViewById(R.id.etServerSettingTest);

	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用

		//获取并显网址
		etServerSettingNormal.setText(StringUtil.getNoBlankString(getIntent().getStringExtra(INTENT_NORMAL_ADDRESS)));
		etServerSettingTest.setText(StringUtil.getNoBlankString(getIntent().getStringExtra(INTENT_TEST_ADDRESS)));
	}


	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须调用

		findViewById(R.id.tvServerSettingReturn).setOnClickListener(this);
		findViewById(R.id.tvServerSettingForward).setOnClickListener(this);

		findViewById(R.id.tvServerSettingNormalSet).setOnClickListener(this);
		findViewById(R.id.tvServerSettingNormalOpen).setOnClickListener(this);

		findViewById(R.id.tvServerSettingTestSet).setOnClickListener(this);
		findViewById(R.id.tvServerSettingTestOpen).setOnClickListener(this);

	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvServerSettingReturn:
			onPageReturn();
			break;
		case R.id.tvServerSettingForward:
			etServerSettingNormal.setText(StringUtil.getTrimedString(normalAddress));
			etServerSettingTest.setText(StringUtil.getTrimedString(testAddress));
			break;

		case R.id.tvServerSettingNormalSet:
			if (StringUtil.isNotEmpty(sharedPreferencesPath, true) && StringUtil.isNotEmpty(normalKey, true)) {
				DataKeeper.save(context, sharedPreferencesPath, pathMode, normalKey
						, StringUtil.getNoBlankString(etServerSettingNormal));
			} else {
				setResult(RESULT_OK, new Intent().putExtra(RESULT_NORMAL_ADDRESS
						, StringUtil.getNoBlankString(etServerSettingNormal)));
			}
			onPageReturn();
			break;
		case R.id.tvServerSettingTestSet:
			if (StringUtil.isNotEmpty(sharedPreferencesPath, true) && StringUtil.isNotEmpty(testKey, true)) {
				DataKeeper.save(context, sharedPreferencesPath, pathMode, testKey
						, StringUtil.getNoBlankString(etServerSettingTest));
			} else {
				setResult(RESULT_OK, new Intent().putExtra(RESULT_TEST_ADDRESS
						, StringUtil.getNoBlankString(etServerSettingTest)));
			}
			onPageReturn();
			break;

		case R.id.tvServerSettingNormalOpen:
			toActivity(WebViewActivity.createIntent(context, "正式服务器", StringUtil.getNoBlankString(etServerSettingNormal)));
			break;
		case R.id.tvServerSettingTestOpen:
			toActivity(WebViewActivity.createIntent(context, "正式服务器", StringUtil.getNoBlankString(etServerSettingTest)));
			break;
		default:
			break;
		}
	}



	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}