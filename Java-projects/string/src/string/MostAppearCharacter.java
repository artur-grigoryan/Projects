package string;

import java.util.HashMap;

public class MostAppearCharacter {
	public static void mostAppear(String s){
		if(s==null) return;
		if(s.length()==1){
			System.out.println(s+": 1");
		}
		else{
			HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
			int max=0;
			char ch=' ';
			for(int i=0; i<s.length(); i++){
				char tmp=s.charAt(i);
				if(!hm.containsKey(tmp)){
					hm.put(tmp, 1);
				}
				else{
					hm.put(tmp, hm.get(tmp)+1);
				}
				if(hm.get(tmp)>max){
					max=hm.get(tmp);
					ch=tmp;
				}
			}
			System.out.println(ch+": "+max);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="aabbs";
		mostAppear(s);

	}

}
