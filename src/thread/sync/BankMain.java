package thread.sync;

import util.MyLogger;
import util.ThreadUtils;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

public class BankMain {

    public static void main(String[] args) throws InterruptedException {
        // BankAccount account = new BankAccountV1(1000);
        // BankAccount account = new BankAccountV2(1000);
        BankAccount account = new BankAccountV3(1000);

        Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
        Thread t2 = new Thread(new WithdrawTask(account, 800), "t2");

        t1.start();
        t2.start(); // 모든 객체(인스턴스)는 내부의 자신만의 락(lock)을 가지고 있다. 모니터 락이라고도 한다.

        sleep(500);
        log("t1 state: " + t1.getState());
        log("t2 state: " + t2.getState());

        t1.join();
        t2.join();

        log("최종 잔액: " + account.getBalance());
    }
}
