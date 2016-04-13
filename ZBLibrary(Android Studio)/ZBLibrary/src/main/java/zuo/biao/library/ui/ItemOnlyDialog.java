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

package zuo.biao.library.ui;

import zuo.biao.library.R;
import zuo.biao.library.util.StringUtil;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**通用纵向Item选项dialog
 * @author lemon
 * @use new ItemOnlyDialog(...).show();
 */
public class ItemOnlyDialog extends Dialog {
	private static final String TAG = "ItemOnlyDialog";  

	/** 
	 * 自定义Dialog监听器 
	 */  
	public interface PriorityListener {  
		/** 
		 * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示 
		 */  
		public void refreshPriorityUI(int position);  
	}

	private Context context;
	private String[] items;
	private String title; 
	private int imgResId;
	private int backgroundResId;
	private PriorityListener listener;
	/** 
	 * 带监听器参数的构造函数 
	 */  
	public ItemOnlyDialog(Context context, String[] items,
			PriorityListener listener) {
		super(context, R.style.MyDialog);
		
		this.context = context;
		this.items = items;
		this.listener = listener;  
	}
	public ItemOnlyDialog(Context context, String[] items, String title, 
			PriorityListener listener) {
		super(context, R.style.MyDialog);
		
		this.context = context;
		this.items = items;
		this.title = title;
		this.listener = listener;  
	}
	public ItemOnlyDialog(Context context, String[] items, String title, 
			int imgResId, int backgroundResId, PriorityListener listener) {
		super(context, R.style.MyDialog);
		
		this.context = context;
		this.items = items;
		this.title = title;
		this.imgResId = imgResId;
		this.backgroundResId = backgroundResId;
		this.listener = listener;  
	}

	private LinearLayout llTitleBar;
	private ImageView img;
	private TextView tvTitle;
	private ListView lv;
	private ArrayAdapter<String> adapter;

	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.item_only_dialog); 
		setCanceledOnTouchOutside(true);

		llTitleBar = (LinearLayout) findViewById(R.id.llItemOnlyDialogTitleBar);
		tvTitle = (TextView) findViewById(R.id.tvItemOnlyDialogTitle);
		img = (ImageView) findViewById(R.id.ivItemOnlyDialogImg);
		
		adapter = new ArrayAdapter<String>(context, R.layout.item_only_dialog_item, items);
		lv = (ListView) findViewById(R.id.lvItemOnlyDialog);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int positon, long arg3) {
				dismiss();  
				listener.refreshPriorityUI(positon);
			}
		});

		if (StringUtil.isNotEmpty(title, true)) {
			
			llTitleBar.setVisibility(View.VISIBLE);
			tvTitle.setText(StringUtil.getCurrentString());
			if (imgResId > 0) {
				try {
					img.setImageResource(imgResId);
				} catch (Exception e) {
					Log.i(TAG, "img.setImageResource(imgResId); >>> catch (Exception e) " + e.getMessage());
				}
			}
			if (backgroundResId > 0) {
				try {
					llTitleBar.setBackgroundResource(backgroundResId);
				} catch (Exception e) {
					Log.i(TAG, "llTitleBar.setBackgroundResource(backgroundResId); >>> catch (Exception e) " + e.getMessage());
				}
			}
		} else {
			llTitleBar.setVisibility(View.GONE);
		}
	}
	
}

