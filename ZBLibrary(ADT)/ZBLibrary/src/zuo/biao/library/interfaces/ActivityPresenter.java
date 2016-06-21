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

package zuo.biao.library.interfaces;

import zuo.biao.library.base.BaseActivity;
import android.support.annotation.NonNull;

/**Fragment的逻辑接口
 * @author Lemon
 * @use implements ActivityPresenter
 */
public interface ActivityPresenter extends Presenter {

	/**获取Activity
	 * @return BaseActivity而不是Activity，因为非BaseActivity的子类不需要这个方法
	 * @must 在非抽象Activity中 return this;
	 */
	@NonNull
	public BaseActivity getActivity();//无public导致有时自动生成的getActivity方法会缺少public且对此报错

}