import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ResolveRaceCondition_Semaphore {
    static int count = 0;
    static Semaphore semaphore = new Semaphore(1);

    private static void doWork() {
        for (int i = 0; i < 1000000; ++i) {
            try {
                semaphore.acquire();
                count++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //In case if the try failed for any reason
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(ResolveRaceCondition_Semaphore::doWork);
        Thread t2 = new Thread(ResolveRaceCondition_Semaphore::doWork);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.printf("count :%d\n", count);
    }
}
