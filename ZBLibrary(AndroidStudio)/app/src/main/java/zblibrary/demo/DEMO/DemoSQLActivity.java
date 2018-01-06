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

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import zblibrary.demo.R;
import zblibrary.demo.manager.SQLHelper;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.util.StringUtil;


/** 使用方法：复制>粘贴>改名>改代码 */
/**数据库SQLite示例Activity
 * @author Lemon
 * @use toActivity(DemoSQLActivity.createIntent(...));
 */
public class DemoSQLActivity extends BaseActivity implements OnClickListener, OnBottomDragListener {
	private static final String TAG = "DemoSQLActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, DemoSQLActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	public Activity getActivity() {
		return this; //必须return this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_sql_activity, this);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		showShortToast("点击[重置]按钮会恢复数据");
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView tvDemoSQLInfo;

	private ScrollView svDemoSQL;

	private TextView tvDemoSQLShow0;
	private TextView tvDemoSQLShow1;

	private EditText etDemoSQLQueryColumn;
	private EditText etDemoSQLQueryValue;

	private EditText etDemoSQLEditColumn;
	private EditText etDemoSQLEditValue;
	@Override
	public void initView() {//必须在onCreate方法内调用

		tvDemoSQLInfo = findView(R.id.tvDemoSQLInfo);

		svDemoSQL = findView(R.id.svDemoSQL);

		tvDemoSQLShow0 = findView(R.id.tvDemoSQLShow0);
		tvDemoSQLShow1 = findView(R.id.tvDemoSQLShow1);

		etDemoSQLQueryColumn = findView(R.id.etDemoSQLQueryColumn);
		etDemoSQLQueryValue = findView(R.id.etDemoSQLQueryValue);

		etDemoSQLEditColumn = findView(R.id.etDemoSQLEditColumn);
		etDemoSQLEditValue = findView(R.id.etDemoSQLEditValue);
	}


	private void printAll() {
		runUiThread(new Runnable() {

			@Override
			public void run() {
				etDemoSQLQueryColumn.setText("");
			}
		});
		runThread(TAG + "printAll", new Runnable() {

			@Override
			public void run() {
				print("", getString(sqlHelper));
			}
		});
	}

	private void print(String s) {
		print(StringUtil.getTrimedString(tvDemoSQLShow1), s);
	}
	private void print(final String s0, final String s1) {
		runUiThread(new Runnable() {

			@Override
			public void run() {
				dismissProgressDialog();
				tvDemoSQLShow0.setText("" + s0);
				tvDemoSQLShow1.setText("" + s1);
				svDemoSQL.smoothScrollTo(0, 0);
			}
		});
	}

	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	private SQLHelper sqlHelper;
	@Override
	public void initData() {//必须在onCreate方法内调用

		sqlHelper = new SQLHelper(context);

		tvDemoSQLInfo.setText(SQLHelper.TABLE_NAME + "(" + SQLHelper.COLUMN_ID + ", " + SQLHelper.COLUMN_NAME
				+ ", " + SQLHelper.COLUMN_PHONE + ", " + SQLHelper.COLUMN_MAIL + ", " + SQLHelper.COLUMN_OTHER + ")");

		etDemoSQLQueryColumn.setText(SQLHelper.COLUMN_ID);
		etDemoSQLQueryValue.setText("1");

		etDemoSQLEditColumn.setText(SQLHelper.COLUMN_NAME);
		etDemoSQLEditValue.setText("xxx");

		printAll();
	}



	private void reset() {
		showProgressDialog("Resetting...");
		runThread(TAG + "reset", new Runnable() {

			@Override
			public void run() {
				sqlHelper.onUpgrade(sqlHelper.getWritableDatabase(), SQLHelper.TABLE_VERSION, SQLHelper.TABLE_VERSION + 1);

				for (int i = 0; i < 10; i++) {
					ContentValues values = new ContentValues();
					values.put(SQLHelper.COLUMN_NAME, "name_" + i);
					values.put(SQLHelper.COLUMN_PHONE, "" + (13000000 + i*i));
					values.put(SQLHelper.COLUMN_MAIL, (13000000 + i*i) + "@qq.com");
					sqlHelper.insert(values);
				}

				runUiThread(new Runnable() {

					@Override
					public void run() {
						printAll();
					}
				});
			}
		});

	}


