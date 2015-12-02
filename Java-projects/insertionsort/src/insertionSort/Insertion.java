package insertionSort;

public class Insertion {
	public static void insertionSort (int[] a){
		if(a.length==0) {
			return;
			}

		for (int i=1; i<a.length; i++){
			int j=i;
			while(a[j]<a[j-1] && j>=1){
				int tmp=a[j];
				a[j]=a[j-1];
				a[j-1]=tmp;
				j--;
			
			}
			
		}
	}
	
	public static void selectionSort (int[] a){
		if(a.length==0) return;
		for (int i=0; i<a.length-1; i++){
			int pos=i;
			for(int j=i+1;j<a.length;j++){
				if(a[j]<a[pos]){
					pos=j;
				}
			}
		
			if(pos!=i){
				int tmp=a[i];
				a[i]=a[pos];
				a[pos]=tmp;
			}
		}
	}
	public static void bubleSort(int[] a){
		if(a.length==0) return;
		for(int i=0; i<a.length-1; i++){
			for(int j=a.length-1; j>i; j--){
				if(a[j]<a[j-1]){
					int tmp=a[j-1];
					a[j-1]=a[j];
					a[j]=tmp;
				}
			}
		}
	}
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a= {7, 4, 6, 5, 2, 1, 3, 2, 10, 8};
		bubleSort(a);
		//quickSort(a, 0, a.length);
		for (int i=0; i< a.length; i++){
			System.out.print(a[i] + " ");
		}
		System.out.println();
		

	}

}