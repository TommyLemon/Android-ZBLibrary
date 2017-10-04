package zuo.biao.library.base;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 17/10/4.
 */

public abstract class BaseRecyclerView<T> extends RecyclerView.ViewHolder {

    public Activity context;
    public BaseRecyclerView(Activity context, @LayoutRes int layoutResId) {
        super(context.getLayoutInflater().inflate(layoutResId, null));
        this.context = context;
    }

    protected List<View> onClickViewList;
    /**通过id查找并获取控件，使用时不需要强转
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findView(int id) {
        return (V) itemView.findViewById(id);
    }
    /**通过id查找并获取控件，使用时不需要强转
     * @param id
     * @return
     */
    public <V extends View> V findViewById(int id) {
        return findView(id);
    }
    /**通过id查找并获取控件，并setOnClickListener
     * @param id
     * @param listener
     * @return
     */
    public <V extends View> V findView(int id, View.OnClickListener listener) {
        V v = findView(id);
        v.setOnClickListener(listener);
        if (onClickViewList == null) {
            onClickViewList = new ArrayList<View>();
        }
        onClickViewList.add(v);
        return v;
    }
    /**通过id查找并获取控件，并setOnClickListener
     * @param id
     * @param listener
     * @return
     */
    public <V extends View> V findViewById(int id, View.OnClickListener listener) {
        return findView(id, listener);
    }

    protected T data = null;
    /**获取数据
     * @return
     */
    public T getData() {
        return data;
    }


    public abstract View createView(LayoutInflater inflater);

    public abstract void bindView(T data);
}
