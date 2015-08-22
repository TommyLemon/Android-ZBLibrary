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

import java.util.List;

import android.util.Log;

/**数字相关类
 * @author Lemon
 * @use NumberUtil.
 */
public class NumberUtil {
	private static final String TAG = "NumberUtil";

	private NumberUtil() {/* 不能实例化**/}
	
	/**获取一个列表的最大和最小数值
	 * @param numberList
	 * @return
	 */
	public static int[] getMaxMinNumber(List<Integer> numberList) {
		if (numberList == null || numberList.size() <= 0) {
			Log.e(TAG, "getMaxMinNumber  numberList == null || numberList.size() <= 0 >> return null;");
			return null;
		}

		int max = 0;
		int min = 0;
		for (int i = 0; i < numberList.size(); i++) {
			max = Math.max(max, numberList.get(i));
			min = Math.min(min, numberList.get(i));
		}

		return new int[]{max, min};
	}
	
	
	
}
