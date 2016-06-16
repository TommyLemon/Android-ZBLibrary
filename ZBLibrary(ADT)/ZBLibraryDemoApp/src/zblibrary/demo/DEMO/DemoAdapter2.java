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

import java.util.List;

import zuo.biao.library.base.BaseAdapter;
import zuo.biao.library.bean.Entry;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/** 使用方法：复制>粘贴>改名>改代码  */
/**adapter模板，比较方便
 * *适用于listView,gridView
 * @author Lemon
 * @use new DemoAdapter2(...),具体参考.DemoActivity(setList方法内)
 */
public class DemoAdapter2 extends BaseAdapter<Entry<String, String>> {
	//	private static final String TAG = "DemoAdapter";


	public DemoAdapter2(Activity context, List<Entry<String, String>> list) {
		super(context, list);
	}

	//getView使用自定义View的写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		DemoView demoView = convertView == null ? null : (DemoView) convertView.getTag();
		if (demoView == null) {
			demoView = new DemoView(context, resources);
			convertView = demoView.createView(inflater);

			convertView.setTag(demoView);
		}

		demoView.setView(position, getItem(position));

		return super.getView(position, convertView, parent);
	}

	//getView使用自定义View的写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}
