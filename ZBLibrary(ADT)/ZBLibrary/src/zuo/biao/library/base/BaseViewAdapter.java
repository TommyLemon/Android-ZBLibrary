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

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/** 使用方法：复制>粘贴>改名>改代码  */
/**adapter模板
 * 适用于listView,gridView
 * @author Lemon
 * @param <T>
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use new BaseViewAdapter(...),具体参考.DemoActivity(setList方法内)
 */
public abstract class BaseViewAdapter<T, BV extends BaseView<T>> extends BaseAdapter<T> {
//	private static final String TAG = "BaseViewAdapter";

	public BaseViewAdapter(Activity context, List<T> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		@SuppressWarnings("unchecked")
		BV bv = (BV) (convertView == null ? null : convertView.getTag());
		if (bv == null) {
			bv = createView(position, convertView, parent);
			convertView = bv.createView(inflater);

			convertView.setTag(bv);
		}

		bv.setView(getItem(position));

		return super.getView(position, convertView, parent);
	}

	/**生成新的BV
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract BV createView(int position, View convertView, ViewGroup parent);

}
