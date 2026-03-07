package thread.sync.lock;

import util.MyLogger;
import util.ThreadUtils;

import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

public class LockSupportMainV1 {

    public static void main(String[] args) {
        Thread thread = new Thread(new ParkTask(), "Thread-1");
        thread.start();

        // 잠시 대기하여 Thread-1이 park 상태에 빠질 시간을 준다.
        sleep(100);
        log("Thread-1 state: " + thread.getState());
        log("main -> unpark(Thread-1)");
        LockSupport.unpark(thread); // 1. unpark 사용
        //thread.interrupt(); // 2. interrupt 사용
    }

    static class ParkTask implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park();
            log("park 종료, state: " + Thread.currentThread().getState());
            log("인터럽트 상태: " + Thread.currentThread().isInterrupted());
        }
    }
}
