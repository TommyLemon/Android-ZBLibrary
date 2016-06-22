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

package zuo.biao.library.base;

import zuo.biao.library.R;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**基础底部弹出界面Activity
 * @author Lemon
 * @warn 不要在子类重复这个类中onCreate中的代码
 * @use extends BaseBottomWindow, 具体参考.DemoBottomWindow
 */
public abstract class BaseBottomWindow extends BaseActivity implements OnClickListener {
	//	private static final String TAG = "BaseBottomWindow";

	public static final String INTENT_ITEMS = "INTENT_ITEMS";
	public static final String INTENT_ITEM_IDS = "INTENT_ITEM_IDS";

	public static final String RESULT_TITLE = "RESULT_TITLE";
	public static final String RESULT_ITEM = "RESULT_ITEM";
	public static final String RESULT_ITEM_ID = "RESULT_ITEM_ID";


	protected Resources resources = null;//该Activity资源管理器
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		resources = getResources();
	}



	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	protected View vBaseBottomWindowRoot;//子Activity全局背景View

	@Nullable
	protected TextView tvBaseBottomWindowTitle;

	@Nullable
	protected TextView tvBaseBottomWindowReturn;

	protected Animation animation;//界面进出动画
	/**
	 * 如果在子类中调用(即super.initView());则view必须含有initView中初始化用到的id(非@Nullable标记)且id对应的View的类型全部相同；
	 * 否则必须在子类initView中重写这个类中initView内的代码(所有id替换成可用id)
	 */
	@Override
	public void initView() {// 必须调用
		enterAnim = exitAnim = R.anim.null_anim;

		vBaseBottomWindowRoot = findViewById(R.id.vBaseBottomWindowRoot);

		tvBaseBottomWindowTitle = (TextView) findViewById(R.id.tvBaseBottomWindowTitle);
		tvBaseBottomWindowReturn = (TextView) findViewById(R.id.tvBaseBottomWindowReturn);

		vBaseBottomWindowRoot.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bottom_window_enter));
	}

	@SuppressLint("HandlerLeak")
	public Handler exitHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			BaseBottomWindow.super.finish();
		}
	};

	// UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {// 必须调用
		intent = getIntent();

		if (tvBaseBottomWindowTitle != null) {
			tvBaseBottomWindowTitle.setVisibility(StringUtil.isNotEmpty(getTitleName(), true) ? View.VISIBLE : View.GONE);
			tvBaseBottomWindowTitle.setText(StringUtil.getTrimedString(getTitleName()));
		}

	}

	/**获取导航栏标题名
	 * @return null - View.GONE; "" - View.GONE; "xxx" - "xxx"
	 */
	@Nullable
	protected abstract String getTitleName();

	// data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	// listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {// 必须调用

		if (tvBaseBottomWindowReturn != null) {
			tvBaseBottomWindowReturn.setOnClickListener(this);
		}

//		if (vBaseBottomWindowRoot != null) {
//			vBaseBottomWindowRoot.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					finish();
//				}
//			});
//		}

	}


	// 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvBaseBottomWindowReturn) {
			finish();
		}
	}


	// 类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**带动画退出,并使退出事件只响应一次
	 */
	@Override
	public void finish() {
		vBaseBottomWindowRoot.setEnabled(false);

		vBaseBottomWindowRoot.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bottom_window_exit));
		vBaseBottomWindowRoot.setVisibility(View.GONE);

		exitHandler.sendEmptyMessageDelayed(0, 200);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		vBaseBottomWindowRoot = null;

		tvBaseBottomWindowTitle = null;
		tvBaseBottomWindowReturn = null;
	}
	
	// 类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// 系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	// listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	// 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}