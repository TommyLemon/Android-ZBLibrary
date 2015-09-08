package zuo.biao.library.ui;

import zuo.biao.library.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnPageReturnListener;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

/**通用网页Activity
 * @author Lemon
 * @use toActivity或startActivity(WebViewActivity.createIntent)
 */
public class WebViewActivity extends BaseActivity implements OnClickListener, OnPageReturnListener {
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
		setContentView(R.layout.web_view_activity, this);
		//类相关初始化
		context = this;
		isActivityAlive = true;
		//类相关初始化

		initView();
		initData();
		initListener();
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView tvWebViewTitle;
	private TextView tvWebViewReturn;
	private WebView wvWebView;

	private EditText etWebView;
	@Override
	public void initView() {

		tvWebViewTitle = (TextView) findViewById(R.id.tvWebViewTitle);
		tvWebViewReturn = (TextView) findViewById(R.id.tvWebViewReturn);

		wvWebView = (WebView) findViewById(R.id.wvWebView);  

		etWebView = (EditText) findViewById(R.id.etWebView);
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String INTENT_RETURN = "INTENT_RETURN";
	public static final String INTENT_URL = "INTENT_URL";

	private String url;
	private Handler webHandler = new Handler();
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	public void initData() {

		intent = getIntent();
	
		url = StringUtil.getCorrectUrl(intent.getStringExtra(INTENT_URL));
		if (StringUtil.isNotEmpty(url, true) == false) {
			Log.e(TAG, "initData  StringUtil.isNotEmpty(url, true) == false >> finish(); return;");
			finish();
			return;
		}

		if (StringUtil.isNotEmpty(intent.getStringExtra(INTENT_TITLE), true)) {
			tvWebViewTitle.setText("" + StringUtil.getCurrentString());
		}
		if (StringUtil.isNotEmpty(intent.getStringExtra(INTENT_RETURN), true)) {
			tvWebViewReturn.setText("" + StringUtil.getCurrentString());
		}

		WebSettings webSettings = wvWebView.getSettings();       
		webSettings.setJavaScriptEnabled(true);

		wvWebView.requestFocus();
		wvWebView.addJavascriptInterface(new Object() {       
			@SuppressWarnings("unused")
			public void clickOnAndroid() {       
				webHandler.post(new Runnable() {       
					public void run() {       
						wvWebView.loadUrl("javascript:wave()");       
					}       
				});       
			}       
		}, "demo");     
		loadUrl(url);
		//		禁用跳转浏览器，但因无client导致不能点击
		wvWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url){

				return false;
			}
		});

	}



	private void loadUrl(String url) {
		if (StringUtil.isUrl(url) == false) {
			return;
		}
		this.url = url;
		
		etWebView.setText(url);
		wvWebView.loadUrl(url); 		
	}

	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {

		findViewById(R.id.tvWebViewReturn).setOnClickListener(this);

		findViewById(R.id.ibtnWebView).setOnClickListener(this);
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvWebViewReturn) {
			finish();
		} else if (v.getId() == R.id.ibtnWebView) {
			if (StringUtil.isNotEmpty(etWebView, true)) {
				loadUrl(StringUtil.getCorrectUrl(etWebView));
			}
		}
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {       
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wvWebView.canGoBack()) {       
			wvWebView.goBack();
			etWebView.setText(StringUtil.getCorrectUrl(wvWebView.getUrl()));
			return true;       
		}       
		return super.onKeyUp(keyCode, event);       
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