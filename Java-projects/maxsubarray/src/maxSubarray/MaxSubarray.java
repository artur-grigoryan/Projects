package maxSubarray;

public class MaxSubarray {
	public static void findMaximum(int[] a){
		if(a==null) return;
		if(a.length<=1){
			System.out.println(a[0]);
		}
		else{
			int startIndex=0;
			int endIndex=0;
			int maxSum=Integer.MIN_VALUE;
			int currentSum=0;
			int maxCurrentIndex=0;
			for(int i=0; i<a.length; i++){
				int current=a[i];
				currentSum+=current;
				if(currentSum>maxSum){
					maxSum=currentSum;
					startIndex=maxCurrentIndex;
					endIndex=i;
				}
				else if(currentSum<0){
					currentSum=0;
					maxCurrentIndex=i+1;
				}
			}
			
			System.out.println("Max sum is: "+maxSum+", start index is: "+startIndex+", end index is: "+endIndex);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a= {3, 4, -1, -7, -1, 3, -2, 5, 4};
		findMaximum(a);

	}

}
