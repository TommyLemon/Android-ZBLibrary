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

import zuo.biao.library.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**图片加载管理类
 * @author Lemon
 * @use ImageLoaderUtil.getInstance(context).loadImage(...)
 */
public class ImageLoaderUtil {
	private static final String TAG = "ImageLoaderUtil";

	private static ImageLoader imageLoader;
	/**初始化方法
	 * @must 使用其它方法前必须调用，建议在自定义Application的onCreate方法中调用
	 * @param context
	 */
	public static void init(Context context) {
		if (context == null) {
			Log.e(TAG, "\n\n\n\n\n !!!!!!  <<<<<< init  context == null >> return; >>>>>>>> \n\n\n\n");
			return;
		}
		imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.defaultDisplayImageOptions(getOption(0))
						// .threadPoolSize(5)
						// //.threadPriority(Thread.MIN_PRIORITY + 3)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
						// .discCacheSize((int)(Runtime.getRuntime().maxMemory()/2))
						// .discCache(new UnlimitedDiscCache(getCachePath()))
						// .memoryCacheSize(2 * 1024 * 1024)
						// .memoryCacheExtraOptions(147, 147)
						// .writeDebugLogs()
						// .httpConnectTimeout(5000)
						// .httpReadTimeout(20000)
				.diskCacheExtraOptions(ScreenUtil.getScreenWidth(context), ScreenUtil.getScreenHeight(context), null)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
						// .displayer(new RoundedBitmapDisplayer(5))
				.build();

		imageLoader.init(config);
	}

	/**加载图片
	 * 加载小图应再调用该方法前使用getSmallUri处理uri
	 * @param iv
	 * @param uri
	 */
	public static void loadImage(ImageView iv, String uri) {
		loadImage(iv, uri, TYPE_DEFAULT);
	}

	public static final int TYPE_DEFAULT = 0;
	public static final int TYPE_ROUND_CORNER = 1;
	public static final int TYPE_OVAL = 2;
	/**加载图片
	 * 加载小图应再调用该方法前使用getSmallUri处理uri
	 * @param type 图片显示类型
	 * @param iv
	 * @param uri 网址url或本地路径path
	 */
	public static void loadImage(int type, ImageView iv, String uri) {
		if (iv == null) {// || iv.getWidth() <= 0) {
			Log.i(TAG, "loadImage  iv == null >> return;");
			return;
		}
		switch (type) {
			case TYPE_ROUND_CORNER:
				loadImage(iv, uri, 10);
				break;
			case TYPE_OVAL:
				loadImage(iv, uri, iv.getMeasuredWidth() / 2);
				break;
			default:
				loadImage(iv, uri, 0);
				break;
		}
	}

	public static String IMAGEVIEW_KEY_URL = "IMAGEVIEW_KEY_URL";
	public static String IMAGEVIEW_KEY_STATE = "IMAGEVIEW_KEY_STATE";
	public static int IMAGEVIEW_STATE_ISSHOW = 1;
	public static int IMAGEVIEW_STATE_NOTSHOW = 2;

	/**加载图片
	 * 加载小图应再调用该方法前使用getSmallUri处理uri
	 * @param iv
	 * @param uri 网址url或本地路径path
	 * @param cornerRadiusSize 图片圆角大小
	 */
	public static void loadImage(ImageView iv, String uri, int cornerRadiusSize) {
		Log.i(TAG, "loadImage  iv" + (iv == null ? "==" : "!=") + "null; uri=" + uri);
		if (iv == null) {
			return;
		}

		uri = getCorrectUri(uri);
		try {
			if (imageLoader == null) {
				Log.e(TAG, "\n\n\n\n\n !!!! <<< loadImage  imageLoader == null !!!   >>>>> 必须调用init方法!!! \n\n\n\n");
				imageLoader = ImageLoader.getInstance();
			}
			Log.i(TAG, "loadImage imageLoader.displayImage uri=" + uri);
			imageLoader.displayImage(uri, iv, getOption(cornerRadiusSize));
		} catch (Exception e) {
			Log.e(TAG, "loadImage try { if ((int) TagUtil.getTag(....  >> } catch (Exception e) {\n" + e.getMessage());
		}
	}


