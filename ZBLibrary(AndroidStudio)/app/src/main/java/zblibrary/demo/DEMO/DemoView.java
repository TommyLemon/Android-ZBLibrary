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
import zuo.biao.library.base.BaseView;
import zuo.biao.library.model.Entry;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**使用方法：复制>粘贴>改名>改代码  */
/**自定义View模板，当View比较庞大复杂(解耦效果明显)或使用次数>=2(方便重用)时建议使用
 * @author Lemon
 * @see DemoAdapter2#getView(int, View, android.view.ViewGroup)
 * @use
 * <br> DemoView demoView = new DemoView(context, resources);
 * <br> adapter中使用:[具体参考.BaseViewAdapter(getView使用自定义View的写法)]
 * <br> convertView = demoView.createView(inflater);
 * <br> demoView.bindView(position, data);
 * <br> 或 其它类中使用: 
 * <br> containerView.addView(demoView.createView(inflater));
 * <br> demoView.bindView(data);
 * <br> 然后
 * <br> demoView.setOnDataChangedListener(onDataChangedListener); data = demoView.getData();//非必需
 * <br> demoView.setOnClickListener(onClickListener);//非必需
 * <br> ...
 */
public class DemoView extends BaseView<Entry<String, String>> implements OnClickListener {
	private static final String TAG = "DemoView";

	public DemoView(Activity context, Resources resources) {
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
		ivDemoViewHead = findView(R.id.ivDemoViewHead, this);
		tvDemoViewName = findView(R.id.tvDemoViewName, this);
		tvDemoViewNumber = findView(R.id.tvDemoViewNumber);
		//示例代码>>>>>>>>>>>>>>>>

		return convertView;
	}


	@Override
	public void bindView(Entry<String, String> data){
		//示例代码<<<<<<<<<<<<<<<<
		if (data == null) {
			Log.e(TAG, "bindView data == null >> data = new Entry<>(); ");
			data = new Entry<String, String>();
		}
		this.data = data;

		tvDemoViewName.setText(StringUtil.getTrimedString(data.getKey()));
		tvDemoViewNumber.setText(StringUtil.getTrimedString(data.getValue()));
		//示例代码>>>>>>>>>>>>>>>>
	}


	//示例代码<<<<<<<<<<<<<<<<
	@Override
	public void onClick(View v) {
		if (onClickListener != null) {
			onClickListener.onClick(v);
			return;
		}
		if (data == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.tvDemoViewName:
			data.setKey("New " + data.getKey());
			bindView(data);
			if (onDataChangedListener != null) {
				onDataChangedListener.onDataChanged();
			}
			break;
		default:
			break;
		}
	}
	//示例代码>>>>>>>>>>>>>>>>


}
