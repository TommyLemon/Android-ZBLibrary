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

package zuo.biao.library.DEMO;

import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseAdapter;
import zuo.biao.library.bean.KeyValueBean;
import zuo.biao.library.util.StringUtil;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/** 使用方法：复制>粘贴>改名>改代码  */
/**adapter模板
 * 适用于listView,gridView
 * @author Lemon
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use new DemoAdapter(...),具体参考.DemoActivity(setList方法内)
 */
public class DemoAdapter extends BaseAdapter<KeyValueBean> {
//	private static final String TAG = "DemoAdapter";


	public DemoAdapter(Activity context, List<KeyValueBean> list) {
		super(context, list);
	}


	//getView的常规写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
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

		final KeyValueBean ckvb = getItem(position);


		holder.tvDemoItemName.setText(StringUtil.getTrimedString(ckvb.getValue()));
		holder.tvDemoItemNumber.setText(StringUtil.getNoBlankString(ckvb.getKey()));

		return convertView;
	}

	public class ViewHolder {

		public ImageView ivDemoItemHead;
		public TextView tvDemoItemName;
		public TextView tvDemoItemNumber;
	}
	//getView的常规写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


//	//getView使用自定义View的写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		DemoView demoView = convertView == null ? null : (DemoView) convertView.getTag();
//		if (convertView == null) {
//			demoView = new DemoView(context, inflater);
//			convertView = demoView.getView();
//
//			convertView.setTag(demoView);
//		}
//
//		demoView.setView(getItem(position));
//
//		return convertView;
//	}
//
//	//getView使用自定义View的写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	

}
