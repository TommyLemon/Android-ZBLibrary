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

import zuo.biao.library.interfaces.OnResultListener;
import zuo.biao.library.manager.CacheManager;
import android.os.AsyncTask;

/**缓存读取器
 * @author Lemon
 * @use new CacheLoader<T>().execute(clazz, id, listener);
 */
public class CacheLoader<T> extends AsyncTask<Void, Void, T> {

	private Class<T> clazz;
	private String id;
	private OnResultListener<T> listener;
	public CacheLoader<T> execute(Class<T> clazz, String id, OnResultListener<T> listener) {
		this.clazz = clazz;
		this.id = id;
		this.listener = listener;
		execute();
		return this;
	}

	@Override
	protected T doInBackground(Void... params) {
		return CacheManager.getInstance().get(clazz, id);
	}

	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		listener.onResult(result);
	}
}
