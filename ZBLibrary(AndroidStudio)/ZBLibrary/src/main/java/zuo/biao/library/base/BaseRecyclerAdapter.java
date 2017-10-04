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

package zuo.biao.library.base;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.interfaces.AdapterViewPresenter;

/**基础RecyclerView Adapter
 * <br> 适用于RecyclerView及其子类
 * @author Lemon
 * @param <T> 数据模型(model/JavaBean)类
 * @param <BV> BaseRecyclerView的子类
 * @see #setOnViewClickListener
 * @use extends BaseRecyclerAdapter<T, BV>, 具体参考.DemoRecyclerAdapter
 */
public abstract class BaseRecyclerAdapter<T, BV extends BaseRecyclerView<T>> extends RecyclerView.Adapter<BV>
implements AdapterViewPresenter<BV> {

	/**
	 * 管理整个界面的Activity实例
	 */
	public Activity context;
	/**
	 * 布局解释器,用来实例化列表的item的界面
	 */
	public LayoutInflater inflater;
	/**
	 * 资源获取器，用于获取res目录下的文件及文件中的内容等
	 */
	public Resources resources;
	public BaseRecyclerAdapter(Activity context) {
		super();
		this.context = context;

		inflater = context.getLayoutInflater();
		resources = context.getResources();
	}

	/**
	 * 传进来的数据列表
	 */
	public List<T> list;
	public List<T> getList() {
		return list;
	}

	/**刷新列表
	 */
	public synchronized void refresh(List<T> list) {
		this.list = list == null ? null : new ArrayList<T>(list);
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return list.size();
	}


	public T getItem(int position) {
		return list.get(position);
	}


	@Override
	public BV onCreateViewHolder(ViewGroup parent, int position) {
		BV bv = createView(position, parent);
		bv.createView(inflater);
		return bv;
	}


	@Override
	public void onBindViewHolder(BV bv, int position) {
		bindView(position, bv);
	}

	@Override
	public void bindView(int position, BV bv) {
		bv.bindView(getItem(position));
	}


}
