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

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**通用字符串(String)相关类,为null时返回""
 * @author Lemon
 * @use StringUtil.
 */
public class StringUtil {
	private static final String TAG = "StringUtil";

	public StringUtil() {
	}

	private static String currentString = "";
	/**获取刚传入处理后的string
	 * @return
	 */
	public static String getCurrentString() {
		return currentString;
	}

	//获取string,为null时返回"" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**获取string,为null则返回""
	 * @param tv
	 * @return
	 */
	public static String getString(TextView tv) {
		if (tv == null || tv.getText() == null) {
			return "";
		}
		return getString(tv.getText().toString());
	}
	/**获取string,为null则返回""
	 * @param et
	 * @return
	 */
	public static String getString(EditText et) {
		if (et == null || et.getText() == null) {
			return "";
		}
		return getString(et.getText().toString());
	}
	/**获取string,为null则返回""
	 * @param object
	 * @return
	 */
	public static String getString(Object object) {
		return object == null ? "" : getString(String.valueOf(object));
	}
	/**获取string,为null则返回""
	 * @param cs
	 * @return
	 */
	public static String getString(CharSequence cs) {
		return cs == null ? "" : getString(cs.toString());
	}
	/**获取string,为null则返回""
	 * @param s
	 * @return
	 */
	public static String getString(String s) {
		return s == null ? "" : s;
	}

	//获取string,为null时返回"" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//获取去掉前后空格后的string<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**获取去掉前后空格后的string,为null则返回""
	 * @param tv
	 * @return
	 */
	public static String getTrimedString(TextView tv) {
		return getTrimedString(getString(tv));
	}
	/**获取去掉前后空格后的string,为null则返回""
	 * @param et
	 * @return
	 */
	public static String getTrimedString(EditText et) {
		return getTrimedString(getString(et));
	}
	/**获取去掉前后空格后的string,为null则返回""
	 * @param object
	 * @return
	 */
	public static String getTrimedString(Object object) {
		return getTrimedString(getString(object));
	}
	/**获取去掉前后空格后的string,为null则返回""
	 * @param cs
	 * @return
	 */
	public static String getTrimedString(CharSequence cs) {
		return getTrimedString(getString(cs));
	}
	/**获取去掉前后空格后的string,为null则返回""
	 * @param s
	 * @return
	 */
	public static String getTrimedString(String s) {
		return getString(s).trim();
	}

	//获取去掉前后空格后的string>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//获取去掉所有空格后的string <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**获取去掉所有空格后的string,为null则返回""
	 * @param tv
	 * @return
	 */
	public static String getNoBlankString(TextView tv) {
		return getNoBlankString(getString(tv));
	}
	/**获取去掉所有空格后的string,为null则返回""
	 * @param et
	 * @return
	 */
	public static String getNoBlankString(EditText et) {
		return getNoBlankString(getString(et));
	}
	/**获取去掉所有空格后的string,为null则返回""
	 * @param object
	 * @return
	 */
	public static String getNoBlankString(Object object) {
		return getNoBlankString(getString(object));
	}
	/**获取去掉所有空格后的string,为null则返回""
	 * @param cs
	 * @return
	 */
	public static String getNoBlankString(CharSequence cs) {
		return getNoBlankString(getString(cs));
	}
	/**获取去掉所有空格后的string,为null则返回""
	 * @param s
	 * @return
	 */
	public static String getNoBlankString(String s) {
		return getString(s).replaceAll(" ", "");
	}

	//获取去掉所有空格后的string >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//获取string的长度<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**获取string的长度,为null则返回0
	 * @param tv
	 * @param trim
	 * @return
	 */
	public static int getLength(TextView tv, boolean trim) {
		return getLength(getString(tv), trim);
	}
	/**获取string的长度,为null则返回0
	 * @param et
	 * @param trim
	 * @return
	 */
	public static int getLength(EditText et, boolean trim) {
		return getLength(getString(et), trim);
	}
	/**获取string的长度,为null则返回0
	 * @param object
	 * @param trim
	 * @return
	 */
	public static int getLength(Object object, boolean trim) {
		return getLength(getString(object), trim);
	}
	/**获取string的长度,为null则返回0
	 * @param cs
	 * @param trim
	 * @return
	 */
	public static int getLength(CharSequence cs, boolean trim) {
		return getLength(getString(cs), trim);
	}
	/**获取string的长度,为null则返回0
	 * @param s
	 * @param trim
	 * @return
	 */
	public static int getLength(String s, boolean trim) {
		s = trim ? getTrimedString(s) : s;
		return getString(s).length();
	}

	//获取string的长度>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//判断字符是否非空 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**判断字符是否非空
	 * @param tv
	 * @param trim
	 * @return
	 */
	public static boolean isNotEmpty(TextView tv, boolean trim) {
		return isNotEmpty(getString(tv), trim);
	}
	/**判断字符是否非空
	 * @param et
	 * @param trim
	 * @return
	 */
	public static boolean isNotEmpty(EditText et, boolean trim) {
		return isNotEmpty(getString(et), trim);
	}
	/**判断字符是否非空
	 * @param object
	 * @param trim
	 * @return
	 */
	public static boolean isNotEmpty(Object object, boolean trim) {
		return isNotEmpty(getString(object), trim);
	}
	/**判断字符是否非空
	 * @param s
	 * @param trim
	 * @return
	 */
	public static boolean isNotEmpty(String s, boolean trim) {
		//		Log.i(TAG, "getTrimedString   s = " + s);
		if (s == null) {
			return false;
		}
		if (trim) {
			s = s.trim();
		}
		if (s.length() <= 0) {
			return false;
		}

		currentString = s;

		return true;
	}

