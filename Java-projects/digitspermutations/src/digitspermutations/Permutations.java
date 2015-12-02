package digitspermutations;

import java.util.ArrayList;

public class Permutations {
	public static ArrayList<ArrayList<Integer>> f (int n, int k){
		if(n<=k || k<0 || n>9) return null;
		
		ArrayList<ArrayList<Integer>> allresults = new ArrayList<ArrayList<Integer>>();
		if(k==0){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			allresults.add(temp);
			return allresults;
		}
		
		for (int i=0; i<=n; i++){
			ArrayList<ArrayList<Integer>> res = f(n-1, k-1);
			for (int j=0; j< res.size(); j++){
				for (int l=0; l<res.get(j).size(); l++){
					allresults.add(insertTo(res.get(j), i, l));
				}
			}
		}
		
		ArrayList<ArrayList<Integer>> last = f(n-1, k);
		for (int i=0; i<last.size(); i++){
			allresults.add(last.get(i));
		}
		return allresults;
		
	}
	
	public static ArrayList<Integer> insertTo(ArrayList<Integer> a, int elem, int pos){
		int tmp=a.get(pos);
		a.add(pos, elem);
		for (int i=pos+1; i<a.size(); i++){
			int tmp2=a.get(i);
			a.add(i, tmp);
			tmp=tmp2;
		}
		return a;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 4;
		int k = 2;
		ArrayList<ArrayList<Integer>> res = f(n, k);
		for (int i=0; i<res.size(); i++){
			System.out.print(res.get(i) + " ");
		}
		System.out.println();

	}

}
