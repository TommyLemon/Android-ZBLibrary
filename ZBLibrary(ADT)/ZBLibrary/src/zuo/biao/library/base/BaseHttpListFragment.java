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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.interfaces.OnReachViewBorderListener;
import zuo.biao.library.manager.HttpManager;
import zuo.biao.library.manager.ListDiskCacheManager;
import zuo.biao.library.ui.XListView;
import zuo.biao.library.ui.XListView.IXListViewListener;
import zuo.biao.library.util.StringUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**基础http获取列表的android.support.v4.app.Fragment
 * @author Lemon
 * @warn 1.不要在子类重复这个类中onCreateView中的代码;
 *       2.只使用lvBaseHttpList为显示http数据的ListView，不要在子类中改变它
 * @param <T> model(JavaBean)类名
 * @use extends BaseHttpListFragment, 具体参考.DemoHttpListFragment
 * @must 在子类onCreateView中super.onCreateView(inflater, container, savedInstanceState);
 *       initView();initData();initListener(); return view;
 */
public abstract class BaseHttpListFragment<T> extends BaseFragment implements
HttpManager.OnHttpResponseListener, IXListViewListener {
	private static final String TAG = "BaseHttpListFragment";

	/**
	 * @warn 如果在子类中super.initView();则view必须含有initView中初始化用到的id且id对应的View的类型全部相同；
	 *       否则必须在子类initView中重写这个类中initView内的代码(所有id替换成可用id)
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 * @must 1.不要在子类重复这个类中onCreateView中的代码;
	 *       2.在子类onCreateView中super.onCreateView(inflater, container, savedInstanceState);
	 *       initView();initData();initListener(); return view;
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return onCreateView(inflater, container, savedInstanceState, 0);
	}
	/**
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @param layoutResID fragment全局视图view的布局资源id，默认值为R.layout.base_http_list_fragment
	 * @return
	 * @must 1.不要在子类重复这个类中onCreateView中的代码;
	 *       2.在子类onCreateView中super.onCreateView(inflater, container, savedInstanceState, layoutResID);
	 *       initView();initData();initListener(); return view;
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layoutResID) {
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<<<
		view = inflater.inflate(layoutResID <= 0 ? R.layout.base_http_list_fragment : layoutResID, container, false);
		context = (BaseActivity) getActivity();
		isAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		return view;
	}



	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**
	 * 显示列表的ListView
	 */
	protected XListView lvBaseList;
	/**
	 * 如果在子类中调用(即super.initView());则view必须含有initView中初始化用到的id且id对应的View的类型全部相同；
	 * 否则必须在子类initView中重写这个类中initView内的代码(所有id替换成可用id)
	 */
	@Override
	public void initView() {// 必须调用

		lvBaseList = (XListView) findViewById(R.id.lvBaseList);
	}

	/**显示列表（已在UI线程中）
	 * @param list
	 */
	public abstract void setList(List<T> list);//abstract是为了调用子类中的该方法

	//可以不是BaseHttpAdapter，这样更灵活;写在子类中更清晰灵活
	//	private BaseAdapter adapter;//private BaseHttpAdapter<T> adapter;
	/**设置列表适配器
	 * 直接调用可满足大部分情况下的需求。但由于setList中不同情况下可能需要插入其它代码
	 * （比如在(list == null || list.size() <= 0)情况下插入无数据提示 或 setAdapter(adapter)(adapter != null)后设置特殊的监听），
	 * 所以可用setAdapter(当里面代码不能满足需求时可在子类重写)满足需求。
	 * @param adapter if (adapter != null && adapter instanceof BaseHttpAdapter) >> 预加载
	 */
	@SuppressWarnings("unchecked")
	public void setAdapter(BaseAdapter adapter) {
		lvBaseList.setAdapter(adapter);
		lvBaseList.showFooter(adapter != null);

		if (adapter != null && adapter instanceof zuo.biao.library.base.BaseAdapter) {
			((zuo.biao.library.base.BaseAdapter<T>) adapter).setOnReachViewBorderListener(new OnReachViewBorderListener() {
				@Override
				public void onReach(int type, View v) {
					if (type == TYPE_BOTTOM) {
						lvBaseList.onLoadMore();
					}
				}
			});
		}
	}

	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private boolean isToLoadCache;
	private boolean isToSaveCache;
	@Override
	public void initData() {// 必须调用

		isToSaveCache = getCacheClass() != null && StringUtil.isNotEmpty(getCacheGroup(), true);
		isToLoadCache = isToSaveCache;
	}

	public void loadData(int pageNum_) {
		loadData(pageNum_, isToLoadCache);
	}
	/**
	 * 正在加载
	 */
	protected boolean isLoading = false;
	/**
	 * 服务器还有更多
	 */
	protected boolean isServerHaveMore = true;
	/**
	 * 加载页码
	 */
	protected int pageNum;
	private int loadCacheStart;
	/**加载数据，用httpGetList方法发请求获取数据
	 * @param pageNum_
	 * @param isToLoadCache
	 */
	public void loadData(int pageNum_, final boolean isToLoadCache) {
		if (isLoading) {
			return;
		}
		isLoading = true;

		if (pageNum_ <= HttpManager.PAGE_NUM_0) {
			pageNum_ = HttpManager.PAGE_NUM_0;
			isServerHaveMore = true;
			loadCacheStart = 0;//使用则可像网络正常情况下的重载，不使用则在网络异常情况下不重载（导致重载后加载数据下移）
		} else {
			if (isServerHaveMore == false) {
				stopLoadData();
				return;
			}
			loadCacheStart = list == null ? 0 : list.size();
		}
		this.pageNum = pageNum_;

		runThread(TAG + "loadData", new Runnable() {

			@Override
			public void run() {
				//从缓存获取数据
				final List<T> newList = isToLoadCache == false ? null : ListDiskCacheManager.getInstance().getList(
						getCacheClass(), getCacheGroup(), loadCacheStart);
				if (newList == null || newList.size() <= 0) {
					httpGetList(pageNum);
					return;
				}

				if (pageNum <= HttpManager.PAGE_NUM_0) {
					list = newList;
				} else {
					if (list == null) {
						list = new ArrayList<>();
					}
					list.addAll(newList);
				}

				isLoading = false;
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (isAlive) {
							setList(list);
							if (pageNum <= HttpManager.PAGE_NUM_0) {
								loadData(pageNum, false);
							}
						}
					}
				});
			}
		});

	}
	/**停止加载数据
	 */
	public void stopLoadData() {
		isLoading = false;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (pageNum <= HttpManager.PAGE_NUM_0) {
					lvBaseList.stopRefresh();
				} else {
					lvBaseList.stopLoadMore(isServerHaveMore);
				}
			}
		});
	}


	/**
	 * http获取列表，在非UI线程中
	 */
	public abstract void httpGetList(int pageNum);

	/**
	 * 将Json串转为List，在非UI线程中
	 */
	public abstract List<T> parseArray(String json);



	/**
	 * 数据列表
	 */
	public List<T> list;
	/**
	 * 新数据列表
	 */
	private List<T> newList = null;
	/**处理列表
	 * @param newList_ 新数据列表
	 * @return
	 */
	public void handleList(List<T> newList_) {
		this.newList = newList_;
		if (newList == null) {
			newList = new ArrayList<>();
		}

		if (pageNum <= HttpManager.PAGE_NUM_0) {
			saveCacheStart = 0;
			list = newList;
			if (list != null && list.size() > 0) {
				isToLoadCache = false;
			}
		} else {
			saveCacheStart = list == null ? 0 : list.size();
			if (newList.size() <= 0) {
				isServerHaveMore = false;
			} else {
				if (list == null) {
					list = new ArrayList<>();
				}
				list.addAll(newList);
			}
		}

	}



	/**
	 * 获取需要缓存的类
	 * @return null-不缓存
	 */
	@Nullable
	public abstract Class<T> getCacheClass();
	/**
	 * 获取缓存的分组
	 * @return 不含非空字符的String-不缓存
	 */
	@Nullable
	public abstract String getCacheGroup();
	/**
	 * 获取缓存数据的id，在非UI线程中s
	 * @param data
	 * @return "" + data.getId(); //不用long是因为某些数据(例如订单)的id超出long的最大值
	 */
	@Nullable
	public abstract String getCacheId(T data);



	private int saveCacheStart;
	/**保存缓存
	 */
	public void saveCache() {
		LinkedHashMap<String, T> map = new LinkedHashMap<>();
		for (T data : newList) {
			if (data != null) {
				map.put(getCacheId(data), data);//map.put(null, data);不会崩溃
			}
		}

		ListDiskCacheManager.getInstance().saveList(getCacheClass(), getCacheGroup(), map
				, saveCacheStart, newList.size());
	}

	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {// 必须调用

		lvBaseList.setXListViewListener(this);
	}

	/**
	 * @param requestCode 请求码，自定义，同一个Activity中以实现接口方式发起多个网络请求时以状态码区分各个请求
	 * @param resultCode 服务器返回结果码
	 * @param json 服务器返回的Json串，用parseArray方法解析
	 */
	@Override
	public void onHttpRequestSuccess(int requestCode, int resultCode, final String json) {
		stopLoadData();
		runThread(TAG + "onHttpRequestSuccess", new Runnable() {
			@Override
			public void run() {

				handleList(parseArray(json));

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (isAlive) {
							setList(list);
						}
					}
				});

				if (isToSaveCache) {
					saveCache();
				}
			}
		});
	}

	/**里面只有stopLoadData();showShortToast(R.string.get_failed); 不能满足需求时可重写该方法
	 * @param requestCode 请求码，自定义，同一个Activity中以实现接口方式发起多个网络请求时以状态码区分各个请求
	 * @param exception   OKHTTP中请求异常
	 */
	@Override
	public void onHttpRequestError(int requestCode, Exception exception) {
		stopLoadData();
		showShortToast(R.string.get_failed);
	}


	@Override
	public void onRefresh() {
		loadData(HttpManager.PAGE_NUM_0);
	}
	@Override
	public void onLoadMore() {
		loadData(pageNum + 1);
	}


	// 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// 类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// 类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// 系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	// listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	// 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}