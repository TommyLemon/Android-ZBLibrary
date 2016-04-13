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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseAdapter;
import zuo.biao.library.bean.KeyValueBean;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/** 使用方法：复制>粘贴>改名>改代码  */
/**adapter模板
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * 适用于listView,gridView
 * @author Lemon
 * @use new DemoAdapter
 */
public class DemoAdapter extends BaseAdapter<KeyValueBean> {
	private static final String TAG = "DemoAdapter";


	public DemoAdapter(Activity context, List<KeyValueBean> list) {
		this(context, list, false);
	}
	public DemoAdapter(Activity context, List<KeyValueBean> list, boolean showSelfDefineView) {
		super(context, list);

		initCheck();//初始化数据，不需要选中标记功能可以删除
	}

	private HashMap<Integer, Boolean> hashMap;//实现选中标记的列表，不需要可以删除
	/**标记List<String>中的值是否已被选中。
	 * 不需要可以删除
	 * 要放到constructor【这个adapter只有ModleAdapter(Context context, List<Object> list)这一个constructor】里去
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	private void initCheck() {
		hashMap = new HashMap<Integer, Boolean>();
		for (int i = 0; i < list.size(); i++) {
			hashMap.put(i, false);
		}
	}
	
	//item标记功能，不需要可以删除<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public boolean getItemChecked(int position) {
		return hashMap.get(position);
	}
	public void setItemChecked(int position, boolean isChecked) {
		hashMap.put(position, isChecked);
		notifyDataSetChanged();
	}
	//item标记功能，不需要可以删除>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	//getView的常规写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = convertView == null ? null : (ViewHolder) convertView.getTag();
		if (holder == null) {
			//TODO model_item改为你需要的
			convertView = inflater.inflate(R.layout.demo_item, parent, false);
			holder = new ViewHolder();

			holder.ivHead = (ImageView) convertView.findViewById(R.id.ivHead);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);

			convertView.setTag(holder);
		}

		final KeyValueBean ckvb = getItem(position);

		if (getItemChecked(position) == false) {
			holder.ivHead.setBackgroundResource(R.color.alpha_1);
		} else {
			holder.ivHead.setBackgroundResource(R.color.green);
		}

		holder.tvName.setText(StringUtil.getTrimedString(ckvb.getValue()));
		holder.tvNumber.setText(StringUtil.getNoBlankString(ckvb.getKey()));

		holder.ivHead.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getItemChecked(position) == false) {
					setItemChecked(position, true);
					Toast.makeText(context,
							"选择了第 " + String.valueOf(position) + " 个item name=" + ckvb.getKey(), Toast.LENGTH_SHORT).show();
				} else {
					setItemChecked(position, false);
					Log.i(TAG, "取消选择第 " + String.valueOf(position) + " 个item name=" + ckvb.getKey());
				}
			}
		});

		return convertView;
	}

	public class ViewHolder {

		public ImageView ivHead;
		public TextView tvName;
		public TextView tvNumber;
	}
	//getView的常规写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


//	//getView使用自定义View的写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		DemoView modelView = convertView == null ? null : (DemoView) convertView.getTag();
//		if (convertView == null) {
//			modelView = new DemoView(context, inflater);
//			convertView = modelView.getView();
//
//			convertView.setTag(modelView);
//		}
//
//		modelView.setView(getItem(position));
//
//		return convertView;
//	}
//
//	//getView使用自定义View的写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**刷新列表
	 * 建议使用refresh(null)替代notifyDataSetChanged();
	 * @param list 什么时候开始list为空也不会崩溃了？？
	 */
	@Override
	public synchronized void refresh(List<KeyValueBean> list) {
		if (list != null && list.size() > 0) {
			this.list = new ArrayList<>(list);
			initCheck();
		}
		notifyDataSetChanged();
	}

	

}
