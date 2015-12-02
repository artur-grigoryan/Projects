package string;

import java.util.Arrays;

public class ReverseString {
	public static String Reverse(String s){
        if(s.length()==0) return null;
        char[] temp=s.toCharArray();
        for(int i=0; i<temp.length/2; i++){
            char tmp = temp[i];
            temp[i]=temp[temp.length-1-i];
            temp[temp.length-i-1]=tmp;
        }
        String s2=new String(temp);
        return s2;
    }
	
	public static boolean isRotate(String s1, String s2){
		if(s1==null || s2==null) return false;
		if(s1.length()!=s2.length()) return false;
		String s=s1+s1;
		return (s.indexOf(s2)!=-1);
	}
	
	public static String sort (String s){
		if(s==null) return null;
		char[] ch=s.toCharArray();
		Arrays.sort(ch);
		String res=new String(ch);
		return res;
	}
	public static boolean isAnagram(String s1, String s2){
		if(s1==null || s2==null) return false;
		return sort(s1).equals(sort(s2));
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "asbad";
		//Reverse(s);
		//System.out.println(Reverse(s));
		String s2="asbad";
		//System.out.println(isSubstring(s, s2));
		//System.out.println(isRotate(s, s2));
		System.out.println(sort(s));
		System.out.println(sort(s2));
		System.out.println(isAnagram(s, s2));
		

	}

}
