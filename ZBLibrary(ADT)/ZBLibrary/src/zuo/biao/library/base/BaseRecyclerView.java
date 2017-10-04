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
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseView.OnViewClickListener;

/**基础自定义RecyclerView
 * @author Lemon
 * @param <T> 数据模型(model/JavaBean)类
 * @see OnViewClickListener
 * @use extends RecyclerView<T>, 具体参考.DemoRecyclerView
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
