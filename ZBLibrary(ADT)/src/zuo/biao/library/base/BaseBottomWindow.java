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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.R;

/**基础底部弹出界面Activity
 * @author Lemon
 * @use extends BaseBottomWindow且调用setContentView(int layoutResID, BaseBottomWindow context, int backgroundViewResID)方法
 */
public abstract class BaseBottomWindow extends Activity {
	private static final String TAG = "BaseBottomWindow";

	protected BaseBottomWindow context = null;//在onCreate方法中赋值，不能在子Activity中创建
	protected boolean isActivityAlive = false;//该Activity是否已被使用并未被销毁。 在onCreate方法中赋值为true，不能在子Activity中创建

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉默认标题栏
	}
	
	
	private View vBg;//子Activity全局背景View
	protected Animation animation;//界面进出动画
	/**设置界面布局View
	 * @param layoutResID - 子Activity全局View的R.layout.ID
	 * @param context 
	 * @param backgroundViewResID - 子Activity全局背景View的R.id.ID
	 */
	public void setContentView(int layoutResID, BaseBottomWindow context, int backgroundViewResID) {
		setContentView(layoutResID);

		animation = AnimationUtils.loadAnimation(context, R.anim.bottom_push_in_keyboard);
		animation.setDuration(200);

		//导致卡顿this改context也没用		vBg = LayoutInflater.from(this).inflate(layoutResID, null);
		vBg = context.findViewById(backgroundViewResID);
		vBg.startAnimation(animation);
		//		vBg.setVisibility(View.VISIBLE);
		//响应的是显示区域而不是半透明背景,不过这个功能非必需		vBg.setOnClickListener(l);
	}

	public static final String INTENT_TITLE = "INTENT_TITLE";
	
	protected Intent intent = null;//可用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
	protected List<Handler> handlerList = new ArrayList<Handler>();
	protected List<Runnable> runnableList = new ArrayList<Runnable>();

	public abstract void initView();//UI显示方法，必须在setContentView后调用
	public abstract void initData();//data数据方法，必须在setContentView后调用
	public abstract void initListener();//listener事件监听方法，必须在setContentView后调用


	@SuppressLint("HandlerLeak")
	public Handler exitHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			BaseBottomWindow.super.finish();
			overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
		}
	};


	/**运行线程
	 * @param threadName
	 * @param runnable 
	 * @return 
	 */
	public Handler runThread(String threadName, Runnable runnable) {
		if (runnable == null) {
			Log.e(TAG, "runThread  runnable == null >> return");
			return null;
		}
		HandlerThread handlerThread = new HandlerThread("" + threadName);
		handlerThread.start();//创建一个HandlerThread并启动它
		Handler handler = new Handler(handlerThread.getLooper());//使用HandlerThread的looper对象创建Handler
		handler.post(runnable);//将线程post到Handler中

		handlerList.add(handler);
		runnableList.add(runnable);

		return handler;
	}
	
	/**带动画退出,并使退出事件只响应一次
	 */
	@Override
	public void finish() {
		isActivityAlive = false;

		vBg.setEnabled(false);

		animation = AnimationUtils.loadAnimation(context, R.anim.bottom_push_out_keyboard);
		animation.setDuration(200);
		vBg.startAnimation(animation);
		vBg.setVisibility(View.GONE);

		exitHandler.sendEmptyMessageDelayed(0, 200);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//里面的代码不需要重写，通过super.onDestroy();即可得到<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		isActivityAlive = false;
		if (handlerList != null && runnableList != null) {
			for (int i = 0; i < handlerList.size(); i++) {
				try {
					(handlerList.get(i)).removeCallbacks(runnableList.get(i));
				} catch (Exception e) {
					Log.e(TAG, "onDestroy try { handler.removeCallbacks(runnable);...  >> catch  : " + e.getMessage());
				}
			}
		}
		//里面的代码不需要重写，通过super.onDestroy();即可得到>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	}

	
}
