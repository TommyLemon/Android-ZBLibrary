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

package zblibrary.demo.util;

import java.util.ArrayList;
import java.util.List;

import zblibrary.demo.application.DemoApplication;
import zuo.biao.library.bean.Parameter;
import zuo.biao.library.manager.HttpManager;
import zuo.biao.library.manager.HttpManager.OnHttpResponseListener;
import zuo.biao.library.util.MD5Util;
import zuo.biao.library.util.SettingUtil;
import zuo.biao.library.util.StringUtil;

/**HTTP请求工具类
 * @author Lemon
 * @use 添加请求方法xxxMethod >> HttpRequest.xxxMethod(...)
 */
public class HttpRequest {
//	private static final String TAG = "HttpRequest";


	/**添加请求参数，value为空时不添加
	 * @param list
	 * @param key
	 * @param value
	 */
	public static void addExistParameter(List<Parameter> list, String key, Object value) {
		if (list == null) {
			list = new ArrayList<Parameter>();
		}
		if (StringUtil.isNotEmpty(key, true) && StringUtil.isNotEmpty(value, true) ) {
			list.add(new Parameter(key, value));
		}
	}

	
	
	/**基础URL，这里服务器设置可切换*/
	public static final String URL_BASE = SettingUtil.getCurrentServerAddress(DemoApplication.getInstance());
	public static final String KEY_PAGE_NUM = "pageNum";

	
	

	//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	
	//user<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public static final String KEY_RANGE = "range";

	public static final String KEY_ID = "id";
	public static final String KEY_USER_ID = "userId";
	public static final String KEY_CURRENT_USER_ID = "currentUserId";

	public static final String KEY_NAME = "name";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_AUTH_CODE = "authCode";

	public static final String KEY_SEX = "sex";
	public static final int SEX_MAIL = 1;
	public static final int SEX_FEMAIL = 2;
	public static final int SEX_ALL = 3;


	//account<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	/**注册
	 * @param phone
	 * @param password
	 * @param listener
	 */
	public void register(final String phone, final String password,
			final int requestCode, final OnHttpResponseListener listener) {
		List<Parameter> paramList = new ArrayList<Parameter>();
		addExistParameter(paramList, KEY_PHONE, phone);
		addExistParameter(paramList, KEY_PASSWORD, MD5Util.MD5(password));
		
		HttpManager.getInstance().post(paramList, URL_BASE + "user/register", requestCode, listener);
	}
	
	/**登陆
	 * @param phone
	 * @param password
	 * @param listener
	 */
	public void login(final String phone, final String password,
			final int requestCode, final OnHttpResponseListener listener) {
		List<Parameter> paramList = new ArrayList<Parameter>();
		addExistParameter(paramList, KEY_PHONE, phone);
		addExistParameter(paramList, KEY_PASSWORD, MD5Util.MD5(password));
		
		HttpManager.getInstance().post(paramList, URL_BASE + "user/login", requestCode, listener);
	}
	
	//account>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
	
	/**获取用户
	 * @param userId
	 * @param requestCode
	 * @param listener
	 */
	public static void getUser(long userId, final int requestCode, final OnHttpResponseListener listener) {
		List<Parameter> paramList = new ArrayList<Parameter>();
		addExistParameter(paramList, KEY_CURRENT_USER_ID, DemoApplication.getInstance().getCurrentUserId());
		addExistParameter(paramList, KEY_USER_ID, userId);
		
		HttpManager.getInstance().post(paramList, URL_BASE + "user/infomation", requestCode, listener);
	}
	public static final int RESULT_GET_USER_SUCCEED = 100;

	public static final int USER_LIST_RANGE_ALL = 0;
	public static final int USER_LIST_RANGE_RECOMMEND = 1;
	/**获取用户列表
	 * @param range
	 * @param pageNum
	 * @param requestCode
	 * @param listener
	 */
	public static void getUserList(int range, int pageNum, final int requestCode, final OnHttpResponseListener listener) {
		List<Parameter> paramList = new ArrayList<Parameter>();
		addExistParameter(paramList, KEY_CURRENT_USER_ID, DemoApplication.getInstance().getCurrentUserId());
		addExistParameter(paramList, KEY_RANGE, range);
		addExistParameter(paramList, KEY_PAGE_NUM, pageNum);

		HttpManager.getInstance().get(paramList, URL_BASE + "user/list", requestCode, listener);
	}
	public static final int RESULT_GET_USER_LIST_SUCCEED = 110;



	//user>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	
	//示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
}