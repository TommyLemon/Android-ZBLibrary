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
import zuo.biao.library.base.BaseView;
import zuo.biao.library.util.ImageLoaderUtil;
import zuo.biao.library.util.StringUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**用户View
 * @author Lemon
 * @use
	UserView userView = new UserView(context, inflater);
	adapter中使用convertView = userView.getView();//[具体见.DemoAdapter]
    或  其它类中使用  containerView.addView(userView.getConvertView());
	userView.setView(object);
	userView.setOnDataChangedListener(onDataChangedListener);object = userView.getData();//非必需
	userView.setOnClickListener(onClickListener);//非必需
	...
 */
public class UserView extends BaseView<User> {
	private static final String TAG = "UserView";

	public UserView(Activity context, LayoutInflater inflater) {
		super(context, inflater);
	}


	public ImageView ivUserViewHead;
	public TextView tvUserViewName;
	public TextView tvUserViewNumber;
	@SuppressLint("InflateParams")
	@Override
	public View getView() {
		convertView = inflater.inflate(R.layout.user_view, null);

		//示例代码<<<<<<<<<<<<<<<<
		ivUserViewHead = (ImageView) findViewById(R.id.ivUserViewHead);
		tvUserViewName = (TextView) findViewById(R.id.tvUserViewName);
		tvUserViewNumber = (TextView) findViewById(R.id.tvUserViewNumber);
		//示例代码>>>>>>>>>>>>>>>>

		return convertView;
	}



	private User data;//传进来的数据
	@Override
	public User getData() {
		return data;
	}

	@Override
	public void setView(User data){
		if (data == null) {
			Log.e(TAG, "setView data == null >> return; ");
			return;
		}
		this.data = data;

		ImageLoaderUtil.loadImage(ivUserViewHead, data.getHead());//没有测试用的small图片 ImageLoaderUtil.getSmallUri(data.getHead()));
		tvUserViewName.setText(StringUtil.getTrimedString(data.getName()));
		tvUserViewNumber.setText(StringUtil.getNoBlankString(data.getPhone()));
	}

}
