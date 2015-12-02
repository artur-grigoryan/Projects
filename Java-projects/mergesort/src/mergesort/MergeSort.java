package mergesort;

public class MergeSort {
	private int[] numbers;
	private int[] helper;
	private int number;
	public void sort(int[] values){
	
		number=values.length;
		numbers=new int[number];
		for(int i=0;i<number;i++)
			numbers[i]=values[i];
		helper=new int[number];
		mergesort(0, number-1);
		for(int i=0;i<numbers.length;i++)
		{
			System.out.print(numbers[i]+" ");
		}
		System.out.println();
	}
	
	private void mergesort(int low, int high){
		if(low<high){
			int middle= low+(high-low)/2;
			mergesort(low, middle);
			mergesort(middle+1, high);
			merge(low, middle, high);
		}
	}
	
	private void merge(int low, int middle, int high){
		for (int i=low; i<=high; i++){
			helper[i]=numbers[i];	
		}
		int i=low;
		int j=middle+1;
		int k=low;
		while(i<=middle && j<=high){
			if(helper[i]<=helper[j]){
				numbers[k]=helper[i];
				i++;
			}
			else{
				numbers[k]=helper[j];
				j++;
			}
			k++;
		}
		while (i<=middle){
			numbers[k]=helper[i];
			i++;
			k++;
		}
		
	}
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a={4, 5, 2, 7, 6, 8, 1};
		MergeSort Ob=new MergeSort();
		Ob.sort(a);

		
		

	}

}
