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

package zuo.biao.library.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.StringUtil;

/**通用获取裁剪单张照片Activity
 * @author Lemon
 * @use toActivity或startActivityForResult > onActivityResult方法内data.getStringExtra(
 * CutPictureActivity.RESULT_PICTURE_PATH)可得到图片存储路径(String)
 */
public class CutPictureActivity extends BaseActivity {
	private static final String TAG = "CutPictureActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isActivityAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		//功能归类分区方法，必须调用<<<<<<<<<<
		initData();
		initView();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initView() {//必须调用
		//TODO
	}


	/**照片裁剪
	 * @param context
	 * @param requestCode
	 * @param path
	 * @param width
	 * @param height
	 */
	public void startPhotoZoom(String path, int width, int height) {
		startPhotoZoom(Uri.fromFile(new File(path)), width, height);
	}
	/**照片裁剪
	 * @param context
	 * @param requestCode
	 * @param fromFile
	 * @param width
	 * @param height
	 */
	public void startPhotoZoom(Uri fileUri, int width, int height) {
		intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(fileUri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", width);
		intent.putExtra("outputY", height);
		intent.putExtra("return-data", true);
		Log.i(TAG, "startPhotoZoom  "+ fileUri +" uri");
		toActivity(intent, REQUEST_CUT_PHOTO);
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	public static final String INTENT_ORIGINAL_PICTURE_PATH = "INTENT_ORIGINAL_PICTURE_PATH";
	public static final String INTENT_CUTTED_PICTURE_PATH = "INTENT_CUTTED_PICTURE_PATH";
	public static final String INTENT_CUTTED_PICTURE_NAME = "INTENT_CUTTED_PICTURE_NAME";

	public static final String INTENT_CUT_WIDTH = "INTENT_CUT_WIDTH";
	public static final String INTENT_CUT_HEIGHT = "INTENT_CUT_HEIGHT";

	private String originalPicturePath = "";
	private String cuttedPicturePath = "";
	private String cuttedPictureName = "";
	@Override
	public void initData() {//必须调用

		intent = getIntent();

		originalPicturePath = intent.getStringExtra(INTENT_ORIGINAL_PICTURE_PATH);
		int picWidth = intent.getIntExtra(INTENT_CUT_WIDTH, 0);
		int picHeight = intent.getIntExtra(INTENT_CUT_HEIGHT, 0);
		if (StringUtil.isNotEmpty(originalPicturePath, true) == false || (picWidth <= 0 && picHeight <= 0)) {
			showShortToast("图片不存在！");
			finish();
			return;
		}
		if (picHeight <= 0) {
			picHeight = picWidth;
		}
		if (picWidth <= 0) {
			picWidth = picHeight;
		}

		startPhotoZoom(originalPicturePath, picWidth, picHeight);
	}



	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须调用

	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final int REQUEST_CODE_CAMERA = 18;
	public static final int REQUEST_CODE_LOCAL = 19;
	public static final int REQUEST_CUT_PHOTO = 20;

	public static final String RESULT_PICTURE_PATH = "RESULT_PICTURE_PATH";
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CUT_PHOTO: //发送本地图片
				if (data != null) {
					Bundle bundle = data.getExtras();
					if (bundle != null) {
						Bitmap photo = bundle.getParcelable("data");
						//photo.
						if (photo != null) {
							//照片的路径
							//oringlePicturePath 不对
							cuttedPicturePath = intent.getStringExtra(INTENT_CUTTED_PICTURE_PATH);
							if (StringUtil.isFilePath(cuttedPicturePath) == false) {
								cuttedPicturePath = DataKeeper.fileRootPath + DataKeeper.imagePath;
							}
							cuttedPictureName = intent.getStringExtra(INTENT_CUTTED_PICTURE_NAME);
							if (StringUtil.isFilePath(cuttedPictureName) == false) {
								cuttedPictureName = "photo" + System.currentTimeMillis();
							}
							cuttedPicturePath = CommonUtil.savePhotoToSDCard(cuttedPicturePath, cuttedPictureName, "jpg", photo);
							setResult(RESULT_OK, new Intent().putExtra(RESULT_PICTURE_PATH, cuttedPicturePath));
						}
					}
				}
				break;
			default:
				break;
			}
		}

		finish();
	}

	@Override
	public void finish() {
		exitAnim = enterAnim = R.anim.null_anim;
		super.finish();
	}

	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}