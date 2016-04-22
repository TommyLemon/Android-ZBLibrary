package zuo.biao.library.base;

import zuo.biao.library.R;
import android.app.Application;

/**Application示例
 * @author Lemon
 * @use extends BaseApplication
 */
public class BaseApplication extends Application {
	//	private static final String TAG = "BaseApplication";

	private static BaseApplication baseApplication;
	public static BaseApplication getApplication() {
		return baseApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		baseApplication = this;

		System.out.println("项目启动");
	}


	public String getAppName() {
		return getResources().getString(R.string.app_name);
	}

	/**
	 * 应用包名，在AndroidManifest文件内申明
	 */
	public static String PACKAGE_NAME = "zuo.biao.library";

	/**
	 * 作为launcher的activity是否活着（已启动且未被销毁）
	 */
	public static boolean isMainActivityAlive = false;


}
