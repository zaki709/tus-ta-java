public class ATM{
    static int SIZE = 100;
    int account[];

    public ATM(){
	 account = new int[SIZE];
	 for(int i=0;i<SIZE;i++){
	     account[i] = 100000;
	 }
    }

    void printMoney(){
        int sum = 0;    
        for(int i = 0;i < SIZE;i++){
            System.out.println(account[i]);
            sum += this.account[i];
        };
        System.out.println(sum);
        System.out.println(sum == 100000 * SIZE);
    }

    void move(int from, int to, int money){
        if (from < 99 && to < 99){
            synchronized(this){
                int from_money = this.account[from];
                if(from_money > money){
                    this.account[from] -= money;
                    this.account[to] += money;
                };
            };
        };
    }

    public static void main(String args[]){
	 ATM atm = new ATM();
	 for(int i=0;i<30;i++){
	     new Customer(atm).start();
	 }
    }
}

class Customer extends Thread{
    ATM atm;
    public Customer(ATM atm){
	 this.atm = atm;
    }

    public void run(){
	 for(int i=0;i<1000;i++){
	     atm.move((int)(Math.random()*atm.SIZE),(int)(Math.random()*atm.SIZE),(int)(Math.random()*10000));
	     try{
		 sleep((int)(Math.random()*10));
	     }catch(InterruptedException e){
		 System.err.println(""+e);
	     }
	 }
        atm.printMoney();
    }
}