	private void insert() {
		showProgressDialog("Inserting...");
		runThread(TAG + "insert", new Runnable() {

			@Override
			public void run() {
				sqlHelper.insert(getContentValues());
				runUiThread(new Runnable() {

					@Override
					public void run() {
						etDemoSQLQueryColumn.setText(StringUtil.getString(etDemoSQLEditColumn));
						etDemoSQLQueryValue.setText(StringUtil.getString(etDemoSQLEditValue));
						query();
					}
				});
			}
		});
	}
	private void delete() {
		showProgressDialog("Deleting...");
		runThread(TAG + "delete", new Runnable() {

			@Override
			public void run() {
				sqlHelper.delete(getQueryColumn(), getQueryValue());
				runUiThread(new Runnable() {

					@Override
					public void run() {
						etDemoSQLQueryColumn.setText("");
						query();
					}
				});
			}
		});
	}
	private void update() {
		showProgressDialog("Updating...");
		runThread(TAG + "update", new Runnable() {

			@Override
			public void run() {
				sqlHelper.update(getQueryColumn(), getQueryValue(), getContentValues());
				runUiThread(new Runnable() {

					@Override
					public void run() {
						etDemoSQLQueryColumn.setText(StringUtil.getString(etDemoSQLEditColumn));
						etDemoSQLQueryValue.setText(StringUtil.getString(etDemoSQLEditValue));
						query();
					}
				});
			}
		});
	}
	private void query() {
		showProgressDialog("Querying...");
		runThread(TAG + "query", new Runnable() {

			@Override
			public void run() {
				print(getString(sqlHelper));
			}
		});
	}



	private String getQueryColumn() {
		return StringUtil.getTrimedString(etDemoSQLQueryColumn);
	}
	private String getQueryValue() {
		return StringUtil.getTrimedString(etDemoSQLQueryValue);
	}
	private String getEditColumn() {
		return StringUtil.getTrimedString(etDemoSQLEditColumn);
	}
	private String getEditValue() {
		return StringUtil.getTrimedString(etDemoSQLEditValue);
	}

	private ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put(getQueryColumn(), getQueryValue());
		values.put(getEditColumn(), getEditValue());
		return values;
	}


	private String getString(SQLHelper sqlHelper) {
		List<ContentValues> list = sqlHelper.getList(getQueryColumn(), getQueryValue());
		if (list == null || list.isEmpty()) {
			return "";
		}

		String s = "{\n";
		for (ContentValues values : list) {
			s += getString(values) + ",";
		}
		return s += "\n}";
	}


	private String getString(ContentValues values) {
		if (values == null || values.size() <= 0) {
			return "";
		}

		String s = "{\n";
		for (String key : values.keySet()) {
			s += ("    " + key + ":" + values.get(key) + ",\n");
		}
		return s += "}";
	}


	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须在onCreate方法内调用

		findView(R.id.btnDemoSQLInsert).setOnClickListener(this);
		findView(R.id.btnDemoSQLDelete).setOnClickListener(this);
		findView(R.id.btnDemoSQLUpdate).setOnClickListener(this);
		findView(R.id.btnDemoSQLQuery).setOnClickListener(this);
	}

	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {
			reset();
			return;
		}

		finish();
	}

	//系统自带监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDemoSQLInsert:
			insert();
			break;
		case R.id.btnDemoSQLDelete:
			delete();
			break;
		case R.id.btnDemoSQLUpdate:
			update();
			break;
		case R.id.btnDemoSQLQuery:
			query();
			break;
		default:
			break;
		}
	}




	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}