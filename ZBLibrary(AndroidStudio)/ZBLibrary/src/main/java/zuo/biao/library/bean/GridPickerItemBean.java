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

/**网格选择器JavaBean
 *@author Lemon
 *@date 2015-7-20 上午3:34:55
 */
@SuppressWarnings("serial")
public class GridPickerItemBean extends Entry<Boolean, String> {

	public GridPickerItemBean() {
		this(null);
	}
	public GridPickerItemBean(String value) {
		this(value, true);
	}
	public GridPickerItemBean(String value, boolean enabled) {
		super(enabled, value);
	}
	
	public boolean getEnabled() {
		return getKey();
	}
	public void setEnabled(boolean enabled) {
		setKey(enabled);
	}
	
}
