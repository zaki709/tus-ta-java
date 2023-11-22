public class DiningPhilosophers {
    boolean[] forks = new boolean[9];
    boolean[] knives = new boolean[9];

    synchronized void getFork(int n) {
        while (forks[n] || knives[n]) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        forks[n] = true;
        System.out.println(n + " get Fork");
    }

    synchronized void getKnife(int n) {
        while (forks[n] || knives[n]) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        knives[n] = true;
        System.out.println(n + " get Knife");
    }

    synchronized void reset(int n) {
        forks[n] = false;
        knives[n] = false;
        notifyAll(); // リソースが解放されたことを通知
    }

    synchronized boolean canEat(int n) {
        return forks[n] && knives[n];
    }

    public static void main(String args[]) {
        DiningPhilosophers dlt = new DiningPhilosophers();
        for (int i = 1; i < 9; i++) {
            new HumanThread(dlt, i).start();
        }
    }
}

class HumanThread extends Thread {
    DiningPhilosophers dlt;
    int id;

    public HumanThread(DiningPhilosophers d, int n) {
        id = n;
        dlt = d;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            while (!dlt.canEat(id)) {
                dlt.getFork(id);
                dlt.getKnife(id);
            }
            System.out.println(id + " ate a meal.");
            dlt.reset(id);
        }
    }
}
