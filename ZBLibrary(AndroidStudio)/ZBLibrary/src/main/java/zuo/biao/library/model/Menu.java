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

package zuo.biao.library.model;

import android.content.Intent;

/**菜单类
 * @author Lemon
 */
public class Menu {

	private int bolongGroup;
	private String name;
	private int imageRes;
	private int operationType;
	private Intent operationIntent;
	private int intentCode;
	
	public Menu() {
		// TODO Auto-generated constructor stub
	}
	public Menu(String name) {
		this.name = name;
	}
	public Menu(int bolongGroup, String name) {
		this.bolongGroup = bolongGroup;
		this.name = name;
	}
	public Menu(String name, int imageRes) {
		this.name = name;
		this.imageRes = imageRes;
	}
	public Menu(String name, int imageRes, int intentCode) {
		this.name = name;
		this.imageRes = imageRes;
		this.intentCode = intentCode;
	}
	public Menu(int bolongGroup, String name, int operationType, Intent operationIntent) {
		this.bolongGroup = bolongGroup;
		this.name = name;
		this.operationType = operationType;
		this.operationIntent = operationIntent;
	}
	public Menu(int bolongGroup, String name, int operationType, Intent operationIntent, int intentCode) {
		this.bolongGroup = bolongGroup;
		this.name = name;
		this.operationType = operationType;
		this.operationIntent = operationIntent;
		this.intentCode = intentCode;
	}
	public Menu(int bolongGroup, String name, int imageRes, int operationType, Intent operationIntent) {
		this.bolongGroup = bolongGroup;
		this.name = name;
		this.imageRes = imageRes;
		this.operationType = operationType;
		this.operationIntent = operationIntent;
	}
	public Menu(int bolongGroup, String name, int imageRes, int operationType, Intent operationIntent, int intentCode) {
		this.bolongGroup = bolongGroup;
		this.name = name;
		this.imageRes = imageRes;
		this.operationType = operationType;
		this.operationIntent = operationIntent;
		this.intentCode = intentCode;
	}

	
	public int getIntentCode() {
		return intentCode;
	}

	public void setIntentCode(int intentCode) {
		this.intentCode = intentCode;
	}

	public int getImageRes() {
		return imageRes;
	}

	public void setImageRes(int imageRes) {
		this.imageRes = imageRes;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBolongGroup() {
		return bolongGroup;
	}
	public void setBolongGroup(int bolongGroup) {
		this.bolongGroup = bolongGroup;
	}
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	public Intent getOperationIntent() {
		return operationIntent;
	}
	public void setOperationIntent(Intent operationIntent) {
		this.operationIntent = operationIntent;
	}
	
}
