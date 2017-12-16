package zuo.biao.library.base;

import android.app.Activity;
import android.content.res.Resources;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.interfaces.AdapterViewPresenter;
import zuo.biao.library.interfaces.OnLoadListener;
import zuo.biao.library.util.SettingUtil;


/**基础Adapter，基于SmartRefreshLayout的BaseRecyclerAdapter修改
 * <br> 适用于几乎所有列表、表格，包括：
 * <br> 1.RecyclerView及其子类
 * <br> 2.ListView,GridView等AbsListView的子类
 * @author SCWANG
 * @author Lemon
 * @warn 出于性能考虑，里面很多方法对变量(比如list)都没有判断，应在adapter外判断
 * @param <T> 数据模型(model/JavaBean)类
 * @param <BV> BaseView的子类，相当于ViewHolder
 * @use extends BaseAdapter<T, BV>, 具体参考.DemoAdapter
 */
public abstract class BaseAdapter<T, BV extends BaseView<T>> extends RecyclerView.Adapter<BV>
        implements ListAdapter, AdapterViewPresenter<BV> {


    private AdapterView.OnItemClickListener onItemClickListener;
    public BaseAdapter<T, BV> setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }
    private AdapterView.OnItemLongClickListener onItemLongClickListener;
    public BaseAdapter<T, BV> setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        return this;
    }


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
    public BaseAdapter(Activity context) {
        setHasStableIds(false);
        this.context = context;
        this.inflater = context.getLayoutInflater();
        this.resources = context.getResources();
    }



    //预加载，可不使用 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    protected OnLoadListener onLoadListener;
    /**设置加载更多的监听
     * @param onLoadListener
     */
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    /**
     * 预加载提前数。
     * <br > = 0 - 列表滚到底部(最后一个Item View显示)时加载更多
     * <br > < 0 - 禁用加载更多
     * <br > > 0 - 列表滚到倒数第preloadCount个Item View显示时加载更多
     * @use 可在子类getView被调用前(可以是在构造器内)赋值
     */
    protected int preloadCount = 0;

    //预加载，可不使用 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /**bv的显示方法
     * @param position
     * @param bv
     */
    @Override
    public void bindView(int position, BV bv) {
        bv.bindView(getItem(position), position, getItemViewType(position));
        if (SettingUtil.preload && onLoadListener != null && position >= getCount() - 1 - preloadCount) {
            onLoadListener.onLoadMore();
        }
    }




    /**
     * 传进来的数据列表
     */
    protected List<T> list;
    public List<T> getList() {
        return list;
    }
    /**刷新列表
     */
    public synchronized void refresh(List<T> list) {
        this.list = list == null ? null : new ArrayList<T>(list);
        notifyDataSetChanged();
    }




    //RecyclerAdapter <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Override
    public BV onCreateViewHolder(final ViewGroup parent, int viewType) {
        final BV bv = createView(viewType, parent);
        bv.createView();
        bv.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    AdapterView pv = parent instanceof AdapterView ? (AdapterView) parent : null;
                    onItemClickListener.onItemClick(pv, v, bv.position, getItemId(bv.position));
                }
            }
        });
        bv.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener == null) {
                    return false;
                }
                AdapterView pv = parent instanceof AdapterView ? (AdapterView) parent : null;
                return onItemLongClickListener.onItemLongClick(pv, v, bv.position, getItemId(bv.position));
            }
        });
        return bv;
    }


    @Override
    public void onBindViewHolder(BV bv, int position) {
        bindView(position, bv);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    //RecyclerAdapter >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







    //ListAdapter <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

//    public boolean hasStableIds() {
//        return false;
//    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyListDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BV bv = convertView == null ? null : (BV) convertView.getTag();
        if (bv == null) {
            bv = onCreateViewHolder(parent, getItemViewType(position));
            convertView = bv.itemView;
            convertView.setTag(bv);
        }
        onBindViewHolder(bv, position);
        return convertView;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        return getCount() <= 0;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
    /**获取item数据
     */
    @Override
    public T getItem(int position) {
        return list.get(position);
    }
    /**获取item的id，如果不能满足需求可在子类重写
     * @param position
     * @return position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    //ListAdapter >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}
