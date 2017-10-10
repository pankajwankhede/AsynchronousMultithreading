package de.cammeritz.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fabian / Cammeritz on 10.10.2017 at 20:48.
 */

public abstract interface ISchedeuler {

    public abstract void cancel(int id);

    public abstract void cancel(ITask task);

    public abstract ITask runAsync(Runnable task);

    public abstract ITask run(Runnable task, long delay, TimeUnit timeUnit);

    public abstract ITask runRepeating(Runnable task, long delay, long repeat, TimeUnit timeUnit);

    public abstract Unsafe unsafe();

    public static abstract interface Unsafe {
        public abstract ExecutorService getExecutorService();
    }

}

