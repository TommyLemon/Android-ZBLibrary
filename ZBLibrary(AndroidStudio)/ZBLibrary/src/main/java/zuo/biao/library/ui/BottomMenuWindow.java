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

import java.util.ArrayList;
import java.util.Arrays;

import zuo.biao.library.R;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**通用底部弹出菜单
 * @author lemon
 * @use
	toActivity(BottomMenuWindow.createIntent);
	>> onActivityResult方法内
	data.getIntExtra(BottomMenuWindow.RESULT_ITEM_ID) 可得到点击的(int) position
	或
	data.getIntExtra(BottomMenuWindow.RESULT_INTENT_CODE) 可得到点击的(int) intentCode
 */
public class BottomMenuWindow extends Activity implements OnItemClickListener, OnClickListener {
	private static final String TAG = "BottomMenuWindow";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动BottomMenuWindow的Intent
	 * @param context
	 * @param names
	 * @return
	 */
	public static Intent createIntent(Context context, String[] names) {
		return createIntent(context, names, new ArrayList<Integer>());
	}

	/**启动BottomMenuWindow的Intent
	 * @param context
	 * @param nameList
	 * @return
	 */
	public static Intent createIntent(Context context, ArrayList<String> nameList) {
		return createIntent(context, nameList, null);
	}

	/**启动BottomMenuWindow的Intent
	 * @param context
	 * @param names
	 * @param intentCodes
	 * @return
	 */
	public static Intent createIntent(Context context, String[] names, int[] intentCodes) {
		return new Intent(context, BottomMenuWindow.class).
				putExtra(INTENT_ITEMS, names).
				putExtra(INTENT_INTENTCODES, intentCodes);
	}

	/**启动BottomMenuWindow的Intent
	 * @param context
	 * @param names
	 * @param intentCodeList
	 * @return
	 */
	public static Intent createIntent(Context context, String[] names, ArrayList<Integer> intentCodeList) {
		return new Intent(context, BottomMenuWindow.class).
				putExtra(INTENT_ITEMS, names).
				putExtra(INTENT_INTENTCODES, intentCodeList);
	}

	/**启动BottomMenuWindow的Intent
	 * @param context
	 * @param nameList
	 * @param intentCodeList
	 * @return
	 */
	public static Intent createIntent(Context context, 
			ArrayList<String> nameList, ArrayList<Integer> intentCodeList) {
		return new Intent(context, BottomMenuWindow.class).
				putStringArrayListExtra(INTENT_ITEMS, nameList).
				putIntegerArrayListExtra(INTENT_INTENTCODES, intentCodeList);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	private boolean isActivityAlive;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottom_menu_window);

		isActivityAlive = true;

		init();
	}

	public static final String INTENT_TITLE = "INTENT_TITLE";
	public static final String INTENT_ITEMS = "INTENT_ITEMS";
	public static final String INTENT_INTENTCODES = "INTENT_INTENTCODES";

	public static final String RESULT_TITLE = "RESULT_TITLE";
	public static final String RESULT_NAME = "RESULT_NAME";
	public static final String RESULT_ITEM_ID = "RESULT_ITEM_ID";
	public static final String RESULT_INTENT_CODE = "RESULT_INTENT_CODE";

	private TextView tvBottomMenuTitle;
	private View llBottomMenuBg;
	private View tvBottomMenuCancel;
	private View llBottomMenuMenuContainer;
	private ListView lvBottomMenu;

	private String title;
	private ArrayList<String> nameList = null;
	private ArrayList<Integer> intentCodeList = null;

	private ArrayAdapter<String> adapter;
	private Animation animation;
	private void init() {

		llBottomMenuBg = findViewById(R.id.llBottomMenuBg);
		tvBottomMenuCancel = findViewById(R.id.tvBottomMenuCancel);
		

		llBottomMenuMenuContainer = findViewById(R.id.llBottomMenuMenuContainer);

		tvBottomMenuTitle = (TextView) findViewById(R.id.tvBottomMenuTitle);

		Intent intent = getIntent();

		title = intent.getStringExtra(INTENT_TITLE);
		if (StringUtil.isNotEmpty(title, true)) {
			tvBottomMenuTitle.setVisibility(View.VISIBLE);
			tvBottomMenuTitle.setText(StringUtil.getCurrentString());
		} else {
			tvBottomMenuTitle.setVisibility(View.GONE);
		}
		

		int[] intentCodes = intent.getIntArrayExtra(INTENT_INTENTCODES);
		if (intentCodes == null || intentCodes.length <= 0) {
			intentCodeList = intent.getIntegerArrayListExtra(INTENT_INTENTCODES);
		} else {
			intentCodeList = new ArrayList<Integer>();
			for (int code : intentCodes) {
				intentCodeList.add(code);
			}
		}

		String[] menuItems = intent.getStringArrayExtra(INTENT_ITEMS);
		if (menuItems == null || menuItems.length <= 0) {
			nameList = intent.getStringArrayListExtra(INTENT_ITEMS);
		} else {
			nameList = new ArrayList<String>(Arrays.asList(menuItems));
		}
		if (nameList == null || nameList.size() <= 0) {
			Log.e(TAG, "init   nameList == null || nameList.size() <= 0 >> finish();return;");
			finish();
			return;
		}

		animation = AnimationUtils.loadAnimation(this, R.anim.bottom_push_in_keyboard);
		animation.setDuration(150);
		llBottomMenuMenuContainer.startAnimation(animation);
		llBottomMenuMenuContainer.setVisibility(View.VISIBLE);

		adapter = new ArrayAdapter<String>(this, R.layout.bottom_menu_item, R.id.tvBottomMenuItem, nameList);

		lvBottomMenu = (ListView) findViewById(R.id.lvBottomMenu);
		lvBottomMenu.setAdapter(adapter);
		lvBottomMenu.setOnItemClickListener(this);
		
		llBottomMenuBg.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				exit();
				return true;
			}
		});
		tvBottomMenuCancel.setOnClickListener(this);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (isActivityAlive) {
				exit();
				return true;
			}
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() ==  R.id.tvBottomMenuCancel) {
			exit();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		Intent intent = new Intent()
		.putExtra(RESULT_TITLE, StringUtil.getTrimedString(tvBottomMenuTitle))
		.putExtra(RESULT_ITEM_ID, position);
		if (intentCodeList != null && intentCodeList.size() > position) {
			intent.putExtra(RESULT_INTENT_CODE, intentCodeList.get(position));
		}

		setResult(RESULT_OK, intent);
		exit();
	}


	@SuppressLint("HandlerLeak")
	private Handler exitHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			finish();
			overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
		}
	};

	public void exit() {
		isActivityAlive = false;

		llBottomMenuBg.setEnabled(false);
		tvBottomMenuCancel.setEnabled(false);
		lvBottomMenu.setEnabled(false);

		animation = AnimationUtils.loadAnimation(this, R.anim.bottom_push_out_keyboard);
		animation.setDuration(150);
		llBottomMenuMenuContainer.startAnimation(animation);
		llBottomMenuMenuContainer.setVisibility(View.GONE);

		exitHandler.sendEmptyMessageDelayed(0, 240);
	}



}
