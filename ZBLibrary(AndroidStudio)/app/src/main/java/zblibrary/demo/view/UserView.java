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
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**用户View
 * @author Lemon
 * @use
 * <br> UserView userView = new UserView(context, resources);
 * <br> adapter中使用:[具体参考.DemoAdapter2(getView使用自定义View的写法)]
 * <br> convertView = userView.createView(inflater);
 * <br> userView.bindView(position, data);
 * <br> 或  其它类中使用: 
 * <br> containerView.addView(userView.createView(inflater));
 * <br> userView.bindView(data);
 * <br> 然后
 * <br> userView.setOnDataChangedListener(onDataChangedListener);data = userView.getData();//非必需
 * <br> userView.setOnClickListener(onClickListener);//非必需
 */
public class UserView extends BaseView<User> implements OnClickListener {
	private static final String TAG = "UserView";

	public UserView(Activity context, Resources resources) {
		super(context, resources);
	}

	public ImageView ivUserViewHead;
	public ImageView ivUserViewStar;

	public TextView tvUserViewSex;

	public TextView tvUserViewName;
	public TextView tvUserViewId;
	public TextView tvUserViewNumber;
	@SuppressLint("InflateParams")
	@Override
	public View createView(LayoutInflater inflater) {
		convertView = inflater.inflate(R.layout.user_view, null);

		ivUserViewHead = findViewById(R.id.ivUserViewHead, this);
		ivUserViewStar = findViewById(R.id.ivUserViewStar, this);

		tvUserViewSex = findViewById(R.id.tvUserViewSex, this);

		tvUserViewName = findViewById(R.id.tvUserViewName);
		tvUserViewId = findViewById(R.id.tvUserViewId);
		tvUserViewNumber = findViewById(R.id.tvUserViewNumber);

		return convertView;
	}

	@Override
	public void bindView(User data){
		if (data == null) {
			Log.e(TAG, "bindView data == null >> data = new User(); ");
			data = new User();
		}
		this.data = data;

		ImageLoaderUtil.loadImage(ivUserViewHead, data.getHead(), ImageLoaderUtil.TYPE_OVAL);
		ivUserViewStar.setImageResource(data.getStarred() ? R.drawable.star_light : R.drawable.star);

		tvUserViewSex.setBackgroundResource(data.getSex() == User.SEX_FEMALE
				? R.drawable.circle_pink : R.drawable.circle_blue);
		tvUserViewSex.setText(data.getSex() == User.SEX_FEMALE ?  "女" : "男");
		tvUserViewSex.setTextColor(getColor(data.getSex() == User.SEX_FEMALE ? R.color.pink : R.color.blue));

		tvUserViewName.setText(StringUtil.getTrimedString(data.getName()));
		tvUserViewId.setText("ID:" + data.getId());
		tvUserViewNumber.setText("Phone:" + StringUtil.getNoBlankString(data.getPhone()));
	}

	@Override
	public void onClick(View v) {
		if (BaseModel.isCorrect(data) == false) {
			return;
		}
		switch (v.getId()) {
		case R.id.ivUserViewHead:
			toActivity(WebViewActivity.createIntent(context, data.getName(), data.getHead()));
			break;
		default:
			switch (v.getId()) {
			case R.id.ivUserViewStar:
				data.setStarred(! data.getStarred());
				break;
			case R.id.tvUserViewSex:
				data.setSex(data.getSex() == User.SEX_FEMALE ? User.SEX_MAIL : User.SEX_FEMALE);
				break;
			}
			bindView(data);
			break;
		}		
	}
}