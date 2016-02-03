package demo.samarium.java.thread;

import pri.samarium.java.thread.ThreadUtil;

/**
 * abc三个线程依次执行输出1到10，前一个线程输出完一个数，后一个线程再输出一个数
 */
public class WaitAndNotify {
    private Thread a;
    private Thread b;
    private Thread c;

    private Object A = new Object();
    private Object B = new Object();
    private Object C = new Object();

    public WaitAndNotify() {

    }

    public void demo() {
        startThreadAIfNecessary();
    }

    private void startThreadAIfNecessary() {
        if (a == null) {
            a = new MyThread("a", A, B);
            a.start();
        }
    }

    private void startThreadCIfNecessary() {
        if (c == null) {
            c = new MyThread("c", C, A);
            c.start();
        }
    }

    private void startThreadBIfNecessary() {
        if (b == null) {
            b = new MyThread("b", B, C);
            b.start();
        }
    }

    class MyThread extends Thread {
        Object current;
        Object next;

        public MyThread(String name, Object current, Object next) {
            super(name);
            this.current = current;
            this.next = next;
        }

        @Override
        public void run() {
            for (int i = 1; i < 10; i++) {
                String name = Thread.currentThread().getName();
                System.out.println(" -> " + name + "(" + i + ")");
                synchronized (current) {
                    synchronized (next) {
                        ThreadUtil.notify(next);
                        if (B.equals(next)) {
                            startThreadBIfNecessary();
                        } else if (C.equals(next)) {
                            startThreadCIfNecessary();
                        } else if (A.equals(next)) {
                            startThreadAIfNecessary();
                        }
                    }
                    ThreadUtil.currentThreadSleepUntilSomeoneNotify(current);
                }
            }
        }
    }

}
