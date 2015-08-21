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

import android.app.Activity;
import android.util.DisplayMetrics;

/**屏幕相关类
 * @author Lemon
 * @use NumberUtil.
 */
public class ScreenUtil {
	//	private static final String TAG = "SceenUtil";

	private ScreenUtil() {/* 不能实例化**/}


	public static int[] screenSize;
	public static int[] getScreenSize(Activity activity){
		if (screenSize == null || screenSize[0] <= 480 || screenSize[1] <= 800) {//小于该分辨率会显示不全
			screenSize = new int[2];
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			screenSize[0] = dm.widthPixels;
			screenSize[1] = dm.heightPixels;
		}
		return screenSize;
	}
	public static int getScreenWidth(Activity activity){
		return getScreenSize(activity)[0];
	}
	public static int getScreenHeight(Activity activity){
		return getScreenSize(activity)[1];
	}


}
