package test;

public class Interview {
	public static int f(int n){
		System.out.print(n+" ");
		if(n<3){
			f(f(f(++n)));
		}
		return n;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		f(1);

	}

}
