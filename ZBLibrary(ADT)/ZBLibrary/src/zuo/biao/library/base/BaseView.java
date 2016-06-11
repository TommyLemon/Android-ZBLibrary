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

import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**基础自定义View
 * @author Lemon
 * @param <T> 数据模型(model/JavaBean)类。 null ? View.GONE : View.VISIBLE
 * @use extends BaseView<T>, 具体参考.DemoView
 */
public abstract class BaseView<T> {
	private static final String TAG = "BaseView";

	/**
	 * 传入的Activity,可在子类直接使用
	 */
	protected Activity context;
	protected Resources resources;
	public BaseView(Activity context, Resources resources) {
		this.context = context;
		this.resources = resources == null ? context.getResources() : resources;
	}

	/**数据改变回调接口
	 * (Object) getData() - 改变的数据
	 */
	public interface OnDataChangedListener {
		void onDataChanged();
	}

	public OnDataChangedListener onDataChangedListener;//数据改变回调监听类的实例
	/**设置数据改变事件监听类
	 * @param l
	 */
	public void setOnDataChangedListener(OnDataChangedListener l) {
		onDataChangedListener = l;
	}

	public OnTouchListener onTouchListener;//接触View回调监听类的实例
	/**设置接触View事件监听类
	 * @param l
	 */
	public void setOnTouchListener(OnTouchListener l) {
		onTouchListener = l;
	}

	public OnClickListener onClickListener;//点击View回调监听类的实例
	/**设置点击View事件监听类
	 * @param l
	 */
	public void setOnClickListener(OnClickListener l) {
		onClickListener = l;
	}

	public OnLongClickListener onLongClickListener;//长按View回调监听类的实例
	/**设置长按View事件监听类
	 * @param l
	 */
	public void setOnLongClickListener(OnLongClickListener l) {
		onLongClickListener = l;
	}



	/**
	 * 子类整个视图,可在子类直接使用
	 * @must getView方法内对其赋值且不能为null
	 */
	protected View convertView = null;

	/**通过id查找并获取控件，使用时不需要强转
	 * @param id
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public <V extends View> V findViewById(int id) {
		return (V) convertView.findViewById(id);
	}
	/**通过id查找并获取控件，并setOnClickListener
	 * @param id
	 * @param l
	 * @return
	 */
	public <V extends View> V findViewById(int id, OnClickListener l) {
		V v = findViewById(id);
		v.setOnClickListener(l);
		return v;
	}
	/**创建一个新的View
	 * @return
	 */
	public abstract View createView(@NonNull LayoutInflater inflater);

	/**获取convertView的宽度
	 * @return
	 */
	public int getWidth() {
		return convertView.getWidth();
	}
	/**获取convertView的高度
	 * @return
	 */
	public int getHeight() {
		return convertView.getHeight();
	}

	/**
	 * data在列表中的位置
	 * *只使用setView(int position, T data)方法来设置position，保证position与data对应正确
	 */
	protected int position = 0;
	/**获取data在列表中的位置
	 */
	public int getPosition() {
		return position;
	}
	
	protected T data = null;
	/**获取数据
	 * @return
	 */
	public T getData() {
		return data;
	}
	
	/**设置并显示内容
	 * @warn 只能在createView后使用
	 * @param position - data在列表中的位置
	 * @param data - 传入的数据
	 */
	public void setView(int position, T data) {
		this.position = position;
		setView(data);
	}
	/**设置并显示内容
	 * @warn 只能在createView后使用
	 * @param data - 传入的数据
	 */
	public abstract void setView(T data);

	public void setVisibility(int visibility) {
		convertView.setVisibility(visibility);
	}
	/**设置背景
	 * @param resId
	 */
	public void setBackground(int resId) {
		if (resId > 0 && convertView != null) {
			try {
				convertView.setBackgroundResource(resId);
			} catch (Exception e) {
				Log.e(TAG, "setBackground   try { convertView.setBackgroundResource(resId);" +
						" \n >> } catch (Exception e) { \n" + e.getMessage());
			}
		}
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
	public String getString(int id) {
		return resources.getString(id);
	}
	public int getColor(int id) {
		return resources.getColor(id);
	}
	public Drawable getDrawable(int id) {
		return resources.getDrawable(id);
	}
	public float getDimension(int id) {
		return resources.getDimension(id);
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



}
