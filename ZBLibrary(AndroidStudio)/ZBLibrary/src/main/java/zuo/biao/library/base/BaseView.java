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
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;


/**基础自定义View，适合任何View
 * <br /> 可以用于Adapter内的ItemView，也可以单独作为一个组件使用
 * @author Lemon
 * @param <T> 数据模型(model/JavaBean)类
 * @see #onDataChangedListener
 * @see #onDestroy
 * @use extends BaseView<T>, 具体参考.DemoView
 */
public abstract class BaseView<T> extends RecyclerView.ViewHolder {
	private static final String TAG = "BaseView";

	/**数据改变回调接口
	 * (Object) getData() - 改变的数据
	 */
	public interface OnDataChangedListener {
		void onDataChanged();
	}

	public OnDataChangedListener onDataChangedListener;//数据改变回调监听回调的实例
	/**设置数据改变事件监听回调
	 * @param listener
	 */
	public BaseView<T> setOnDataChangedListener(OnDataChangedListener listener) {
		onDataChangedListener = listener;
		return this;
	}


	/**
	 * @param context
	 * @param layoutResId
	 * @see #BaseView(Activity, int, ViewGroup)
	 */
	public BaseView(Activity context, @LayoutRes int layoutResId) {
		this(context, layoutResId, null);
	}

	/**
	 * @param context
	 * @param layoutResId
	 * @param parent TODO 如果itemView不能占满宽度 或 高度不对，一般是RecyclerView的问题，可通过传parent解决
	 */
	public BaseView(Activity context, @LayoutRes int layoutResId, ViewGroup parent) {
		this(context, context.getLayoutInflater().inflate(layoutResId, parent, false));
	}
	/**
	 * 传入的Activity,可在子类直接使用
	 */
	public final Activity context;
	/**
	 * @param context
	 * @param itemView
	 */
	public BaseView(Activity context, View itemView) {
		super(itemView);
		this.context = context;
	}





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
	public <V extends View> V findView(int id, OnClickListener listener) {
		V v = findView(id);
		v.setOnClickListener(listener);
		return v;
	}
	/**通过id查找并获取控件，并setOnClickListener
	 * @param id
	 * @param listener
	 * @return
	 */
	public <V extends View> V findViewById(int id, OnClickListener listener) {
		return findView(id, listener);
	}

	public T data = null;
	/**
	 * data在列表中的位置
	 * @must 只使用bindView(int position, T data)方法来设置position，保证position与data对应正确
	 */
	public int position = 0;
	/**
	 * 视图类型，部分情况下需要根据viewType使用不同layout，对应Adapter的itemViewType
	 */
	public int viewType = 0;

	/**创建一个新的View
	 * @return
	 */
	public View createView() {
		return itemView;
	}

	/**获取itemView的宽度
	 * @warn 只能在createView后使用
	 * @return
	 */
	public int getWidth() {
		return itemView.getWidth();
	}
	/**获取itemView的高度
	 * @warn 只能在createView后使用
	 * @return
	 */
	public int getHeight() {
		return itemView.getHeight();
	}





	/**设置并显示内容，建议在子类bindView内this.data = data;
	 * @warn 只能在createView后使用
	 * @param data - 传入的数据
	 * @param position - data在列表中的位置
	 * @param viewType - 视图类型，部分情况下需要根据viewType使用不同layout
	 */
	public void bindView(T data, int position, int viewType) {
		this.position = position;
		this.viewType = viewType;
		bindView(data);
	}
	/**设置并显示内容，建议在子类bindView内this.data = data;
	 * @warn 只能在createView后使用
	 * @param data_ - 传入的数据
	 */
	public void bindView(T data_) {
		if (data_ == null) {
			Log.w(TAG, "bindView data_ == null");
		}
		this.data = data_;
	}

	/**获取可见性
	 * @warn 只能在createView后使用
	 * @return 可见性 (View.VISIBLE, View.GONE, View.INVISIBLE);
	 */
	public int getVisibility() {
		return itemView.getVisibility();
	}
	/**设置可见性
	 * @warn 只能在createView后使用
	 * @param visibility - 可见性 (View.VISIBLE, View.GONE, View.INVISIBLE);
	 */
	public void setVisibility(int visibility) {
		itemView.setVisibility(visibility);
	}


	/**设置背景
	 * @warn 只能在createView后使用
	 * @param resId
	 */
	public void setBackground(int resId) {
		itemView.setBackgroundResource(resId);
	}


	//	/**性能不好
	//	 * @param id
	//	 * @param s
	//	 */
	//	public void setText(int id, String s) {
	//		TextView tv = (TextView) findViewById(id);
	//		tv.setText(s);
	//	}



	//resources方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public Resources resources;
	public final Resources getResources() {
		if(resources == null) {
			resources = context.getResources();
		}
		return resources;
	}

	public String getString(int id) {
		return getResources().getString(id);
	}
	public int getColor(int id) {
		return getResources().getColor(id);
	}
	public Drawable getDrawable(int id) {
		return getResources().getDrawable(id);
	}
	public float getDimension(int id) {
		return getResources().getDimension(id);
	}
	//resources方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//show short toast 方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	/**快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
	 * @param stringResId
	 */
	public void showShortToast(int stringResId) {
		CommonUtil.showShortToast(context, stringResId);
	}
	/**快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
	 * @param string
	 */
	public void showShortToast(String string) {
		CommonUtil.showShortToast(context, string);
	}
	//show short toast 方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//启动新Activity方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**打开新的Activity，向左滑入效果
	 * @param intent
	 */
	public void toActivity(final Intent intent) {
		CommonUtil.toActivity(context, intent);
	}
	/**打开新的Activity
	 * @param intent
	 * @param showAnimation
	 */
	public void toActivity(final Intent intent, final boolean showAnimation) {
		CommonUtil.toActivity(context, intent, showAnimation);
	}
	/**打开新的Activity，向左滑入效果
	 * @param intent
	 * @param requestCode
	 */
	public void toActivity(final Intent intent, final int requestCode) {
		CommonUtil.toActivity(context, intent, requestCode);
	}
	/**打开新的Activity
	 * @param intent
	 * @param requestCode
	 * @param showAnimation
	 */
	public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
		CommonUtil.toActivity(context, intent, requestCode, showAnimation);
	}
	//启动新Activity方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**销毁并回收内存，建议在对应的View占用大量内存时使用
	 * @warn 只能在UI线程中调用
	 */
	public void onDestroy() {
		if (itemView != null) {
			try {
				itemView.destroyDrawingCache();
			} catch (Exception e) {
				Log.w(TAG, "onDestroy  try { itemView.destroyDrawingCache();" +
						" >> } catch (Exception e) {\n" + e.getMessage());
			}
		}

		onDataChangedListener = null;

		data = null;
		position = 0;

	}

}
