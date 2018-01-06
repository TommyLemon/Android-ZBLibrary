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
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import zblibrary.demo.R;
import zblibrary.demo.activity_fragment.UserActivity;
import zblibrary.demo.adapter.UserAdapter;
import zblibrary.demo.model.User;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.TestUtil;
import zuo.biao.library.base.BaseHttpListActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.util.JSON;


/** 使用方法：复制>粘贴>改名>改代码 */
/**用户列表界面Activity示例
 * @author Lemon
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use toActivity(DemoHttpListActivity.createIntent(...));
 */
public class DemoHttpListActivity extends BaseHttpListActivity<User, UserAdapter> implements OnBottomDragListener {
	//	private static final String TAG = "DemoHttpListActivity";


	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final int RANGE_ALL = HttpRequest.USER_LIST_RANGE_ALL;
	public static final int RANGE_RECOMMEND = HttpRequest.USER_LIST_RANGE_RECOMMEND;

	public static final String INTENT_RANGE = "INTENT_RANGE";


	/**启动这个Activity的Intent
	 * @param context
	 * @param range
	 * @return
	 */
	public static Intent createIntent(Context context, int range) {
		return new Intent(context, DemoHttpListActivity.class).putExtra(INTENT_RANGE, range);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	@Override
	public Activity getActivity() {
		return this; //必须return this;
	}


	private int range = RANGE_ALL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO demo_http_list_activity改为你所需要的layout文件；传this是为了底部左右滑动手势
		setContentView(R.layout.demo_http_list_activity, this);

		intent = getIntent();
		range = intent.getIntExtra(INTENT_RANGE, range);


		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		lvBaseList.onRefresh();
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initView() {//必须调用
		super.initView();

	}

	@Override
	public void setList(final List<User> list) {
		setList(new AdapterCallBack<UserAdapter>() {

			@Override
			public UserAdapter createAdapter() {
				return new UserAdapter(context);
			}

			@Override
			public void refreshAdapter() {
				adapter.refresh(list);
			}
		});
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用
		super.initData();

	}

	@Override
	public void getListAsync(final int page) {
		//实际使用时用这个，需要配置服务器地址		HttpRequest.getUserList(range, page, -page, this);

		//仅测试用<<<<<<<<<<<
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				onHttpResponse(-page, page >= 5 ? null : JSON.toJSONString(TestUtil.getUserList(page, 10)), null);
			}
		}, 1000);
		//仅测试用>>>>>>>>>>>>
	}

	@Override
	public List<User> parseArray(String json) {
		return JSON.parseArray(json, User.class);
	}



	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initEvent() {//必须调用
		super.initEvent();

	}


	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {

			return;
		}

		finish();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (id > 0) {
			toActivity(UserActivity.createIntent(context, id));
		}
	}

	//系统自带监听方法 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}