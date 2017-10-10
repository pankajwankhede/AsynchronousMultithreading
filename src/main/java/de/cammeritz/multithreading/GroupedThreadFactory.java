package de.cammeritz.multithreading;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Fabian / Cammeritz on 10.10.2017 at 20:48.
 */

public class GroupedThreadFactory implements ThreadFactory {

    private final ThreadGroup group;

    public GroupedThreadFactory(String name) {
        this.group = new TaskGroup(name);
    }

    public ThreadGroup getGroup() {
        return this.group;
    }

    public Thread newThread(Runnable r) {
        return new Thread(this.group, r);
    }

    public static class TaskGroup extends ThreadGroup {

        public TaskGroup(String name) {
            super(name);
        }

    }

}
