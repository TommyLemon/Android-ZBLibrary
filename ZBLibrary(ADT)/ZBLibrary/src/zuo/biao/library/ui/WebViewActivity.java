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
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**通用网页Activity
 * @author Lemon
 * @use toActivity或startActivity(WebViewActivity.createIntent)
 */
public class WebViewActivity extends BaseActivity implements OnClickListener, OnBottomDragListener {
	public static final String TAG = "WebViewActivity";

	/**获取启动这个Activity的Intent
	 * @param title
	 * @param url
	 */
	public static Intent createIntent(Context context, String title, String url) {
		return new Intent(context, WebViewActivity.class).
				putExtra(WebViewActivity.INTENT_TITLE, title).
				putExtra(WebViewActivity.INTENT_URL, url);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.web_view_activity, this);//传this是为了全局滑动返回
		//类相关初始化
		context = this;
		isAlive = true;
		//类相关初始化

		initView();
		initData();
		initListener();
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView tvWebViewTitle;
	private WebView wvWebView;
	@Override
	public void initView() {

		tvWebViewTitle = (TextView) findViewById(R.id.tvWebViewTitle);

		wvWebView = (WebView) findViewById(R.id.wvWebView);
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_RETURN = "INTENT_RETURN";
	public static final String INTENT_URL = "INTENT_URL";

	@SuppressWarnings("unused")
	private Handler webHandler = new Handler();
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	public void initData() {

		intent = getIntent();
		if (StringUtil.isNotEmpty(intent.getStringExtra(INTENT_TITLE), true)) {
			tvWebViewTitle.setText("" + StringUtil.getCurrentString());
		}

		WebSettings webSettings = wvWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		String url = StringUtil.getCorrectUrl(getIntent().getStringExtra(INTENT_URL));
		if (StringUtil.isNotEmpty(url, true) == false) {
			Log.e(TAG, "initData  StringUtil.isNotEmpty(url, true) == false >> finish(); return;");
			finish();
			return;
		}

		wvWebView.requestFocus();
		wvWebView.loadUrl(url);
		wvWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url){

				return false;
			}
		});

	}



	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {

		findViewById(R.id.tvWebViewReturn).setOnClickListener(this);

	}
	
	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {
			if (wvWebView.canGoForward()) {
				wvWebView.goForward();
			}
			return;
		}		
		onBackPressed();
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvWebViewReturn) {
			finish();
		}
	}
	@Override
	public void onBackPressed() {
		if (wvWebView.canGoBack()) {
			wvWebView.goBack();
			return;
		}
		
		super.onBackPressed();
	}

	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	protected void onDestroy() {
		super.onDestroy();
		context = null;
		wvWebView = null;
	}



	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



}