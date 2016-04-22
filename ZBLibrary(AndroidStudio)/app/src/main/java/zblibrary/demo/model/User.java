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

import java.io.Serializable;

/**用户类
 * @author Lemon
 */
public class User implements Serializable {

	private static final long serialVersionUID = 9022684467723310334L;
	
	
	private long id;
	private String head; //头像
	private String name; //名字
	private String phone; //电话号码
	

	public User() {
		//default
	}
	public User(long id) {
		this(id, null);
	}
	public User(String name) {
		this(-1, name);
	}
	public User(long id, String name) {
		this(id, name, null);
	}
	public User(long id, String name, String phone) {
		this(id, name, phone, null);
	}
	public User(long id, String name, String phone, String head) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.head = head;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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

}
