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

package zblibrary.demo.view;

import zblibrary.demo.R;
import zblibrary.demo.model.User;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.ImageLoaderUtil;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**用户View
 * @author Lemon
 * @use
	UserView userView = new UserView(context, resources);
	adapter中使用[具体参考.DemoAdapter2(getView使用自定义View的写法)]
	convertView = userView.createView(inflater);
	userView.setView(position, data);
    或  其它类中使用 
    containerView.addView(userView.createView(inflater));
    userView.setView(data);
    然后
	userView.setOnDataChangedListener(onDataChangedListener);data = userView.getData();//非必需
	userView.setOnClickListener(onClickListener);//非必需
 */
public class UserView extends BaseView<User> implements OnClickListener {
	private static final String TAG = "UserView";

	public UserView(Activity context, Resources resources) {
		super(context, resources);
	}


	public ImageView ivUserViewHead;
	public TextView tvUserViewName;
	public TextView tvUserViewNumber;
	@SuppressLint("InflateParams")
	@Override
	public View createView(@NonNull LayoutInflater inflater) {
		convertView = inflater.inflate(R.layout.user_view, null);

		ivUserViewHead = findViewById(R.id.ivUserViewHead, this);
		tvUserViewName = findViewById(R.id.tvUserViewName);
		tvUserViewNumber = findViewById(R.id.tvUserViewNumber);

		return convertView;
	}

	@Override
	public void setView(User data){
		if (data == null) {
			Log.e(TAG, "setView data == null >> return; ");
			return;
		}
		this.data = data;

		ImageLoaderUtil.loadImage(ivUserViewHead, data.getHead(), ImageLoaderUtil.TYPE_OVAL);//没有测试用的small图片 ImageLoaderUtil.getSmallUri(data.getHead()));
		tvUserViewName.setText(StringUtil.getTrimedString(data.getName()));
		tvUserViewNumber.setText(StringUtil.getNoBlankString(data.getPhone()));
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivUserViewHead:
			if (BaseModel.isCorrect(data)) {
				toActivity(WebViewActivity.createIntent(context, data.getName(), data.getHead()));
			}
			break;
		default:
			break;
		}		
	}

}
