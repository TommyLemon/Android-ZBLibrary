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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**通用字符类
 * @author Lemon
 * @use new TextUtil
 */
public class TextUtil {
	private static final String TAG = "TextUtil";

	private TextUtil() {/* 不能实例化**/}
	
	public static final int TEXT_TYPE_OTHER = 0;
	public static final int TEXT_TYPE_NUMBER = 1;
	public static final int TEXT_TYPE_ALPHABET = 2;
	public static final int TEXT_TYPE_CHINESE = 3;

	/**是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++){
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}

	/**获取字符类型。似乎存在问题，尚未使用
	 * @param str
	 * @return
	 */
	public static int getTextType(String str) {
		if (str == null || "".equals(str)) {
			Log.i(TAG, "str == null || equals(str) >> return TEXT_TYPE_OTHER;");
			return TEXT_TYPE_OTHER;
		}
		Pattern p = Pattern.compile("[0-9]*"); 
		Matcher m = p.matcher(str); 
		if(m.matches() ){
			return TEXT_TYPE_NUMBER;
		} 
		p=Pattern.compile("[a-zA-Z]");
		m=p.matcher(str);
		if(m.matches()){
			return TEXT_TYPE_ALPHABET;
		}
		p=Pattern.compile("[\u4e00-\u9fa5]");
		m=p.matcher(str);
		if(m.matches()){
			return TEXT_TYPE_CHINESE;
		}
		return TEXT_TYPE_OTHER;
	}

	/**是否含有type类型字符
	 * @param str
	 * @param type
	 * @return
	 */
	public static boolean isContainsType(String str, int type) {
		if (str == null || "".equals(str)) {
			Log.i(TAG, "str == null || equals(str) >> return TEXT_TYPE_OTHER;");
			return false;
		}

		Pattern p = null;
		switch (type) {
		case TEXT_TYPE_NUMBER:
			p = Pattern.compile("[0-9]*"); 
			break;
		case TEXT_TYPE_ALPHABET:
			p=Pattern.compile("[a-zA-Z]");
			break;
		case TEXT_TYPE_CHINESE:
			p=Pattern.compile("[\u4e00-\u9fa5]");
			break;
		default:
			Log.i(TAG, "isContainsType  default: >> return false;");
			return false;
		}

		Matcher m;
		for (int i = 0; i < str.length(); i++) {
			m = p.matcher(str.substring(i, i+1)); 
			if (m.matches()) {
				return true;
			}
		}

		return false;
	}

	/**判断字符类型，即是否只含有type类型字符
	 * @param str
	 * @param type
	 * @return
	 */
	public static boolean isTextType(String str, int type) {
		if (str == null || "".equals(str)) {
			Log.i(TAG, "str == null || equals(str) >> return TEXT_TYPE_OTHER;");
			return false;
		}

		Pattern p = null;
		switch (type) {
		case TEXT_TYPE_NUMBER:
			p = Pattern.compile("[0-9]*"); 
			break;
		case TEXT_TYPE_ALPHABET:
			p=Pattern.compile("[a-zA-Z]");
			break;
		case TEXT_TYPE_CHINESE:
			p=Pattern.compile("[\u4e00-\u9fa5]");
			break;
		default:
			Log.i(TAG, "isTextType  default: >> return false;");
			return false;
		}

		Matcher m;
		for (int i = 0; i < str.length(); i++) {
			m = p.matcher(str.substring(i, i+1)); 
			if (! m.matches()) {
				return false;
			}
		}

		return true;
	}


	//String<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public static String getTrimedString(String string) {
		return StringUtil.getTrimedString(string);
	}
	public static boolean isNotEmpty(TextView tv, boolean trim) {
		return StringUtil.isNotEmpty(tv, trim);
	}
	public static boolean isNotEmpty(EditText et, boolean trim) {
		return StringUtil.isNotEmpty(et, trim);
	}
	public static boolean isNotEmpty(Object object, boolean trim) {
		return object == null ? false : StringUtil.isNotEmpty(object.toString(), trim);
	}
	public static boolean isNotEmpty(String string, boolean trim) {
		return StringUtil.isNotEmpty(string, trim);
	}
	//String>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//Number<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public static int[] getMaxMinNumber(List<Integer> numberList) {
		return NumberUtil.getMaxMinNumber(numberList);
	}
	//Number>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
