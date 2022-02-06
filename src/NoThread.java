public class NoThread {
    static int count;
    private static  void doWork(){
        for(int i = 0 ; i < 1000000; ++i){
            count++;
        }
    }
    public static void main(String[] args)throws InterruptedException{
        System.out.println("First Call..");
        doWork();
        System.out.println("Second Call..");
        doWork();
        System.out.println(count);
    }
}
