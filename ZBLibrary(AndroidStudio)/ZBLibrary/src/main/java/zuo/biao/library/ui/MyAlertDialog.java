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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**通用对话框类
 * @author lemon
 * @use new MyAlertDialog(...).show();
 */
public class MyAlertDialog extends Dialog implements android.view.View.OnClickListener {
//	private static final String TAG = "MyAlertDialog";  

	/** 
	 * 自定义Dialog监听器 
	 */  
	public interface OnButtonClickListener {  
		/** 
		 * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示 
		 */  
		public void onButtonClick(int requestCode, boolean isPositive);  
	}


	@SuppressWarnings("unused")
	private Context context;
	private String title; 
	private String message; 
	private String strPositive;
	private String strNegative;
	private boolean showNegativeButton = true;
	private int requestCode;
	private OnButtonClickListener listener;

	/** 
	 * 带监听器参数的构造函数 
	 */  
	public MyAlertDialog(Context context, String title, String message, boolean showNegativeButton,
			int requestCode, OnButtonClickListener listener) {
		super(context, R.style.MyDialog);

		this.context = context;
		this.title = title;
		this.message = message;
		this.showNegativeButton = showNegativeButton;
		this.requestCode = requestCode;
		this.listener = listener;  
	}
	public MyAlertDialog(Context context, String title, String message, boolean showNegativeButton,
			String strPositive, int requestCode, OnButtonClickListener listener) {
		super(context, R.style.MyDialog);

		this.context = context;
		this.title = title;
		this.message = message;
		this.showNegativeButton = showNegativeButton;
		this.strPositive = strPositive;
		this.requestCode = requestCode;
		this.listener = listener;  
	}
	public MyAlertDialog(Context context, String title, String message, 
			String strPositive, String strNegative, int requestCode, OnButtonClickListener listener) {
		super(context, R.style.MyDialog);

		this.context = context;
		this.title = title;
		this.message = message;
		this.strPositive = strPositive;
		this.strNegative = strNegative;
		this.requestCode = requestCode;
		this.listener = listener;  
	}

	private TextView tvTitle;
	private TextView tvMessage;
	private Button btnPositive;
	private Button btnNegative;
	@Override
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.my_alert_dialog); 
		setCanceledOnTouchOutside(true);

		tvTitle = (TextView) findViewById(R.id.tvMyAlertDialogTitle);
		tvMessage = (TextView) findViewById(R.id.tvMyAlertDialogMessage);
		btnPositive = (Button) findViewById(R.id.btnMyAlertDialogPositive);
		btnNegative = (Button) findViewById(R.id.btnMyAlertDialogNegative);

		tvTitle.setVisibility(StringUtil.isNotEmpty(title, true) ? View.VISIBLE : View.GONE);
		tvTitle.setText("" + StringUtil.getCurrentString());

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
			listener.onButtonClick(requestCode, true);
		} else if (v.getId() ==  R.id.btnMyAlertDialogNegative) {
			listener.onButtonClick(requestCode, false);
		}

		dismiss();
	}

}

