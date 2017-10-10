package de.cammeritz.multithreading;

/**
 * Created by Fabian / Cammeritz on 10.10.2017 at 20:48.
 */

public abstract interface ITask {

    public abstract int getId();

    public abstract Runnable getTask();

    public abstract void cancel();

}
