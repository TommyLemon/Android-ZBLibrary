package zuo.biao.library.interfaces;

/**缓存回调
 * @param <M>
 */
public interface OnCacheCallBack<M> {
	/**
	 * 获取需要缓存的类
	 * @return null-不缓存
	 */
	abstract Class<M> getCacheClass();
	/**
	 * 获取缓存的分组
	 * @return 不含非空字符的String-不缓存
	 */
	abstract String getCacheGroup();
	/**
	 * 获取缓存数据的id，在非UI线程中s
	 * @param data
	 * @return "" + data.getId(); //不用long是因为某些数据(例如订单)的id超出long的最大值
	 */
	abstract String getCacheId(M data);
}