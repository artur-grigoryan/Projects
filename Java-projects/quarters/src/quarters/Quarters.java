package quarters;

public class Quarters {
	public static int makeChange(int n, int demon){
		int next_demon=0;
		switch(demon) {
		case 25:
			next_demon=10;
			break;
		case 10:
			next_demon=5;
			break;
		case 5:
			next_demon=1;
			break;
		case 1:
			return 1;
		}
		int ways = 0;
		for (int i=0; i*demon<=n; i++){
			ways+=makeChange(n-i*demon, next_demon);
		}
		return ways;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(makeChange(10, 5));

	}

}
