import java.util.concurrent.atomic.AtomicInteger;


public class ResolveRaceCondition_Atomic {
    static AtomicInteger count = new AtomicInteger(0);

    private static  void doWork(){
        for(int i = 0 ; i < 1000000; ++i){
            count.incrementAndGet();
        }
    }

    public static void main(String[] args)throws InterruptedException{
        Thread t1  = new Thread(ResolveRaceCondition_Atomic::doWork);
        Thread t2  = new Thread(ResolveRaceCondition_Atomic::doWork);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.printf("count :%d\n", count.get());
    }
}
