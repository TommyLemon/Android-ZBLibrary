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

package zblibrary.demo.activity_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;

import java.io.File;

import zblibrary.demo.DEMO.DemoMainActivity;
import zblibrary.demo.R;
import zblibrary.demo.application.DemoApplication;
import zblibrary.demo.util.Constant;
import zblibrary.demo.util.HttpRequest;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.DownloadUtil;
import zuo.biao.library.util.SettingUtil;

/**关于界面
 * @author Lemon
 */
public class AboutActivity extends BaseActivity implements OnClickListener, OnLongClickListener, OnBottomDragListener {
	private static final String TAG = "AboutActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, AboutActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	public Activity getActivity() {
		return this; //必须return this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity, this);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		if (SettingUtil.isOnTestMode) {
			showShortToast("测试服务器\n" + HttpRequest.URL_BASE);
		}


		//仅测试用
		HttpRequest.translate("library", 0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {
				showShortToast("测试Http请求:翻译library结果为\n" + resultJson);
			}
		});

	}

	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private ImageView ivAboutGesture;

	private TextView tvAboutAppInfo;

	private ImageView ivAboutQRCode;
	@Override
	public void initView() {

		ivAboutGesture = findView(R.id.ivAboutGesture);
		ivAboutGesture.setVisibility(SettingUtil.isFirstStart ? View.VISIBLE : View.GONE);
		if (SettingUtil.isFirstStart) {
			ivAboutGesture.setImageResource(R.drawable.gesture_left);
		}

		tvAboutAppInfo = findView(R.id.tvAboutAppInfo);

		ivAboutQRCode = findView(R.id.ivAboutQRCode, this);
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {

		tvAboutAppInfo.setText(DemoApplication.getInstance().getAppName()
				+ "\n" + DemoApplication.getInstance().getAppVersion());

		setQRCode();
	}


	private Bitmap qRCodeBitmap;
	/**显示二维码
	 */
	protected void setQRCode() {
		runThread(TAG + "setQRCode", new Runnable() {

			@Override
			public void run() {

				try {
					qRCodeBitmap = EncodingHandler.createQRCode(Constant.APP_DOWNLOAD_WEBSITE
							, (int) (2 * getResources().getDimension(R.dimen.qrcode_size)));
				} catch (WriterException e) {
					e.printStackTrace();
					Log.e(TAG, "initData  try {Bitmap qrcode = EncodingHandler.createQRCode(contactJson, ivContactQRCodeCode.getWidth());" +
							" >> } catch (WriterException e) {" + e.getMessage());
				}

				runUiThread(new Runnable() {
					@Override
					public void run() {
						ivAboutQRCode.setImageBitmap(qRCodeBitmap);
					}
				});
			}
		});
	}

	/**下载应用
	 */
	private void downloadApp() {
		showProgressDialog("正在下载...");
		runThread(TAG + "downloadApp", new Runnable() {
			@Override
			public void run() {
				File file = DownloadUtil.downLoadFile(context, "ZBLibraryDemo", ".apk", Constant.APP_DOWNLOAD_WEBSITE);
				dismissProgressDialog();
				DownloadUtil.openFile(context, file);
			}
		});
	}

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {

		findView(R.id.llAboutMainTabActivity).setOnClickListener(this);
		findView(R.id.llAboutZBLibraryMainActivity).setOnClickListener(this);

		findView(R.id.llAboutUpdate).setOnClickListener(this);
		findView(R.id.llAboutShare).setOnClickListener(this);
		findView(R.id.llAboutComment).setOnClickListener(this);

		findView(R.id.llAboutDeveloper, this).setOnLongClickListener(this);
		findView(R.id.llAboutWeibo, this).setOnLongClickListener(this);
		findView(R.id.llAboutContactUs, this).setOnLongClickListener(this);
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {
			toActivity(WebViewActivity.createIntent(context, "博客", Constant.APP_OFFICIAL_BLOG));

			ivAboutGesture.setImageResource(R.drawable.gesture_right);
			return;
		}

		if (SettingUtil.isFirstStart) {
			runThread(TAG + "onDragBottom", new Runnable() {
				@Override
				public void run() {
					Log.i(TAG, "onDragBottom  >> SettingUtil.putBoolean(context, SettingUtil.KEY_IS_FIRST_IN, false);");
					SettingUtil.putBoolean(SettingUtil.KEY_IS_FIRST_START, false);
				}
			});
		}

		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.llAboutMainTabActivity:
			startActivity(MainTabActivity.createIntent(context).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);

			enterAnim = exitAnim = R.anim.null_anim;
			finish();
			break;
		case R.id.llAboutZBLibraryMainActivity:
			startActivity(DemoMainActivity.createIntent(context));
			overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
			break;

		case R.id.llAboutUpdate:
			toActivity(WebViewActivity.createIntent(context, "更新日志", Constant.UPDATE_LOG_WEBSITE));
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
			toActivity(WebViewActivity.createIntent(context, "博客", Constant.APP_OFFICIAL_BLOG));
			break;
		case R.id.llAboutContactUs:
			CommonUtil.sendEmail(context, Constant.APP_OFFICIAL_EMAIL);
			break;

		case R.id.ivAboutQRCode:
			downloadApp();
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
			CommonUtil.copyText(context, Constant.APP_OFFICIAL_BLOG);
			return true;
		case R.id.llAboutContactUs:
			CommonUtil.copyText(context, Constant.APP_OFFICIAL_EMAIL);
			return true;
		default:
			break;
		}
		return false;
	}



	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