	public static final String FILE_PATH_PREFIX = StringUtil.FILE_PATH_PREFIX;
	public static final String HTTP = StringUtil.HTTP;
	public static final String URL_PREFIX = StringUtil.URL_PREFIX;
	public static final String URL_PREFIXs = StringUtil.URL_PREFIXs;

	public static String URL_SUFFIX_SMALL = "!common";
	/**获取可用的uri
	 * @param uri
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String getCorrectUri(String uri) {
		Log.i(TAG, "<<<<  getCorrectUri  uri = " + uri);
//		if (StringUtil.isNotEmpty(uri, true) == false) {
//			Log.e(TAG, "getCorrectUri  StringUtil.isNotEmpty(uri, true) == false >> return null;");
//			return null;
//		}
		uri = StringUtil.getNoBlankString(changeUrl(uri));

		if (uri.toLowerCase().startsWith(HTTP)) {
			//TODO
		} else {
//			String path = uri.startsWith(FILE_PATH_PREFIX) ? uri : FILE_PATH_PREFIX + uri;
			uri = uri.startsWith(FILE_PATH_PREFIX) ? uri : FILE_PATH_PREFIX + uri;
//			if (path.startsWith("/")) {
//				path = FILE_PATH_PREFIX + uri;
//			}
//			Log.i(TAG, "getCorrectUri  uri.toLowerCase().startsWith(HTTP) == false >>  uri = " + uri);
//			uri = StringUtil.isFilePathExist(path) ? path : URL_PREFIX + uri;
		}

		Log.i(TAG, "getCorrectUri  return uri = " + uri + " >>>>> ");
		return uri;
	}

	/**域名中带下划线无法解析，转换为可解析域名
	 * @param uri
	 * @return
	 */
	public static String changeUrl(String uri) {
		uri = StringUtil.getNoBlankString(uri);
		String head = "http://zuo.biao.images";
		if (uri.startsWith(head)) {
			String end = uri.substring(head.length());
			return SettingUtil.IMAGE_BASE_URL + end;
		}
		return uri;
	}

	/**获取配置
	 * @param cornerRadiusSize
	 * @return
	 */
	private static DisplayImageOptions getOption(int cornerRadiusSize) {
		return getOption(cornerRadiusSize, cornerRadiusSize <= 0
				? R.drawable.image_miss_not_round : R.drawable.image_miss);
	}
	/**获取配置
	 * @param cornerRadiusSize
	 * @param defaultImageResId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static DisplayImageOptions getOption(int cornerRadiusSize, int defaultImageResId) {
		Options options0 = new Options();
		options0.inPreferredConfig = Bitmap.Config.RGB_565;

		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		if(defaultImageResId > 0) {
			try {
				builder.showImageForEmptyUri(defaultImageResId)
						.showImageOnLoading(defaultImageResId)
						.showImageOnFail(defaultImageResId);
			} catch (Exception e) {
				Log.e(TAG, "getOption  try {builder.showImageForEmptyUri(defaultImageResId) ..." +
						" >> } catch (Exception e) { \n" + e.getMessage());
			}
		}
		if (cornerRadiusSize > 0) {
			builder.displayer(new RoundedBitmapDisplayer(cornerRadiusSize));
		}

		return builder.cacheInMemory(true).cacheOnDisc(true).decodingOptions(options0).build();
	}

	/**获取小图url或path
	 * path不加URL_SUFFIX_SMALL
	 * @param uri
	 * @return
	 */
	public static String getSmallUri(String uri) {
		return getSmallUri(uri, false);
	}
	/**获取小图url或path
	 * path不加URL_SUFFIX_SMALL
	 * @param uri
	 * @param isLocalPath
	 * @return
	 */
	public static String getSmallUri(String uri, boolean isLocalPath) {
		if (uri == null) {
			Log.e(TAG, "getSmallUri  uri == null >> return null;");
			return null;
		}

		if (uri.startsWith("/") || uri.startsWith(FILE_PATH_PREFIX) || StringUtil.isFilePathExist(FILE_PATH_PREFIX + uri)) {
			isLocalPath = true;
		}
		return isLocalPath || uri.endsWith(URL_SUFFIX_SMALL)
				? uri : uri + URL_SUFFIX_SMALL;
	}
}
