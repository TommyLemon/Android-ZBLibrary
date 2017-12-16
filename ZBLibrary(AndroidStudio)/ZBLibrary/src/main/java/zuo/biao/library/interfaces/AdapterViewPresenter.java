package zuo.biao.library.interfaces;

import android.view.ViewGroup;

public interface AdapterViewPresenter<V> {

	/**生成新的BV
	 * @param viewType
	 * @param parent
	 * @return
	 */
	public abstract V createView(int viewType, ViewGroup parent);

	/**设置BV显示
	 * @param position
	 * @param bv
	 * @return
	 */
	public abstract void bindView(int position, V bv);

}
