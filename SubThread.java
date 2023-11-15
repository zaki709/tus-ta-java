public class SubThread extends Thread{

    public SubThread(){
	 this.start();
	 while(true){
	     System.out.println("AAA");
	 }
    }
    public void run(){
	 while(true){
	     System.out.println("BBB");
	 }
    }

    public static void main(String args[]){
	 new SubThread();
    }
}