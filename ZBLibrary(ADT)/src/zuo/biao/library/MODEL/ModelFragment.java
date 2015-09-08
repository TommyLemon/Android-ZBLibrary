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

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.base.BaseFragmentActivity;
import zuo.biao.library.bean.KeyValueBean;
import zuo.biao.library.util.StringUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**使用方法：复制>粘贴>改名>改代码  */
/**fragment示例
 * @author Lemon
 * @warn 复制到其它工程内使用时务必修改import zuo.biao.library.R;的文件路径（这里是zuo.biao.library）为所在应用包名
 * @use new ModelFragment,详细使用见zuo.biao.library.MODEL.ModelFragmentActivity(initData方法内)
 */
public class ModelFragment extends BaseFragment implements OnClickListener {
	private static final String TAG = "ModelFragment";

	//示例代码<<<<<<<<
	public ModelFragment() {
		// TODO default
	}
	private long userId;
	public ModelFragment(long userId) {
		this.userId = userId;
	}
	//示例代码>>>>>>>>>

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//必须使用<<<<<<<<<<<<<<<<<<
		//model_activity改为你所需要的layout文件
		view = inflater.inflate(R.layout.model_fragment, container, false);
		context = (BaseFragmentActivity) getActivity();
		isActivityAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

		return view;
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	public static final String INTENT_TITLE = "INTENT_TITLE";
	
	private TextView tvModelFragmentTitle;
	private ListView lvModelFragment;

	private ScaleAnimation rollingOverAnim0 = new ScaleAnimation(1, 0, 1, 1,
			Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
			0.5f);
	private ScaleAnimation rollingOverAnim1 = new ScaleAnimation(0, 1, 1, 1,
			Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
			0.5f);
	//示例代码>>>>>>>>
	@Override
	public void initView() {//必须调用

		//示例代码<<<<<<<<<<<<<<

		tvModelFragmentTitle = (TextView) view.findViewById(R.id.tvModelFragmentTitle);
		if (StringUtil.isNotEmpty(context.getIntent().getStringExtra(INTENT_TITLE), true)) {
			tvModelFragmentTitle.setText(StringUtil.getCurrentString());
		}

		lvModelFragment = (ListView) view.findViewById(R.id.lvModelFragment);

		rollingOverAnim0.setDuration(200);
		rollingOverAnim1.setDuration(200);

		//示例代码>>>>>>>>>>>>>>
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
			Log.i(TAG, "setList list == null || list.size() <= 0 >> lvModelFragment.setAdapter(null); return;");
			adapter = null;
			lvModelFragment.setAdapter(null);
			return;
		}

		if (adapter == null) {
			adapter = new ModelAdapter(context, list);
			lvModelFragment.setAdapter(adapter);
		} else {
			adapter.refresh(list);
		}
	}

	public void changeListStyle() {
		adapter = new ModelAdapter(context, list, ! adapter.getShowSelfDefineView());
		lvModelFragment.startAnimation(rollingOverAnim0);
		lvModelFragment.setAdapter(adapter);
		lvModelFragment.startAnimation(rollingOverAnim1);
	}

	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	private List<KeyValueBean> list;
	//示例代码>>>>>>>>>
	@Override
	public void initData() {//必须调用

		//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		showProgressDialog(R.string.loading);

		runThread(TAG + "initData", new Runnable() {//runnable已在baseFragment中新建
			@Override
			public void run() {

				list = getContactList(userId);
				context.runOnUiThread(new Runnable() {
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

	private Handler resetListHandler;
	@Override
	public void initListener() {//必须调用
		//示例代码<<<<<<<<<<<<<<<<<<<
	
		resetListHandler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				
				lvModelFragment.setAdapter(adapter);
				return false;
			}
		});
		
		view.findViewById(R.id.tvModelFragmentReturn).setOnClickListener(this);
		view.findViewById(R.id.ivModelFragmentForward).setOnClickListener(this);

		lvModelFragment.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position > 10) {
					context.setResult(Activity.RESULT_OK, new Intent().putExtra(RESULT_CLICKED_ITEM, position));
					context.finish();
				} else {
					KeyValueBean ckvb = adapter.getItem(position);
					toActivity(new Intent(context, ModelActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).putExtra(INTENT_TITLE, ckvb.getValue()), REQUEST_TO_MODEL);
				}
			}
		});
		//示例代码>>>>>>>>>>>>>>>>>>>
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<<<<<<<<<<<<
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tvModelFragmentReturn:
//			context.finish();
//			//			getFragmentManager().popBackStack();
//			break;
//		case R.id.ivModelFragmentForward:
//			adapter = new ModelFragmentAdapter(context, list, adapter.getShowSelfDefineView());
//			if (lvModelFragment.getChildCount() > 0) {
//				lvModelFragment.smoothScrollToPosition(0);
//			}
//			resetListHandler.sendEmptyMessageDelayed(0, 10 * lvModelFragment.getCount());
//			break;
//		default:
//			break;
//		}
//	}
	//Library内switch方法中case R.id.idx会报错
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvModelFragmentReturn) {
			context.finish();
			//			getFragmentManager().popBackStack();
		} else if (v.getId() == R.id.ivModelFragmentForward) {
			adapter = new ModelAdapter(context, list, adapter.getShowSelfDefineView());
			if (lvModelFragment.getChildCount() > 0) {
				lvModelFragment.smoothScrollToPosition(0);
			}
			resetListHandler.sendEmptyMessageDelayed(0, 10 * lvModelFragment.getCount());
		}
	}
	//示例代码<<<<<<<<<<<<<<<<<<<


	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<<<<<<<<<<<<
	private static final int REQUEST_TO_MODEL = 10;
	public static final String RESULT_CLICKED_ITEM = "RESULT_CLICKED_ITEM";
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
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


	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}