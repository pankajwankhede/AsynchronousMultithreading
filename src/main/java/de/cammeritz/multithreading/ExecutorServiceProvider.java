package de.cammeritz.multithreading;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Fabian / Cammeritz on 10.10.2017 at 21:03.
 */

public class ExecutorServiceProvider {

    private static ExecutorService executorService = null;

    protected static ExecutorService getExecutorService() {
        if(executorService == null) {
            String name = "multiThreading";
            executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat(name + " Pool Thread #%1$d").setThreadFactory(new GroupedThreadFactory(name)).build());
        }
        return executorService;
    }

}
