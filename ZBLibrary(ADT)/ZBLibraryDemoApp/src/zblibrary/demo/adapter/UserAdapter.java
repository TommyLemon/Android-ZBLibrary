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

package zblibrary.demo.adapter;

import java.util.List;

import zblibrary.demo.R;
import zblibrary.demo.base.BaseHttpAdapter;
import zblibrary.demo.model.User;
import zuo.biao.library.util.ImageLoaderUtil;
import zuo.biao.library.util.StringUtil;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**用户adapter
 * @author Lemon
 */
public class UserAdapter extends BaseHttpAdapter<User> {
//	private static final String TAG = "UserAdapter";

	public UserAdapter(Activity context, List<User> list) {
		super(context, list);
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = convertView == null ? null : (ViewHolder) convertView.getTag();
		if (holder == null) {
			convertView = inflater.inflate(R.layout.user_item, parent, false);
			holder = new ViewHolder();

			holder.ivUserItemHead = (ImageView) convertView.findViewById(R.id.ivUserItemHead);
			holder.tvUserItemName = (TextView) convertView.findViewById(R.id.tvUserItemName);
			holder.tvUserItemNumber = (TextView) convertView.findViewById(R.id.tvUserItemNumber);

			convertView.setTag(holder);
		}

		final User data = getItem(position);

		ImageLoaderUtil.loadImage(holder.ivUserItemHead, data.getHead());//没有测试用的small图片 ImageLoaderUtil.getSmallUri(data.getHead()));
		holder.tvUserItemName.setText(StringUtil.getTrimedString(data.getName()));
		holder.tvUserItemNumber.setText(StringUtil.getNoBlankString(data.getPhone()));

		return super.getView(position, convertView, parent);
	}
	
	class ViewHolder {
		public ImageView ivUserItemHead;
		public TextView tvUserItemName;
		public TextView tvUserItemNumber;
	}

}