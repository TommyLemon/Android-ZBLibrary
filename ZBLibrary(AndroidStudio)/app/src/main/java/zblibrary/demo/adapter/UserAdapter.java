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

import zblibrary.demo.base.BaseHttpAdapter;
import zblibrary.demo.model.User;
import zblibrary.demo.view.UserView;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

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
		UserView userView = convertView == null ? null : (UserView) convertView.getTag();
		if (convertView == null) {
			userView = new UserView(context, inflater);
			convertView = userView.getView();

			convertView.setTag(userView);
		}

		userView.setView(getItem(position));

		return super.getView(position, convertView, parent);
	}

}