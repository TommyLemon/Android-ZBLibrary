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

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zblibrary.demo.adapter.UserAdapter;
import zblibrary.demo.base.BaseHttpListFragment;
import zblibrary.demo.manager.HttpRequest;
import zblibrary.demo.model.User;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.Json;

/**用户列表界面fragment
 * @author Lemon
 * @use new UserListFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 * @must 查看 .HttpRequest 中的@must和@warn
 *       查看 .SettingUtil 中的@must和@warn
 */
public class UserListFragment extends BaseHttpListFragment<User> implements OnItemClickListener {
	//	private static final String TAG = "UserListFragment";

	//与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String ARGUMENT_RANGE = "ARGUMENT_RANGE";

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

		Toast.makeText(context, "服务器地址等信息有误，请查看这个类的@must信息", Toast.LENGTH_LONG).show();

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

		lvBaseHttpList.onRefresh();

		return view;
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initView() {//必须调用
		super.initView();

		setAdapter(null);
	}

	private UserAdapter adapter;
	@Override
	public void setList(List<User> list) {
		if (list == null || list.size() <= 0) {
			setAdapter(null);
			return;
		}

		if (adapter == null) {
			adapter = new UserAdapter(context, list);
			setAdapter(adapter);
		} else {
			adapter.refresh(list);
		}

	}




	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用
		super.initData();

	}

	@Override
	public void httpGetList(final int pageNum) {
		//需要配置服务器地址		HttpRequest.getInstance().getUserList(range, pageNum, 0, this);

		//仅测试用<<<<<<<<<<<
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				onHttpRequestSuccess(0, HttpRequest.RESULT_GET_USER_LIST_SUCCEED
						, Json.toJSONString(getList(range, pageNum)));
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



	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initListener() {//必须调用

		super.initListener();

		lvBaseHttpList.setOnItemClickListener(this);
	}



	//系统自带监听方法 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		position -= lvBaseHttpList.getHeaderViewsCount();
		if (position < 0 || adapter == null || position >= adapter.getCount()) {
			return;
		}
		
		User user = adapter.getItem(position);	
		if (user == null) {
			user = new User();
		}
		toActivity(WebViewActivity.createIntent(context, user.getName(), user.getHead()));
	}


	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>





	/**示例方法：获取列表
	 * @author lemon
	 * @param range 
	 * @param pageNum 
	 * @return
	 */
	protected List<User> getList(long range, int pageNum) {
		range ++;
		List<User> list = new ArrayList<User>();
		int userId;
		for (int i = 0; i < 10; i++) {
			userId = i + pageNum*10;
			list.add(new User(i, "联系人" + userId, String.valueOf(1311736568 + (i + range)*(pageNum + range)), getPictureUrl(userId)));
		}
		return list;
	}


	/**获取图片地址，仅供测试用
	 * @param userId
	 * @return
	 */
	private String getPictureUrl(int userId) {
		switch (userId%6) {
		case 0:
			return "https://avatars1.githubusercontent.com/u/5738175?v=3&s=40";
		case 1:
			return "http://static.oschina.net/uploads/user/1218/2437072_100.jpg?t=1461076033000";
		case 2:
			return "https://www.baidu.com/img/bd_logo1.png";
		case 3:
			return "http://common.cnblogs.com/images/icon_weibo_24.png";
		case 4:
			return "http://common.cnblogs.com/images/wechat.png";
		case 5:
			return "http://images2015.cnblogs.com/blog/660067/201604/660067-20160404191409609-2089759742.png";
		}
		return null;
	}




}