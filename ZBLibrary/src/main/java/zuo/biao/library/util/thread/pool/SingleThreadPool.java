package zuo.biao.library.util.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author lijiaqi
 * @date 2019/6/14.
 *
 * 单一线程线程池，一般用于单一的，线性顺序执行任务。
 *
 * exp： 单一流水线顺序处理产品
 */
public class SingleThreadPool {

    private volatile ExecutorService mExecutor;


    public SingleThreadPool() {

    }

    private void initThreadPoolExecutor(){
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()){
            synchronized (SingleThreadPool.class){
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()){
                    mExecutor = Executors.newSingleThreadExecutor();
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


}








