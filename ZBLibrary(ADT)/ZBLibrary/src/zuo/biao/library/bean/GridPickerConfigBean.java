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

package zuo.biao.library.bean;

/**GridPickerView初始化配置model
 *@author Lemon
 *@date 2015-7-23 上午12:54:01
 */
public class GridPickerConfigBean {

	//	private List<GridPickerItemBean> list;//list一般都会变 ,GridPickerView只允许一个list

	private String tabSuffix;
	
	private String selectedItemName;//可变
	private int selectedItemPostion;//可变
	
	private int numColumns;//第一次设置后就固定不变
	private int maxShowRows;//第一次设置后就固定不变

	/**
	 * @param tabSuffix
	 * @param selectedItemName
	 * @param selectedItemPostion
	 */
	public GridPickerConfigBean(String tabSuffix, String selectedItemName, int selectedItemPostion) {
		this.tabSuffix = tabSuffix;
		this.selectedItemName = selectedItemName;
		this.selectedItemPostion = selectedItemPostion;
	}
	/**
	 * @param tabSuffix
	 * @param selectedItemName
	 * @param selectedItemPostion
	 * @param numColumns
	 * @param maxShowRows
	 */
	public GridPickerConfigBean(String tabSuffix, String selectedItemName, int selectedItemPostion, int numColumns, int maxShowRows) {
		this.tabSuffix = tabSuffix;
		this.selectedItemName = selectedItemName;
		this.selectedItemPostion = selectedItemPostion;

		this.numColumns = numColumns;
		this.maxShowRows = maxShowRows;
	}

	/**只允许通过这个方法修改数据
	 * @param selectedItemName
	 * @param selectedItemPostion
	 * @return
	 */
	public GridPickerConfigBean set(String selectedItemName, int selectedItemPostion) {
		return set(tabSuffix, selectedItemName, selectedItemPostion);
	}
	/**只允许通过这个方法修改数据
	 * @param tabSuffix
	 * @param selectedItemName
	 * @param selectedItemPostion
	 * @return
	 */
	public GridPickerConfigBean set(String tabSuffix, String selectedItemName, int selectedItemPostion) {
		this.selectedItemName = selectedItemName;
		this.selectedItemPostion = selectedItemPostion;
		return this;
	}

	/**带后缀
	 * @return
	 */
	public String getTabName() {
		return getSelectedItemName() + getTabSuffix();
	}
	
	public String getTabSuffix() {
		return tabSuffix == null ? "" : tabSuffix;
	}
	
	public String getSelectedItemName() {
		return selectedItemName == null ? "" : selectedItemName;
	}
	public int getSelectedItemPostion() {
		return selectedItemPostion;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public int getMaxShowRows() {
		return maxShowRows;
	}




}
