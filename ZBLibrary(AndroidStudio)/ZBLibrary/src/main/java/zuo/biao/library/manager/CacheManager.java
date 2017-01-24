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

/**磁盘缓存管理类
 * @author Lemon
 * @use CacheManager.getInstance().xxxMethod(...);具体参考.BaseListActivity
 */
public class CacheManager {
	private static final String TAG = "CacheManager";

	public static final String CACHE_PATH = DataKeeper.ROOT_SHARE_PREFS_ + "CACHE_PATH";

	private Context context;
	private CacheManager(Context context) {
		this.context = context;
	}

	private static CacheManager manager;
	public static synchronized CacheManager getInstance() {
		if (manager == null) {
			manager = new CacheManager(BaseApplication.getInstance());
		}
		return manager;
	}


	/**
	 * @param clazz
	 * @return
	 */
	public <T> String getClassPath(Class<T> clazz) {
		return clazz == null ? null : CACHE_PATH + clazz.getName();
	}
	/**
	 * @param clazz
	 * @return
	 */
	public <T> String getListPath(Class<T> clazz) {
		String classPath = getClassPath(clazz);
		return StringUtil.isNotEmpty(classPath, true) ? classPath + KEY_LIST : null;
	}
	/**
	 * @param clazz
	 * @param group
	 * @return
	 */
	public <T> String getGroupPath(Class<T> clazz, String group) {
		String classPath = getClassPath(clazz);
		return StringUtil.isNotEmpty(classPath, true) == false || StringUtil.isNotEmpty(group, true) == false
				? null : classPath + KEY_GROUP_ + StringUtil.getTrimedString(group);
	}

	private SharedPreferences getSharedPreferences(String path) {
		return StringUtil.isNotEmpty(path, true) == false
				? null : context.getSharedPreferences(StringUtil.getTrimedString(path), Context.MODE_PRIVATE);
	}


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
	/**
	 * 分组中列表每页最大数量
	 */
	public static final int MAX_PAGE_SIZE = 10;

	/**获取列表
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getAllList(Class<T> clazz) {
		return getList(clazz, -1);
	}
	/**获取列表
	 * @param clazz
	 * @param start
	 * @return
	 */
	public <T> List<T> getList(Class<T> clazz, int start) {
		return getList(clazz, null, start);
	}
	/**获取列表
	 * @param clazz
	 * @param group
	 * @return
	 */
	public <T> List<T> getAllList(Class<T> clazz, String group) {
		return StringUtil.isNotEmpty(group, true) ? getList(clazz, group, -1) : null;
	}
	/**获取列表
	 * @param clazz
	 * @param group == null ? all : in group
	 * @param start < 0 ? all in group : subList(start, end)
	 * @return
	 */
	public <T> List<T> getList(Class<T> clazz, String group, int start) {
		Log.i(TAG, "getList  group = " + group +"; start = " + start);

		Cache<T> cacheList = clazz == null ? null : new Cache<T>(context, clazz, getClassPath(clazz)
				+ KEY_LIST);

		if (StringUtil.isNotEmpty(group, true) == false) {
			return cacheList == null ? null : cacheList.getValueList(start, start + 10);
		}

		List<String> idList = getIdList(clazz, group);
		if (idList == null) {
			Log.e(TAG, "getList  idList == null >> return null;");
			return null;
		}

		if (start >= 0) {
			Log.i(TAG, "getList  start >= 0 >> ");

			int end = start + getPageSize(clazz, group);
			if (end > idList.size()) {
				Log.i(TAG, "getList  end > idList.size() >> end = idList.size();");
				end = idList.size();
			}
			Log.i(TAG, "getList  end = " + end);
			if (end <= start) {
				Log.e(TAG, "getList  end <= start >> return null;");
				return null;
			}

			Log.i(TAG, "getList  >> idList = idList.subList(start, end); >>");
			idList = idList.subList(start, end);
		}

		List<T> list = new ArrayList<T>();
		T data;
		for (String id : idList) {
			data = cacheList.get(id);
			if (data != null) {
				list.add(data);
			}
		}

		Log.i(TAG, "getList  return list; list.size() = " + list.size());

		return list;
	}

