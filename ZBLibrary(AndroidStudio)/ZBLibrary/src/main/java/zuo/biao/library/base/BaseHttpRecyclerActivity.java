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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.interfaces.OnLoadListener;
import zuo.biao.library.interfaces.OnStopLoadListener;
import zuo.biao.library.util.Log;


/**基础http网络列表的Activity
 * @author Lemon
 * @param <T> 数据模型(model/JavaBean)类
 * @param <VH> ViewHolder或其子类
 * @param <A> 管理LV的Adapter
 * @see #getListAsync(int)
 * @see #onHttpResponse(int, String, Exception)
 * @see
 *   <pre>
 *       基础使用：<br />
 *       extends BaseHttpRecyclerActivity 并在子类onCreate中srlBaseHttpRecycler.autoRefresh(), 具体参考.DemoHttpRecyclerActivity
 *       <br /><br />
 *       列表数据加载及显示过程：<br />
 *       1.srlBaseHttpRecycler.autoRefresh触发刷新 <br />
 *       2.getListAsync异步获取列表数据 <br />
 *       3.onHttpResponse处理获取数据的结果 <br />
 *       4.setList把列表数据绑定到adapter <br />
 *   </pre>
 */
public abstract class BaseHttpRecyclerActivity<T, VH extends RecyclerView.ViewHolder, A extends RecyclerView.Adapter<VH>>
		extends BaseRecyclerActivity<T, VH, A>
		implements OnHttpResponseListener, OnStopLoadListener, OnRefreshListener, OnLoadmoreListener {
	private static final String TAG = "BaseHttpRecyclerActivity";




	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	protected SmartRefreshLayout srlBaseHttpRecycler;

	@Override
	public void initView() {
		super.initView();

		srlBaseHttpRecycler = findView(R.id.srlBaseHttpRecycler);

		setList((List<T>) null);//ListView需要设置adapter才能显示header和footer; setAdapter调不到子类方法
	}

	@Override
	public void setAdapter(A adapter) {
		if (adapter != null && adapter instanceof zuo.biao.library.base.BaseAdapter) {
			((zuo.biao.library.base.BaseAdapter) adapter).setOnLoadListener(new OnLoadListener() {
				@Override
				public void onRefresh() {
					srlBaseHttpRecycler.autoRefresh();
				}

				@Override
				public void onLoadMore() {
					srlBaseHttpRecycler.autoLoadmore();
				}
			});
		}
		super.setAdapter(adapter);
	}

	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {
		super.initData();

	}

	/**
	 * @param page 用-page作为requestCode
	 */
	@Override
	public abstract void getListAsync(int page);

	/**
	 * 将JSON串转为List（已在非UI线程中）
	 * *直接JSON.parseArray(json, getCacheClass());可以省去这个方法，但由于可能json不完全符合parseArray条件，所以还是要保留。
	 * *比如json只有其中一部分能作为parseArray的字符串时，必须先提取出这段字符串再parseArray
	 */
	public abstract List<T> parseArray(String json);


	// Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {// 必须调用
		super.initEvent();
		setOnStopLoadListener(this);

		srlBaseHttpRecycler.setOnRefreshListener(this);
		srlBaseHttpRecycler.setOnLoadmoreListener(this);
	}


	/**重写后可自定义对这个事件的处理
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}

	@Override
	public void onRefresh(RefreshLayout refreshlayout) {
		onRefresh();
	}

	@Override
	public void onLoadmore(RefreshLayout refreshlayout) {
		onLoadMore();
	}


	@Override
	public void onStopRefresh() {
		runUiThread(new Runnable() {

			@Override
			public void run() {
				srlBaseHttpRecycler.finishRefresh();
				srlBaseHttpRecycler.setLoadmoreFinished(false);
			}
		});
	}
	@Override
	public void onStopLoadMore(final boolean isHaveMore) {
		runUiThread(new Runnable() {

			@Override
			public void run() {
				if (isHaveMore) {
					srlBaseHttpRecycler.finishLoadmore();
				} else {
					srlBaseHttpRecycler.finishLoadmoreWithNoMoreData();
				}
				srlBaseHttpRecycler.setLoadmoreFinished(! isHaveMore);
			}
		});
	}

	/**处理Http请求结果
	 * @param requestCode  = -page {@link #getListAsync(int)}
	 * @param resultJson
	 * @param e
	 */
	@Override
	public void onHttpResponse(final int requestCode, final String resultJson, final Exception e) {
		runThread(TAG + "onHttpResponse", new Runnable() {

			@Override
			public void run() {
				int page = 0;
				if (requestCode > 0) {
					Log.w(TAG, "requestCode > 0, 应该用BaseListFragment#getListAsync(int page)中的page的负数作为requestCode!");
				} else {
					page = - requestCode;
				}

				onResponse(page, parseArray(resultJson), e);
			}
		});
	}

	/**处理结果
	 * @param page
	 * @param list
	 * @param e
	 */
	public void onResponse(int page, List<T> list, Exception e) {
		if ((list == null || list.isEmpty()) && e != null) {
			onLoadFailed(page, e);
		} else {
			onLoadSucceed(page, list);
		}
	}


	// 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// 类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// 类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// 系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	// Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	// 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}