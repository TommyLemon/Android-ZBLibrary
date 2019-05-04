package zuo.biao.library.util.thread.pool;

import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 2019.4.20
 * 无核心线程，最大数量工作线程，存活时间短。
 * 可用于 一次性、大数量、高频等异步任务处理。
 * @author bladeofgod
 */
public class CacheThreadPool {

    private volatile ThreadPoolExecutor mExecutors;

    public CacheThreadPool(){}

    private void initThreadPoolExecutor(){
        if (mExecutors == null || mExecutors.isShutdown() || mExecutors.isTerminated()){
            synchronized (CacheThreadPool.class){
                if (mExecutors == null || mExecutors.isShutdown()|| mExecutors.isTerminated()){
                    long keepAliveTime = 60L;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    mExecutors = new ThreadPoolExecutor(
                            0
                    , Integer.MAX_VALUE, keepAliveTime, unit, new SynchronousQueue<>());
                }
            }
        }
    }

    public void execute(Runnable task){
        initThreadPoolExecutor();
        mExecutors.execute(task);
    }

    public Future submit(Runnable task){
        initThreadPoolExecutor();
        return mExecutors.submit(task);
    }

    public void remove(Runnable task){
        initThreadPoolExecutor();
        mExecutors.remove(task);
    }


}
