package de.cammeritz.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Fabian / Cammeritz on 10.10.2017 at 20:48.
 */

public class Task implements Runnable, ITask {

    private final Schedeuler schedeuler;
    private final int id;
    private final Runnable task;
    private final long delay;
    private final long repeat;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public Task(Schedeuler schedeuler, int id, Runnable task, long delay, long repeat, TimeUnit timeUnit) {
        this.schedeuler = schedeuler;
        this.id = id;
        this.task = task;
        this.delay = timeUnit.toMillis(delay);
        this.repeat = timeUnit.toMillis(repeat);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Runnable getTask() {
        return this.task;
    }

    @Override
    public void cancel() {
        boolean wasActive = this.running.getAndSet(false);
        if(wasActive) {
            this.schedeuler.cancel(this);
        }
    }

    @Override
    public void run() {
        if(this.delay > 0L) {
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        while(this.running.get()) {
            try {
                this.task.run();
            } catch (Throwable t) {
                //Log your Error here!
                System.out.println(String.format("The Task %s was interrupted and threw an exception", new Object[]{this}) + ":");
                t.printStackTrace();
            }
            if(this.repeat <= 0L) {
                break;
            }
            try {
                Thread.sleep(this.repeat);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        cancel();
    }

}
