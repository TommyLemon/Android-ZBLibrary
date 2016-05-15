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
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import zuo.biao.library.interfaces.OnReachViewBorderListener;

/**基础http列表Adapter，可实现预加载
 * @author Lemon
 * @param <T> model(JavaBean)类名
 * @use 1.extends BaseHttpAdapter<T>;
 *      2.重写getView并在getView中最后 return super.getView(position, convertView, parent);
 *      3.在使用子类的类中调用子类setOnReachViewBorderListener方法（这个方法就在这个类）
 */
public abstract class BaseHttpAdapter<T> extends BaseAdapter<T> {
	//	private static final String TAG = "BaseHttpAdapter";

	public BaseHttpAdapter(Activity context, List<T> list) {
		super(context, list);
	}

	protected OnReachViewBorderListener onReachViewBorderListener;
	public void setOnReachViewBorderListener(OnReachViewBorderListener onReachViewBorderListener) {
		this.onReachViewBorderListener = onReachViewBorderListener;
	}


	/**
	 * 预加载提前数
	 * @use 可在子类getView前赋值;
	 */
	public static int PRELOAD_NUM = 1;

	/**获取item对应View的方法
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 * @use 子类的getView中最后 return super.getView(position, convertView, parent);
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (onReachViewBorderListener != null && position >= getCount() - 1 - PRELOAD_NUM) {
			onReachViewBorderListener.onReach(OnReachViewBorderListener.TYPE_BOTTOM, parent);
		}
		return convertView;
	}

}
