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

package zuo.biao.library.MODEL;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseFragmentActivity;
import zuo.biao.library.interfaces.OnPageReturnListener;
import zuo.biao.library.util.StringUtil;

/**使用方法：复制>粘贴>改名>改代码  */
/**fragmentActivity示例
 * @author Lemon
 * @use toActivity(ModelFragmentActivity.createIntent(...));
 */
public class ModelFragmentActivity extends BaseFragmentActivity implements OnClickListener, OnPageReturnListener {
	//	private static final String TAG = "ModelFragmentActivity";

	/**启动这个Activity的Intent
	 * @param context
	 * @param title
	 * @return
	 */
	public static Intent createIntent(Context context, String title) {
		return new Intent(context, ModelFragmentActivity.class).putExtra(INTENT_TITLE, title);
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.model_fragment_activity, this);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isActivityAlive = true;
		fragmentManager = getSupportFragmentManager();
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	private TextView tvModelFragmentActivityTitle;
	private View ivModelFragmentActivityForward;
	private ModelFragment modelFragment;
	//示例代码>>>>>>>>
	@Override
	public void initView() {//必须调用
		//示例代码<<<<<<<<
		tvModelFragmentActivityTitle = (TextView) findViewById(R.id.tvModelFragmentActivityTitle);
		ivModelFragmentActivityForward = findViewById(R.id.ivModelFragmentActivityForward);
		//示例代码>>>>>>>>
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	public static final String INTENT_USER_ID = "INTENT_USER_ID";
	//示例代码>>>>>>>>
	@Override
	public void initData() {

		//示例代码<<<<<<<<
		intent = getIntent();
		if (StringUtil.isNotEmpty(intent.getStringExtra(INTENT_TITLE), true)) {
			tvModelFragmentActivityTitle.setText("" + StringUtil.getCurrentString());
		}

		showShortToast("userId = " + intent.getLongExtra(INTENT_USER_ID, 0));

		modelFragment = new ModelFragment(intent.getLongExtra(INTENT_USER_ID, 0));
		fragmentManager
		.beginTransaction()
		.add(R.id.flModelFragmentActivityContainer, modelFragment)
		.show(modelFragment)
		.commit();
		//示例代码>>>>>>>>
	}

	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final int RESULT_MODEL = 30;
	@Override
	public void initListener() {//必须调用
		//示例代码<<<<<<<<<<<<<<<<<<<
		findViewById(R.id.tvModelFragmentActivityReturn).setOnClickListener(this);
		ivModelFragmentActivityForward.setOnClickListener(this);
		//示例代码>>>>>>>>>>>>>>>>>>>
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//示例代码<<<<<<<<<<<<<<<<<<<
	//	@Override
	//	public void onClick(View v) {
	//		switch (v.getId()) {
	//		case R.id.tvModelFragmentActivityReturn:
	//			onPageReturn();
	//			break;
	//		case R.id.ivModelFragmentActivityForward:
	//			modelFragment.changeListStyle();
	//			break;
	//		default:
	//			break;
	//		}
	//	}
	//Library内switch方法中case R.id.idx会报错
	@Override
	public void onClick(View v) {
		if (v.getId() ==  R.id.tvModelFragmentActivityReturn) {
			onPageReturn();
		} else if (v.getId() ==  R.id.ivModelFragmentActivityForward) {
			modelFragment.changeListStyle();
		}
	}
	//示例代码>>>>>>>>>>>>>>>>>>>





	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}