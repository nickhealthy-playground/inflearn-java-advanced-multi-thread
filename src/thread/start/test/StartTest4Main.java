package thread.start.test;

import static util.MyLogger.log;

public class StartTest4Main {

    public static void main(String[] args) {
        MyTask A = new MyTask("A", 1000);
        MyTask B = new MyTask("B", 500);

        Thread threadA = new Thread(A, "Thread-A");
        Thread threadB = new Thread(B, "Thread-B");

        threadA.start();
        threadB.start();

    }

    static class MyTask implements Runnable {

        private final String name;
        private final int millis;

        public MyTask(String name, int millis) {
            this.name = name;
            this.millis = millis;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    log(this.name);
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
