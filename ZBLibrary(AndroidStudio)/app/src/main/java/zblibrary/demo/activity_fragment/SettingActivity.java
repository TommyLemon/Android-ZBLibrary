package zblibrary.demo.activity_fragment;

import java.util.ArrayList;

import zblibrary.demo.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseFragmentActivity;
import zuo.biao.library.interfaces.OnFinishListener;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.SettingUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**设置界面Activity
 * @author Lemon
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class SettingActivity extends BaseActivity implements OnClickListener, OnFinishListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_PHONE = "INTENT_PHONE";
	public static final String INTENT_PHONE_LIST = "INTENT_PHONE_LIST";

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return createIntent(context, null);
	}
	/**启动这个Activity的Intent
	 * @param context
	 * @param phone
	 * @return
	 */
	public static Intent createIntent(Context context, String phone) {
		return new Intent(context, SettingActivity.class).putExtra(INTENT_PHONE, phone);
	}

	/**启动这个Activity的Intent
	 * @param context
	 * @param phoneList
	 * @return
	 */
	public static Intent createIntent(BaseFragmentActivity context, ArrayList<String> phoneList) {
		return new Intent(context, SettingActivity.class).putExtra(INTENT_PHONE_LIST, phoneList);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity, this);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>


		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private View ivSettingForward;

	private ImageView[] ivSettings;
	@Override
	public void initView() {//必须调用

		ivSettingForward = findViewById(R.id.ivSettingForward);

		ivSettings = new ImageView[5];
		ivSettings[0] = (ImageView) findViewById(R.id.ivSettingVoice); 
		ivSettings[1] = (ImageView) findViewById(R.id.ivSettingVibrate); 

		ivSettings[2] = (ImageView) findViewById(R.id.ivSettingNoDisturb); 

		ivSettings[3] = (ImageView) findViewById(R.id.ivSettingTestMode); 
		ivSettings[4] = (ImageView) findViewById(R.id.ivSettingFirstStart);

	}

	private boolean[] settings;
	private int[] switchResIds = new int[]{R.drawable.off, R.drawable.on};
	/**设置开关
	 * @param which
	 * @param isToOn
	 */
	private void setSwitch(int which, boolean isToOn) {
		if (ivSettings == null || which < 0 || which >= ivSettings.length) {
			Log.e(TAG, "ivSettings == null || which < 0 || which >= ivSettings.length >> reutrn;");
			return;
		}

		ivSettings[which].setImageResource(isToOn == false ? switchResIds[0] : switchResIds[1]);
		settings[which] = isToOn;
	}





	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initData() {//必须调用


		showProgressDialog(R.string.loading);

		runThread(TAG + "initData", new Runnable() {

			@Override
			public void run() {

				settings = SettingUtil.getAllBooleans(context);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (isAlive) {
							dismissProgressDialog();
							if (settings == null || settings.length <= 0) {
								finish();
								return;
							}
							for (int i = 0; i < settings.length; i++) {
								setSwitch(i, settings[i]);
							}
						}
					}
				});
			}
		});


	}



	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须调用
		findViewById(R.id.tvSettingReturn).setOnClickListener(this);
		ivSettingForward.setOnClickListener(this);

		for (int i = 0; i < ivSettings.length; i++) {
			final int which = i;
			ivSettings[which].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					isSettingChanged = true;
					setSwitch(which, ! settings[which]);					
				}
			});
		}
	}


	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvSettingReturn:
			finish();
			break;
		case R.id.ivSettingForward:
			SettingUtil.restoreDefault(context);
			initData();
			break;
		default:
			break;
		}
	}


	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private static final int REQUEST_TO_MODEL = 10;
	public static final int RESULT_MODEL = 21;
	public static final String RESULT_CLICKED_ITEM = "RESULT_CLICKED_ITEM";
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case REQUEST_TO_MODEL:
			if (data != null) {
				showShortToast("clicked Item position = " + data.getIntExtra(RESULT_CLICKED_ITEM, -1));
			}
			break;
		default:
			break;
		}
	}


	private boolean isSettingChanged = false;
	@Override
	public void finish() {
		if (isSettingChanged) {
			showProgressDialog("正在保存设置，请稍后...");
			runThread(TAG, new Runnable() {

				@Override
				public void run() {

					SettingUtil.putAllBoolean(context, settings);
					isSettingChanged = false;
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (isAlive) {
								SettingActivity.this.finish();
							}
						}
					});
				}
			});
			return;
		}

		super.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		ivSettings = null;
		settings = null;
		context = null;
	}



	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}