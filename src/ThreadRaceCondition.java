public class ThreadRaceCondition {
    static int count;
    private static  void doWork(){
        for(int i = 0 ; i < 1000000; ++i){
            count++;
        }
    }
    public static void main(String[] args)throws InterruptedException{
        Thread t1 = new Thread(ThreadRaceCondition::doWork);
        Thread t2 = new Thread(ThreadRaceCondition::doWork);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(count);
    }
}
