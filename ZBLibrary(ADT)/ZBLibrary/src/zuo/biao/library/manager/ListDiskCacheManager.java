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

package zuo.biao.library.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import zuo.biao.library.base.BaseApplication;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.Json;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**列表缓存管理类
 * @author Lemon
 * @use ListDiskCacheManager.getInstance().xxx  ,具体参考.BaseHttpListActivity(onHttpRequestSuccess方法内)
 */
public class ListDiskCacheManager {
	private static final String TAG = "ListDiskCacheManager";

	public static final String CACHE_PATH = DataKeeper.ROOT_SHARE_PREFS_ + "CACHE_PATH";

	private Context context;
	private ListDiskCacheManager(Context context) {
		this.context = context;
	}

	private static ListDiskCacheManager manager;
	public static synchronized ListDiskCacheManager getInstance() {
		if (manager == null) {
			manager = new ListDiskCacheManager(BaseApplication.getInstance());
		}
		return manager;
	}


	public <T> String getClassPath(Class<T> clazz) {
		return clazz == null ? null : CACHE_PATH + clazz.getName();
	}

	private SharedPreferences getSharedPreferences(String path) {
		return StringUtil.isNotEmpty(path, true) == false
				? null : context.getSharedPreferences(StringUtil.getTrimedString(path), Context.MODE_PRIVATE);
	}


	public static final String NAME_USER = "USER";
	public static final String NAME_WORK = "WORK";


	/**
	 * 数据列表
	 */
	public static final String KEY_LIST = "LIST";

	/**
	 * 数据分组,自定义
	 */
	public static final String KEY_GROUP_ = "GROUP_";
	/**
	 * 分组中id列表每页数量
	 */
	public static final String KEY_PAGE_SIZE = "KEY_PAGE_SIZE";
	/**
	 * 分组中id列表,用json string的形式储存（避免排序问题）
	 */
	public static final String KEY_ID_LIST = "KEY_ID_LIST";


	/**获取列表
	 * @param clazz
	 * @param group
	 * @param start
	 * @return
	 */
	public <T> List<T> getList(Class<T> clazz, String group, int start) {
		Log.i(TAG, "getList  group = " + group +"; start = " + start);
		List<String> idList = getIdList(clazz, group);
		int end = start + getPageSize(clazz, group);
		if (end <= start || idList == null || idList.size() < end) {
			Log.e(TAG, "getList  end <= start || idList == null || idList.size() < end >> return null;");
			return null;
		}
		idList = idList.subList(start, end);

		ListDiskCache<T> cache = new ListDiskCache<>(context, clazz, getClassPath(clazz) + KEY_LIST);

		List<T> list = new ArrayList<T>();
		T data;
		for (String id : idList) {
			data = cache.get(id);
			if (data != null) {
				list.add(data);
			}
		}

		Log.i(TAG, "getList  return list; list.size() = " + list.size());

		return list;
	}



	/**获取每页数量
	 * @param clazz
	 * @param group
	 * @return
	 */
	private <T> int getPageSize(Class<T> clazz, String group) {
		SharedPreferences sp = getSharedPreferences(
				getClassPath(clazz) + KEY_GROUP_ + StringUtil.getTrimedString(group));
		return sp == null ? null : sp.getInt(KEY_PAGE_SIZE, 0);
	}

	/**获取id列表
	 * @param clazz
	 * @param group
	 * @return
	 */
	public <T> List<String> getIdList(Class<T> clazz, String group) {
		SharedPreferences sp = getSharedPreferences(
				getClassPath(clazz) + KEY_GROUP_ + StringUtil.getTrimedString(group));
		return sp == null ? null : Json.parseArray(sp.getString(KEY_ID_LIST, null), String.class);
	}

	/**保存列表
	 * @param clazz 类
	 * @param group 分组
	 * @param map 数据表
	 * @param start 存储起始位置,[start, start + map.size()]中原有的将被替换
	 * @param pageSize 每页大小
	 */
	public <T> void saveList(Class<T> clazz, String group, LinkedHashMap<String, T> map, int start, int pageSize) {
		if (clazz == null || map == null || map.size() <= 0 || StringUtil.isNotEmpty(group, true) == false) {
			Log.e(TAG, "saveList  clazz == null || map == null || map.size() <= 0" +
					" || StringUtil.isNotEmpty(group, true) == false >> return;");
			return;
		}
		if (start < 0) {
			start = 0;
		}

		Log.i(TAG, "saveList  group = " + group + "; map.size() = " + map.size()
				+ "; start = " + start +"; pageSize = " + pageSize);
		List<String> newIdList = new ArrayList<String>(map.keySet());//用String而不是Long，因为订单Order的id超出Long的最大值
		//		if (newIdList != null) {
		Log.i(TAG, "saveList newIdList.size() = " + newIdList.size()
				+ "; start save <<<<<<<<<<<<<<<<<\n ");

		final String CLASS_PATH = getClassPath(clazz);

		//保存至分组<<<<<<<<<<<<<<<<<<<<<<<<<
		SharedPreferences sp = getSharedPreferences(CLASS_PATH + KEY_GROUP_ + StringUtil.getTrimedString(group));
		//			sp.edit().putString(KEY_GROUP, group);
		Editor editor = sp.edit();

		//			Log.i(TAG, "\n saveList pageSize = " + getPageSize(clazz, group) + " <<<<<<<<");
		//列表每页大小
		if (pageSize > sp.getInt(KEY_PAGE_SIZE, 0)) {
			editor.remove(KEY_PAGE_SIZE).putInt(KEY_PAGE_SIZE, pageSize);
		}
		//			Log.i(TAG, "\n saveList pageSize = " + getPageSize(clazz, group) + ">>>>>>>>>");

		//id列表
		List<String> idList = Json.parseArray(sp.getString(KEY_ID_LIST, null), String.class);
		if (idList == null) {
			idList = new ArrayList<>();
		}
		//			Log.i(TAG, "\n saveList idList.size() = " + idList.size() + " <<<<<<<<");
		for (int i = start; i < start + newIdList.size(); i++) {
			if (i < idList.size()) {
				idList.set(i, newIdList.get(i - start));
			} else {
				idList.add(newIdList.get(i - start));
			}
		}
		editor.remove(KEY_ID_LIST).putString(KEY_ID_LIST, Json.toJSONString(idList));

		//			Log.i(TAG, "\n saveList idList.size() = " + getIdList(clazz, group).size() + ">>>>>>>>>");

		editor.commit();
		//保存至分组>>>>>>>>>>>>>>>>>>>>>>>>>

		//保存所有数据<<<<<<<<<<<<<<<<<<<<<<<<<
		ListDiskCache<T> listDiskCache = new ListDiskCache<>(context, clazz, CLASS_PATH + KEY_LIST);
		for (String id: newIdList) {
			if (id != null) {
				listDiskCache.save(id, map.get(id));
			}
		}
		//保存所有数据>>>>>>>>>>>>>>>>>>>>>>>>>

		Log.i(TAG, "\n saveList listDiskCache.getSize() = " + listDiskCache.getSize()
				+ "; end save >>>>>>>>>>>> ");
		//		}

	}

}
