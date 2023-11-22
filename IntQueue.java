public class IntQueue {
    final int SIZE = 10;
    private int[] values = new int[SIZE];
    private int tail = -1;
    private Object lock = new Object();
    private int counter = 0;

    boolean enqueue(int data) {
        synchronized (lock) {
            while (tail == SIZE - 1 || data < 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tail++;
            countup();
            values[tail] = data;
            System.out.println("enqueue :" + data + " tail:" + tail);
            lock.notify();  // 呼び出し側のスレッドを再開させる
            return true;
        }
    }

    int dequeue() {
        synchronized (lock) {
            while (isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int data = values[0];
            for (int i = 0; i < SIZE - 1; i++) {
                values[i] = values[i + 1];
            }
            tail--;
            countup();
            System.out.println("dequeue :" + data + " tail:" + tail);
            lock.notify();  // 呼び出し側のスレッドを再開させる
            return data;
        }
    }

    boolean isEmpty() {
        return (tail == -1);
    }

    void countup(){
        counter++;
        System.out.println("counter:" + counter);
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

class EnQueueThread extends Thread {
    IntQueue q;

    public EnQueueThread(IntQueue q) {
        this.q = q;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            int data = (int) (Math.random() * 100 + 1);
            q.enqueue(data);
        }
    }
}

class DeQueueThread extends Thread {
    IntQueue q;

    public DeQueueThread(IntQueue q) {
        this.q = q;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            q.dequeue();
        }
    }
}