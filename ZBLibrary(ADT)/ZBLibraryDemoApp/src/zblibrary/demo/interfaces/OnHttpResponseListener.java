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

package zblibrary.demo.interfaces;

/**网络请求回调接口
 * @author Lemon
 */
public interface OnHttpResponseListener {
	/**
	 * @param requestCode 请求码，自定义，在发起请求的类中可以用requestCode来区分各个请求
	 * @param resultCode 服务器返回状态码
	 * @param resultData 服务器返回的Json串
	 */
	void onHttpSuccess(int requestCode, int resultCode, String resultData);
	/**
	 * @param requestCode 请求码，自定义，在发起请求的类中可以用requestCode来区分各个请求
	 * @param e 请求异常
	 */
	void onHttpError(int requestCode, Exception e);
}