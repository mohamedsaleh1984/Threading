import java.util.concurrent.locks.ReentrantLock;

public class ResolveRaceCondition_Lock {
    static ReentrantLock reentrantLock = new ReentrantLock();
    static int count = 0;

    private static  void doWork(){
        for(int i = 0 ; i < 1000000; ++i){
            reentrantLock.lock();
            count++;
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args)throws InterruptedException{
        Thread t1  = new Thread(ResolveRaceCondition_Lock::doWork);
        Thread t2  = new Thread(ResolveRaceCondition_Lock::doWork);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.printf("count :%d\n", count);
    }
}
