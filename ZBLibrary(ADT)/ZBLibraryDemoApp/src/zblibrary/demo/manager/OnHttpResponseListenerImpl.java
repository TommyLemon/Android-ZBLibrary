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

package zblibrary.demo.manager;

import org.json.JSONObject;

import zblibrary.demo.activity_fragment.UserActivity;
import zblibrary.demo.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**Http请求结果解析类
 * *适合类似以下固定的json格式
 * <br>   {
   <br>     "code": 100,
   <br>     "data": {//可以为任何实体类json，通过Json.parseObject(json, Class<T>)解析；
                      或者是其它类型的JSONObject，解析方式如 {@link #onHttpResponse} 内所示
   <br>         ...
   <br>      }
   <br>   }
 * @author Lemon
 * @see UserActivity#initData()
 * @use 把请求中的listener替换成new OnHttpResponseListenerImpl(listener)
 */
public class OnHttpResponseListenerImpl implements OnHttpResponseListener
, zuo.biao.library.interfaces.OnHttpResponseListener {
	private static final String TAG = "OnHttpResponseListenerImpl";

	OnHttpResponseListener listener;
	public OnHttpResponseListenerImpl(OnHttpResponseListener listener) {
		this.listener = listener;
	}


	
	/**zuo.biao.library.manager.HttpManager.OnHttpResponseListener的回调方法，这里转用listener处理
	 */
	@Override
	public void onHttpResponse(int requestCode, String resultJson, Exception e) {
		Log.i(TAG, "onHttpResponse  requestCode = " + requestCode + "; resultJson = " + resultJson
				+ "; \n\ne = " + (e == null ? null : e.getMessage()));

		int resultCode = 0;
		String resultData = null;
		Exception exception = null;
		try {
			JSONObject jsonObject = new JSONObject(resultJson);
			resultCode = jsonObject.getInt("code");//TODO code改为接口文档给的key
			resultData = jsonObject.getString("data");//TODO data改为接口文档给的key
		} catch (Exception e1) {
			Log.e(TAG, "onHttpResponse  try { sonObject = new JSONObject(resultJson);... >>" +
					" } catch (JSONException e1) {\n" + e1.getMessage());
			exception = e1;
		}
		Log.i(TAG, "onHttpResponse  resultCode = " + resultCode + "; resultData = " + resultData);
		
		if (listener == null) {
			listener = this;
		}		
		if ((e == null && exception == null) || resultCode > 0 || StringUtil.isNotEmpty(resultData, true)) {
			listener.onHttpSuccess(requestCode, resultCode, resultData);
		} else {
			listener.onHttpError(requestCode, new Exception(TAG + ": e = " + e + "; \n exception = " + exception));
		}
	}

	@Override
	public void onHttpSuccess(int requestCode, int resultCode, String resultData) {
		Log.i(TAG, "onHttpSuccess  requestCode = " + requestCode + "; resultCode = " + resultCode
				+ "; resultData = \n" + resultData);
	}
	@Override
	public void onHttpError(int requestCode, Exception e) {
		Log.i(TAG, "onHttpSuccess  requestCode = " + requestCode + "; e = " + e);
	}

}
