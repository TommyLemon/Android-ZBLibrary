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

package zuo.biao.library.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**应用设置工具类
 * @author Lemon
 * @must application中在DataKeeper.init();后SettingUtil.init(...);
 * @warn 修改服务器地址（URL_SERVER_ADDRESS_NORMAL_HTTP等）
 */
public final class SettingUtil {
	private static final String TAG = "SettingUtil";

	public static final boolean isReleased = false;//应用已发布

	/**建议改成你自己项目的路径*/
	public static final String APP_SETTING = "SHARE_PREFS_" + "APP_SETTING";

	private SettingUtil() {/*不能实例化**/}


	public static final String KEY_VOICE = "KEY_VOICE";//开启通知声
	public static final String KEY_VIBRATE = "KEY_VIBRATE";//开启震动
	public static final String KEY_NO_DISTURB = "KEY_NO_DISTURB";//夜间防打扰

	public static final String KEY_IS_ON_TEST_MODE = "KEY_IS_ON_TEST_MODE";//测试模式
	public static final String KEY_IS_FIRST_START = "KEY_IS_FIRST_START";//第一次打开应用

	public static final String[] KEYS = {
		KEY_VOICE,
		KEY_VIBRATE,
		KEY_NO_DISTURB,

		KEY_IS_ON_TEST_MODE,
		KEY_IS_FIRST_START,
	};

	public static boolean voice = true;//开启通知声
	public static boolean vibrate = true;//开启震动
	public static boolean noDisturb = false;//夜间防打扰

	public static boolean isOnTestMode = false;//测试模式
	public static boolean isFirstStart = true;//第一次打开应用

	public static final boolean[] defaultValues = {
		voice,//开启通知声
		vibrate,//开启震动
		noDisturb,//夜间防打扰

		isOnTestMode,//测试模式
		isFirstStart,//第一次打开应用
	};

	/**初始化
	 * @param context
	 */
	public static void init(Context context) {

		voice = getBoolean(context, KEY_VOICE, voice);
		vibrate = getBoolean(context, KEY_VIBRATE, vibrate);
		noDisturb = getBoolean(context, KEY_NO_DISTURB, noDisturb);

		isOnTestMode = getBoolean(context, KEY_IS_ON_TEST_MODE, isOnTestMode);
		isFirstStart = getBoolean(context, KEY_IS_FIRST_START, isFirstStart);
	}

	/**恢复默认
	 * @param context
	 */
	public static void restoreDefault(Context context) {
		for (int i = 0; i < KEYS.length; i++) {
			putBoolean(context, KEYS[i], defaultValues[i]);
		}

		init(context);
	}


	/**判断是否存在key
	 * @param key
	 * @return
	 */
	public static boolean isContainKey(String key) {
		return getKeyIndex(key) >= 0;
	}

	/**获取key在KEYS中的位置
	 * @param key
	 * @return
	 */
	public static int getKeyIndex(String key) {
		key = StringUtil.getTrimedString(key);
		for (int i = 0; i < KEYS.length; i++) {
			if (key.equals(KEYS[i])) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean defaultValue){
		if (context == null || isContainKey(key) == false) {
			Log.e(TAG, "writeBoolean  context == null || isContainKey(key) == false >> return defaultValue;");
			return defaultValue;
		}

		return context.getSharedPreferences(APP_SETTING, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
	}


	/**设置所有boolean
	 * @param context
	 * @param values
	 */
	public static void putAllBoolean(Context context, boolean[] values){
		if (context == null || values == null || values.length != KEYS.length) {
			Log.e(TAG, "putAllBoolean  context == null || values == null || values.length != KEYS.length >> return;");
			return;
		}

		Editor editor = context.getSharedPreferences(APP_SETTING, Context.MODE_PRIVATE).edit();
		editor.clear();
		for (int i = 0; i < values.length; i++) {
			editor.putBoolean(KEYS[i], values[i]);
		}
		editor.commit();

		init(context);
	}

	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putBoolean(Context context, String key, boolean value){
		int keyIndex = getKeyIndex(key);
		if (context == null || keyIndex <= 0) {
			Log.e(TAG, "writeBoolean  context == null || keyIndex <= 0 >> return;");
			return;
		}

		context.getSharedPreferences(APP_SETTING, Context.MODE_PRIVATE)
		.edit()
		.remove(key)//防止因类型不同导致崩溃
		.putBoolean(key, value)
		.commit();

		init(context);
	}

	/**获取所有boolean值
	 * @param context
	 * @return
	 */
	public static boolean[] getAllBooleans(Context context) {
		init(context);
		return new boolean[]{
				voice,
				vibrate,
				noDisturb,

				isOnTestMode,
				isFirstStart,
		};
	}

	public static final int[] NO_DISTURB_START_TIME = {23, 0};
	public static final int[] NO_DISTURB_END_TIME = {6, 0};

	/**免打扰
	 * @param context
	 * @return
	 */
	public static boolean noDisturb(Context context) {
		return getBoolean(context, KEY_NO_DISTURB, noDisturb)
				&& TimeUtil.isNowInTimeArea(NO_DISTURB_START_TIME, NO_DISTURB_END_TIME);
	}

	/**
	 * TODO 改为你的存图片的服务器地址
	 */
	public static final String IMAGE_BASE_URL = "http://demo.upaiyun.com";

	public static final String KEY_SERVER_ADDRESS_NORMAL = "KEY_SERVER_ADDRESS_NORMAL";
	public static final String KEY_SERVER_ADDRESS_TEST = "KEY_SERVER_ADDRESS_TEST";

	/**
	 * TODO 改为你的正式服务器地址
	 */
	public static final String URL_SERVER_ADDRESS_NORMAL_HTTP = "http://www.baidu.com/";//正式服务器
	/**
	 * TODO 改为你的正式服务器地址
	 */
	public static final String URL_SERVER_ADDRESS_NORMAL_HTTPS = "https://www.baidu.com/";//正式服务器
	/**
	 * TODO 改为你的测试服务器地址,如果有的话
	 */
	public static final String URL_SERVER_ADDRESS_TEST = "https://github.com/TommyLemon/Android-ZBLibrary";//测试服务器

	/**获取当前服务器地址
	 * isHttps = false
	 * @param context
	 * @return
	 */
	public static String getCurrentServerAddress(Context context) {
		return getCurrentServerAddress(context, false);
	}
	/**获取当前服务器地址
	 * @param context
	 * @param isHttps
	 * @return
	 */
	public static String getCurrentServerAddress(Context context, boolean isHttps) {
		return isHttps ? URL_SERVER_ADDRESS_NORMAL_HTTPS : getServerAddress(context, isOnTestMode);
	}
	/**获取服务器地址
	 * isHttps = false
	 * @param context
	 * @param isTest
	 * @return
	 */
	public static String getServerAddress(Context context, boolean isTest) {
		return getServerAddress(context, isTest, false);
	}
	/**获取服务器地址
	 * @param context
	 * @param isTest
	 * @return
	 */
	public static String getServerAddress(Context context, boolean isTest, boolean isHttps) {
		SharedPreferences sdf = context == null ? null : context.getSharedPreferences(APP_SETTING, Context.MODE_PRIVATE);
		return sdf == null ? null : sdf.getString(
				isTest ? KEY_SERVER_ADDRESS_TEST : KEY_SERVER_ADDRESS_NORMAL 
				, isTest ? URL_SERVER_ADDRESS_TEST : (
						isHttps ? URL_SERVER_ADDRESS_NORMAL_HTTPS : URL_SERVER_ADDRESS_NORMAL_HTTP));
	}



}
