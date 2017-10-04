package zblibrary.demo.DEMO;

import zblibrary.demo.R;
import zblibrary.demo.DEMO.DemoRecyclerAdapter.DemoRecyclerView;
import zuo.biao.library.base.BaseRecyclerAdapter;
import zuo.biao.library.base.BaseRecyclerView;
import zuo.biao.library.model.Entry;
import zuo.biao.library.util.StringUtil;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DemoRecyclerAdapter extends BaseRecyclerAdapter<Entry<String, String>, DemoRecyclerView> {

	public DemoRecyclerAdapter(Activity context) {
		super(context);
	}

	@Override
	public DemoRecyclerView createView(int position, ViewGroup parent) {
		return new DemoRecyclerView(context);
	}



	public class DemoRecyclerView extends BaseRecyclerView<Entry<String, String>> {

		public DemoRecyclerView(Activity context) {
			super(context, R.layout.demo_view);
		}

		//示例代码<<<<<<<<<<<<<<<<
		public ImageView ivDemoViewHead;
		public TextView tvDemoViewName;
		public TextView tvDemoViewNumber;
		//示例代码>>>>>>>>>>>>>>>>
		@Override
		public View createView(LayoutInflater inflater) {

			//示例代码<<<<<<<<<<<<<<<<
			ivDemoViewHead = findView(R.id.ivDemoViewHead);
			tvDemoViewName = findView(R.id.tvDemoViewName);
			tvDemoViewNumber = findView(R.id.tvDemoViewNumber);
			//示例代码>>>>>>>>>>>>>>>>

			return itemView;
		}


		@Override
		public void bindView(Entry<String, String> data){
			//示例代码<<<<<<<<<<<<<<<<
			this.data = data;//这里data传进来的只有adapter内item数据，可不判空

//			ImageLoaderUtil.loadImage(ivDemoViewHead, data.getKey());
			
			Glide.with(context).asBitmap().load(data.getKey()).into(ivDemoViewHead);
			
			tvDemoViewName.setText(StringUtil.getTrimedString(data.getValue()));
			//示例代码>>>>>>>>>>>>>>>>
		}

	}


}
