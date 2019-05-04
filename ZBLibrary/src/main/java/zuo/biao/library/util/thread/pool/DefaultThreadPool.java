package zuo.biao.library.util.thread.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 2019.4.20
 * 指定数量的核心线程和工作线程 3秒存活时间
 * 可用于一般异步任务处理
 * @author bladeofgod
 */

public class DefaultThreadPool {

    private volatile ThreadPoolExecutor mExecutor;
    private int mCoreSize;
    private int mMaximumSize;

    public DefaultThreadPool(int mCoreSize,int mMaximumSize){
        this.mCoreSize = mCoreSize;
        this.mMaximumSize = mMaximumSize;
    }

    private void initThreadPoolExecutor(){
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()){
            synchronized (DefaultThreadPool.class){
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()){
                    long keepAliveTime = 3000;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue blockingQueue = new LinkedBlockingDeque();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    mExecutor = new ThreadPoolExecutor(mCoreSize,
                            mMaximumSize, keepAliveTime,
                            unit, blockingQueue,threadFactory,handler);
                }
            }
        }
    }

    public void execute(Runnable task){
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    public Future submit(Runnable task){
        initThreadPoolExecutor();
        return mExecutor.submit(task);
    }

    public void remove(Runnable task){
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }

}
