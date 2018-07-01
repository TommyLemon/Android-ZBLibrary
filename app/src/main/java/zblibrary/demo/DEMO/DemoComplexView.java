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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import zblibrary.demo.R;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.model.Entry;
import zuo.biao.library.util.StringUtil;

/**使用方法：复制>粘贴>改名>改代码  */
/**自定义View模板，当View比较庞大复杂(解耦效果明显)或使用次数>=2(方便重用)时建议使用
 * @author Lemon
 * @see DemoAdapter#createView
 * @use
 * <br> DemoComplexView DemoComplexView = new DemoComplexView(context, resources);
 * <br> adapter中使用:[具体参考.BaseViewAdapter(getView使用自定义View的写法)]
 * <br> convertView = DemoComplexView.createView(inflater);
 * <br> DemoComplexView.bindView(position, data);
 * <br> 或 其它类中使用:
 * <br> containerView.addView(DemoComplexView.createView(inflater));
 * <br> DemoComplexView.bindView(data);
 * <br> 然后
 * <br> DemoComplexView.setOnDataChangedListener(onDataChangedListener); data = DemoComplexView.getData();//非必需
 * <br> DemoComplexView.setOnClickListener(onClickListener);//非必需
 * <br> ...
 */
public class DemoComplexView extends BaseView<Entry<String, String>> implements OnClickListener {
	private static final String TAG = "DemoComplexView";

	public DemoComplexView(Activity context) {
		super(context, R.layout.demo_complex_view); //TODO demo_complex_view改为你所需要的layout文件
	}


	//示例代码<<<<<<<<<<<<<<<<
	public ImageView ivDemoComplexViewHead;
	public TextView tvDemoComplexViewName;
	public TextView tvDemoComplexViewNumber;
	//示例代码>>>>>>>>>>>>>>>>
	@Override
	public View createView() {
		//示例代码<<<<<<<<<<<<<<<<
		ivDemoComplexViewHead = findView(R.id.ivDemoComplexViewHead);
		tvDemoComplexViewName = findView(R.id.tvDemoComplexViewName, this);
		tvDemoComplexViewNumber = findView(R.id.tvDemoComplexViewNumber);
		//示例代码>>>>>>>>>>>>>>>>

		return super.createView();
	}


	@Override
	public void bindView(Entry<String, String> data_){
		//示例代码<<<<<<<<<<<<<<<<
		super.bindView(data_ != null ? data_ : new Entry<String, String>());

		Glide.with(context).load(data.getKey()).into(ivDemoComplexViewHead);

		tvDemoComplexViewName.setText("Name " + position);
		tvDemoComplexViewNumber.setText(StringUtil.getTrimedString(data.getValue()));
		//示例代码>>>>>>>>>>>>>>>>
	}


	//示例代码<<<<<<<<<<<<<<<<
	@Override
	public void onClick(View v) {
		if (data == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.tvDemoComplexViewName:
			tvDemoComplexViewName.setText("New " + StringUtil.getString(tvDemoComplexViewName));
			break;
		default:
			break;
		}
	}
	//示例代码>>>>>>>>>>>>>>>>


}
