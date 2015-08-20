package com.example.uidemo.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.uidemo.R;
import com.example.uidemo.application.DemoApplication;

import java.util.ArrayList;

import zuo.biao.library.MODEL.ModelActivity;
import zuo.biao.library.MODEL.ModelFragmentActivity;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.BottomMenuWindow;
import zuo.biao.library.ui.CutPictureActivity;
import zuo.biao.library.ui.DatePickerWindow;
import zuo.biao.library.ui.EditTextInfoActivity;
import zuo.biao.library.ui.EditTextInfoWindow;
import zuo.biao.library.ui.ItemOnlyDialog;
import zuo.biao.library.ui.MyAlertDialog;
import zuo.biao.library.ui.PlacePickerWindow;
import zuo.biao.library.ui.SelectPictureActivity;
import zuo.biao.library.ui.TopMenuWindow;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.StringUtil;
import zuo.biao.library.util.TimeUtil;

/**demo主页
 * @author Lemon
 */
public class DemoMainActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "DemoMainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_main_activity);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isActivityAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private String[] topbarColorNames = {"灰色", "蓝色", "黄色"};
	private int[] topbarColorResIds = {R.color.gray, R.color.blue,R.color.yellow};

	private View rlDemoMainTopbar;
	private View ivDemoMainMenu;

	private ImageView ivDemoMainHead;
	private TextView tvDemoMainHeadName;

	private ScrollView svDemoMain;
	@Override
	public void initView() {//必须调用

		rlDemoMainTopbar = findViewById(R.id.rlDemoMainTopbar);
		ivDemoMainMenu = findViewById(R.id.ivDemoMainMenu);

		ivDemoMainHead = (ImageView) findViewById(R.id.ivDemoMainHead);
		tvDemoMainHeadName = (TextView) findViewById(R.id.tvDemoMainHeadName);

		svDemoMain = (ScrollView) findViewById(R.id.svDemoMain);
	}

	/**显示列表选择弹窗
	 */
	private void showItemOnlyDialog() {
		new ItemOnlyDialog(context, topbarColorNames, "选择颜色", new ItemOnlyDialog.PriorityListener() {

			@Override
			public void refreshPriorityUI(int position) {

				rlDemoMainTopbar.setBackgroundResource(topbarColorResIds[position]);
			}
		}).show();
	}

	/**显示对话框弹窗
	 */
	private void showMyAlertDialog() {
		new MyAlertDialog(context, "改导航颜色", "确定将导航栏颜色改为红色？", true, new MyAlertDialog.PriorityListener() {

			@Override
			public void refreshPriorityUI(boolean isPositive) {
				if (isPositive) {

					rlDemoMainTopbar.setBackgroundResource(R.color.red);
				}
			}
		}).show();
	}

	/**显示顶部菜单
	 */
	private void showTopMenu() {
		toActivity(TopMenuWindow.createIntent(context, new String[]{"更改导航栏颜色", "更改图片"}), REQUEST_TO_TOP_MENU, false);
	}


	private String picturePath;
	/**选择图片
	 */
	private void selectPicture() {
		toActivity(SelectPictureActivity.createIntent(context), REQUEST_TO_SELECT_PICTURE, false);
	}

	/**裁剪图片
	 * @param path
	 */
	private void cutPicture(String path) {
		if (StringUtil.isFilePath(path) == false) {
			Log.e(TAG, "cutPicture  StringUtil.isFilePath(path) == false >> showShortToast(找不到图片);return;");
			showShortToast("找不到图片");
			return;
		}
		this.picturePath = path;

		intent = new Intent(context, CutPictureActivity.class);
		intent.putExtra(CutPictureActivity.INTENT_ORIGINAL_PICTURE_PATH, picturePath);
		intent.putExtra(CutPictureActivity.INTENT_CUTTED_PICTURE_PATH, DataKeeper.fileRootPath + DataKeeper.imagePath);
		intent.putExtra(CutPictureActivity.INTENT_CUTTED_PICTURE_NAME, "photo" + System.currentTimeMillis());
		intent.putExtra(CutPictureActivity.INTENT_CUT_HEIGHT, 200);
		toActivity(intent, REQUEST_TO_CUT_PICTURE);
	}

	/**显示图片
	 * @param path
	 */
	private void setPicture(String path) {
		if (StringUtil.isFilePath(path) == false) {
			Log.e(TAG, "setPicture  StringUtil.isFilePath(path) == false >> showShortToast(找不到图片);return;");
			showShortToast("找不到图片");
			return;
		}
		this.picturePath = path;

		svDemoMain.smoothScrollTo(0, 0);
		try {
			ivDemoMainHead.setImageDrawable(new BitmapDrawable(getResources(), picturePath));
		} catch (Exception e) {
			showShortToast("设置图片失败了，再试一次吧^_^");
		}
	}

	/**编辑图片名称
	 */
	private void editName(boolean toWindow) {
		if (toWindow) {
			intent = EditTextInfoWindow.createIntent(context, EditTextInfoWindow.TYPE_NICK
					, "照片名称", StringUtil.getTrimedString(tvDemoMainHeadName), DemoApplication.getApplication().getAppPackageName());
		} else {
			intent = EditTextInfoActivity.createIntent(context, EditTextInfoActivity.TYPE_NICK
					, "照片名称", StringUtil.getTrimedString(tvDemoMainHeadName));
		}

		toActivity(intent, REQUEST_TO_EDIT_TEXT_INFO, ! toWindow);
	}

	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用

	}



	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须调用

		findViewById(R.id.ivDemoMainReturn).setOnClickListener(this);
		ivDemoMainMenu.setOnClickListener(this);

		ivDemoMainHead.setOnClickListener(this);
		tvDemoMainHeadName.setOnClickListener(this);

		findViewById(R.id.llDemoMainItemOnlyDialog).setOnClickListener(this);
		findViewById(R.id.llDemoMainMyAlertDialog).setOnClickListener(this);

		findViewById(R.id.llDemoMainSelectPictureActivity).setOnClickListener(this);
		findViewById(R.id.llDemoMainCutPictureActivity).setOnClickListener(this);
		findViewById(R.id.llDemoMainWebViewActivity).setOnClickListener(this);
		findViewById(R.id.llDemoMainEditTextInfoActivity).setOnClickListener(this);
		findViewById(R.id.llDemoMainModelActivity).setOnClickListener(this);
		findViewById(R.id.llDemoMainModelFragmentActivity).setOnClickListener(this);

		findViewById(R.id.llDemoMainTopMenuWindow).setOnClickListener(this);
		findViewById(R.id.llDemoMainBottomMenuWindow).setOnClickListener(this);
		findViewById(R.id.llDemoMainEditTextInfoWindow).setOnClickListener(this);
		findViewById(R.id.llDemoMainDatePickerWindow).setOnClickListener(this);
		findViewById(R.id.llDemoMainPlacePickerWindow).setOnClickListener(this);

	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	//@Override
	//public void onClick(View v) {//直接调用不会显示v被点击效果
	//	switch (v.getId()) {
	//		case R.id.ivDemoMainReturn:
	//			enterAnim = R.anim.fade;
	//			exitAnim = R.anim.bottom_push_out;
	//			finish();
	//			break;
	//		case R.id.ivDemoMainMenu:
	//			showTopMenu();
	//			break;
	//
	//		case R.id.ivDemoMainHead:
	//			selectPicture();
	//			break;
	//		case R.id.tvDemoMainHeadName:
	//			editName(true);
	//			break;
	//
	//		case R.id.llDemoMainItemOnlyDialog:
	//			showItemOnlyDialog();
	//			break;
	//		case R.id.llDemoMainMyAlertDialog:
	//			showMyAlertDialog();
	//			break;
	//
	//		case R.id.llDemoMainSelectPictureActivity:
	//			selectPicture();
	//			break;
	//		case R.id.llDemoMainCutPictureActivity:
	//			cutPicture(picturePath);
	//			break;
	//		case R.id.llDemoMainWebViewActivity:
	//			toActivity(WebViewActivity.createIntent(context, "百度首页", "www.baidu.com"));
	//			break;
	//		case R.id.llDemoMainEditTextInfoActivity:
	//			editName(false);
	//			break;
	//		case R.id.llDemoMainModelActivity:
	//			toActivity(ModelActivity.createIntent(context, null));
	//			break;
	//		case R.id.llDemoMainModelFragmentActivity:
	//			toActivity(ModelFragmentActivity.createIntent(context, null));
	//			break;
	//		case R.id.llDemoMainTopMenuWindow:
	//			showTopMenu();
	//			break;
	//		case R.id.llDemoMainBottomMenuWindow:
	//			toActivity(BottomMenuWindow.createIntent(context, "选择颜色", topbarColorNames), REQUEST_TO_BOTTOM_MENU, false);
	//			break;
	//		case R.id.llDemoMainEditTextInfoWindow:
	//			editName(true);
	//			break;
	//		case R.id.llDemoMainDatePickerWindow:
	//			toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
	//					, TimeUtil.getDateDetail(System.currentTimeMillis())), REQUEST_TO_DATE_PICKER, false);
	//			break;
	//		case R.id.llDemoMainPlacePickerWindow:
	//			toActivity(PlacePickerWindow.createIntent(context, 2), REQUEST_TO_PLACE_PICKER, false);
	//			break;
	//		default:
	//			break;
	//	}
	//}
	//Library内switch方法中case R.id.idx:报错
	@Override
	public void onClick(View v) {//直接调用不会显示v被点击效果
		if (v.getId() == R.id.ivDemoMainReturn) {
			enterAnim = R.anim.fade;
			exitAnim = R.anim.bottom_push_out;
			finish();
		} else if (v.getId() == R.id.ivDemoMainMenu) {
			showTopMenu();
		} else if (v.getId() ==  R.id.ivDemoMainHead) {
			selectPicture();
		} else if (v.getId() ==  R.id.tvDemoMainHeadName) {
			editName(true);
		} else if (v.getId() ==  R.id.llDemoMainItemOnlyDialog) {
			showItemOnlyDialog();
		} else if (v.getId() ==  R.id.llDemoMainMyAlertDialog) {
			showMyAlertDialog();
		} else if (v.getId() ==  R.id.llDemoMainSelectPictureActivity) {
			selectPicture();
		} else if (v.getId() ==  R.id.llDemoMainCutPictureActivity) {
			cutPicture(picturePath);
		} else if (v.getId() ==  R.id.llDemoMainWebViewActivity) {
			toActivity(WebViewActivity.createIntent(context, "百度首页", "www.baidu.com"));
		} else if (v.getId() ==  R.id.llDemoMainEditTextInfoActivity) {
			editName(false);
		} else if (v.getId() ==  R.id.llDemoMainModelActivity) {
			toActivity(ModelActivity.createIntent(context, null));
		} else if (v.getId() ==  R.id.llDemoMainModelFragmentActivity) {
			toActivity(ModelFragmentActivity.createIntent(context, null));
		} else if (v.getId() ==  R.id.llDemoMainTopMenuWindow) {
			showTopMenu();
		} else if (v.getId() ==  R.id.llDemoMainBottomMenuWindow) {
			toActivity(BottomMenuWindow.createIntent(context, "选择颜色", topbarColorNames), REQUEST_TO_BOTTOM_MENU, false);
		} else if (v.getId() ==  R.id.llDemoMainEditTextInfoWindow) {
			editName(true);
		} else if (v.getId() ==  R.id.llDemoMainDatePickerWindow) {
			toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
			, TimeUtil.getDateDetail(System.currentTimeMillis())), REQUEST_TO_DATE_PICKER, false);
		} else if (v.getId() ==  R.id.llDemoMainPlacePickerWindow) {
			toActivity(PlacePickerWindow.createIntent(context, DemoApplication.getApplication().getAppPackageName(), 2), REQUEST_TO_PLACE_PICKER, false);
		}
	}



	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private static final int REQUEST_TO_SELECT_PICTURE = 20;
	private static final int REQUEST_TO_CUT_PICTURE = 21;
	private static final int REQUEST_TO_EDIT_TEXT_INFO = 22;
	private static final int REQUEST_TO_TOP_MENU = 23;
	private static final int REQUEST_TO_BOTTOM_MENU = 24;
	private static final int REQUEST_TO_DATE_PICKER = 25;
	private static final int REQUEST_TO_PLACE_PICKER = 26;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_TO_SELECT_PICTURE:
				if (data != null) {
					cutPicture(data.getStringExtra(SelectPictureActivity.RESULT_PICTURE_PATH));
				}
				break;
			case REQUEST_TO_CUT_PICTURE://返回的结果
				if (data != null) {
					setPicture(data.getStringExtra(CutPictureActivity.RESULT_PICTURE_PATH));
				}
				break;
			case REQUEST_TO_EDIT_TEXT_INFO:
				if (data != null) {
					svDemoMain.smoothScrollTo(0, 0);
					tvDemoMainHeadName.setText(StringUtil.getTrimedString(
							data.getStringExtra(EditTextInfoWindow.RESULT_VALUE)));
				}
				break;
			case REQUEST_TO_TOP_MENU:
				if (data != null) {
					switch (data.getIntExtra(TopMenuWindow.RESULT_POSITION, -1)) {
					case 0:
						showItemOnlyDialog();
						break;
					case 1:
						selectPicture();
						break;
					default:
						break;
					}
				}
				break;
			case REQUEST_TO_BOTTOM_MENU:
				if (data != null) {
					int selectedPosition = data.getIntExtra(BottomMenuWindow.RESULT_POSITION, -1);
					if (selectedPosition >= 0 && selectedPosition < topbarColorResIds.length) {
						rlDemoMainTopbar.setBackgroundResource(topbarColorResIds[selectedPosition]);
					}
				}
				break;
			case REQUEST_TO_DATE_PICKER:
				if (data != null) {
					//					List<Integer> selectedPositionList = data.getIntegerArrayListExtra(
					//							GridPickerWindow.RESULT_SELECTED_POSITIONS);
					//					showShortToast("selectedPositionList.size() = " + (selectedPositionList == null
					//							? "null" : selectedPositionList.size()));

					ArrayList<Integer> dateList = data.getIntegerArrayListExtra(DatePickerWindow.RESULT_DATE_DETAIL_LIST);
					if (dateList != null && dateList.size() >= 3) {
						showShortToast("选择的日期为" + dateList.get(0) + "-" + dateList.get(1) + "-" + dateList.get(2));
					}
				}
				break;
			case REQUEST_TO_PLACE_PICKER:
				if (data != null) {
					ArrayList<String> placeList = data.getStringArrayListExtra(PlacePickerWindow.RESULT_PLACE_LIST);
					if (placeList != null) {
						String place = "";
						for (String s : placeList) {
							place += StringUtil.getTrimedString(s);
						}
						showShortToast("选择的地区为: " + place);
					}
				}
				break;
			default:
				break;
			}
		}
	}

	private long firstTime = 0;//第一次返回按钮计时
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
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

		return super.onKeyUp(keyCode, event);
	}


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}