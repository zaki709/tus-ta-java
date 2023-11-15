public class Runnable_Thread implements Runnable{

    public Runnable_Thread(){
    }

    public void run(){
	 while(true){
	     System.out.println("BBB");
	 }
    }

    public static void main(String args[]){
	 Runnable_Thread r = new Runnable_Thread();
	 Thread thread = new Thread(r);
	 thread.start();
	 while(true){
	     System.out.println("AAA");
	 }
    }
}