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

package zblibrary.demo.activity_fragment;

import zblibrary.demo.R;
import zblibrary.demo.model.User;
import zblibrary.demo.util.BottomMenuUtil;
import zblibrary.demo.view.UserView;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.manager.ListDiskCacheManager;
import zuo.biao.library.ui.BottomMenuView;
import zuo.biao.library.ui.BottomMenuView.OnBottomMenuItemClickListener;
import zuo.biao.library.ui.BottomMenuWindow;
import zuo.biao.library.ui.EditTextInfoActivity;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**联系人资料界面
 * @author Lemon
 */
public class UserActivity extends BaseActivity implements OnClickListener, OnBottomDragListener, OnBottomMenuItemClickListener {
	public static final String TAG = "UserActivity";

	/**获取启动UserActivity的intent
	 * @param context
	 * @param userId
	 * @return
	 */
	public static Intent createIntent(Context context, long userId) {
		return new Intent(context, UserActivity.class).putExtra(INTENT_ID, userId);
	}


	@Override
	@NonNull
	public BaseActivity getActivity() {
		return this;
	}

	private long userId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_activity, this);

		intent = getIntent();
		userId = intent.getLongExtra(INTENT_ID, userId);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView tvUserTitle;

	private ViewGroup llUserBusinessCardContainer;
	private UserView userView;
	
	private TextView tvUserTag;

	private ViewGroup llUserBottomMenuContainer;
	private BottomMenuView bottomMenuView;
	@Override
	public void initView() {//必须调用

		tvUserTitle = (TextView) findViewById(R.id.tvUserTitle);

		//添加用户名片<<<<<<<<<<<<<<<<<<<<<<
		llUserBusinessCardContainer = (ViewGroup) findViewById(R.id.llUserBusinessCardContainer);
		llUserBusinessCardContainer.removeAllViews();

		userView = new UserView(context, getResources());
		llUserBusinessCardContainer.addView(userView.createView(getLayoutInflater()));
		userView.setView(new User());
		//添加用户名片>>>>>>>>>>>>>>>>>>>>>>>


		tvUserTag = (TextView) findViewById(R.id.tvUserTag);
		
		
		//添加底部菜单<<<<<<<<<<<<<<<<<<<<<<
		llUserBottomMenuContainer = (ViewGroup) findViewById(R.id.llUserBottomMenuContainer);
		llUserBottomMenuContainer.removeAllViews();

		bottomMenuView = new BottomMenuView(context, getResources(), REQUEST_TO_BOTTOM_MENU);
		llUserBottomMenuContainer.addView(bottomMenuView.createView(getLayoutInflater()));
		//添加底部菜单>>>>>>>>>>>>>>>>>>>>>>>

	}

	private User user;
	private void setUser(User user_) {
		this.user = user_;
		if (user == null) {
			Log.w(TAG, "setUser  user == null >> user = new User();");
			user = new User();
		}

		userView.setView(user);
		
		tvUserTag.setText(StringUtil.getTrimedString(user.getTag()));
	}

	
	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initData() {//必须调用

		if (StringUtil.isNotEmpty(getIntent().getStringExtra(INTENT_TITLE), true)) {
			tvUserTitle.setText(StringUtil.getCurrentString());
		}

		bottomMenuView.setView(BottomMenuUtil.getMenuList(BottomMenuUtil.USER));

		runThread(TAG + "initData", new Runnable() {
			@Override
			public void run() {

				user = ListDiskCacheManager.getInstance().get(User.class, "" + userId);
				runUiThread(new Runnable() {
					@Override
					public void run() {
						setUser(user);
					}
				});
			}
		});
	}

	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须调用

		findViewById(R.id.ivUserReturn).setOnClickListener(this);
		
		findViewById(R.id.llUserTag).setOnClickListener(this);

		bottomMenuView.setOnMenuItemClickListener(this);
	}

	@Override
	public void onBottomMenuItemClick(int intentCode) {
		if (user == null) {
			Log.e(TAG, "onBottomMenuItemClick  user == null >> return;");
			return;
		}
		switch (intentCode) {
		case BottomMenuUtil.INTENT_CODE_SEND:
			CommonUtil.shareInfo(context, user.toString());
			break;
		case BottomMenuUtil.INTENT_CODE_QRCODE:
			toActivity(QRCodeActivity.createIntent(context, userId));
			break;
		default:
			String phone = StringUtil.getCorrectPhone(user.getPhone());
			if (StringUtil.isNotEmpty(phone, true) == false) {
				return;
			}
			switch (intentCode) {
			case BottomMenuUtil.INTENT_CODE_SEND_MESSAGE:
				CommonUtil.toMessageChat(context, user.getPhone());
				break;
			case BottomMenuUtil.INTENT_CODE_CALL:
				CommonUtil.call(context, phone);
				break;
			case BottomMenuUtil.INTENT_CODE_SEND_EMAIL:
				CommonUtil.sendEmail(context, phone + "@qq.com");
				break;
			default:
				break;
			}
			break;
		}
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {

			return;
		}

		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivUserReturn:
			onDragBottom(false);
			break;
			
		case R.id.llUserTag:
			toActivity(EditTextInfoActivity.createIntent(context, "标签"
					, StringUtil.getTrimedString(tvUserTag)), REQUEST_TO_EDIT_TEXT_INFO);
			break;
		default:
			break;
		}
	}


	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private static final int REQUEST_TO_BOTTOM_MENU = 1;
	private static final int REQUEST_TO_EDIT_TEXT_INFO = 2;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case REQUEST_TO_BOTTOM_MENU:
			if (data != null) {
				onBottomMenuItemClick(data.getIntExtra(BottomMenuWindow.RESULT_INTENT_CODE, -1));
			}
			break;
		case REQUEST_TO_EDIT_TEXT_INFO:
			if (user == null) {
				user = new User(userId);
			}
			user.setTag(data == null ? null : data.getStringExtra(EditTextInfoActivity.RESULT_VALUE));
			setUser(user);
			break;
		}
	}


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}