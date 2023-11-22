public class IntQueue_pra3 {

    final int SIZE = 10; //final:後から変更されない

    private int[] values = new int[SIZE];

    private int tail = -1;

 

    private int count = 0;

    synchronized boolean enqueue(int data) { // bloolean:trueかfalseを返す型

        if(data<0){

             return false;

        }

       

         try{

             if(tail==SIZE-1) {

                 System.out.println("QueuegのSIZEが最大であるため待機する");

                 wait();

                 return false;

             }

        }catch(InterruptedException e){

             e.printStackTrace();

        }

       

         tail++;

        counter();

        values[tail] = data;

        System.out.println("enqueue :"+data+" tail:"+tail);

        notify();

        return true;

    }

   

    synchronized int dequeue() {

 

        try{

            if(isEmpty()){  //isEmpty:文字列の長さが0か判定する.0ならtrue

                 System.out.println("QueuegのSIZEが最小であるため待機する");

                 wait();

                 return -1;

            }

        }catch(InterruptedException e){

            e.printStackTrace();

        }

 

        int data = values[0];

        for(int i=0;i<SIZE-1;i++){

            values[i] = values[i+1];

        }

        tail--;

        counter();

        System.out.println("dequeue :"+data+" tail:"+tail);

        notify();

        return data;

    }

 

   

    boolean isEmpty() {

        return (tail == -1);

    }

 

    void counter(){

        count++;

        System.out.println("count is" + count);

    }

   

 

    public static void main(String[] args) {

        IntQueue q = new IntQueue();

        EnQueueThread eq1 = new EnQueueThread(q);

        EnQueueThread eq2 = new EnQueueThread(q);

        DeQueueThread dq1 = new DeQueueThread(q);

        DeQueueThread dq2 = new DeQueueThread(q);

        eq1.start(); //非同期で複数のtaskを実行する

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

        System.out.println("enpueue is end");

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

               System.out.println("depueue is end");

    }

}