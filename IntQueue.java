public class IntQueue {
    final int SIZE = 10;
    private int[] values = new int[SIZE];
    private int tail = -1;
    volatile private int counter = 0;
    
    synchronized boolean enqueue(int data) {
     System.out.println("enqueue is called");
	 if(data<0){
	     return false;
	 }
     try{
        if(tail==SIZE-1) {
            wait();
            return false;
	    }
     }catch(InterruptedException e){
        System.err.println("enqueue said error");
     }
	 
     
     tail++;
     values[tail] = data;
     System.out.println("enqueue :"+data+" tail:"+tail);
     countUp();
     System.out.println(counter);
     notify();
     return true;
	 
    }
    
    synchronized int dequeue() {
     System.out.println("dequeue is called");
	 try{
        if(isEmpty()){
            wait();
            return -1;
        }
     }catch(InterruptedException e){
        System.err.println("dequeue said error");
     }
     

	 int data = values[0];
	 for(int i=0;i<SIZE-1;i++){
	     values[i] = values[i+1];
	 }
	 tail--;
	 System.out.println("dequeue :"+data+" tail:"+tail);
     countUp();
     System.out.println(counter);
     notify();
	 return data;
    }
    
    synchronized boolean isEmpty() {
	 return (tail == -1);
    }

    synchronized void countUp(){
        counter ++;
    }

    public static void main(String[] args) {
        IntQueue q = new IntQueue();
	 EnQueueThread eq1 = new EnQueueThread(q);
	 EnQueueThread eq2 = new EnQueueThread(q);
	 DeQueueThread dq1 = new DeQueueThread(q);
	 DeQueueThread dq2 = new DeQueueThread(q);
	 eq1.start();
	 eq2.start();
	 dq1.start();
	 dq2.start();
    }
}

class EnQueueThread extends Thread{
    IntQueue q;
    public EnQueueThread(IntQueue q){
	 this.q = q;
    }
     
    public void run(){
	 for(int i=0;i<10;i++){
	     int data = (int)(Math.random()*100+1);
	     q.enqueue(data);
	 }
     System.out.println("Enqueue process is end");
    }
}

class DeQueueThread extends Thread{
    IntQueue q;
    public DeQueueThread(IntQueue q){
	 this.q = q;
    }

    public void run(){
	 for(int i=0;i<10;i++){
	     q.dequeue();
	 }
     System.out.println("Dequeue process is end");
    }
}