import java.util.HashMap;


public class DuplicateInMatrix {
	public static void duplicate (int[][] a, int n){
		if(a==null || n<1) return;
		HashMap<Integer, Integer> ht = new HashMap<Integer, Integer>();
		for (int i=0; i<n; i++){
			if(!ht.containsKey(a[0][i]))
				ht.put(a[0][i], 1);
		}
		for (int i=1; i<n; i++){
			for (int j=0; j<n; j++){
				if(ht.containsKey(a[i][j])){
					int tmp= ht.get(a[i][j])+1;
					ht.put(a[i][j], tmp);
					break;
				}
			}
		} 
		for(int i=0; i<n; i++){
			if(ht.get(a[0][i])==n){
				System.out.print(a[0][i] +" ");
				//ht.remove(a[0][i]);
				//if(ht.isEmpty()) break;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=3;
		int[][] a={{1, 2, 3},{1, 12, 13},{1, 1, -3}};
		duplicate(a, n);

	}

}
