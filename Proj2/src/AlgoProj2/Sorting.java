package AlgoProj2;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Arrays;

public class Sorting {
    
    private static int[] arr;
    private static int[] arrCopy;
    private static int[] arrCopy2;
    private static BufferedReader read;
    private static Random randomGenerator;
    
    private static int size;
    private static int random;

    private static void printArray(String msg) {
    	System.out.print(msg + " [" + arr[0]);
        for(int i=1; i<size; i++) {
            System.out.print(", " + arr[i]);
        }
        System.out.println("]");
    }

    public static void insertSort(int left, int right) {
	  // insertSort the subarray arr[left, right]
	  int i, j;

	  for(i=left+1; i<=right; i++) {
	    int temp = arr[i];           // store a[i] in temp
	    j = i;                       // start shifts at i
	    // until one is smaller,
	    while(j>left && arr[j-1] >= temp) {
		  arr[j] = arr[j-1];        // shift item to right
		  --j;                      // go left one position
	    }
	    arr[j] = temp;              // insert stored item
	  }  // end for
    }  // end insertSort()
    
    public static void insertionSort() {
    	insertSort(0, size-1);  
    } // end insertionSort()

    
    public static void maxheapify(int i, int n) { 
	   // Pre: the left and right subtrees of node i are max heaps.
	   // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left=2*i+1;
        int right=2*i+2;
        
        if(left < n && arr[left] > arr[max]) max = left;
        if(right < n && arr[right] > arr[max]) max = right;
        
        if (max != i) {  // node i is not maximal
            exchange(i, max);
            maxheapify(max, n);
        }
    }
    
    public static void exchange(int i, int j){
        int t=arr[i];
        arr[i]=arr[j];
        arr[j]=t; 
    }
    
    public static void heapsort(){
    	// Build an in-place bottom up max heap
        for (int i=size/2; i>=0; i--) maxheapify(i, size);
         
        for(int i=size-1;i>0;i--) {
            exchange(0, i);       // move max from heap to position i.
            maxheapify(0, i);     // adjust heap
        }
    }
       
    private static void bottomUpMergesort(int low, int high){
        if (low < high) {
            int t = 1;
            while(t < high){
                int s = t;
                t = 2*s;
                int i = 0;
                while(i + t <=high){
                    merge(i+1, i+s, i+t);
                    i = i + t;
                }
                if (i+s < high)
                    merge(i+1,i+s,high);
            } 
        }
    }
    
    private static void mergesort(int low, int high) {
        // Check if low is smaller than high, if not then the array is sorted
        if (low < high) {
          // Get the index of the element which is in the middle
          int middle = low + (high - low) / 2;         
          if(isSorted(low,high)){
          }else{
          // Sort the left side of the array
            if(high - low < 100){
                insertSort(low,high);
            }else{
            mergesort(low, middle);
            }
          // Sort the right side of the array
            if(high - low < 100){
                insertSort(low,high);
            }else{
            mergesort(middle + 1, high);
            }
          // Combine them both
            merge(low, middle, high);
          }
        }
    }

