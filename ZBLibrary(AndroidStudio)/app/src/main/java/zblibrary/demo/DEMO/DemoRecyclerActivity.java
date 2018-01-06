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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import zblibrary.demo.R;
import zblibrary.demo.util.TestUtil;
import zuo.biao.library.base.BaseRecyclerActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.model.Entry;


/** 使用方法：复制>粘贴>改名>改代码 */
/**RecyclerView Activity示例
 * @author Lemon
 * @use toActivity(DemoRecyclerActivity.createIntent(...));
 */
public class DemoRecyclerActivity
extends BaseRecyclerActivity<Entry<String, String>, DemoComplexView, DemoComplexAdapter>
implements OnBottomDragListener {
	//	private static final String TAG = "DemoRecyclerActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_RANGE = "INTENT_RANGE";

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context, int range) {
		return new Intent(context, DemoRecyclerActivity.class).putExtra(INTENT_RANGE, range);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	public Activity getActivity() {
		return this; //必须return this;
	}

	private int range = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO demo_recycler_activity改为你所需要的layout文件；传this是为了底部左右滑动手势
		setContentView(R.layout.demo_recycler_activity, this);

		intent = getIntent();
		range = intent.getIntExtra(INTENT_RANGE, range);

		//		initCache(this);//初始化缓存，Entry<String, String>替换成不带类型的类才可使用，原因看 .CacheCallBack

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		onRefresh();
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initView() {//必须在onCreate方法内调用
		super.initView();

	}

	@Override
	public void setList(final List<Entry<String, String>> list) {
		//示例代码<<<<<<<<<<<<<<<
		setList(new AdapterCallBack<DemoComplexAdapter>() {

			@Override
			public DemoComplexAdapter createAdapter() {
				return new DemoComplexAdapter(context);
			}

			@Override
			public void refreshAdapter() {
				adapter.refresh(list);
			}
		});
		//示例代码>>>>>>>>>>>>>>>
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须在onCreate方法内调用
		super.initData();
		//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		tvBaseTitle.setText("" + rvBaseRecycler.getLayoutManager().getClass().getSimpleName());

		showShortToast("range = " + range);

		//示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	}

	@Override
	public void getListAsync(int page) {
		showProgressDialog(R.string.loading);

		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		for (int i = 0; i < 6; i++) {
			list.add(new Entry<String, String>(getPictureUrl(i + 6*page), "联系人" + i + 6*page));
		}

		onLoadSucceed(page, list);
	}

	/**获取图片地址，仅供测试用
	 * @param userId
	 * @return
	 */
	private String getPictureUrl(int userId) {
		return TestUtil.getPicture(userId % 6);
	}

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须在onCreate方法内调用
		super.initEvent();
		//示例代码<<<<<<<<<<<<<<<<<<<

	}


	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {

			return;
		}

		finish();
	}


	//示例代码：ItemView点击和长按事件处理 <<<<<<<<<<<<<<<<<<<
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		showShortToast("点击了 " + position);
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		showShortToast("长按了 " + position);
		return true;
	}
	//示例代码：ItemView点击和长按事件处理 >>>>>>>>>>>>>>>>>>>

	//系统自带监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<






	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}