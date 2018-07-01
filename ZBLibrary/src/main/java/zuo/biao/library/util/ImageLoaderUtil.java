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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**图片加载工具类，使用UniversalImageLoader，本工程已支持Glide，不需要UniversalImageLoader可这样减小包体积：
 * <br > 1.删除 ImageLoaderUtil.java
 * <br > 2.删除libs目录下的 universal-image-loader.jar
 * <br > 3.删除 BaseApplication.init 中的 ImageLoaderUtil.init 这行代码
 * @author Lemon
 * @use ImageLoaderUtil.loadImage(...)
 */
@Deprecated
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
	 * type = TYPE_DEFAULT
	 * @param iv
	 * @param uri 网址url或本地路径path
	 */
	public static void loadImage(ImageView iv, String uri) {
		loadImage(iv, uri, TYPE_DEFAULT);
	}

	public static final int TYPE_DEFAULT = 0;//矩形
	public static final int TYPE_ROUND_CORNER = 1;//圆角矩形
	public static final int TYPE_OVAL = 2;//圆形
	/**加载图片
	 * 加载小图应再调用该方法前使用getSmallUri处理uri
	 * @param type 图片显示类型
	 * @param iv
	 * @param uri 网址url或本地路径path
	 */
	public static void loadImage(final ImageView iv, String uri, final int type) {
		if (iv == null) {// || iv.getWidth() <= 0) {
			Log.i(TAG, "loadImage  iv == null >> return;");
			return;
		}
		Log.i(TAG, "loadImage  iv" + (iv == null ? "==" : "!=") + "null; uri=" + uri);

		uri = getCorrectUri(uri);

		//新的加载图片
		imageLoader.displayImage(uri, iv, new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View arg1) {
				//XML代码直接设置src属性更直观方便
				//				switch (type) {
				//				case TYPE_OVAL:
				//					iv.setImageResource(R.drawable.oval_alpha);
				//					break;
				//				case TYPE_ROUND_CORNER:
				//					iv.setImageResource(R.drawable.round_alpha);
				//					break;
				//				default:
				//					iv.setImageResource(R.drawable.square_alpha);
				//					break;
				//				}
			}
			@Override
			public void onLoadingFailed(String imageUri, View arg1, FailReason arg2) {
			}
			@Override
			public void onLoadingComplete(String imageUri, View arg1, Bitmap loadedImage) {
				if (loadedImage == null) {
					Log.e(TAG, "loadImage  imageLoader.displayImage.onLoadingComplete  loadedImage == null >> return;");
					return;
				}
				switch (type) {
				case TYPE_OVAL:
					iv.setImageBitmap(CommonUtil.toRoundCorner(loadedImage, loadedImage.getWidth()/2));
					break;
				case TYPE_ROUND_CORNER:
					iv.setImageBitmap(CommonUtil.toRoundCorner(loadedImage, loadedImage.getWidth()/10));
					break;
				default:
					iv.setImageBitmap(loadedImage);
					break;
				}
			}
			@Override
			public void onLoadingCancelled(String imageUri, View arg1) {
			}
		});

	}


	public static final String FILE_PATH_PREFIX = StringUtil.FILE_PATH_PREFIX;

	/**如果需要加载小图请修改为小图实际标识
	 */
	public static String URL_SUFFIX_SMALL = "!common";
	/**获取可用的uri
	 * @param uri
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String getCorrectUri(String uri) {
		Log.i(TAG, "<<<<  getCorrectUri  uri = " + uri);
		uri = StringUtil.getNoBlankString(uri);

		if (uri.toLowerCase().startsWith(StringUtil.HTTP) == false) {
			uri = uri.startsWith(FILE_PATH_PREFIX) ? uri : FILE_PATH_PREFIX + uri;
		}

		Log.i(TAG, "getCorrectUri  return uri = " + uri + " >>>>> ");
		return uri;
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


	/**获取配置
	 * @param cornerRadiusSize
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static DisplayImageOptions getOption(int cornerRadiusSize) {
		Options options0 = new Options();
		options0.inPreferredConfig = Bitmap.Config.RGB_565;

		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		if (cornerRadiusSize > 0) {
			builder.displayer(new RoundedBitmapDisplayer(cornerRadiusSize));
		}

		return builder.cacheInMemory(true).cacheOnDisc(true).decodingOptions(options0).build();
	}

	

}
