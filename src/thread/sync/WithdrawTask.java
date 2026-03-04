package thread.sync;

// 출금을 담당하는 Runnable 구현체 | 생성 시 출금할 계좌(account)와 출금할 금액(amount)을 저장해둠
public class WithdrawTask implements Runnable {

    private BankAccount account;
    private int amount;

    public WithdrawTask(BankAccount account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}
