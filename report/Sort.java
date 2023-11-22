public class Sort{
    private int array[];
    public Sort(int n){
        array = new int[n];
        for(int i=0;i<n;i++){
            array[i] = (int)(Math.random()*Integer.MAX_VALUE);
        }
        
        long start = System.currentTimeMillis();
        //BubbleSort bs = new BubbleSort(array);
        MergeSort ms = new MergeSort(array);
        //ms.MergeThreads();
        //array = bs.getArray();
        array = ms.getArray();
        printArray(array);
        long end = System.currentTimeMillis();
        System.out.println("sort?: "+sortCheck(array)+
                ", Processing time: "+(end-start)+"ms");
    }

    public static boolean sortCheck(int array[]){
        for(int i=0;i<array.length-1;i++){
            if(array[i]>array[i+1])return false;
        }
	    return true;
    }
    
    public static void printArray(int array[]){
        for(int i=0;i<array.length;i++){
            System.out.print(array[i]+" ");
        }
	    System.out.println();
    }
    
    public static void main(String args[]){
	    new Sort(100000);
    }
}

class BubbleSort{
    private int array[];
    BubbleSort(int[] n){
	    array = n;
	    sort();
    }
    private void sort(){
        for(int i=array.length-1;i>0;i--){
            for(int j=0;j<i;j++){
                if(array[j]>array[j+1]){
                    int tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                }
            }
        }
    }

    public int[] getArray(){
	    return array;
    }
}

class MergeSort{
    private int[] array;
    private int[] am1;
    private int[] am2;
    private int[] ansArray;
    MergeSort(int[] n){
        array = n;
        ansArray = new int[array.length];
        int mid = array.length / 2;
        int[] m1 = new int[mid];
        int[] m2 = new int[array.length - mid];
        for(int i=0;i<mid;i++){
            m1[i] = array[i];
        }
        for(int i = 0;i<ansArray.length - mid;i++){
            m2[i] = array[i + mid];
        }
        Msort ms1 = new Msort(m1);
        Msort ms2 = new Msort(m2);
        try{
            ms1.start();
            ms2.start();
            ms1.join();
            ms2.join();
        }catch(InterruptedException e){}
        am1 = ms1.getarray();
        am2 = ms2.getarray();
        MergeThreads(am1,am2);
    }
    public void MergeThreads(int[] sam1,int[] sam2){
        int i = 0;
        int j = 0;
        while(i<sam1.length||j<sam2.length){
            if(j>=sam2.length||(i<sam1.length&&sam1[i]<sam2[j])){
                ansArray[i+j] = sam1[i];
                i++;
            }else{
                ansArray[i+j] = sam2[j];
                j++;
            }
        }
    }
    public int[] getArray(){
        return ansArray;
    }
}
class Msort extends Thread{
    private int[] tmp;
    private int[] ansarray;
    Msort(int[] array){
        tmp = array;
    }
    public void run(){
        mergeSort(tmp);
    }
    void merge(int[] a1,int[] a2,int[] a){
        int i = 0;
        int j = 0;
        while(i<a1.length||j<a2.length){
            if(j>=a2.length||(i<a1.length && a1[i]<a2[j])){
                a[i+j] = a1[i];
                i++;
            }else{
                a[i+j] = a2[j];
                j++;
            }
        }
        ansarray = a;
    }
    void mergeSort(int[] a){
        int mid = a.length/2;
        int end = a.length - mid;
        int[] a1 = new int[mid];
        int[] a2 = new int[end];
        if(a.length>1){
            for(int i = 0;i<mid;i++){
                a1[i] = a[i];
            }
            for(int i = 0;i<end;i++){
                a2[i] = a[mid + i];
            }
            mergeSort(a1);
            mergeSort(a2);
            merge(a1,a2,a);
        }
    }
    public int[] getarray(){
        return ansarray;
    }
}