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

package zblibrary.demo.activity_fragment;

import java.util.List;

import zblibrary.demo.adapter.UserAdapter;
import zblibrary.demo.model.User;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.TestUtil;
import zuo.biao.library.base.BaseHttpListFragment;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.interfaces.CacheCallBack;
import zuo.biao.library.util.Json;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**用户列表界面fragment
 * @author Lemon
 * @use new UserListFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 * @must 查看 .HttpManager 中的@must和@warn
 *       查看 .SettingUtil 中的@must和@warn
 */
public class UserListFragment extends BaseHttpListFragment<User, UserAdapter>//CacheAdapter<User, UserView, UserAdapter>>//
implements OnItemClickListener, CacheCallBack<User> {
	//	private static final String TAG = "UserListFragment";

	//与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String ARGUMENT_RANGE = "ARGUMENT_RANGE";

	public static UserListFragment createInstance(int range) {
		UserListFragment fragment = new UserListFragment();

		Bundle bundle = new Bundle();
		bundle.putInt(ARGUMENT_RANGE, range);

		fragment.setArguments(bundle);
		return fragment;
	}

	//与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	public static final int RANGE_ALL = HttpRequest.USER_LIST_RANGE_ALL;
	public static final int RANGE_RECOMMEND = HttpRequest.USER_LIST_RANGE_RECOMMEND;

	private int range = RANGE_ALL;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		argument = getArguments();
		if (argument != null) {
			range = argument.getInt(ARGUMENT_RANGE, range);
		}

		Toast.makeText(context, "服务器配置有误，请查看这个类的@must", Toast.LENGTH_LONG).show();

		initCache(this);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		lvBaseList.onRefresh();

		return view;
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

		//		//ListView内容太复杂（比如微信朋友圈）时容易卡顿，可使用以下方式异步加载
		//		setListAsync(list, new OnResultListener<List<String>>() {
		//
		//			@Override
		//			public void onResult(final List<String> result) {
		//				setList(new AdapterCallBack<CacheAdapter<User, UserView, UserAdapter>>() {
		//
		//					@Override
		//					public CacheAdapter<User, UserView, UserAdapter> createAdapter() {
		//						return new CacheAdapter<User, UserView, UserAdapter>(
		//								new UserAdapter(context), UserListFragment.this);
		//					}
		//
		//					@Override
		//					public void refreshAdapter() {
		//						adapter.refresh(result);
		//					}
		//				});
		//			}
		//		});

	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用
		super.initData();

	}

	@Override
	public void getListAsync(final int pageNum) {
		//实际使用时用这个，需要配置服务器地址		HttpRequest.getUserList(range, pageNum, 0, this);

		//仅测试用<<<<<<<<<<<
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				onHttpResponse(0, Json.toJSONString(TestUtil.getUserList(pageNum, getCachePageSize())), null);
			}
		}, 1000);
		//仅测试用>>>>>>>>>>>>
	}

	@Override
	public List<User> parseArray(String json) {
		return Json.parseArray(json, User.class);
	}

	@Override
	public Class<User> getCacheClass() {
		return User.class;
	}
	@Override
	public String getCacheGroup() {
		return "range=" + range;
	}
	@Override
	public String getCacheId(User data) {
		return data == null ? null : "" + data.getId();
	}


	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initEvent() {//必须调用
		super.initEvent();

		lvBaseList.setOnItemClickListener(this);
	}



	//系统自带监听方法 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		position -= lvBaseList.getHeaderViewsCount();
		if (position < 0 || adapter == null || position >= adapter.getCount()) {
			return;
		}

		User user = list.get(position);//adapter.getItem(position);	
		if (BaseModel.isCorrect(user)) {//相当于 user != null && user.getId() > 0
			toActivity(UserActivity.createIntent(context, user.getId()));
		}
	}


	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}