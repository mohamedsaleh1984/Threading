import java.util.concurrent.locks.ReentrantLock;

public class ResolveDeadLock_DeterministicLock {
    private static class Account {
        private String name = "";
        public int balance = 100;
        public ReentrantLock lock = new ReentrantLock();

        Account(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    private static void transfer(final Account from, final Account to, final int amount) {
        final int cmp = from.getName().compareTo(to.getName());

        Account account1 = cmp < 0 ? from : to;
        Account account2 = cmp < 0 ? to : from;

        try {
            account1.lock.lock();
            System.out.printf("Grabbed %s lock\n", account1.getName());
            System.out.flush();

            account2.lock.lock();
            System.out.printf("Grabbed %s lock\n", account2.getName());
            System.out.flush();

            from.balance -= amount;
            to.balance += amount;

            System.out.printf("Transfer %s->%s completed\n", from.getName(), to.getName());
            System.out.flush();

        } finally {
            account2.lock.unlock();
            account1.lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Account acc1 = new Account("A");
        final Account acc2 = new Account("B");

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; ++i)
                transfer(acc1, acc2, 10);
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; ++i)
                transfer(acc2, acc1, 3);
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.printf("Success Account1: %d , Account2: %d", acc1.balance,acc2.balance);
    }
}