    private static void merge(int low, int middle, int high) {

        // Copy first part into the arrCopy array
        for (int i = low; i <= middle; i++) {
          arrCopy2[i] = arr[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) {
          if (arrCopy2[i] <= arr[j]) {
            arr[k] = arrCopy2[i];
            i++;
          } else {
            arr[k] = arr[j];
            j++;
          }
          k++;
        }
	
        // Copy the rest of the left part of the array into the target array
        while (i <= middle) {
          arr[k] = arrCopy2[i];
          k++;
          i++;
        }

    }
      
    private static void quicksort(int low, int high) {
        int i = low, j = high;
            
	    // Get the pivot element from the middle of the list
	    int mid = high+low/2;
            int pivot = arr[(high+mid+low)/3];

	    // Divide into two lists
	    while (i <= j) {
    	      // If the current value from the left list is smaller then the pivot
    	      // element then get the next element from the left list
    	      while (arr[i] < pivot) i++;
    	      
    	      // If the current value from the right list is larger then the pivot
    	      // element then get the next element from the right list
    	      while (arr[j] > pivot) j--;

    	      // If we have found a value in the left list which is larger than
    	      // the pivot element and if we have found a value in the right list
    	      // which is smaller then the pivot element then we exchange the
    	      // values.
    	      // As we are done we can increase i and j
    	      if (i < j) {
    	        exchange(i, j);
    	        i++;
    	        j--;
    	      } else if (i == j) { i++; j--; }
        }
            
    	// Recursion
    	if(isSorted(low,high)){
        }else{
            if (low < j){
              //if(high-low < 100){
                //insertSort(low,high);
              //}else{
                quicksort(low, j);
              //}
        }
    	if (i < high){
              //if(high-low < 100){
                //insertSort(low,high);
              //}else{
                quicksort(i, high);
              //}
        }
        }
    }
    
    private static boolean isSorted(int low, int high){
          return low > high;  
    }

    public static void main(String[] args) {
   
        read = new BufferedReader(new InputStreamReader(System.in));
        
        randomGenerator = new Random();
        
        try {
            System.out.print("Please enter array size : ");
            size = Integer.parseInt(read.readLine());
            
            System.out.print("Please enter the random range : ");
            random = Integer.parseInt(read.readLine());
        } catch(Exception ex){
            ex.printStackTrace();
        }
           
        
        
        // create array
        arr = new int[size];
        arrCopy = new int[size];
        arrCopy2 = new int[size];
            
        // fill array
        for(int i=0; i<size; i++) 
           arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
        
        //code to iterate 100 times
        long mergAvgTotal = 0;
        long qSortAvgTotal = 0;
        long buAvgTotal = 0;
        long hSortAvgTotal = 0;
        long aSortAvgTotal = 0;
        for(int c = 1; c <= 100; c++){
        // built-in sort
        long start = System.currentTimeMillis();
        if (size < 101) printArray("Initial array:");
        Arrays.sort(arr);
        if (size < 101) printArray("out");
        long finish = System.currentTimeMillis();
        aSortAvgTotal = aSortAvgTotal + (finish-start);
        //System.out.println("Arrays.sort: " + (finish-start) + " milliseconds.");
	    
        /*
        // Heap sort      
        for(int i=0; i<size; i++) arr[i] = arrCopy[i];
        start = finish;
        if (size < 101) printArray("in");
        heapsort();
        if (size < 101) printArray("out");
        finish = System.currentTimeMillis();
        hSortAvgTotal = hSortAvgTotal + (finish-start);
        //System.out.println("heapsort: " + (finish-start) + " milliseconds.");
        */
        
        // Merge sort
        for(int i=0; i<size; i++) arr[i] = arrCopy[i];
        start = finish;
        if (size < 101) printArray("in");
        mergesort(0, size-1);
        if (size < 101) printArray("out");
        finish = System.currentTimeMillis();
        //System.out.println("mergesort: " + (finish-start) + " milliseconds.");
        mergAvgTotal = mergAvgTotal + (finish-start);
        //System.out.println("Merge Average: " + (mergAvgTotal/c) + " milliseconds");
        
        /*
        //bottomup mergesort
        for(int i=0; i<size; i++) arr[i] = arrCopy[i];
        start = finish;
        if (size < 101) printArray("in");
        bottomUpMergesort(0, size-1);
        if (size < 101) printArray("out");
        finish = System.currentTimeMillis();
        System.out.println("BUmergesort: " + (finish-start) + " milliseconds.");
        //buAvgTotal = buAvgTotal + (finish-start);
        //System.out.println("BU Average: " + buAvgTotal/c + "milliseconds");
        //}
        */
        
        // Quick sort
        for(int i=0; i<size; i++) arr[i] = arrCopy[i];
        start = finish;
        if (size < 101) printArray("in");
        quicksort(0, size-1);
        if (size < 101) printArray("out");
        finish = System.currentTimeMillis();
        //System.out.println("quicksort: " + (finish-start) + " milliseconds.");
        qSortAvgTotal = qSortAvgTotal + (finish-start);
        //System.out.println("Quick Average: " + (qSortAvgTotal/c) + " milliseconds");
	
        /*
        // arr[0..size-1] is already sorted. We randomly swap 100 pairs to make it nearly-sorted.
	    for (int i = 0; i < 100; i++) {
		    int j  = randomGenerator.nextInt(size);
		    int k  = randomGenerator.nextInt(size);
		    exchange(j, k);
	    }
	    for(int i=0; i<size; i++) arrCopy2[i] = arr[i];

	// Quick sort on nearly-sorted array
        start = finish;
        if (size < 101) printArray("in");
        quicksort(0, size-1);
        if (size < 101) printArray("out");
        finish = System.currentTimeMillis();
        System.out.println("quicksort on nearly-sorted: " + (finish-start) + " milliseconds.");
        }
        */
        
        /*
        // Insert sort on nearly-sorted array      
        for(int i=0; i<size; i++) arr[i] = arrCopy2[i];
	    start = finish;
        if (size < 101) printArray("in");
	    insertionSort();
	    if (size < 101) printArray("out");
	    finish = System.currentTimeMillis();
	    System.out.println("insertsort on nearly-sorted: " + (finish-start) + " milliseconds.");
        */
        }
        System.out.println("Merge Average: " + (mergAvgTotal/100) + " milliseconds");
        System.out.println("A.Sort Average: " + (aSortAvgTotal/100) + " milliseconds");
        System.out.println("Heap Average: " + (hSortAvgTotal/100) + " milliseconds");
        System.out.println("Quick Average: " + (qSortAvgTotal/100) + " milliseconds");
    }
}