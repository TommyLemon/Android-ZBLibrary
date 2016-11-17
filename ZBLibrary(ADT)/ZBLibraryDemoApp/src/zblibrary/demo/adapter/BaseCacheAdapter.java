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

package zblibrary.demo.adapter;

import zblibrary.demo.adapter.BaseCacheAdapter.CacheItemView;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.base.BaseViewAdapter;
import zuo.biao.library.interfaces.CacheCallBack;
import zuo.biao.library.interfaces.OnResultListener;
import zuo.biao.library.util.Log;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

/**基础缓存Adapter，Item异步加载缓存数据很流畅。
 * @author Lemon
 * @param <T>
 * @param <BV>
 * @param <CIV>
 * @warn 目前数据量太大(一般是1000以上)会崩溃
 * @use extends BaseCacheAdapter<T, BV, CIV>，具体见.DemoAdapter3和.UserAdapter3
 */
public abstract class BaseCacheAdapter<T, BV extends BaseView<T>, CIV extends CacheItemView<T, BV>>
extends BaseViewAdapter<String, CIV> implements OnRemoveListener<CacheLoader<T>> {
	//	private static final String TAG = "BaseCacheAdapter";

	private CacheCallBack<T> cacheCallBack;
	private LimitedArrayList<CacheLoader<T>> loaderList;
	public BaseCacheAdapter(Activity context, CacheCallBack<T> cacheCallBack) {
		super(context);
		this.cacheCallBack = cacheCallBack;
		loaderList = new LimitedArrayList<CacheLoader<T>>(cacheCallBack.getCachePageSize());
		loaderList.setOnRemoveListener(this);

		setPresenter(this);//TODO 和不设置效果一样？？
	}

	@Override
	public void onRemove(CacheLoader<T> loader) {
		loader.cancel(true);
	}

	@Override
	public void bindView(int position, CIV civ) {//handler操作太频繁导致崩溃
		super.bindView(position, civ);
		loaderList.add(new CacheLoader<T>().execute(cacheCallBack.getCacheClass(), getItem(position), civ));
	}


	/**缓存item对应的View
	 * @use extends CacheItemView
	 */
	public static abstract class CacheItemView<T, BV extends BaseView<T>> extends BaseView<String>
	implements OnResultListener<T> {
		private static final String TAG = "CacheItemView";

		public BV bv;
		public CacheItemView(BV bv) {
			super(bv.context, bv.resources);
			this.bv = bv;
			Log.d(TAG, "CacheItemView(bv)");
		}

		@Override
		public View createView(LayoutInflater inflater) {
			Log.d(TAG, "createView  return bv.createView(" + position + ", " + viewType + ");");
			return bv.createView(inflater, position, viewType);
		}

		@Override
		public void bindView(String data) {
			Log.d(TAG, "bindView  data = " + data);
		}

		@Override
		public void onResult(T result) {
			Log.d(TAG, "onResult position = " + position + ", viewType = " + viewType);// + "" + ", result = \n" + Json.toJSONString(result));
			bv.bindView(result, position, viewType);		
		}
	}

}