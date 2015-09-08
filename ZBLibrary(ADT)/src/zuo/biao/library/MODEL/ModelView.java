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

package zuo.biao.library.MODEL;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.bean.KeyValueBean;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**使用方法：复制>粘贴>改名>改代码  */
/**通用自定义View模板，当View比较庞大复杂且使用次数>=2时建议使用
 * @author Lemon
 * @warn 复制到其它工程内使用时务必修改import zuo.biao.library.R;的文件路径（这里是zuo.biao.library）为所在应用包名
 * @use
	ModelView modleView = new ModelView(context, inflater);
	adapter中使用convertView = modleView.getView();//[具体见.ModelAdapter] 或  其它类中使用 
	containerView.addView(modleView.getConvertView());
	modleView.setView(object);
	modleView.setOnDataChangedListener(onDataChangedListener);object = modleView.getData();//非必需
	modleView.setOnClickListener(onClickListener);//非必需
	...
 */
public class ModelView extends BaseView<KeyValueBean> implements OnClickListener {
	private static final String TAG = "ModelView";

	public ModelView(Activity context, LayoutInflater inflater) {
		super(context, inflater);
	}


	public ImageView ivModelViewHead;
	public TextView tvModelViewName;
	public TextView tvModelViewNumber;
	@SuppressLint("InflateParams")
	@Override
	public View getView() {
		//model_view改为你所需要的layout文件
		convertView = inflater.inflate(R.layout.model_view, null);

		//示例代码<<<<<<<<<<<<<<<<
		ivModelViewHead = (ImageView) convertView.findViewById(R.id.ivModelViewHead);
		tvModelViewName = (TextView) convertView.findViewById(R.id.tvModelViewName);
		tvModelViewNumber = (TextView) convertView.findViewById(R.id.tvModelViewNumber);
		//示例代码>>>>>>>>>>>>>>>>

		return convertView;
	}



	private KeyValueBean keyValueBean;//传进来的数据
	@Override
	public KeyValueBean getData() {//Object可以改为任意类型，这里改为CommonKeyValueBean
		return keyValueBean;
	}
	
	@Override
	public void setView(KeyValueBean ckvb){
		if (ckvb == null) {
			Log.e(TAG, "setInerView ckvb == null >> return; ");
			return;
		}
		this.keyValueBean = (KeyValueBean) ckvb;

		tvModelViewName.setText("" + keyValueBean.getKey());
		tvModelViewNumber.setText("" + keyValueBean.getValue());

		//示例代码<<<<<<<<<<<<<<<<
		ivModelViewHead.setOnClickListener(this);
		tvModelViewName.setOnClickListener(this);
		//示例代码>>>>>>>>>>>>>>>>
	}

	/**刷新界面，refresh符合习惯
	 * @param ckvb
	 */
	public void refresh(final KeyValueBean ckvb) {
		setView(ckvb);
	}

	@Override
	public void onClick(View v) {
		//在其它类的回调接口内处理
		if (onClickListener != null) {
			onClickListener.onClick(v);
		}
		// 或 在本地处理
		//		switch (v.getId()) {
		//		case R.id.tvModelViewName:
		//			keyValueBean.setKey("New " + keyValueBean.getKey());
		//			refresh(keyValueBean);
		//			if (onDataChangedListener != null) {
		//				onDataChangedListener.onDataChanged();
		//			}
		//			break;
		//		default:
		//			break;
		//		}
		//Library内switch方法中case R.id.idx会报错
		if (v.getId() == R.id.tvModelViewName) {
			keyValueBean.setKey("New " + keyValueBean.getKey());
			refresh(keyValueBean);
			if (onDataChangedListener != null) {
				onDataChangedListener.onDataChanged();
			}
		}
	}


}
