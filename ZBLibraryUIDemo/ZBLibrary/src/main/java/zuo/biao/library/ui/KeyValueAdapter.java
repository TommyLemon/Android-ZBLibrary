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

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.bean.KeyValueBean;
import zuo.biao.library.util.StringUtil;

/**key-value型(两个都是String类型)Adapter，
 * @author Lemon
 * @use new KeyValueAdapter
 */
public class KeyValueAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Activity context;//一般Activity可改为使用该adapter的Activity名，便于在这个adapter里使用Activity里的方法
	private List<KeyValueBean> list;//传进来的数据,这里的String类型可换成其他类型
	private int layoutRes;//布局id
	private LayoutInflater inflater;//布局解释器,用来实例化列表的item的界面
	public KeyValueAdapter(Activity context, List<KeyValueBean> list) {     
		this.context = context;
		this.list = list;
		this.layoutRes = R.layout.key_value_item;

		inflater = LayoutInflater.from(context);
	}   
	public KeyValueAdapter(Activity context, List<KeyValueBean> list, int layoutRes) {     
		this.context = context;
		this.list = list;
		this.layoutRes = layoutRes;
		
		inflater = LayoutInflater.from(context);
	}   

	public List<KeyValueBean> getList() {
		return list;
	}
	//建议使用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public void addItem(KeyValueBean kvb) {
		list.add(kvb);
		refresh(list);
	}
	public void removeItem(int position) {
		List<KeyValueBean> newList = new ArrayList<KeyValueBean>();
		for (int i = 0; i < getCount(); i++) {
			if (i != position) {
				newList.add(list.get(i));
			}
		}
		refresh(newList);
	}
	//建议使用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	public int getCount() {  
		return list.size();     
	}
	@Override
	public KeyValueBean getItem(int position) {    
		return list.get(position); 
	}          
	@Override
	public long getItemId(int position) {
		return position;
	}           
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(layoutRes, parent, false);

			holder = new ViewHolder();
			holder.tvKey = (TextView) convertView.findViewById(R.id.tvKeyValueItemKey);
			holder.tvValue = (TextView) convertView.findViewById(R.id.tvKeyValueItemValue);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final KeyValueBean ckvb = getItem(position);
		holder.tvKey.setText(StringUtil.getTrimedString(ckvb.getKey()));
		holder.tvValue.setText(StringUtil.getTrimedString(ckvb.getValue()));

		return convertView;
	}

	public class ViewHolder {  
		public TextView tvKey;
		public TextView tvValue;
	}


	/**刷新列表，建议使用refresh(null)
	 * @param list
	 */
	public void refresh(List<KeyValueBean> list) {
		if (list != null && list.size() > 0) {
			this.list = list;
		}
		notifyDataSetChanged();
	}


}
