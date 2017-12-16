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

package zblibrary.demo.DEMO;

import android.content.Context;
import android.content.Intent;

import zuo.biao.library.base.BaseBroadcastReceiver;
import zuo.biao.library.util.Log;


/** 使用方法：复制>粘贴>改名>改代码 */
/**BroadcastReceiver示例
 * @author Lemon
 * @use
 * <br>  demoBroadcastReceiver = new HeadsetConnectionBroadcastReceiver(context);
 * <br>  demoBroadcastReceiver.register(onHeadsetConnectionChangedListener);
 * <br>  或
 * <br>  demoBroadcastReceiver.register();demoBroadcastReceiver.setOnReceiveListener(onReceiveListener);
 * <br>  然后在Activity或Fragment的onDestroy内
 * <br>  demoBroadcastReceiver.unregister();
 * <br>  具体参考.DemoFragmentActivity(initEvent方法内)
 */
public class HeadsetConnectionBroadcastReceiver extends BaseBroadcastReceiver {
	private static final String TAG = "HeadsetConnectionBroadcastReceiver";

	//示例代码<<<<<<<<<<<<<<<<<<<
	/**耳机状态改变（插入、拔出）监听回调
	 */
	public interface OnHeadsetConnectionChangedListener {
		void onHeadsetConnectionChanged(boolean isConnected);
	}
	//示例代码>>>>>>>>>>>>>>>>>>>


	public HeadsetConnectionBroadcastReceiver(Context context) {
		super(context);
	}

	//示例代码<<<<<<<<<<<<<<<<<<<
	private OnHeadsetConnectionChangedListener onHeadsetConnectionChangedListener;
	public HeadsetConnectionBroadcastReceiver register(OnHeadsetConnectionChangedListener listener) {
		this.onHeadsetConnectionChangedListener = listener;
		register();
		return this;
	}
	//示例代码>>>>>>>>>>>>>>>>>>>

	@Override
	public BaseBroadcastReceiver register() {
		//示例代码<<<<<<<<<<<<<<<<<<<
		//TODO android.intent.action.HEADSET_PLUG改为你需要的action，//支持String, String[], List<String>
		return (BaseBroadcastReceiver) register(context, this, "android.intent.action.HEADSET_PLUG");
		//示例代码>>>>>>>>>>>>>>>>>>>
	}
	@Override
	public void unregister() {
		unregister(context, this);
	}

	//示例代码<<<<<<<<<<<<<<<<<<<
	public static final String STATE = "state";
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		//		if (onReceiveListener != null) {
		//			onReceiveListener.onReceive(intent);
		//			return;
		//		}
		if (intent != null && intent.hasExtra(STATE)){
			Log.i(TAG, "onReceive intent.getIntExtra(STATE, 0) = " + intent.getIntExtra(STATE, 0));
			if (onHeadsetConnectionChangedListener != null) {
				onHeadsetConnectionChangedListener.onHeadsetConnectionChanged(intent.getIntExtra(STATE, 0) == 1);
			}
		}
	}
	//示例代码>>>>>>>>>>>>>>>>>>>

}
