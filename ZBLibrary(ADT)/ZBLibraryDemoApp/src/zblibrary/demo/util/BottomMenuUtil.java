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

package zblibrary.demo.util;

import java.util.ArrayList;
import java.util.List;

import zblibrary.demo.R;
import zuo.biao.library.bean.FunctionServiceBean;

/**底部菜单工具类
 * @author Lemon
 */
public class BottomMenuUtil {

	public static final String NAME_SEND_MESSAGE = "发信息";//信息记录显示在通话记录左边
	public static final String NAME_CALL = "呼叫";//信息记录显示在通话记录左边
	public static final String NAME_SEND = "发送";
	public static final String NAME_QRCODE = "二维码";
	public static final String NAME_ADD_TO = "添加至";
	public static final String NAME_EDIT = "编辑";
	public static final String NAME_EDIT_ALL = "编辑所有";
	public static final String NAME_DELETE = "删除";
	public static final String NAME_SEND_EMAIL = "发邮件";

	public static final int INTENT_CODE_SEND_MESSAGE = 1;//信息记录显示在通话记录左边
	public static final int INTENT_CODE_CALL = 2;//信息记录显示在通话记录左边
	public static final int INTENT_CODE_SEND = 3;
	public static final int INTENT_CODE_QRCODE = 4;
//	public static final int INTENT_CODE_SETTING = 5;
	public static final int INTENT_CODE_ADD_TO = 6;
	public static final int INTENT_CODE_EDIT = 7;
	public static final int INTENT_CODE_EDIT_ALL = 8;
	public static final int INTENT_CODE_DELETE = 9;
	public static final int INTENT_CODE_SEND_EMAIL = 10;

	public static FunctionServiceBean FSB_SEND_MESSAGE = new FunctionServiceBean(NAME_SEND_MESSAGE, R.drawable.mail_light, INTENT_CODE_SEND_MESSAGE);
	public static FunctionServiceBean FSB_CALL = new FunctionServiceBean(NAME_CALL, R.drawable.call_light, INTENT_CODE_CALL);
	public static FunctionServiceBean FSB_SEND = new FunctionServiceBean(NAME_SEND, R.drawable.send_light, INTENT_CODE_SEND);
	public static FunctionServiceBean FSB_QRCODE = new FunctionServiceBean(NAME_QRCODE, R.drawable.qrcode, INTENT_CODE_QRCODE);
//	public static FunctionServiceBean FSB_SETTING = new FunctionServiceBean(NAME_SETTING, R.drawable.setting_light, INTENT_CODE_SETTING);
	public static FunctionServiceBean FSB_ADD_TO = new FunctionServiceBean(NAME_ADD_TO, R.drawable.add_light, INTENT_CODE_ADD_TO);
	public static FunctionServiceBean FSB_EDIT = new FunctionServiceBean(NAME_EDIT, R.drawable.edit_light, INTENT_CODE_EDIT);
	public static FunctionServiceBean FSB_EDIT_ALL = new FunctionServiceBean(NAME_EDIT_ALL, R.drawable.edit_light, INTENT_CODE_EDIT_ALL);
	public static FunctionServiceBean FSB_DELETE = new FunctionServiceBean(NAME_DELETE, R.drawable.delete_light, INTENT_CODE_DELETE);
	public static FunctionServiceBean FSB_SEND_EMAIL = new FunctionServiceBean(NAME_SEND_EMAIL, R.drawable.mail_light, INTENT_CODE_SEND_EMAIL);

	public static final int CONTACT_LIST_FRAGMENT_MULTI = 1;

	public static final int USER = 1;
	public static List<FunctionServiceBean> getMenuList(int which) {
		List<FunctionServiceBean> list = new ArrayList<FunctionServiceBean>();
		switch (which) {
		case USER:
			list.add(FSB_SEND_MESSAGE);
			list.add(FSB_CALL);
			list.add(FSB_SEND_EMAIL);
			list.add(FSB_SEND);
			list.add(FSB_QRCODE);
			break;
		default:
			break;
		}

		return list;
	}

}
