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

package zblibrary.demo.model;

import zuo.biao.library.base.BaseModel;

/**用户类
 * @author Lemon
 */
public class User extends BaseModel {

	private static final long serialVersionUID = 1L;

	public static final int SEX_MAIL = 0;
	public static final int SEX_FEMALE = 1;
	public static final int SEX_UNKNOWN = 2;

	private int sex; //性别
	private String head; //头像
	private String name; //名字
	private String phone; //电话号码
	private String tag; //标签
	private boolean starred; //星标

	/**默认构造方法，JSON等解析时必须要有
	 */
	public User() {
		//default
	}
	public User(long id) {
		this();
		this.id = id;
	}
	public User(long id, String name) {
		this(id);
		this.name = name;
	}
	

	/**
	 * 以下getter和setter可以自动生成
	 * <br>  eclipse: 右键菜单 > Source > Generate Getters and Setters
	 * <br>  android studio: 右键菜单 > Generate > Getter and Setter
	 */
	
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public boolean getStarred() {
		return starred;
	}
	public void setStarred(boolean starred) {
		this.starred = starred;
	}

	@Override
	protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
		return id > 0;// && StringUtil.isNotEmpty(phone, true);
	}

}
