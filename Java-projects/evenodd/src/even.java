
public class even {
	public static int swapbits(int n){
		int oddbits = (n & 0xAAAAAAAA)>>1;
		int evenbits = (n & 0x55555555)<<1;
		int result = oddbits | evenbits;
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int res = swapbits(25);
		System.out.println(res);

	}

}
