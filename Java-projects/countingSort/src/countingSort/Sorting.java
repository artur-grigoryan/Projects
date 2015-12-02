package countingSort;

public class Sorting {
	public static void countingSort(int[] a){
		if(a==null || a.length<=1) return;
		int max=a[0];
		for(int i=1; i<a.length; i++){
			if (a[i]>max){
				max=a[i];
			}
		}
		int[] count = new int[max+1];
		for(int i=0; i<count.length; i++){
			count[i]=0;
		}
		for (int i=0; i<a.length; i++){
			count[a[i]]+=1;
		}
		for (int i=0; i<count.length; i++){
				for (int j=0; j<count[i]; j++){
					System.out.print(i +" ");
				}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a={2, 1, 40, 2, 4 ,5, 3, 6, 0, 9, 30, 1};
		countingSort(a);

	}

}
