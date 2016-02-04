package pri.samarium.java.thread;

import pri.samarium.common.Config;

public class ThreadUtil {

    public static void currentThreadSleepUntilSomeoneNotify(Object o) {
        try {
            o.wait();
        } catch (InterruptedException e) {
            if (Config.isPrintStackTraceEnalbed()) {
                e.printStackTrace();
            }
        }
    }

    public static void notify(Object o) {
        o.notify();
    }

    public static void currentThreadSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            if (Config.isPrintStackTraceEnalbed()) {
                e.printStackTrace();
            }
        }
    }
}
