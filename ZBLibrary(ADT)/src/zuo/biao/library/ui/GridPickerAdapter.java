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

package zuo.biao.library.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.bean.GridPickerItemBean;
import zuo.biao.library.util.StringUtil;

/**网格选择器adapter
 * @author Lemon
 * @use new GridPickerAdapter
 */
public class GridPickerAdapter extends BaseAdapter {
//	private static final String TAG = "GridPickerAdapter";

	private OnItemSelectedListener onItemSelectedListener;
	public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
		this.onItemSelectedListener = onItemSelectedListener;
	}

//	private Activity context;//一般Activity可改为使用该adapter的Activity名，便于在这个adapter里使用Activity里的方法
	private List<GridPickerItemBean> list;//传进来的数据,这里的String类型可换成其他类型
	private int currentPosition;//初始选中位置
	private int height;//item高度
	private LayoutInflater inflater;//布局解释器,用来实例化列表的item的界面
	private Resources resources;
	public GridPickerAdapter(Activity context, List<GridPickerItemBean> list, int currentPosition, int height) {
//		this.context = context;
		this.list = list;
		this.currentPosition = currentPosition;
		this.height = height;

		inflater = LayoutInflater.from(context);
		resources = context.getResources();
	}

	public int getCurrentPosition() {
		return currentPosition;
	}
	
	public String getCurrentItemName() {
		return StringUtil.getTrimedString(getItem(getCurrentPosition()).getValue());
	}

	
	private HashMap<Integer, Boolean> hashMap;//实现选中标记的列表，不需要可以删除
	/**标记List<String>中的值是否已被选中。
	 * 不需要可以删除
	 * 要放到constructor【这个adapter只有ModleAdapter(Context context, List<Object> list)这一个constructor】里去
	 * @param list
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	private void initCheck() {
		hashMap = new HashMap<Integer, Boolean>();
		for (int i = 0; i < list.size(); i++) {
			hashMap.put(i, false);
		}
	}

	public List<GridPickerItemBean> getList() {
		return list;
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

	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public GridPickerItemBean getItem(int position) {
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}


	//getView的常规写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	private LayoutParams layoutParams;
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.grid_picker_item, parent, false);
			holder = new ViewHolder();

			holder.tv = (TextView) convertView.findViewById(R.id.tvGridPickerItem);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final GridPickerItemBean gpb = getItem(position);

		holder.tv.setText(StringUtil.getTrimedString(gpb.getValue()));
		if (gpb.getEnabled() == false) {
			holder.tv.setTextColor(resources.getColor(R.color.gray_2));
			convertView.setBackgroundResource(R.color.alpha_1);
		} else {
			convertView.setBackgroundResource(R.color.alpha_complete);
			holder.tv.setTextColor(resources.getColor(R.color.black));
			holder.tv.setBackgroundResource(position == currentPosition ? R.drawable.green_rounded_rectangle_normal : R.drawable.null_drawable);
		}

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (gpb.getEnabled()) {
					currentPosition = position;
					if (onItemSelectedListener != null) {
						onItemSelectedListener.onItemSelected(null, v, position, getItemId(position));
					}
					notifyDataSetChanged();
				}
			}
		});

		if (height > 0) {
			layoutParams = convertView.getLayoutParams();
			layoutParams.height = height;
			convertView.setLayoutParams(layoutParams);
		}

		return convertView;
	}

	public class ViewHolder {
		public TextView tv;
	}
	//getView的常规写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	/**刷新列表
	 * 建议使用refresh(null)替代notifyDataSetChanged();
	 * @param list
	 */
	public void refresh(List<GridPickerItemBean> list) {
		if (list != null && list.size() > 0) {
			this.list = list;
			initCheck();
		}
		notifyDataSetChanged();
	}


}
