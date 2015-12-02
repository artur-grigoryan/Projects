package repeatedarray;

public class Repeated {
	public static int Find (int[]a){
		int result=0;
		for (int i=0; i<32; i++){
			int sum=0;
			for(int j=0; j< a.length; j++){
				if((a[j] & 1<<i)!=0)
					sum++;
			}
			if(sum%3==1)
				result= result | (1<<i);
		}
		return result;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a= {1, 3, 1, 1, 2, 2, 2};
		System.out.println(Find(a));
		
	}

}
