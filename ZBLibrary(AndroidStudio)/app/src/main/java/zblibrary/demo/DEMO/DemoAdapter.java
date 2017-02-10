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

import zblibrary.demo.R;
import zuo.biao.library.base.BaseAdapter;
import zuo.biao.library.model.Entry;
import zuo.biao.library.util.StringUtil;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/** 使用方法：复制>粘贴>改名>改代码  */
/**adapter模板，最灵活且性能最好，但如果有setOnClickListener等事件就不方便了
 * <br> 适用于ListView,GridView等AbsListView的子类
 * @author Lemon
 * @use new DemoAdapter(...); 具体参考.DemoActivity(setList方法内)
 */
public class DemoAdapter extends BaseAdapter<Entry<String, String>> {
//	private static final String TAG = "DemoAdapter";


	public DemoAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//示例代码<<<<<<<<<<<<<<<<
		ViewHolder holder = convertView == null ? null : (ViewHolder) convertView.getTag();
		if (holder == null) {
			//TODO demo_item改为你所需要的layout文件
			convertView = inflater.inflate(R.layout.demo_item, parent, false);
			holder = new ViewHolder();

			holder.ivDemoItemHead = (ImageView) convertView.findViewById(R.id.ivDemoItemHead);
			holder.tvDemoItemName = (TextView) convertView.findViewById(R.id.tvDemoItemName);
			holder.tvDemoItemNumber = (TextView) convertView.findViewById(R.id.tvDemoItemNumber);

			convertView.setTag(holder);
		}

		final Entry<String, String> data = getItem(position);

		holder.tvDemoItemName.setText(StringUtil.getTrimedString(data.getValue()));
		holder.tvDemoItemNumber.setText(StringUtil.getNoBlankString(data.getKey()));

		return super.getView(position, convertView, parent);
		//示例代码>>>>>>>>>>>>>>>>
	}
	
	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	
	static class ViewHolder {
		//示例代码<<<<<<<<<<<<<<<<
		public ImageView ivDemoItemHead;
		public TextView tvDemoItemName;
		public TextView tvDemoItemNumber;
		//示例代码>>>>>>>>>>>>>>>>
	}
	

}
