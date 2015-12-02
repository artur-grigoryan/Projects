package quickSort;

public class QuickSort {
	public static void quickSort (int[] a, int start, int end){
		if(a==null || a.length<=1) return;
		if(start>=end || end<=start) return;
		else {
			int pivot = a[start];
			int i=start+1;
			int tmp;
			for (int j=start+1; j<=end; j++){
				if(pivot>a[j]){
					tmp=a[i];
					a[i]=a[j];
					a[j]=tmp;
					i++;
				}
			}
			a[start]=a[i-1];
			a[i-1]=pivot;
			quickSort(a, start, i-2);
			quickSort(a, i, end);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a={9, 6, 4, 7, 3, 2, 7, 9};
		System.out.println(a.length);
		quickSort(a, 0, a.length-1);
		for (int i=0; i<a.length; i++)
			System.out.print(a[i] +" ");
		System.out.println();

	}

}
