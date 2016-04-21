package zblibrary.demo.activity_fragment;

import zblibrary.demo.R;
import zblibrary.demo.constant.Constant;
import zblibrary.demo.manager.HttpRequest;
import zuo.biao.library.DEMO.DemoMainActivity;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.SettingUtil;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

/**关于主界面
 * @author Lemon
 */
public class AboutActivity extends BaseActivity implements OnClickListener, OnLongClickListener {
	//	private static final String TAG = "AboutActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_IS_EXIT_BY_DOUBLE_CLICK = "INTENT_IS_EXIT_BY_DOUBLE_CLICK";

	/**启动这个Activity的Intent
	 * isExitByDoubleClick = true
	 * @param context
	 * @param isExitByDoubleClick
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return createIntent(context, true);
	}
	/**启动这个Activity的Intent
	 * @param context
	 * @param isExitByDoubleClick
	 * @return
	 */
	public static Intent createIntent(Context context, boolean isExitByDoubleClick) {
		return new Intent(context, AboutActivity.class).putExtra(INTENT_IS_EXIT_BY_DOUBLE_CLICK, isExitByDoubleClick);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	
	
	private boolean isExitByDoubleClick = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		intent = getIntent();
		isExitByDoubleClick = intent.getBooleanExtra(INTENT_IS_EXIT_BY_DOUBLE_CLICK, isExitByDoubleClick);
		
		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

		if (SettingUtil.isOnTestMode) {
			CommonUtil.showShortToast(context, "测试服务器\n" + HttpRequest.URL_BASE);
		}
	}

	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initView() {

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {

	}



	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {

		findViewById(R.id.llAboutZBLibraryMainActivity).setOnClickListener(this);
		findViewById(R.id.llAboutBottomTabActivity).setOnClickListener(this);

		findViewById(R.id.llAboutShare).setOnClickListener(this);
		findViewById(R.id.llAboutComment).setOnClickListener(this);

		findViewById(R.id.llAboutDeveloper).setOnClickListener(this);
		findViewById(R.id.llAboutWeibo).setOnClickListener(this);
		findViewById(R.id.llAboutContactUs).setOnClickListener(this);

		findViewById(R.id.llAboutDeveloper).setOnLongClickListener(this);
		findViewById(R.id.llAboutWeibo).setOnLongClickListener(this);
		findViewById(R.id.llAboutContactUs).setOnLongClickListener(this);
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.llAboutZBLibraryMainActivity:
			startActivity(DemoMainActivity.createIntent(context));
			overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
			break;
		case R.id.llAboutBottomTabActivity:
			startActivity(BottomTabActivity.createIntent(context));
			overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
			break;

		case R.id.llAboutShare:
			CommonUtil.shareInfo(context, getString(R.string.share_app) + "\n 点击链接直接查看ZBLibrary\n" + Constant.APP_DOWNLOAD_WEBSITE);
			break;
		case R.id.llAboutComment:
			showShortToast("应用未上线不能查看");
			startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=" + getPackageName())));
			break;

		case R.id.llAboutDeveloper:
			toActivity(WebViewActivity.createIntent(context, "开发者", Constant.APP_DEVELOPER_WEBSITE));
			break;
		case R.id.llAboutWeibo:
			toActivity(WebViewActivity.createIntent(context, "微博", Constant.APP_OFFICIAL_WEIBO));
			break;
		case R.id.llAboutContactUs:
			CommonUtil.sendEmail(context, Constant.APP_OFFICIAL_EMAIL);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.llAboutDeveloper:
			CommonUtil.copyText(context, Constant.APP_DEVELOPER_WEBSITE);
			return true;
		case R.id.llAboutWeibo:
			CommonUtil.copyText(context, Constant.APP_OFFICIAL_WEIBO);
			return true;
		case R.id.llAboutContactUs:
			CommonUtil.copyText(context, Constant.APP_OFFICIAL_EMAIL);
			return true;
		default:
			break;
		}
		return false;
	}


	private long firstTime = 0;//第一次返回按钮计时
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			if (isExitByDoubleClick) {
				long secondTime = System.currentTimeMillis();
				if(secondTime - firstTime > 2000){
					showShortToast("再按一次退出");
					firstTime = secondTime;
				} else {//完全退出
					moveTaskToBack(false);//应用退到后台
					System.exit(0);
				}
				return true;
			}
		}

		return super.onKeyUp(keyCode, event);
	}


	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
