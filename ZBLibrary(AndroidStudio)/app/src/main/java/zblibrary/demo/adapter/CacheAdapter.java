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

import zblibrary.demo.adapter.CacheAdapter.CacheItemView;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.base.BaseViewAdapter;
import zuo.biao.library.interfaces.CacheCallBack;
import zuo.biao.library.interfaces.OnResultListener;
import zuo.biao.library.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**缓存Adapter，Item异步加载缓存数据很流畅。相比BaseCacheAdapter，不会影响原有adapter逻辑
 * @author Lemon
 * @param <T>
 * @param <BV>
 * @param <BA>
 * @warn 目前数据量太大(一般是1000以上)会崩溃；最后一个item数据不对，加载完后才会显示正确的数据
 * @use 把原来的adapter替换成new CacheAdapter(adapter, cacheCallBack)，具体见.UserListFragment
 */
public class CacheAdapter<T, BV extends BaseView<T>, BA extends BaseViewAdapter<T, BV>>
extends BaseViewAdapter<String, CacheItemView<T, BV>> implements OnRemoveListener<CacheLoader<T>> {
	//	private static final String TAG = "CacheAdapter";

	public BA ba;
	private CacheCallBack<T> cacheCallBack;
	private LimitedArrayList<CacheLoader<T>> loaderList;
	public CacheAdapter(BA ba, CacheCallBack<T> cacheCallBack) {
		super(ba.context);
		this.ba = ba;
		this.cacheCallBack = cacheCallBack;
		loaderList = new LimitedArrayList<CacheLoader<T>>(cacheCallBack.getCachePageSize());
		loaderList.setOnRemoveListener(this);

		//		setPresenter(this);//TODO 和不设置效果一样？？
	}

	@Override
	public void onRemove(CacheLoader<T> loader) {
		loader.cancel(true);
	}

	@Override
	public CacheItemView<T, BV> createView(int position, ViewGroup parent) {
		return new CacheItemView<T, BV>(ba.createView(position, parent));
	}

	@Override
	public void bindView(int position, CacheItemView<T, BV> civ) {//handler操作太频繁导致崩溃
		super.bindView(position, civ);
		loaderList.add(new CacheLoader<T>().execute(cacheCallBack.getCacheClass(), getItem(position), civ));
	}

	/**缓存item对应的View
	 * @use extends CacheItemView
	 */
	public static class CacheItemView<T, BV extends BaseView<T>> extends BaseView<String> implements OnResultListener<T> {
		private static final String TAG = "CacheItemView";

		public BV bv;
		public CacheItemView(BV bv) {
			super(bv.context, bv.resources);
			this.bv = bv;
			//			Log.d(TAG, "CacheItemView(bv)");
		}

		@Override
		public View createView(LayoutInflater inflater) {
			//			Log.d(TAG, "createView  return bv.createView(" + position + ", " + viewType + ");");
			return bv.createView(inflater, position, viewType);
		}

		@Override
		public void bindView(String data) {
			Log.d(TAG, "bindView  data = " + data);
			//bv.bindView(null);//导致闪屏		  
		}

		@Override
		public void onResult(T result) {
			//			Log.d(TAG, "onResult position = " + position + ", viewType = " + viewType);// + "" + ", result = \n" + Json.toJSONString(result));
			bv.bindView(result, position, viewType);		
		}
	}

}