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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseView.OnDataChangedListener;
import zuo.biao.library.bean.KeyValueBean;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/** 使用方法：复制>粘贴>改名>改代码  */
/**adapter模板
 * 适用于listView,gridView
 * @author Lemon
 * @warn 复制到其它工程内使用时务必修改import zuo.biao.library.R;的文件路径（这里是zuo.biao.library）为所在应用包名
 * @use new ModelAdapter
 */
public class ModelAdapter extends BaseAdapter {
	private static final String TAG = "ModelAdapter";

	private Activity context;//一般Activity可改为使用该adapter的Activity名，便于在这个adapter里使用Activity里的方法
	private List<KeyValueBean> list;//传进来的数据,这里的String类型可换成其他类型
	private boolean showSelfDefineView;
	public boolean getShowSelfDefineView() {
		return showSelfDefineView;
	}

	private LayoutInflater inflater;//布局解释器,用来实例化列表的item的界面
	//	private int selectedCount = 0;
	public ModelAdapter(Activity context, List<KeyValueBean> list) {
		this.context = context;
		this.list = list;
		this.showSelfDefineView = false;

		initCheck();//初始化数据，不需要选中标记功能可以删除
		inflater = LayoutInflater.from(context);
	}
	public ModelAdapter(Activity context, List<KeyValueBean> list, boolean showSelfDefineView) {
		this.context = context;
		this.list = list;
		this.showSelfDefineView = showSelfDefineView;

		initCheck();//初始化数据，不需要选中标记功能可以删除
		inflater = LayoutInflater.from(context);
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
	
	public List<KeyValueBean> getList() {
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
	//建议使用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public void addItem(KeyValueBean object) {
		list.add(object);
		refresh(list);
	}
	/**list.remove(position),adapter.remove(position)容易报错
	 * @param position
	 */
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


	//getView的常规写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(showSelfDefineView) {
			return getSelfDefineView(position, convertView, parent);
		}

		final ViewHolder holder;
		if (convertView == null) {
			//model_item改为你所需要的layout文件
			convertView = inflater.inflate(R.layout.model_item, parent, false);
			holder = new ViewHolder();

			holder.ivHead = (ImageView) convertView.findViewById(R.id.ivHead);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final KeyValueBean ckvb = getItem(position);

		if (getItemChecked(position) == false) {
			holder.ivHead.setBackgroundResource(R.color.alpha_1);
		} else {
			holder.ivHead.setBackgroundResource(R.color.green);
		}

		holder.tvName.setText(StringUtil.getTrimedString(ckvb.getValue()));
		holder.tvNumber.setText(StringUtil.getNoBlankString(ckvb.getKey()));

		holder.ivHead.setOnClickListener(new View.OnClickListener() {
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

	//getView使用ModleView的一种写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public View getSelfDefineView(final int position, View convertView, ViewGroup parent) {
		final ModelView modelView;
		if (convertView == null) {
			modelView = new ModelView(context, inflater);
			convertView = modelView.getView();

			convertView.setTag(modelView);
		} else {
			modelView = (ModelView) convertView.getTag();
		}

		modelView.setView(getItem(position));
		
		modelView.setOnDataChangedListener(new OnDataChangedListener() {

			@Override
			public void onDataChanged() {
				if (modelView.getData() != null) {
					list.set(position, modelView.getData());
					refresh(list);
				}
			}
		});
		
		modelView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				KeyValueBean ckvb = modelView.getData();
				if (ckvb == null) {
					return;
				}

				if (v.getId() == R.id.ivModelViewHead) {
					CommonUtil.showShortToast(context, ckvb.getKey() + " 的号码是 " + ckvb.getValue());
					CommonUtil.toActivity(context, ModelFragmentActivity.createIntent(context, ckvb.getKey()));
				}
			}
		});

		return convertView;
	}
	//getView使用ModleView的一种写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	/**刷新列表
	 * 建议使用refresh(null)替代notifyDataSetChanged();
	 * @param showSelfDefineView
	 */
	public void refresh(boolean showSelfDefineView) {
//		refresh(list, showSelfDefineView);
		this.showSelfDefineView = showSelfDefineView;
		notifyDataSetChanged();
	}
	/**刷新列表
	 * 建议使用refresh(null)替代notifyDataSetChanged();
	 * @param list
	 */
	public void refresh(List<KeyValueBean> list) {
		refresh(list, showSelfDefineView);
	}
	/**刷新列表
	 * 建议使用refresh(null)替代notifyDataSetChanged();
	 * @param list
	 * @param showSelfDefineView
	 */
	public void refresh(List<KeyValueBean> list, boolean showSelfDefineView) {
//		this.showSelfDefineView = showSelfDefineView;
		if (list != null && list.size() > 0) {
			this.list = list;
			initCheck();
		}
//		selectedCount = 0;
//		for (int i = 0; i < this.list.size(); i++) {
//			if (getItemChecked(i) == true) {
//				selectedCount ++;
//			}
//		}
		notifyDataSetChanged();
	}


}
