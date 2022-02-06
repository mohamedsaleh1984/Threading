public class ResolveRaceCondition_Synchronized_Keyword {
    static int count;
    private static  synchronized  void doWork(){
        for(int i = 0 ; i < 1000000; ++i){
            count++;
        }
    }
    public static void main(String[] args)throws InterruptedException{
        Thread t1 = new Thread(ResolveRaceCondition_Synchronized_Keyword::doWork);
        Thread t2 = new Thread(ResolveRaceCondition_Synchronized_Keyword::doWork);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(count);
    }
}