	//判断字符是否非空 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//判断字符类型 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//判断手机格式是否正确
	public static boolean isPhone(String phone) {
		if (isNotEmpty(phone, true) == false) {
			return false;
		}

		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-2,5-9])|(17[0-9]))\\d{8}$");

		currentString = phone;

		return p.matcher(phone).matches();
	}
	//判断email格式是否正确
	public static boolean isEmail(String email) {
		if (isNotEmpty(email, true) == false) {
			return false;
		}

		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);

		currentString = email;

		return p.matcher(email).matches();
	}
	//判断是否全是数字
	public static boolean isNumer(String number) {
		if (isNotEmpty(number, true) == false) {
			return false;
		}

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(number);
		if (!isNum.matches()) {
			return false;
		}

		currentString = number;

		return true;
	}
	/**判断字符类型是否是号码或字母
	 * @param inputed
	 * @return
	 */
	public static boolean isNumberOrAlpha(String inputed) {
		if (inputed == null) {
			Log.e(TAG, "isNumberOrAlpha  inputed == null >> return false;");
			return false;
		}
		Pattern pNumber = Pattern.compile("[0-9]*"); 
		Matcher mNumber;
		Pattern pAlpha = Pattern.compile("[a-zA-Z]");
		Matcher mAlpha;
		for (int i = 0; i < inputed.length(); i++) {
			mNumber = pNumber.matcher(inputed.substring(i, i+1));
			mAlpha = pAlpha.matcher(inputed.substring(i, i+1));
			if(! mNumber.matches() && ! mAlpha.matches()){
				return false;
			}
		}

		currentString = inputed;

		return true;
	}

	public static final String URL_STAFFIX = "http://";
	public static final String URL_STAFFIXs = "https://";
	/**判断字符类型是否是网址
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url) {
		if (isNotEmpty(url, true) == false) {
			return false;
		} else if (! url.startsWith(URL_STAFFIX) && ! url.startsWith(URL_STAFFIXs)) {
			return false;
		}

		currentString = url;

		return true;
	}

	/**判断文件路径是否存在
	 * @param path
	 * @return
	 */
	public static boolean isFilePathExist(String path) {
		return StringUtil.isFilePath(path) && new File(path).exists();
	}
	/**判断字符类型是否是路径
	 * @param path
	 * @return
	 */
	public static boolean isFilePath(String path) {
		if (isNotEmpty(path, true) == false) {
			return false;
		}

		if (! path.contains(".") || path.endsWith(".")) {
			return false;
		}

		currentString = path;

		return true;
	}

	//判断字符类型 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//提取特殊字符<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**去掉string内所有非数字类型字符
	 * @param object
	 * @return
	 */
	public static String getNumber(Object object) {
		return getNumber(getString(object));
	}
	/**去掉string内所有非数字类型字符
	 * @param cs
	 * @return
	 */
	public static String getNumber(CharSequence cs) {
		return getNumber(getString(cs));
	}
	/**去掉string内所有非数字类型字符
	 * @param tv
	 * @return
	 */
	public static String getNumber(TextView tv) {
		return getNumber(getString(tv));
	}
	/**去掉string内所有非数字类型字符
	 * @param et
	 * @return
	 */
	public static String getNumber(EditText et) {
		return getNumber(getString(et));
	}
	/**去掉string内所有非数字类型字符
	 * @param s
	 * @return
	 */
	public static String getNumber(String s) {
		if (isNotEmpty(s, true) == false) {
			return "";
		}

		String numberString = "";
		String single;
		for (int i = 0; i < s.length(); i++) {
			single = s.substring(i, i + 1);
			if (isNumer(single)) {
				numberString += single;
			}
		}

		return numberString;
	}

	//提取特殊字符>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//校正（自动补全等）字符串<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**获取网址，自动补全
	 * @param et
	 * @return
	 */
	public static String getCorrectUrl(EditText et) {
		return getCorrectUrl(getString(et));
	}
	/**获取网址，自动补全
	 * @param url
	 * @return
	 */
	public static String getCorrectUrl(String url) {
		Log.i(TAG, "getCorrectUrl : \n" + url);
		if (isNotEmpty(url, true) == false) {
			return "";
		}

		if (! url.endsWith("/") && ! url.endsWith(".htm") && ! url.endsWith(".html")) {
			url = url + "/";
		}

		if (isUrl(url) == false) {
			return URL_STAFFIX + url;
		}
		return url;
	}

	/**获取去掉所有 空格 、"-" 、"+86" 后的phone
	 * @param et
	 * @return
	 */
	public static String getCorrectPhone(EditText et) {
		return getCorrectPhone(getString(et));
	}
	/**获取去掉所有 空格 、"-" 、"+86" 后的phone
	 * @param number
	 * @return
	 */
	public static String getCorrectPhone(String phone) {
		if (isNotEmpty(phone, true) == false) {
			return "";
		}

		phone = getNoBlankString(phone);
		phone = phone.replaceAll("-", "");
		if (phone.startsWith("+86")) {
			phone = phone.substring(3);
		}
		return phone;
	}


	/**获取邮箱，自动补全
	 * @param et
	 * @return
	 */
	public static String getCorrectEmail(EditText et) {
		return getCorrectEmail(getString(et));
	}
	/**获取邮箱，自动补全
	 * @param email
	 * @return
	 */
	public static String getCorrectEmail(String email) {
		if (isNotEmpty(email, true) == false) {
			return "";
		}

		email = getNoBlankString(email);
		if (isEmail(email) == false && ! email.endsWith(".com")) {
			email += ".com";
		}

		return email;
	}


	//校正（自动补全等）字符串>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
