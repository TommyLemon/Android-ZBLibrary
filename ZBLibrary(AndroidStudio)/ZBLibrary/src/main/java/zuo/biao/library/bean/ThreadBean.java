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

import android.os.Handler;
import android.os.HandlerThread;

/**线程类
 * @author Lemon
 */
public class ThreadBean {

	private String name;
	private HandlerThread thread;
	private Runnable runnable;
	private Handler handler;

	public ThreadBean() {
	}	

	public ThreadBean(String name, HandlerThread thread, Runnable runnable, Handler handler) {
		this.name = name;
		this.thread = thread;
		this.runnable = runnable;
		this.handler = handler;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HandlerThread getThread() {
		return thread;
	}

	public void setThread(HandlerThread thread) {
		this.thread = thread;
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	
	

}
