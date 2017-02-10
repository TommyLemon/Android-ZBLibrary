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
import zblibrary.demo.DEMO.DemoAdapter2.ItemView;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.base.BaseViewAdapter;
import zuo.biao.library.model.Entry;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**使用方法：复制>粘贴>改名>改代码  */
/**adapter模板，最方便，建议使用
 * <br> 适用于ListView,GridView等AbsListView的子类
 * @author Lemon
 * @use 修改.ItemView代码 >> new DemoAdapter2(...),具体参考.DemoActivity(setList方法内)
 */
public class DemoAdapter2 extends BaseViewAdapter<Entry<String, String>, ItemView> {

	public DemoAdapter2(Activity context) {
		super(context);
	}

	@Override
	public ItemView createView(int position, ViewGroup parent) {
		return new ItemView(context, resources);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	
	/**item对应的View，可改为外部类
	 * @use 改代码
	 */
	static class ItemView extends BaseView<Entry<String, String>> implements OnClickListener {

		public ItemView(Activity context, Resources resources) {
			super(context, resources);
		}


		//示例代码<<<<<<<<<<<<<<<<
		public ImageView ivDemoViewHead;
		public TextView tvDemoViewName;
		public TextView tvDemoViewNumber;
		//示例代码>>>>>>>>>>>>>>>>
		@SuppressLint("InflateParams")
		@Override
		public View createView(LayoutInflater inflater) {
			//TODO demo_view改为你所需要的layout文件，可以根据viewType使用不同layout
			convertView = inflater.inflate(R.layout.demo_view, null);

			//示例代码<<<<<<<<<<<<<<<<
			ivDemoViewHead = findViewById(R.id.ivDemoViewHead, this);
			tvDemoViewName = findViewById(R.id.tvDemoViewName, this);
			tvDemoViewNumber = findViewById(R.id.tvDemoViewNumber);
			//示例代码>>>>>>>>>>>>>>>>

			return convertView;
		}

		@Override
		public void bindView(Entry<String, String> data){
			//示例代码<<<<<<<<<<<<<<<<
			this.data = data;//这里data传进来的只有adapter内item数据，可不判空

			tvDemoViewName.setText(StringUtil.getTrimedString(data.getKey()));
			tvDemoViewNumber.setText(StringUtil.getNoBlankString(data.getValue()));
			//示例代码>>>>>>>>>>>>>>>>
		}

		//示例代码<<<<<<<<<<<<<<<<
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ivDemoViewHead:
				toActivity(DemoFragmentActivity.createIntent(context, position)
						.putExtra(DemoFragmentActivity.INTENT_TITLE, data.getKey()));
				break;
			case R.id.tvDemoViewName:
				data.setKey("New " + data.getKey());
				bindView(data);
				break;
			default:
				break;
			}
		}
		//示例代码>>>>>>>>>>>>>>>>

	}

}
