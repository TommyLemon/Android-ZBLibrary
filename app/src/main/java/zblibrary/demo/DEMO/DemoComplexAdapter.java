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

import android.app.Activity;
import android.view.ViewGroup;

import zuo.biao.library.base.BaseAdapter;
import zuo.biao.library.model.Entry;


/** 使用方法：复制>粘贴>改名>改代码 */

/**adapter模板
 * <br> 适用于几乎所有列表、表格，包括：
 * <br> 1.RecyclerView及其子类
 * <br> 2.ListView,GridView等AbsListView的子类
 * @author Lemon
 * @use 修改.ItemView代码 >> new DemoAdapter(...),具体参考.DemoActivity(setList方法内)
 */
public class DemoComplexAdapter extends BaseAdapter<Entry<String, String>, DemoComplexView> {

	public DemoComplexAdapter(Activity context) {
		super(context);
	}

	@Override
	public DemoComplexView createView(int position, ViewGroup parent) {
		return new DemoComplexView(context);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}


}
