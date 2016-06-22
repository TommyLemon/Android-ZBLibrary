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

package zuo.biao.library.base;

import java.util.List;

import zuo.biao.library.interfaces.OnReachViewBorderListener;
import zuo.biao.library.interfaces.OnStopLoadListener;
import zuo.biao.library.manager.HttpManager;
import zuo.biao.library.ui.XListView;
import zuo.biao.library.ui.XListView.IXListViewListener;
import android.view.View;
import android.widget.ListAdapter;

/**基础http获取列表的Activity
 * @author Lemon
 * @param <T> 数据模型(model/JavaBean)类
 * @use extends BaseHttpListActivity 并在子类onCreate中调用onRefresh(...), 具体参考.DemoHttpListActivity
 */
public abstract class BaseHttpListActivity<T> extends BaseListActivity<T, XListView> implements
HttpManager.OnHttpResponseListener, IXListViewListener, OnStopLoadListener {
	//	private static final String TAG = "BaseHttpListActivity";




	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//可以不是BaseHttpAdapter，这样更灵活;写在子类中更清晰灵活
	//	private BaseAdapter adapter;//private BaseHttpAdapter<T> adapter;
	/**设置列表适配器
	 * 直接调用可满足大部分情况下的需求。但由于setList中不同情况下可能需要插入其它代码
	 * （比如在(list == null || list.size() <= 0)情况下插入无数据提示 或 setAdapter(adapter)(adapter != null)后设置特殊的监听），
	 * 所以可用setAdapter(当里面代码不能满足需求时可在子类重写)满足需求。
	 * @param adapter if (adapter != null && adapter instanceof BaseHttpAdapter) >> 预加载
	 */
	@SuppressWarnings("unchecked")
	public void setAdapter(ListAdapter adapter) {
		lvBaseList.setAdapter(adapter);
		lvBaseList.showFooter(adapter != null);

		if (adapter != null && adapter instanceof zuo.biao.library.base.BaseAdapter) {
			((zuo.biao.library.base.BaseAdapter<T>) adapter).setOnReachViewBorderListener(new OnReachViewBorderListener(){
				@Override
				public void onReach(int type, View v) {
					if (type == TYPE_BOTTOM) {
						lvBaseList.onLoadMore();
					}
				}
			});
		}
	}

	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	/**
	 * 将Json串转为List，在非UI线程中
	 */
	public abstract List<T> parseArray(String json);


	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {// 必须调用
		super.initListener();
		setOnStopLoadListener(this);

		lvBaseList.setXListViewListener(this);
	}

	@Override
	public void onStopRefresh() {
		runUiThread(new Runnable() {

			@Override
			public void run() {
				lvBaseList.stopRefresh();
			}
		});
	}
	@Override
	public void onStopLoadMore(final boolean isHaveMore) {
		runUiThread(new Runnable() {

			@Override
			public void run() {
				lvBaseList.stopLoadMore(isHaveMore);
			}
		});
	}

	/**
	 * @param requestCode 请求码，自定义，同一个Activity中以实现接口方式发起多个网络请求时以状态码区分各个请求
	 * @param resultCode 服务器返回结果码
	 * @param json 服务器返回的Json串，用parseArray方法解析
	 */
	@Override
	public void onHttpRequestSuccess(int requestCode, int resultCode, final String json) {
		onLoadSucceed(parseArray(json));
	}

	/**里面只有stopLoadData();showShortToast(R.string.get_failed); 不能满足需求时可重写该方法
	 * @param requestCode 请求码，自定义，同一个Activity中以实现接口方式发起多个网络请求时以状态码区分各个请求
	 * @param exception   OKHTTP中请求异常
	 */
	@Override
	public void onHttpRequestError(int requestCode, Exception exception) {
		onLoadFailed(requestCode, exception);
	}


	// 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// 类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// 类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// 系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	// listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	// 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}