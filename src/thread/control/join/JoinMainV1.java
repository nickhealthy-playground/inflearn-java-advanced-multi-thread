package thread.control.join;

import util.MyLogger;
import util.ThreadUtils;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

public class JoinMainV1 {

    public static void main(String[] args) {
        log("start");
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        new Thread(task1).start();
        new Thread(task2).start();

        log("task1.result = " + task1.result);
        log("task2.result = " + task2.result);

        int sumAll = task1.result + task2.result;
        log("task1 + task2 = " + sumAll);
        log("end");
    }

    static class SumTask  implements Runnable {
        int start;
        int end;
        int result = 0;

        public SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            log("작업 시작");
            sleep(2000);
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }

            result = sum;
            log("작업 완료 result = " + result);

        }
    }
}
