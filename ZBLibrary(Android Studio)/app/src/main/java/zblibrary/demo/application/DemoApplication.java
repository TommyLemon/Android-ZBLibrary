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

package zblibrary.demo.application;

import java.io.File;

import zblibrary.demo.constant.Constant;
import zblibrary.demo.model.User;
import zblibrary.demo.utils.DataUtil;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.ImageLoaderUtil;
import zuo.biao.library.util.SettingUtil;
import zuo.biao.library.util.StringUtil;
import android.app.Application;
import android.util.Log;

/**Application
 * @author Lemon
 */
public class DemoApplication extends Application {
	private static final String TAG = "DemoApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;

		DataKeeper.init();
		SettingUtil.init(this);

		File appFile = new File(Constant.BASE_FILE_PATH);
		if (!appFile.exists() || (appFile.exists() && appFile.isFile())) {
			appFile.mkdirs();
		}
		
		ImageLoaderUtil.init(getApplicationContext());
	}
	
	private static DemoApplication context;
	public static DemoApplication getInstance() {
		return context;
	}
	
	/**获取版本号(显示给用户看的)
	 * @return
	 */
	public String getVersion() {
		return "2.0";
	}



	private static User currentUser = null;
	public User getCurrentUser() {
		if (currentUser == null) {
			currentUser = DataUtil.getCurrentUser(context);
		}
		return currentUser;
	}

	public void saveCurrentUser(User user) {
		if (user == null) {
			Log.e(TAG, "saveCurrentUser  currentUser == null >> return;");
			return;
		}
		if (user.getId() <= 0 && StringUtil.isNotEmpty(user.getName(), true) == false) {
			Log.e(TAG, "saveCurrentUser  user.getId() <= 0" +
					" && StringUtil.isNotEmpty(user.getName(), true) == false >> return;");
			return;
		}

		currentUser = user;
		DataUtil.saveCurrentUser(context, currentUser);
	}

	/**判断是否为当前用户
	 * @param userId
	 * @return
	 */
	public boolean isCurrentUser(long userId) {
		return DataUtil.isCurrentUser(context, userId);
	}

	/**获取当前用户id
	 * @return
	 */
	public long getCurrentUserId() {
		if (currentUser == null) {
			currentUser = getCurrentUser();
		}
		Log.d(TAG, "getCurrentUserId  currentUserId = " + (currentUser == null ? "null" : currentUser.getId()));
		return currentUser == null ? 0 : currentUser.getId();
	}

	
}
