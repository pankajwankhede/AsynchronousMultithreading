package de.cammeritz.multithreading;

import com.google.common.base.Preconditions;
import gnu.trove.TCollections;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Fabian / Cammeritz on 10.10.2017 at 20:47.
 */

public class Schedeuler implements ISchedeuler {

    private static ExecutorService executorService;

    private final Object lock = new Object();
    private final AtomicInteger counter = new AtomicInteger();
    private final TIntObjectMap<Task> runningTasks = TCollections.synchronizedMap(new TIntObjectHashMap<Task>());

    private final ISchedeuler.Unsafe unsafe = new ISchedeuler.Unsafe() {
        public ExecutorService getExecutorService() {
            return ExecutorServiceProvider.getExecutorService();
        }
    };

    public void cancel(int id) {
        Task ct = (Task) this.runningTasks.get(id);
        Preconditions.checkArgument(ct != null, "No task with id %s running", new Object[] { Integer.valueOf(id) });
        ct.cancel();
    }

    void cancel(Task task) {
        synchronized (this.lock) {
            this.runningTasks.remove(task.getId());
        }
    }

    public void cancel(ITask task) {
        task.cancel();
    }

    public ITask runRepeating(Runnable task, long delay, long repeat, TimeUnit timeUnit) {
        Preconditions.checkNotNull(task, "task");
        Task prep = new Task(this, this.counter.getAndIncrement(), task, delay, repeat, timeUnit);
        synchronized (this.lock) {
            this.runningTasks.put(prep.getId(), prep);
        }
        executorService.execute(prep);
        return prep;
    }

    public ITask run(Runnable task, long delay, TimeUnit timeUnit) {
        return runRepeating(task, delay, 0L, timeUnit);
    }

    public ITask runAsync(Runnable task) {
        return run(task, 0L, TimeUnit.MILLISECONDS);
    }

    public ISchedeuler.Unsafe unsafe() {
        return this.unsafe;
    }

}