	/**获取单个值
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> clazz, String id) {
		Cache<T> cacheList = clazz == null
				? null : new Cache<T>(context, clazz, getClassPath(clazz) + KEY_LIST);
		return cacheList == null ? null : cacheList.get(id);
	}



	/**获取每页数量
	 * @param clazz
	 * @param group
	 * @return
	 */
	private <T> int getPageSize(Class<T> clazz, String group) {
		SharedPreferences sp = getSharedPreferences(
				getClassPath(clazz) + KEY_GROUP_ + StringUtil.getTrimedString(group));
		return sp == null ? 0 : sp.getInt(KEY_PAGE_SIZE, 0);
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
	 * @param map 数据表
	 */
	public <T> void addList(Class<T> clazz, LinkedHashMap<String, T> map) {
		saveList(clazz, null, map, 0, 0);
	}
	/**添加列表
	 * @param clazz 类
	 * @param group 分组
	 * @param map 数据表
	 */
	public <T> void addList(Class<T> clazz, String group, LinkedHashMap<String, T> map) {
		addList(clazz, group, map, -1);
	}
	/**添加列表
	 * @param clazz 类
	 * @param group 分组
	 * @param map 数据表
	 * @param pageSize 每页大小
	 */
	public <T> void addList(Class<T> clazz, String group, LinkedHashMap<String, T> map, int pageSize) {
		if (StringUtil.isNotEmpty(group, true) == false) {
			Log.e(TAG, "addList  StringUtil.isNotEmpty(group, true) == false >> return;");
			return;
		}
		saveList(clazz, group, map, -1, pageSize);
	}
	/**保存列表
	 * @param clazz 类
	 * @param group 分组
	 * @param map 数据表
	 * @param start 存储起始位置,[start, start + map.size()]中原有的将被替换. start = start < 0 ? idList.size() : start;
	 * @param pageSize 每页大小
	 */
	public <T> void saveList(Class<T> clazz, String group, LinkedHashMap<String, T> map, int start, int pageSize) {
		if (clazz == null || map == null || map.size() <= 0) {
			Log.e(TAG, "saveList  clazz == null || map == null || map.size() <= 0 >> return;");
			return;
		}
		final String CLASS_PATH = getClassPath(clazz);

		if (StringUtil.isNotEmpty(group, true)) {

			Log.i(TAG, "saveList  group = " + group + "; map.size() = " + map.size()
					+ "; start = " + start +"; pageSize = " + pageSize);
			List<String> newIdList = new ArrayList<String>(map.keySet());//用String而不是Long，因为订单Order的id超出Long的最大值
			//		if (newIdList != null) {
			Log.i(TAG, "saveList newIdList.size() = " + newIdList.size()
					+ "; start save <<<<<<<<<<<<<<<<<\n ");


			//保存至分组<<<<<<<<<<<<<<<<<<<<<<<<<
			SharedPreferences sp = getSharedPreferences(CLASS_PATH + KEY_GROUP_ + StringUtil.getTrimedString(group));
			//			sp.edit().putString(KEY_GROUP, group);
			Editor editor = sp.edit();

			//			Log.i(TAG, "\n saveList pageSize = " + getPageSize(clazz, group) + " <<<<<<<<");
			//列表每页大小
			if (pageSize > 0) {//sp.getInt(KEY_PAGE_SIZE, 0)) {
				if (pageSize > MAX_PAGE_SIZE) {
					pageSize = MAX_PAGE_SIZE;
				}
				editor.remove(KEY_PAGE_SIZE).putInt(KEY_PAGE_SIZE, pageSize);
			}
			//			Log.i(TAG, "\n saveList pageSize = " + getPageSize(clazz, group) + ">>>>>>>>>");

			//id列表
			List<String> idList = Json.parseArray(sp.getString(KEY_ID_LIST, null), String.class);
			if (idList == null) {
				idList = new ArrayList<String>();
			}
			if (start < 0) {
				start = idList.size();
			}
			//			Log.i(TAG, "\n saveList idList.size() = " + idList.size() + " <<<<<<<<");
			for (int i = start; i < start + newIdList.size(); i++) {
				if (i < idList.size()) {
					idList.set(i, newIdList.get(i - start));
				} else {
					idList.add(newIdList.get(i - start));
				}
			}
			editor.remove(KEY_ID_LIST).putString(KEY_ID_LIST, Json.toJSONString(idList)).commit();

			//			Log.i(TAG, "\n saveList idList.size() = " + getIdList(clazz, group).size() + ">>>>>>>>>");
		}

		//保存至分组>>>>>>>>>>>>>>>>>>>>>>>>>



		//保存所有数据<<<<<<<<<<<<<<<<<<<<<<<<<
		Cache<T> cache = new Cache<T>(context, clazz, CLASS_PATH + KEY_LIST);
		cache.saveList(map);
		//保存所有数据>>>>>>>>>>>>>>>>>>>>>>>>>

		Log.i(TAG, "\n saveList cache.getSize() = " + cache.getSize()
				+ "; end save >>>>>>>>>>>> ");
		//		}

	}

	/**保存
	 * 未完成
	 * @param clazz
	 * @param data 数据
	 * @param id
	 */
	public <T> void save(Class<T> clazz, T data, String id) {
		save(clazz, data, id, null);
	}
	/**ROOT
	 * 保存 
	 * @param clazz
	 * @param data 数据
	 * @param id
	 * @param group 分组
	 */
	public <T> void save(Class<T> clazz, T data, String id, String group) {
		if (data == null || StringUtil.isNotEmpty(id, true) == false) {
			Log.e(id, "save  data == null || StringUtil.isNotEmpty(id, true) == false  >>  return;");
			return;
		}
		SharedPreferences sp = getSharedPreferences(getGroupPath(clazz, group));
		if (sp == null) {
			Log.e(TAG, "save sp == null >> return;");
			return;
		}

		List<String> idList = getIdList(clazz, group);
		if (idList == null) {
			idList = new ArrayList<String>();
		}
		if (idList.contains(id)) {
			Log.e(TAG, "save idList.contains(id) >> return;");
			return;
		}

		idList.add(0, id);
		sp.edit().remove(KEY_ID_LIST).putString(KEY_ID_LIST, Json.toJSONString(idList)).commit();

		new Cache<T>(context, clazz, getListPath(clazz)).save(id, data);
	}

	/**清空类
	 * @param <T>
	 * @param clazz
	 */
	public <T> void clear(Class<T> clazz) {
		clear(getSharedPreferences(getListPath(clazz)));
	}
	/**清空群组
	 * @param <T>
	 * @param clazz
	 * @param group
	 */
	public <T> void clear(Class<T> clazz, String group) {
		clear(clazz, group, false);
	}
	/**清空群组
	 * @param clazz
	 * @param group
	 * @param removeAllInGroup 删除群组内所有id对应数据
	 * @param <T>
	 */
	public <T> void clear(Class<T> clazz, String group, boolean removeAllInGroup) {
		Log.i(TAG, "clear  group = " + group + "; removeAllInGroup = " + removeAllInGroup);
		List<String> list = removeAllInGroup == false ? null : getIdList(clazz, group);
		if (list != null) {
			Cache<T> cache = new Cache<T>(context, clazz, getListPath(clazz));
			for (String id : list) {
				cache.remove(id);
			}
		}
		clear(getSharedPreferences(getGroupPath(clazz, group)));
	}
	/**清空
	 * @param sp
	 */
	public void clear(SharedPreferences sp) {
		if (sp == null) {
			Log.e(TAG, "clearList  sp == null >> return;");
			return;
		}
		sp.edit().clear().commit();
	}

	public <T> void remove(Class<T> clazz, String id) {
		if (clazz == null) {
			Log.e(TAG, "remove  clazz == null >> return;");
			return;
		}
		new Cache<T>(context, clazz, getListPath(clazz)).remove(id);
	}

}
