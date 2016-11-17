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

package zblibrary.demo.adapter;

import java.util.ArrayList;

import zuo.biao.library.util.Log;

/**限制长度的ArrayList，当达到maxSize后add会先删除第0个
 * @author Lemon
 * @param <T>
 * @see #add
 * @see #remove
 */
public class LimitedArrayList<T> extends ArrayList<T> implements OnAddListener<T>, OnRemoveListener<T> {
	private static final String TAG = "LimitedArrayList";
	private static final long serialVersionUID = 5605890676585766055L;

	private OnAddListener<T> onAddListener;
	public void setOnAddListener(OnAddListener<T> onAddListener) {
		this.onAddListener = onAddListener;
	}
	private OnRemoveListener<T> onRemoveListener;
	public void setOnRemoveListener(OnRemoveListener<T> onRemoveListener) {
		this.onRemoveListener = onRemoveListener;
	}
	
	
	private int maxSize;
	public LimitedArrayList(int maxSize) {
		this.maxSize = maxSize;
	}
	
	
	@Override
	public void onAdd(T object) {
		if (size() >= maxSize) {
			Log.d(TAG, "add  size() >= maxSize >> remove(0);");
			remove(0);
		}
		if (onAddListener != null) {
			onAddListener.onAdd(object);
		}
	}

	@Override
	public void onRemove(T object) {
		if (onRemoveListener != null) {
			onRemoveListener.onRemove(object);
		}
	}
	
	@Override
	public boolean add(T object) {
		onAdd(object);
		return super.add(object);
	}
	@Override
	public void add(int index, T object) {
		onAdd(object);
		super.add(index, object);
	}
	
	@Override
	public T remove(int index) {
		onRemove(get(index));
		return super.remove(index);
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object object) {
		onRemove((T) object);
		return super.remove(object);
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < size(); i++) {
			remove(i);
		}
		super.clear();
	}


}
