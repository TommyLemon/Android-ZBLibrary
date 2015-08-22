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

import zuo.biao.library.R;
import zuo.biao.library.util.StringUtil;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**通用对话框类
 * @author lemon
 * @use new MyAlertDialog(...).show();
 */
public class MyAlertDialog extends Dialog implements android.view.View.OnClickListener {
	private static final String TAG = "MyAlertDialog";  

	/** 
	 * 自定义Dialog监听器 
	 */  
	public interface PriorityListener {  
		/** 
		 * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示 
		 */  
		public void refreshPriorityUI(boolean isPositive);  
	}


	@SuppressWarnings("unused")
	private Context context;
	private String title; 
	private String message; 
	private String strPositive;
	private String strNegative;
	private boolean showNegativeButton = true;
	private int imgResId;
	private int backgroundResId;
	private PriorityListener listener;

	/** 
	 * 带监听器参数的构造函数 
	 */  
	public MyAlertDialog(Context context, String title, String message, boolean showNegativeButton,
			PriorityListener listener) {
		super(context, R.style.MyDialog);

		this.context = context;
		this.title = title;
		this.message = message;
		this.showNegativeButton = showNegativeButton;
		this.listener = listener;  
	}
	public MyAlertDialog(Context context, String title, String message, boolean showNegativeButton,
			String strPositive, PriorityListener listener) {
		super(context, R.style.MyDialog);

		this.context = context;
		this.title = title;
		this.message = message;
		this.showNegativeButton = showNegativeButton;
		this.strPositive = strPositive;
		this.listener = listener;  
	}
	public MyAlertDialog(Context context, String title, String message, 
			String strPositive, String strNegative, PriorityListener listener) {
		super(context, R.style.MyDialog);

		this.context = context;
		this.title = title;
		this.message = message;
		this.strPositive = strPositive;
		this.strNegative = strNegative;
		this.listener = listener;  
	}
	public MyAlertDialog(Context context, String title, String message, 
			String strPositive, String strNegative, int imgResId, int backgroundResId, PriorityListener listener) {
		super(context, R.style.MyDialog);

		this.context = context;
		this.title = title;
		this.message = message;
		this.strPositive = strPositive;
		this.strNegative = strNegative;
		this.imgResId = imgResId;
		this.backgroundResId = backgroundResId;
		this.listener = listener;  
	}


	private LinearLayout llTitleBar;
	private ImageView img;
	private TextView tvTitle;
	private TextView tvMessage;
	private Button btnPositive;
	private Button btnNegative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.my_alert_dialog); 
		setCanceledOnTouchOutside(true);

		llTitleBar = (LinearLayout) findViewById(R.id.llMyAlertDialogTitlebar);
		tvTitle = (TextView) findViewById(R.id.tvMyAlertDialogTitle);
		img = (ImageView) findViewById(R.id.ivMyAlertDialogIcon);
		tvMessage = (TextView) findViewById(R.id.tvMyAlertDialogMessage);
		btnPositive = (Button) findViewById(R.id.btnMyAlertDialogPositive);
		btnNegative = (Button) findViewById(R.id.btnMyAlertDialogNegative);

		if (StringUtil.isNotEmpty(title, true)) {

			llTitleBar.setVisibility(View.VISIBLE);
			tvTitle.setText("" + StringUtil.getCurrentString());

			if (imgResId > 0) {
				try {
					img.setImageResource(imgResId);
				} catch (Exception e) {
					Log.i(TAG, "img.setImageResource(imgResId); >>> catch (Exception e)  " + e.getMessage());
				}
			}
			if (backgroundResId > 0) {
				try {
					llTitleBar.setBackgroundResource(backgroundResId);
				} catch (Exception e) {
					Log.i(TAG, "llTitleBar.setBackgroundResource(backgroundResId); >>> catch (Exception e)  " + e.getMessage());
				}
			}
		} else {
			llTitleBar.setVisibility(View.GONE);
		}

		if (StringUtil.isNotEmpty(strPositive, true)) {
			btnPositive.setText(StringUtil.getCurrentString());
		}
		btnPositive.setOnClickListener(this);

		if (showNegativeButton) {
			if (StringUtil.isNotEmpty(strNegative, true)) {
				btnNegative.setText(StringUtil.getCurrentString());
			}
			btnNegative.setOnClickListener(this);
		} else {
			btnNegative.setVisibility(View.GONE);
		}

		tvMessage.setText(StringUtil.getTrimedString(message));
	}

	@Override
	public void onClick(final View v) {
		if (v.getId() ==  R.id.btnMyAlertDialogPositive) {
			listener.refreshPriorityUI(true);
		} else if (v.getId() ==  R.id.btnMyAlertDialogNegative) {
			listener.refreshPriorityUI(false);
		}

		dismiss();
	}

}

