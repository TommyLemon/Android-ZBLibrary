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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.bean.KeyValueBean;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**通用网格Adapter
 * 适用于gridView
 * @author Lemon
 * @use new GridAdapter
 */
public class GridAdapter extends BaseAdapter {
	private static final String TAG = "GridAdapter";

	private Activity context;//一般Activity可改为使用该adapter的Activity名，便于在这个adapter里使用Activity里的方法
	private List<KeyValueBean> list;//传进来的数据,这里的String类型可换成其他类型
	private HashMap<Integer, Boolean> hashMap;//实现选中标记的列表，不需要可以删除
	private int layoutRes;//item视图资源
	private boolean hasCheck = false;//是否使用标记功能
	private LayoutInflater inflater;//布局解释器,用来实例化列表的item的界面
	public GridAdapter(Activity context, List<KeyValueBean> list) {     
		this.context = context;
		this.layoutRes = R.layout.grid_item;
		
		initList(list);//初始化数据，不需要选中标记功能可以删除，但这里要加上“this.list = list;”这句
		inflater = LayoutInflater.from(context);
	}   
	public GridAdapter(Activity context, List<KeyValueBean> list, boolean hasCheck) {     
		this.context = context;
		this.layoutRes = R.layout.grid_item;
		this.hasCheck = hasCheck;

		initList(list);//初始化数据，不需要选中标记功能可以删除，但这里要加上“this.list = list;”这句
		inflater = LayoutInflater.from(context);
	}   
	public GridAdapter(Activity context, List<KeyValueBean> list, int layoutRes, boolean hasCheck) {     
		this.context = context;
		this.layoutRes = layoutRes;
		this.hasCheck = hasCheck;

		initList(list);//初始化数据，不需要选中标记功能可以删除，但这里要加上“this.list = list;”这句
		inflater = LayoutInflater.from(context);
	}   

	public List<KeyValueBean> getList() {
		return list;
	}
	//item标记功能，不需要可以删除<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public boolean getItemChecked(int position) {   
		if (hasCheck == false) {
			Log.e(TAG, "<<< !!! hasCheck == false  >>>>> ");
			return false;
		}
		return hashMap.get(position); 
	}      
	public void setItemChecked(int position, boolean isChecked) {   
		if (hasCheck == false) {
			Log.e(TAG, "<<< !!! hasCheck == false >>>>> ");
			return;
		}
		hashMap.put(position, isChecked);
		refresh(null);
	} 
	//item标记功能，不需要可以删除>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//建议使用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public void addItem(KeyValueBean object) {
		list.add(object);
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

	public int selectedCount = 0;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(layoutRes, parent, false);

			holder = new ViewHolder();
			holder.ivHead = (ImageView) convertView.findViewById(R.id.ivGridItemHead);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvGridItemName);
			if (hasCheck == true) {
				holder.ivCheck = (ImageView) convertView.findViewById(R.id.ivGridItemCheck);
			}

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final KeyValueBean cgb = getItem(position);
		final String imageUrl = cgb.getKey();
		final String name = cgb.getValue();

		if (StringUtil.isNotEmpty(imageUrl, true) == false) { 
			holder.ivHead.setBackgroundResource(R.color.alpha_3);
		} else {
//			ImageLoadUtils.loadImageFromUrl(imageUrl.endsWith(Util.IMAGE_URL_SUFFIX_SMALL)
//					? imageUrl : imageUrl + Util.IMAGE_URL_SUFFIX_SMALL, holder.ivHead);
		}

		if (name == null || "".equals(name.trim())) {
			holder.tvName.setVisibility(View.GONE);
		} else {
			holder.tvName.setVisibility(View.VISIBLE);
			holder.tvName.setText("" + name);
		}

		if (hasCheck == true) {
			holder.ivCheck.setVisibility(View.VISIBLE);

			holder.ivCheck.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (getItemChecked(position) == false) {
						setItemChecked(position, true);
						((BaseActivity) context).showShortToast("选择了第 " + String.valueOf(position) + " 个item name=" + name);
					} else {
						setItemChecked(position, false);
						Log.i(TAG, "取消选择第 " + String.valueOf(position) + " 个item name=" + name);
					}
				}
			});
		}

		return convertView;
	}

	public class ViewHolder {  
		public ImageView ivHead;
		public TextView tvName;
		public ImageView ivCheck;
	}


	/**刷新列表，建议使用refresh(null)
	 * @param list
	 */
	public void refresh(List<KeyValueBean> list) {
		if (list != null && list.size() > 0) {
			initList(list);
		}
		if (hasCheck == true) {
			selectedCount = 0;
			for (int i = 0; i < this.list.size(); i++) {
				if (getItemChecked(i) == true) {
					selectedCount ++;
				}
			}
		}
		notifyDataSetChanged();
	}

	/**标记List<String>中的值是否已被选中。
	 * 不需要可以删除，但“this.list = list;”这句
	 * 要放到constructor【这个adapter只有ModleAdapter(Context context, List<Object> list)这一个constructor】里去
	 * @param hashMap
	 * @param list
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	private void initList(List<KeyValueBean> list) {
		this.list = list;
		if (hasCheck == true) {
			hashMap = new HashMap<Integer, Boolean>();
			for (int i = 0; i < list.size(); i++) {
				hashMap.put(i, false);
			}
		}
	}

}
