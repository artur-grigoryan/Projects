package rotatedarray;

public class Minimum {
	public static int min(int[] a, int start, int end){
		if(a==null|| start>end) return -1;
		if(a.length<=1) return a[0];
		if(end-start==1) return Math.min(a[start], a[end]);
		if(a[start]<a[end]) return a[start];
		int min=a[0];
		int mid=(start+end)/2;
		if(a[mid]>a[start]){
			min=min(a, mid, end);
		}
		if(a[mid]<a[start]){
			min=min(a, start, mid);
		}
		return min;
				
 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[]a={1, 2, 3, 4, 5, 6};
		int m=min(a, 0, a.length-1);
		System.out.println(m);

	}

}
