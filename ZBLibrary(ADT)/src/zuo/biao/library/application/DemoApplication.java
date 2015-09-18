package zuo.biao.library.application;

import zuo.biao.library.R;
import zuo.biao.library.util.DataKeeper;
import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

/**Application示例
 * @author Lemon
 * @use extends DemoApplication
 */
public class DemoApplication extends Application {
	//	private static final String TAG = "DemoApplication";

	private static DemoApplication demoApplication;
	public static DemoApplication getApplication() {
		return demoApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		demoApplication = this;
		
		DataKeeper.init();

		System.out.println("项目启动");
	}


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

	public String getAppName() {
		return getResources().getString(R.string.app_name);
	}



	//类相关<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//类相关>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}
