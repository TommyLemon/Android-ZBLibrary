package zblibrary.demo.DEMO;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseRecyclerView;
import zuo.biao.library.interfaces.AdapterViewPresenter;

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
