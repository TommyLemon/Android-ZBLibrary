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

import zblibrary.demo.DEMO.DemoAdapter3.ItemView;
import zuo.biao.library.base.BaseCacheAdapter;
import zuo.biao.library.base.BaseCacheAdapter.CacheItemView;
import zuo.biao.library.interfaces.CacheCallBack;
import zuo.biao.library.model.Entry;
import android.app.Activity;
import android.view.ViewGroup;

/**使用方法：复制>粘贴>改名>改代码  */
/**adapter模板，异步加载数据很流畅，建议在加载复杂View时使用
 * *适用于listView,gridView
 * @author Lemon
 * @use 修改.ItemView代码 >> new DemoAdapter3(...),具体参考.DemoActivity(setList方法内)
 */
public class DemoAdapter3 extends BaseCacheAdapter<Entry<String, String>, DemoView, ItemView> {
	//	private static final String TAG = "DemoAdapter3";

	public DemoAdapter3(Activity context, CacheCallBack<Entry<String, String>> cacheCallBack) {
		super(context, cacheCallBack);
	}

	@Override
	public ItemView createView(int position, ViewGroup parent) {
		return new ItemView(new DemoView(context, resources));
	}

	static class ItemView extends CacheItemView<Entry<String, String>, DemoView> {

		public ItemView(DemoView bv) {
			super(bv);
		}

	}
	
}