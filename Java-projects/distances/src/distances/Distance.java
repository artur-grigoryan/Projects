package distances;

import java.util.HashMap;

public class Distance {
	public static void distances(int[] a){
		if (a.length==0) return;
		HashMap<Integer, Integer> hm1 = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> hm2 = new HashMap<Integer, Integer>();
		for (int i=0; i<a.length; i++){
			if(!hm1.containsKey(a[i])){
				hm1.put(a[i], i);
			}
			if(!hm2.containsKey(a[a.length-i-1])){
				hm2.put(a[a.length-i-1], a.length-i-1);
			}
		}
		//int[] res = new int[hm1.size()];
		for (int i=0; i<a.length; i++){
			if(hm1.containsKey(a[i])){
			if(hm1.get(a[i])!= hm2.get(a[i])){
				System.out.print(hm2.get(a[i])-hm1.get(a[i]) + " ");
			}
			else System.out.print(-1+ " ");
			
			hm1.remove(a[i]);
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {1, 2, 3, 1, 5,5,5,5,6,7,7};
		distances(a);

	}

}
