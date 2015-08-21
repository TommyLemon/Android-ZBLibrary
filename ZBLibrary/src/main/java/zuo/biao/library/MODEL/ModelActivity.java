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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.bean.KeyValueBean;
import zuo.biao.library.interfaces.OnPageReturnListener;
import zuo.biao.library.util.StringUtil;

/**使用方法：复制>粘贴>改名>改代码  */
/**activity示例；如果是FragmentActivity应该继承BaseFragmentActivity
 * @author Lemon
 * @use toActivity(ModelActivity.createIntent(...));
 */
public class ModelActivity extends BaseActivity implements OnClickListener, OnPageReturnListener {
	private static final String TAG = "ModelActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @param title
	 * @return
	 */
	public static Intent createIntent(Context context, String title) {
		return new Intent(context, ModelActivity.class).putExtra(INTENT_TITLE, title);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.model_activity, this);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isActivityAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<

	private TextView tvModelTitle;
	private View ivModelForward;
	private ListView lvModel;

	private ScaleAnimation rollingOverAnim0 = new ScaleAnimation(1, 0, 1, 1,
			Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
			0.5f);
	private ScaleAnimation rollingOverAnim1 = new ScaleAnimation(0, 1, 1, 1,
			Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
			0.5f);
	//示例代码>>>>>>>>
	@Override
	public void initView() {//必须调用

		//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		tvModelTitle = (TextView) findViewById(R.id.tvModelTitle);
		ivModelForward = findViewById(R.id.ivModelForward);

		lvModel = (ListView) findViewById(R.id.lvModel);

		rollingOverAnim0.setDuration(200);
		rollingOverAnim1.setDuration(200);

		//示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	}

	//示例代码<<<<<<<<
	private ModelAdapter adapter;
	//示例代码>>>>>>>>
	/** 示例方法 ：显示列表内容
	 * @author author
	 * @param list
	 */
	private void setList(List<KeyValueBean> list) {
		if (list == null || list.size() <= 0) {
			Log.i(TAG, "setList list == null || list.size() <= 0 >> lvModel.setAdapter(null); return;");
			adapter = null;
			lvModel.setAdapter(null);
			return;
		}

		if (adapter == null) {
			adapter = new ModelAdapter(context, list);
			lvModel.setAdapter(adapter);
		} else {
			adapter.refresh(list);
		}
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	private long userId;
	private List<KeyValueBean> list;
	//示例代码>>>>>>>>>
	@Override
	public void initData() {//必须调用

		//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		if (StringUtil.isNotEmpty(getIntent().getStringExtra(INTENT_TITLE), false)) {
			tvModelTitle.setText("" + StringUtil.getCurrentString());
		}

		showProgressDialog(R.string.loading);

		userId = getIntent().getLongExtra("userId", 0);

		runThread(TAG + "initData", new Runnable() {//runnable已在baseFragment中新建
			@Override
			public void run() {

				list = getContactList(userId);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (isActivityAlive == true) {//isActivityAlive已在baseFragment中新建
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
	protected List<KeyValueBean> getContactList(long userId) {
		list = new ArrayList<KeyValueBean>();
		for (int i = 0; i < 64; i++) {
			list.add(new KeyValueBean("联系人" + i , String.valueOf(1311736568 + i*i)));
		}
		return list;
	}


	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须调用
		//示例代码<<<<<<<<<<<<<<<<<<<
		findViewById(R.id.tvModelReturn).setOnClickListener(this);
		ivModelForward.setOnClickListener(this);

		lvModel.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position > 10) {
					setResult(RESULT_OK, new Intent().putExtra(RESULT_CLICKED_ITEM, position));
					finish();
				} else {
					toActivity(ModelFragmentActivity.createIntent(
							context, adapter.getItem(position).getKey()), REQUEST_TO_MODEL);
				}
			}
		});
		//示例代码>>>>>>>>>>>>>>>>>>>
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//示例代码<<<<<<<<<<<<<<<<<<<
	//@Override
	//public void onClick(View v) {
	//	switch (v.getId()) {
	//		case R.id.tvModelReturn:
	//			onPageReturn();
	//			break;
	//		case R.id.ivModelForward:
	//			adapter = new ModelAdapter(context, list, ! adapter.getShowSelfDefineView());
	//			lvModel.startAnimation(rollingOverAnim0);
	//			lvModel.setAdapter(adapter);
	//			lvModel.startAnimation(rollingOverAnim1);
	//			break;
	//		default:
	//			break;
	//	}
	//}
	//Library内switch方法中case R.id.idx会报错
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvModelReturn) {
			onPageReturn();
		} else if (v.getId() == R.id.ivModelForward) {
			adapter = new ModelAdapter(context, list, ! adapter.getShowSelfDefineView());
			lvModel.startAnimation(rollingOverAnim0);
			lvModel.setAdapter(adapter);
			lvModel.startAnimation(rollingOverAnim1);
		}
	}
	//示例代码>>>>>>>>>>>>>>>>>>>


	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<<<<<<<<<<<<
	private static final int REQUEST_TO_MODEL = 10;
	public static final int RESULT_MODEL = 21;
	public static final String RESULT_CLICKED_ITEM = "RESULT_CLICKED_ITEM";
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_TO_MODEL:
				if (data != null) {
					showShortToast("clicked Item position = " + data.getIntExtra(RESULT_CLICKED_ITEM, -1));
				}
				break;
			default:
				break;
			}
		}
	}
	//示例代码>>>>>>>>>>>>>>>>>>>




	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}