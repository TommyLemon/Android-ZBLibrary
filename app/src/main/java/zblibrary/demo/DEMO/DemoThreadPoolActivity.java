package zblibrary.demo.DEMO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

import zblibrary.demo.R;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.thread.pool.ThreadPoolProxyFactory;

/*
* ThreadPoolProxy DEMO
*  默认代理两种线程池，也可自行扩展
*
*  第一种：DefaultThreadPool; 核心3线程，最大5线程，3000毫秒存活时间
*           可执行一般异步任务需求。
*  第二种：CacheThreadPool: 线程数为Integer.MAX,无核心线程，存活时间：60毫秒
*           适合执行大量、高频、一次性、后台（建议无关UI的）等的异步任务，
*
*  自动释放，也可手动释放，节约资源，方便管理。
*
*  使用起来很方便，仅做简单展示
* */

public class DemoThreadPoolActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "DemoThreadPoolActivity";

    private static final int DEFAULT_POOL = 500;
    private static final int CACHE_POOL = 800;
    private static final int SINGLE_POOL = 1000;

    public static Intent createIntent(Context context){
        return new Intent(context,DemoThreadPoolActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_thread_pool);

        initView();
        initData();
        initEvent();

    }



    TextView tvDefault,tvCache,tvSingle;

    @Override
    public void initView() {

        tvDefault = findView(R.id.tv_default);
        tvCache = findView(R.id.tv_cache);
        tvSingle = findView(R.id.tv_single);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        findView(R.id.btn_default).setOnClickListener(this);
        findView(R.id.btn_cache).setOnClickListener(this);
        findView(R.id.btn_single).setOnClickListener(this);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DEFAULT_POOL:
                    handleMsgFromDefault();
                    break;
                case CACHE_POOL:
                    handleMsgFromCache();
                    break;
                case SINGLE_POOL:
                    handleMsgFromSingle();
                    break;
            }
        }
    };


    //===============================================================
    private void handleMsgFromDefault(){
        tvDefault.setText(
                "DefaultThreadPool; 核心3线程，最大5线程，3000毫秒存活时间可执行一般异步任务需求。\n使用简单，这里仅做简单展示"
        );
    }

    //===============================================================
    String info = "CacheThreadPool: 线程数为Integer.MAX,无核心线程，存活时间：" +
            "60毫秒适合执行大量、高频、一次性、后台（建议无关UI的）等的异步任务，\n" +
            "仅做简单展示，输出可查看LOG\n";
    StringBuffer sb = new StringBuffer();
    private void handleMsgFromCache(){
        sb.append(info);
        tvCache.setText(sb);
    }



    /*
    * 常规线程池：
    * 一般异步任务或与ui有关的建议使用此线程池
    *
    * */
    private void initDefaultPool(){

        ThreadPoolProxyFactory.getDefaultThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = DEFAULT_POOL;
                handler.sendMessageDelayed(msg, 500);
            }
        });

    }

    /*
    * cache线程池：
    * 建议与ui无关的异步任务使用此线程池
    * #与UI有关的，注意处理好数据处理顺序。
    *
    * 这里仅在log输出展示
    * */
    private int count = 1;
    private void initCachePool(){
        for (int j= 0;j < 20;j++){
            ThreadPoolProxyFactory.getCacheThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    Message msg = Message.obtain();
                    msg.what = CACHE_POOL;
                    handler.sendMessageDelayed(msg, 50);
                }
            });
        }
    }

    /*
    * 单一线程池
    * 适合需要顺序执行的异步任务
    *
    *
    * */
    private AtomicInteger taskIndex = new AtomicInteger();
    private void initSinglePool(){
        taskIndex.set(1);
        for (int i=0 ; i< 5 ; i++){
            ThreadPoolProxyFactory.getSingleThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    Message msg = Message.obtain();
                    msg.what = SINGLE_POOL;
                    handler.sendMessageDelayed(msg,1000);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //===============================================================

    StringBuffer singleSB =new StringBuffer();
    private void handleMsgFromSingle(){
        singleSB.append("正在处理任务： " + taskIndex.getAndIncrement() + "\n");
        tvSingle.setText(singleSB.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_default:
                showShortToast("请求中" );
                initDefaultPool();
                break;
            case R.id.btn_cache:
                showShortToast("请求中");
                initCachePool();
                break;
            case R.id.btn_single:
                showShortToast("请求中");
                initSinglePool();
                break;
        }

    }
}
