package bucketSort;

public class BucketSort {
	public static void bucketSort(int[] a, int n){
		if(a==null|| n<=0) return;
		int min=a[0];
		int max = min;
	    for( int i = 1; i < n; i++ ){
	    	if( a[i] > max ){
	    		 max = a[i];
	    	}
	    	else if( a[i] < min ){
	    		min = a[i];
	    	}
	    }
	    int[] bucket =new int[max-min+1];
	    for(int i=0; i<n; i++){
	    	bucket[a[i]-min]++;
	    }
	    int i=0;
	    for (int b=0; b<bucket.length; b++){
	    	for (int j=0; j<bucket[b]; j++){
	    		a[i++]=b+min;
	    	}
	    }
	    for (int k=0; k<a.length; k++){
	    	System.out.print(a[k]+" ");
	    } 
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a[]={23, 12, 10, 25, 12, 11};
		bucketSort(a, 6);

	}

}
