import java.util.ArrayList;


public class Permutation {
	public static ArrayList<String> permutation (String s){
		ArrayList<String> allpermut = new ArrayList<String>();
		if(s== null) return null;
		if(s.length()==0){
			allpermut.add("");
			return allpermut;
		}
		char c = s.charAt(0);
		ArrayList<String> permut= permutation(s.substring(1));
		for (String word : permut){
				for (int i=0; i<=word.length(); i++){
				String temp = insertTo(word, c, i);
			
				
				allpermut.add(temp);
			}
		}
		return allpermut;
	}
	public static String insertTo(String s, char c, int pos){
		String first = s.substring(0, pos);
		String last = s.substring(pos);
		String joint = first+c+last;
		return joint;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "abc";
		//System.out.println(str);
		ArrayList<String> perm = permutation(str);
		for (int i=0; i< perm.size(); i++) {
			System.out.println(perm.get(i));
		}

	}

}
