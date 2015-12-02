package search;

public class Search {
	public static int binarySearch(int[] a, int key, int start, int end){
		if(a==null) return -1;
		if(start>end) return -1;
		else{
			int mid=(end+start)/2;
			if(a[mid]>key){
				return binarySearch(a, key, start, mid-1);
			}
			else if (a[mid]<key){
				return binarySearch(a, key, mid+1, end);
			}
			return mid;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {1, 3 ,4, 5 ,6, 8, 9};
		System.out.println(binarySearch(a, 6, 0, a.length));

	}

}
