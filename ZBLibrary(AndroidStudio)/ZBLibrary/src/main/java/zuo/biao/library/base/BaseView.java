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

import zuo.biao.library.interfaces.DataGetter;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**基础自定义View,能规范代码格式以及省掉小部分代码。
 * @author Lemon
 * @param <T> 数据模型。null ? View.GONE : View.VISIBLE
 * @use extends BaseView<T>, 具体参考.DemoView
 */
public abstract class BaseView<T> implements DataGetter {
	private static final String TAG = "BaseView";

	/**
	 * 传入的Activity,可在子类直接使用
	 */
	protected Activity context;
	/**
	 * 传入的布局解释器,可在子类直接使用
	 * 虽然context.getLayoutInflater()或LayoutInflater.from(context)都可获得inflater
	 * ，但是在一个界面大量使用这种view（ListView,GridView等）时传一个公用的inflater能明显提高性能
	 */
	protected LayoutInflater inflater;
	protected Resources resources;
	public BaseView(Activity context, LayoutInflater inflater) {
		this.context = context;
		if (inflater == null) {
			inflater = context.getLayoutInflater();
		}
		this.inflater = inflater;
		this.resources = context.getResources();
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


	protected View findViewById(int id) {
		return convertView.findViewById(id);
	}

	/**
	 * 子类整个视图,可在子类直接使用
	 * @must getView方法内对其赋值且不能为null
	 */
	protected View convertView = null;
	/**获取View
	 * @return
	 */
	public abstract View getView();

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

	/**设置并显示内容
	 * @warn 只能在getView后使用
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
