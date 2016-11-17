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
import zblibrary.demo.adapter.UserAdapter3.ItemView;
import zblibrary.demo.model.User;
import zblibrary.demo.view.UserView;
import zuo.biao.library.interfaces.CacheCallBack;
import android.app.Activity;
import android.view.ViewGroup;

/**用户adapter，异步加载更流畅
 * @author Lemon
 */
public class UserAdapter3 extends BaseCacheAdapter<User, UserView, ItemView> {
	//	private static final String TAG = "UserAdapter3";

	public UserAdapter3(Activity context, CacheCallBack<User> cacheCallBack) {
		super(context, cacheCallBack);
	}

	@Override
	public ItemView createView(int position, ViewGroup parent) {
		return new ItemView(new UserView(context, resources));
	}

	static class ItemView extends CacheItemView<User, UserView> {

		public ItemView(UserView bv) {
			super(bv);
		}
		
	}
	
}