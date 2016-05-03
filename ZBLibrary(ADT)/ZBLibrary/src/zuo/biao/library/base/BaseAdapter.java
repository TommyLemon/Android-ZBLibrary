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

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.util.CommonUtil;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;

/**基础Adapter
 * @author Lemon
 * @param <T> model(JavaBean)类名
 * @use extends BaseAdapter<T>, 具体参考.DemoAdapter
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
	//	private static final String TAG = "BaseAdapter";

	/**
	 * 管理整个界面的Activity实例
	 */
	protected Activity context;
	/**
	 * 传进来的数据列表
	 */
	protected List<T> list;
	/**
	 * 布局解释器,用来实例化列表的item的界面
	 */
	protected LayoutInflater inflater;
	public BaseAdapter(Activity context, List<T> list) {
		this.context = context;
		this.list = new ArrayList<>(list);

		inflater = context.getLayoutInflater();
	}

	public List<T> getList() {
		return list;
	}

	@Override
	public int getCount() {
		return list.size();
	}
	/**获取item数据
	 * @warn 处于性能考虑，这里不判断position，应在adapter外判断
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

	/**刷新列表
	 * 建议使用refresh(null)替代notifyDataSetChanged();
	 * @param list 什么时候开始list为空也不会崩溃了？？
	 */
	public synchronized void refresh(List<T> list) {
		if (list != null && list.size() > 0) {
			this.list = new ArrayList<>(list);
		}
		notifyDataSetChanged();
	}





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
