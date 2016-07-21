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

import java.util.ArrayList;
import java.util.List;

import zblibrary.demo.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseListActivity;
import zuo.biao.library.bean.Entry;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.ui.GridAdapter;
import zuo.biao.library.util.StringUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

/**使用方法：复制>粘贴>改名>改代码  */
/**activity示例
 * @author Lemon
 * @warn 这里列表显示组件lvBaseList是GridView，如果是lvBaseList是ListView就改成ListView
 * @use toActivity(DemoListActivity.createIntent(...));
 */
public class DemoListActivity extends BaseListActivity<Entry<String, String>, GridView>
implements OnClickListener, OnBottomDragListener {
	private static final String TAG = "DemoListActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_RANGE = "INTENT_RANGE";

	public static final String RESULT_CLICKED_ITEM = "RESULT_CLICKED_ITEM";

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context, int range) {
		return new Intent(context, DemoListActivity.class).putExtra(INTENT_RANGE, range);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	
	@Override
	@NonNull
	public BaseActivity getActivity() {
		return this;
	}

	private int range = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO demo_list_activity改为你所需要的layout文件；传this是为了底部左右滑动手势
		setContentView(R.layout.demo_list_activity, this);

		intent = getIntent();
		range = intent.getIntExtra(INTENT_RANGE, range);

//		initCache(this);//初始化缓存，Entry<String, String>替换成不带类型的类才可使用，原因看 .OnCacheCallBack
		
		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

		onRefresh();
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<
	private TextView tvDemoListTitle;
	//示例代码>>>>>>>>
	@Override
	public void initView() {//必须在onCreate方法内调用
		super.initView();

		//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		tvDemoListTitle = (TextView) findViewById(R.id.tvDemoListTitle);

		//示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	}

	//示例代码<<<<<<<<
	private GridAdapter adapter;
	//示例代码>>>>>>>>
	/** 示例方法 ：显示列表内容
	 * @author author
	 * @param list
	 */
	@Override
	public void setList(List<Entry<String, String>> list) {
		if (list == null || list.size() <= 0) {
			Log.i(TAG, "setList list == null || list.size() <= 0 >> lvBaseHttpList.setAdapter(null); return;");
			adapter = null;
			lvBaseList.setAdapter(null);
			return;
		}

		if (adapter == null) {
			adapter = new GridAdapter(context, list);
			lvBaseList.setAdapter(adapter);
		} else {
			adapter.refresh(list);
		}

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须在onCreate方法内调用
		super.initData();
		//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		if (StringUtil.isNotEmpty(getIntent().getStringExtra(INTENT_TITLE), false)) {
			tvDemoListTitle.setText(StringUtil.getCurrentString());
		}

		showShortToast("range = " + range);

		//示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	}

	@Override
	public void getListAsync(int pageNum) {
		showProgressDialog(R.string.loading);

		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		for (int i = 0; i < 6; i++) {
			list.add(new Entry<String, String>(getPictureUrl(i + 6*pageNum), "联系人" + i + 6*pageNum));
		}

		onLoadSucceed(list);
	}

	/**获取图片地址，仅供测试用
	 * @param userId
	 * @return
	 */
	private String getPictureUrl(int userId) {
		switch (userId % 6) {
		case 0:
			return "http://images2015.cnblogs.com/blog/660067/201604/660067-20160404191409609-2089759742.png";
		case 1:
			return "https://avatars1.githubusercontent.com/u/5738175?v=3&s=40";
		case 2:
			return "http://static.oschina.net/uploads/user/1218/2437072_100.jpg?t=1461076033000";
		case 3:
			return "https://www.baidu.com/img/bd_logo1.png";
		case 4:
			return "http://common.cnblogs.com/images/icon_weibo_24.png";
		case 5:
			return "http://common.cnblogs.com/images/wechat.png";
		}
		return null;
	}

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须在onCreate方法内调用
		super.initListener();
		//示例代码<<<<<<<<<<<<<<<<<<<
		findViewById(R.id.ivDemoListReturn).setOnClickListener(this);

		lvBaseList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showShortToast("选择了 " + adapter.getItem(position).getValue());
				setResult(RESULT_OK, new Intent().putExtra(RESULT_CLICKED_ITEM, position));
				finish();
			}
		});
		//示例代码>>>>>>>>>>>>>>>>>>>
	}



	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {

			return;
		}	

		finish();
	}


	//系统自带监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//示例代码<<<<<<<<<<<<<<<<<<<
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivDemoListReturn:
			onDragBottom(false);
			break;
		default:
			break;
		}
	}
	//示例代码>>>>>>>>>>>>>>>>>>>





	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}