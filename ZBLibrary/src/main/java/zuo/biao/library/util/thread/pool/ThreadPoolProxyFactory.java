package zuo.biao.library.util.thread.pool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 2019.4.20
 * 线程池代理，根据不同需求返回不同类型线程池
 * 可自行根据需求扩展
 * @author bladeofgod
 */

public class ThreadPoolProxyFactory {

    private static volatile DefaultThreadPool defaultThreadPool;
    private static volatile CacheThreadPool cacheThreadPool;
    private static volatile SingleThreadPool singleThreadPool;

    public static SingleThreadPool getSingleThreadPool(){
        if (singleThreadPool == null){
            synchronized (ThreadPoolExecutor.class){
                if (singleThreadPool == null){
                    singleThreadPool = new SingleThreadPool();
                }
            }
        }
        return singleThreadPool;
    }

    public static DefaultThreadPool getDefaultThreadPool(){
        if (defaultThreadPool == null){
            synchronized (ThreadPoolProxyFactory.class){
                if (defaultThreadPool == null){
                    defaultThreadPool = new DefaultThreadPool(3, 5);
                }
            }
        }
        return defaultThreadPool;
    }

    public static CacheThreadPool getCacheThreadPool(){
        if (cacheThreadPool == null){
            synchronized (ThreadPoolProxyFactory.class){
                if (cacheThreadPool == null){
                    cacheThreadPool = new CacheThreadPool();
                }
            }
        }
        return cacheThreadPool;
    }

}
