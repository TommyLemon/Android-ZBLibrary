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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import zblibrary.demo.R;
import zblibrary.demo.model.User;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.StringUtil;

/**用户View
 * @author Lemon
 * @use
 * <br> UserView userView = new UserView(context, resources);
 * <br> adapter中使用:[具体参考.BaseViewAdapter(getView使用自定义View的写法)]
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

	public UserView(Activity context, ViewGroup parent) {
		super(context, R.layout.user_view, parent);
	}

	public ImageView ivUserViewHead;
	public ImageView ivUserViewStar;

	public TextView tvUserViewSex;

	public TextView tvUserViewName;
	public TextView tvUserViewId;
	public TextView tvUserViewNumber;
	@SuppressLint("InflateParams")
	@Override
	public View createView() {
		ivUserViewHead = findView(R.id.ivUserViewHead, this);
		ivUserViewStar = findView(R.id.ivUserViewStar, this);

		tvUserViewSex = findView(R.id.tvUserViewSex, this);

		tvUserViewName = findView(R.id.tvUserViewName);
		tvUserViewId = findView(R.id.tvUserViewId);
		tvUserViewNumber = findView(R.id.tvUserViewNumber);

		return super.createView();
	}

	@Override
	public void bindView(User data_){
		super.bindView(data_ != null ? data_ : new User());

		Glide.with(context).asBitmap().load(data.getHead()).into(new SimpleTarget<Bitmap>() {

			@Override
			public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
				ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth()/2));
			}
		});

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